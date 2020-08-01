package com.market.controller.product;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.market.controller.common.Pager;
import com.market.domain.Member;
import com.market.domain.OrderDetail;
import com.market.domain.Product;
import com.market.domain.ProductImage;
import com.market.model.product.ProductImageService;
import com.market.model.product.ProductService;

@Controller
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@Autowired 
	private ProductImageService productImageService;
	
	@Autowired
	private Pager pager;

	//상품 등록 페이지
	@RequestMapping(value = "/product/registForm", method = RequestMethod.GET)
	public String registForm(HttpServletRequest request) {
		return "product/registForm";
	}
	
	//상품 등록
	@RequestMapping(value = "/product/regist", method = RequestMethod.POST)
	public ModelAndView regist(Product product, ProductImage productImage, OrderDetail orderDetail, HttpServletRequest request) {
		HttpSession session=request.getSession();
		//주문요약 정보 중 누가 샀는지를 결정!!
		Member member = (Member)session.getAttribute("member");
		orderDetail.setMember(member);
		productService.insert(product, productImage, orderDetail, request, request.getServletContext().getRealPath("/data/"));
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("url", "/");
		mav.addObject("msg", "등록 성공");
		mav.setViewName("view/message");

		return mav;
	}
	
	//로그인한 회원인 경우 같은 동네의 중고 상품 조회
	@RequestMapping(value="/product", method = RequestMethod.GET)
	public ModelAndView selectMyArea(HttpServletRequest request) {
		HttpSession session=request.getSession();
		Member member = (Member)session.getAttribute("member");
		ModelAndView mav = new ModelAndView();
		
		List productList = productService.selectProduct(member);
		List productImageList = new ArrayList();
		for(int i=0; i<productList.size();i++) {
			OrderDetail product = (OrderDetail) productList.get(i);
			int product_id=product.getProduct().getProduct_id();
			List list = productImageService.selectAll(product_id);
			productImageList.add(list);
		}
		//페이징 처리 객체 
		pager.init(productList, request);
		mav.addObject("productList", productList);
		mav.addObject("productImageList", productImageList);
		mav.addObject("pager", pager);
		
		mav.setViewName("product/main");
		return mav;
	}

}
