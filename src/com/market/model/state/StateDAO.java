package com.market.model.state;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.market.domain.OrderDetail;
import com.market.domain.State;
import com.market.exception.DMLException;

@Repository
public class StateDAO {
	@Autowired
	private SqlSessionTemplate sessionTemplate;
	
	public List selectAll() {
		return sessionTemplate.selectList("State.selectAll");
	}
	
	public State select(int state_id) {
		return sessionTemplate.selectOne("State.select", state_id);
	}
	
	public void updateState(OrderDetail orderDetail) throws DMLException{
		int result = sessionTemplate.update("OrderDetail.updateState", orderDetail);
		if(result==0) {
			throw new DMLException("수정되지 않았습니다");
		}
	}
}
