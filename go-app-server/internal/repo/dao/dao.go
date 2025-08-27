package dao

import (
	"context"
	"database/sql"
	"go-app-server/internal/entity/config"
	"gorm.io/gorm"
)

// 状态常量
const (
	StatusValid   = 1 // 有效
	StatusInvalid = 2 // 无效
)

// 上下文DB句柄标识，可以用字符串
type dbKey struct {
}

func GetDbFromContext(ctx context.Context) *gorm.DB {
	value := ctx.Value(dbKey{})
	// 返回无事务的DB句柄
	if value == nil {
		value := config.Gdb.Debug().WithContext(ctx)
		return value
	}
	return value.(*gorm.DB)
}

func DoTransaction(ctx context.Context, fun func(txContext context.Context) error, opts ...*sql.TxOptions) error {
	// 已经在事务中
	if ctx.Value(dbKey{}) != nil {
		return fun(ctx)
	}
	return GetDbFromContext(ctx).Transaction(func(tx *gorm.DB) error {
		txContext := context.WithValue(ctx, dbKey{}, tx)
		return fun(txContext)
	}, opts...)
}
