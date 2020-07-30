package com.market.controller.common;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.market.exception.DMLException;
import com.market.exception.FileException;
import com.market.exception.MemberAuthException;

@ControllerAdvice
public class GlobalExceptionHandler {
	// 로그인하지 않은 경우
	@ExceptionHandler(MemberAuthException.class)
	public String handleException(MemberAuthException e, Model model) {
		model.addAttribute("msg", e.getMessage());
		model.addAttribute("url", "/member/loginForm.jsp");
		return "view/message";
	}

	// DML예외 처리
	@ExceptionHandler(DMLException.class)
	public String handleException(DMLException e, Model model) {
		model.addAttribute("msg", e.getMessage());
		return "view/error";
	}

	// 파일 예외 처리
	@ExceptionHandler(FileException.class)
	public String handleException(FileException e, Model model) {
		model.addAttribute("msg", e.getMessage());
		return "view/error";
	}

}
