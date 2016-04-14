package com.cmsz.ck.model;

/**
 * @author oysj
 *
 */
public class UserModel {
	private long userId;
	private String userName;
	private String topic;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}
	
}
