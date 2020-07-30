package com.market.controller.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.market.model.category.CategoryService;

@Controller
public class MainController {
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String test(Model model, HttpServletRequest request) {
		List categoryList = categoryService.selectAll();
		model.addAttribute("categoryList", categoryList);
		return "main";
	}
}

