package com.hhf.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Administrator
 *
 */

public class User implements Serializable{
	

	private long id;
	private String userName;
	private String passWord;
	private String name;
	private String address;
	private int yes;//权限、默认0。
	private Timestamp createDate;
	private String picPath;//头像路径

	public User(){}

	public User(long id,String name){
		this.id=id;
		this.name=name;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", passWord=" + passWord + ", name=" + name + ", address="
				+ address + ", yes=" + yes + ", createDate=" + createDate + ", picPath=" + picPath + "]";
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPassWord() {
		return passWord;
	}


	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public int getYes() {
		return yes;
	}


	public void setYes(int yes) {
		this.yes = yes;
	}


	public Timestamp getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}


	public String getPicPath() {
		return picPath;
	}


	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	

}
