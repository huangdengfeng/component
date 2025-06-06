package client

import (
	log "github.com/sirupsen/logrus"
	"google.golang.org/grpc"
	"google.golang.org/grpc/credentials/insecure"
	"google.golang.org/grpc/keepalive"
	"jwt-server/entity/pb"
	"time"
)

// 心跳
var kacp = keepalive.ClientParameters{
	Time:                3 * time.Minute,  // 客户端每隔1min发送一次心跳ping
	Timeout:             10 * time.Second, // 如果没有收到服务端的心跳响应，认为连接失败的超时时间
	PermitWithoutStream: true,             // 即使没有活动的RPC流，也允许发送心跳
}

// 连接超时
var connectParams = grpc.ConnectParams{MinConnectTimeout: 3 * time.Second}
var DefaultTimeout = time.Second * 6

func CreateClient(target string) pb.JwtClient {

	opts := []grpc.DialOption{grpc.WithKeepaliveParams(kacp),
		grpc.WithTransportCredentials(insecure.NewCredentials()),
		grpc.WithConnectParams(connectParams)}
	opts = append(opts)

	conn, err := grpc.NewClient(target, opts...)
	if err != nil {
		log.Fatalf("connect error [%s]", err)
	}
	client := pb.NewJwtClient(conn)
	return client
}
