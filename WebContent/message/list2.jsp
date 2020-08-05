<%@page import="com.market.domain.Message"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	List<Message> messageInfo = (List)request.getAttribute("messageInfo");
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

<style>
.category_icon{
	width:inherit;
	max-width:40%;
	height:auto;
	margin-top:30%;
}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
$(function(){
	//var sender=$("input[name='sender']").val();
	//getConversationInfo();
});
function getConversationInfo(sender){
	//var member=$("input[name='sender']").val();
	var member=sender;
	$.ajax({
		"url":"/chat/info",
		"type":"post",
		"data":{
			member_id:member
		},
		success:function(data){
			$("#chatInfo").append("<h3>"+data+"</h3>");
		}
	});
}

//대화 목록으로 이동
function messageForm(product, group){
	var product_id=product;
	var team=group;
	location.href="/chat?product_id="+product_id+"&&team="+team;
	
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

				<!-- Section -->
				<section>
					<header class="major">
						<h2>대화 목록</h2>
					</header>
					<div class="features">
						<%for(int i=0; i<messageInfo.size();i++){ %>
						<%Message message=messageInfo.get(i); %>
						<article>
							<span class="icon" ><img class="category_icon" src="/images/icon/cucumber.png"></span>
							<div class="content">
								<%int product_id=message.getProduct().getProduct_id(); %>
								<%int team=message.getTeam(); %>
								<div onClick="messageForm(<%=product_id%>,<%=team%>)">
								<%if(message.getSender()==member.getMember_id()){ %>
									<div>
									<h3><%=message.getMember().getId()%></h3>
									</div>
								<%}else{%>
									<input type="hidden" name="sender" value="<%=message.getSender()%>">
									<div id="chatInfo">
									</div>
								<%}%>
							<%=message.getProduct().getTitle() %>
								</div>
							</div>
						</article>
						<%} %>
						
					</div>
				</section>


			</div>
		</div>

		<%@ include file="/include/sidebar.jsp"%>

	</div>

	<%@ include file="/include/common.jsp"%>
	

</body>
<script>

</script>
</html>