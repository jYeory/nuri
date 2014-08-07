package com.nuri.service.test;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nuri.service.RoleService;

/**
 * 
 * =============================================================================
 *            프로젝트명 :   openERP
 *            화  일  명 :   RoleServiceTest.java
 *            기      능 :   RoleService Test Case 파일
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
public class RoleServiceTest extends AbstractApplicationContextTest{
	private static Log log = LogFactory.getLog(RoleServiceTest.class);
	
	@Resource(name="roleService")
    private RoleService roleService;
	
	
	public void testInsertRole(){
		
	}
}
