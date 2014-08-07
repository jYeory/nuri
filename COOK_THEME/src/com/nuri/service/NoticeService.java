package com.nuri.service;

import java.util.List;

import com.nuri.common.utils.Parameters;
import com.nuri.domain.Notice;

public interface NoticeService {
	Notice getNotice(String noticeSeq);
	/**
	 *	공지사항 추가 
	 */
	Notice addNotice(Notice notice);
	int updateNotice(Notice notice);
	int deleteNotice(String noticeSeq);
	List<?> noticeList(Parameters<String, Object> params);
	List<?> noticeList(Parameters<String, Object> params, int pg, int ps);
}
