<%@page import="com.market.controller.common.Pager"%>
<%@page import="com.market.domain.Message"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	List<Message> messageInfo = (List)request.getAttribute("messageInfo");
	int product_id = (int) request.getAttribute("product_id");
	Pager  pager = (Pager) request.getAttribute("pager");
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
	getConversationInfo();
});
function getConversationInfo(){
	var num = <%=pager.getNum()%>;
	for(var i=0; i<<%=pager.getPageSize()%>;i++){
		if(num<1)break;
		num--;
		var product_id=$($("input[name='product_id']")[i]).val();
		var team=$($("input[name='team']")[i]).val();
		var member_id=$($("input[name='member_id']")[i]).val();
		var receiver=$($("input[name='receiver']")[i]).val();
		var sender=$($("input[name='sender']")[i]).val();
		var title=$($("input[name='title']")[i]).val();
		var member = 0;
		if(member_id!=sender){
			member=sender;
		}else{
			member=receiver;
		}
		console.log(team);
		var features = document.getElementById("features");
		var article = document.createElement("article");
		var span = document.createElement("span");
		span.className ="icon";
		var img = document.createElement("img");
		var h3 = document.createElement("h3");
		img.className="category_icon";
		var content = document.createElement("div");
		content.className="content";
        img.src="/images/icon/cucumber.png";
        span.append(img);
        article.append(span);
        $.ajax({
			"url":"/chat/info",
			"type":"post",
			"async":false,
			"data":{
				member_id:member
			},
			success:function(data){
				h3.append(data);
			}
		});
        
		content.append(h3);
		content.append(title);
		article.append(content);
		features.append(article);
		(function(){
			var product = product_id;
			var member_id = member;
			document.getElementsByClassName('content')[i].onclick = function() {
	          messageForm(product, member_id);
	      }
	    })(i);
	}
}

//Order_detail 구매자 등록
function messageForm(product_id, member){
	location.href="/success?product_id="+product_id+"&buyer_id="+member;
}

//구매자 다음에 선택
function success(){
	alert("거래가 완료되었습니다.");
	location.href="/product/detail?product_id="+<%=product_id%>;
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
						<h2>거래 상대를 골라주세요</h2>
						<a onclick="success()" class="button primary fit" style="width:28%">다음에 선택하기</a> 
					</header>
					<div class="features" id="features">
						<input type="hidden" id="messageList" value="<%=messageInfo.size()%>">
						<% int curPos = pager.getCurPos(); %>
						<% int num = pager.getNum();%>
						<% for (int i = 0; i < pager.getPageSize(); i++) { %>
						<% if (num < 1) break; %>
						<%Message message=messageInfo.get(curPos++); %>
						<input type="hidden" value="<%=num--%>" /> 
						<input type="hidden" id="product_id" name="product_id" value="<%=product_id%>"/>
						<input type="hidden" id="team" name="team" value="<%=message.getTeam()%>"/>
						<input type="hidden" id="sender" name="sender" value="<%=message.getSender()%>">
						<input type="hidden" id="member_id" name="member_id" value="<%=member.getMember_id()%>">
						<input type="hidden" id="receiver" name="receiver" value="<%=message.getMember().getMember_id()%>">
						<input type="hidden" id="title" name="title" value="<%=message.getProduct().getTitle() %>">
						<%} %>
					</div>
					
					<ul class="pagination" style="text-align:center">
						<%int firstPage=pager.getFirstPage(); %>
						<%int lastPage=pager.getLastPage(); %>
						<%int totalPage=pager.getTotalPage(); %>
						<%int currentPage=pager.getCurrentPage(); %>
						<%if(messageInfo.size()!=0){ %>
						<%if(firstPage-1 >= 1){%>
						<li><a href="/choose/buyer?product_id=<%=product_id%>&currentPage=<%=firstPage-1 %>" class="button">Prev</a></li>
				        <%}else{%>
				        <li><span class="button disabled">Prev</span></li>
				        <%}%>
						<% for(int i=firstPage; i<=lastPage; i++){ %>
        				<%if(i>totalPage) break; %>
        				<li><a <% if(currentPage==i){%>class="page active"<%}else{%>class="page"<%}%> href="/choose/buyer?product_id=<%=product_id%>&currentPage=<%=i%>"><%= i %></a></li>
        				<% } %>
						 <%if((lastPage+1)>totalPage) {%>
				        <li><span class="button disabled">Next</span></li>
				        <%}else{%>
				        <li><a href="/choose/buyer?product_id=<%=product_id%>&currentPage=<%=lastPage+1%>" class="button">Next</a></li>
				        <%}%>
				        <%}%>
					</ul>
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