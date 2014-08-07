package com.nuri.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nuri.common.service.AbstractGenericManager;
import com.nuri.common.utils.PagedList;
import com.nuri.common.utils.Parameters;
import com.nuri.dao.NoticeDao;
import com.nuri.domain.Notice;
import com.nuri.domain.User;
import com.nuri.service.NoticeService;

/**
 * 
 * =============================================================================
 *            프로젝트명 :   openERP
 *            화  일  명 :   RoleServiceImpl.java
 *            기      능 :   RoleService 구현체
 *            인      수 :   
 *            특이  사항 :
 *-----------------------------------------------------------------------------
 *                              변경 사항				                     
 *-----------------------------------------------------------------------------
 *    변경일자       	변경자(작성자)                 		변경 내역                 
 *   ----------     	--------------------------       -------------------------
 *   2013. 7. 24.      	jYeory<jyeory@gmail.com>         	최 초 작 성                      
 *==============================================================================
 * 
 * @author jYeory
 *
 */
@RemoteProxy(name="NoticeDwr")
@Service("noticeService")
public class NoticeServiceImpl extends AbstractGenericManager implements NoticeService{
	
	Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	private NoticeDao dao;
	
	@SuppressWarnings("unchecked")
	@Override
	public PagedList<?> noticeList(Parameters<String, Object> params, int pg, int ps) {
		return (PagedList<Notice>) list("com.nuri.dao.NoticeDao", "noticeList", params, pg, ps);
	}

	@Override
	public List<?> noticeList(Parameters<String, Object> params) {
		return dao.noticeList(params);
	}
	
	@Override
	@RemoteMethod
	public Notice getNotice(String noticeSeq){
		return dao.getNotice(noticeSeq);
	}

	@Override
	@RemoteMethod
	@Transactional
	public Notice addNotice(Notice notice) {
		notice.setCreateBy( ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserSeq() );
		dao.addNotice(notice);
		return notice;
	}

	@Override
	@RemoteMethod
	@Transactional
	public int updateNotice(Notice notice) {
		notice.setUpdateBy( ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserSeq() );
		return dao.updateNotice(notice);
	}

	@Override
	@RemoteMethod
	@Transactional
	public int deleteNotice(String noticeSeq) {
		return dao.deleteNotice(noticeSeq);
	}
}
