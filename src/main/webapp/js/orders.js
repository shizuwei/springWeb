var app = angular.module("app",[]);

app.controller('myController', function($scope, $http) {
	$scope.user =  {};
	$scope.img =   {};
	$scope.good =  {};
	$scope.order = {};

	function init(){
		$http({
			 method:'POST', 
			 url:'goods/status.do', 
			 data: {}  
		}).success(function(response){
			console.log(JSON.stringify(response));	
			if(response.status == 200)
			{
				$scope.good.status = response.data;
			}
		});
		
		$http({
			 method:'POST', 
			 url:'order/status.do', 
			 data: {}  
		}).success(function(response){
			console.log(JSON.stringify(response));	
			if(response.status == 200)
			{
				$scope.order.status = response.data;
			}
		});
		
		$http({
			 method:'POST', 
			 url:'img/listFolder.do', 
			 data: {}  
		}).success(function(response){
			console.log(JSON.stringify(response));	
			if(response.status == 200)
			{
				$scope.folders = response.data;
			}
		});
		
  
       $scope.setPage = function (pageNo) {
    	    $scope.currentPage = pageNo;
       };
       $scope.pageChanged = function () {
           console.log("pageNo="+ $scope.currentPage);
       };
       
       $scope.maxSize = 5;
       $scope.totalItems = 0;
       $scope.currentPage = 1;

	}
	
	$scope.pageselect = function(){
		  console.log("pageNo="+ $scope.currentPage);
	}

	init();
	
	$scope.user.logout = function(){
		
		$http({
			 method:'POST', 
			 url:'./user/logout.do', 
			 data: {}  
		}).success(function(response){
			console.log(JSON.stringify(response));	
			if(response.status == 200)
			{
				console.log("logout success!");
				$(window.location).attr('href', './login.html');
			}
		});

	}
	
	$scope.img.allCheckClick = function(){
		var allChecked = !$scope.img.checkedAll;
		$scope.img.checkedAll = allChecked;
		for(var img in imgs){
			img.checked = allChecked;
		}	
	}
	
	$scope.search = function(){
		console.log("start search ... ");
		$http({
			 method:'POST', 
			 url:'./order/getOrders.do', 
			 data: {
				orderStatus:$scope.orderStatus,
				goodsStatus:$scope.goodsStatus,
				folder:$scope.folder
			 }
		}).success(function(response){
			console.log(JSON.stringify(response));	
			if(response.status == 200)
			{
				var displayOrderGoods = [];
				var orders = response.data;
				
				for(var i = 0; i < orders.length; i++){
					var order = orders[i];
					for(var j = 0; j < order.orderGoods.length; j++){
						var og = order.orderGoods[j];
						if(j == 0){
							og.span = order.orderGoods.length; //能显示第一列
							og.orderDescription = order.orderDescription;
							og.orderFolder = order.orderFolder;
							og.orderId = order.orderId;
							og.orderPayTime = order.orderPayTime;
							og.orderTotalPrice = order.orderTotalPrice;
						}else{
							og.span = -1; //不能显示第一列
						}
						
						displayOrderGoods.push(og);
					}
				}
				
				$scope.orders = displayOrderGoods;
				console.log("search success!" + JSON.stringify(displayOrderGoods));
			}
		});
	}
	
});
