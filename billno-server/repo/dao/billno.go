package dao

import (
	"billno-server/entity/errs"
	"errors"
	"gorm.io/gorm"
	"gorm.io/gorm/clause"
	"time"
)

type billnoDaoImpl struct {
}

func NewBillnoDao() BillnoDao {
	return &billnoDaoImpl{}
}

type BillnoPO struct {
	BizCode    string `gorm:"primaryKey"`
	BizDesc    string
	Current    uint64
	Step       uint
	UpdateTime time.Time
}

func (po *BillnoPO) TableName() string {
	return "t_billno"
}

func (d *billnoDaoImpl) FindForUpdate(tx *gorm.DB, bizCode string) (*BillnoPO, error) {
	var po BillnoPO
	result := tx.Clauses(clause.Locking{Strength: "UPDATE"}).Where("biz_code = ?", bizCode).Take(&po)
	if result.Error != nil {
		if errors.Is(result.Error, gorm.ErrRecordNotFound) {
			return nil, nil
		}
		return nil, errs.SqlError.Newf(result.Error)
	}
	return &po, nil
}

func (d *billnoDaoImpl) Update(tx *gorm.DB, bizCode string, current uint64) error {
	result := tx.Select("Current", "UpdateTime").
		Where("biz_code = ?", bizCode).Limit(2).
		Updates(&BillnoPO{Current: current, UpdateTime: time.Now()})
	if result.Error != nil {
		return errs.SqlError.Newf(result.Error)
	}
	if result.RowsAffected != 1 {
		return errs.SqlAffectError.Newf(1, result.RowsAffected)
	}
	return nil
}

func (d *billnoDaoImpl) FindAll(tx *gorm.DB) ([]*BillnoPO, error) {
	var all []*BillnoPO
	result := tx.Find(&all)
	if result.Error != nil {
		return nil, errs.SqlError.Newf(result.Error)
	}
	return all, nil
}
