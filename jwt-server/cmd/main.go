package main

import (
	log "github.com/sirupsen/logrus"
	"google.golang.org/grpc"
	"jwt-server/entity/config"
	"jwt-server/entity/grpc/server"
	"jwt-server/logic"
	"jwt-server/service"
	"os"
	"os/signal"
	"syscall"
)

func main() {
	config.Init()
	defer config.Shutdown()
	s := createServer()
	sig := make(chan os.Signal, 1)
	signal.Notify(sig, syscall.SIGINT, syscall.SIGTERM)
	o := <-sig
	log.Infof("recieve signal %s ,server will stop gracefully", o.String())
	server.Stop(s)
}

func createServer() *grpc.Server {
	address := config.Global.Server.Listen
	signKey := config.Global.Jwt.SignKey

	jwtService := logic.NewJwtService([]byte(signKey))
	jwtServer := service.NewJwtServerImpl(jwtService)
	s := server.Start(address, jwtServer)
	return s
}
