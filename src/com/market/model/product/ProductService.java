package com.market.model.product;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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

	/*
	public List selectAll() {
		return productDAO.selectAll();
	}
*/
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
	
	//상품 한건 조회
	public Product select(int product_id) {
		return productDAO.select(product_id);
	}
	
	public OrderDetail selectJoin(int product_id) {
		return orderDetailDAO.selectJoin(product_id);
	}
	
	//카테고리별 상품 조회
	public List selectProductByCategory(int category_id) {
		return orderDetailDAO.selectProductByCategory(category_id);
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
	
	public void updateBuyer(OrderDetail orderDetail) {
		orderDetailDAO.updateBuyer(orderDetail);
	}
	
	public void update(Product product, ProductImage productImage, HttpServletRequest request)throws DMLException, FileException {
		productDAO.update(product);
		productImage.setProduct(product);
		List list = productImageDAO.selectAll(product.getProduct_id());
		String realPath=request.getServletContext().getRealPath("/data/");
		
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
		Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
		int i=0; //while문을 도는 횟수
		int imageSize = list.size(); //디비에 저장되어 있는 해당 상품 관련 이미지 저장 파일 크기
		
		while(iterator.hasNext()) {
			MultipartFile multipartFile = multipartHttpServletRequest.getFile(iterator.next());
			String string = multipartFile.getName();
			int j =  Integer.parseInt(string.substring(string.length() - 1)); //filename_j 
			String ori = multipartFile.getOriginalFilename();
			if(imageSize-1>=i) { 
				if(i==j) { //j와 while문을 돈 횟수가 같다면
					if(ori.isEmpty()==false) { //원래 있던 파일이 삭제되고 다른 이미지가 들어간것으로 간주
						ProductImage img = (ProductImage) list.get(i);
						productImageDAO.deleteImg(img.getImage_id());
						List<Map<String,Object>> save = FileManager.saveOneFile(multipartFile, ori, realPath);
						String original_filename = (String) save.get(0).get("original_filename");
						String filename = (String) save.get(0).get("filename");
						productImage.setOriginal_filename(original_filename);
						productImage.setFilename(filename);
						productImageDAO.insert(productImage);
					}
				}else if(i!=j){//j와 while문을 돈 횟수가 같지 않다
					if(ori.isEmpty()) { 
						// 넘어온 파일명이 널값이라면 원래 i에 있던 파일은 삭제된 이미지로 간주 
						// j번째 파일은 새로 등록
						ProductImage image = (ProductImage) list.get(i);
						productImageDAO.deleteImg(image.getImage_id());
						ProductImage img = (ProductImage)list.get(j);
						productImage.setOriginal_filename(img.getOriginal_filename());
						productImage.setFilename(img.getFilename());
						productImageDAO.insert(productImage);
					}else { //새롭게 등록된 파일
						ProductImage img = (ProductImage) list.get(i);
						productImageDAO.deleteImg(img.getImage_id());
						List<Map<String,Object>> save = FileManager.saveOneFile(multipartFile, ori, realPath);
						String original_filename = (String) save.get(0).get("original_filename");
						String filename = (String) save.get(0).get("filename");
						productImage.setOriginal_filename(original_filename);
						productImage.setFilename(filename);
						productImageDAO.insert(productImage);
					}
				}
			}else if(imageSize<=i) { // 새롭게 등록한 이미지 파일 insert
				List<Map<String,Object>> save = FileManager.saveOneFile(multipartFile, ori, realPath);
				String original_filename = (String) save.get(0).get("original_filename");
				String filename = (String) save.get(0).get("filename");
				productImage.setOriginal_filename(original_filename);
				productImage.setFilename(filename);
				productImageDAO.insert(productImage);
			}
			i++;
		}
		if(imageSize-i>0) { //디비에 저장되어 있는 imageSize보다 while문을 실행한 횟수가 작거나 같다면 i번째 부터 디비에 남아있는 나머지 데이터를 지우기
			for(int j=i; j<imageSize;j++) {
				ProductImage img = (ProductImage)list.get(j);
				productImageDAO.deleteImg(img.getImage_id());
			}
		}
		
		//이미지 파일 삭제
		Map<String, String[]> map = request.getParameterMap();
		String[] obj = map.get("del_file");
		if(obj!=null) {
			for(int j=0; j<obj.length; j++) {
				FileManager.removeFile(realPath+obj[j]);
			}
		}
		
	}

}
