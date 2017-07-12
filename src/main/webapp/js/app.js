var app = angular.module("app", ['ui.bootstrap']);

app.controller('myController', function($scope, $http, switchSerivce, imgService) {
	$scope.user =  {};
	$scope.img =   {};
	$scope.good =  {};
	$scope.order = {};
	$scope.navs = [
		{
			name:"首页",
			active:false
		},
		{
			name:"后台",
			active:true
		},
	];
	
	function updateAll(){
		
		imgService.update($scope);
	}

	function init(){
		
		$scope.today = new Date().format("yyyy-MM-dd");
		
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
		
	  
       $scope.setPage = function (pageNo) {
    	    $scope.currentPage = pageNo;
       };
       $scope.pageChanged = function () {
           console.log("pageNo="+ $scope.currentPage);
       };
       
       $scope.maxSize = 5;
       $scope.totalItems = 0;
       $scope.currentPage = 1;
		
		
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
			 url:'user/list.do', 
			 data: {}  
		}).success(function(response){
			console.log(JSON.stringify(response));	
			if(response.status == 200)
			{
				$scope.user.list = response.data;
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
		
		updateAll();
		
	}
	
	$scope.pageselect = function(){
		
		  console.log("pageNo="+ $scope.currentPage);
          imgService.update($scope);
	}
	
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
	
	
	init();
    $scope.switch = function(i) {
    	switchSerivce.switchTo(i, $scope.navs);
    }

    $scope.img.edit = function() {

    	 var editImgs = imgService.getCheckedImgs($scope);

    	 if(editImgs.length > 1){
    	 	alert("一次只能编辑一个");
    	 	return;
    	 }

    	 if (editImgs.length == 1) {
    	 	$scope.img.cur = editImgs[0];
    	 	
    	 	$("#editImg").modal({keyboard: true})
    	 }
    }
    
    function getGoodsIndex(goods, g){
       	for(var i =0; i < goods.length;i++){
    		if(goods[i].size == g.size){
    			return i;
    		}
    	}
       	
       	return -1;
       	
    }
    
    $scope.img.addGood = function() {

    	console.log("input:"+$scope.img.cur.price+$scope.img.cur.size);
    	var cur = $scope.img.cur;
    	var obj = {imgId:cur.imgId,size:cur.inputSize,goodsPrice:cur.inputPrice,goodsName:cur.imgName};
    	
    	var idx = getGoodsIndex(cur.goods, obj);
    	if(idx >= 0)
    	{
    		return ;
    	}
    	
    	// $(event.target).toggleClass("spin");
    	
    	 $http({
    	     method:'POST', 
    		 url:'goods/add.do', 
    	     data: obj  
    	    }).success(function(response){
    	    	console.log(JSON.stringify(response));
    	    	
    	    	if(response.status == 200)
            	{
                	var data = response.data;
                	var newObj = {id: data.id, goodsName: obj.goodsName, goodsPrice: obj.goodsPrice, imgId: obj.imgId, size: obj.size};
                	cur.goods.push(newObj);
        			
        			// $(event.target).toggleClass("spin");
                	console.log("success!");
            	}           
    	      
    	    }).error(function(response){
    	      console.log("error");
    	      console.log(JSON.stringify(response));
    	    })
   }

    $scope.img.editName = function(cur, event) {
    	$(event.target).toggleClass("spin");
    	$.ajax({ 
            type:"POST", 
            url:"img/edit.do", 
            dataType:"json",      
            contentType:"application/json",               
            data:JSON.stringify({imgId:cur.imgId,imgName:cur.imgName}), 
            success:function(data){ 
            	var data = data.data;
    			console.log(JSON.stringify(data));
    			$(event.target).toggleClass("spin");                           
            } 
         });  	
    }

    $scope.img.update = function() {
    	 imgService.update($scope);
    	
    }
    $scope.img.update();
    
    $scope.img.import = function() {
    	console.log("start import img..");
    	  
    	console.log("import img..");
    }

    $scope.img.upload = function() {
    	console.log("start upload img..");
    	imgService.upload();
    	$scope.folder = $('#img-folder').val();
    	$scope.folders.push($scope.folder);
    	console.log("upload img..");
    }

	$scope.img.delete = function(){
		imgService.delete($scope);
	}
	

	$scope.img.editStatus = function(){
		imgService.delete($scope);
	}


	$scope.img.submitEdit = function(){
		console.log('submitEdit');
	}

/*
 * 添加订单信息： 包括：用户，多少件，订单状态。
 */
	$scope.good.edit = function(img, g){
		$scope.img.cur = img;
		$scope.good.cur = g;
		$scope.selectGoodsStatus = 0;
		$('#editGood').modal({
		  keyboard: false,
		})
		$scope.img.update();
		console.log("good.edit " + img.name + JSON.stringify(g));
	}
	
	
	$scope.good.editInfo = function(cur,event){
	 	$(event.target).toggleClass("spin");
	 	console.log("good.editSize " + JSON.stringify(cur));
		 $http({
			 method:'POST', 
			 url:'goods/editSize.do', 
			 data: cur  
		}).success(function(response){
				console.log(JSON.stringify(response));	
				if(response.status == 200)
				{
					$(event.target).toggleClass("spin");
				}
			});
	}


	$scope.good.delete = function(img, g){
		console.log("good.delete " + JSON.stringify(g));
		var idx = getGoodsIndex(img.goods, g);
		if(idx < 0)
		{
			return;
		}

		 $http({
			 method:'POST', 
			 url:'goods/delete.do', 
			 data: g  
		}).success(function(response){
				console.log(JSON.stringify(response));	
				if(response.status == 200)
				{
					img.goods.splice(idx, 1);
				}
			});
	}
	
	$scope.order.addOrder = function(){
		var curImg = $scope.img.cur;
		var curGood = $scope.good.cur;
		var curUserId = parseInt($scope.selectedUserId);
		var curOrderStatus = parseInt($scope.selectedOrderstatus);
		var curGoodCnt = parseInt($scope.curGoodCnt);
		var curGoodsStatus = parseInt($scope.selectedGoodsStatus);
		console.log("addOrder...");
		
		var order = {goodsId:curGood.goodsId,goodsCnt:curGoodCnt,orderStatus:curOrderStatus,goodsStatus:curGoodsStatus,userId:curUserId};
		$http({
			 method:'POST', 
			 url:'order/add.do', 
			 data: order  
		}).success(function(response){
				console.log(JSON.stringify(response));	
				if(response.status == 200)
				{	
					var hasOrder = false;
					if(curGood.orders != null || curGood.orders.length == 0){
						for(var i = 0; i < curGood.orders.length; i++ ){
							var o = curGood.orders[i];
							if(o.userId == curUserId){
								hasOrder = true;
							}
						}
					}
					if(!hasOrder){
						response.data.order.orderGoods=[order];
						curGood.orders.push(response.data.order);
					}
					console.log("..ok!")
				}else{
					console.log("..fail!")
				}
		});
		
	}
	
	$scope.order.finishEdit = function(){
		imgService.update($scope);
	}
	
	$scope.order.editOrder = function(orderGoods,event){
		var curImg = $scope.img.cur;
		var curGood = $scope.good.cur;
		
		$(event.target).toggleClass("spin");
		$http({
			 method:'POST', 
			 url:'order/editOrderGoods.do', 
			 data: orderGoods  
		}).success(function(response){
				console.log(JSON.stringify(response));	
				if(response.status == 200)
				{
					$(event.target).toggleClass("spin");
					// updateAll();
					console.log("..ok!")
				}else{
					console.log("..fail!")
				}
		});
		
	}
	
	$scope.goodsStatusChanged = function(o){
		console.log(JSON.stringify(o));	
	}
	
	$scope.order.delOrder = function(orderGoods,event){
		var curImg = $scope.img.cur;
		var curGood = $scope.good.cur;
		$(event.target).toggleClass("spin");
		$http({
			 method:'POST', 
			 url:'order/delOrderGoods.do', 
			 data: orderGoods  
		}).success(function(response){
				console.log(JSON.stringify(response));	
				if(response.status == 200)
				{
					$(event.target).toggleClass("spin");
					updateAll();
					console.log("..ok!")
				}else{
					console.log("..fail!")
				}
		});
		
	}

});


app.service('switchSerivce', function() {
    this.switchTo = function (i, navs) {
    	for (var j = 0; j < navs.length; j++) {
    		if ( i == j) {
    			navs[j].active = true;
    			console.log(i);
    		} 
    		else {
    			navs[j].active = false;
    		}
    	}
    }
});

app.service('imgService', function($http) {

	this.update = function($scope) {
		$http({url:"./img/list.do",
			 method:'POST', 
			params:{pageNum:$scope.currentPage,pageSize:$scope.maxSize}, 
			data:{folder:$scope.folder,userId:$scope.userId, orderStatus:$scope.orderStatus, goodsStatus:$scope.goodsStatus}
		})
		.success(function(data,status,headers,config){
			$scope.imgs = data.data;
			if(data.pageDto){
				$scope.totalItems = data.pageDto.count;
			}
			console.log(JSON.stringify(data));
		});
	}

	this.upload = function(){
		$('#imgFileInput').fileinput("upload");
	}

    this.delete = function($socpe) {
    	for(var i = 0; i < $socpe.imgs.length; i++){
    		var img = $socpe.imgs[i];
    		if (img.checked) {
    			console.log("checked = " + img.url);
    			$http({url:"./img/delete.do",params:{id:img.id}}).success(function(data,status,headers,config){
    				$scope.imgs = data.data;
    				console.log(JSON.stringify(data));
    			});
    		}
    	}
    	/* 可以调用批量删除接口, 然后update */
    }

    this.getCheckedImgs = function($socpe) {
    	var checkedImgs = [];
    	for(var i = 0; i < $socpe.imgs.length; i++){
    		var img = $socpe.imgs[i];
    		if (img.checked) {
    			console.log("checked = " + img.url);
    			checkedImgs.push(img);
    		}
    	}
    	return checkedImgs;
    }
});


app.service('loginInfo', function () {

})



$(document).ready(function(){
	$('#imgFileInput').fileinput({
		// language:'zh',
		uploadUrl:'./img/upload.do',
		showUpload:true,
		showRemove:true,
		dropZoneEnabled:false,
		showCaption:true,
		allowedPreviewTypes: ['image'],  
        allowedFileTypes: ['image'],  
        allowedFileExtensions:  ['jpg', 'png'],  
        maxFileSize : 2000,  
        maxFileCount: 30,
        uploadAsync: true,
        previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
        msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
        uploadExtraData: function(previewId, index) {   // 额外参数的关键点
                    var obj = {};
                    obj.folder = $('#img-folder').val();
                    obj.width = $('#img-width').val();
                    obj.height = $('#img-height').val();
                    console.log(obj);
                    return obj;
                }
	}).on('filepreupload', function(event, data, previewId, index) {     // 上传中
            var form = data.form, files = data.files, extra = data.extra,
            response = data.response, reader = data.reader;
            console.log('文件正在上传');
    }).on("filebatchuploadsuccess", function (event, data, previewId, index) {
		 var obj = data.response;
		 alert(JSON.stringify(obj));
	}).on("filebatchselected",function(event, files) { 
		console.log("files = " + files)  
    }).on("fileuploaded", function(event, data) {  
    	 console.log(data.response);  
    });
});