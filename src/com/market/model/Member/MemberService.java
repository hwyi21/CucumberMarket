package com.market.model.member;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.market.domain.Member;
import com.market.exception.DMLException;
import com.market.model.common.encrypt.Encrypt;

@Service
public class MemberService {
	@Autowired
	private MemberDAO memberDAO;

	//회원가입
	public void insert(Member member) throws DMLException, UnsupportedEncodingException {
		//난수 발생
		String salt = Encrypt.generateSalt();
		member.setSalt(salt);
		
		//비밀번호 암호화
		String password = member.getPassword();
		password = Encrypt.getEncrypt(password, salt);
		member.setPassword(password);
		memberDAO.insert(member);
	}

	//로그인
	public Member loginCheck(Member member) throws DMLException, UnsupportedEncodingException {
		String id = member.getId();
		String salt = memberDAO.selectSalt(id);
		
		String password = member.getPassword();
		password = Encrypt.getEncrypt(password, salt);
		
		member.setPassword(password);
		
		return memberDAO.loginCheck(member);
	}
	
	public Member select(int member_id) {
		return memberDAO.select(member_id);
	}
}
