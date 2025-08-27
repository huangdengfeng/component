package request

// PageQry 分页参数
type PageQry struct {
	Page     int `json:"page"`
	PageSize int `json:"pageSize"`
}
