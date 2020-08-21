<%@page import="com.market.domain.Category"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	List<Category> categoryList = (List)request.getAttribute("categoryList");
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
	max-width:50%;
	height:auto;
	margin-top:22%;
}
</style>
</head>
<body class="is-preload">

	<!-- Wrapper -->
	<div id="wrapper">

		<!-- Main -->
		<div id="main">
			<div class="inner">

				<%@ include file="/include/main_navi.jsp"%>


				<!-- Banner -->
				<section id="banner">
					<div class="content">
						<header>
							<h1>
								오이마켓에 오신 것을 환영 합니다
							</h1>
						</header>
						<p>지금 바로 오이마켓에서 내 주변 이웃과 물건을 거래해보세요!</p>
						<ul class="actions">
							<li><a href="/product/registForm" class="button big">물건 팔기</a></li>
						</ul>
					</div>
					<span class="image object"><img src="images/1.jpg" alt="" />
					</span>
				</section>

				<!-- Section -->
				<section>
					<header class="major">
						<h2>Category</h2>
					</header>
					<div class="features">
						<%for(int i=0; i<categoryList.size();i++){ %>
						<%Category category=categoryList.get(i); %>
						<article>
							<span class="icon" ><img class="category_icon" src="/images/icon/<%=category.getCategory_image()%>"></span>
							<div class="content">
								<h3><%=category.getCategory_name()%></h3>
								<a href="/product/category?category_id=<%=category.getCategory_id()%>&category_name=<%=category.getCategory_name()%>" class="button small">더 보기</a>
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