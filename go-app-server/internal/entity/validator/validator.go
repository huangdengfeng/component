package validator

import (
	"github.com/go-playground/validator/v10"
	"go-app-server/internal/entity/errs"
	"log/slog"
)

var Validator = validator.New(validator.WithRequiredStructEnabled())

func Struct(any any) error {
	if err := Validator.Struct(any); err != nil {
		slog.Error("param invalid", "error", err)
		return errs.Newf(errs.BadArgs, err)
	}
	return nil
}

func Var(filed any, tag string) error {
	if err := Validator.Var(filed, tag); err != nil {
		slog.Error("param invalid", "error", err)
		return errs.Newf(errs.BadArgs, err)
	}
	return nil
}
