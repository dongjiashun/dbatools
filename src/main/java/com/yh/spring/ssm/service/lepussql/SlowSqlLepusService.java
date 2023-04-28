package com.yh.spring.ssm.service.lepussql;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yh.spring.ssm.bean.SlowSqlLepus;
import com.yh.spring.ssm.bean.serverdisk.MysqlServerDisk;
import com.yh.spring.ssm.bean.serverdisk.ServerDisk;

@ Service
public interface SlowSqlLepusService {
	//插入磁盘信息
	
	List<SlowSqlLepus> select_SlowSqlLepus(String date) throws Exception;

}
