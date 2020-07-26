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
<%@ include file="/include/common.jsp"%>
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
							<p>A free and fully responsive site template</p>
						</header>
						<p>Aenean ornare velit lacus, ac varius enim ullamcorper eu.
							Proin aliquam facilisis ante interdum congue. Integer mollis,
							nisl amet convallis, porttitor magna ullamcorper, amet egestas
							mauris. Ut magna finibus nisi nec lacinia. Nam maximus erat id
							euismod egestas. Pellentesque sapien ac quam. Lorem ipsum dolor
							sit nullam.</p>
						<ul class="actions">
							<li><a href="#" class="button big">Learn More</a></li>
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
								
							</div>
						</article>
						<%} %>
						
					</div>
				</section>


			</div>
		</div>

		<%@ include file="/include/sidebar.jsp"%>

	</div>

	

</body>
</html>