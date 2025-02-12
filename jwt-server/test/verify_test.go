package test

import (
	"context"
	"github.com/stretchr/testify/assert"
	"jwt-server/entity/errs"
	"jwt-server/entity/pb"
	"testing"
	"time"
)

func TestVerify(t *testing.T) {
	signResp, err := gclient.Sign(context.Background(), &pb.SignReq{JwtInfo: &pb.JwtInfo{
		Sub:        "",
		Iss:        "",
		Aud:        nil,
		Exp:        0,
		Nbf:        0,
		Iat:        0,
		Jti:        "",
		Attributes: nil,
	}})
	assert.NoError(t, err)
	verifyResp, err := gclient.Verify(context.Background(), &pb.VerifyReq{
		Token: signResp.Token,
	})
	assert.NoError(t, err)
	assert.NotNil(t, verifyResp)
}

func TestVerifyErr(t *testing.T) {

	verifyResp, err := gclient.Verify(context.Background(), &pb.VerifyReq{
		Token: "123",
	})
	assert.NoError(t, err)
	assert.True(t, verifyResp.ErrCode == errs.JwtError.Code)
}
func TestVerifyExpired(t *testing.T) {
	signResp, err := gclient.Sign(context.Background(), &pb.SignReq{JwtInfo: &pb.JwtInfo{
		Sub:        "",
		Iss:        "",
		Aud:        nil,
		Exp:        time.Now().Add(-10 * time.Second).Unix(),
		Nbf:        0,
		Iat:        0,
		Jti:        "",
		Attributes: nil,
	}})
	assert.NoError(t, err)
	resp, err := gclient.Verify(context.Background(), &pb.VerifyReq{
		Token: signResp.Token,
	})
	assert.NoError(t, err)
	assert.True(t, resp.ErrCode == errs.JwtTokenExpired.Code)
}

func BenchmarkVerify(b *testing.B) {
	signResp, err := gclient.Sign(context.Background(), &pb.SignReq{JwtInfo: &pb.JwtInfo{
		Sub: "test subject",
		Iss: "xxxxxxxxxxxxxxxxx",
		Aud: nil,
		Exp: time.Now().Add(10 * time.Minute).Unix(),
		Nbf: 0,
		Iat: 0,
		Jti: "xxxxxxxxxxxxxxxxx",
		Attributes: map[string]string{
			"checkSum": "xxxxxxxxxxxxxxxxx",
		},
	}})
	assert.NoError(b, err)
	token := signResp.Token
	b.ResetTimer()
	for i := 0; i < b.N; i++ {
		resp, err := gclient.Verify(context.Background(), &pb.VerifyReq{
			Token: token,
		})
		assert.NoError(b, err)
		assert.True(b, resp.ErrCode == 0)
	}
}
