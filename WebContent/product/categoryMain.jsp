<%@page import="com.market.domain.OrderDetail"%>
<%@page import="com.market.domain.State"%>
<%@page import="com.market.controller.common.Pager"%>
<%@page import="com.market.domain.ProductImage"%>
<%@page import="com.market.domain.Product"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	List<Product> productList = (List) request.getAttribute("productList");
	List<OrderDetail> orderDetailList = (List) request.getAttribute("orderDetailList");
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
					<div class="posts">
						<% int curPos = pager.getCurPos(); %>
						<% int num = pager.getNum();%>
						<% for (int i = 0; i < pager.getPageSize(); i++) { %>
						<% if (num < 1) break; %>
						<% int curPos2=curPos;%>
						<% Product product = (Product) productList.get(curPos); %>
						<% OrderDetail orderDetail= orderDetailList.get(curPos); %>
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
								<%if(state.getState_id()!=1){%><h5 style="font-color:green; font-weight:bold"><%=state.getState_title()%></h5><%}%>
								<h4>가격 : <%=product.getPrice()%></h4>
							</p>
							<ul class="actions">
								<li>
									<a href="/product/detail?product_id=<%=product.getProduct_id()%>" class="button">자세히</a>
								</li>
							</ul>
						</article>
						<% }%>
						<!-- 등록된 상품이 없는 경우 -->
						<% if (productList.size() == 0) { %>
						<article style="width: 100%">
							<h3>현재 카테고리에 등록된 상품이 없습니다.</h3>
						</article>
						<% } %>
					</div>
					<!-- 페이징 처리 -->
					<ul class="pagination" style="text-align:center">
						<%int firstPage=pager.getFirstPage(); %>
						<%int lastPage=pager.getLastPage(); %>
						<%int totalPage=pager.getTotalPage(); %>
						<%int currentPage=pager.getCurrentPage(); %>
						<%if(firstPage-1 > 1){%>
						<li><a href="/product/categoryMain?currentPage=<%=firstPage-1 %>" class="button">Prev</a></li>
				        <%}else{%>
				        <li><span class="button disabled">Prev</span></li>
				        <%}%>
						<% for(int i=firstPage; i<=lastPage; i++){ %>
        				<%if(i>totalPage) break; %>
        				<li><a <% if(currentPage==i){%>class="page active"<%}else{%>class="page"<%}%> href="/product/categoryMain?currentPage=<%=i%>"><%= i %></a></li>
        				<% } %>
						 <%if((lastPage+1)>totalPage) {%>
				        <li><span class="button disabled">Next</span></li>
				        <%}else{%>
				        <li><a href="/product/categoryMain?currentPage=<%=lastPage+1%>" class="button">Next</a></li>
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