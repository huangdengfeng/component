package main

import (
	"context"
	"errors"
	"github.com/gin-gonic/gin"
	"go-app-server/internal/entity/config"
	"go-app-server/internal/logic"
	"go-app-server/internal/repo/dao"
	"go-app-server/internal/service"
	"log/slog"
	"net/http"
	"os"
	"os/signal"
	"syscall"
	"time"
)

func main() {
	// 初始化配置
	config.Init()
	defer config.Shutdown()

	// 初始化DAO层
	studentInfoDao := &dao.StudentInfoDao{}
	// 初始化Service层
	studentInfoService := logic.NewStudentInfoService(studentInfoDao)
	// 初始化Executor层
	studentInfoExe := service.NewStudentInfoExe(studentInfoService)
	// 创建gin引擎
	gin.SetMode(gin.ReleaseMode)
	engine := gin.New()
	engine.Use(gin.Recovery())
	// 注册路由
	service.RegisterRoutes(engine, studentInfoExe)

	// 创建HTTP服务器
	srv := &http.Server{
		Addr:    config.Gconfig.Server.Listen,
		Handler: engine,
	}
	// 启动服务器
	go func() {
		slog.Info("start server", "addr", config.Gconfig.Server.Listen)
		if err := srv.ListenAndServe(); err != nil && !errors.Is(err, http.ErrServerClosed) {
			slog.Error("server error", "err", err.Error())
			os.Exit(1)
		}
	}()

	// 等待中断信号以优雅地关闭服务器
	quit := make(chan os.Signal, 1)
	// 监听 SIGINT 和 SIGTERM 信号
	signal.Notify(quit, syscall.SIGINT, syscall.SIGTERM)
	<-quit
	slog.Info("shutting down server...")

	// 设置关闭超时时间
	shutdownTimeout := time.Duration(config.Gconfig.Server.ShutdownTimeout)
	ctx, cancel := context.WithTimeout(context.Background(), shutdownTimeout)
	defer cancel()

	if err := srv.Shutdown(ctx); err != nil {
		slog.Error("server forced to shutdown", "err", err.Error())
		os.Exit(1)
	}
	slog.Info("server exited")
}
