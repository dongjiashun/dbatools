package com.yh.spring.ssm.service.serverdisk;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yh.spring.ssm.bean.serverdisk.ServerDisk;

@ Service
public interface ServerDiskService {

	//插入磁盘信息
	int insert_ServerDiskInfo(List<ServerDisk> date);
	
	List<ServerDisk> select_ServerDiskInfo(String date) throws Exception;

}
