package com.nuri.service;

import java.util.List;

import com.nuri.common.utils.Parameters;
import com.nuri.domain.Attachment;

public interface AttachmentService {
	Attachment getAttachment(Parameters<String, String> params);
	int addAttachment(Attachment attach);
	int deleteAttachment(String attachSeq);
	List<?> attachList(Parameters<String, String> params);
	List<?> attachList(Parameters<String, String> params, int pg, int ps);
}
