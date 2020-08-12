package com.market.model.member;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.market.domain.Member;
import com.market.exception.DMLException;

@Repository
public class MemberDAO {
	@Autowired
	private SqlSessionTemplate sessionTemplate;
	
	// 회원가입
	public void insert(Member member) throws DMLException{
		int result = sessionTemplate.insert("Member.insert", member);
		if (result == 0) {
			throw new DMLException("회원가입에 실패하였습니다. 관리자에 문의하세요");
		}
	}
	
	//로그인
	public Member loginCheck(Member member) throws DMLException {
		Member obj = sessionTemplate.selectOne("Member.loginCheck", member);
		if (obj == null) {// 회원이 없을 경우, 비즈니스적 에러상황으로 간주
			throw new DMLException("로그인 정보가 올바르지 않습니다.");
		}
		return obj;
	}
	
	//난수 가져오기
	public String selectSalt(String id) throws DMLException {
		String salt = sessionTemplate.selectOne("Member.selectSalt", id);
		if(salt==null) {
			throw new DMLException("로그인 정보가 올바르지 않습니다.");
		}
		return salt;
	}
	
	public Member select(int member_id) {
		return sessionTemplate.selectOne("Member.select", member_id);
	}
}
