package com.market.controller.mypage;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.market.controller.common.Pager;
import com.market.domain.Member;
import com.market.domain.OrderDetail;
import com.market.model.mypage.MyPageService;
import com.market.model.product.ProductImageService;

@Controller
public class MypageController {
	@Autowired 
	private MyPageService myPageService;
	
	@Autowired 
	private ProductImageService productImageService;
	
	@Autowired
	private Pager pager;
	
	@RequestMapping(value="/mypage/main", method=RequestMethod.GET)
	public String test(HttpServletRequest request) {
		return "mypage/main";
	}
	
	//판매 내역 조회
	@RequestMapping(value="/mypage/sale", method = RequestMethod.GET)
	public String selectSale(Model model, HttpServletRequest request) {
		HttpSession session=request.getSession();
		Member member = (Member)session.getAttribute("member");
		int member_id = member.getMember_id();
		
		List productList = myPageService.selectSale(member_id);
		List productImageList = new ArrayList();
		for(int i=0; i<productList.size();i++) {
			OrderDetail product = (OrderDetail) productList.get(i);
			int product_id=product.getProduct().getProduct_id();
			if(product_id==0) {
				continue;
			}else {
				List list = productImageService.selectAll(product_id);
				productImageList.add(list);
			}
		}
		//페이징 처리 객체 
		pager.init(productList, request);
		model.addAttribute("productList", productList);
		model.addAttribute("productImageList", productImageList);
		model.addAttribute("pager", pager);
		
		return "mypage/saleList";
	}
	
	//구매 내역 조회
	@RequestMapping(value="/mypage/buy", method = RequestMethod.GET)
	public String selectBuy(Model model, HttpServletRequest request) {
		HttpSession session=request.getSession();
		Member member = (Member)session.getAttribute("member");
		int member_id = member.getMember_id();
		
		List productList = myPageService.selectBuy(member_id);
		List productImageList = new ArrayList();
		for(int i=0; i<productList.size();i++) {
			OrderDetail product = (OrderDetail) productList.get(i);
			int product_id=product.getProduct().getProduct_id();
			if(product_id==0) {
				continue;
			}else {
				List list = productImageService.selectAll(product_id);
				productImageList.add(list);
			}
		}
		//페이징 처리 객체 
		pager.init(productList, request);
		model.addAttribute("productList", productList);
		model.addAttribute("productImageList", productImageList);
		model.addAttribute("pager", pager);
		
		return "mypage/buyList";
	}

}
