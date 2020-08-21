package com.market.controller.product;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.market.controller.common.Pager;
import com.market.domain.BookmarkProduct;
import com.market.domain.Member;
import com.market.domain.OrderDetail;
import com.market.domain.Product;
import com.market.domain.ProductImage;
import com.market.model.mypage.MyPageService;
import com.market.model.product.ProductImageService;
import com.market.model.product.ProductService;

@Controller
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@Autowired 
	private ProductImageService productImageService;
	
	@Autowired
	private MyPageService myPageService;
	
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
		Member member = (Member)session.getAttribute("member");
		orderDetail.setMember(member);
		productService.insert(product, productImage, orderDetail, request, request.getServletContext().getRealPath("/data/"));
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("url", "/product");
		mav.addObject("msg", "상품이 등록되었습니다.");
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
			if(product_id==0) {
				continue;
			}else {
				List list = productImageService.selectAll(product_id);
				productImageList.add(list);
			}
		}
		//페이징 처리 객체 
		pager.init(productList, request);
		mav.addObject("productList", productList);
		mav.addObject("productImageList", productImageList);
		mav.addObject("pager", pager);
		
		mav.setViewName("product/main");
		return mav;
	}
	
	//카테고리 별 상품 모아보기
	@RequestMapping(value="/product/category", method = RequestMethod.GET)
	public String selectCategoryProduct(Model model, HttpServletRequest request, @RequestParam int category_id) {
		List productList = productService.selectProductByCategory(category_id);
		List productImageList = new ArrayList();
		String category = request.getParameter("category_name");
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
		model.addAttribute("category", category);
		model.addAttribute("category_id", category_id);
		model.addAttribute("pager", pager);
		
		return "product/categoryMain";
	}
	
	//상품 상세 페이지
	@RequestMapping(value="/product/detail", method = RequestMethod.GET)
	public String productDetail(Model model, HttpServletRequest request, @RequestParam int product_id) {
		HttpSession session=request.getSession();
		Member member = (Member)session.getAttribute("member");
		Map map = new HashMap<String, Object>();
		String referer = request.getHeader("referer");
		String getUri = null;
		try {
			URI uri = new URI(referer);
			getUri = uri.getPath();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		OrderDetail orderDetail = productService.selectDetail(product_id);
		Product product = orderDetail.getProduct();
		Member saler = orderDetail.getMember();
		//상품별 관심상품 등록 갯수 조회
		int count = myPageService.countBookmark(product_id);
		List productImageList = productImageService.selectAll(product_id);
		if(member!=null) {
			map.put("member", member);
			map.put("product", product);
			BookmarkProduct bookmarkProduct = myPageService.select(map);
			model.addAttribute("bookmarkProduct", bookmarkProduct);
		}
		
		model.addAttribute("product", product);
		model.addAttribute("orderDetail", orderDetail);
		model.addAttribute("saler", saler);
		model.addAttribute("count", count);
		model.addAttribute("getUri", getUri);
		
		model.addAttribute("productImageList", productImageList);
		return "product/detail";
	}
	
	//상품 삭제 페이지
	@RequestMapping(value="/product/delete", method= RequestMethod.POST)
	public String productDelete(Model model, HttpServletRequest request, @RequestParam int product_id) {
		String realPath=request.getServletContext().getRealPath("/data/");
		productService.delete(product_id, request);
		
		model.addAttribute("url", "/product");
		model.addAttribute("msg", "상품이 삭제 되었습니다.");
		return "view/message";
	}
	
	//상품 수정 페이지
	@RequestMapping(value="/product/updateForm", method = RequestMethod.GET)
	public String updateForm(Model model, HttpServletRequest request, @RequestParam int product_id) {
		OrderDetail orderDetail = productService.selectDetail(product_id);
		Product product = orderDetail.getProduct();
		List productImageList = productImageService.selectAll(product_id);
		
		String referer = request.getHeader("referer");
		String getUri = null;
		try {
			URI uri = new URI(referer);
			getUri = uri.getPath();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		model.addAttribute("product", product);
		model.addAttribute("productImageList", productImageList);
		model.addAttribute("getUri", getUri);
		return "product/updateForm";
	}
	
	//상품 수정처리
	@RequestMapping(value="/product/update", method = RequestMethod.POST)
	public String updateProduct(Model model, HttpServletRequest request, Product product, ProductImage productImage) {
		product.setRe_regdate(request.getParameter("re_regdate"));
		//System.out.println(request.getParameter("re_regdate")+"끌올"); //선택on 선택x false
		productService.update(product, productImage, request);
		
		model.addAttribute("msg", "상품이 업데이트 되었습니다.");
		model.addAttribute("url", "/product/detail?product_id="+product.getProduct_id());
		return "view/message";
	}
	
	//Order_detail 테이블 구매자 업데이트
	@RequestMapping(value="/success", method = RequestMethod.GET)
	public String updateBuyer(Model model, HttpServletRequest request, OrderDetail orderDetail, @RequestParam int product_id, @RequestParam int buyer_id) {
		orderDetail = productService.selectDetail(product_id);
		orderDetail.setProduct(orderDetail.getProduct());
		orderDetail.setBuyer_id(buyer_id);
		
		productService.updateBuyer(orderDetail);
		model.addAttribute("msg", "거래가 완료되었습니다.");
		model.addAttribute("url", "/product/detail?product_id="+product_id);
		return "view/message";
	}
}