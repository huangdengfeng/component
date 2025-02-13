package logic

type BillnoService interface {
	GetBillno(bizCode string) (uint64, error)
}
