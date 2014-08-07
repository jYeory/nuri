package com.nuri.common.controller;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

public class BaseController {
	
	Log logger = LogFactory.getLog(BaseController.class);
	
	// Exception 처리 메소드  
	@ExceptionHandler(Throwable.class)
	@ResponseBody
	public Map<String,Object> handleException(Exception e, HttpServletResponse response) throws Exception{
		logger.error("[ERROR 발생!] ====================================================");
		logger.error("[발생 대상 : "+this.getClass()+"]");
		logger.error(ExceptionUtils.getStackTrace(e));
		logger.error("====================================================================");
		
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		
		Map<String, Object> map = new HashMap<String, Object>();
		int status = 0;
		String message = "";
		
		if(e instanceof DuplicateKeyException){
			status = 1;
			message = "데이터가 존재합니다.";
		}else if(e instanceof DataIntegrityViolationException){
			Throwable throwable = e.getCause();
			if ( throwable.getCause() instanceof SQLIntegrityConstraintViolationException ){
				if( throwable.getCause().getMessage().indexOf("ORA-01400") > -1){
					status = 7;
					message = "NULL을 삽입할 수 없습니다.";
				}
				else {
					status = 6;
					message = "무결성 제약조건에 위배되었습니다.";
				}
			}
			else{
				status = 2;
				message = "잘못된 데이터 형식입니다.";
			}
		}else if(e instanceof BadSqlGrammarException){
			status = 3;
			message = "SQL 문법오류.";
		}else if(e instanceof DataAccessException){
			status = 4;
			message = "데이터 오류.";
		}else if(e instanceof NullPointerException){
			status = 5;
			message = "데이터가 존재하지 않습니다.";
		}else{
			status = 99;
			message = "ERROR.";
		}
		
		map.put("status", status);
		map.put("message", message);
		map.put("exception", e.getClass().getName());
		
		return map;
	}
}
