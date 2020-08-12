<%@page import="com.market.domain.ProductImage"%>
<%@page import="com.market.domain.Product"%>
<%@page import="com.market.domain.Message"%>
<%@page import="java.util.List"%>
<%@page import="com.market.domain.OrderDetail"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	OrderDetail orderDetail=(OrderDetail)request.getAttribute("orderDetail");
	List<Message> messageList=(List)request.getAttribute("messageList");
	List<ProductImage> productImageList=(List)request.getAttribute("productImageList");
%>
<!DOCTYPE HTML>
<!--
	Editorial by HTML5 UP
	html5up.net | @ajlkn
	Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
-->
<html>
<head>
<title>Hello!</title>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no" />

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
var writer_count=0;
var content_count=0;
var group=0;

$(function(){
	var objDiv = document.getElementById("contentArea2"); 
	if(objDiv.scrollTop != objDiv.scrollHeight){
		objDiv.scrollTop = objDiv.scrollHeight;
	}

	group = $("input[name='team']").val();
	getList(group);
	$($("button")[0]).click(function(){ //등록
		regist();
	});
	
	//엔터키 입력시 ajax로 데이터 보내기
	$("#content").keypress(function (e) {
	   if (e.keyCode == 13) {
		   $.ajax({
				"url":"/chat/send",
				"type":"post",
				"data":{
					product_id:$("input[name='product_id']").val(),
					member_id:$("input[name='member_id']").val(),
					sender:$("input[name='sender']").val(),
					content:$("input[name='content']").val(),
					team:group
				},
				success:function(data){
					//리스트 보여주기
					$('form').each(function(){
					    this.reset();
					  });
					getList(data);
					group=data;
				}
			}); return false;
	    }
	});
	
});


function regist(){
	$.ajax({
		"url":"/chat/send",
		"type":"post",
		"data":{
			product_id:$("input[name='product_id']").val(),
			member_id:$("input[name='member_id']").val(),
			sender:$("input[name='sender']").val(),
			content:$("input[name='content']").val(),
			team:group
		},
		success:function(data){
			//리스트 보여주기
			$('form').each(function(){
			    this.reset();
			  });
			getList(data);
			group=data;
		}
	}); return false;
}

function getConversationInfo(sender){
	var member=sender;
	var sender=$("input[name='sender']").val();
	$.ajax({
		"url":"/chat/info",
		"type":"post",
		"async":false,
		"data":{
			member_id:member
		},
		success:function(data){
			if(member==sender){
				$("#contentArea").append("<div name='writer_"+(writer_count++)+"' style='color:green; text-align:right;'>["+data+"]</div>");
			}else{
				$("#contentArea").append("<div name='writer_"+(writer_count++)+"' style='color:black; text-align:left;'>["+data+"]</div>");
			}
		}
	});
}

function getList(data){
	var group = data;
	var sender=$("input[name='sender']").val();
	$.ajax({
		"url":"/chat/get",
		"type":"get",
		"data":{
			product_id:$("input[name='product_id']").val(),
			team:group
		},
		success:function(result){
			//console.log(result);
			var json=JSON.parse(result);
			$("#contentArea").empty();
			for(var i=0; i<json.messageList.length;i++){
				var obj=json.messageList[i];
				if(obj.sender==sender){
					getConversationInfo(obj.sender);
					$("#contentArea").append("<div name='content_"+(content_count++)+"'style='word-break:break-all; word-wrap:break-word; text-align:right'>"+obj.content+"</div>");
				}else{
					getConversationInfo(obj.sender);
					$("#contentArea").append("<div name='content_"+(content_count++)+"'style='word-break:break-all; word-wrap:break-word; text-align:left'>"+obj.content+"</div>");
				}
				
			}
		}
	}); return false;
}
</script>
</head>
<body class="is-preload">

	<!-- Wrapper -->
	<div id="wrapper">

		<!-- Main -->
		<div id="main">
			<div class="inner">

				<%@ include file="/include/main_navi.jsp"%>
				<!-- Content -->
				<section>
					
					<div class="box" style="height:150px; overflow:hidden; display:flex;">
						<%Product product=orderDetail.getProduct(); 
						ProductImage productImage = productImageList.get(0);%>
						<img src="/data/<%=productImage.getFilename()%>" style="width:100px; align:left; float:left; margin-right:10px">
						<h3 style="margin-top:40px;"><%=product.getTitle() %></h3>
						
					</div>
					<div class="box" id="contentArea2" style="overflow:auto">
						<div id="contentArea"></div>
					</div>
					<div>
						<form>
						<input type="hidden" name="product_id" value="<%=orderDetail.getProduct().getProduct_id()%>" />
						<input type="hidden" name="sender" value="<%=member.getMember_id()%>" />
						<%if(messageList==null){ %>
							<input type="hidden" name="team" value="0" />
							<input type="hidden" name="member_id" value="<%=orderDetail.getMember().getMember_id()%>" />
						<%}else{ %>
							<%int find=0; %> 
							<%for(int i=0; i<messageList.size(); i++){ %>
								<%Message message=messageList.get(i); %>
								<%if(message.getSender()!=member.getMember_id()){ %>
									<input type="hidden" name="member_id" value="<%=message.getSender()%>" />
								<%find++; break;}%>
							<%}if(find==0){ %> <!-- 보낸사람이 1명만 존재한다면 받는 사람 아이디는 상품을 등록한사람 -->
							<input type="hidden" name="member_id" value="<%=orderDetail.getMember().getMember_id()%>" />
							<%} %>
							<%Message message=messageList.get(0);%>
							<input type="hidden" name="team" value="<%=message.getTeam()%>"/>
						<%}%>
						<input style="width:90%; margin-bottom:10px; float:left" type="text" id="content" name="content" placeholder="메세지 내용을 입력하세요"/>
						</form>
						<button style="float:right">보내기</button>
					</div>
				</section>
			</div>
		</div>

		<%@ include file="/include/sidebar.jsp"%>
	</div>
	<%@ include file="/include/common.jsp"%>
</body>

</html>