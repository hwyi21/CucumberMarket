<%@page import="com.market.domain.Member"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	Member member = (Member) session.getAttribute("member");
%>
<!-- Header -->
<header id="header">
	<a href="/" class="logo"><strong>오이마켓</strong></a>
	<ul class="icons">
		<!-- <li><a href="/member/loginForm.jsp" class="icon">로그인</a></li>
		<li><a href="/member/registForm.jsp" class="icon">회원가입</a></li> -->
		<% if (member == null) { %>
		<li><a href="/member/loginForm.jsp" class="icon">로그인</a></li>
		<li><a href="/member/registForm.jsp" class="icon">회원가입</a></li>
		<% } else { %>
		<li><a href="/member/logout" class="icon">로그아웃</a></li>
		<li><a href="/mypage/main" class="icon">MyPage</a></li>
		<% } %>
	</ul>
</header>