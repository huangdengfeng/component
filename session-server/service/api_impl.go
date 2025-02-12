package service

import (
	"context"
	"errors"
	"session-server/entity/errs"
	"session-server/entity/pb"
	"session-server/logic"
)

type sessionServerImpl struct {
	sessionService logic.SessionService
	pb.UnimplementedSessionServer
}

func NewSessionServer(sessionService logic.SessionService) pb.SessionServer {
	return &sessionServerImpl{sessionService: sessionService}
}

func (s *sessionServerImpl) Create(ctx context.Context, req *pb.CreateReq) (*pb.CreateResp, error) {
	if req.MaxInactiveInterval <= 0 {
		return &pb.CreateResp{ErrCode: errs.BasArgs.Code, ErrMsg: errs.BasArgs.Msgf("maxInactiveInterval must greater than 0")}, nil
	}
	sessionId, err := s.sessionService.Create(ctx, req.MaxInactiveInterval, req.Attributes)
	if err != nil {
		e := errs.Parse(err)
		return &pb.CreateResp{ErrCode: e.Code, ErrMsg: e.Msg}, nil
	}
	return &pb.CreateResp{SessionId: sessionId}, nil
}

func (s *sessionServerImpl) SetAttribute(ctx context.Context, req *pb.SetAttributeReq) (*pb.SetAttributeResp, error) {
	if len(req.SessionId) <= 0 {
		return &pb.SetAttributeResp{ErrCode: errs.BasArgs.Code, ErrMsg: errs.BasArgs.Msgf("sessionId is empty")}, nil
	}
	if len(req.Key) <= 0 {
		return &pb.SetAttributeResp{ErrCode: errs.BasArgs.Code, ErrMsg: errs.BasArgs.Msgf("attribute field is empty")}, nil
	}
	err := s.sessionService.SetAttribute(ctx, req.SessionId, req.Key, req.Value)
	if err != nil {
		if errors.Is(err, errs.SessionInvalid) {
			return &pb.SetAttributeResp{SessionInvalid: true}, nil
		}
		e := errs.Parse(err)
		return &pb.SetAttributeResp{ErrCode: e.Code, ErrMsg: e.Msg}, nil
	}
	return &pb.SetAttributeResp{}, nil
}

func (s *sessionServerImpl) GetAttribute(ctx context.Context, req *pb.GetAttributeReq) (*pb.GetAttributeResp, error) {
	if len(req.SessionId) <= 0 {
		return &pb.GetAttributeResp{ErrCode: errs.BasArgs.Code, ErrMsg: errs.BasArgs.Msgf("sessionId is empty")}, nil
	}
	if len(req.Key) <= 0 {
		return &pb.GetAttributeResp{ErrCode: errs.BasArgs.Code, ErrMsg: errs.BasArgs.Msgf("attribute field is empty")}, nil
	}
	value, err := s.sessionService.GetAttribute(ctx, req.GetSessionId(), req.GetKey())
	if err != nil {
		if errors.Is(err, errs.SessionInvalid) {
			return &pb.GetAttributeResp{SessionInvalid: true}, nil
		}
		e := errs.Parse(err)
		return &pb.GetAttributeResp{ErrCode: e.Code, ErrMsg: e.Msg}, nil
	}
	return &pb.GetAttributeResp{SessionInvalid: false, Value: value}, err
}

func (s *sessionServerImpl) GetAllAttribute(ctx context.Context, req *pb.GetAllAttributeReq) (*pb.GetAllAttributeResp, error) {
	if len(req.SessionId) <= 0 {
		return &pb.GetAllAttributeResp{ErrCode: errs.BasArgs.Code, ErrMsg: errs.BasArgs.Msgf("sessionId is empty")}, nil
	}
	all, err := s.sessionService.GetAllAttribute(ctx, req.GetSessionId())
	if err != nil {
		if errors.Is(err, errs.SessionInvalid) {
			return &pb.GetAllAttributeResp{SessionInvalid: true}, nil
		}
		e := errs.Parse(err)
		return &pb.GetAllAttributeResp{ErrCode: e.Code, ErrMsg: e.Msg}, nil
	}
	return &pb.GetAllAttributeResp{Attributes: all}, err
}

func (s *sessionServerImpl) RemoveAttribute(ctx context.Context, req *pb.RemoveAttributeReq) (*pb.RemoveAttributeResp, error) {
	if len(req.SessionId) <= 0 {
		return &pb.RemoveAttributeResp{ErrCode: errs.BasArgs.Code, ErrMsg: errs.BasArgs.Msgf("sessionId is empty")}, nil
	}
	if len(req.Key) <= 0 {
		return &pb.RemoveAttributeResp{ErrCode: errs.BasArgs.Code, ErrMsg: errs.BasArgs.Msgf("attribute field is empty")}, nil
	}
	if err := s.sessionService.RemoveAttribute(ctx, req.SessionId, req.Key); err != nil {
		if errors.Is(err, errs.SessionInvalid) {
			return &pb.RemoveAttributeResp{SessionInvalid: true}, nil
		}
		e := errs.Parse(err)
		return &pb.RemoveAttributeResp{ErrCode: e.Code, ErrMsg: e.Msg}, nil
	}
	return &pb.RemoveAttributeResp{}, nil
}

func (s *sessionServerImpl) Invalidate(ctx context.Context, req *pb.InvalidateReq) (*pb.InvalidateResp, error) {
	if err := s.sessionService.Invalidate(ctx, req.SessionId); err != nil {
		return nil, err
	}
	return &pb.InvalidateResp{}, nil
}
