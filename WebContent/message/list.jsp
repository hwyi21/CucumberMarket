<%@page import="com.market.domain.Message"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	List<Message> messageList = request.getAttribute("messageList");
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
	max-width:48%;
	height:auto;
	margin-top:22%;
}
</style>
<script>
$(function(){
	var data=$("input[name='member_id']").val();
	getConversationInfo(data);
});
function getConversationInfo(data){
	var member=data;
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
						<h2>Category</h2>
					</header>
					<div class="features">
						<%for(int i=0; i<messageList.size();i++){ %>
						<%Message message=messageList.get(i); %>
						<article>
							<span class="icon" ><img class="category_icon" src="/images/icon/cucumber.png"></span>
							<div class="content" name="chatInfo">
								<%if(message.getSender()==member.getMember_id()){ %>
									<h3><%=message.getMember().getId()%></h3>
								<%}else{%>
									<input type="hidden" name="member_id" value="<%=message.getSender()%>">
							
								<%}%>
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
</html>