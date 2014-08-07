package com.nuri.domain;

import java.util.Date;
import java.util.List;

public class MasterGroupCode {
	
	private String grpSeq;
	private String grpNameKor;
	private String grpNameEng;
	private String grpDescription;
	private String createBy;
	private Date createDate;
	private String updateBy;
	private Date updateDate;
	private Date disabled;
	// 임시
	private List<MasterCode> masterCodes;
	
	public String getGrpSeq() {
		return grpSeq;
	}
	public void setGrpSeq(String grpSeq) {
		this.grpSeq = grpSeq;
	}
	public String getGrpNameKor() {
		return grpNameKor;
	}
	public void setGrpNameKor(String grpNameKor) {
		this.grpNameKor = grpNameKor;
	}
	public String getGrpNameEng() {
		return grpNameEng;
	}
	public void setGrpNameEng(String grpNameEng) {
		this.grpNameEng = grpNameEng;
	}
	public String getGrpDescription() {
		return grpDescription;
	}
	public void setGrpDescription(String grpDescription) {
		this.grpDescription = grpDescription;
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

	public List<MasterCode> getMasterCodes() {
		return masterCodes;
	}
	public void setMasterCodes(List<MasterCode> masterCodes) {
		this.masterCodes = masterCodes;
	}
}
