package test

import (
	"go-app-server/internal/entity/config"
	"os"
	"testing"
)

// TestMain 在所有测试开始前运行一次，结束后运行一次
func TestMain(m *testing.M) {
	setup()
	// 运行所有测试
	code := m.Run()
	teardown()
	// 退出
	os.Exit(code)
}

func setup() {
	config.ConfigPath = "../conf"
	config.Init()
}
func teardown() {
	config.Shutdown()
}
