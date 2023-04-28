package com.yh.spring.ssm.Controller;
import java.security.InvalidAlgorithmParameterException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.yh.spring.ssm.sendMail.ZabbixConfig;
import com.yh.spring.ssm.util.ArgumentsAnalysisUtils;
import com.zabbix4j.ZabbixApi;
import com.zabbix4j.host.HostCreateRequest;
import com.zabbix4j.host.HostCreateResponse;
import com.zabbix4j.host.HostUpdateRequest;
import com.zabbix4j.hostinteface.HostInterfaceObject;

/**
 * mistongops/mst@Qazwsx123
 * @author dongjs
 * @desc 基于zabbix50 的api， 区别于zabbix40
 * @ 自动添加 hostname，且可以更新hostname的模板和基础信息
 */
public class Auto_zabbix50_tools {

	
	private static String name = "";
	private static String pwd = "";
	private static String url = "";
	private static String template;
	private static String hostname;
	private static String groupid;
	private static String mysqlip;

	
	public static void main(String[] args) {
		//第一步判断改hostname是否在zabbix中存在
		//第二步create hostname  关联模板
		//第三步 有必要的情况下 修改hostname
		Properties properties = ZabbixConfig.build("resources/zabbix.properties");
		  ArgumentsAnalysisUtils aas = new ArgumentsAnalysisUtils( "zabbix自动化工具说明书" ) ;
		  aas.add( "功能介绍 " ) ;
		  aas.add( "注意使用改工具需要对zabbix接口有一定了解。拒绝初学者使用" ) ;
		  aas.add( "1.根据传入的删除自动创建模板且，关联相关的模板" ) ;
		  aas.add( "--host 表示zabbix中的hostname" ) ;
		  aas.add( "查询历史告警命令案例 " ) ;
		  aas.add( "java -cp xxx.jar  com.yh.spring.ssm.Controller.Auto_zabbix50_tools --host=s_port_ip" ) ;
		  Map paramsmap = new HashMap( ) ;
		  // 参数设置规则
		  try {
			  paramsmap = aas.parse( args ) ;
		  }
		  catch ( InvalidAlgorithmParameterException e ) {
				e.printStackTrace( ) ;
		  }
		  if ( paramsmap.containsKey( "host" ) ) {
			   hostname=paramsmap.get("host").toString().trim();
		  }
		  if(hostname.contains("s")){
			  //说明本次操作是 从库  操作
			  template = (String)properties.get("mysql.slave.template");
		  }else if(hostname.contains("m")){
			  //说明本次操作是 主库  操作
			  template = (String)properties.get("mysql.master.template");
		  }else{
			  System.out.println("参数不合理请参照说明书案例进行操作");
			  aas.print();
			  System.exit(0);
		  }
		  //分割参数信息，去除ip 端口  主从状态
		      String[] hostinfo = hostname.split("_");
		      mysqlip = hostinfo[2];
			  url = (String) properties.get("zabbix.url");
			  name = (String) properties.get("zabbix.userName");
			  pwd = (String) properties.get("zabbix.passWord");
			  groupid = (String) properties.get("mysql.groupid");
              //template  批量特殊处理
              String[] temid = template.split(",");
              List<Integer> temlist = new ArrayList<Integer>();
              for (String templateid : temid) {
            	  temlist.add(Integer.parseInt(templateid));
				}
			  try {
								ZabbixApi zabbixApi = new ZabbixApi(url);
								zabbixApi.login(name, pwd);
								System.out.println("login success");
				                HostCreateRequest request = new HostCreateRequest();
				                HostCreateRequest.Params params = request.getParams();
				                params.setTemplates(temlist);
//				                params.addTemplateId(10170);
//				                params.setTemplates(temlist);
				                params.addGroupId(Integer.parseInt(groupid));
				                //添加宏
//				                List<Macro> macros = new ArrayList<Macro>();
//				                Macro macro1 = new Macro();
//				                macro1.setMacro("{$MACRO1}");
//				                macro1.setValue("value1");
//				                macros.add(macro1);
//				                params.setMacros(macros);
				                //添加主机接口
				                HostInterfaceObject hostInterface = new HostInterfaceObject();
				                hostInterface.setIp(mysqlip);
				                params.addHostInterfaceObject(hostInterface);
				                //添加主机，和主机名
				                params.setHost(hostname);
				                params.setName(hostname);
				
				                HostCreateResponse response = zabbixApi.host().create(request);
				                int hostId = response.getResult().getHostids().get(0);
				                System.out.println("==========操作完成：操作hostid：========="+hostId);
				            } catch (Exception e) {
				                System.out.println(e);
				            }
	}
//	// update hostname信息
//	public void hostupdate(){
//		HostUpdateRequest updatereq = new HostUpdateRequest();
//		HostUpdateRequest.Params updateparams=    updatereq.getParams();
//		updateparams.
//        String[] temid = template.split(",");
//        for (String templateid : temid) {
//        	updateparams.setHost(hostname);
//        	updateparams.setName(hostname);
//        	updateparams.setTemplates(templates);
//        	params.addTemplateId(Integer.parseInt(templateid));
//		}
//	}
//	
}
