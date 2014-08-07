package com.nuri.common.utils;

import com.nuri.common.service.EnableManager;

public class CommonUtil {
	/**
	 * 해당 모듈이 활성화 상태인지 여부
	 * 
	 * @param name
	 * @return
	 */
	public static boolean isEnable(String name){
		return ((EnableManager)SpringContextUtils.getBean("enableMgr")).isEnable(name);
	}

}
