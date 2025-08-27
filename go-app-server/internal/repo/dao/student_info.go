package dao

import (
	"context"
	"errors"
	"go-app-server/internal/entity/errs"
	"gorm.io/gorm"
	"gorm.io/gorm/clause"
	"log/slog"
	"time"
)

// StudentInfoPO 学生信息实体
type StudentInfoPO struct {
	ID         int64      `gorm:"column:id;primaryKey;autoIncrement;comment:学生ID"`
	No         string     `gorm:"column:no;comment:学号"`
	Name       string     `gorm:"column:name;comment:姓名"`
	Sex        int8       `gorm:"column:sex;comment:性别：1、男；2、女"`
	Introduce  string     `gorm:"column:introduce;comment:介绍"`
	Birthday   *time.Time `gorm:"column:birthday;comment:生日"`
	Mobile     string     `gorm:"column:mobile;comment:手机号"`
	Status     int8       `gorm:"column:status;comment:状态：1、有效；2、无效"`
	CreateTime time.Time  `gorm:"column:create_time;comment:创建时间"`
	UpdateTime time.Time  `gorm:"column:update_time;comment:更新时间"`
}

// 性别常量
const (
	SexMale   = 1 // 男
	SexFemale = 2 // 女
)

// StudentInfoPOCondition 多条件查询
type StudentInfoPOCondition struct {
	ID        int64
	No        string
	FuzzyName string
	Name      string
	Mobile    string
	Status    *int8
}

// TableName 自定义表名
func (s StudentInfoPO) TableName() string {
	return "student_info"
}

type StudentInfoDao struct {
}

// QueryByPage 分页查询
func (d *StudentInfoDao) QueryByPage(ctx context.Context, condition *StudentInfoPOCondition, page int, pageSize int) ([]*StudentInfoPO, int64, error) {
	db := GetDbFromContext(ctx).Limit(pageSize).Offset(pageSize * (page - 1))
	if condition.ID != 0 {
		db.Where("id = ?", condition.ID)
	}
	if condition.No != "" {
		db.Where("no = ?", condition.No)
	}
	if condition.FuzzyName != "" {
		db.Where("name LIKE ?", "%"+condition.FuzzyName+"%")
	}
	if condition.Name != "" {
		db.Where("name = ?", condition.Name)
	}
	if condition.Mobile != "" {
		db.Where("mobile = ?", condition.Mobile)
	}
	if condition.Status != nil {
		db.Where("status = ?", condition.Status)
	}

	db.Order("create_time desc")
	var pos []*StudentInfoPO
	if result := db.Find(&pos); result.Error != nil {
		return nil, 0, result.Error
	}
	var total int64
	if result := db.Count(&total); result.Error != nil {
		return nil, 0, result.Error
	}
	return pos, total, nil
}

// Query 根据主键查询
func (d *StudentInfoDao) Query(ctx context.Context, id int64) (*StudentInfoPO, error) {
	var po StudentInfoPO
	result := GetDbFromContext(ctx).Take(&po, "id=?", id)
	if nil == result.Error {
		return &po, nil
	}
	if errors.Is(result.Error, gorm.ErrRecordNotFound) {
		return nil, nil
	}
	slog.ErrorContext(ctx, "db query error", "error", result.Error)
	return nil, errs.Newf(errs.SqlError)
}

// QueryForUpdate 根据主键加锁查询
func (d *StudentInfoDao) QueryForUpdate(ctx context.Context, id int64) (*StudentInfoPO, error) {
	var po StudentInfoPO
	result := GetDbFromContext(ctx).Clauses(clause.Locking{Strength: "UPDATE"}).Take(&po, "id=?", id)
	if nil == result.Error {
		return &po, nil
	}
	if errors.Is(result.Error, gorm.ErrRecordNotFound) {
		return nil, nil
	}
	slog.ErrorContext(ctx, "db query error", "error", result.Error)
	return nil, errs.Newf(errs.SqlError)
}

// QueryByNo 根据学号查询
func (d *StudentInfoDao) QueryByNo(ctx context.Context, no string) (*StudentInfoPO, error) {
	var po StudentInfoPO
	result := GetDbFromContext(ctx).Take(&po, "no=?", no)
	if nil == result.Error {
		return &po, nil
	}
	if errors.Is(result.Error, gorm.ErrRecordNotFound) {
		return nil, nil
	}
	slog.ErrorContext(ctx, "db query error", "error", result.Error)
	return nil, errs.Newf(errs.SqlError)
}

// Create 插入
func (d *StudentInfoDao) Create(ctx context.Context, po *StudentInfoPO) error {
	result := GetDbFromContext(ctx).Create(po)
	if result.Error != nil {
		slog.ErrorContext(ctx, "db create", "error", result.Error)
		return errs.Newf(errs.SqlError)
	}
	if result.RowsAffected != 1 {
		slog.ErrorContext(ctx, "create rows affected not match", "expected", 1, "actual", result.RowsAffected)
		return errs.Newf(errs.RowsAffectedNotMatch)
	}
	return nil
}

// Update 根据主键更新
func (d *StudentInfoDao) Update(ctx context.Context, po *StudentInfoPO) error {
	result := GetDbFromContext(ctx).Limit(1).Save(po)
	if result.Error != nil {
		slog.ErrorContext(ctx, "db update error", "error", result.Error)
		return errs.Newf(errs.SqlError)
	}
	if result.RowsAffected != 1 {
		slog.ErrorContext(ctx, "update rows affected not match", "expected", 1, "actual", result.RowsAffected)
		return errs.Newf(errs.RowsAffectedNotMatch)
	}
	return nil
}

// Delete 根据主键删除
func (d *StudentInfoDao) Delete(ctx context.Context, id int64) error {
	result := GetDbFromContext(ctx).Where("id=?", id).Limit(1).Delete(&StudentInfoPO{})
	if result.Error != nil {
		slog.ErrorContext(ctx, "db delete error", "error", result.Error)
		return errs.Newf(errs.SqlError)
	}
	if result.RowsAffected != 1 {
		slog.ErrorContext(ctx, "delete rows affected not match", "expected", 1, "actual", result.RowsAffected)
		return errs.Newf(errs.RowsAffectedNotMatch)
	}
	return nil
}
