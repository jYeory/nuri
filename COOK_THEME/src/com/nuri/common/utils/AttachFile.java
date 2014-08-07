package com.nuri.common.utils;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class AttachFile {
	public String attachId;
	public String attachDocType;
	public String attachDocId;
	public String copyAttachDocType;
	public String copyAttachDocId;

	public String attachFiles;
	public String attachKey;
	public String attachDeleteFile;
	public String alreadyAttach;

	public String attachName;
	public long attachSize;
	public byte[] file;
	public String ico;
	public String inst_usr;
	public List<AttachFile> flist;
	public String viewType;


	public String getCopyAttachDocType() {
		return copyAttachDocType;
	}
	public void setCopyAttachDocType(String copyAttachDocType) {
		this.copyAttachDocType = copyAttachDocType;
	}
	public String getCopyAttachDocId() {
		return copyAttachDocId;
	}
	public void setCopyAttachDocId(String copyAttachDocId) {
		this.copyAttachDocId = copyAttachDocId;
	}
	public String getAttachId() {
		return attachId;
	}
	public void setAttachId(String attachId) {
		this.attachId = attachId;
	}
	public String getAttachDocType() {
		return attachDocType;
	}
	public void setAttachDocType(String attachDocType) {
		this.attachDocType = attachDocType;
	}
	public String getAttachDocId() {
		return attachDocId;
	}
	public void setAttachDocId(String attachDocId) {
		this.attachDocId = attachDocId;
	}
	public String getAttachFiles() {
		return attachFiles;
	}
	public void setAttachFiles(String attachFiles) {
		this.attachFiles = attachFiles;
	}
	public String getAttachKey() {
		return attachKey;
	}
	public void setAttachKey(String attachKey) {
		this.attachKey = attachKey;
	}
	public String getAttachDeleteFile() {
		return attachDeleteFile;
	}
	public void setAttachDeleteFile(String attachDeleteFile) {
		this.attachDeleteFile = attachDeleteFile;
	}
	public String getAlreadyAttach() {
		return alreadyAttach;
	}
	public void setAlreadyAttach(String alreadyAttach) {
		this.alreadyAttach = alreadyAttach;
	}
	public String getAttachName() {
		return attachName;
	}
	public void setAttachName(String attachName) {
		this.attachName = attachName;
	}
	public long getAttachSize() {
		return attachSize;
	}
	public void setAttachSize(long attachSize) {
		this.attachSize = attachSize;
	}
	public byte[] getFile() {
		return file;
	}
	public void setFile(byte[] file) {
		this.file = file;
	}
	public String getIco() {
		return ico;
	}
	public void setIco(String ico) {
		this.ico = ico;
	}
	public String getInst_usr() {
		return inst_usr;
	}
	public void setInst_usr(String inst_usr) {
		this.inst_usr = inst_usr;
	}
	public List<AttachFile> getFlist() {
		return flist;
	}
	public void setFlist(List<AttachFile> flist) {
		this.flist = flist;
	}
	public String getViewType() {
		return viewType;
	}
	public void setViewType(String viewType) {
		this.viewType = viewType;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);  
	}

}
