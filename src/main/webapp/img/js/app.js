var app = angular.module("app", []);

app.controller('myController', function($scope, switchSerivce, imgService) {
	$scope.user = {};
	$scope.img = {};
	$scope.good = {};
	$scope.navs = [
		{
			name:"首页",
			active:true
		},
		{
			name:"后台",
			active:false
		},
		];

	$('#loginDialog').modal({
	  keyboard: false,
	  backdrop: 'static', //点击空白不关闭
	})

	function init(){
		$scope.today = new Date().format("yyyy-MM-dd");
		$scope.imgs = imgService.getAll();
	}

    $scope.switch = function(i) {
    	switchSerivce.switchTo(i, $scope.navs);
    }

    $scope.user.login = function(){
    	console.log("user = " + $scope.user.name);
    	if($scope.user.name && $scope.user.name.length == 5){
    		$('#loginDialog').modal('hide');
    		$('#userNameInput').removeClass('has-warning');
    		init();
    	}else{
    		$('#userNameInput').addClass('has-warning');
    	}
    	
    }

    $scope.img.edit = function() {

    	 var editImgs = imgService.getCheckedImgs();

    	 if(editImgs.length > 1){
    	 	alert("一次只能编辑一个");
    	 	return;
    	 }

    	 if (editImgs.length == 1) {
    	 	$scope.img.cur = editImgs[0];
    	 	$("#editImg").modal({keyboard: true})
    	 }
    }

    $scope.img.editName = function(cur, event) {
    	$(event.target).toggleClass("spin");
    }

    $scope.img.update = function() {
    	$scope.imgs = imgService.getAll();
    }

    $scope.img.import = function() {
    	console.log("start import img..");
    	  
    	console.log("import img..");
    }

    $scope.img.upload = function() {
    	console.log("start upload img..");
    	imgService.upload();
    	console.log("upload img..");
    }



	$scope.img.delete = function(){
		imgService.delete();
	}


	$scope.img.submitEdit = function(){
		console.log('submitEdit');
	}

	$scope.good.edit = function(img, g){
		$scope.img.cur = img;
		$scope.good.cur = g;
		$('#editGood').modal({
		  keyboard: false,
		})
		$scope.img.update();
		console.log("good.edit " + img.name + JSON.stringify(g));
	}

	$scope.good.editSize = function(cur,event){
	 	$(event.target).toggleClass("spin");
		console.log("good.editSize " + JSON.stringify(cur));
	}


	$scope.good.delete = function(img, g){
		$scope.img.update();
		console.log("good.delete " + JSON.stringify(g));
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

app.service('imgService', function() {

	this.imgs =  [
			{
				id: 1,
				url: "./img/img1.png",
				name:"xxx",
				checked:false,
				goods:[
					{
						id:1,
						name:'1-1-0 MAX',
						price:111,
						orders:[{
							id:1,
							user:{
								id:1,
								name:'xiaowang'
							},
							count:2,
							status:'已经付款'
						}],
						status:'已经到货'
					},
					{
						id:1,
						name:'1-1-0 MIN',
						price:222,
						orders:[{
							id:1,
							user:{
								id:2,
								name:'xiaowang2'
							},
							count:22,
							status:'已经付款'
						}],
						status:'已经到货'

					}
				]
			},
			{
				id: 2,
				url: "./img/img1.png",
				name:"yyy",
				checked:false,
				goods:[
					{
						id:1,
						name:'1-1-0 MAX',
						price:111,
						orders:[{
							id:1,
							user:{
								id:1,
								name:'xiaowang'
							},
							count:2,
							status:'已经付款'
						}]
					},
					{
						id:2,
						name:'1-1-0 MIN',
						price:111,
						orders:[{
							id:1,
							user:{
								id:1,
								name:'xiaowang'
							},
							count:2,
							status:'已经付款'
						}]
					}
				]
			}
		]

	this.getAll = function() {
		return this.imgs;
	}

	this.deleteMark = function(id){
		console.log("deleteMark");
		// 后台删除 再在Imgs中找到当前img 并更新
	}

	this.upload = function(){
		$('#imgFileInput').fileinput("upload");
	}

    this.delete = function() {
    	for(var i = 0; i < this.imgs.length; i++){
    		var img = this.imgs[i];
    		if (img.checked) {
    			console.log("checked = " + img.url);
    			this.imgs.splice(i, 1);
    		}
    	}
    	/* 可以调用批量删除接口, 然后update*/
    }

    this.getCheckedImgs = function() {
    	var checkedImgs = [];
    	for(var i = 0; i < this.imgs.length; i++){
    		var img = this.imgs[i];
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
		//language:'zh',
		uploadUrl:'./xxxxurl',
		showUpload:true,
		showRemove:true,
		dropZoneEnabled:false,
		showCaption:true,
		allowedPreviewTypes: ['image'],  
        allowedFileTypes: ['image'],  
        allowedFileExtensions:  ['jpg', 'png'],  
        maxFileSize : 2000,  
        maxFileCount: 5,
        uploadAsync: true,
        previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
        msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
        uploadExtraData: function(previewId, index) {   //额外参数的关键点
                    var obj = {};
                    obj.folder = $('#img-folder').val();
                    obj.width = $('#img-width').val();
                    obj.height = $('#img-height').val();
                    console.log(obj);
                    return obj;
                }
	}).on('filepreupload', function(event, data, previewId, index) {     //上传中
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