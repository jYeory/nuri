package com.nuri.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.nuri.common.controller.BaseController;
import com.nuri.common.utils.FileController;
import com.nuri.common.utils.PagedList;
import com.nuri.common.utils.Parameters;
import com.nuri.domain.Notice;
import com.nuri.domain.User;
import com.nuri.service.NoticeService;

@Controller
public class NoticeController extends BaseController {

	@Autowired 
	private NoticeService noticeService;
	
	@Autowired 
	private FileController uploadUtil;
	
	/**
	 * 공지사항 리스트 페이지
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/notice/noticeList.do")
	public String noticePage(HttpServletRequest request, HttpServletResponse response){
		 return "notice/noticeList";
	}
	
	/**
	 * 공지사항 목록 가져오기
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/notice/getList.do")
	@ResponseBody
	public PagedList<Notice> noticeList(HttpServletRequest request, HttpServletResponse response){
		
		Parameters<String, Object> params = new Parameters<String, Object>(request);
		//parameter for paging 
		int rows = Integer.parseInt((String) params.get("rows"));
		int page = Integer.parseInt((String) params.get("page"));
		
		return (PagedList<Notice>) noticeService.noticeList(params, page, rows);
	}
	
	/**
	 * 공지사항 수정
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@RequestMapping("/notice/noticeEdit.do")
	public ModelAndView addNoticePage(HttpServletRequest request, HttpServletResponse response){
		Parameters<String, Object> params = new Parameters<String, Object>(request);
		String cmd = params.getString("cmd");
		
		ModelAndView mv = new ModelAndView();
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		mv.addObject("user", user);
		mv.addObject("cmd", cmd);
		mv.setViewName("notice/noticeEdit");
		
		if(cmd.equals("edit")){
			String noticeSeq = params.getString("noticeSeq");
			mv.addObject("noticeSeq", noticeSeq);
		}
		
		return mv;
	}
	
	/**
	 * 공지사항 등록
	 * @param request
	 * @param response
	 * @param notice
	 * @return
	 */
	@RequestMapping("/notice/addNotice.do")
	public ModelAndView addRoomType(HttpServletRequest request, HttpServletResponse response, @ModelAttribute Notice notice){
		MultipartHttpServletRequest multipartRequest =  (MultipartHttpServletRequest)request;  //다중파일 업로드
		List<MultipartFile> files = multipartRequest.getFiles("noticeAttachFile");
		
		ModelAndView mv = new ModelAndView();
		try{
			// 공지사항 첨부파일은 1개
			MultipartFile mpf = files.get(0);
			notice.setHasAttach( !mpf.isEmpty() ? "1" : "0" );
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			notice.setCreateBy(user.getUserSeq());
			
			noticeService.addNotice(notice);
			
			if(notice.getNoticeSeq() != null && !mpf.isEmpty()){
				// 첨부파일 복사 및 업로드 시작.
//				uploadUtil.doFileUpload("notice", notice.getNoticeSeq(), files);
			}
			
			mv.addObject("resultMessage", "공지사항을 등록하였습니다.");
		}catch(Exception e){
			mv.addObject("resultMessage", "공지사항 등록에 실패하였습니다.");
		}
		
		mv.setViewName("redirect:/notice/noticeGrid.jsp");
		return mv;
	}
	
}
