package org.myapp.common.redis;

public class RedisSessionInfo {
	Long accountid;
	String username;
	String email;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getAccountid() {
		return accountid;
	}
	public void setAccountid(Long accountid) {
		this.accountid = accountid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
