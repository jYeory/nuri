package com.nuri.common.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import com.nuri.common.utils.PagedList;
import com.nuri.common.utils.Parameters;

/**
 * =============================================================================
 *            프로젝트명 :   openERP
 *            화  일  명 :   AbstractGenericManager.java
 *            기      능 :   
 *            인      수 :   
 *            특이  사항 :
 *-----------------------------------------------------------------------------
 *                              변경 사항				                     
 *-----------------------------------------------------------------------------
 *    변경일자       	변경자(작성자)                 		변경 내역                 
 *   ----------     	--------------------------       -------------------------
 *   2013. 07. 19.      이재열<jyeory@gmail.com>         	최 초 작 성                      
 *==============================================================================
 * 
 * @author JaeYeol Lee
 *
 */
public abstract class AbstractGenericManager implements GenericManager {
	protected final Log logger = LogFactory.getLog(getClass());

	private MessageSource messageSource;

	@Autowired
	public SqlSessionFactory sessFactoryIbatis;

	/**
	 * 전역 메시지 세팅
	 * 
	 * @param messageSource
	 */
	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	/**
	 * 전역 메시지 가져오기
	 * 
	 * @return
	 */
	public MessageSource getMessageSource() {
		return messageSource;
	}

	/* (non-Javadoc)
	 * @see net.autobrain.common.service.GenericManager#getMessage(java.lang.String, java.lang.String)
	 */
	@Override
	public String getMessage(String code, String defaultMessage) {
		return messageSource.getMessage(code, null, defaultMessage, null);
	}

	/* (non-Javadoc)
	 * @see net.autobrain.common.service.GenericManager#getMessage(java.lang.String)
	 */
	@Override
	public String getMessage(String code) {
		return messageSource.getMessage(code, null, code, null);
	}

	/* (non-Javadoc)
	 * @see net.autobrain.common.service.GenericManager#getMessage(java.lang.String, java.lang.Object[])
	 */
	@Override
	public String getMessage(String code, Object[] args) {
		return messageSource.getMessage(code, args, code, null);
	}

	/* (non-Javadoc)
	 * @see net.autobrain.common.service.GenericManager#getMessage(java.lang.String, java.lang.Object[], java.lang.String)
	 */
	@Override
	public String getMessage(String code, Object[] args, String defaultMesasge) {
		return messageSource.getMessage(code, args, defaultMesasge, null);
	}

	/**
	 * iBatis SqlSession 을 이용한 목록
	 * 
	 * @param mapper 해당 mapper의 namespace
	 * @param selectStatement select statement id
	 * @param params
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<?> list(String mapper, String selectStatement, Parameters<String, ?> params){
		SqlSession sqlSession = null;
		try{
			sqlSession = sessFactoryIbatis.openSession();
//			return new PagedList(sqlSession.selectList(mapper+'.'+selectStatement, params));
			return sqlSession.selectList(mapper+'.'+selectStatement, params);
		}finally{
			sqlSession.close();
		}
	}

	/**
	 * iBatis SqlSession 을 이용한 목록
	 * 
	 * @param mapper
	 * @param selectStatement
	 * @param params
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List<?> list(String mapper, String selectStatement, Parameters<String, ?> params, int currentPage, int pageSize) {
		return list(mapper, selectStatement, params, new RowBounds((currentPage-1)*pageSize, pageSize));
	}

	/**
	 * iBatis SqlSession 을 이용한 페이징 목록
	 * 
	 * @param mapper 해당 mapper의 namespace
	 * @param selectStatement select statement id
	 * @param params
	 * @param rowBounds
	 * @return PagedList
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<?> list(String mapper, String selectStatement, Parameters<String, ?> params, RowBounds rowBounds){
		SqlSession sqlSession = null;
		try{
			sqlSession = sessFactoryIbatis.openSession();
			PagedList<?> results = new PagedList(sqlSession.selectList(mapper+'.'+selectStatement, params, rowBounds));

			// Total Count set
			Number totalCount = (Number)sqlSession.selectOne(mapper+".count-"+selectStatement, params);
			results.setTotalCount(totalCount.intValue());
			results.setPageSize(rowBounds.getLimit());
			results.setCurrentPage(rowBounds.getOffset()/rowBounds.getLimit() + 1);

			return results;
		}finally{
			sqlSession.close();
		}
	}

}
