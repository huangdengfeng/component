package main

import (
	"billno-server/entity/config"
	"billno-server/entity/grpc/server"
	"billno-server/entity/pb"
	"billno-server/repo/dao"
	"billno-server/service"
	log "github.com/sirupsen/logrus"
	"os"
	"os/signal"
	"syscall"
)

func main() {
	config.Init()
	defer config.Shutdown()

	s := server.Start(createServer())

	sig := make(chan os.Signal, 1)
	signal.Notify(sig, syscall.SIGINT, syscall.SIGTERM)
	o := <-sig
	log.Printf("recieve signal %s ,server will stop gracefully", o.String())
	server.Stop(s)
}

func createServer() pb.BillnoServer {
	billnoDao := dao.NewBillnoDao()
	return service.NewBillnoServer(billnoDao)
}
