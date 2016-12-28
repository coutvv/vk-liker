package ru.coutvv.vkliker.data.entity;

import com.google.gson.annotations.SerializedName;

public class Group {

	@SerializedName("id")
	private Long id;
	@SerializedName("name")
	private String name;
	@SerializedName("screen_name")
	private String screenName;
	@SerializedName("is_closed")
	private Integer isClosed;
	@SerializedName("type")
	private String type;
	@SerializedName("is_admin")
	private Integer isAdmin;
	@SerializedName("is_member")
	private Integer isMember;
	@SerializedName("photo_50")
	private String photo50;
	@SerializedName("photo_100")
	private String photo100;
	@SerializedName("photo_200")
	private String photo200;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public Integer getIsClosed() {
		return isClosed;
	}

	public void setIsClosed(Integer isClosed) {
		this.isClosed = isClosed;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Integer getIsMember() {
		return isMember;
	}

	public void setIsMember(Integer isMember) {
		this.isMember = isMember;
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

	public String getPhoto200() {
		return photo200;
	}

	public void setPhoto200(String photo200) {
		this.photo200 = photo200;
	}

	public String toString() {
		return name + " " + id + "\n";
	}
}
