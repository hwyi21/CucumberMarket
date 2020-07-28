package com.market.controller.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.market.domain.Category;
import com.market.model.category.CategoryService;

@Controller
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	@RequestMapping(value = "/category/list", method = RequestMethod.GET, produces = "text/html;charset=utf8")
	@ResponseBody
	public String selectAll() {
		List<Category> categoryList = categoryService.selectAll();
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"categoryList\":[");
		for (int i = 0; i < categoryList.size(); i++) {
			Category category = categoryList.get(i);
			sb.append("{");
			sb.append("\"category_id\":" + category.getCategory_id() + ",");
			sb.append("\"category_name\":\"" + category.getCategory_name() + "\"");
			if (i < categoryList.size() - 1) {
				sb.append("},");
			} else {
				sb.append("}");
			}
		}
		sb.append("]");
		sb.append("}");
		return sb.toString();
	}
}
