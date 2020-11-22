package main

import (
	"database/sql"

	_ "github.com/go-sql-driver/mysql"
)

var (
	dbConn *sql.DB
	err    error
)

// 初始化dbConn
func init() {
	dbConn, err = sql.Open("mysql", "root:hjm@tcp(localhost:3306)/file_sync?charset=utf8")
	if err != nil {
		panic(err.Error())
	}
}
