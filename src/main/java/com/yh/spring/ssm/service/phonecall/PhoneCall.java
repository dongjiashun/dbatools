package com.yh.spring.ssm.service.phonecall;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.yh.spring.ssm.sendMail.PhoneConfig;
import com.yh.spring.ssm.util.HttpUtils;

public class PhoneCall {

	public static String calllUrl = "";

	public void phonecall(String html, String send) {
		List<String> filecontext;
		boolean phonestatus = true;
		try {
			filecontext = FileUtils.readLines(new File("resources/filterphone.txt"), "UTF-8");
			for (String string : filecontext) {
			if(html.contains(string)){
				System.out.println("电话告警已过滤:"+string);
				phonestatus = false;
			}
			}
			if(phonestatus){
				System.out.println("电话告警-------"+html);
				html = html.replaceAll("告警主机", "<br/>告警主机");
		    	html = html.replaceAll("主机IP", "<br/>主机IP");
		    	html = html.replaceAll("告警时间", "<br/>告警时间");
		    	html = html.replaceAll("告警等级", "<br/>告警等级");
		    	html = html.replaceAll("告警信息：", "<br/>告警信息：");
		    	html = html.replaceAll("问题详情", "<br/>问题详情");
		    	html = html.replaceAll("事件ID", "<br/>事件ID");
		    	html = html.replaceAll("产生时间", "<br/>产生时间");
		    	html = html.replaceAll("恢复时间", "<br/>恢复时间");
		    	String[] test= html.split("<br/>");
		    	String txt ="";
		    	if(!test[1].contains("告警主机")){
		    		txt = "未知数据库";
		    	}else{
		    		txt = test[1].replaceAll("告警主机：","");
		    	}
		    	
				// callurl=http://10.77.1.76:10010/phone/call
				// phones=18757589409
				// ##---server 服务器名称 TTS_171187047
				// ttscode=
				if (StringUtils.isNotBlank(send) && send.equals("1")) {
					String callurl = "";
					String phonelist = "";
					String ttscode = "";
					Properties properties = PhoneConfig
							.build("resources/phone.properties");
					callurl = (String) properties.get("callurl");
					phonelist = (String) properties.get("phonelist");
					ttscode = (String) properties.get("ttscode");
					// TODO 自动生成的方法存根
					// 判断电话号码是否有逗号
					// String url1 = "http://10.77.1.76:10010/phone/call?"
					String[] plists = phonelist.split(",");
					if (plists.length > 0 && plists.length == 1) {
						// 只有一个电话
						new PhoneCall().usephone(plists[0], txt, ttscode, callurl);
					} else {
						// 多个电话
						for (String phone : plists) {
							new PhoneCall().usephone(phone, txt, ttscode, callurl);
						}
					}
				}
			}
			
		
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	public void usephone(String phone, String txt, String ttscode,
			String callurl) {
		Map param = new HashMap();
		param.put("phone", phone);
		param.put("server", txt);
		param.put("TtsCode", ttscode);
		HttpUtils.sendGet(callurl, param);
	}

	public static void main(String[] args) {
		
		String text = "NOTE: Escalation cancelled: trigger Free disk space is less than 10% on volume /disabled."+
"金服重要异常告警信息(请关注)                                "
+"告警主机：db-pay1-mysql-1                                    "
+"主机IP：  10.33.102.127                                     "
+"告警时间：2019.08.15  21:46:05                              "
+"告警等级：Disaster                                          "
+"告警信息：Free disk space is less than 10% on volume /      "
+"问题详情：Free disk space on / (percentage):10 %            "
+"事件ID：  35174941"                                        ;
		new PhoneCall().phonecall(text,"1");

	}
}
