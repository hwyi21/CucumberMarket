package com.market.model.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.market.domain.Member;
import com.market.domain.OrderDetail;
import com.market.domain.Product;
import com.market.domain.ProductImage;
import com.market.exception.DMLException;
import com.market.exception.FileException;
import com.market.model.common.address.SearchAddress;
import com.market.model.common.file.FileManager;

@Service
public class ProductService {
	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private ProductImageDAO productImageDAO;
	
	@Autowired
	private OrderDetailDAO orderDetailDAO;

	public List selectAll() {
		return productDAO.selectAll();
	}

	public void insert(Product product, ProductImage productImage, OrderDetail orderDetail, HttpServletRequest request, String realPath)
			throws DMLException, FileException {
		productDAO.insert(product);
		productImage.setProduct(product);
		orderDetail.setProduct(product);
		List<Map<String,Object>> list = FileManager.saveFile(request, realPath);
		for (int i = 0; i < list.size(); i++) {
			String original_filename = (String) list.get(i).get("original_filename");
			String filename = (String) list.get(i).get("filename");
			productImage.setOriginal_filename(original_filename);
			productImage.setFilename(filename);
			productImageDAO.insert(productImage);
		}
		orderDetailDAO.insert(orderDetail);
	}

	
	// 로그인한 유저와 같은 지역의 상품 조회
	public List selectProduct(Member member) {
		String address = SearchAddress.address(member.getLocate());
		return orderDetailDAO.selectProduct(address);
	}
	

}
