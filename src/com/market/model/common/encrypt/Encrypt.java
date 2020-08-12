package com.market.model.common.encrypt;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import com.market.exception.DMLException;

public class Encrypt {
	
	public static String getEncrypt(String password, String salt) throws UnsupportedEncodingException{
		return getEncrypt(password, salt.getBytes("UTF-8"));
	}
	
	//비밀번호 암호화
	public static String getEncrypt(String password, byte[] salt) throws UnsupportedEncodingException{
		String result = "";
		
		byte[] a = password.getBytes("UTF-8");
		byte[] bytes = new byte[a.length + salt.length];
		
		System.arraycopy(a, 0, bytes, 0, a.length);
		System.arraycopy(salt, 0, bytes, a.length, salt.length);
		
		try {
			// 암호화 방식 지정
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(bytes);
			
			byte[] byteData = md.digest();
			
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xFF) + 256, 16).substring(1));
			}
			
			result = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// 난수 생성
	public static String generateSalt() {
		Random random = new Random();
		
		byte[] salt = new byte[8];
		random.nextBytes(salt);
		
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < salt.length; i++) {
			//문자 하나마다 매칭되는 16진수로 변환
			sb.append(String.format("%02x",salt[i]));
		}
		
		return sb.toString();
	}


}
