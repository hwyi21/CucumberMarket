<%@ page contentType="text/html; charset=UTF-8"%>
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
	max-width:47%;
	height:auto;
	margin-top:25%;
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

				<!-- Section -->
				<section>
					<header class="major">
						<h2>MyPage</h2>
					</header>
					<div class="features">
						<article onclick="location.href='/mypage/sale'">
							<span class="icon" ><img class="category_icon" src="/images/icon/money.png"></span>
							<div class="content">
								<h3>판매내역</h3>
							</div>
						</article>
						<article onclick="location.href='/mypage/buy'">
							<span class="icon" ><img class="category_icon" src="/images/icon/buy.png"></span>
							<div class="content">
								<h3>구매내역</h3>
							</div>
						</article>
						<article onclick="location.href='/product/category?category_id='">
							<span class="icon" ><img class="category_icon" src="/images/icon/heart.png"></span>
							<div class="content">
								<h3>관심 상품</h3>
							</div>
						</article>
					</div>
				</section>


			</div>
		</div>

		<%@ include file="/include/sidebar.jsp"%>

	</div>

	<%@ include file="/include/common.jsp"%>

</body>
</html>