package errs

var Success = New(0, "success")
var Unknown = New(1000, "system error [%s]")
var BasArgs = New(1001, "bad args [%s]")
var RpcError = New(1002, "call remote error [%s]")
var SqlError = New(1003, "sql error [%s]")
var SqlAffectError = New(1004, "affect row expect [%d] actual [%d]")
var BizCodeNotFound = New(2000, "biz code [%s] not found")
var BizCodeCacheNotReady = New(2001, "biz code [%s] cache not ready")
