<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; " pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>英伦买买买</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="../bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet"
	href="../datepicker/css/bootstrap-datepicker3.min.css">
<link rel="stylesheet" href="../customcss/custom.css">

</head>
<body>
	<div class="container">
		<ul class="nav nav-pills">
			<li class="active"><a href="#">Home</a></li>
			<li class="dropdown"><a class="dropdown-toggle"
				data-toggle="dropdown" href="#"> Java <span class="caret"></span>
			</a>
				<ul class="dropdown-menu">
					<li><a href="#">Swing</a></li>
					<li><a href="#">jMeter</a></li>
					<li><a href="#">EJB</a></li>
					<li class="divider"></li>
					<li><a href="#">分离的链接</a></li>
				</ul></li>
			<li><a href="#">PHP</a></li>

			<ul class="nav navbar-nav navbar-right">
				<li><a href="#"><span class="glyphicon glyphicon-user"></span>
						注册</a></li>
				<li><a href="#"><span class="glyphicon glyphicon-log-in"></span>
						登录</a></li>
			</ul>
		</ul>

		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">面板标题</h3>
			</div>
			<div class="panel-body">
				<form id="form" role="form" class="form-horizontal" action="" method="post">
					<div class="form-group">
						<label class="col-sm-2 control-label">用户编码：</label>
						<div class="col-sm-4">
							<input type="text" name="userNumber"
								value="${param.userNumber}" class="form-control"
								placeholder="账户号为必填项" />
						</div>
						<label class="col-sm-2 control-label">商品品牌：</label>
						<div class="col-sm-4">
							<input type="text" value="${param.brand}" class="form-control" name="brand"
								placeholder="商品品牌">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">付款状态：</label>
						<div class="col-sm-4">
							<select name="payStatus" class="form-control">
								<c:forEach items="${payStatus}" var="item">
									<c:choose>
										<c:when test="${param.payStatus == item.code}">
											<option value="${param.payStatus}" selected="selected">${item.note}</option>
										</c:when>
										<c:otherwise>
											<option value="${item.code}">${item.note}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
						</div>
						<label class="col-sm-2 control-label">到货状态：</label>
						<div class="col-sm-4">
							<select name="arriveStatus" class="form-control">
								<c:forEach items="${arriveStatus}" var="item">
									<c:choose>
										<c:when test="${param.arriveStatus == item.code}">
											<option value="${item.code}" selected="selected">${item.note}</option>
										</c:when>
										<c:otherwise>
											<option value="${item.code}">${item.note}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">订单日期：</label>
						<div class="col-sm-10">
							<div class="input-daterange input-group" id="datepicker">
								<input type="text" class="form-control input-sm "
									style="height: 34px" name="begin" value="${param.begin}"/> <span
									class="input-group-addon">到</span> <input type="text"
									class="form-control input-sm " style="height: 34px" name="end" value="${param.end}"/>
							</div>
						</div>
					</div>
					<div class="form-group" style="margin-bottom: 0">
						<label class="col-sm-10 control-label">
							订单数：${info.unPayedCount + info.payedCount}个
							未付款量:${info.unPayedCount}个 
							未付款额:${info.unPayedMoneySum}元
							已付款量:${info.payedCount}个 
							已付款额:${info.payedMoneySum}元
							未到货量:${info.notArrivedCount}个
							已到货量:${info.arrivedCount}个
							已发货量:${info.sendCount}个
							 </label>
						<div class="col-sm-2">
							<button type="submit" class="btn btn-primary pull-right">提交</button>
						</div>
						<input id="page" type="hidden" value="1" name="page" />
					</div>

				</form>
			</div>
		</div>

		<div class="table-responsive">
			<table class="table table-striped table-bordered ">
				<thead>
					<tr>
						<th>序号</th>
						<th >订单信息</th>
						<th>订单商品</th>
						<th>订单费用</th>
						<th>订单状态</th>
						<th>备注</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list}" var="item">
						<tr>
							<td>${item.order.id}</td>
							<td>日期：${item.order.orderTime}<br>
								账号：${item.user.accountNumber}<br> 
								微信：${item.user.wxName}
							</td>

							<td>
								<img src="${item.goods.imgURL}"
								style="max-width: 150px; max-height: 150px;"/>
								<div class="v-align-box">
									货号：${item.goods.goodNumber} <br>
									品牌：${item.brand.brandName} <br>
									尺寸：${item.goods.size}  <br>
									单价：${item.goods.price / 100.0} <br>
								</div>
						   </td>

							<td> 
								<div  class="v-align-box">
									总价：${item.order.totalPrice / 100.0} <br>
									运费：￥${item.order.freight / 100.0} <br>
									汇率：${item.order.exchangeRate / 100.0}<br>
								<div>
							</td>
							<td>
								${item.order.orderStatusStr} <br>
								${item.order.goodsStatusStr}
							</td>
							<td>${item.order.discription}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>

		<ul class="pagination">
		 <c:forEach begin="1" end="${ (page.count - 1) / page.pageSize + 1}" var="each">
		 	<c:choose>
		 		<c:when test="${each == page.pageNum}">
		 			<li class="active">
		 				<a href="#">${each}</a>
		 				<%-- <a href="./list.do?page=${each}&userNumber=${param.userNumber}&brand=${param.brand}&payStatus=${param.payStatus}&arriveStatus=${param.arriveStatus}&begin=${param.begin}&end=${param.end}">${each}</a> --%>
		 			</li>
		 		 </c:when>
		 		 <c:otherwise>
					<li>
						<a href="#">${each}</a>
					</li>
				</c:otherwise>
		 	</c:choose>
			</c:forEach>
		</ul>

	</div>

	<script type="text/javascript" src="../jquery/2.1.1/jquery.min.js"
		charset="UTF-8"></script>
	<script type="text/javascript"
		src="../bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="../datepicker/js/bootstrap-datepicker.min.js"
		type="text/javascript"></script>
	<script src="../datepicker/locales/bootstrap-datepicker.zh-CN.min.js"
		charset="UTF-8" type="text/javascript"></script>


	<script type="text/javascript">
		$('#datepicker').datepicker({
			language : "zh-CN",
			//startView: 2,
			autoclose : true,
			todayHighlight : true,
			//todayBtn: true,
			format : "yyyy-mm-dd"
		});
		
		$(".pagination a").click(function(e){
			$("#page").attr('value',$(this).text());
			$('#form').submit();
			
		});
		
	</script>
</body>

</html>
