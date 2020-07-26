package com.market.model.Member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.market.domain.Member;
import com.market.exception.DMLException;

@Service
public class MemberService {
	@Autowired
	private MemberDAO memberDAO;

	//회원가입
	public void insert(Member member) throws DMLException {
		memberDAO.insert(member);
	}

	//로그인
	public Member loginCheck(Member member) throws DMLException {
		return memberDAO.loginCheck(member);
	}
}
