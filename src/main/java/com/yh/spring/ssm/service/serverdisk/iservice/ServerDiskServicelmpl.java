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
import com.yh.spring.ssm.bean.serverdisk.ServerDisk;
import com.yh.spring.ssm.public_dao.DaoSupport;
import com.yh.spring.ssm.service.otterautoconfig.OtterAutoService;
import com.yh.spring.ssm.service.serverdisk.ServerDiskService;
@ SuppressWarnings( "unchecked" )
@Component(value="ServerDiskServicelmpl")
public class ServerDiskServicelmpl implements ServerDiskService {
	@ Resource( name = "daoSupport" )
	 private DaoSupport dao ;

	@Override
	public int insert_ServerDiskInfo(List<ServerDisk> data) {
		int a = 0;
		try {
			a = (int) dao.save("serverdisk.insert_ServerDiskInfo", data);
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
	public List<ServerDisk> select_ServerDiskInfo(String nowtime) throws Exception {
		List<ServerDisk> list = (List<ServerDisk>) dao.findForList("serverdisk.select_ServerDiskInfo", nowtime);
		if(list != null){
			return list;
		}
		return list;
	}
	
}
