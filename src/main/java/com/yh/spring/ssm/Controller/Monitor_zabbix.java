package com.yh.spring.ssm.Controller;

import java.security.InvalidAlgorithmParameterException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Session;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.gson.Gson;
import com.yh.spring.ssm.bean.AlterZabbixBean;
import com.yh.spring.ssm.bean.Rds_slowsql;
import com.yh.spring.ssm.bean.alterLevel;
import com.yh.spring.ssm.sendMail.MailAuthenticator;
import com.yh.spring.ssm.sendMail.MailConfig;
import com.yh.spring.ssm.sendMail.MailUtils;
import com.yh.spring.ssm.sendMail.ZabbixConfig;
import com.yh.spring.ssm.service.phonecall.PhoneCall;
import com.yh.spring.ssm.service.sendmail.RdsSendMailService;
import com.yh.spring.ssm.util.ArgumentsAnalysisUtils;
import com.yh.spring.ssm.util.GsonUtils;
import com.yh.spring.ssm.util.ZabbixUtil;

/**
 * 
 * @author dongjs
 *
 */
public class Monitor_zabbix {

	
	private static String name = "";
	private static String pwd = "";
	private static String url = "";
	
	//定时器定时 1分钟执行一次
	private static long time = 0l;
	
	//告警参数
	private static String groupid = "10";
	private static String timeFrom = null;
	private static String level = "0";
	private static String send = "";
	private static String phone = "0"; //0为关闭，1为开启
	private static long t = 0;
	
	public static void main(String[] args) {
//		int groupid = 10;
//		Long timeFrom = 1556181735l;

		  ArgumentsAnalysisUtils aas = new ArgumentsAnalysisUtils( "zabbix监控项目说明书" ) ;
		  aas.add( "功能介绍 " ) ;
		  aas.add( "1.过滤告警级别" ) ;
		  aas.add( "2.过滤用户组 " ) ;
		  aas.add( "3.查询历史告警信息精确到组和具体时间" ) ;
		  aas.add( "4.定时监听功能可配置" ) ;
		  aas.add( "5.自定义邮件配置，邮件主题可以配置" ) ;
		  aas.add( "6.自定义zabbix监听api设定，不局限于本公司" ) ;
		  aas.add( "格式规范 " ) ;
		  aas.add( "提供根据 告警等级，用户组，时间 来过滤监控 " ) ;
		  aas.add( "i 表示用户组 数据库的用户组是10" ) ;
		  aas.add( "d 表示查询的时间 之后 的告警" ) ;
		  aas.add( "send 可选 true 为定时发邮件" ) ;
		  aas.add( "time 可选 默认是 1分钟一次 单位为分钟" ) ;
		  aas.add( "l 表示告警1：warn 2：Average 3：High 4：Disaster " ) ;
		  aas.add( "phone 表示是否开启电话告警 0是关闭  1是开启" ) ;
		  aas.add( "查询历史告警命令案例 " ) ;
		  aas.add( "java -cp xxx.jar  com.yh.spring.ssm.Controller.Monitor_zabbix --i=10 --d=1556181735 --l=0" ) ;
		  aas.add( "设置定时器功能命令 " ) ;
		  aas.add( "java -cp xxx.jar  com.yh.spring.ssm.Controller.Monitor_zabbix --i=10 --d=1556181735 --l=0 --time=1 --send=true" ) ;

		  //aas.add( "--file 自定义过滤的sql文件路径? " ) ;
		  @ SuppressWarnings( "rawtypes" )
		  //价值配置文件
		  Properties properties = ZabbixConfig.build("resources/zabbix.properties");
		  url = (String) properties.get("zabbix.url");
		  name = (String) properties.get("zabbix.userName");
		  pwd = (String) properties.get("zabbix.passWord");
//	    System.out.println(url+name+pwd);
		  Map params = new HashMap( ) ;
		  // 参数设置规则
		  try {
				params = aas.parse( args ) ;
		  }
		  catch ( InvalidAlgorithmParameterException e ) {
				e.printStackTrace( ) ;
		  }
		  if ( params.containsKey( "i" ) ) {
			   groupid=params.get("i").toString().trim();
		  }
		  if ( params.containsKey( "d" ) ) {
			   timeFrom =  params.get("d").toString().trim();
//			  timeFrom =  new Monitor_zabbix().dateformate(timeFrom1);
		  }
		  if ( params.containsKey( "l" ) ) {
			   level=(String) params.get("l");
		  }
		  // send true 表示定时发邮件
		  if ( params.containsKey( "send" ) ) {
			  send=(String) params.get("send");
		  }
		  if ( params.containsKey( "time" ) ) {
			  t= Long.parseLong((String) params.get("time")) ;
			  time = t*60*1000;
		  }
		  if ( params.containsKey( "phone" ) ) {
			  phone= (String) params.get("phone") ;
			
		  }
		  // run in a second  
//	         long timeInterval = 1000;  
	    	if(StringUtils.isNotBlank(send)&&send.equals("true")&&t>0){
	        Runnable runnable = new Runnable() {  
	            public void run() {  
	                while (true) {  
	                    // ------- code for task to run  
//	                    System.out.println("Hello !!");
	                	System.out.println(new Monitor_zabbix().timeFromUse());
	                	timeFrom = new Monitor_zabbix().timeFromUse();
	                    new Monitor_zabbix().zabbixapi(groupid,timeFrom,level,send);
	                    // ------- ends here  
	                    try {  
	                        Thread.sleep(time);  
	                    } catch (InterruptedException e) {  
	                        e.printStackTrace();  
	                    }  
	                }  
	            }  
	        };  
	        Thread thread = new Thread(runnable);  
	        thread.start();  
	    	}else{
	    		  new Monitor_zabbix().zabbixapi(groupid,timeFrom,level,send);
	    	}
	}

	
	public void zabbixapi(String groupid, String timeFrom ,String level ,String send){
		try {
			Map  alterZabbixMap = new HashMap<String, AlterZabbixBean>();
			Gson gson =new Gson();
//		用户登入
			ZabbixUtil zabbix = new ZabbixUtil(name, pwd, url);
			//判断获取zabbix数据
//		zabbix.getHostGroupList();
//		zabbix.getHostList();
			//配置过滤文件
			String jsonData = zabbix.getAlertListByGroupids(groupid, timeFrom);
			//读取配置文件过滤数据
			ArrayList<AlterZabbixBean> albums2  = 
					JSON.parseObject(jsonData, new TypeReference<ArrayList<AlterZabbixBean>>(){});
//			System.out.println(albums2.size());
			for (AlterZabbixBean alterZabbixBean : albums2) {
				String clock = alterZabbixBean.getClock();
				String Message = alterZabbixBean.getMessage();
				alterZabbixMap.put(clock, Message);
//			System.out.println("---------------------\r\n"+alterZabbixBean.toString());
			}
//			System.out.println(alterZabbixMap.size());
			//根据告警的级别分类
			Collection<String> valueCollection = alterZabbixMap.values();
			List<String> alterZabbixList = new ArrayList<String>(valueCollection);
//		List<String> alterZabbixList = (List) alterZabbixMap.values();
			for(String message : alterZabbixList){
//				System.out.println(message+"\r\n");		
				if(level.equals("0") ){
					//表示显示所有
					System.out.println("All+\r\n"+message);	
					MailUtils.prepareSendMail(message.toString(),send);
//					new PhoneCall().phonecall(message.toString(),phone);

				}
				if(level.equals("1") && message.contains(alterLevel.Warning)){
					System.out.println("Warning+\r\n"+message);		
					MailUtils.prepareSendMail(message.toString(),send);
				}
				if(level.equals("2") && message.contains(alterLevel.Average)){
					System.out.println("Average+\r\n"+message);	
					MailUtils.prepareSendMail(message.toString(),send);
				}
				if(level.equals("3") && message.contains(alterLevel.High)){
					System.out.println("High+\r\n"+message);
					MailUtils.prepareSendMail(message.toString(),send);
				}
				if(level.equals("4") && message.contains(alterLevel.Disaster)){
					System.out.println("Disaster+\r\n"+message);	
					MailUtils.prepareSendMail(message.toString(),send);
					//发送电话告警
					new PhoneCall().phonecall(message.toString(),phone);
				}
			}
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	public String dateformate(String date){
//		String Datee = "2019-04-22 17:42:15";
		
		System.out.println(date.toString().trim());;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			java.util.Date date_util = sdf.parse(date);
			 long ts = date_util.getTime()/1000;
			 
			 return Long.toString(ts);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	public String timeFromUse(){
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  Date now = new Date();
		  System.out.println(now);
		  Date beforeDate = new Date(now .getTime() - time);//30分钟前的时间
		  Long timeend = beforeDate.getTime()/1000;
//		 System.out.println( Long.toString(timeend));
//		  System.out.println(sdf.format(beforeDate));
		return Long.toString(timeend);
	}
	
}
