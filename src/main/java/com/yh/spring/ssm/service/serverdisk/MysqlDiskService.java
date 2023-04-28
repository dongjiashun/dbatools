package com.yh.spring.ssm.service.serverdisk;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yh.spring.ssm.bean.serverdisk.MysqlServerDisk;
import com.yh.spring.ssm.bean.serverdisk.ServerDisk;

@ Service
public interface MysqlDiskService {
	//插入磁盘信息
	int insert_MysqlDiskInfo(List<MysqlServerDisk> date);
	
	List<MysqlServerDisk> select_MysqlDiskInfo(String date) throws Exception;

}
