Date.prototype.format = function(fmt) { 
     var o = { 
        "M+" : this.getMonth()+1,                 // 月份
        "d+" : this.getDate(),                    // 日
        "h+" : this.getHours(),                   // 小时
        "m+" : this.getMinutes(),                 // 分
        "s+" : this.getSeconds(),                 // 秒
        "q+" : Math.floor((this.getMonth()+3)/3), // 季度
        "S"  : this.getMilliseconds()             // 毫秒
    }; 
    if(/(y+)/.test(fmt)) {
            fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
    }
     for(var k in o) {
        if(new RegExp("("+ k +")").test(fmt)){
             fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
         }
     }
    return fmt; 
}      

function IsPC() {
    var userAgentInfo = navigator.userAgent;
    var Agents = ["Android", "iPhone",
                "SymbianOS", "Windows Phone",
                "iPad", "iPod"];
    var flag = true;
    for (var v = 0; v < Agents.length; v++) {
        if (userAgentInfo.indexOf(Agents[v]) > 0) {
            flag = false;
            break;
        }
    }
    return flag;
}

function uaredirect(f) {
	 try {
	  if (document.getElementById("bdmark") != null) {
	   return
	  }
	  var b = false;
	  if (arguments[1]) {
	   var e = window.location.host;
	   var a = window.location.href;
	   if (isSubdomain(arguments[1], e) == 1) {
	    f = f + "/#m/" + a;
	    b = true
	   } else {
	    if (isSubdomain(arguments[1], e) == 2) {
	     f = f + "/#m/" + a;
	     b = true
	    } else {
	     f = a;
	     b = false
	    }
	   }
	  } else {
	   b = true
	  }
	  if (b) {
	   var c = window.location.hash;
	   if (!c.match("fromapp")) {
	    if ((navigator.userAgent.match(/(iPhone|iPod|Android|ios|SymbianOS)/i))) {
	     location.replace(f)
	    }
	   }
	  }
	 } catch(d) {}
	}
	function isSubdomain(c, d) {
	 this.getdomain = function(f) {
	  var e = f.indexOf("://");
	  if (e > 0) {
	   var h = f.substr(e + 3)
	  } else {
	   var h = f
	  }
	  var g = /^www\./;
	  if (g.test(h)) {
	   h = h.substr(4)
	  }
	  return h
	 };
	 if (c == d) {
	  return 1
	 } else {
	  var c = this.getdomain(c);
	  var b = this.getdomain(d);
	  if (c == b) {
	   return 1
	  } else {
	   c = c.replace(".", "\\.");
	   var a = new RegExp("\\." + c + "$");
	   if (b.match(a)) {
	    return 2
	   } else {
	    return 0
	   }
	  }
	 }
	};