package com.nuri.domain;

import java.util.Date;

import org.apache.ibatis.type.Alias;

@Alias("MenuRole")
public class MenuRole {
	
	private int seq;
	private String menuId;
	private String roleSeq;
	private String createBy;
	private Date createDate;
	
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getRoleSeq() {
		return roleSeq;
	}
	public void setRoleSeq(String roleSeq) {
		this.roleSeq = roleSeq;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
