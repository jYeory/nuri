package com.nuri.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nuri.common.service.AbstractGenericManager;
import com.nuri.common.utils.PagedList;
import com.nuri.common.utils.Parameters;
import com.nuri.dao.AttachmentDao;
import com.nuri.domain.Attachment;
import com.nuri.service.AttachmentService;

/**
 * 
 * =============================================================================
 *            프로젝트명 :   
 *            화  일  명 :   AttachmentServiceImpl.java
 *            기      능 :   AttachmentService 구현체
 *            인      수 :   
 *            특이  사항 :
 *-----------------------------------------------------------------------------
 *                              변경 사항				                     
 *-----------------------------------------------------------------------------
 *    변경일자       	변경자(작성자)                 		변경 내역                 
 *   ----------     	--------------------------       -------------------------
 *   2014. 7. 14.      	jYeory<jyeory@gmail.com>         	최 초 작 성                      
 *==============================================================================
 * 
 * @author jYeory
 *
 */
@RemoteProxy(name="AttachmentDwr")
@Service("attachmentService")
public class AttachmentServiceImpl extends AbstractGenericManager implements AttachmentService{
	
	Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	private AttachmentDao dao;
	
	@Override
	public Attachment getAttachment(Parameters<String, String> params){
		return dao.getAttachment(params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PagedList<?> attachList(Parameters<String, String> params, int pg, int ps) {
		return (PagedList<Attachment>) list("com.nuri.dao.AttachmentDao", "attachList", params, pg, ps);
	}

	@Override
	@RemoteMethod
	public List<?> attachList(Parameters<String, String> params) {
		return dao.attachList(params);
	}

	@Override
	@RemoteMethod
	@Transactional
	public int addAttachment(Attachment attach) {
		return dao.addAttachment(attach);
	}

	@Override
	@RemoteMethod
	@Transactional
	public int deleteAttachment(String attachSeq) {
		return dao.deleteAttachment(attachSeq);
	}
}
