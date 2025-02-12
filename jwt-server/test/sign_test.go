package test

import (
	"context"
	"github.com/stretchr/testify/assert"
	"jwt-server/entity/errs"
	"jwt-server/entity/grpc/client"
	"jwt-server/entity/pb"
	"testing"
	"time"
)

func TestSign(t *testing.T) {
	timeoutCtx, cancelFunc := context.WithTimeout(context.Background(), client.DefaultTimeout)
	defer cancelFunc()
	resp, err := gclient.Sign(timeoutCtx, &pb.SignReq{
		JwtInfo: &pb.JwtInfo{
			Sub:        "test",
			Iss:        "",
			Aud:        nil,
			Exp:        time.Now().Add(time.Hour * 2).Unix(),
			Nbf:        0,
			Iat:        0,
			Jti:        "",
			Attributes: nil,
		},
	})
	assert.NoError(t, err)
	assert.NotEmpty(t, resp.Token)
}

func TestSignErr(t *testing.T) {
	resp, err := gclient.Sign(context.Background(), &pb.SignReq{
		JwtInfo: &pb.JwtInfo{
			Sub: "test",
			Iss: "",
			Aud: nil,
			Exp: time.Now().Add(time.Hour * 2).Unix(),
			Nbf: 0,
			Iat: 0,
			Jti: "",
			Attributes: map[string]string{
				"sub": "exists sub",
			},
		},
	})
	assert.NoError(t, err)
	assert.True(t, resp.ErrCode == errs.AttrKeyLimit.Code)
	assert.Empty(t, resp.Token)
}

func BenchmarkSign(b *testing.B) {
	for i := 0; i < b.N; i++ {
		resp, err := gclient.Sign(context.Background(), &pb.SignReq{
			JwtInfo: &pb.JwtInfo{
				Sub: "test sub",
				Iss: "xxxxxxxx",
				Aud: nil,
				Exp: time.Now().Add(time.Hour * 2).Unix(),
				Nbf: 0,
				Iat: 0,
				Jti: "",
				Attributes: map[string]string{
					"checkSum": "xxxxxxxxxx",
				},
			},
		})
		assert.NoError(b, err)
		assert.NotEmpty(b, resp.Token)
	}
}
