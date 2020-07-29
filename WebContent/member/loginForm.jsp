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

<script>
function login(){
	form1.method="post";
	form1.action="/member/login";
	form1.submit();
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
					<h2>로그인</h2>
				</section>
				<form name="form1">
					<div class="row gtr-uniform">
						<div class="col-6 col-12-xsmall">
							<input type="text" name="id" placeholder="ID" />
						</div>
						<div class="col-6 col-12-xsmall">
							<input type="password" name="password" placeholder="Password" />
						</div>
						<!-- Break -->
						<div class="col-12">
							<ul class="actions">
								<li><input type="submit" value="로그인" class="primary" onClick="login()"/></li>
								<li><input type="button" value="회원가입" onclick="location.href='/member/registForm.jsp'"/></li>
							</ul>
						</div>
					</div>
				</form>
			</div>
		</div>

		<%@ include file="/include/sidebar.jsp"%>
	</div>
	<%@ include file="/include/common.jsp"%>

</body>
</html>