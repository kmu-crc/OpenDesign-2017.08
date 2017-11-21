/*
 * Copyright (c) 2016 OpenDesign All rights reserved.
 *
 * This software is the confidential and proprietary information of OpenDesign.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with OpenDesign.
 */
package com.opendesign.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;

import net.coobird.thumbnailator.Thumbnails;

/**
 * <pre>
 * 썸네일 처리
 * </pre>
 * 
 * @author hanchanghao
 * @since 2016. 11. 15.
 */
public class ThumbnailManager {

	
	/**
	 * (디자이너/제작자) 작품 썸네일 (작품 목록 페이지에서 사용됨 [프로젝트 작업도 포함 공통] )
	 * 사이트 내 표준 사이즈 (228 x 169)
	 */
	private static final int IMG_WIDTH_DESIGN_WORK_MEDIUM = 228;
 
	/**
	 * (디자인 프로젝트) 작품 (상세 에서 사용됨)
	 * 사이트 내 표준 사이즈 (1084 x 677)
	 */
	private static final int IMG_WIDTH_DESIGN_WORK_LARGE = 1084;
	
	
	/**
	 * (프로젝트) 작품 (주제영역에서 보여지는 width)
	 * 사이트 내 표준 사이즈 (192 x 가변)
	 */
	private static final int IMG_WIDTH_PROJECT_WORK_SMALL = 192;
	
	/**
	 * (프로젝트) 작품 (디자인 프로젝트 목록 썸네일)
	 * 사이트 내 표준 사이즈 (268 x 109)
	 */
	private static final int IMG_WIDTH_PROJECT_WORK_MEDIUM = 268;
 
	/**
	 * (프로젝트) 작품 (상세 레이어 팝업)
	 * 사이트 내 표준 사이즈 (956 x 가변)
	 */
	private static final int IMG_WIDTH_PROJECT_WORK_LARGE = 956;
	
	/**
	 * 썸네일(디자인/제작자 작업) 이름 Suffix - Medium(중간) 사이즈
	 */
	public static final String SUFFIX_DESIGN_WORK_MEDIUM = "_DE_M";
	
	/**
	 * 썸네일(디자인/제작자 작업) 이름 Suffix - Big 사이즈
	 */
	public static final String SUFFIX_DESIGN_WORK_LARGE = "_DE_L";
	
	/**
	 * 썸네일(프로젝트 작업) 이름 Suffix - Small 사이즈
	 */
	public static final String SUFFIX_PROJECT_WORK_SMALL = "_PR_S";
	
	/**
	 * 썸네일(프로젝트 작업) 이름 Suffix - Medium(중간) 사이즈
	 */
	public static final String SUFFIX_PROJECT_WORK_MEDIUM = "_PR_M";
	
	/**
	 * 썸네일(프로젝트 작업) 이름 Suffix - Big 사이즈
	 */
	public static final String SUFFIX_PROJECT_WORK_LARGE = "_PR_L";

	
	
	/**
	 * (디자이너/제작자) 작품 (작품 목록 페이지에서 사용됨)
	 * 사이트 내 표준 사이즈 (228 x 169)
	 * @param filePath
	 */
	public static void saveThumbDesignWorkMedium(String filePath) {
		try {
			resizeNSaveThumbnail(IMG_WIDTH_DESIGN_WORK_MEDIUM, new File(filePath), SUFFIX_DESIGN_WORK_MEDIUM);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * (디자인 프로젝트) 작품 (상세 에서 사용됨)
	 * 사이트 내 표준 사이즈 (1084 x 677)
	 * @param filePath
	 */
	public static void saveThumbDesignWorkLarge(String filePath) {
		try {
			resizeNSaveThumbnail(IMG_WIDTH_DESIGN_WORK_LARGE, new File(filePath), SUFFIX_DESIGN_WORK_LARGE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * (프로젝트) 작품 (주제영역에서 보여지는 width)
	 * 사이트 내 표준 사이즈 (192 x 가변)
	 * @param filePath
	 */
	public static void saveThumbProjectWorkSmall(String filePath) {
		try {
			System.out.println(filePath+"---filePath");
			resizeNSaveThumbnail(IMG_WIDTH_PROJECT_WORK_SMALL, new File(filePath), SUFFIX_PROJECT_WORK_SMALL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * (프로젝트) 작품 (디자인 프로젝트 목록 썸네일)
	 * 사이트 내 표준 사이즈 (268 x 109)
	 * @param filePath
	 */
	public static void saveThumbProjectWorkMedium(String filePath) {
		try {
			resizeNSaveThumbnail(IMG_WIDTH_PROJECT_WORK_MEDIUM, new File(filePath), SUFFIX_PROJECT_WORK_MEDIUM);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * (프로젝트) 작품 (상세 레이어 팝업)
	 * 사이트 내 표준 사이즈 (956 x 가변)
	 * @param filePath
	 */
	public static void saveThumbProjectWorkLarge(String filePath) {
		try {
			resizeNSaveThumbnail(IMG_WIDTH_PROJECT_WORK_LARGE, new File(filePath), SUFFIX_PROJECT_WORK_LARGE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 썸네일 이미지 변환 후 저장
	 * 파라미터로 넘겨지는 width 값을 기준으로 resize 된다.
	 * @param width
	 * @param imageFile
	 * @param suffix
	 * @throws Exception
	 */
	private static void resizeNSaveThumbnail(double width, 
				File imageFile, String suffix) throws Exception {
		
		System.out.println(width+"---width");
		System.out.println(imageFile+"---imageFile");
		System.out.println(suffix+"---suffix");
		
		BufferedImage originalImage = ImageIO.read(imageFile);
		System.out.println(originalImage+"---original Image");
		double originalWidth = originalImage.getWidth();
		System.out.println(originalWidth+"---originWid");
		double originalHeight = originalImage.getHeight();
		System.out.println(originalHeight+"---originHei");
		System.out.println("working here");
		
		double resizableWidth = originalWidth;
		double resizableHeight = originalHeight;
		
		String extension = imageFile.getName().substring(imageFile.getName().indexOf(".") + 1);
		String fileName = imageFile.getName().substring(0, imageFile.getName().indexOf(".") );
		String target = imageFile.getAbsolutePath();
		target = target.substring(0, target.lastIndexOf(File.separator) + 1) + fileName + suffix + "." + extension;
		
		System.out.println(extension+"---extension");
		System.out.println(fileName+"---fileName");
		System.out.println(target+"---target");
		
		if( originalWidth > width ) {
			resizableWidth = width;
			double ratio = (double)width / (double) originalImage.getWidth();
			resizableHeight = originalImage.getHeight() * ratio;
			
			Thumbnails.of(originalImage).size((int)resizableWidth, (int)resizableHeight)
			.outputFormat(extension)
			.toFile(target);
		} else {
			CmnUtil.fileCopy(imageFile.getAbsolutePath(), target);
		}
		
		System.out.println("all done");
		
		
	}
	
	/**
	 * 디자이너/제작자 작품 등록시 썸네일 저장 
	 * @param thumbnail
	 * @param productFileList
	 */
	public static void resizeNClone4DesignWork(String thumbnailPath, List<String> productFilePaths) {
		
		File thumbFile = new File(thumbnailPath);
		if( StringUtils.isNotEmpty(thumbnailPath) && isImageFile(thumbFile) ) {
			saveThumbDesignWorkMedium(thumbnailPath); //목록 썸네일
			saveThumbDesignWorkLarge(thumbnailPath); //상세 썸네일
		}
		
		for( String filePath : productFilePaths ) {
			File productFile = new File(filePath);
			
			if( StringUtils.isNotEmpty(filePath) && isImageFile(productFile) ) {
				
				saveThumbDesignWorkLarge(filePath); //상세 작업
			}
		}
		
	}
	
	/**
	 * 썸네일 파일명(Full path) 가져오기 
	 * 
	 * @param filePath - fullPath
	 * @param suffix - {@link ThumbnailManager#SUFFIX_DESIGN_WORK_MEDIUM} so on....
	 * @return
	 */
	public static String getThumbnail(String filePath, String suffix) {
		
		if( StringUtils.isEmpty(filePath) ) {
			return filePath;
		}
		
		String fileName = filePath.substring(filePath.lastIndexOf(File.separator) + 1);		
		fileName = fileName.substring(0, fileName.indexOf(".") );
		
		String extension = filePath.substring(filePath.indexOf(".") + 1);
		
		String filePathWithSuffix = filePath.substring(0, filePath.lastIndexOf(File.separator) + 1) + fileName + suffix + "." + extension;
		
		return filePathWithSuffix;
	}
	
	
	/**
	 * <pre>
	 * 이미지 파일 여부를 판단한다.
	 * 
	 * 기존에는 파일의 실제 메타로 판단하였으나,
	 * 몇몇 png 에서 이미지 메타정보가 누락된 것이 있어서,
	 * 파일 확장자로만 여부를 판단하는 것으로 변경 하였음
	 * </pre>
	 * @param file
	 * @return
	 */
	public static boolean isImageFile(File file) {
		
		/*
		String mimeType = new MimetypesFileTypeMap().getContentType( file );
		   // mimeType should now be something like "image/png"
		
		System.out.println("mimetype=" + mimeType + " file=" + file);
		
		return mimeType.substring(0,5).equalsIgnoreCase("image");
		*/
		String[] validSuffixes = {"jpg", "jpeg", "png", "gif", "bmp"};
		for( String suffix : validSuffixes ) {
			String filePath = file.getName().toLowerCase();
			if( filePath.endsWith(suffix) ) {
				return true;
			}
		}
		return false;
	}
	
	// 문서 파일인지 확인
	public static boolean isDocuFile(File file) {
		
		String[] validSuffixes = {"doc", "docx", "ppt", "pptx"};
		for( String suffix : validSuffixes ) {
			String filePath = file.getName().toLowerCase();
			if( filePath.endsWith(suffix) ) {
				return true;
			}
		}
		return false;
	}
	
	
	public static void main(String[] args) {
		
//		//product 밑에 
//		saveThumbDesignWorkMedium(thumbnailPath); //목록 썸네일
//		saveThumbDesignWorkLarge(thumbnailPath); //상세 썸네일
//		
//		//project 밑에
//		saveThumbDesignWorkMedium(thumbnailPath); //목록 썸네일
//		saveThumbProjectWorkMedium(thumbnailPath); //목록 썸네일
//		
//		//work 밑에
//		saveThumbProjectWorkSmall(thumbnailPath); //목록 썸네일
//		saveThumbProjectWorkLarge(thumbnailPath); //목록 썸네일
		
		File productFolder = new File("/Users/windfall/Desktop/00_working/02_프로젝트/02_01_OpenDesign/02_01_100_temp/km_upload/product");
		
		int updated = 0;
		int total = 0;
		for( File image : productFolder.listFiles() ) {
			if( image != null) {
				
				System.out.println("resizing " + image.getAbsolutePath());
				saveThumbDesignWorkMedium(image.getAbsolutePath()); //목록 썸네일
				saveThumbDesignWorkLarge(image.getAbsolutePath()); //상세 썸네일
				updated++;
				
			} else {
				System.out.println("Fail=" + image);
			}
			total++;
		}
		
		System.out.println("Product Files=["+total+"] updated=["+updated+"]");
		
		updated = 0;
		total = 0;
		File projectFolder = new File("/Users/windfall/Desktop/00_working/02_프로젝트/02_01_OpenDesign/02_01_100_temp/km_upload/project");
		
		for( File image : projectFolder.listFiles() ) {
			if( image != null) {
				
				System.out.println("resizing " + image.getAbsolutePath());
				saveThumbDesignWorkMedium(image.getAbsolutePath()); //목록 썸네일
				saveThumbProjectWorkMedium(image.getAbsolutePath()); //상세 썸네일
				updated++;
				
			} else {
				System.out.println("Fail=" + image);
			}
			total++;
		}
		
		System.out.println("Project Files=["+total+"] updated=["+updated+"]");
		
		updated = 0;
		total = 0;
		
		File workFolder = new File("/Users/windfall/Desktop/00_working/02_프로젝트/02_01_OpenDesign/02_01_100_temp/km_upload/project_work_file");
		
		for( File image : workFolder.listFiles() ) {
			if( image != null) {
				
				System.out.println("resizing " + image.getAbsolutePath());
				saveThumbProjectWorkSmall(image.getAbsolutePath()); //목록 썸네일
				saveThumbProjectWorkLarge(image.getAbsolutePath()); //상세 썸네일
				updated++;
				
			} else {
				System.out.println("Fail=" + image);
			}
			total++;
		}
		
		System.out.println("Work Files=["+total+"] updated=["+updated+"]");
		
	}
	
	
	
}
