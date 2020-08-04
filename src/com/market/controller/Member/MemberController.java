package com.market.controller.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.market.domain.Member;
import com.market.domain.Message;
import com.market.domain.OrderDetail;
import com.market.model.member.MemberService;

@Controller
public class MemberController {
	@Autowired
	private MemberService memberService;

	//회원가입
	@RequestMapping(value = "/member/regist", method = RequestMethod.POST)
	public String regist(Model model, Member member) {
		memberService.insert(member);
		model.addAttribute("msg", "회원가입 성공!");
		model.addAttribute("url", "/member/loginForm.jsp");

		return "view/message";
	}

	//로그인
	@RequestMapping(value="/member/login", method=RequestMethod.POST)
	public String loginCheck(Member member, HttpServletRequest request) {
		Member obj = memberService.loginCheck(member);

		//로그인 성공시 세션 유지
		HttpSession session=request.getSession();
		session.setAttribute("member", obj); //회원 정보 저장!
		
		return "redirect:/";
	}
	
	//로그아웃
	@RequestMapping(value = "/member/logout", method = RequestMethod.GET)
	public String logout(Model model, HttpSession session) {
		//현재 클라이언트와 관련된 세션 무효화
		session.invalidate(); 

		model.addAttribute("msg", "로그아웃 되었습니다.");
		model.addAttribute("url", "/");
		return "view/message";
	}
	
	@RequestMapping(value="/chat/info", method=RequestMethod.POST)
	@ResponseBody
	public String getChatInfo(HttpServletRequest request, @RequestParam int member_id) {
		HttpSession session=request.getSession();
		Member member = (Member)session.getAttribute("member");
		
		Member chatInfo = memberService.select(member_id);
		String id = chatInfo.getId();
		
		return id;
	}

}
