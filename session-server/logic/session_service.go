package logic

import (
	"context"
	"session-server/entity/errs"
	"session-server/entity/session"
	"session-server/repo/cache"
	"slices"
	"strconv"
	"time"
)

type SessionService interface {
	Create(ctx context.Context, maxInactiveInterval int64, attributes map[string][]byte) (string, error)
	Invalidate(ctx context.Context, sessionId string) error
	GetAttribute(ctx context.Context, sessionId string, field string) ([]byte, error)
	SetAttribute(ctx context.Context, sessionId string, field string, value []byte) error
	GetAllAttribute(ctx context.Context, sessionId string) (map[string][]byte, error)
	RemoveAttribute(ctx context.Context, sessionId string, field string) error
	GetMaxInactiveInterval(ctx context.Context, sessionId string) (int64, error)
}

type sessionServiceImpl struct {
	redisDao *cache.RedisDao
}

const (
	MaxInactiveIntervalKey = "_maxInactiveInterval"
	CreateTimeKey          = "_createTime"
)

var InternalKey = []string{MaxInactiveIntervalKey, CreateTimeKey}

func NewSessionService(redisDao *cache.RedisDao) SessionService {
	return &sessionServiceImpl{redisDao: redisDao}
}

// Create 创建session
//
//	@Description: 创建session
//	@param ctx
//	@param maxInactiveInterval 超时时间单位秒
//	@param attributes 属性，可以为空
//	@return string sessionId
//	@return error
func (s *sessionServiceImpl) Create(ctx context.Context, maxInactiveInterval int64, attributes map[string][]byte) (string, error) {
	if maxInactiveInterval <= 0 {
		return "", errs.BasArgs.Newf("maxInactiveInterval must greater than 0")
	}
	if attributes == nil {
		attributes = make(map[string][]byte)
	}
	for k, _ := range attributes {
		if len(k) == 0 {
			return "", errs.BasArgs.Newf("attribute key must not empty")
		}
		if slices.Contains(InternalKey, k) {
			return "", errs.AttrKeyLimit.Newf(k)
		}
	}
	attributes[MaxInactiveIntervalKey] = []byte(strconv.FormatInt(maxInactiveInterval, 10))
	attributes[CreateTimeKey] = []byte(strconv.FormatInt(time.Now().Unix(), 10))
	fv := make([]any, 0, len(attributes)*2+len(InternalKey))
	for k, v := range attributes {
		fv = append(fv, k, v)
	}
	sessionId := session.New()
	if err := s.redisDao.Hset(ctx, sessionId, fv...); err != nil {
		return "", err
	}
	if err := s.redisDao.Expire(ctx, sessionId, time.Duration(maxInactiveInterval)*time.Second); err != nil {
		return "", err
	}
	return sessionId, nil
}

func (s *sessionServiceImpl) Invalidate(ctx context.Context, sessionId string) error {
	if len(sessionId) <= 0 {
		return errs.BasArgs.Newf("sessionId is empty")
	}
	return s.redisDao.Del(ctx, sessionId)
}

func (s *sessionServiceImpl) GetAttribute(ctx context.Context, sessionId string, field string) ([]byte, error) {
	if len(sessionId) <= 0 {
		return nil, errs.BasArgs.Newf("sessionId is empty")
	}
	if len(field) <= 0 {
		return nil, errs.BasArgs.Newf("attribute field is empty")
	}
	maxInactiveInterval, err := s.GetMaxInactiveInterval(ctx, sessionId)
	if err != nil {
		return nil, err
	}
	value, err := s.redisDao.Hget(ctx, sessionId, field)
	if err != nil {
		return nil, err
	}
	if err := s.redisDao.Expire(ctx, sessionId, time.Duration(maxInactiveInterval)*time.Second); err != nil {
		return nil, err
	}
	return value, nil
}

func (s *sessionServiceImpl) SetAttribute(ctx context.Context, sessionId string, field string, value []byte) error {
	if len(sessionId) <= 0 {
		return errs.BasArgs.Newf("sessionId is empty")
	}
	if len(field) <= 0 {
		return errs.BasArgs.Newf("attribute field is empty")
	}
	maxInactiveInterval, err := s.GetMaxInactiveInterval(ctx, sessionId)
	if err != nil {
		return err
	}
	if err := s.redisDao.Hset(ctx, sessionId, field, value); err != nil {
		return err
	}
	if err := s.redisDao.Expire(ctx, sessionId, time.Duration(maxInactiveInterval)*time.Second); err != nil {
		return err
	}
	return nil
}

func (s *sessionServiceImpl) RemoveAttribute(ctx context.Context, sessionId string, field string) error {
	if len(sessionId) <= 0 {
		return errs.BasArgs.Newf("sessionId is empty")
	}
	if len(field) <= 0 {
		return errs.BasArgs.Newf("attribute field is empty")
	}
	maxInactiveInterval, err := s.GetMaxInactiveInterval(ctx, sessionId)
	if err != nil {
		return err
	}
	if err := s.redisDao.Hdel(ctx, sessionId, field); err != nil {
		return err
	}
	if err := s.redisDao.Expire(ctx, sessionId, time.Duration(maxInactiveInterval)*time.Second); err != nil {
		return err
	}
	return nil
}

func (s *sessionServiceImpl) GetMaxInactiveInterval(ctx context.Context, sessionId string) (int64, error) {
	if len(sessionId) <= 0 {
		return 0, errs.BasArgs.Newf("sessionId is empty")
	}
	maxInactiveInterval, err := s.redisDao.Hget(ctx, sessionId, MaxInactiveIntervalKey)
	if err != nil {
		return 0, err
	}
	if len(maxInactiveInterval) == 0 {
		return 0, errs.SessionInvalid
	}
	expireSeconds, err := strconv.Atoi(string(maxInactiveInterval))
	if err != nil {
		return 0, errs.RedisError.Newf(err)
	}
	return int64(expireSeconds), nil
}

func (s *sessionServiceImpl) GetAllAttribute(ctx context.Context, sessionId string) (map[string][]byte, error) {
	if len(sessionId) <= 0 {
		return nil, errs.BasArgs.Newf("sessionId is empty")
	}
	maxInactiveInterval, err := s.GetMaxInactiveInterval(ctx, sessionId)
	if err != nil {
		return nil, err
	}
	value, err := s.redisDao.HgetAll(ctx, sessionId)
	if err != nil {
		return nil, err
	}
	if err := s.redisDao.Expire(ctx, sessionId, time.Duration(maxInactiveInterval)*time.Second); err != nil {
		return nil, err
	}
	for _, v := range InternalKey {
		delete(value, v)
	}
	return value, nil
}
