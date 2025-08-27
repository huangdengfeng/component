package response

import (
	"errors"
	"go-app-server/internal/entity/errs"
)

// Response 通用响应体
type Response struct {
	Code int32  `json:"code"`
	Msg  string `json:"msg"`
	Data any    `json:"data"`
}

// DefaultSuccess 成功
var DefaultSuccess = &Response{Code: errs.Success.Code, Msg: errs.Success.Msg}

// Success 成功
func Success(data any) *Response {
	return &Response{Code: errs.SuccessCode, Msg: errs.SuccessMsg, Data: data}
}

// ErrorWithCodeMsg 失败
func ErrorWithCodeMsg(code int32, msg string) *Response {
	return &Response{Code: code, Msg: msg}
}

// Error 失败
func Error(err error) *Response {
	// 是自定义错误类型
	var e errs.Error
	if errors.As(err, &e) {
		return &Response{Code: e.Code, Msg: e.Msg}
	}
	formattedErr := errs.Newf(errs.Unknown, err.Error())
	return &Response{Code: formattedErr.Code, Msg: formattedErr.Msg}
}
