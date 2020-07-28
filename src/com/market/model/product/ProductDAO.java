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
	
	public List selectAll() {
		return sessionTemplate.selectList("Product.selectAll");
	}
	
	public void insert(Product product) throws DMLException{
		int result = sessionTemplate.insert("Product.insert", product);
		if(result==0) {
			throw new DMLException("상품이 등록되지 않았습니다");
		}
	}
}
