package com.nuri.domain;

import java.util.Date;

import org.apache.ibatis.type.Alias;

@Alias("Company")
public class Company {
	
	private int companySeq;
	private String companyName;
	private String companyCeoName;
	private String companyBizNo;
	private String companyZipcode;
	private String companyAddress;
	private String companyTel;
	private Date createDate;
	private Date updateDate;
	private Date disabled;
	
	public int getCompanySeq() {
		return companySeq;
	}
	public void setCompanySeq(int companySeq) {
		this.companySeq = companySeq;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyCeoName() {
		return companyCeoName;
	}
	public void setCompanyCeoName(String companyCeoName) {
		this.companyCeoName = companyCeoName;
	}
	public String getCompanyBizNo() {
		return companyBizNo;
	}
	public void setCompanyBizNo(String companyBizNo) {
		this.companyBizNo = companyBizNo;
	}
	public String getCompanyZipcode() {
		return companyZipcode;
	}
	public void setCompanyZipcode(String companyZipcode) {
		this.companyZipcode = companyZipcode;
	}
	public String getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	public String getCompanyTel() {
		return companyTel;
	}
	public void setCompanyTel(String companyTel) {
		this.companyTel = companyTel;
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
