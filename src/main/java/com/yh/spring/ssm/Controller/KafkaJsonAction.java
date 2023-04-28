package com.yh.spring.ssm.Controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class KafkaJsonAction {

	String filepath = "kafka.txt";
	
	
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	private void kafkajson() {
		List<String> filecontext;
		try {
			filecontext = FileUtils.readLines(new File("resources/"+filepath), "UTF-8");
			for (String string : filecontext) {
				String [] arr = string.split(",");
				String topic = arr[0].split(":")[1];
				String PartitionCount = arr[1].split(":")[1];
				String ReplicationFactor = arr[2].split(":")[1];
				//-----json templant
			  
			    Map data2 = new HashMap<String, Object>();
			    List partitionslist = new ArrayList<Map>();
			    List replicaslist = new ArrayList();
			    replicaslist.add(0);
			    replicaslist.add(1);
			    replicaslist.add(2);
	

			    for (int i = 0; i < Integer.valueOf(PartitionCount).intValue(); i++) {
			    	Map data3 = new HashMap<String, Object>();
			    	data3.put("topic", topic);
			    	data3.put("partition", i);
			    	data3.put("replicas", replicaslist);
			    	partitionslist.add(data3);
				}
			    Map data1 = new HashMap<String, Object>();			  
			    data1.put("version", 1);
			    data1.put("partitions", partitionslist);
			    List resultdata  = new ArrayList<Map>();
			    resultdata.add(data1);
			    ;
			    JSONArray jsonArray=new JSONArray(resultdata);
			    String endstring =  StringUtils.removeEnd(jsonArray.toString(), "]");
			   String resultstring =  StringUtils.removeStart(endstring, "[");
			    FileUtils.write(new File("resources/kafkajson/"+topic+".json"), resultstring, "utf8", false);
				System.out.println(resultstring);
			}
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			
			
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new KafkaJsonAction().kafkajson();
//		System.out.println(StringUtils.removeEnd("[{partiti]on=0,]", "]")); ;
//		System.out.println(StringUtils.removeStart("[{partiti]on=0,]", "["));
	}
}
