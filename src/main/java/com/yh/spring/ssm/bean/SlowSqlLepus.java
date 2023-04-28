package com.yh.spring.ssm.bean;

public class SlowSqlLepus {
	/**
	 * 数据库 基于 lepus 慢查统计
	 */
	private String dbname;
	private String username;
	private String typesum;
	private String sqlsum;
	private String locksum;
	public String getDbname() {
		return dbname;
	}
	public void setDbname(String dbname) {
		this.dbname = dbname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTypesum() {
		return typesum;
	}
	public void setTypesum(String typesum) {
		this.typesum = typesum;
	}
	public String getSqlsum() {
		return sqlsum;
	}
	public void setSqlsum(String sqlsum) {
		this.sqlsum = sqlsum;
	}
	public String getLocksum() {
		return locksum;
	}
	public void setLocksum(String locksum) {
		this.locksum = locksum;
	}
	
	

}
