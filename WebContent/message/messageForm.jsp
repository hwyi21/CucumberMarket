<%@page import="com.market.domain.Message"%>
<%@page import="java.util.List"%>
<%@page import="com.market.domain.OrderDetail"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	OrderDetail orderDetail=(OrderDetail)request.getAttribute("orderDetail");
	List<Message> messageList=(List)request.getAttribute("messageList");
	Object uri = request.getAttribute("getUri");
	String getUri = uri.toString();
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
var countClick=0;
$(function(){
	var getUri=$("input[name='uri']").val();
	$($("button")[0]).click(function(){ //등록
		if(countClick==0&&getUri=="/product/detail"){
			regist();
			countClick++;
		}else{
			regist();
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
			team:$("input[name='team']").val()
			
		},
		success:function(data){
			//리스트 보여주기
			$('form').each(function(){
			    this.reset();
			  });
			getList(data);
		}
	});
}

//비동기로 데이터 가져오기
function getList(data){
	var group = data;
	$.ajax({
		"url":"/chat/get",
		"type":"get",
		"data":{
			product_id:$("input[name='product_id']").val(),
			team:group
		},
		success:function(result){
			console.log(result);
			var json=JSON.parse(result);
			$("code").empty();
			
			for(var i=0; i<json.messageList.length;i++){
				var obj=json.messageList[i];
				$("code").append("<div style='width:50%; algin:left; border:solid 1px blue'></div><div style='width:-50%; text-align:right; word-break:break-all; word-wrap:break-word;'>"+obj.content+"</div>");
			}
		}
	}); 
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
					<pre>
						<code>
							<%if(messageList.size()!=0){ %>
							<%for(int i=0;i<messageList.size();i++){ %>
							<%Message message=messageList.get(i); %>
							<%=message.getContent() %>
							<%} %>
							<%} %>
						</code>
					</pre>
					<div>
						<input type="hidden" name="sender" value="<%=member.getMember_id()%>" />
						<input type="hidden" name="product_id" value="<%=orderDetail.getProduct().getProduct_id()%>" />
						<%if(messageList.size()==0){ %>
							<input type="hidden" name="team" value="0" />
							<input type="hidden" name="member_id" value="<%=orderDetail.getMember().getMember_id()%>" />
						<%}else{ %>
							<%for(int i=0; i<messageList.size(); i++){ %>
								<%Message message=messageList.get(i); %>
								<%if(message.getSender()!=member.getMember_id()){ %>
									<input type="hidden" name="member_id" value="<%=message.getSender()%>" />
								<%break;}%>
							<%} %>
							<%Message message=messageList.get(0);%>
							<input type="hidden" name="team" value="<%=message.getTeam()%>"/>
						<%}%>
						<input type="hidden" name="uri" value="<%=getUri%>" />
						<form>
						<input style="width:80%; margin-bottom:10px; float:left" type="text" name="content" placeholder="메세지 내용을 입력하세요"/>
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