package com.market.controller.category;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Test {
	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
		Date time = new Date();
		SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss", Locale.KOREA);
		Date regdate= format.parse("2020-08-13 11:52:41");
		long diff = time.getTime() - regdate.getTime();
		long sec = diff / 1000;
		long min = diff / 60000;
		long hour= diff / 3600000;
		if(sec<60) {
			System.out.println(sec+"초");
		}else if(sec>=60&&min<60) {
			System.out.println(min+"분");
		}else if(sec>=60&&min>59) {
			System.out.println(hour+"시간");
		}
		
	}
} 
