package com.market.model.product;

import java.util.List;

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
	
	//상품에 등록된 모든 이미지 조회
	public List selectAll(int product_id) {
		return sessionTemplate.selectList("ProductImage.selectAll", product_id);
	}
	
	//상품 이미지 삭제
	public void delete(int product_id) throws DMLException{
		int result = sessionTemplate.delete("ProductImage.delete", product_id);
		if(result==0) {
			throw new DMLException("상품이 이미지가 삭제 되지 않았습니다.");
		}
	}
	
	//상품 업데이트시 이미지 삭제
	public void deleteImg(int image_id) throws DMLException{
		int result = sessionTemplate.delete("ProductImage.deleteImg", image_id);
		if(result==0) {
			throw new DMLException("상품이 이미지가 업데이트 되지 않았습니다.");
		}
	}
	
	public void update(int image_id) throws DMLException{
		int result = sessionTemplate.delete("ProductImage.update", image_id);
		if(result==0) {
			throw new DMLException("상품이 이미지가 업데이트 되지 않았습니다.");
		}
	}
	
	//상품 업데이트
	public void update(ProductImage productImage) throws DMLException{
		int result = sessionTemplate.delete("ProductImage.update", productImage);
		if(result==0) {
			throw new DMLException("상품이 수정 되지 않았습니다.");
		}
	}
}
