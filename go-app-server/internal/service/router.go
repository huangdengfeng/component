package service

import (
	"github.com/gin-gonic/gin"
)

// RegisterRoutes 注册所有路由
func RegisterRoutes(r *gin.Engine, studentInfoExe *StudentInfoExe) {
	// 学生信息相关路由
	studentGroup := r.Group("/student")
	{
		studentGroup.POST("/create", studentInfoExe.Create)
		studentGroup.POST("/update", studentInfoExe.Update)
		studentGroup.POST("/delete", studentInfoExe.Delete)
		studentGroup.POST("/page", studentInfoExe.Page)
	}
}
