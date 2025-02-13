package logic

import (
	"billno-server/entity/errs"
	"billno-server/repo/dao"
	log "github.com/sirupsen/logrus"
	"gorm.io/gorm"
	"sync"
	"time"
)

type billnoServiceImpl struct {
	billnoDao dao.BillnoDao
}

type BizBillnoItem struct {
	mutex      sync.Mutex
	current    uint64
	currentMax uint64
}

func NewBillnoService(billnoDao dao.BillnoDao) *billnoServiceImpl {
	b := &billnoServiceImpl{billnoDao: billnoDao}
	err := b.init()
	if err != nil {
		log.Panic(err)
	}
	return b
}

var (
	billnoMap = sync.Map{}
	once      sync.Once
)

func (s *billnoServiceImpl) init() error {
	var e error
	once.Do(func() {
		addCache := func(po *dao.BillnoPO) {
			billnoMap.Store(po.BizCode, &BizBillnoItem{
				mutex:      sync.Mutex{},
				current:    po.Current,
				currentMax: po.Current + uint64(po.Step),
			})
		}
		all, err := s.billnoDao.FindAll(dao.GetDbWithoutCtx())
		if err != nil {
			e = err
		}
		for _, po := range all {
			addCache(po)
			log.Infof("add biz_code [%s] current [%d] step [%d] ", po.BizCode, po.Current, po.Step)
		}
		// 定时加载增量
		go func() {
			ticker := time.NewTicker(1 * time.Minute)
			for _ = range ticker.C {
				all, err = s.billnoDao.FindAll(dao.GetDbWithoutCtx())
				if err != nil {
					log.Errorf("hot add biz_code err [%s]", err)
				}
				for _, po := range all {
					_, ok := billnoMap.Load(po.BizCode)
					if !ok {
						addCache(po)
						log.Infof("hot add biz_code [%s] current [%d] step [%d] ", po.BizCode, po.Current, po.Step)
					}
				}
			}
		}()
	})
	return e
}

func (s *billnoServiceImpl) GetBillno(bizCode string) (uint64, error) {
	value, ok := billnoMap.Load(bizCode)
	if !ok {
		return 0, errs.BizCodeCacheNotReady.Newf(bizCode)
	}
	item := value.(*BizBillnoItem)
	item.mutex.Lock()
	defer item.mutex.Unlock()
	// 从DB 加载
	if item.current >= item.currentMax {
		current, step, err := s.fetchFromDB(bizCode)
		if err != nil {
			return 0, err
		}
		item.current = current
		item.currentMax = current + uint64(step)
	}
	result := item.current
	item.current++
	return result, nil
}

func (s *billnoServiceImpl) fetchFromDB(bizCode string) (uint64, uint, error) {
	var current uint64
	var step uint
	err := dao.GetDbWithoutCtx().Transaction(func(tx *gorm.DB) error {
		po, err := s.billnoDao.FindForUpdate(tx, bizCode)
		if err != nil {
			return err
		}
		if po == nil {
			return errs.BizCodeNotFound.Newf(bizCode)
		}
		// 更新
		if err = s.billnoDao.Update(tx, bizCode, po.Current+uint64(po.Step)); err != nil {
			return err
		}
		current = po.Current
		step = po.Step
		return nil
	})
	if err != nil {
		return 0, 0, err
	}
	return current, step, nil
}
