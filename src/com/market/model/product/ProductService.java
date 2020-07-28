package com.market.model.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.market.domain.Product;
import com.market.exception.DMLException;

@Service
public class ProductService {
	@Autowired
	private ProductDAO productDAO;
	
	public List selectAll() {
		return productDAO.selectAll();
	}
	
	public void regist(Product product) throws DMLException{
		productDAO.insert(product);
	}
}
