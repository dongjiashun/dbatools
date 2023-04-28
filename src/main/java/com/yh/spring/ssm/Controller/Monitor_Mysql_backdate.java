package com.yh.spring.ssm.Controller;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yh.spring.ssm.bean.serverdisk.MysqlServerDisk;
import com.yh.spring.ssm.bean.serverdisk.ServerDisk;
import com.yh.spring.ssm.sendMail.ZabbixConfig;
import com.yh.spring.ssm.service.rdsfuntion.iservice.RdsSlowSqlServiceImpl;
import com.yh.spring.ssm.service.rdsfuntion.iservice.RdsWhiteListDbServiceImpl;
import com.yh.spring.ssm.service.sendmail.MysqlDiskSendMailService;
import com.yh.spring.ssm.service.sendmail.ServerDiskSendMailService;
import com.yh.spring.ssm.service.serverdisk.iservice.MysqlDiskServicelmpl;
import com.yh.spring.ssm.service.serverdisk.iservice.ServerDiskServicelmpl;
import com.yh.spring.ssm.util.ArgumentsAnalysisUtils;

public class Monitor_Mysql_backdate {
	
	public static ClassPathXmlApplicationContext ac;
	public static MysqlDiskServicelmpl sds;
	/**
	 * 功能：监控所有服务器磁盘空间和数据库实例的大小
	 * 关系：服务器>数据库实例>数据库每个表的大小
	 * 目的：实时监控线上数据库磁盘运行状态和健康值，预估风险。补全现阶段磁盘监控不足的问题
	 * 
	 */
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		 ArgumentsAnalysisUtils aas = new ArgumentsAnalysisUtils( "每日巡检数据库备份日报" ) ;
		  aas.add( "功能W介绍 " ) ;
		  aas.add( "1.监控所有数据库备份文件状态" ) ;
		  aas.add( "前提条件" ) ;
		  aas.add( "ansible部署环境，此项目基于ansible收集数据。" ) ;
		  Map params = new HashMap( ) ;
		  // 参数设置规则
		  try {
				params = aas.parse( args ) ;
		  }
		  catch ( InvalidAlgorithmParameterException e ) {
				e.printStackTrace( ) ;
		  }
		  String serverlog ; 
		  String database_status;
		  @ SuppressWarnings( "rawtypes" )
		  //价值配置文件
		  Properties properties = ZabbixConfig.build("resources/databasebackstatus.properties");
		  serverlog = (String) properties.get("db.backdate.file");
		 database_status= (String) properties.get("keyword");
		  Map serverdiskmap = new HashMap();
		  Map serverinfoMap = null;
		  MysqlServerDisk mysqlserverdisk = null;
		  String hostname = null ;
		  List<MysqlServerDisk> serverinfoList = new ArrayList<MysqlServerDisk>();;
			try {
				//加载spring配置文件
				if (ac == null) {
					ac = new ClassPathXmlApplicationContext("ApplicationContext_mybatis_ehcache.xml");
					sds = (MysqlDiskServicelmpl) ac.getBean("MysqlDiskServicelmpl");
				}
				List<String> filecontext = FileUtils.readLines(new File("resources/"+serverlog), "UTF-8");
				for (String string : filecontext) {
					if(string.contains("rc=")){
						serverinfoMap = new HashMap();						
						//获取hostname db1
						String [] arr = string.split("\\|");
						System.out.println("获取hostname：："+arr[0]);		
						//设置hostnamedb1
						hostname = arr[0];
						serverinfoMap.put("arr[0]",mysqlserverdisk );
					}else if(string.contains("Filesystem")){
						continue;
					}
					if(string.contains(database_status)){
						mysqlserverdisk=new MysqlServerDisk();
						//获取到了数据库
						System.out.println("有用数据"+string);
						//根据空格分隔
						String [] arr = string.split("\\s+");
						mysqlserverdisk.setHostname(hostname);
						mysqlserverdisk.setUsed(arr[0]);
						//设置单位，GB，TB 单位转换
						if(arr[0].contains("T")){
							//说明单位为T
							String used = arr[0].replace("T", "");
							float usedlong = Float.parseFloat(used);							
							mysqlserverdisk.setUsed(usedlong*1024+"G");
						}else if(arr[0].contains("G")){
							mysqlserverdisk.setUsed(arr[0]);
						}
						mysqlserverdisk.setDisk_path(arr[1]);
						//记录服务器中目录磁盘信息
						serverinfoList.add(mysqlserverdisk);
						//插入数据库
					}
				}
				//插入数据库serverdisk表
				int insertnum = sds.insert_MysqlDiskInfo(serverinfoList);
				if(insertnum>0){
					System.out.println("mysqldisk--数据插入成功");
				}
				//发送邮件给
				List<MysqlServerDisk> data = sds.select_MysqlDiskInfo(null);
				new MysqlDiskSendMailService().infoAddHtml(data);
			} catch (Exception e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}  
		  
//
	}

}
