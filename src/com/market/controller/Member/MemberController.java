package com.market.controller.Member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.market.domain.Member;
import com.market.exception.DMLException;
import com.market.model.Member.MemberService;

@Controller
public class MemberController {
	@Autowired
	private MemberService memberService;

	//ȸ������
	@RequestMapping(value = "/member/regist", method = RequestMethod.POST)
	public String regist(Model model, Member member) {
		memberService.insert(member);
		model.addAttribute("msg", "ȸ������ ����!");
		model.addAttribute("url", "/");

		return "view/message";
	}

	//�α���
	@RequestMapping(value="/member/login", method=RequestMethod.POST)
	public String loginCheck(Member member, HttpServletRequest request) {
		Member result = memberService.loginCheck(member);

		//�α��� ������ ���� ����
		HttpSession session=request.getSession();
		session.setAttribute("member", result); //ȸ�� ���� ����!
		
		return "redirect:/";
	}
	
	//�α׾ƿ�
	@RequestMapping(value = "/member/logout", method = RequestMethod.GET)
	public String logout(Model model, HttpSession session) {
		//���� Ŭ���̾�Ʈ�� ���õ� ���� ��ȿȭ
		session.invalidate(); 

		model.addAttribute("msg", "�α׾ƿ� �Ǿ����ϴ�.");
		model.addAttribute("url", "/");
		return "view/message";
	}

	// ���� ó��
	@ExceptionHandler(DMLException.class)
	public String handle(Model model, DMLException e) {
		model.addAttribute("e", e);// ���� ��ü ��ü�� ��´�!!
		model.addAttribute("msg", e.getMessage());// ���� ��ü ��ü�� ��´�!!
		return "view/error";
	}
}
