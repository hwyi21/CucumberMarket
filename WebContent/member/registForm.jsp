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
function validate(){
	if(form1.id.value==""||form1.password.value==""){
		alert("정확한 정보를 입력해주세요");
		return;
	}
	regist();
}

function regist(){
	form1.method="post";
	form1.action="/member/regist";
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
					<h2>회원가입</h2>
				</section>
				<form name="form1">
					<div class="row gtr-uniform">
						<div class="col-6 col-12-xsmall">
							<input type="text" name="id" placeholder="ID" value=""/>
						</div>
						<div class="col-6 col-12-xsmall">
							<input type="password" name="password" placeholder="Password" value=""/>
						</div>
						
						<div><strong> 위치를 확인해주세요! 현재 위치가 지도에 표시된 곳과 다르다면 지도를 움직여 현재위치를 알맞게 표시해주세요~<br>
						지도에 표시된 위치로 회원가입이 이루어집니다!</strong></div>
						<div class="map_wrap">
							<div id="map"
								style="width: 100%; height: 100%; position: relative; overflow: hidden;"></div>
							<div class="hAddr">
								<span class="title">지도중심기준 행정동 주소정보</span> <span id="centerAddr" ></span>
								<input type="hidden" name="locate" id="locate" value="locate">
							</div>
						</div>

						<!-- Break -->
						<div class="col-12">
							<ul class="actions">
								<li><input type="button" value="회원가입" class="primary" onclick="validate()"/></li>
								<li><input type="reset" value="Reset" /></li>
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
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=a2bdeae7d2fc038a9600d2f5d0d610ef&libraries=services"></script>
<script>
	var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
	mapOption = {
		center : new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
		level : 5 // 지도의 확대 레벨
	};
	var map = new kakao.maps.Map(mapContainer, mapOption);

	// 주소-좌표 변환 객체를 생성합니다
	var geocoder = new kakao.maps.services.Geocoder();

	var marker = new kakao.maps.Marker(), // 클릭한 위치를 표시할 마커입니다
	infowindow = new kakao.maps.InfoWindow({
		zindex : 1
	}); // 클릭한 위치에 대한 주소를 표시할 인포윈도우입니다

	//HTML5의 geolocation으로 사용할 수 있는지 확인
	if (navigator.geolocation) {

		// GeoLocation을 이용해서 접속 위치를 얻어오기
		navigator.geolocation.getCurrentPosition(function(position) {

			var lat = position.coords.latitude, // 위도
			lon = position.coords.longitude; // 경도

			var locPosition = new kakao.maps.LatLng(lat, lon), // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성
			message = '<div style="padding:5px;">여기에 계신가요?!</div>'; // 인포윈도우에 표시될 내용
			
			//현재 위치에 마커와 인포윈도우 표시
			displayMarker(locPosition, message);

		});

	} else { // HTML5의 GeoLocation을 사용할 수 없을때 마커 표시 위치와 인포윈도우 내용을 설정

		var locPosition = new kakao.maps.LatLng(33.450701, 126.570667), message = '현재 위치를 불러 올 수 없습니다 ㅠㅠ'

	}
	
	// 현재 위치에 마커와 인포윈도우를 표시하는 함수
	function displayMarker(locPosition, message) {
		// 지도 중심좌표를 접속위치로 변경합니다
		map.setCenter(locPosition);
		
		//마커 표시
		marker.setPosition(locPosition);
		marker.setMap(map);

		// 인포윈도우를 마커위에 표시합니다 
		infowindow.setContent(message);
		infowindow.open(map, marker);
		
		//현재 좌표의 행정동 주소 불러오기
		searchAddrFromCoords(map.getCenter(), displayCenterInfo);
	}

	// 지도를 클릭했을 때 클릭 위치 좌표에 대한 주소정보를 표시하도록 이벤트 등록
	kakao.maps.event.addListener(map, 'click', function(mouseEvent) {
		searchDetailAddrFromCoords(mouseEvent.latLng, function(result, status) {
			if (status === kakao.maps.services.Status.OK) {
				var detailAddr = !!result[0].road_address ? '<div>도로명주소 : '
						+ result[0].road_address.address_name + '</div>' : '';
				detailAddr += '<div>지번 주소 : ' + result[0].address.address_name
						+ '</div>';

				var content = '<div class="bAddr">'
						+ '<span class="title">법정동 주소정보</span>' + detailAddr
						+ '</div>';

				// 마커를 클릭한 위치에 표시
				marker.setPosition(mouseEvent.latLng);
				marker.setMap(map);

				// 인포윈도우에 클릭한 위치에 대한 법정동 상세 주소정보 표시
				infowindow.setContent(content);
				infowindow.open(map, marker);
			}
		});
	});
	
	//지도에 이벤트리스너 등록
	kakao.maps.event.addListener(map, 'idle', function() {
		searchAddrFromCoords(map.getCenter(), displayCenterInfo);
	});

	// 좌표로 행정동 주소 정보를 요청합니다
	function searchAddrFromCoords(coords, callback) {
		geocoder.coord2RegionCode(coords.getLng(), coords.getLat(), callback);
	}

	// 좌표로 법정동 상세 주소 정보를 요청합니다
	function searchDetailAddrFromCoords(coords, callback) {
		geocoder.coord2Address(coords.getLng(), coords.getLat(), callback);
	}
	
	// 지도 좌측상단에 지도 중심좌표에 대한 주소정보를 표출하는 함수
	function displayCenterInfo(result, status) {
		if (status === kakao.maps.services.Status.OK) {
			var infoDiv = document.getElementById('centerAddr');
			for (var i = 0; i < result.length; i++) {
				// 행정동의 region_type 값은 'H' 이므로
				if (result[i].region_type === 'H') {
					infoDiv.innerHTML = result[i].address_name;
					$('#locate').val(infoDiv.innerHTML);
					break;
				}
			}
		}
	}
	
</script>
</html>