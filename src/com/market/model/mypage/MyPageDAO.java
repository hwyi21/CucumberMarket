package com.market.model.mypage;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MyPageDAO {
	@Autowired
	private SqlSessionTemplate sessionTemplate;

	//판매내역 조회
	public List selectSale(int member_id) {
		return sessionTemplate.selectList("OrderDetail.selectSale", member_id);
	}
	
	//구매내역 조회
	public List selectBuy(int member_id) {
		return sessionTemplate.selectList("OrderDetail.selectBuy", member_id);
	}
}
