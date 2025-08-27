package errs

var Success = New(0, "success")
var Unknown = New(1000, "system errs [%s]")
var BadArgs = New(1001, "bad args [%s]")
var SqlError = New(1002, "sql errs")
var RecordNotFound = New(1003, "record not found")
var RowsAffectedNotMatch = New(1004, "rows affected not match")
