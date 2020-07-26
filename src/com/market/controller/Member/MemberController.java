package com.market.controller.Member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.market.domain.Member;
import com.market.exception.DMLException;
import com.market.model.Member.MemberService;

@Controller
public class MemberController {
	@Autowired
	private MemberService memberService;

	@RequestMapping(value = "/member/regist", method = RequestMethod.POST)
	public String regist(Model model, Member member) {

		memberService.insert(member);
		model.addAttribute("msg", "회원가입 성공!");
		model.addAttribute("url", "/");

		return "view/message";
	}

	// 예외 처리
	@ExceptionHandler(DMLException.class)
	public String handle(Model model, DMLException e) {
		model.addAttribute("e", e);// 에러 객체 자체를 담는다!!
		model.addAttribute("msg", e.getMessage());// 에러 객체 자체를 담는다!!
		return "view/error";
	}
}
