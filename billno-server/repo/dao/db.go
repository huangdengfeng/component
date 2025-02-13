package dao

import (
	"billno-server/entity/config"
	"context"
	"gorm.io/gorm"
)

func GetDb(ctx context.Context) *gorm.DB {
	if config.SqlClient == nil {
		return nil
	}
	if config.DebugEnable {
		return config.SqlClient.Debug().WithContext(ctx)
	}
	// WithContext 返回是每个会话，可以DB sql之前互不干扰
	return config.SqlClient.WithContext(ctx)
}

func GetDbWithoutCtx() *gorm.DB {
	ctx := context.Background()
	return GetDb(ctx)
}
