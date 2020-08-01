package com.market.model.common.address;

public class SearchAddress {
	public static String address(String address){
		int realLoc=address.lastIndexOf(" ");
		String result=address.substring(0,realLoc);
		return result;
	}
}
