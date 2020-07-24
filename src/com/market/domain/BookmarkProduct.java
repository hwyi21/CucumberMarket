package com.market.domain;

public class BookmarkProduct {
	private int bookmark_product_id;
	private Member member;
	private Product product;
	
	public int getBookmark_product_id() {
		return bookmark_product_id;
	}
	public void setBookmark_product_id(int bookmark_product_id) {
		this.bookmark_product_id = bookmark_product_id;
	}
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
	
}
