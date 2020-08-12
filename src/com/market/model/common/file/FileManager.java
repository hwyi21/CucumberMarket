package com.market.model.common.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.market.exception.FileException;

@Component
public class FileManager {
	//파일명 생성하기
	public static String createFilename(String path) {
		long time = System.currentTimeMillis();
		String ext = getExt(path); //확장자를 얻어옴
		String filename=time+"."+ext;
		return filename;
	}
	
	//확장자만 추출하기
	public static String getExt(String path) {
		int index = path.lastIndexOf(".");
		String ext = path.substring(index+1, path.length());
		return ext;
	}
	
	//지정한 경로에 파일  이미지 여러장 저장하기
	public static List saveFile(HttpServletRequest request, String realPath) throws FileException{
		boolean flag=false;
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
		Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
		MultipartFile multipartFile = null;
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>(); 
		Map<String, Object> listMap = null;
		while (iterator.hasNext()) {
			multipartFile = multipartHttpServletRequest.getFile(iterator.next());
			if (multipartFile.isEmpty() == false) {	
				//원래 파일 이름
				String ori = multipartFile.getOriginalFilename();
				// 디비에 저장할 파일 경로 + 이름
				String filename=realPath+FileManager.createFilename(ori);
				File dest=new File(filename);
				try {
					multipartFile.transferTo(dest);//디스크에 저장
					flag=true;
				} catch (IllegalStateException e) {
					e.printStackTrace();
					flag=false;
				} catch (IOException e) {
					flag=false;
					e.printStackTrace();
				} 
				listMap = new HashMap<String,Object>(); 
				listMap.put("original_filename", ori);
				listMap.put("filename", dest.getName()); 
				list.add(listMap); 
			}
		}
		if(flag==false) {
			throw new FileException("파일 저장에 실패하였습니다.");
		}
		return list;

	}
	
	//이미지 파일 한 건 저장
	public static List saveOneFile(MultipartFile multipartFile, String ori, String realPath) throws FileException{
		boolean flag=false;
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>(); 
		Map<String, Object> listMap = null;
		
		// 디비에 저장할 파일 경로 + 이름
		String filename=realPath+FileManager.createFilename(ori);
		File dest=new File(filename);
		try {
			multipartFile.transferTo(dest);//디스크에 저장
			flag=true;
		} catch (IllegalStateException e) {
			e.printStackTrace();
			flag=false;
		} catch (IOException e) {
			flag=false;
			e.printStackTrace();
		} 
		listMap = new HashMap<String,Object>(); 
		listMap.put("original_filename", ori);
		listMap.put("filename", dest.getName()); 
		list.add(listMap); 
			
		if(flag==false) {
			throw new FileException("파일 업데이트에 실패하였습니다.");
		}
		return list;
	}
	
	//파일 삭제
	public static void removeFile(String realPath) throws FileException{
		File file = new File(realPath);
		boolean result=file.delete();
		if(!result) {
			throw new FileException("삭제에 실패하였습니다.");
		}
	}
}
