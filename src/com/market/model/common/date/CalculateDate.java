package com.market.model.common.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class CalculateDate {
	
	public static String cal(String s) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
		Date time = new Date();
		Date regdate= format.parse(s);
		String count = null;
		long diff = time.getTime() - regdate.getTime();
		long sec = diff / 1000;
		long min = diff / 60000;
		long hour= diff / 3600000;
		long after = diff/( 24*60*60*1000);
		after = Math.abs(after);
		
		if(sec<60) {
			if(sec==1) {
				count = "방금";
			}else {
				count = sec+"초 전";
			}
		}else if(sec>=60&&min<60) {
			count = min+"분 전";
		}else if(sec>=60&&min>59&&hour<24) {
			count=hour+"시간 전";
		}else if(sec>=60&&min>59&&hour>24&&after<3) {
			if(after==1) {
				count = "어제";
			}else if(after==2) {
				count = "그저께";
			}
		}else {
			count = s.substring(0,10);
		}
		return count;
	}
} 
