package com.nuri.domain;

import java.util.Date;

import org.apache.ibatis.type.Alias;
import org.directwebremoting.annotations.DataTransferObject;

@Alias("Attachment")
@DataTransferObject
public class Attachment {

	private int attachSeq;
	private String attachDocType;
	private String attachDocKey;
	private String filename;
	private String fakeName;
	private Long fileSize;
	private String contentType;
	private Date createDate;
	
	public int getAttachSeq() {
		return attachSeq;
	}
	public void setAttachSeq(int attachSeq) {
		this.attachSeq = attachSeq;
	}
	public String getAttachDocType() {
		return attachDocType;
	}
	public void setAttachDocType(String attachDocType) {
		this.attachDocType = attachDocType;
	}
	public String getAttachDocKey() {
		return attachDocKey;
	}
	public void setAttachDocKey(String attachDocKey) {
		this.attachDocKey = attachDocKey;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFakeName() {
		return fakeName;
	}
	public void setFakeName(String fakeName) {
		this.fakeName = fakeName;
	}
	public Long getFileSize() {
		return fileSize;
	}
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
