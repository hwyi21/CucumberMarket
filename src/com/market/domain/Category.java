package com.market.domain;

import org.springframework.web.multipart.MultipartFile;

public class Category {
	private int category_id;
	private String category_name;
	private String category_image;
	private MultipartFile myFile;
	
	public int getCategory_id() {
		return category_id;
	}
	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}
	public String getCategory_name() {
		return category_name;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	public String getCategory_image() {
		return category_image;
	}
	public void setCategory_image(String category_image) {
		this.category_image = category_image;
	}
	public MultipartFile getMyFile() {
		return myFile;
	}
	public void setMyFile(MultipartFile myFile) {
		this.myFile = myFile;
	}
	
	
	
}
