package logic

import (
	"context"
	"go-app-server/internal/entity/errs"
	"go-app-server/internal/entity/validator"
	"go-app-server/internal/repo/dao"
	"log/slog"
	"time"
)

type StudentInfoService struct {
	studentInfoDao *dao.StudentInfoDao
}

func NewStudentInfoService(studentInfoDao *dao.StudentInfoDao) *StudentInfoService {
	return &StudentInfoService{studentInfoDao: studentInfoDao}
}

type StudentInfoVO struct {
	ID        int64
	No        string `validate:"required"`
	Name      string `validate:"required"`
	Sex       int8   `validate:"required"`
	Introduce string
	Birthday  *time.Time
	Mobile    string
	Status    int8 `validate:"required"`
}

func (s *StudentInfoService) Add(ctx context.Context, vo *StudentInfoVO) error {
	if err := validator.Struct(vo); err != nil {
		return err
	}
	po := &dao.StudentInfoPO{
		No:         vo.No,
		Name:       vo.Name,
		Sex:        vo.Sex,
		Introduce:  vo.Introduce,
		Birthday:   vo.Birthday,
		Mobile:     vo.Mobile,
		Status:     vo.Status,
		CreateTime: time.Now(),
		UpdateTime: time.Now(),
	}
	return dao.DoTransaction(ctx, func(ctx context.Context) error {
		return s.studentInfoDao.Create(ctx, po)
	})
}

func (s *StudentInfoService) Update(ctx context.Context, vo *StudentInfoVO) error {
	if err := validator.Struct(vo); err != nil {
		return err
	}
	if vo.ID == 0 {
		slog.Error("update id is zero")
		return errs.Newf(errs.BadArgs, "id is zero")
	}
	po, err := s.studentInfoDao.Query(ctx, vo.ID)
	if err != nil {
		return err
	}
	if po == nil {
		slog.Error("update record not found", "id", vo.ID)
		return errs.RecordNotFound
	}
	po.Name = vo.Name
	po.Sex = vo.Sex
	po.Introduce = vo.Introduce
	po.Birthday = vo.Birthday
	po.Mobile = vo.Mobile
	po.Status = vo.Status
	po.UpdateTime = time.Now()

	return dao.DoTransaction(ctx, func(ctx context.Context) error {
		return s.studentInfoDao.Update(ctx, po)
	})
}

func (s *StudentInfoService) Delete(ctx context.Context, id int64) error {
	return dao.DoTransaction(ctx, func(ctx context.Context) error {
		// 先查询记录是否存在
		po, err := s.studentInfoDao.Query(ctx, id)
		if err != nil {
			return err
		}
		if po == nil {
			slog.Error("delete record not found", "id", id)
			return errs.RecordNotFound
		}
		return s.studentInfoDao.Delete(ctx, id)
	})
}

func (s *StudentInfoService) QueryByPage(ctx context.Context, condition *dao.StudentInfoPOCondition, page int, pageSize int) ([]*dao.StudentInfoPO, int64, error) {
	return s.studentInfoDao.QueryByPage(ctx, condition, page, pageSize)
}
