package server

import (
	log "github.com/sirupsen/logrus"
	"google.golang.org/grpc"
	"google.golang.org/grpc/keepalive"
	"jwt-server/entity/pb"
	"net"
	"time"
)

var kp = keepalive.ServerParameters{
	MaxConnectionIdle: 10 * time.Minute, // 如果连接空闲超过这个时间，服务端将关闭连接
}
var kep = keepalive.EnforcementPolicy{
	PermitWithoutStream: true, // 空闲时候发ping ，而不是断开连接
}

// Start 启动服务器
func Start(address string, jwtServer pb.JwtServer) *grpc.Server {
	var grpcServer = grpc.NewServer(grpc.KeepaliveParams(kp), grpc.KeepaliveEnforcementPolicy(kep))
	pb.RegisterJwtServer(grpcServer, jwtServer)

	go func() {
		listen, err := net.Listen("tcp", address)
		if err != nil {
			log.Fatalf("listen error [%s]", err)
		}
		log.Infof("server listen %s", address)
		err = grpcServer.Serve(listen)
		if err != nil {
			log.Fatalf("server serve error [%s]", err)
		} else {

		}
	}()
	return grpcServer
}

func Stop(grpcServer *grpc.Server) {
	grpcServer.GracefulStop()
}
