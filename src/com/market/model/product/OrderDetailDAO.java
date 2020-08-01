package com.market.model.product;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.market.domain.OrderDetail;
import com.market.exception.DMLException;

@Repository
public class OrderDetailDAO {
	@Autowired
	private SqlSessionTemplate sessionTemplate;
	
	public void insert(OrderDetail orderDetail) throws DMLException{
		int result = sessionTemplate.insert("OrderDetail.insert", orderDetail);
		if(result==0) {
			throw new DMLException("상품이 등록되지 않았습니다");
		}
	}
	
	public List selectProduct(String address) {
		return sessionTemplate.selectList("OrderDetail.selectProduct", address);
	}
}
