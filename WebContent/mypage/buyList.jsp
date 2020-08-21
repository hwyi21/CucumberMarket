<%@page import="com.market.domain.State"%>
<%@page import="com.market.controller.common.Pager"%>
<%@page import="com.market.domain.ProductImage"%>
<%@page import="com.market.domain.Product"%>
<%@page import="com.market.domain.OrderDetail"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	List<OrderDetail> productList = (List) request.getAttribute("productList");
	List<ProductImage> productImageList = (List) request.getAttribute("productImageList");
	Pager pager = (Pager) request.getAttribute("pager");
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
</style>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
	
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
					<header class="major">
						<h2>구매내역</h2>
					</header>
					<div class="posts">
						<% int curPos = pager.getCurPos(); %>
						<% int num = pager.getNum();%>
						<% for (int i = 0; i < pager.getPageSize(); i++) { %>
						<% if (num < 1) break; %>
						<% OrderDetail orderDetail = (OrderDetail) productList.get(curPos); %>
						<% Product product = orderDetail.getProduct(); %>
						<% Member saler = orderDetail.getMember(); %>
						<% State state = orderDetail.getState();%>
						<% List obj = (List) productImageList.get(curPos++);
							ProductImage productImage = (ProductImage) obj.get(0);
						%>
						<article>
							<input type="hidden" value="<%=num--%>" /> 
							<a class="image"><img src="/data/<%=productImage.getFilename()%>"/></a>
							<h3><%=product.getTitle()%></h3>
							<p>
								<%=saler.getLocate()%> <%if(product.getRe_regdate()!=null){%>끌올<%} %>
								<%if(state.getState_id()!=1){%><h5 style="color:green; font-weight:bold"><%=state.getState_title()%></h5><%}%>
								<h4>가격 : <fmt:formatNumber value="<%=product.getPrice() %>" pattern="#,###"/> 원</h4>
							</p>
							<ul class="actions">
								<li>
									<a href="/product/detail?product_id=<%=product.getProduct_id()%>" class="button">자세히</a>
								</li>
							</ul>
						</article>
						<% }%>
						<!-- 구매한 상품이 없는 경우 -->
						<% if (productList.size() == 0) { %>
						<article style="width: 100%">
							<h3>아직 구매하신 상품이 없습니다</h3>
							<p>우리 동네 이웃이 판매중인 다양한 물건을 구경해보세요</p>
							<button onclick="location.href='/product'">우리 동네 물건 보러가기</button>
						</article>
						<% } %>
					</div>
					<!-- 페이징 처리 -->
					<ul class="pagination" style="text-align:center">
						<%int firstPage=pager.getFirstPage(); %>
						<%int lastPage=pager.getLastPage(); %>
						<%int totalPage=pager.getTotalPage(); %>
						<%int currentPage=pager.getCurrentPage(); %>
						<%if(firstPage-1 >= 1){%>
						<li><a href="/mypage/buy?currentPage=<%=firstPage-1 %>" class="button">Prev</a></li>
				        <%}else{%>
				        <li><span class="button disabled">Prev</span></li>
				        <%}%>
						<% for(int i=firstPage; i<=lastPage; i++){ %>
        				<%if(i>totalPage) break; %>
        				<li><a <% if(currentPage==i){%>class="page active"<%}else{%>class="page"<%}%> href="/mypage/buy?currentPage=<%=i%>"><%= i %></a></li>
        				<% } %>
						 <%if((lastPage+1)>totalPage) {%>
				        <li><span class="button disabled">Next</span></li>
				        <%}else{%>
				        <li><a href="/mypage/buy?currentPage=<%=lastPage+1%>" class="button">Next</a></li>
				        <%}%>
					</ul>
				</section>
			</div>
		</div>

		<%@ include file="/include/sidebar.jsp"%>
	</div>
	<%@ include file="/include/common.jsp"%>

</body>

</html>