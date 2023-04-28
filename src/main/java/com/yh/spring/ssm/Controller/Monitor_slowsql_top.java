package com.yh.spring.ssm.Controller;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yh.spring.ssm.bean.SlowSqlLepus;
import com.yh.spring.ssm.service.lepussql.iservice.SlowSqlLepusServicelmpl;
import com.yh.spring.ssm.service.sendmail.SlowSqlLepusSendMail;
import com.yh.spring.ssm.util.ArgumentsAnalysisUtils;

/**
 * 
 * @author yinghao
 * @功能： 基于lepus 系统做的慢查询统计报告
 * @定时器： 执行天数为7天
 *
 */
public class Monitor_slowsql_top {
	
	public static ClassPathXmlApplicationContext ac;
	public static SlowSqlLepusServicelmpl sds;

	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		 ArgumentsAnalysisUtils aas = new ArgumentsAnalysisUtils( "数据库慢查询项目说明书" ) ;
		  aas.add( "功能W介绍 " ) ;
		  aas.add( "1.查询线上所有数据库慢查询统计" ) ;
		  Map params = new HashMap( ) ;
		  // 参数设置规则
		  try {
				params = aas.parse( args ) ;
		  }
		  catch ( InvalidAlgorithmParameterException e ) {
				e.printStackTrace( ) ;
		  }
			try {
				//加载spring配置文件
				if (ac == null) {
					ac = new ClassPathXmlApplicationContext("ApplicationContext_mybatis_ehcache.xml");
					sds = (SlowSqlLepusServicelmpl) ac.getBean("SlowSqlLepusServicelmpl");
				}

				List<SlowSqlLepus> data = sds.select_SlowSqlLepus(null);
				new SlowSqlLepusSendMail().infoAddHtml(data);
			} catch (Exception e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}  
		  
//
	}

}
