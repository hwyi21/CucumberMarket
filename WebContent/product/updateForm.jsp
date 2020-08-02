<%@page import="com.market.domain.ProductImage"%>
<%@page import="java.util.List"%>
<%@page import="com.market.domain.Product"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	Product product = (Product)request.getAttribute("product");
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
.map_wrap {
	position: relative;
	width: 100%;
	height: 350px;
}

.title {
	font-weight: bold;
	display: block;
}

.hAddr {
	position: absolute;
	left: 10px;
	top: 10px;
	border-radius: 2px;
	background: #fff;
	background: rgba(255, 255, 255, 0.8);
	z-index: 1;
	padding: 5px;
}

#centerAddr {
	display: block;
	margin-top: 2px;
	font-weight: normal;
}

.bAddr {
	padding: 5px;
	text-overflow: ellipsis;
	overflow: hidden;
	white-space: nowrap;
}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
var category_id=<%=product.getCategory().getCategory_id()%>;
var gfv_count =<%=productImageList.size()%>;

$(function() {
	if($("input:checkbox[name=demo-copy]").is(":checked") == true) {
		$('#re_regdate').val("true");
	}
	getCategoryList(); //카테고리 목록 가져오기

	$("#addFile").on("click", function(e) { //파일 추가 버튼 
		e.preventDefault();
		addFile();
	});
	$("a[name='delete']").on("click", function(e) { //삭제 버튼 
		e.preventDefault();
		deleteFile($(this));
	});

});
function getCategoryList(){
	$.ajax({
		"url":"/category/list",
		"type":"get",
		success:function(result){
			var json=JSON.parse(result);			
			$("select").empty();
			$("select").append("<option value='0'>카테고리 선택</option>");
			for(var i=0; i<json.categoryList.length;i++){
				var obj=json.categoryList[i];
				//전역변수로 선언된 카테고리의 id와 obj의 id가 같은 경우
				//selected 처리
				if(obj.category_id==category_id){
					$("select").append("<option selected value='"+obj.category_id+"'>"+obj.category_name+"</option>");					
				}else{
					$("select").append("<option value='"+obj.category_id+"'>"+obj.category_name+"</option>");					
				}
				
			}
		}
	});
}
function addFile() {
	var str = "<p><li><input type='file' name='filename_" + (gfv_count++) + "'><a href='#this' name='delete'>삭제</a></li></p>";
	$("#fileDiv").append(str);
	$("a[name='delete']").on("click", function(e) { //삭제 버튼 
		e.preventDefault();
		deleteFile($(this));
	});
}

function deleteFile(obj) {
	obj.parent().remove();
}
function update(){	
	$("form").attr({
		"action":"/product/update",
		"enctype":"multipart/form-data",
		"method":"post"
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
				<!-- Content -->
				<section>
					<h2>중고거래 글쓰기</h2>
				</section>

				<form>
					<div class="row gtr-uniform">
						<div class="col-12">
							<input type="hidden" name="product_id" value="<%=product.getProduct_id()%>"> 
							<input type="text" name="title"
								value="<%=product.getTitle() %>" />
						</div>

						<!-- Break -->
						<div class="col-12">
							<select name="category.category_id" id="demo-category">
								<option value="0">- Category -</option>
							</select>
						</div>

						<div class="col-12">
							<input type="text" name="price"
								value="<%=product.getPrice() %>" />
						</div>
						<div class="col-6 col-12-small">
								<input type="checkbox" id="demo-copy" name="demo-copy">
								<label for="demo-copy">끌올</label>
							<input type="hidden" name="re_regdate" value="false">
						</div>

						<div class="col-12">
							<ul class="actions">
								<div id="fileDiv">
									<p>
								 	<%for (int i=0; i<productImageList.size();i++){%>
								 	<%ProductImage productImage = productImageList.get(i); %>
									<li>
										<img src="/data/<%=productImage.getFilename() %>" width="200px">
										<input type="file" name="filename_<%=i%>"> 
										<a href='#this' name='delete'>삭제</a>
									</li>
									<%} %>
									</p>
								</div>
							</ul>
						</div>
						<a href="#this" id="addFile"><input type="button" value="파일추가" /></a>

						<div class="col-12">
							<textarea name="content" id="demo-message" rows="6"><%=product.getContent() %></textarea>
						</div>
						<!-- Break -->
						<div class="col-12">
							<ul class="actions">
								<li><input type="submit" value="상품 수정하기" class="primary" onclick="update()"/></li>
								<li><input type="reset" value="Reset" /></li>
								<li><input type="button" value="목록으로" onclick="location.href='/product'" /></li>
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