package logic

import (
	"jwt-server/entity/jwt"
)

type JwtService interface {
	Sign(info *jwt.JwtInfo) (string, error)
	Verify(token string) (*jwt.JwtInfo, error)
}

type jwtServiceImpl struct {
	signKey []byte
}

func NewJwtService(signKey []byte) JwtService {
	return &jwtServiceImpl{signKey: signKey}
}

func (j *jwtServiceImpl) Sign(info *jwt.JwtInfo) (string, error) {
	signed, err := jwt.Sign(info, j.signKey)
	return signed, err
}

func (j *jwtServiceImpl) Verify(token string) (*jwt.JwtInfo, error) {
	jwtInfo, err := jwt.Verify(token, j.signKey)
	return jwtInfo, err
}
