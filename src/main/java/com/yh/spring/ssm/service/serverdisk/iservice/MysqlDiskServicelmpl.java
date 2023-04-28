package com.yh.spring.ssm.service.serverdisk.iservice;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.yh.spring.ssm.bean.RdsSlowlogItem;
import com.yh.spring.ssm.bean.SlowSqlForMail;
import com.yh.spring.ssm.bean.otter.Otter_DATA_MEDIA;
import com.yh.spring.ssm.bean.otter.Otter_DATA_MEDIA_kid;
import com.yh.spring.ssm.bean.serverdisk.MysqlServerDisk;
import com.yh.spring.ssm.bean.serverdisk.ServerDisk;
import com.yh.spring.ssm.public_dao.DaoSupport;
import com.yh.spring.ssm.service.otterautoconfig.OtterAutoService;
import com.yh.spring.ssm.service.serverdisk.MysqlDiskService;
import com.yh.spring.ssm.service.serverdisk.ServerDiskService;
@ SuppressWarnings( "unchecked" )
@Component(value="MysqlDiskServicelmpl")
public class MysqlDiskServicelmpl implements MysqlDiskService {
	@ Resource( name = "daoSupport" )
	 private DaoSupport dao ;

	@Override
	public int insert_MysqlDiskInfo(List<MysqlServerDisk> data) {
		int a = 0;
		try {
			a = (int) dao.save("mysqldisk.insert_MysqlDiskInfo", data);
			if(a>0){
				return a;
			}
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return a;	
	}

	@Override
	public List<MysqlServerDisk> select_MysqlDiskInfo(String nowtime) throws Exception {
		List<MysqlServerDisk> list = (List<MysqlServerDisk>) dao.findForList("mysqldisk.select_MysqlDiskInfo", nowtime);
		if(list != null){
			return list;
		}
		return list;
	}
	
}
