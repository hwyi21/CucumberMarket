package com.market.model.Member;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.market.domain.Member;
import com.market.exception.DMLException;

@Repository
public class MemberDAO {
	@Autowired
	private SqlSessionTemplate sessionTemplate;
	
	public void insert (Member member) throws DMLException{
		int result=sessionTemplate.insert("Member.insert",member);
		if(result==0) {
	         throw new DMLException("ȸ�����Կ� �����Ͽ����ϴ�. �����ڿ� �����ϼ���");
	     }
		
	}
}
