package com.market.model.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
	@Autowired
	private CategoryDAO categoryDAO;
	
	public List selectAll() {
		return categoryDAO.selectAll();
	}
}
