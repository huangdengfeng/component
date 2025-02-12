package test

import (
	log "github.com/sirupsen/logrus"
	"google.golang.org/grpc"
	"jwt-server/entity/config"
	"jwt-server/entity/grpc/client"
	"jwt-server/entity/grpc/server"
	"jwt-server/entity/pb"
	"jwt-server/logic"
	"jwt-server/service"
	"os"
	"testing"
)

var gclient pb.JwtClient
var gserver *grpc.Server

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

	signKey := config.Global.Jwt.SignKey
	jwtService := logic.NewJwtService([]byte(signKey))
	gserver = server.Start(config.Global.Server.Listen, service.NewJwtServerImpl(jwtService))

	// start gclient
	gclient = client.CreateClient(config.Global.Server.Listen)

	log.Infof("[test] set up init success")
}

func teardown() {
	config.Shutdown()
	server.Stop(gserver)
	log.Infof("[test] tear down success")
}
