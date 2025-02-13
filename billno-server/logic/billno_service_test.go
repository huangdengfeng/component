package logic

import (
	"billno-server/repo/dao"
	"github.com/golang/mock/gomock"
	"github.com/stretchr/testify/assert"
	"testing"
	"time"
)

func Test_GetBillno(t *testing.T) {
	ctrl := gomock.NewController(t)
	defer ctrl.Finish()
	mockBillnoDao := dao.NewMockBillnoDao(ctrl)
	mockBillnoDao.EXPECT().FindAll(gomock.Any()).Return([]*dao.BillnoPO{
		{
			BizCode:    "test",
			BizDesc:    "test",
			Current:    10,
			Step:       1,
			UpdateTime: time.Time{},
		},
	}, nil)

	service := NewBillnoService(mockBillnoDao)
	billno, err := service.GetBillno("test")
	assert.NoError(t, err)
	assert.Equal(t, uint64(10), billno)
}
