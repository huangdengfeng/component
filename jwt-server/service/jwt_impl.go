package service

import (
	"context"
	"errors"
	log "github.com/sirupsen/logrus"
	"jwt-server/entity/errs"
	"jwt-server/entity/jwt"
	"jwt-server/entity/pb"
	"jwt-server/logic"
)

type jwtServerImpl struct {
	pb.UnimplementedJwtServer
	jwtService logic.JwtService
}

func NewJwtServerImpl(jwtService logic.JwtService) pb.JwtServer {
	return &jwtServerImpl{jwtService: jwtService}
}

func (j *jwtServerImpl) Sign(ctx context.Context, req *pb.SignReq) (*pb.SignResp, error) {
	if req.JwtInfo == nil {
		return &pb.SignResp{ErrCode: errs.BasArgs.Code, ErrMsg: errs.BasArgs.Newf("JwtInfo is nil").Msg}, nil
	}
	log.Debugf("sign recieve:%+v", req)
	jwtInfo := &jwt.JwtInfo{
		Sub:        req.JwtInfo.Sub,
		Iss:        req.JwtInfo.Iss,
		Aud:        req.JwtInfo.Aud,
		Exp:        req.JwtInfo.Exp,
		Nbf:        req.JwtInfo.Nbf,
		Iat:        req.JwtInfo.Iat,
		Jti:        req.JwtInfo.Jti,
		Attributes: req.JwtInfo.Attributes,
	}
	signed, err := j.jwtService.Sign(jwtInfo)
	if err != nil {
		log.Errorf("sign error [%s]", err)
		e := errs.Parse(err)
		return &pb.SignResp{ErrCode: e.Code, ErrMsg: e.Msg}, nil
	}
	return &pb.SignResp{Token: signed}, nil
}

func (j *jwtServerImpl) Verify(ctx context.Context, req *pb.VerifyReq) (*pb.VerifyResp, error) {
	jwtInfo, err := j.jwtService.Verify(req.Token)
	if err != nil {
		log.Errorf("Verify error [%s]", err)
		if errors.Is(err, errs.JwtTokenExpired) {
			return &pb.VerifyResp{Expired: true}, nil
		}
		e := errs.Parse(err)
		return &pb.VerifyResp{ErrCode: e.Code, ErrMsg: e.Msg}, nil
	}

	return &pb.VerifyResp{
		JwtInfo: &pb.JwtInfo{
			Sub:        jwtInfo.Sub,
			Iss:        jwtInfo.Iss,
			Aud:        jwtInfo.Aud,
			Exp:        jwtInfo.Exp,
			Nbf:        jwtInfo.Nbf,
			Iat:        jwtInfo.Iat,
			Jti:        jwtInfo.Jti,
			Attributes: jwtInfo.Attributes,
		},
	}, nil
}
