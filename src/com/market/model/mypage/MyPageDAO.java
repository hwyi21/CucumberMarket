package com.market.model.mypage;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.market.domain.BookmarkProduct;
import com.market.exception.DMLException;

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
	
	//관심상품 조회
	public List selectMyBookmark(int member_id) {
		return sessionTemplate.selectList("BookmarkProduct.selectAll", member_id);
	}
	
	//상품별 관심상품 등록 갯수 조회
	public int countBookmark(int product_id) {
		return sessionTemplate.selectOne("BookmarkProduct.countBookmark", product_id);
	}
	
	//관심상품 등록
	public void insertBookmarkProduct(BookmarkProduct bookmarkProduct) throws DMLException{
		int result = sessionTemplate.insert("BookmarkProduct.insert", bookmarkProduct);
		if(result==0) {
			throw new DMLException("관심 상품 등록에 실패했습니다");
		}
	}
	
	//관심상품 등록 여부 조회
	public BookmarkProduct select(Map map) throws DMLException{
		return sessionTemplate.selectOne("BookmarkProduct.select", map);
	}
	
	public void delete(BookmarkProduct bookmarkProduct) throws DMLException{
		int result = sessionTemplate.delete("BookmarkProduct.delete", bookmarkProduct);
		if(result==0) {
			throw new DMLException("관심 상품 해제에 실패했습니다");
		}
	}
}
