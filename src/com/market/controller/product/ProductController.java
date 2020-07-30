package com.market.controller.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.market.domain.Product;
import com.market.domain.ProductImage;
import com.market.model.product.ProductService;

@Controller
public class ProductController {
	@Autowired
	private ProductService productService;

	//상품 등록 페이지
	@RequestMapping(value = "/product/registForm", method = RequestMethod.GET)
	public String registForm(HttpServletRequest request) {
		return "product/registForm";
	}
	
	//상품 등록
	@RequestMapping(value = "/product/regist", method = RequestMethod.POST)
	public ModelAndView regist(Product product, ProductImage productImage, HttpServletRequest request) {
		HttpSession session=request.getSession();
		productService.insert(product, productImage, request, request.getServletContext().getRealPath("/data/"));

		ModelAndView mav = new ModelAndView();
		mav.addObject("url", "/");
		mav.addObject("msg", "등록 성공");
		mav.setViewName("view/message");

		return mav;
	}

}
