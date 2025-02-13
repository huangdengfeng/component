package test

import (
	"billno-server/entity/config"
	gclient "billno-server/entity/grpc/client"
	"billno-server/entity/grpc/server"
	"billno-server/entity/pb"
	"billno-server/repo/dao"
	"billno-server/service"
	log "github.com/sirupsen/logrus"
	"os"
	"testing"
)

var client pb.BillnoClient

func TestMain(m *testing.M) {
	setup()
	// 运行测试
	exitCode := m.Run()
	// 退出测试
	teardown()
	os.Exit(exitCode)
}
func setup() {
	config.ServerConfigPath = "../conf"
	config.Init()
	var createServer = func() pb.BillnoServer {
		return service.NewBillnoServer(dao.NewBillnoDao())
	}
	server.Start(createServer())

	// start client
	client = gclient.CreateClient(config.Global.Server.Listen)

	log.Infof("[test] set up init success")
}

func teardown() {
	config.Shutdown()
	log.Infof("[test] tear down success")
}
