package com.nuri.common.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.nuri.domain.Attachment;
import com.nuri.service.AttachmentService;

@Controller
public class FileController {

	@Value("${file.upload.directory}")
    private String fileUploadDirectory;
	
	@Autowired
	private AttachmentService attachService;
	
	String newFilenameBase = null;
	String originalFileExtension = null;
	String newFilename = null;
	String storageDirectory = null;
	String contentType = null;
	File newFile = null;
	
	/**
	 * 첨부파일 다운로드
	 * @param request
	 * @param response
	 * @return
	 */
    @RequestMapping(value = "/common/file/getFile.do")
    @ResponseBody
    public byte[] getFile(HttpServletRequest request, HttpServletResponse response) {
	   Parameters<String, String> params = new Parameters<String, String>(request);
	   Attachment attachment = attachService.getAttachment(params);
	   
	   File file = new File(fileUploadDirectory+"/"+params.get("attachDocType")+"/"+ attachment.getFakeName());
       response.setContentType( attachment.getContentType() );
//	   response.setContentType("application/octet-stream");
       response.setHeader("Content-Disposition", "attachment; filename=\"" + attachment.getFilename() + "\"");
       response.setHeader("Pragma", "no-cache");
       response.setHeader("Cache-Control", "no-cache");
       response.setContentLength( attachment.getFileSize().intValue());
       try {
            return FileUtils.readFileToByteArray(file);
       } catch(IOException e) {
           e.printStackTrace();
           return null;
       }
    }
	
	/**
	 * 첨부파일 복사 및 DB 입력
	 * 
	 * @param attachDocType		첨부하는 파일의 문서 유형 (공지사항 등)
	 * @param attachDocKey		첨부하는 파일의 문서 키
	 * @param files
	 */
	@RequestMapping("/common/file/upload.do")
	public void doFileUpload(HttpServletRequest request, HttpServletResponse response){
		Parameters<String, String> params = new Parameters<String, String>(request);
		MultipartHttpServletRequest multipartRequest =  (MultipartHttpServletRequest) request;  //다중파일 업로드
		List<MultipartFile> files = multipartRequest.getFiles("noticeAttachFile");
		
		String attachDocType = params.getString("attachDocType");
		String attachDocKey = params.getString("attachDocKey");
		Attachment attach = null;
		for(MultipartFile mpf : files){
			attach = new Attachment();
			
			newFilenameBase = UUID.randomUUID().toString();
			originalFileExtension = mpf.getOriginalFilename().substring(mpf.getOriginalFilename().lastIndexOf("."));
			newFilename = newFilenameBase + originalFileExtension;
			storageDirectory = fileUploadDirectory;
			contentType = mpf.getContentType();
			
			newFile = new File(storageDirectory);
			if(!newFile.exists()) newFile.mkdir();
			newFile = new File(storageDirectory, attachDocType);
			if(!newFile.exists()) newFile.mkdir();
			newFile = new File(newFile.getAbsolutePath(), newFilename);
			
			try {
				// DB에 입력
				attach.setAttachDocType(attachDocType);
				attach.setAttachDocKey(attachDocKey);
				attach.setFakeName(newFilename);
				attach.setContentType(contentType);
				attach.setFilename(mpf.getOriginalFilename());
				attach.setFileSize(mpf.getSize());

				attachService.addAttachment(attach);
				
				// 파일 복사
				FileCopyUtils.copy(mpf.getBytes(), newFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
