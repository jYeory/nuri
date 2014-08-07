package com.nuri.domain;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;
import org.directwebremoting.annotations.DataTransferObject;

@Alias("Role")
@DataTransferObject
public class Role implements Serializable{
	
	private static final long serialVersionUID = 7051177840534249843L;
	
	private String roleSeq;
	private String roleCode;
	private String roleNameKor;
	private String roleNameEng;
	private String description;
	private Date createDate;
	private Date updateDate;
	private Date disabled;
	
	public String getRoleSeq() {
		return roleSeq;
	}
	public void setRoleSeq(String roleSeq) {
		this.roleSeq = roleSeq;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public String getRoleNameKor() {
		return roleNameKor;
	}
	public void setRoleNameKor(String roleNameKor) {
		this.roleNameKor = roleNameKor;
	}
	public String getRoleNameEng() {
		return roleNameEng;
	}
	public void setRoleNameEng(String roleNameEng) {
		this.roleNameEng = roleNameEng;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Date getDisabled() {
		return disabled;
	}
	public void setDisabled(Date disabled) {
		this.disabled = disabled;
	}
}
