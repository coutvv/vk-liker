package ru.coutvv.vkliker.data.entity;

import com.google.gson.annotations.SerializedName;

public class Profile {
	@SerializedName("id") 
	private Long id;
	@SerializedName("first_name") 
	private String firstName;
	@SerializedName("last_name") 
	private String lastName;
	@SerializedName("sex") 
	private Integer sex;
	@SerializedName("screen_name") 
	private String screenName;
	@SerializedName("photo_50") 
	private String photo50;
	@SerializedName("photo_100") 
	private String photo100;
	@SerializedName("online") 
	private Integer online;
	@SerializedName("online_app") 
	private String onlineApp;
	@SerializedName("online_mobile") 
	private Integer onlineMobile;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	public String getPhoto50() {
		return photo50;
	}
	public void setPhoto50(String photo50) {
		this.photo50 = photo50;
	}
	public String getPhoto100() {
		return photo100;
	}
	public void setPhoto100(String photo100) {
		this.photo100 = photo100;
	}
	public Integer getOnline() {
		return online;
	}
	public void setOnline(Integer online) {
		this.online = online;
	}
	public String getOnlineApp() {
		return onlineApp;
	}
	public void setOnlineApp(String onlineApp) {
		this.onlineApp = onlineApp;
	}
	public Integer getOnlineMobile() {
		return onlineMobile;
	}
	public void setOnlineMobile(Integer onlineMobile) {
		this.onlineMobile = onlineMobile;
	}
	
	@Override
	public String toString() {
		return firstName + " " + lastName;
	}
	
}
