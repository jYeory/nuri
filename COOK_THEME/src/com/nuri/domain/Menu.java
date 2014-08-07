package com.nuri.domain;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;
import org.directwebremoting.annotations.DataTransferObject;

@Alias("Menu")
@DataTransferObject
public class Menu implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String menuSeq;
	private String menuId;
	private String menuFunc;
	private String prtMenuId;
	private String menuName;
	private String menuCls;
	private String menuUrl;
	private String description;
	private String roles;
	private int ords;
	private String createBy;
	private Date createDate;
	private String updateBy;
	private Date updateDate;
	private Date disabled;
	
	private String prtMenuName;
	
	public String getMenuSeq() {
		return menuSeq;
	}
	public void setMenuSeq(String menuSeq) {
		this.menuSeq = menuSeq;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getMenuFunc() {
		return menuFunc;
	}
	public void setMenuFunc(String menuFunc) {
		this.menuFunc = menuFunc;
	}
	public String getPrtMenuId() {
		return prtMenuId;
	}
	public void setPrtMenuId(String prtMenuId) {
		this.prtMenuId = prtMenuId;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getMenuCls() {
		return menuCls;
	}
	public void setMenuCls(String menuCls) {
		this.menuCls = menuCls;
	}
	public String getMenuUrl() {
		return menuUrl;
	}
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	public int getOrds() {
		return ords;
	}
	public void setOrds(int ords) {
		this.ords = ords;
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
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
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
	
	public String getPrtMenuName() {
		return prtMenuName;
	}
	public void setPrtMenuName(String prtMenuName) {
		this.prtMenuName = prtMenuName;
	}
	
}
