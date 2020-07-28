package com.market.model.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.market.domain.ProductImage;
import com.market.exception.DMLException;
import com.market.exception.FileException;
import com.market.model.common.file.FileManager;

@Service
public class ProductImageService {
	@Autowired
	private ProductImageDAO productImageDAO;
	
	public void regist(ProductImage productImage,MultipartFile myFile, String realPath) throws DMLException, FileException{
		
		String filename=FileManager.saveFile(myFile, realPath);
		productImage.setFilename(filename);
		productImageDAO.insert(productImage);
	}
}
