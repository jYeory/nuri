package com.nuri.common.service;

import java.io.IOException;

/**
 * =============================================================================
 *            프로젝트명 	:   nPine_CRM
 *            화  일  명 	:   EnableManager.java
 *            기      능 	:   모듈 활성화 관리자
 *            인      수 	:   
 *            특이  사항 	:
 *-----------------------------------------------------------------------------
 *                              변경 사항				                     
 *-----------------------------------------------------------------------------
 *    변경일자       			변경자(작성자)                 		변경 내역                 
 *   ----------     	--------------------------       -------------------------
 *   2011. 6. 30.      		이재열<jyeory@gmail.com>         		최 초 작 성                      
 *==============================================================================
 * 
 * @author JaeYeol Lee
 *
 */
public interface EnableManager extends GenericManager {

	/**
	 * 설정파일 리셋
	 * 
	 * @throws IOException
	 */
	public void reset() throws IOException;

	/**
	 * 해당 모듈이 활성화 상태인지 여부
	 * 
	 * @param name
	 * @return
	 * @throws IOException 
	 */
	public boolean isEnable(String name);

	/**
	 * 해당 모듈을 활성화
	 * 
	 * @param name
	 */
	public void enable(String name);

	/**
	 * 해당 모듈을 비활성화
	 * 
	 * @param name
	 */
	public void disable(String name);
}