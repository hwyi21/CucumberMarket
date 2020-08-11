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
	/*
	public List selectAll() {
		return sessionTemplate.selectList("Product.selectAll");
	}*/
	
	// 상품 한건 가져오기
	public Product select(int product_id) { 
		return sessionTemplate.selectOne("Product.select", product_id); 
	}
	 

	
	public void insert(Product product) throws DMLException{
		int result = sessionTemplate.insert("Product.insert", product);
		if(result==0) {
			throw new DMLException("상품이 등록되지 않았습니다");
		}
	}
	
	//상품 삭제
	public void delete(int product_id) throws DMLException{
		int result = sessionTemplate.delete("Product.delete", product_id);
		if(result==0) {
			throw new DMLException("상품이 삭제 되지 않았습니다.");
		}
	}
	
	//상품 업데이트
	public void update(Product product) throws DMLException{
		int result = sessionTemplate.delete("Product.update", product);
		if(result==0) {
			throw new DMLException("상품이 수정 되지 않았습니다.");
		}
	}
	
	
}
