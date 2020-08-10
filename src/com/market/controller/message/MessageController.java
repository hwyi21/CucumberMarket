package com.market.controller.message;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.market.controller.common.Pager;
import com.market.domain.Member;
import com.market.domain.Message;
import com.market.domain.OrderDetail;
import com.market.domain.Product;
import com.market.model.message.MessageService;
import com.market.model.product.ProductService;

@Controller
public class MessageController {
	@Autowired
	private ProductService productService;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private Pager pager;
	
	//대화 폼 불러오기
	@RequestMapping(value="/chat", method=RequestMethod.GET)
	public String messageForm(Model model, HttpServletRequest request, @RequestParam int product_id, @RequestParam int team) {
		HttpSession session=request.getSession();
		Member member = (Member)session.getAttribute("member");
		String referer = request.getHeader("referer");
		String getUri = null;
		try {
			URI uri = new URI(referer);
			getUri = uri.getPath();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		OrderDetail orderDetail = productService.selectDetail(product_id);
		if(team!=0) {
			Product product = orderDetail.getProduct();
			Message message = new Message();
			message.setProduct(product);
			message.setTeam(team);
			List messageList = messageService.selectMessage(message);
			model.addAttribute("messageList", messageList);
		}
		model.addAttribute("orderDetail", orderDetail);
		model.addAttribute("getUri", getUri);
		return "message/messageForm";
	}
	
	//대화 내역 등록
	@RequestMapping(value="/chat/send", method=RequestMethod.POST)
	@ResponseBody
	public String send(Message message, HttpServletRequest request, @RequestParam int product_id, @RequestParam int member_id) {
		HttpSession session=request.getSession();
		Member member = (Member)session.getAttribute("member");
		OrderDetail orderDetail= productService.selectDetail(product_id);
		message.setMember(orderDetail.getMember());
		message.setProduct(orderDetail.getProduct());
		String team =null;
		if(message.getTeam()==0) {
			messageService.insertFirst(message);
			team = Integer.toString(message.getMessage_id());
		}else {
			messageService.insert(message);
			team = Integer.toString(message.getTeam());
		}
		return team;
	}
	
	//메세지 내용 조회
	@RequestMapping(value="/chat/get", method=RequestMethod.GET, produces="text/html;charset=utf8")
	@ResponseBody
	public String selectAll(Message message, HttpServletRequest request, @RequestParam int product_id, @RequestParam String team) {
		HttpSession session=request.getSession();
		Member member = (Member)session.getAttribute("member");
		OrderDetail orderDetail= productService.selectDetail(product_id);
		int group = Integer.parseInt(team);
		message.setProduct(orderDetail.getProduct());
		message.setTeam(group);
		List<Message> messageList = messageService.selectMessage(message);
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"messageList\":[");
		for(int i=0; i<messageList.size();i++) {
			message= messageList.get(i);
			sb.append("{");
			sb.append("\"message_id\":"+message.getMessage_id()+",");
			sb.append("\"product_id\":"+message.getProduct().getProduct_id()+",");
			sb.append("\"member_id\":"+message.getMember().getMember_id()+",");
			sb.append("\"sender\":"+message.getSender()+",");
			sb.append("\"regdate\":\""+message.getRegdate()+"\",");
			sb.append("\"content\":\""+message.getContent()+"\"");	
			if(i<messageList.size()-1) {
				sb.append("},");
			}else {
				sb.append("}");
			}
		}
		sb.append("]");
		sb.append("}");
		return sb.toString();
	}
	
	//대화 목록 가져오기
	@RequestMapping(value="/message/list", method=RequestMethod.GET)
	public String getChatList(Model model, HttpServletRequest request) {
		HttpSession session=request.getSession();
		Member member = (Member)session.getAttribute("member");
		int member_id = member.getMember_id();
		List messageList = messageService.select(member_id);
		List<Message> messageInfo = new ArrayList<Message>();
		for(int i=0; i<messageList.size();i++) {
			Message message= (Message) messageList.get(i);
			message.setProduct(message.getProduct());
			message.setTeam(message.getTeam());
			List list = messageService.selectMessage(message);
			messageInfo.add((Message) list.get(0));
		}
		pager.init(messageInfo, request);
		model.addAttribute("messageInfo", messageInfo);
		model.addAttribute("pager", pager);
		return "message/list";
	}
	
	//대화 목록 가져오기
	@RequestMapping(value="/choose/buyer", method=RequestMethod.GET)
	public String getBuyer(Model model, HttpServletRequest request, @RequestParam int product_id) {
		HttpSession session=request.getSession();
		Member member = (Member)session.getAttribute("member");
		List messageList = messageService.selectBuyer(product_id);
		List<Message> messageInfo = new ArrayList<Message>();
		for(int i=0; i<messageList.size();i++) {
			Message message= (Message) messageList.get(i);
			message.setProduct(message.getProduct());
			message.setTeam(message.getTeam());
			List list = messageService.selectMessage(message);
			messageInfo.add((Message) list.get(0));
		}
		pager.init(messageInfo, request);
		model.addAttribute("messageInfo", messageInfo);
		model.addAttribute("pager", pager);
		return "message/buyerList";
	}
}
