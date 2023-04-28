package com.yh.spring.ssm.Controller;

import java.security.InvalidAlgorithmParameterException;
import java.util.HashMap;
import java.util.Map;

import com.yh.spring.ssm.bean.Rds_slowsql;
import com.yh.spring.ssm.service.sendmail.RdsSendMailService;
import com.yh.spring.ssm.util.ArgumentsAnalysisUtils;

public class SendMailAction {

	//发邮件
	public static void main(String[] args) {
		  ArgumentsAnalysisUtils aas = new ArgumentsAnalysisUtils( "监控所有环境数据库表大小项目说明书" ) ;
		  aas.add( "格式规范 " ) ;
		  aas.add( "每天定时收集数据库大表信息 " ) ;
		  //aas.add( "--file 自定义过滤的sql文件路径? " ) ;
		  @ SuppressWarnings( "rawtypes" )
		  Map params = new HashMap( ) ;
		  // 参数设置规则
		  try {
				params = aas.parse( args ) ;
		  }
		  catch ( InvalidAlgorithmParameterException e ) {
				e.printStackTrace( ) ;
		  }
		  if ( params.containsKey( "file" ) ) {
			  Rds_slowsql.sessionPath=(String) params.get("file");
		  }
		  new RdsSendMailService().sendSqlMail();
	}
}
