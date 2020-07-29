package com.market.model.product;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.market.domain.Product;
import com.market.domain.ProductImage;
import com.market.exception.DMLException;
import com.market.exception.FileException;
import com.market.model.common.file.FileManager;

@Service
public class ProductService {
	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private ProductImageDAO productImageDAO;

	public List selectAll() {
		return productDAO.selectAll();
	}

	public void insert(Product product, ProductImage productImage, HttpServletRequest request, String realPath)
			throws DMLException, FileException {
		productDAO.insert(product);
		productImage.setProduct(product);
		List newFileName = FileManager.saveFile(request, realPath);
		for (int i = 0; i < newFileName.size(); i++) {
			String filename = (String) newFileName.get(i);
			productImage.setFilename(filename);
			productImageDAO.insert(productImage);
		}

	}

}
