package com.market.model.mypage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyPageService {
	@Autowired
	private MyPageDAO myPageDAO;
	
	//판매내역 조회
	public List selectSale(int member_id) {
		return myPageDAO.selectSale(member_id);
	}
	
	//구매내역 조회
	public List selectBuy(int member_id) {
		return myPageDAO.selectBuy(member_id);
	}
}
