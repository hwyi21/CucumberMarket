package com.market.model.message;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.market.domain.Message;
import com.market.exception.DMLException;

@Repository
public class MessageDAO {
	@Autowired
	SqlSessionTemplate sessionTemplate;
	
	//최초 거래 관련 메시지 insert
	public void insertFirst(Message message) throws DMLException{
		int result = sessionTemplate.insert("Message.insertFirst", message);
		if(result==0) {
			throw new DMLException("메시지 전송에 실패했습니다.");
		}
	}
	
	//거래관련 메시지 insert
	public void insert(Message message) throws DMLException{
		int result = sessionTemplate.insert("Message.insert", message);
		if(result==0) {
			throw new DMLException("메시지 전송에 실패했습니다.");
		}
	}
	
	//메세지 내용 조회
	public List selectMessage(Message message) {
		return sessionTemplate.selectList("Message.selectMessage", message);
	}
	
	public List selectBuyer(int product_id) {
		return sessionTemplate.selectList("Message.selectBuyer", product_id);
	}
	
	//메세지 목록 조회
	public List select(int member_id) {
		return sessionTemplate.selectList("Message.select", member_id);
	}
	
}
