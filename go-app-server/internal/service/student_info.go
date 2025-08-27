package service

import (
	"go-app-server/internal/entity/errs"
	"go-app-server/internal/entity/request"
	"go-app-server/internal/entity/response"
	"go-app-server/internal/logic"
	"go-app-server/internal/repo/dao"
	"log/slog"
	"net/http"
	"time"

	"github.com/gin-gonic/gin"
)

type CreateStudentCmd struct {
	No        string `json:"no" validate:"required"`
	Name      string `json:"name" validate:"required"`
	Sex       int8   `json:"sex" validate:"required"`
	Introduce string `json:"introduce"`
	Birthday  string `json:"birthday" validate:"required"`
	Mobile    string `json:"mobile" validate:"required,len=11"`
}
type UpdateStudentCmd struct {
	ID        int64  `json:"id" validate:"required"`
	No        string `json:"no" validate:"required"`
	Name      string `json:"name" validate:"required"`
	Sex       int8   `json:"sex" validate:"required"`
	Introduce string `json:"introduce"`
	Birthday  string `json:"birthday" validate:"required"`
	Mobile    string `json:"mobile" validate:"required,len=11"`
	Status    int8   `json:"status" validate:"required"`
}
type DeleteStudentCmd struct {
	ID int64 `json:"id" validate:"required"`
}

type StudentInfoPageQry struct {
	request.PageQry
	ID        int64  `json:"id"`
	No        string `json:"no"`
	FuzzyName string `json:"fuzzy_name" `
	Name      string `json:"name"`
	Mobile    string `json:"mobile"`
	Status    *int8  `json:"status"`
}

type StudentInfoCO struct {
	ID         int64  `json:"id"`
	No         string `json:"no"`
	Name       string `json:"name"`
	Sex        int8   `json:"sex"`
	Introduce  string `json:"introduce"`
	Birthday   string `json:"birthday"`
	Mobile     string `json:"mobile"`
	Status     int8   `json:"status"`
	CreateTime string `json:"create_time"`
}

type StudentInfoExe struct {
	studentInfoService *logic.StudentInfoService
}

func NewStudentInfoExe(studentInfoService *logic.StudentInfoService) *StudentInfoExe {
	return &StudentInfoExe{studentInfoService: studentInfoService}
}

func (e *StudentInfoExe) Create(c *gin.Context) {
	var cmd CreateStudentCmd
	if err := c.ShouldBindJSON(&cmd); err != nil {
		slog.Error("req param invalid", "error", err.Error())
		c.JSON(http.StatusOK, response.Error(errs.Newf(errs.BadArgs, err)))
		return
	}

	var birthday *time.Time
	if cmd.Birthday != "" {
		// 解析生日时间
		birthdayTmp, err := time.Parse(time.DateOnly, cmd.Birthday)
		if err != nil {
			slog.Error("parse birthday error", "error", err.Error(), "birthday", cmd.Birthday)
			c.JSON(http.StatusOK, response.Error(errs.Newf(errs.BadArgs, err)))
			return
		}
		birthday = &birthdayTmp
	}

	vo := &logic.StudentInfoVO{
		No:        cmd.No,
		Name:      cmd.Name,
		Sex:       cmd.Sex,
		Introduce: cmd.Introduce,
		Birthday:  birthday,
		Mobile:    cmd.Mobile,
		Status:    dao.StatusValid,
	}
	// 调用业务逻辑
	if err := e.studentInfoService.Add(c.Request.Context(), vo); err != nil {
		c.JSON(http.StatusOK, response.Error(err))
		return
	}
	c.JSON(http.StatusOK, response.DefaultSuccess)
}

func (e *StudentInfoExe) Update(c *gin.Context) {
	var cmd UpdateStudentCmd
	if err := c.ShouldBindJSON(&cmd); err != nil {
		slog.Error("req param invalid", "error", err.Error())
		c.JSON(http.StatusOK, response.Error(errs.Newf(errs.BadArgs, err)))
		return
	}
	var birthday *time.Time
	if cmd.Birthday != "" {
		// 解析生日时间
		birthdayTmp, err := time.Parse(time.DateOnly, cmd.Birthday)
		if err != nil {
			slog.Error("parse birthday error", "error", err.Error(), "birthday", cmd.Birthday)
			c.JSON(http.StatusOK, response.Error(errs.Newf(errs.BadArgs, err)))
			return
		}
		birthday = &birthdayTmp
	}
	vo := &logic.StudentInfoVO{
		ID:        cmd.ID,
		No:        cmd.No,
		Name:      cmd.Name,
		Sex:       cmd.Sex,
		Introduce: cmd.Introduce,
		Birthday:  birthday,
		Mobile:    cmd.Mobile,
		Status:    cmd.Status,
	}
	// 调用业务逻辑
	if err := e.studentInfoService.Update(c.Request.Context(), vo); err != nil {
		c.JSON(http.StatusOK, response.Error(err))
		return
	}
	c.JSON(http.StatusOK, response.DefaultSuccess)
}

func (e *StudentInfoExe) Delete(c *gin.Context) {
	var cmd DeleteStudentCmd
	if err := c.ShouldBindJSON(&cmd); err != nil {
		slog.Error("req param invalid", "error", err.Error())
		c.JSON(http.StatusOK, response.Error(errs.Newf(errs.BadArgs, err)))
		return
	}
	// 调用业务逻辑
	if err := e.studentInfoService.Delete(c.Request.Context(), cmd.ID); err != nil {
		c.JSON(http.StatusOK, response.Error(err))
		return
	}
	c.JSON(http.StatusOK, response.DefaultSuccess)
}

func (e *StudentInfoExe) Page(c *gin.Context) {
	var qry StudentInfoPageQry
	if err := c.ShouldBindJSON(&qry); err != nil {
		slog.Error("req param invalid", "error", err.Error())
		c.JSON(http.StatusOK, response.Error(errs.Newf(errs.BadArgs, err)))
		return
	}

	// 构建查询条件
	condition := &dao.StudentInfoPOCondition{
		ID:        qry.ID,
		No:        qry.No,
		FuzzyName: qry.FuzzyName,
		Name:      qry.Name,
		Mobile:    qry.Mobile,
		Status:    qry.Status,
	}

	// 调用业务逻辑
	pos, total, err := e.studentInfoService.QueryByPage(c.Request.Context(), condition, qry.Page, qry.PageSize)
	if err != nil {
		c.JSON(http.StatusOK, response.Error(err))
		return
	}

	var cos = make([]*StudentInfoCO, 0, len(pos))
	for _, po := range pos {
		var birthday string
		if po.Birthday != nil {
			birthday = po.Birthday.Format(time.DateOnly)
		}
		co := &StudentInfoCO{
			ID:         po.ID,
			No:         po.No,
			Name:       po.Name,
			Sex:        po.Sex,
			Introduce:  po.Introduce,
			Birthday:   birthday,
			Mobile:     po.Mobile,
			Status:     po.Status,
			CreateTime: po.Birthday.Format(time.DateTime),
		}
		cos = append(cos, co)
	}
	c.JSON(http.StatusOK, response.Success(response.Page[StudentInfoCO]{
		Total: total,
		Data:  cos,
	}))
}
