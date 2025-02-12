package jwt

import (
	"errors"
	"fmt"
	"github.com/stretchr/testify/assert"
	"jwt-server/entity/errs"
	"testing"
	"time"
)

func TestSign(t *testing.T) {
	token, err := Sign(&JwtInfo{
		Sub: "test sub",
		Iss: "",
		Aud: nil,
		Exp: time.Now().Add(time.Hour * 2).Unix(),
		Nbf: 0,
		Iat: 0,
		Jti: "",
		Attributes: map[string]string{
			"c1": "c1",
		},
	}, []byte("123456"))
	fmt.Println("token is:", token)
	assert.NoError(t, err)
}

func TestSignErr(t *testing.T) {
	token, err := Sign(&JwtInfo{
		Sub: "test sub",
		Iss: "",
		Aud: nil,
		Exp: 0,
		Nbf: 0,
		Iat: 0,
		Jti: "",
		Attributes: map[string]string{
			"sub": "c1",
		},
	}, []byte("123456"))
	assert.True(t, len(token) == 0)
	assert.Equal(t, err.(*errs.Error).Code, errs.AttrKeyLimit.Code)
}

func TestIs(t *testing.T) {
	f := func() error {
		return errs.JwtTokenExpired
	}
	err := f()
	fmt.Println(errors.Is(err, errs.JwtTokenExpired))
	fmt.Println(errors.Is(err, errs.JwtError))
}
