package com.nuri.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


/**
 * <pre>
 * keb.orms.util
 * FileUtil.java
 * <pre>
 *
 * @Desc : File 관련 Util 클래스.
 * @author  :  limhyojeong
 * @since   : 2012. 4. 17.
 * @version :
 */
public class FileUtil {

	private static Logger logger = Logger.getLogger(FileUtil.class);

	public FileUtil(){

	}
	/*
	 * 디렉토리 주소를 받아와서 그 안에 있는 파일 목록을 가져온다.
	 * file_Name     : 파일명
	 * file_Path     : 주소 (파일명 제외)
	 * file_FullPath : 전체 주소 (파일명 포함)
	 */
	/**
	 * Desc : 디렉토리 주소를 받아와서 그 안에 있는 파일 목록을 가져온다.
	 * file_Name     : 파일명
	 * file_Path     : 주소 (파일명 제외)
	 * file_FullPath : 전체 주소 (파일명 포함)
	 * @Mehtod Name : getFileList
	 * @param path : 파일이 존재하는 directory
	 * @return
	 */
	public static List<Map<String,String>> getFileList (String path){
		try {
			File dir = new File(path);
			File[] list = dir.listFiles();
			String fileName = "";
			List<Map<String,String>> lo = new ArrayList<Map<String,String>>();
			Map<String,String> m = null;

			if (list != null) {
				for (int i=0; i<list.length; i++){
					m = new HashMap<String,String>();
					fileName = list[i].getName();

					m.put("fileName", fileName);
					m.put("filePath", list[i].getParent());
					m.put("fileFullPath", list[i].getPath());

					lo.add(m);
				}
			}

			return lo;

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}


	/**
	 * Desc : 해당 directory 에 파일 업로드. parameter name 이 중복되지 않을경우 사용 하여 map 형태로 받음 
	 * ex)  <input type="file" name="file1" /> <input type="file" name="file2" />..
	 * @Mehtod Name : doFileUploadMap
	 * @param multiRequest
	 * @param path : file upload path
	 * @param newFileGbn : 실제파일명 앞에 붙을 구분자
	 * @return : upload 된 file path arrayList
	 * @throws Exception
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static ArrayList<String> doFileUploadMap(MultipartHttpServletRequest multiRequest , String path, String newFileGbn)
			throws Exception, FileNotFoundException, IOException
			{
		Map<String, MultipartFile> fileList = multiRequest.getFileMap();
		ArrayList<String> fullFileNameList = new ArrayList<String>();

		Iterator<Entry<String, MultipartFile>> itr = fileList.entrySet().iterator();
		MultipartFile file;
		createDirectory(path);// directory 생성
		while (itr.hasNext()) {
			Entry<String, MultipartFile> entry = itr.next();
			file = entry.getValue(); //파일 얻기
			String orginFileName = file.getOriginalFilename();//실제파일명
			String newfullFileName = path+newFileGbn+orginFileName;//신규 구분자로 기존파일과 구분
			long size = file.getSize();
			logger.info("orginFileName : "+ orginFileName);
			logger.info("newfullFileName : "+ newfullFileName);
			logger.info("size : "+size);

			if(orginFileName!=null&&!orginFileName.equals("")){
				FileUtil.removeFile(newfullFileName);//동일한 파일 삭제
				file.transferTo(new File(newfullFileName)); //파일생성
				fullFileNameList.add(newfullFileName); //return 파일명 add
			}else{
				fullFileNameList.add(null); //파일이 존재하지 않을경우 null
			}
		}

		return fullFileNameList;
			}

	/**
	 * Desc : 해당 directory에 파일 업로드. 동일한 parameter name사용시 List로 받음 
	 * ex) <input type="file" name="mFile" /> <input type="file" name="mFile" />..
	 * @Mehtod Name : doFileUploadList
	 * @param multiRequest
	 * @param path : file upload path
	 * @param newFileGbn : 실제파일명 앞에 붙을 구분자
	 * @param filePram : requestParameter name - ex) mfile
	 * @return : upload 된 file path arrayList
	 * @throws Exception
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static ArrayList<String> doFileUploadList(MultipartHttpServletRequest multiRequest , String path,  String newFileGbn, String filePram)
			throws Exception, FileNotFoundException, IOException{
		List fileList = multiRequest.getFiles(filePram);
		ArrayList<String> fullFileNameList = new ArrayList<String>();

		for(int i=0;i<fileList.size();i++){

			MultipartFile file= (MultipartFile)fileList.get(i);
			String orginFileName = file.getOriginalFilename();
			String newfullFileName = path+newFileGbn+orginFileName;//신규 구분자로 기존파일과 구분
			long size = file.getSize();
			logger.info("orginFileName : "+ orginFileName);
			logger.info("newfullFileName : "+ newfullFileName);
			logger.info("size : "+size);

			if(orginFileName!=null&&!orginFileName.equals("")){
				FileUtil.removeFile(newfullFileName);//동일한 파일 삭제
				file.transferTo(new File(newfullFileName)); //파일생성
				fullFileNameList.add(newfullFileName); //return 파일명 add
			}else{
				fullFileNameList.add(null); //파일이 존재하지 않을경우 null
			}

		}

		return fullFileNameList;
	}

	/**
	 * Desc : 해당 directory에 파일 업로드. 단일 file
	 * @Mehtod Name : doFileUpload
	 * @param multiRequest
	 * @param path : file upload path
	 * @param newFileName : 신규 파일명 null 이면 실제 파일명으로 저장 (확장자 제외- 확장자는 실제 파일명에서 구함)
	 * @param filePram : requestParameter name - ex) mfile
	 * @return : upload 된 file path
	 * @throws Exception
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String doFileUpload(MultipartHttpServletRequest multiRequest , String path,  String newFileName, String filePram)
			throws Exception, FileNotFoundException, IOException{

		MultipartFile file= multiRequest.getFile(filePram);
		String orginFileName = file.getOriginalFilename();

		String newfullFileName = path+orginFileName;//실제파일명으로 생성
		if(newFileName !=null && !newFileName.equals("")){//신규 파일명 으로 생성
			newfullFileName = path+newFileName+orginFileName.substring(orginFileName.lastIndexOf("."));
		}

		long size = file.getSize();
		logger.info("orginFileName : "+ orginFileName);
		logger.info("newfullFileName : "+ newfullFileName);
		logger.info("size : "+size);

		if(orginFileName!=null&&!orginFileName.equals("")){//파일존재여부

			FileUtil.removeFile(newfullFileName);//동일한 파일 삭제
			file.transferTo(new File(newfullFileName)); //파일생성
			return newfullFileName;

		}else{
			return null;
		}

	}


	/*
	 * 파일 업로드 후 파일 압에 구분자 추가.
	 *
	 */
	public static ArrayList<String> doFileUploadRename(MultipartHttpServletRequest multiRequest , String newFileGbn, String path, String newFileName) throws FileNotFoundException, IOException, Exception{
		return doFileUploadRename(multiRequest , newFileGbn, path, newFileName, "add");
	}

	/*
	 * 파일 업로드 후 파일 이름 변경
	 * newFileGbn : 시스템 명
	 *
	 */
	public static ArrayList<String> doFileUploadRename(MultipartHttpServletRequest multiRequest , String newFileGbn, String path, String newFileName, String type)
			throws Exception, FileNotFoundException, IOException
			{
		final Map<String, MultipartFile> fileList = multiRequest.getFileMap();
		ArrayList<String> fullFileNameList = new ArrayList<String>();

		Iterator<Entry<String, MultipartFile>> itr = fileList.entrySet().iterator();
		MultipartFile file;

		createDirectory(path);
		while (itr.hasNext()) {
			Entry<String, MultipartFile> entry = itr.next();

			file = entry.getValue(); //파일 얻기
			String orginFileName = file.getOriginalFilename();
			long size = file.getSize();
			logger.info("orginFileName : "+ orginFileName);
			logger.info("newFileName : "+ newFileName);
			logger.info("size : "+size);

			String newfullFileName = "";
			if("new".equals(type)){
				String fileType = "";
				if(orginFileName.lastIndexOf(".") != -1){
					fileType = orginFileName.substring(orginFileName.lastIndexOf("."), orginFileName.length());
					logger.info("fileType >>>>>>>" + fileType);
				}
				newfullFileName = path+newFileGbn+newFileName+fileType;//신규 구분자로 기존파일과 구분, 앞에 구분바를 줘서 어느 파일이지 알게함
			}else{
				newfullFileName = path+newFileGbn+newFileName+orginFileName;//신규 구분자로 기존파일과 구분, 앞에 구분바를 줘서 어느 파일이지 알게함
			}

			FileUtil.removeFile(newfullFileName);//동일한 파일 삭제
			//파일 목록에서 newFileName 으로 시작하는 것들 다 지우기 -> 현재 파일 1개일떄를 기준으로 되어 있음 다중파일 등록일때는 수정해야함.
			List<Map<String,String>> lo1 = FileUtil.getFileList(path);
			String fileName = "";
			for(int i = 0 ; i < lo1.size() ; i++){
				fileName = lo1.get(i).get("fileName");
				if(fileName.length() > newFileName.length()){
					if(newFileName.equals(fileName.substring(0,newFileName.length()))){
						FileUtil.removeFile(lo1.get(i).get("fileFullPath"));
					}
				}
			}

			file.transferTo(new File(newfullFileName)); //파일생성

			fullFileNameList.add(newfullFileName);
		}

		return fullFileNameList;
			}

	/*
	 * 디렉로티 생성
	 *
	 */
	public static void createDirectory(String dirPath) throws IOException
	{

		File dir = new File(dirPath);

		// check the same named file
		if(dir.exists() && dir.isFile()) {
			throw new IOException("there is the same named file already exist : " + dirPath);
		}

		// if not exist, create direcotry
		if(!dir.exists()) {
			if(dir.mkdirs()) {
			} else {
				throw new IOException("can not create the directory : " + dirPath);
			}
		}
	}

	/**
	 * 중복되지 않는 파일명을 반환한다.
	 *
	 * @param fileName
	 * @return
	 */
	public static String makeSystemFileName(String fileName) {
		String body = null;
		String ext = null;

		int dot = fileName.lastIndexOf(".");

		if (dot != -1) {
			body = fileName.substring(0, dot);
			ext = fileName.substring(dot);  // includes "."
		}
		else {
			body = fileName;
			ext = "";
		}

//		return ( body+"_"+makeUniqueToken()+ext );
		return String.valueOf(fileName + "_" + Calendar.getInstance().getTimeInMillis() + ext);
		//return ( StringUtil.encodeNameToLong(body) + "_" + StringUtil.makeRandomString(16) + ext );
	}

	/**
	 * 파라미터로 넘어온 파일을 삭제한다.
	 * @param filePath
	 */
	public static void removeFile(String filePath) {
		removeFile(new File(filePath));
	}

	public static void removeFile(File fileObj) {
		if(fileObj.exists()) {
			fileObj.delete();
		}
	}

	/**
	 * 업로드된 파일을 다운 로드 한다.
	 *
	 * @param filePath - 다운로드 하려는 파일 경로 (절대경로)
	 * @param originalFileName - 업로드 시의 원래 파일 이름
	 * @param response
	 * @throws IOException
	 */
	public static void downLoadFile(String filePath, String originalFileName, HttpServletResponse response) throws IOException {

		InputStream in = null;
		OutputStream os = null;

		try
		{
			File file = new File(filePath);
			in = new FileInputStream(file);
			response.reset() ;
			String fname = new String(originalFileName.getBytes("euc-kr"),"8859_1");

			response.setHeader("Content-Disposition", "attachment; filename=\"" + fname + "\";");
			response.setHeader("Content-Type", "application/octet-stream; charset=8859_1");
			response.setHeader("Content-Length", ""+file.length() );

			os = response.getOutputStream();

			byte b[] = new byte[(int)file.length()];
			int leng = 0;

			while( (leng = in.read(b)) > 0 ){
				os.write(b,0,leng);
			}

		}
		catch(IOException ioe) {
			try{
				PrintWriter out = response.getWriter();
				out.println("<script>alert('File Not Found');</script>");

			} catch(Exception e) {
				e.printStackTrace();
			}

		}
		finally{
			if(in !=null)try{in.close();}catch(Exception e){}
			if(os !=null)try{os.close();}catch(Exception e){}
		}
	}

	/**
	 * 업로드된 파일을 다운 로드 한다. -> 구분자 붙인것 삭제
	 *
	 * @param filePath - 다운로드 하려는 파일 경로 (절대경로)
	 * @param originalFileName - 업로드 시의 원래 파일 이름
	 * @param response
	 * @throws IOException
	 */
	public static void downLoadFileRename(String filePath, String originalFileName, String addFileName, HttpServletResponse response) throws IOException {

		InputStream in = null;
		OutputStream os = null;


		try
		{
			File file = new File(filePath);
			in = new FileInputStream(file);
			response.reset() ;
			logger.info("getAbsolutePath?"+file.getAbsolutePath());
			logger.info("getAbsoluteFile?"+file.getAbsoluteFile());
			String fname = new String(originalFileName.getBytes("euc-kr"),"8859_1");

			if(fname.length()>addFileName.length())
				fname = fname.substring(addFileName.length(),fname.length());

			response.setHeader("Content-Disposition", "attachment; filename=\"" + fname + "\";");
			response.setHeader("Content-Type", "application/octet-stream; charset=8859_1");
			response.setHeader("Content-Length", ""+file.length() );

			os = response.getOutputStream();

			byte b[] = new byte[(int)file.length()];
			int leng = 0;

			while( (leng = in.read(b)) > 0 ){
				os.write(b,0,leng);
			}

		}
		catch(IOException ioe) {
			try{
				PrintWriter out = response.getWriter();
				out.println("<script>alert('File Not Found');</script>");

			} catch(Exception e) {
				e.printStackTrace();
			}

		}
		finally{
			if(in !=null)try{in.close();}catch(Exception e){}
			if(os !=null)try{os.close();}catch(Exception e){}
		}
	}


	/**
	 * 업로드된 파일을 다운 로드 한다. -> 구분자 붙인것 삭제
	 *
	 * @param filePath - 다운로드 하려는 파일 경로 (절대경로)
	 * @param originalFileName - 업로드 시의 원래 파일 이름
	 * @param response
	 * @throws IOException
	 */
	public static void downLoadFileNewName(String filePath, String originalFileName, String newFileName, HttpServletResponse response) throws IOException {

		InputStream in = null;
		OutputStream os = null;


		try
		{
			File file = new File(filePath);
			in = new FileInputStream(file);
			response.reset() ;
			logger.info("getAbsolutePath?"+file.getAbsolutePath());
			logger.info("getAbsoluteFile?"+file.getAbsoluteFile());
			String fname = new String(newFileName.getBytes("euc-kr"),"8859_1");

			response.setHeader("Content-Disposition", "attachment; filename=\"" + fname + "\";");
			response.setHeader("Content-Type", "application/octet-stream; charset=8859_1");
			response.setHeader("Content-Length", ""+file.length() );

			os = response.getOutputStream();

			byte b[] = new byte[(int)file.length()];
			int leng = 0;

			while( (leng = in.read(b)) > 0 ){
				os.write(b,0,leng);
			}

		}
		catch(IOException ioe) {
			try{
				PrintWriter out = response.getWriter();
				out.println("<script>alert('File Not Found');</script>");

			} catch(Exception e) {
				e.printStackTrace();
			}

		}
		finally{
			if(in !=null)try{in.close();}catch(Exception e){}
			if(os !=null)try{os.close();}catch(Exception e){}
		}
	}

	/**
	 * Desc : 파일 생성.
	 * @Mehtod Name : createFile
	 * @param filePath
	 * @param fileName
	 * @param data
	 * @throws IOException
	 */
	public static void createFile(String filePath, String fileName, List data) throws IOException {

		PrintWriter pw = null;
		createDirectory(filePath);
		pw = new PrintWriter(new FileWriter(filePath + fileName, false), false);
		if(data!=null){
			Iterator it = data.iterator();
			while(it.hasNext()){
				pw.println(it.next());
			}
		}
		pw.flush();
		pw.close();
	}

	/**
	 * Desc : DB에 blob type insert 가능한 AttacyFile로 converter.
	 * @Mehtod Name : fileToAttach
	 * @param fullfileName
	 * @return
	 * @throws IOException
	 */
	public static AttachFile fileToAttach(String fullfileName) throws IOException {

		AttachFile attach = new AttachFile();
		attach.setAttachName(fullfileName.substring(fullfileName.lastIndexOf("/")+1));
		FileInputStream input = null;
		byte [] fileContent = null;

		File afile = new File(fullfileName);
		attach.setAttachSize(afile.length());

		input = new FileInputStream(afile);

		fileContent = new byte [input.available() + 1];											// byte 배열로 디코딩 내용을 가져온다
		input.read(fileContent);
		attach.setFile(fileContent);

		return attach;
	}

	public static String makeZipFile(String targetDir, String zipfileDir, String makeZipfileName) throws Exception{

		String szInputFile = targetDir;
		String szGZipTemp = zipfileDir +"/" + makeZipfileName;

		File inFile = null;
		File inDirectory = null ;

		File[] ainFile = null;

		FileInputStream fis = null;


		File gzipFile = null;
		FileOutputStream fos = null;
		java.util.zip.ZipOutputStream zos = null;

		java.util.zip.ZipEntry ze = null;

		byte[] buffer = new byte[1024 * 8];
		int nRead;

		try {

			inFile = new File ( szInputFile);

			if (inFile.isDirectory()) {
				inDirectory = new File ( szInputFile);
			} else  {
				throw new Exception();
			}

			ainFile = inDirectory.listFiles();

			gzipFile = new File ( szGZipTemp);
			fos = new FileOutputStream ( gzipFile);
			zos = new java.util.zip.ZipOutputStream( fos);

			for ( int i=0; i < ainFile.length ; i++) {
				fis = new FileInputStream( ainFile[i]);

				ze = new ZipEntry ( ainFile[i].getName());

				if (ze.getName().equals("BaselBusinessLineStructure_B.csv") || ze.getName().equals("BaselBusinessLineStructure_O.csv") )  continue;

				if (ze.getName().equals("LossEventDetail_B.csv") || ze.getName().equals("LossEventDetail_O.csv") )  continue;

				logger.info("ainFile[i].getName() : "+ ainFile[i].getName() + ", fis.available() : " + fis.available());

				zos.putNextEntry(ze);
				zos.setLevel(9);
				buffer = new byte[1024 * 8];
				nRead = 0;

				while ( (nRead = fis.read( buffer)) != -1) {
					zos.write( buffer, 0, nRead);
				}

				fis.close();
				zos.closeEntry();
			}
			return szGZipTemp;

		}catch (IOException e) {
			e.printStackTrace();
			throw e;
		}finally {
			try { 
				fis.close(); 
				zos.close();  
				fos.close(); 
			} catch ( Exception e) {   e.printStackTrace(); throw e;}

		}
	}

}
