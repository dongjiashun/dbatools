package com.yh.spring.ssm.Controller;

import java.security.InvalidAlgorithmParameterException;
import java.util.HashMap;
import java.util.Map;

import com.yh.spring.ssm.api.AliyunApi;
import com.yh.spring.ssm.bean.Rds_slowsql;
import com.yh.spring.ssm.util.ArgumentsAnalysisUtils;

/**
 * 
 * @author dongjs
 *	1,功能说明：该功能主要是检测线上的RDS慢查询日志。每日推送消息--》发送给DBA处理慢sql日志
 */
public class SlowSqlSendAction {

		public static void main(String[] args) 
		{
			  ArgumentsAnalysisUtils aas = new ArgumentsAnalysisUtils( "RDS慢查询项目说明书" ) ;
			  aas.add( "格式规范 " ) ;
			  aas.add( "--file 自定义过滤的sql文件路径? " ) ;
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

			//1.获取慢查询sql，且插入到mysql
			new AliyunApi().slowQueryInfo();
		}
}
