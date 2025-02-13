package service

import (
	"billno-server/entity/errs"
	"billno-server/entity/pb"
	"billno-server/logic"
	"billno-server/repo/dao"
	"context"
)

type billnoServerImpl struct {
	pb.UnimplementedBillnoServer
	billnoService logic.BillnoService
}

func NewBillnoServer(billnoDao dao.BillnoDao) pb.BillnoServer {
	return &billnoServerImpl{
		billnoService: logic.NewBillnoService(billnoDao),
	}
}

func (b *billnoServerImpl) GetBillno(ctx context.Context, req *pb.GetBillnoReq) (*pb.GetBillnoResp, error) {
	if len(req.BizCode) == 0 {
		return &pb.GetBillnoResp{ErrCode: errs.BasArgs.Code, ErrMsg: errs.BasArgs.Msgf("BizCode is empty")}, nil
	}
	billno, err := b.billnoService.GetBillno(req.BizCode)
	if err != nil {
		e := errs.Parse(err)
		return &pb.GetBillnoResp{ErrCode: e.Code, ErrMsg: e.Msg}, nil
	}
	return &pb.GetBillnoResp{Billno: billno}, nil
}
