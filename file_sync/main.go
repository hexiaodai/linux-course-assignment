package main

import (
	"crypto/md5"
	"database/sql"
	"fmt"
	"log"
	"os/exec"
	"strings"

	_ "github.com/go-sql-driver/mysql"
)

func main() {
	var (
		user        = "ljl"
		web_ip      = "ljl2"                // 对方ip地址
		client_path = "/home/ljl/file_sync" // 必须是绝对路径
		server_path = "/home/hjm/file_sync" // 必须是绝对路径
		syncCommand = "./file_sync.sh"
	)

	syncCommand = strings.Join([]string{
		syncCommand,
		user,
		web_ip,
		server_path,
		client_path,
	}, " ")

	// 获取文件时间戳
	ftt, _ := getFileTimestamp(server_path + "/*")

	str := strings.TrimSpace(ftt)            // 去除多余的空格
	str_arr := strings.Split(str, "\n")      // 时间错存到数组中
	str = strings.Replace(str, "\n", "", -1) // 去除\n

	// 生成md5
	data := []byte(str)
	has := md5.Sum(data)
	md5str := fmt.Sprintf("%x", has)
	newfi := &FileInfo{filePath: server_path, timestamp: str_arr[0], md5: md5str}
	// 获取存放在数据库中的md5
	dbfi, _ := getDbFileInfo(server_path)

	fmt.Println("database md5: " + dbfi.md5)
	fmt.Println("new md5: " + md5str)

	if dbfi == nil {
		// 直接同步文件
		syncFile(syncCommand)
		// 保存同步信息
		addDbFileInfo(newfi)
		fmt.Println("同步所有文件 - 完成")
	} else if dbfi.md5 != md5str {
		// md5不一至，同步
		syncFile(syncCommand)
		// 更新md5和时间戳
		editDbFileInfo(newfi)
		fmt.Println("更新修改过的文件 - 完成")
	} else {
		fmt.Println("文件未修改 - 完成")
	}
}

func syncFile(command string) (string, error) {
	cmd := exec.Command("/bin/bash", "-c", command)
	output, err := cmd.Output()
	if err != nil {
		fmt.Printf("Shell (%s): failed with error:%s\n", command, err.Error())
		return "", err
	}
	return string(output), nil
}

func getFileTimestamp(fileName string) (string, error) {
	command := strings.Join([]string{"stat", fileName, "-c", "%Y"}, " ")
	cmd := exec.Command("/bin/bash", "-c", command)
	timestamp, err := cmd.Output()
	if err != nil {
		fmt.Printf("Shell (%s): failed with error:%s\n", command, err.Error())
		return "", err
	}
	return string(timestamp), nil
}

func getDbFileInfo(filePath string) (*FileInfo, error) {
	stmtOut, err := dbConn.Prepare(`
		select file_path, timestamp, md5 from tb_file_sync where file_path = ?
	`)
	if err != nil {
		log.Printf("getDbFileInfo error: %s", err)
		return nil, err
	}
	var (
		_filePath  string
		_timestamp string
		_md5       string
	)
	err = stmtOut.QueryRow(filePath).Scan(&_filePath, &_timestamp, &_md5)
	if err != nil && err != sql.ErrNoRows {
		return nil, err
	}
	res := &FileInfo{
		filePath:  _filePath,
		timestamp: _timestamp,
		md5:       _md5,
	}
	if err == sql.ErrNoRows {
		return nil, nil
	}
	return res, nil
}

func addDbFileInfo(fi *FileInfo) error {
	stmtIns, err := dbConn.Prepare(`
		insert into tb_file_sync
		(file_path, timestamp, md5)
		values (?, ?, ?)
	`)
	if err != nil {
		log.Printf("addDbFileInfo error: %s", err)
		return err
	}
	_, err = stmtIns.Exec(fi.filePath, fi.timestamp, fi.md5)
	if err != nil {
		log.Printf("addDbFileInfo error: %s", err)
		return err
	}
	defer stmtIns.Close()
	return nil
}

func editDbFileInfo(fi *FileInfo) error {
	stmtIns, err := dbConn.Prepare(`
		update tb_file_sync set
		timestamp = ?, md5 = ?
		where file_path = ?
	`)
	if err != nil {
		log.Printf("editDbFileInfo error: %s", err)
		return err
	}
	_, err = stmtIns.Exec(fi.timestamp, fi.md5, fi.filePath)
	if err != nil {
		log.Printf("editDbFileInfo error: %s", err)
		return err
	}
	defer stmtIns.Close()
	return nil
}
