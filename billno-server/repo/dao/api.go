package dao

import "gorm.io/gorm"

// BillnoDao
// mockgen -source api.go -destination api_mock.go --package=dao
type BillnoDao interface {
	FindForUpdate(tx *gorm.DB, bizCode string) (*BillnoPO, error)
	Update(tx *gorm.DB, bizCode string, current uint64) error
	FindAll(tx *gorm.DB) ([]*BillnoPO, error)
}
