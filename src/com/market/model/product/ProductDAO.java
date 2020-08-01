package com.market.model.product;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.market.domain.Product;
import com.market.exception.DMLException;

@Repository
public class ProductDAO {
	@Autowired
	private SqlSessionTemplate sessionTemplate;
	
	// 모든 상품 조회
	public List selectAll() {
		return sessionTemplate.selectList("Product.selectAll");
	}
	
	// 로그인한 유저와 같은 지역의 상품 조회
	public List select(int product_id) {
		return sessionTemplate.selectList("Product.select", product_id);
	}
	
	public void insert(Product product) throws DMLException{
		int result = sessionTemplate.insert("Product.insert", product);
		if(result==0) {
			throw new DMLException("상품이 등록되지 않았습니다");
		}
	}
	
	
}
