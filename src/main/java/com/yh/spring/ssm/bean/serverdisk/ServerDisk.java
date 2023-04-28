package com.yh.spring.ssm.bean.serverdisk;

public class ServerDisk {
//	private String systemfile;
	private String hostname;//主机名称
	private String size;   //磁盘总大小
	private String used;   //使用多少
	private String percentage;//占用百分比
	private String disk_path; //磁盘路径
	private String avail; //剩下多少磁盘
	private String cha;//今天和明天的插值
	
	
	
	

public String getCha() {
		return cha;
	}
	public void setCha(String cha) {
		this.cha = cha;
	}
public String getAvail() {
		return avail;
	}
	public void setAvail(String avail) {
		this.avail = avail;
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
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getUsed() {
		return used;
	}
	public void setUsed(String used) {
		this.used = used;
	}
	public String getPercentage() {
		return percentage;
	}
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	public String getDisk_path() {
		return disk_path;
	}
	public void setDisk_path(String disk_path) {
		this.disk_path = disk_path;
	}
	
}
