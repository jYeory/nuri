package com.nuri.service.test;

import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.nuri.common.utils.JSONUtil;
import com.nuri.common.utils.PagedList;
import com.nuri.common.utils.Parameters;
import com.nuri.domain.Notice;
import com.nuri.domain.Role;
import com.nuri.domain.User;
import com.nuri.service.NoticeService;

/**
 * 
 * =============================================================================
 *            프로젝트명 :   openERP
 *            화  일  명 :   NoticeServiceTest.java
 *            기      능 :   NoticeService Test case 파일
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
public class NoticeServiceTest extends AbstractApplicationContextTest{
	private static Log log = LogFactory.getLog(NoticeServiceTest.class);
	
	@Resource(name="noticeService")
    private NoticeService noticeService;
	
    @Test
    public void testAddNotice(){
    	
    	Parameters<String, Object> params = new Parameters<String, Object>();
    	params.addValue("p_title", "공지사항 테스트. - 제목 -");
    	params.addValue("p_contents", "공지사항 테스트 - 여기는 내용\nblablabla");
    	params.addValue("p_createBy", null);
    	params.addValue("p_createSeq", null);
    	params.addValue("p_updateDate", null);
    	params.addValue("p_disabled", null);

    	Notice notice = (Notice) params.populate(Notice.class);
    	// insert query를 하게 되면 파라미터인 user에 insert 된 key가 매핑되어 return 된다.
    	noticeService.addNotice(notice);
    	
    	System.out.println("Notice > \n"+JSONUtil.encode(notice));
    }
    
}
