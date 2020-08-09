package com.market.model.state;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.market.domain.OrderDetail;
import com.market.domain.State;
import com.market.exception.DMLException;

@Service
public class StateService {
	@Autowired
	private StateDAO stateDAO;
	
	public List selectAll() {
		return stateDAO.selectAll();
	}
	
	public State select(int state_id) {
		return stateDAO.select(state_id);
	}
	
	//상품 거래 상태 업데이트
	public void updateState(OrderDetail orderDetail) throws DMLException{
		stateDAO.updateState(orderDetail);
	}
}
