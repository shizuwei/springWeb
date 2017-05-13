<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; "
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- Title here -->
<title>英伦买买买</title>
<!-- Description, Keywords and Author -->
<meta name="description" content="CNIT_Team,cnit.pro">
<meta name="keywords" content="CNIT_Team,cnit.pro">
<meta name="author" content="CNIT_Team">

<meta name="viewport"
	content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">

<!-- Styles -->
<!-- Bootstrap CSS -->
<link href="../html/static_files/bootstrap.min.css" rel="stylesheet">
<!-- Font awesome CSS -->
<link href="../html/static_files/font-awesome.min.css" rel="stylesheet">
<!-- Animate CSS -->
<link href="../html/static_files/animate.min.css" rel="stylesheet">
<!-- Custom CSS -->
<link href="../html/static_files/style.css" rel="stylesheet">
<link rel="stylesheet" href="../html/static_files/reset.css">
<link rel="stylesheet" href="../html/static_files/remodal.css">
<link rel="stylesheet" href="../html/static_files/remodal-default-theme.css">
<link rel="stylesheet" href="../html/static_files/index.css">
<link rel="stylesheet" href="../html/static_files/jquery.datetimepicker.css">
</head>

<body
	style='height: 100%; background: url(&amp;quot;/img/6.jpg&amp;quot;) repeat;'>
	<div align="center">
		<form id="searchForm"
			action="./list.do" method="post">
			<table class="outside_table" border="1" width="88%" align="center"
				style="color: black;">
				<tbody>
					<tr>
						<td style="background-color: #ff7292;" class="title_bg"
							width="100"><div align="center">
								<font color="white">订单查询</font>
							</div></td>
						<td
							style="padding-top: 10px; padding-bottom: 10px; padding-left: 10px;">
							账户号： <input type="text" style="height: 25px;" id="userNumber"
							name="userNumber" value="00001" placeholder="账户号为必填项">&nbsp;&nbsp;

							订单日期：
							<input type="text" value="" id="begin"
							name="begin" class="uichoose" style="width: 20%"
							placeholder="开始时间">
							至 
							<input type="text" value=""
							id="end" name="end" class="uichoose"
							style="width: 20%" placeholder="结束时间"> 
							<br> <br>
							商品品牌：
							<input type="text" style="height: 25px;" id="brand"
							name="brand" placeholder="商品品牌">&nbsp;&nbsp; 
							付款状态： 
							<input
							id="payStatus" name="payStatus" type="text" class="uichoose"
							onclick="combo(&#39;未付款$已付款$已退款$&#39;,this,&#39;&#39;);"
							style="width: 10%" value="" readonly="readonly"
							data-req="required" placeholder="付款状态"> &nbsp;&nbsp;
							到货状态：
							<input id="arriveStatus" name="arriveStatus" type="text"
							class="uichoose"
							onclick="combo(&#39;未到货$已到货$已发货$&#39;,this,&#39;&#39;);"
							style="width: 10%" value="" readonly="readonly"
							data-req="required" placeholder="到货状态"> &nbsp;&nbsp; 
							<input
							name="search" type="submit" value="查询"
							style="width: 100px; height: 30px; background-color: #FF7292;">
						</td>
					</tr>

					<tr>
						<td style="background-color: #ff7292;" class="title_bg"
							width="100"><div align="center">
								<font color="white">用户信息</font>
							</div></td>
						<td
							style="padding-top: 10px; padding-bottom: 10px; padding-left: 10px;">
							账户号：<c:out value="${list[0].user.accountNumber}" escapeXml="true" default=""/>
							&nbsp;&nbsp;&nbsp;&nbsp;账户微信：${list[0].user.wxName}&nbsp;&nbsp;&nbsp;&nbsp;
							订单数：${info.totalCnt}个</td>
					</tr>

				</tbody>
			</table>

			<table class="outside_table" border="1" cellpadding="0" width="88%"
				cellspacing="0">
				<tbody>
					<tr>
						<td bgcolor="#ff7292" style="font-size: 12px; padding: 5px;">未付款量</td>
						<td style="font-size: 12px; padding: 5px; color: black;">${info.unPayedCnt}个</td>
						<td bgcolor="#ff7292" style="font-size: 12px; padding: 5px;">未付款额</td>
						<td style="font-size: 12px; padding: 5px; color: black;">${info.unPayedMoney}元</td>
						<td bgcolor="#ff7292" style="font-size: 12px; padding: 5px;">已付款量</td>
						<td style="font-size: 12px; padding: 5px; color: black;">${info.payCnt}个</td>
						<td bgcolor="#ff7292" style="font-size: 12px; padding: 5px;">已付款额</td>
						<td style="font-size: 12px; padding: 5px; color: black;">${info.payMoney}元</td>
						<td bgcolor="#ff7292" style="font-size: 12px; padding: 5px;">未到货量</td>
						<td style="font-size: 12px; padding: 5px; color: black;">${info.arriveCnt}个</td>
						<td bgcolor="#ff7292" style="font-size: 12px; padding: 5px;">已到货量</td>
						<td style="font-size: 12px; padding: 5px; color: black;">${info.arriveCnt}个</td>
						<td bgcolor="#ff7292" style="font-size: 12px; padding: 5px;">已发货量</td>
						<td style="font-size: 12px; padding: 5px; color: black;">${info.sendCnt}个</td>
					</tr>
				</tbody>
			</table>

		</form>
		<form id="report" name="report" method="POST" action="/main/list.do">
			<input type="hidden" id="begin" name="begin" value="">
			<input type="hidden" id="end" name="end" value="">
			<input type="hidden" id="brand" name="brand" value="">
			<input type="hidden" id="userNumber" name="userNumber"
				value="00001"> <input type="hidden" id="payStatus"
				name="payStatus" value=""> <input type="hidden"
				id="arriveStatus" name="arriveStatus" value=""> <input
				type="hidden" id="currPageNum" name="currPageNum" value="1">
			<input type="hidden" id="orderColumn" name="orderColumn" value="">
			<input type="hidden" id="orderMode" name="orderMode" value="desc">
			<input type="hidden" id="pageSize" name="pageSize" value="10">
			<input type="hidden" id="total" name="total" value="14">
		</form>
		<table border="0" cellpadding="0" width="88%" cellspacing="0"
			bgcolor="#ff7292">
			<tbody bgcolor="#ff7292">
				<tr>
					<td style="text-align: left;">&nbsp;
						订单数共${page.count}条记录&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td align="right" width="200">共 <font style="color: #990099;">${page.curPageCount}</font>
						页&nbsp;&nbsp;当前第 <font style="color: #990099;">${page.pageNum}</font>页&nbsp;&nbsp;
					</td>
					<td width="70">转到<input size="3" class="skip" id="go"
						onkeydown="return go_onkeydown(&#39;report&#39;)">页
					</td>
					<td width="30px"><table cellpadding="0" cellspacing="0"
							style="cursor: pointer; height: 22px; width: 100%">
							<tbody>
								<tr>
									<td align="center" class="right_btn first"><img
										src="../html/static_files/back.png" width="13" height="8" alt=""></td>
								</tr>
							</tbody>
						</table></td>
					<td width="30px"><table cellpadding="0" cellspacing="0"
							style="cursor: pointer; height: 22px; width: 100%""="">
							<tbody>
								<tr>
									<td align="center" class="right_btn prev"><img
										src="../html/static_files/pre.png" width="7" height="9" alt=""></td>
								</tr>
							</tbody>
						</table></td>
					<td width="30px"><table cellpadding="0" cellspacing="0"
							style="cursor: pointer; height: 22px; width: 100%">
							<tbody>
								<tr>
									<td align="center" class="right_btn next"><img
										src="../html/static_files/next.png" width="7" height="9" alt=""></td>
								</tr>
							</tbody>
						</table></td>
					<td width="30px"><table cellpadding="0" cellspacing="0"
							style="cursor: pointer; height: 22px; width: 100%">
							<tbody>
								<tr>
									<td align="center" class="right_btn last"><img
										src="../html/static_files/forward.png" width="13" height="8" alt=""></td>
								</tr>
							</tbody>
						</table></td>
				</tr>
			</tbody>
		</table>

		<table class="outside_table maintain_list" cellspacing="1"
			cellpadding="0" style="width: 88%; height: 100%; border: 0px;">
			<tbody>
				<tr>
					<th id="id_col" class="title_bg"
						style="min-width: 20px; width: 20px;" nowrap="nowrap">序号</th>
					<th id="ord_date_col" class="title_bg"
						style="min-width: 50px; width: 50px;" nowrap="nowrap">订单信息</th>
					<th id="gds_img_col" class="title_bg"
						style="min-width: 120px; width: 120px;" nowrap="nowrap">订单商品</th>
					<th id="gds_brand_col" class="title_bg"
						style="min-width: 80px; width: 80px;" nowrap="nowrap">费用</th>
					<th id="gds_code_col" class="title_bg"
						style="min-width: 30px; width: 30px;" nowrap="nowrap">状态</th>
					<th id="ord_memo_col" class="title_bg"
						style="min-width: 80px; width: 80px;" nowrap="nowrap">备注</th>
				</tr>



				<c:forEach items="${list}" var="item">
				<tr class="odd">
			
					<td><font color="black">${item.order.id}</font></td>
					<td><font color="black"> 日期：${item.order.orderTime}<br>
							账号：${item.user.accountNumber}<br> 微信：${item.user.wxName}
					</font></td>
					<td>
						<div style="float: left;">

							<img id="gds_Img" src="${item.goods.imgURL}"
								style="max-width: 100px; max-height: 100px;" alt="">


						</div>
						<div style="float: left; padding-left: 30px; padding-top: 5px;">
							<font color="black">货号：${item.goods.goodNumber}</font><br> <font
								color="black">品牌：${item.brand.brandName}</font><br> <font color="black">尺寸：${item.goods.size}</font><br>
							<font color="black">单价：${item.goods.price / 100.0}</font>
						</div>
					</td>
					<td>
						<div style="float: left; padding-left: 30px; padding-top: 5px;">
							<font color="black">总价：</font><font size="5" color="red">￥
								${item.order.totalPrice / 100.0}</font><br> <font color="black">运费：￥ ${item.order.freight / 100.0}</font><br> <font
								color="black">汇率：${item.order.exchangeRate / 100.0}</font>
						</div>
					</td>
					<td><font color="black">${item.order.orderStatusStr}</font><br> <font
						color="black">${item.order.goodsStatusStr}</font></td>
					<td>${item.order.discription}</td>
				</tr>

				</c:forEach>


			</tbody>
		</table>
	</div>

	<div id="showView"
		style="position: absolute; display: none; z-index: 99999; height: auto; width: auto; left: 0px; top: 0px;"></div>

	<!-- Javascript files -->
	<!-- jQuery -->
	<script src="../html/static_files/jquery.js" type="text/javascript"></script>
	<!-- Bootstrap JS -->
	<script src="../html/static_files/bootstrap.min.js" type="text/javascript"></script>
	<!-- jquery Anchor JS -->
	<script src="../html/static_files/jquery.arbitrary-anchor.js"
		type="text/javascript"></script>
	<!-- jQuery way points -->
	<script src="../html/static_files/waypoints.min.js" type="text/javascript"></script>
	<!-- Respond JS for IE8 -->
	<script src="../html/static_files/respond.min.js" type="text/javascript"></script>
	<!-- HTML5 Support for IE -->
	<script src="../html/static_files/html5shiv.js" type="text/javascript"></script>
	<!-- Custom JS -->
	<script src="../html/static_files/custom.js" type="text/javascript"></script>
	<script src="../html/static_files/jquery.datetimepicker.js"
		type="text/javascript"></script>
	<script src="../html/static_files/uiajax.js" type="text/javascript"></script>
	<script src="../html/static_files/orderlist.js" type="text/javascript"></script>
	<script type="text/javascript">
			$(function() {
				$('#begin').datetimepicker({
					lang : 'ch',
					timepicker : false,
					format : 'Y-m-d',
					validateOnBlur : false
				});
				$('#end').datetimepicker({
					lang : 'ch',
					timepicker : false,
					format : 'Y-m-d',
					validateOnBlur : false
				});
				var begin=$("#begin").val();
				var end=$("#end").val();
			});
		</script>
</body>
</html>