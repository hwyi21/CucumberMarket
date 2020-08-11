package com.market.model.mypage;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.market.domain.BookmarkProduct;
import com.market.exception.DMLException;

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
	
	//관심상품 등록
	public void insertBookmarkProduct(BookmarkProduct bookmarkProduct) throws DMLException{
		myPageDAO.insertBookmarkProduct(bookmarkProduct);
	}
	
	//관심상품 등록 여부 확인
	public BookmarkProduct select(Map map) {
		return myPageDAO.select(map);
	}
	
	//관심상품 삭제
	public void delete(BookmarkProduct bookmarkProduct) throws DMLException{
		myPageDAO.delete(bookmarkProduct);
	}
	
	//관심상품 조회
	public List selectMyBookmark(int member_id) {
		return myPageDAO.selectMyBookmark(member_id);
	}
}
