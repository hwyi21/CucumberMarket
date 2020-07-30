package com.market.model.product;

import java.util.List;
import java.util.Map;

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
		List<Map<String,Object>> list = FileManager.saveFile(request, realPath);
		for (int i = 0; i < list.size(); i++) {
			String original_filename = (String) list.get(i).get("original_filename");
			String filename = (String) list.get(i).get("filename");
			productImage.setOriginal_filename(original_filename);
			productImage.setFilename(filename);
			productImageDAO.insert(productImage);
		}

	}

}
