package com.market.controller.state;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.market.domain.Member;
import com.market.domain.Message;
import com.market.domain.OrderDetail;
import com.market.domain.State;
import com.market.model.product.ProductService;
import com.market.model.state.StateService;

@Controller
public class StateController {
	@Autowired
	private StateService stateService;

	@Autowired
	private ProductService productService;
	
	@RequestMapping(value = "/state/list", method = RequestMethod.GET, produces = "text/html;charset=utf8")
	@ResponseBody
	public String selectAll() {
		List<State> stateList = stateService.selectAll();
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"stateList\":[");
		for (int i = 0; i < stateList.size(); i++) {
			State state= stateList.get(i);
			sb.append("{");
			sb.append("\"state_id\":" + state.getState_id()+ ",");
			sb.append("\"state_title\":\"" + state.getState_title() + "\"");
			if (i < stateList.size() - 1) {
				sb.append("},");
			} else {
				sb.append("}");
			}
		}
		sb.append("]");
		sb.append("}");
		return sb.toString();
	}
	
	//거래 상태 바꾸기
	@RequestMapping(value="/state/update", method=RequestMethod.POST)
	@ResponseBody
	public String send(Message message, HttpServletRequest request, @RequestParam int product_id, @RequestParam int state_id) {
		HttpSession session=request.getSession();
		Member member = (Member)session.getAttribute("member");
		State state = stateService.select(state_id);
		OrderDetail orderDetail = productService.selectDetail(product_id);
		orderDetail.setState(state);
		stateService.updateState(orderDetail);
		
		String updateState = Integer.toString(state_id);
		return updateState;
	}
}
