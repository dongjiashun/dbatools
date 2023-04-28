//package zabbix.main;
//
//import java.io.IOException;
//import java.util.LinkedList;
//import java.util.List;
//
//import org.junit.Test;
//
//import zabbix.sender.DataObject;
//import zabbix.sender.SenderResult;
//import zabbix.sender.ZabbixSender;
//
//import com.alibaba.fastjson.JSONObject;
//
///**
// * 
// * @author dongjs
// * 功能：java实现zabbix_sender
// * 场景：单服务器下多个数据库端口，实现自动化监控
// * 优势：对比zabbix通过agent 监控，需要在所有服务器下面部署zabbix-agent
// * 改方式只需要在一台服务器上部署，就可以实现监控所有服务器的功能
// * 针对mpm进行进行重新构建
// * 
// */
//public class ZabbixSenderTest {
//
//	static String host = "10.0.11.45";
//	static int port = 10051;
//
//	public static void main(String[] args) {
//
//		try {
//			ZabbixSender zabbixSender = new ZabbixSender(host, port);
//			DataObject dataObject = new DataObject();
//			dataObject.setHost("192.168.1.1");
//			dataObject.setKey("dongjs");
//			dataObject.setValue("333");
//			dataObject.setClock(System.currentTimeMillis()/1000);
//			SenderResult result = zabbixSender.send(dataObject);
//
//			System.out.println("result:" + result);
//			if (result.success()) {
//				System.out.println("send success.");
//			} else {
//				System.err.println("send fail!");
//			}
//		} catch (IOException e) {
//			System.out.println(e);
//			e.printStackTrace();
//		}
//	}
//	
//}
