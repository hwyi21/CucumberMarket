package com.market.model.product;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.market.domain.ProductImage;
import com.market.exception.DMLException;

@Repository
public class ProductImageDAO {
	@Autowired
	SqlSessionTemplate sessionTemplate;
	
	public void insert(ProductImage productImage) throws DMLException{
		int result = sessionTemplate.insert("ProductImage.insert", productImage);
		if(result==0) {
			throw new DMLException("상품 이미지가 등록되지 않았습니다");
		}
	}
}
