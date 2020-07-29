package com.market.model.product;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.market.domain.ProductImage;
import com.market.exception.DMLException;
import com.market.exception.FileException;
import com.market.model.common.file.FileManager;

@Service
public class ProductImageService {
	@Autowired
	private ProductImageDAO productImageDAO;
	
	public void regist(ProductImage productImage,HttpServletRequest request, String realPath) throws DMLException, FileException{
		
		List newFileName=FileManager.saveFile(request, realPath);
		
		for(int i=0; i<newFileName.size();i++) {
			String filename = (String) newFileName.get(i);
			productImage.setFilename(filename);
			productImageDAO.insert(productImage);
		}
		
	}
}
