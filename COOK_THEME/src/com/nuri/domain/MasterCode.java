package com.nuri.domain;

import java.util.Date;

public class MasterCode {
	// code_seq, code_seq, code_name_kor, code_name_eng, code_description, create_by, create_date, update_by, update_date, disabled
	
	private String codeSeq;
	private String grpSeq;
	private String codeNameKor;
	private String codeNameEng;
	private String codeDescription;
	private String createBy;
	private Date createDate;
	private String updateBy;
	private Date updateDate;
	private Date disabled;
	
	public String getCodeSeq() {
		return codeSeq;
	}
	public void setCodeSeq(String codeSeq) {
		this.codeSeq = codeSeq;
	}
	public String getGrpSeq() {
		return grpSeq;
	}
	public void setGrpSeq(String grpSeq) {
		this.grpSeq = grpSeq;
	}
	public String getCodeNameKor() {
		return codeNameKor;
	}
	public void setCodeNameKor(String codeNameKor) {
		this.codeNameKor = codeNameKor;
	}
	public String getCodeNameEng() {
		return codeNameEng;
	}
	public void setCodeNameEng(String codeNameEng) {
		this.codeNameEng = codeNameEng;
	}
	public String getCodeDescription() {
		return codeDescription;
	}
	public void setCodeDescription(String codeDescription) {
		this.codeDescription = codeDescription;
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
}
