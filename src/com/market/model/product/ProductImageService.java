package com.market.model.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductImageService {
	@Autowired
	private ProductImageDAO productImageDAO;
	
	//상품의 등록된 이미지 조회
	public List selectAll(int product_id) {
		return productImageDAO.selectAll(product_id);
	}

}
