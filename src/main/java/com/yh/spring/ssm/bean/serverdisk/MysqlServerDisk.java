package com.yh.spring.ssm.bean.serverdisk;


/**
 * 
 * @author dongjs
 * 说明：数据库实例对象类
 */
public class MysqlServerDisk {
	private String hostname;//主机名称
	private String used;   //使用多少
	private String disk_path; //磁盘路径
	private String cha;//今天和明天的插值
	

public String getCha() {
		return cha;
	}
	public void setCha(String cha) {
		this.cha = cha;
	}
public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	//	public String getSystemfile() {
//		return systemfile;
//	}
//	public void setSystemfile(String systemfile) {
//		this.systemfile = systemfile;
//	}

	public String getUsed() {
		return used;
	}
	public void setUsed(String used) {
		this.used = used;
	}

	public String getDisk_path() {
		return disk_path;
	}
	public void setDisk_path(String disk_path) {
		this.disk_path = disk_path;
	}
	
}
