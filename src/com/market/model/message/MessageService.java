package com.market.model.message;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.market.domain.Message;
import com.market.exception.DMLException;

@Service
public class MessageService {
	@Autowired
	private MessageDAO messageDAO;
	
	//최초 거래 관련 메시지 insert
	public void insertFirst(Message message) throws DMLException{
		messageDAO.insertFirst(message);
	}
	
	//거래관련 메시지 insert
	public void insert(Message message) throws DMLException{
		messageDAO.insertFirst(message);
	}
	
	//메세지 내용 조회
	public List selectMessage(Message message) {
		return messageDAO.selectMessage(message);
	}
	
	//메세지 목록 조회
	public List select(int member_id) {
		return messageDAO.select(member_id);
	}
}
