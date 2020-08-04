package com.market.model.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.market.domain.Member;
import com.market.domain.OrderDetail;
import com.market.domain.Product;
import com.market.domain.ProductImage;
import com.market.exception.DMLException;
import com.market.exception.FileException;
import com.market.model.common.address.SearchAddress;
import com.market.model.common.file.FileManager;

@Service
public class ProductService {
	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private ProductImageDAO productImageDAO;
	
	@Autowired
	private OrderDetailDAO orderDetailDAO;

	public List selectAll() {
		return productDAO.selectAll();
	}

	public void insert(Product product, ProductImage productImage, OrderDetail orderDetail, HttpServletRequest request, String realPath)
			throws DMLException, FileException {
		productDAO.insert(product);
		productImage.setProduct(product);
		orderDetail.setProduct(product);
		List<Map<String,Object>> list = FileManager.saveFile(request, realPath);
		for (int i = 0; i < list.size(); i++) {
			String original_filename = (String) list.get(i).get("original_filename");
			String filename = (String) list.get(i).get("filename");
			productImage.setOriginal_filename(original_filename);
			productImage.setFilename(filename);
			productImageDAO.insert(productImage);
		}
		orderDetailDAO.insert(orderDetail);
	}

	
	// 로그인한 유저와 같은 지역의 상품 조회
	public List selectProduct(Member member) {
		String address = SearchAddress.address(member.getLocate());
		return orderDetailDAO.selectProduct(address);
	}
	
	//상품 상세페이지 조회
	public OrderDetail selectDetail(int product_id) {
		return orderDetailDAO.select(product_id);
	}
	
	//상품 삭제 및 판매자가 상품 삭제 시 Order_Detail 테이블의 판매자 정보 및 상품 정보 업데이트 / 구매자가 없는 경우 order_detil 테이블의 내역 삭제
	public void delete(int product_id, HttpServletRequest request) throws DMLException{
		productDAO.delete(product_id);
		OrderDetail orderDetail=orderDetailDAO.select(product_id);
		int buyer_id=orderDetail.getBuyer_id();
		if(buyer_id==0) {
			orderDetailDAO.delete(product_id);
		}else {
			orderDetailDAO.update(product_id);
		}
		List list = productImageDAO.selectAll(product_id);
		for(int i=0; i<list.size();i++) {
			ProductImage productImage = (ProductImage) list.get(i);
			String filename = productImage.getFilename();
			String realPath=request.getServletContext().getRealPath("/data/"+filename);
			FileManager.removeFile(realPath);
		}
		productImageDAO.delete(product_id);
	}

}
