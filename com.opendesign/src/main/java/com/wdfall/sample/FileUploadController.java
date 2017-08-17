/*
 * @(#)SampleController.java
 * Copyright (c) Windfall Inc., All rights reserved.
 * 2015. 7. 6. - First implementation
 * contact : devhcchoi@gmail.com
 */
package com.wdfall.sample;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 *주의 사항.
 * RequestParam("myFileField1") MultipartFile file1 의 경우 업로드된 파일이 없으면 에러. 
 * </pre>
 * @author Hyun Chul Choi
 * @email devhcchoi@gmail.com
 * @version 1.0 Since 2015. 7. 12.
 */
@Controller
@RequestMapping(value="/sample")
public class FileUploadController {	

	@RequestMapping(value = "/fileUploadForm.do", method = RequestMethod.GET)
	public String showUploadForm() {
		return "sample/fileUploadForm";
	}

	/**
	 * 여러개의 다른 이름의 file input 처리. 
	 * @param file1 <input type="file" name="myFileField1" />
	 * @param file2 <input type="file" name="myFileField2" />
	 * @param file3 <input type="file" name="myFileField3" />
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/fileUpload.do", method = RequestMethod.POST)
	public ModelAndView handleFileUpload( 
			@RequestParam("myFileField1") MultipartFile file1 ,
			@RequestParam("myFileField2") MultipartFile file2 ,
			@RequestParam("myFileField3") MultipartFile file3 ,
			HttpServletRequest request
			) throws IOException {
		
		ModelMap modelData = new ModelMap();

		StringBuilder resultMessage = new StringBuilder(); 
		if (!file1.isEmpty()) {
			resultMessage.append (  "<br/>File1 successfully uploaded > "  ) ;
			resultMessage.append (file1.getOriginalFilename() );
			saveFile( file1, "c:/temp/", file1.getOriginalFilename() );
		}
		if (!file2.isEmpty()) {
			resultMessage.append (  "<br/>File2 successfully uploaded > "  ) ;
			resultMessage.append (file2.getOriginalFilename() );
			saveFile( file2, "c:/temp/", file2.getOriginalFilename() );
		}
		if (!file3.isEmpty()) {
			resultMessage.append (  "<br/>File3 successfully uploaded > "  ) ;
			resultMessage.append (file3.getOriginalFilename() );
			saveFile( file3, "c:/temp/", file3.getOriginalFilename() );
		}

		
		if(resultMessage.length() == 0 ){
			resultMessage.append( "File couldn't be uploaded" );
		}
		
		modelData.put("comment", request.getParameter("comment") );
		modelData.put("uploadMessage", resultMessage.toString() );
		return new ModelAndView("sample/fileUploadForm", modelData);
	}
	
	
	
	/**
	 * 동일한 이름의 여러 file input 처리
	 * @param files  <input type="file" name="myFileField" /><input type="file" name="myFileField" /><input type="file" name="myFileField" />...
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/multifileUpload.do", method = RequestMethod.POST)
	public ModelAndView handleMultiFileUpload( 
			@RequestParam("myFileField") MultipartFile[] files ,
			HttpServletRequest request
			) throws IOException {
		
		ModelMap modelData = new ModelMap();

		
		StringBuilder resultMessage = new StringBuilder();
		for( MultipartFile multipartFile : files ){
			if (!multipartFile.isEmpty()) {
				resultMessage.append (  "<br/>File successfully uploaded > "  ) ;
				resultMessage.append (multipartFile.getOriginalFilename() );
				saveFile( multipartFile, "c:/temp/", multipartFile.getOriginalFilename() );
			}			
		}

		
		if(resultMessage.length() == 0 ){
			resultMessage.append( "File couldn't be uploaded" );
		}
		
		modelData.put("comment", request.getParameter("comment") );
		modelData.put("uploadMessage", resultMessage.toString() );
		return new ModelAndView("sample/fileUploadForm", modelData);
	}
	
	
	@ExceptionHandler(value = Exception.class)
	public ModelAndView handleException() {
		ModelMap modelData = new ModelMap();
		String failureMessage = "Exception occurred during upload.";
		modelData.put("uploadMessage", failureMessage);
		return new ModelAndView("sample/fileUploadForm", modelData);
	}
	
	
	public static File saveFile( MultipartFile multipartFile , String path, String filename  ) throws IllegalStateException, IOException{
		File file = new File( path + filename);
		multipartFile.transferTo(file);
		return file;
	}
	
	
}
