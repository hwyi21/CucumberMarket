<%@page import="com.market.domain.ProductImage"%>
<%@page import="com.market.domain.Product"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	Product product = (Product)request.getAttribute("product");
	Member saler = (Member)request.getAttribute("saler");
	List<ProductImage> productImageList = (List) request.getAttribute("productImageList");

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
img {
	vertical-align: middle;
}

/* Position the image container (needed to position the left and right arrows) */
.container {
	position: relative;
}

/* Hide the images by default */
.mySlides {
	display: none;
}

/* Add a pointer when hovering over the thumbnail images */
.cursor {
	cursor: pointer;
}

/* Next & previous buttons */
.prev, .next {
	cursor: pointer;
	position: absolute;
	top: 40%;
	width: auto;
	padding: 16px;
	margin-top: -50px;
	color: white;
	font-weight: bold;
	font-size: 20px;
	border-radius: 0 3px 3px 0;
	user-select: none;
	-webkit-user-select: none;
}

/* Position the "next button" to the right */
.next {
	right: 0;
	border-radius: 3px 0 0 3px;
}

/* On hover, add a black background color with a little bit see-through */
.prev:hover, .next:hover {
	background-color: rgba(0, 0, 0, 0.8);
}

/* Number text (1/3 etc) */
.numbertext {
	color: #f2f2f2;
	font-size: 12px;
	padding: 8px 12px;
	position: absolute;
	top: 0;
}

/* Container for image text */
.caption-container {
	text-align: center;
	background-color: #222;
	padding: 2px 16px;
	color: white;
}

.row:after {
  content: "";
  display: table;
  clear: both;
}

/* Six columns side by side */
.column {
  float: left;
  width: 16.66%;
}

/* Add a transparency effect for thumnbail images */
.demo {
	opacity: 0.6;
}

.active, .demo:hover {
	opacity: 1;
}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
function del(){
	if(confirm("삭제하시겠습니까?")){
		$("form").attr({
			"action":"/product/delete",
			"method":"post"
		});
		$("form").submit();
	}
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
					<form>
					<input type="hidden" name="product_id" value="<%=product.getProduct_id()%>"> 
					<div class="container" style="width:80%; margin-bottom:20px;">
					<%for(int i=0; i<productImageList.size(); i++){ %>
					<%ProductImage productImage = productImageList.get(i); %>
						<div class="mySlides">
							<div class="numbertext"><%=i+1%> / <%=productImageList.size() %></div>
							<img src="/data/<%=productImage.getFilename()%>" style="width: 100%;">
							<input type="hidden" name="filename_<%=i%>" value="<%=productImage.getFilename()%>"/>
						</div>
					<%} %>
						<a class="prev" onclick="plusSlides(-1)">❮</a> <a class="next"
							onclick="plusSlides(1)">❯</a>

						<div class="row" style="overflow:auto; height:90px;">
						<%for(int i=1; i<=productImageList.size(); i++){ %>
						<%ProductImage productImage = productImageList.get(i-1); %>
							<div class="column">
								<img class="demo cursor" src="/data/<%=productImage.getFilename()%>" style="width: 100%"
									onclick="currentSlide(<%=i%>)">
							</div>
							<%} %>
						</div>
					</div>
					
					<div class="box">
						<p>
						<h2><%=product.getTitle()%></h2>
						<%=product.getCategory().getCategory_name() %>
						<%if(product.getRe_regdate()==null){%>
						<%=product.getFirst_regdate() %>
						<%}else{ %>끌올
						<%=product.getRe_regdate() %>
						<%} %>
						<br>
						<h4><%=product.getContent() %></h4><br>
						</p>
					</div>
					
					<%if(member.getMember_id()==saler.getMember_id()){ %>
						<ul class="actions">
								<li>
									<a href="/product/delete?product_id=<%=product.getProduct_id()%>" class="button">삭제</a>
								</li>
							</ul>
					<%} %>
					</form>
					<ul class="actions">
						<li>
							<a href="/product" class="button">목록</a>
						</li>
					</ul>
				</section>
			</div>
		</div>

		<%@ include file="/include/sidebar.jsp"%>
	</div>
	<%@ include file="/include/common.jsp"%>

	<script>
		var slideIndex = 1;
		showSlides(slideIndex);

		function plusSlides(n) {
			showSlides(slideIndex += n);
		}

		function currentSlide(n) {
			showSlides(slideIndex = n);
		}

		function showSlides(n) {
			var i;
			var slides = document.getElementsByClassName("mySlides");
			var dots = document.getElementsByClassName("demo");
			if (n > slides.length) {
				slideIndex = 1
			}
			if (n < 1) {
				slideIndex = slides.length
			}
			for (i = 0; i < slides.length; i++) {
				slides[i].style.display = "none";
			}
			for (i = 0; i < dots.length; i++) {
				dots[i].className = dots[i].className.replace(" active", "");
			}
			slides[slideIndex - 1].style.display = "block";
			dots[slideIndex - 1].className += " active";
		}
	</script>
</body>

</html>