package com.nuri.service.test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 * =============================================================================
 *            프로젝트명 :   openERP
 *            화  일  명 :   AbstractApplicationContextTest.java
 *            기      능 :   Test 클래스들의 슈퍼 클래스..
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
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:src/resources/applicationContext-datasource.xml", 
		"file:src/resources/applicationContext-transaction.xml", 
		"file:src/resources/applicationContext.xml"
			})
public class AbstractApplicationContextTest {
	@Autowired ApplicationContext ctx;
}
