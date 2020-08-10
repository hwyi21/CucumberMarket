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
	
	// 로그인한 유저와 같은 지역의 상품 조회
	public List selectProduct(String address) {
		return sessionTemplate.selectList("OrderDetail.selectProduct", address);
	}
	
	public List selectProductByCategory(int category_id) {
		return sessionTemplate.selectList("OrderDetail.selectProductByCategory", category_id);
	}
	
	//상품 상세 페이지 조회
	public OrderDetail select(int product_id) {
		return sessionTemplate.selectOne("OrderDetail.select", product_id);
	}
	
	//상품 삭제 및 판매자가 상품 삭제 시 Order_Detail 테이블의 판매자 정보 및 상품 정보 업데이트
	public void update(int product_id) throws DMLException{
		int result = sessionTemplate.update("OrderDetail.updateSaler", product_id);
		if(result==0) {
			throw new DMLException("상품이 삭제되지 않았습니다.");
		}
	}
	
	//거래 완료시 구매자 업데이트
	public void updateBuyer(OrderDetail orderDetail) throws DMLException{
		int result = sessionTemplate.update("OrderDetail.updateBuyer", orderDetail);
		if(result==0) {
			throw new DMLException("구매자가 선택되지 않았습니다.");
		}
	}
		
	public void delete(int product_id) throws DMLException{
		int result = sessionTemplate.delete("OrderDetail.delete", product_id);
		if(result==0) {
			throw new DMLException("상품이 삭제 되지 않았습니다.");
		}
	}
}
