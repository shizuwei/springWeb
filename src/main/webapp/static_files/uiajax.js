var obj_; // 显示控件
var obt_; // 隐藏字段
var showView;
var if_checkP = 0;
var if_checkF = 0;
var base_id = 1;

$(document).on('opening', '.remodal', function() {
	$('.container').css('overflow', 'scroll');
});
$(document).on('closing', '.remodal', function() {
	$('.container').css('overflow', '');
});
$("body").click(function() {
	checkPosition();
});
var nice = $("html").niceScroll();


$('.clearDate').click(function() {
	$(this).prev().val('');
});
function clearDate2(id, from) {
	if (from) {
			$("#" + id, document.forms[from]).val('');
	} else {
			$("#" + id).val('');
	}
}

function getNowFormatDate(seperator) {
	var date = new Date();
	if (!seperator) {
		seperator = "-";
	}
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	var strDate = date.getDate();
	if (month >= 1 && month <= 9) {
		month = "0" + month;
	}
	if (strDate >= 0 && strDate <= 9) {
		strDate = "0" + strDate;
	}
	var currentdate = year + seperator + month + seperator + strDate;
	return currentdate;
}

function getFormatDate(num, seperator) {
	var date = new Date();
	if (!seperator) {
		seperator = "-";
	}
	date.setDate(date.getDate() + num);
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	var strDate = date.getDate();
	if (month >= 1 && month <= 9) {
		month = "0" + month;
	}
	if (strDate >= 0 && strDate <= 9) {
		strDate = "0" + strDate;
	}
	var currentdate = year + seperator + month + seperator + strDate;
	return currentdate;
}

function getNowFormatDateTime() {
	var date = new Date();
	var seperator1 = "-";
	var seperator2 = ":";
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	var strDate = date.getDate();
	if (month >= 1 && month <= 9) {
		month = "0" + month;
	}
	if (strDate >= 0 && strDate <= 9) {
		strDate = "0" + strDate;
	}
	var currentdate = year + seperator1 + month + seperator1 + strDate + " "
			+ date.getHours() + seperator2 + date.getMinutes() + seperator2
			+ date.getSeconds();
	return currentdate;
}

function clearNoNum(obj) {
	obj.value = obj.value.replace(/[^\d.]/g, "");
	obj.value = obj.value.replace(/^\./g, "");
	obj.value = obj.value.replace(/\.{2,}/g, ".");
	obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$",
			".");
}

function clearNoNum2(obj) {
	obj.value = obj.value.replace(/[^\d]/g, "");
}

function clearNoNumFS(obj) {
	obj.value = obj.value.replace(/[^\d.-]/g, "");
	obj.value = obj.value.replace(/^\./g, "");
	obj.value = obj.value.replace(/\.{2,}/g, ".");
	obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$",
			".");
	if(obj.value.lastIndexOf('-')>0){
		obj.value = '';
	}
}

function getUserStr(id) {
	var str = '';
	for (var i = 0; i < userDate.length; i++) {
		if (id == '' || userDate[i].pid == id) {
			str += userDate[i].id + '^' + userDate[i].name + '^' + '$';
		}
	}
	return str;
}

function getDeptStr(id, name) {
	var str = '';
	for (var i = 0; i < deptDate.length; i++) {
		if (deptDate[i].pid == id) {
			str += deptDate[i].id + '^' + deptDate[i].name + '^' + '$';
		}
	}
	return str;
}

function getUpDeptId(id) {
	for (var i = 0; i < deptDate.length; i++) {
		if (id != '' && deptDate[i].id == id) {
			return deptDate[i].pid;
		}
	}
	return -1;
}

function getDeptName(id) {
	for (var i = 0; i < deptDate.length; i++) {
		if (id != '' && deptDate[i].id == id) {
			return deptDate[i].name;
		}
	}
}

function getDistrictStr(id, name) {
	var str = '';
	for (var i = 0; i < districtDate.length; i++) {
		if (id != '' && districtDate[i].pid == id) {
			str += districtDate[i].id + '^' + districtDate[i].name + '^' + '$';
		}
		if (name != ''
				&& districtDate[i].spell.toLowerCase().indexOf(name) == 0) {
			str += districtDate[i].id + '^' + districtDate[i].name + '^' + '$';
		}
	}
	return str;
}

function getUpDisId(id) {
	for (var i = 0; i < districtDate.length; i++) {
		if (id != '' && districtDate[i].id == id) {
			return districtDate[i].pid;
		}
	}
	return -1;
}

function getDisName(id) {
	for (var i = 0; i < districtDate.length; i++) {
		if (id != '' && districtDate[i].id == id) {
			return districtDate[i].name;
		}
	}
}

function getDistrictReg(id) {
	var str = '';
	for (var i = 0; i < districtDate.length; i++) {
		if (id != '' && districtDate[i].id == id) {
			str = districtDate[i];
			break;
		}
	}
	return str;
}

function setDeptValue(id, obj_id, from) {
	if (!id) {
		$("#" + obj_id).val("").next().val("").next().val("").next().val("");
		return;
	}
	var ids1 = 1, ids2 = 1;
	ids1 = id;
	if (ids1 != base_id) {
		ids2 = getUpDeptId(id);
	}
	if (from) {
		if (ids2 != base_id) {
			$("#" + obj_id, document.forms[from]).val(getDeptName(ids2)).next()
					.val(ids2).next().val(getDeptName(ids1)).next().val(ids1);
		} else if (ids1 != base_id) {
			$("#" + obj_id, document.forms[from]).val(getDeptName(ids1)).next()
					.val(ids1).next().val("").next().val("");
		}
	} else {
		if (ids2 != base_id) {
			$("#" + obj_id).val(getDeptName(ids2)).next().val(ids2).next().val(
					getDeptName(ids1)).next().val(ids1);
		} else if (ids1 != base_id) {
			$("#" + obj_id).val(getDeptName(ids1)).next().val(ids1).next().val(
					"").next().val("");
		}
	}
}

function setDisValue(id, obj_id, reg_obj) {
	if (!id) {
		$("#" + obj_id).val("").next().val("").next().val("").next().val("")
				.next().val("").next().val("");
		return;
	}
	var ids1 = 1, ids2 = 1, ids3 = 1;
	ids1 = id;
	if (ids1 != base_id) {
		ids2 = getUpDisId(ids1);
	}
	if (ids2 != base_id) {
		ids3 = getUpDisId(ids2);
	}
	if (ids3 != base_id) {
		$("#" + obj_id).val(getDisName(ids3)).next().val(ids3).next().val(
				getDisName(ids2)).next().val(ids2).next().val(getDisName(ids1))
				.next().val(ids1);
	} else if (ids2 != base_id) {
		$("#" + obj_id).val(getDisName(ids2)).next().val(ids2).next().val(
				getDisName(ids1)).next().val(ids1).next().val("").next()
				.val("");
	} else if (ids1 != base_id) {
		$("#" + obj_id).val(getDisName(ids1)).next().val(ids1).next().val("")
				.next().val("").next().val("").next().val("");
	}
	if (reg_obj && ids3 != base_id) {
		var diss = getDistrictReg(id);
		$("#" + reg_obj + "Show").html("门牌规则(" + diss.nModel + ")");
		$("#" + reg_obj + "Hide").val(diss.nRegexp);
	}
}

function showDeptByName(obje, level, name) {
	var str = "";
	var errorStr = "无下级部门";
	if (level == 1) {
		str = getDeptStr(base_id, '');
	} else if (level == 2) {
		var deptid = $(obje).prev().val();
		if (deptid) {
			str = getDeptStr(deptid, '');
		} else {
			errorStr = "请选择上级部门";
		}
	}
	obj_ = obje;
	// var width = 120;
	var width = $(obje).outerWidth();
	var temp = "";
	if (str == "") {
		temp += "<div id='boxscroll' class='comboDiv'><div class=\"comboOp reset\" style=\"text-align:center;padding: 0;\" onclick=\"CloseDivId();\">"
				+ errorStr + "</div></div>";
	} else {
		var objV = $(obje).val();
		var values = formatStr(str, "$");
		var count = 0;
		var divclass = "";
		var content = "";
		for (var i = 0; i < values.length; i++) {
			var d = formatStr(values[i], "^");
			if (d[1].indexOf(name) > -1) {
				count++;
				if (d[1] == objV) {
					divclass = "comboOp comboOp-selected";
				} else {
					divclass = "comboOp";
				}
				content += "<div class=\"" + divclass
						+ "\" onclick=\"onChooseDept('" + d[0] + "','" + d[1]
						+ "'," + level + ");\">" + d[1] + "</div>";
			}
		}
		var heigth = 0;
		width = 0;
		if (count > 8) {
			width = 475;
			if (count > 30) {
				heigth = 220;
			} else if (count > 12) {
				heigth = 22 * (count / 3);
			} else {
				heigth = 88;
			}
		} else if (count > 4) {
			width = 320;
			heigth = 88;
		} else {
			width = 160;
			heigth = 88;
		}
		temp = "<div id='boxscroll' class='comboDiv special' style='height: "
				+ heigth + "px;'>" + content + "</div>";
		var required = $(obje).data("req");
		if (!required) {
			temp += "<div class='comboDiv'><div class='comboOp reset' style=\"text-align:center;padding: 0;\" onclick=\"onChooseDept('','',"
					+ level + ");\">重&nbsp;&nbsp;置</div></div>";
		}
	}
	$("#showView").html(temp).css("width", width);
	displayLayer();
}

function showDept(obje, level) {
	var str = "";
	var errorStr = "无下级部门";
	if (level == 1) {
		str = getDeptStr(base_id, '');
	} else if (level == 2) {
		var deptid = $(obje).prev().val();
		if (deptid) {
			str = getDeptStr(deptid, '');
		} else {
			errorStr = "请选择上级部门";
		}
	}
	obj_ = obje;
	// var width = 120;
	var width = $(obje).outerWidth();
	var temp = "";
	if (str == "") {
		temp += "<div id='boxscroll' class='comboDiv'><div class=\"comboOp reset\" style=\"text-align:center;padding: 0;\" onclick=\"CloseDivId();\">"
				+ errorStr + "</div></div>";
	} else {
		var objV = $(obje).val();
		var values = formatStr(str, "$");
		var divclass = "";
		var content = "";
		for (var i = 0; i < values.length; i++) {
			var d = formatStr(values[i], "^");
			if (d[1] == objV) {
				divclass = "comboOp comboOp-selected";
			} else {
				divclass = "comboOp";
			}
			content += "<div class=\"" + divclass
					+ "\" onclick=\"onChooseDept('" + d[0] + "','" + d[1]
					+ "'," + level + ");\">" + d[1] + "</div>";
		}
		var heigth = 0;
		width = 0;
		if (values.length > 8) {
			width = 475;
			if (values.length > 30) {
				heigth = 220;
			} else if (values.length > 12) {
				heigth = 22 * (values.length / 3);
			} else {
				heigth = 88;
			}
		} else if (values.length > 4) {
			width = 320;
			heigth = 88;
		} else {
			width = 160;
			heigth = 88;
		}
		temp = "<div id='boxscroll' class='comboDiv special' style='height: "
				+ heigth + "px;'>" + content + "</div>";
		var required = $(obje).data("req");
		if (!required) {
			temp += "<div class='comboDiv'><div class='comboOp reset' style=\"text-align:center;padding: 0;\" onclick=\"onChooseDept('','',"
					+ level + ");\">重&nbsp;&nbsp;置</div></div>";
		}
	}
	$("#showView").html(temp).css("width", width);
	displayLayer();
}

function onChooseDept(id, name, level) {
	$(obj_).val(name).next().val(id).next().val('').next().val('');
	if (level == 1) {
		$(obj_).next().next().next().next().val('').next().val('');
	}
	CloseDivId();
}

// 选择部门内的用户
function showDeptUser(obje) {
	var str = "";
	var deptid = $(obje).prev().val();
	if (!deptid) {
		deptid = $(obje).prev().prev().prev().val();
		if (!deptid || deptid == base_id) {
			deptid = "";
		}
	}
	str = getUserStr(deptid, '');
	obj_ = obje;
	var width = $(obje).outerWidth();
	var temp = "";
	if (str == "") {
		temp += "<div id='boxscroll' class='comboDiv'><div class=\"comboOp reset\" style=\"text-align:center;padding: 0;\" onclick=\"CloseDivId();\">此部门无员工</div></div>";
	} else {
		var objV = $(obje).val();
		var h_id = $(obje).next().attr("id");
		obt_ = document.getElementById(h_id);
		var values = formatStr(str, "$");
		var divclass = "";
		for (var i = 0; i < values.length; i++) {
			var userInfo = formatStr(values[i], "^");
			if (userInfo[1] == objV) {
				divclass = "comboOp comboOp-selected";
			} else {
				divclass = "comboOp";
			}
			temp += "<div class='" + divclass + "' onclick=\"obj_.value = '"
					+ userInfo[1] + "';obt_.value = '" + userInfo[0]
					+ "';CloseDivId();\">" + userInfo[1] + "</div>";
		}
		var heigth = 0;
		width = 0;
		if (values.length > 8) {
			width = 370;
			if (values.length > 30) {
				heigth = 220;
			} else if (values.length > 12) {
				heigth = 22 * (values.length / 3);
			} else {
				heigth = 88;
			}
		} else if (values.length > 4) {
			width = 250;
			heigth = 88;
		} else {
			width = 120;
			heigth = 88;
		}
		temp = "<div id='boxscroll' class='comboDiv' style='height: " + heigth
				+ "px;'>" + temp + "</div>";
		var required = $(obje).data("req");
		if (!required) {
			temp += "<div class='comboDiv'><div class='comboOp reset' style=\"text-align:center;padding: 0;\" onclick=\"obj_.value = '';obt_.value = '';CloseDivId();\">重&nbsp;&nbsp;置</div></div>";
		}
	}
	$("#showView").html(temp).css("width", width);
	displayLayer();
}

var showVs = new Array();
var hideVs = new Array();
var selVs = new Array();

// 多选：str显示值，obje当前控件，str_h隐藏值
function comboMultiL(str, obje, str_h, fn) {
	if (str == "") {
		CloseDivId();
		return;
	}
	showVs = new Array();
	hideVs = new Array();
	selVs = new Array();
	var ifhide = false;
	obj_ = obje;
	var objV = $(obje).val();
	var objid = $(obje).attr('id');
	objV = "$" + objV;
	var temp = "";
	showVs = formatStr(str, "$");
	if (str_h) {
		ifhide = true;
		var h_id = $(obje).next().attr("id");
		obt_ = document.getElementById(h_id);
		hideVs = formatStr(str_h, "$");
	}
	var divclass = "";
	for (var i = 0; i < showVs.length; i++) {
		if (objV.indexOf("$" + showVs[i] + "$") > -1) {
			selVs[i] = 1;
			divclass = "comboOp comboOp-selected";
		} else {
			selVs[i] = 0;
			divclass = "comboOp";
		}
		temp += "<div class='" + divclass + "' onclick=\"onchooseMultiL("
				+ ifhide + "," + i + ",this,'" + fn + "');\">" + showVs[i]
				+ "</div>";
	}
	var heigth = 0;
	var width = 0;
	if (showVs.length > 8) {
		width = 370;
		if (showVs.length > 30) {
			heigth = 220;
		} else if (showVs.length > 12) {
			heigth = 22 * (showVs.length / 3);
		} else {
			heigth = 88;
		}
	} else if (showVs.length > 5) {
		width = 250;
		heigth = 88;
	} else {
		width = 120;
		heigth = 88;
	}
	temp = "<div id='boxscroll' class='comboDiv' style='height: " + heigth
			+ "px;'>" + temp + "</div>";
	var required = $(obje).data("req");
	if (!required) {
		temp += "<div class='comboDiv'><div class='comboOp reset' style=\"text-align:center;padding: 0;\" onclick=\"obj_.value = '';";
		if (str_h) {
			temp += "obt_.value = '';";
		}
		temp += "CloseDivId();\">重&nbsp;&nbsp;置</div></div>";
	}
	$("#showView").html(temp).css("width", width);
	displayLayer();
}

function onchooseMultiL(ifhide, count, _this, fn) {
	var _selVs = selVs;
	if (selVs[count] == 1) {
		selVs[count] = 0;
		_this.className = 'comboOp';
	} else {
		selVs[count] = 1;
		_this.className = 'comboOp comboOp-selected';
	}
	var objvalue = "";
	for (var i = 0; i < selVs.length; i++) {
		if (selVs[i] == 1) {
			objvalue += showVs[i] + '$';
		}
	}
	obj_.value = objvalue;

	if (ifhide) {
		var obj_hvalue = "";
		for (var i = 0; i < selVs.length; i++) {
			if (selVs[i] == 1) {
				obj_hvalue += hideVs[i] + '$';
			}
		}
		obt_.value = obj_hvalue;
	}
	eval(fn + "('" + objvalue + "')");
}
// 多选：str显示值，obje当前控件，str_h隐藏值
function comboMulti(str, obje, str_h) {
	if (str == "") {
		CloseDivId();
		return;
	}
	showVs = new Array();
	hideVs = new Array();
	selVs = new Array();
	var ifhide = false;
	obj_ = obje;
	var objV = $(obje).val();
	objV = "$" + objV;
	var temp = "";
	showVs = formatStr(str, "$");
	if (str_h) {
		ifhide = true;
		var h_id = $(obje).next().attr("id");
		obt_ = document.getElementById(h_id);
		hideVs = formatStr(str_h, "$");
	}
	var divclass = "";
	for (var i = 0; i < showVs.length; i++) {
		if (objV.indexOf("$" + showVs[i] + "$") > -1) {
			selVs[i] = 1;
			divclass = "comboOp comboOp-selected";
		} else {
			selVs[i] = 0;
			divclass = "comboOp";
		}
		temp += "<div class='" + divclass + "' onclick='onchooseMulti("
				+ ifhide + "," + i + ",this);'>" + showVs[i] + "</div>";
	}
	var heigth = 0;
	var width = 0;
	if (showVs.length > 8) {
		width = 370;
		if (showVs.length > 30) {
			heigth = 220;
		} else if (showVs.length > 12) {
			heigth = 22 * (showVs.length / 3);
		} else {
			heigth = 88;
		}
	} else if (showVs.length > 5) {
		width = 250;
		heigth = 88;
	} else {
		width = 120;
		heigth = 88;
	}
	temp = "<div id='boxscroll' class='comboDiv' style='height: " + heigth
			+ "px;'>" + temp + "</div>";
	var required = $(obje).data("req");
	if (!required) {
		temp += "<div class='comboDiv'><div class='comboOp reset' style=\"text-align:center;padding: 0;\" onclick=\"obj_.value = '';";
		if (str_h) {
			temp += "obt_.value = '';";
		}
		temp += "CloseDivId();\">重&nbsp;&nbsp;置</div></div>";
	}
	$("#showView").html(temp).css("width", width);
	displayLayer();
}

function onchooseMulti(ifhide, count, _this) {
	var _selVs = selVs;
	if (selVs[count] == 1) {
		selVs[count] = 0;
		_this.className = 'comboOp';
	} else {
		selVs[count] = 1;
		_this.className = 'comboOp comboOp-selected';
	}
	var objvalue = "";
	for (var i = 0; i < selVs.length; i++) {
		if (selVs[i] == 1) {
			objvalue += showVs[i] + '$';
		}
	}
	obj_.value = objvalue;

	if (ifhide) {
		var obj_hvalue = "";
		for (var i = 0; i < selVs.length; i++) {
			if (selVs[i] == 1) {
				obj_hvalue += hideVs[i] + '$';
			}
		}
		obt_.value = obj_hvalue;
	}
}
// 单选：str显示值，obje当前控件，str_h隐藏值
function combo(str, obje, str_h) {
	if (str == "") {
		CloseDivId();
		return;
	}
	obj_ = obje;
	var objV = $(obje).val();
	var temp = "";
	var values = formatStr(str, "$");
	var hideValues;
	if (str_h) {
		var h_id = $(obje).next().attr("id");
		obt_ = document.getElementById(h_id);
		hideValues = formatStr(str_h, "$");
	}
	var divclass = "";
	for (var i = 0; i < values.length; i++) {
		if (values[i] == objV) {
			divclass = "comboOp comboOp-selected";
		} else {
			divclass = "comboOp";
		}
		temp += "<div class='" + divclass + "' onclick=\"obj_.value = '"
				+ values[i] + "';";
		if (str_h) {
			temp += "obt_.value = '" + hideValues[i] + "';";
		}
		temp += "CloseDivId();\">" + values[i] + "</div>";
	}
	var heigth = 0;
	var width = 0;
	if (values.length > 8) {
		width = 370;
		if (values.length > 30) {
			heigth = 220;
		} else if (values.length > 12) {
			heigth = 22 * (values.length / 3);
		} else {
			heigth = 88;
		}
	} else if (values.length > 4) {
		width = 250;
		heigth = 88;
	} else {
		width = 120;
		heigth = 88;
	}
	temp = "<div id='boxscroll' class='comboDiv' style='height: " + heigth
			+ "px;'>" + temp + "</div>";
	var required = $(obje).data("req");
	if (!required) {
		temp += "<div class='comboDiv'><div class='comboOp reset' style=\"text-align:center;padding: 0;\" onclick=\"obj_.value = '';";
		if (str_h) {
			temp += "obt_.value = '';";
		}
		temp += "CloseDivId();\">重&nbsp;&nbsp;置</div></div>";
	}
	$("#showView").html(temp).css("width", width);
	displayLayer();
}
// 单选：str显示值，obje当前控件，str_h隐藏值
function comboL(str, obje, str_h, fn) {
	if (str == "") {
		CloseDivId();
		return;
	}
	obj_ = obje;
	var objV = $(obje).val();
	var temp = "";
	var values = formatStr(str, "$");
	var hideValues;
	if (str_h) {
		var h_id = $(obje).next().attr("id");
		obt_ = document.getElementById(h_id);
		hideValues = formatStr(str_h, "$");
	}
	var divclass = "";
	for (var i = 0; i < values.length; i++) {
		if (values[i] == objV) {
			divclass = "comboOp comboOp-selected";
		} else {
			divclass = "comboOp";
		}
		temp += "<div class='" + divclass + "' onclick=\"obj_.value = '"
				+ values[i] + "';";
		if (str_h) {
			temp += "obt_.value = '" + hideValues[i] + "';";
		}
		temp += fn + "('" + values[i] + "');CloseDivId();\">" + values[i]
				+ "</div>";
	}
	var heigth = 0;
	var width = 0;
	if (values.length > 8) {
		width = 370;
		if (values.length > 30) {
			heigth = 220;
		} else if (values.length > 12) {
			heigth = 22 * (values.length / 3);
		} else {
			heigth = 88;
		}
	} else if (values.length > 4) {
		width = 250;
		heigth = 88;
	} else {
		width = 120;
		heigth = 88;
	}
	temp = "<div id='boxscroll' class='comboDiv' style='height: " + heigth
			+ "px;'>" + temp + "</div>";
	var required = $(obje).data("req");
	if (!required) {
		temp += "<div class='comboDiv'><div class='comboOp reset' style=\"text-align:center;padding: 0;\" onclick=\"obj_.value = '';";
		if (str_h) {
			temp += "obt_.value = '';";
		}
		temp += fn + "('');CloseDivId();\">重&nbsp;&nbsp;置</div></div>";
	}
	$("#showView").html(temp).css("width", width);
	displayLayer();
}
// 单选：str显示值，obje当前控件，str_h隐藏值
function comboWide(str, obje, str_h) {
	if (str == "") {
		CloseDivId();
		return;
	}
	obj_ = obje;
	var objV = $(obje).val();
	var temp = "";
	var values = formatStr(str, "$");
	var hideValues;
	if (str_h) {
		var h_id = $(obje).next().attr("id");
		obt_ = document.getElementById(h_id);
		hideValues = formatStr(str_h, "$");
	}
	var divclass = "";
	for (var i = 0; i < values.length; i++) {
		if (values[i] == objV) {
			divclass = "comboOp comboOp-selected";
		} else {
			divclass = "comboOp";
		}
		temp += "<div class='" + divclass + "' onclick=\"obj_.value = '"
				+ values[i] + "';";
		if (str_h) {
			temp += "obt_.value = '" + hideValues[i] + "';";
		}
		temp += "CloseDivId();\">" + values[i] + "</div>";
	}
	var heigth = 0;
	var width = 0;
	if (values.length > 8) {
		width = 475;
		if (values.length > 30) {
			heigth = 220;
		} else if (values.length > 12) {
			heigth = 22 * (values.length / 3);
		} else {
			heigth = 88;
		}
	} else if (values.length > 4) {
		width = 320;
		heigth = 88;
	} else {
		width = 160;
		heigth = 88;
	}
	temp = "<div id='boxscroll' class='comboDiv special' style='height: "
			+ heigth + "px;'>" + temp + "</div>";
	var required = $(obje).data("req");
	if (!required) {
		temp += "<div class='comboDiv'><div class='comboOp reset' style=\"text-align:center;padding: 0;\" onclick=\"obj_.value = '';";
		if (str_h) {
			temp += "obt_.value = '';";
		}
		temp += "CloseDivId();\">重&nbsp;&nbsp;置</div></div>";
	}
	$("#showView").html(temp).css("width", width);
	displayLayer();
}

function showDistrictHelp(obje, disid, form, reg_obj) {
	var str = "";
	var spell = $(obje).val();
	if (spell) {
		str = getDistrictStr('', spell);
	}
	obj_ = obje;
	var width = $(obje).outerWidth();
	var temp = "";
	if (str == "") {
		temp += "<div id='boxscroll' class='comboDiv'><div class=\"comboOp reset\" style=\"text-align:center;padding: 0;\" onclick=\"CloseDivId();\">无此区域</div></div>";
	} else {
		var values = formatStr(str, "$");
		for (var i = 0; i < values.length; i++) {
			var districtInfo = formatStr(values[i], "^");
			if (form) {
				temp += "<div class='comboOp' onclick=\"onChooseHelp('"
						+ districtInfo[0] + "', '" + disid + "','" + form
						+ "','" + reg_obj + "');\">" + districtInfo[1]
						+ "</div>";
			} else {
				temp += "<div class='comboOp' onclick=\"onChooseHelp('"
						+ districtInfo[0] + "', '" + disid + "','" + reg_obj
						+ "');\">" + districtInfo[1] + "</div>";
			}
		}
		var heigth = 0;
		width = 0;
		if (values.length > 8) {
			width = 370;
			if (values.length > 30) {
				heigth = 220;
			} else if (values.length > 12) {
				heigth = 22 * (values.length / 3);
			} else {
				heigth = 88;
			}
		} else if (values.length > 4) {
			width = 250;
			heigth = 88;
		} else {
			width = 120;
			heigth = 88;
		}
		temp = "<div id='boxscroll' class='comboDiv' style='height: " + heigth
				+ "px;'>" + temp + "</div>";
	}
	$("#showView").html(temp).css("width", width);
	displayLayer();
}

function onChooseHelp(id, disid, form, reg_obj) {
	var ids1 = 1, ids2 = 1, ids3 = 1;
	ids1 = id;
	if (ids1 != base_id) {
		ids2 = getUpDisId(ids1);
	}
	if (ids2 != base_id) {
		ids3 = getUpDisId(ids2);
	}
	if (form) {
		if (ids3 != base_id) {
			$("#" + disid, $("form[name=" + form + "]")).val(getDisName(ids3))
					.next().val(ids3).next().val(getDisName(ids2)).next().val(
							ids2).next().val(getDisName(ids1)).next().val(ids1);
		} else if (ids2 != base_id) {
			$("#" + disid, $("form[name=" + form + "]")).val(getDisName(ids2))
					.next().val(ids2).next().val(getDisName(ids1)).next().val(
							ids1).next().val("").next().val("");
		} else if (ids1 != base_id) {
			$("#" + disid, $("form[name=" + form + "]")).val(getDisName(ids1))
					.next().val(ids1).next().val("").next().val("").next().val(
							"").next().val("");
		}
		if (reg_obj && ids3 != base_id) {
			var diss = getDistrictReg(id);
			$("#" + reg_obj + "Show", $("form[name=" + form + "]")).html(
					"门牌规则(" + diss.nModel + ")");
			$("#" + reg_obj + "Hide", $("form[name=" + form + "]")).val(
					diss.nRegexp);
		}
	} else {
		if (ids3 != base_id) {
			$("#" + disid).val(getDisName(ids3)).next().val(ids3).next().val(
					getDisName(ids2)).next().val(ids2).next().val(
					getDisName(ids1)).next().val(ids1);
		} else if (ids2 != base_id) {
			$("#" + disid).val(getDisName(ids2)).next().val(ids2).next().val(
					getDisName(ids1)).next().val(ids1).next().val("").next()
					.val("");
		} else if (ids1 != base_id) {
			$("#" + disid).val(getDisName(ids1)).next().val(ids1).next()
					.val("").next().val("").next().val("").next().val("");
		}
		if (reg_obj && ids3 != base_id) {
			var diss = getDistrictReg(id);
			$("#" + reg_obj + "Show").html("门牌规则(" + diss.nModel + ")");
			$("#" + reg_obj + "Hide").val(diss.nRegexp);
		}
	}
	$(obj_).val(getDisName(ids1));
	CloseDivId();
}

function showDistrict(obje, level, reg_obj) {
	var str = "";
	var errorStr = "无下级区域";
	if (level == 1) {
		str = getDistrictStr(base_id, '');
	} else if (level == 2 || level == 3) {
		var disid = $(obje).prev().val();
		if (disid) {
			str = getDistrictStr(disid, '');
		} else {
			errorStr = "请选择上级区域";
		}
	}
	obj_ = obje;
	var width = $(obje).outerWidth();
	var temp = "";
	if (str == "") {
		temp += "<div id='boxscroll' class='comboDiv'><div class=\"comboOp reset\" style=\"text-align:center;padding: 0;\" onclick=\"CloseDivId();\">"
				+ errorStr + "</div></div>";
	} else {
		var objV = $(obje).val();
		var values = formatStr(str, "$");
		// 插入内容开始
		var divclass = "";
		var content = "";
		for (var i = 0; i < values.length; i++) {
			var disInfo = formatStr(values[i], "^");
			if (disInfo[1] == objV) {
				divclass = "comboOp comboOp-selected";
			} else {
				divclass = "comboOp";
			}
			content += "<div class='" + divclass
					+ "' onclick=\"onChooseDistrict('" + disInfo[0] + "','"
					+ disInfo[1] + "'," + level + ",'" + reg_obj + "');\">"
					+ disInfo[1] + "</div>";
		}
		var heigth = 0;
		width = 0;
		if (values.length > 8) {
			width = 370;
			if (values.length > 30) {
				heigth = 220;
			} else if (values.length > 12) {
				heigth = 22 * (values.length / 3);
			} else {
				heigth = 88;
			}
		} else if (values.length > 4) {
			width = 250;
			heigth = 88;
		} else {
			width = 120;
			heigth = 88;
		}
		temp = "<div id='boxscroll' class='comboDiv' style='height: " + heigth
				+ "px;'>" + content + "</div>";
		var required = $(obje).data("req");
		if (!required) {
			temp += "<div class='comboDiv'><div class='comboOp reset' style=\"text-align:center;padding: 0;\" onclick=\"onChooseDistrict('','',"
					+ level
					+ ",'"
					+ reg_obj
					+ "');\">重&nbsp;&nbsp;置</div></div>";
		}
	}
	$("#showView").html(temp).css("width", width);
	displayLayer();
}

function onChooseDistrict(id, name, level, reg_obj) {
	$(obj_).val(name).next().val(id);
	if (level == 2) {
		$(obj_).next().next().val('').next().val('');
	} else if (level == 1) {
		$(obj_).next().next().val('').next().val('').next().val('').next().val(
				'');
	}
	if (reg_obj && level == 3) {
		var diss = getDistrictReg(id);
		if (diss) {
			$("#" + reg_obj + "Show").html("门牌规则(" + diss.nModel + ")");
			$("#" + reg_obj + "Hide").val(diss.nRegexp);
		} else {
			$("#" + reg_obj + "Show").html("此小区未设置门牌号规则");
			$("#" + reg_obj + "Hide").val('');
		}
	}
	if (reg_obj && level != 3) {
		var diss = getDistrictReg(id);
		$("#" + reg_obj + "Show").html("请选择小区");
		$("#" + reg_obj + "Hide").val('');
	}
	CloseDivId();
}

function showDistrictMulti(obje) {
	var str = "";
	var errorStr = "无下级区域";
	var disid = $(obje).prev().val();
	if (disid) {
		str = getDistrictStr(disid, '');
	} else {
		errorStr = "请选择上级区域";
	}
	showVs = new Array();
	hideVs = new Array();
	selVs = new Array();
	obj_ = obje;
	var width = $(obje).outerWidth();
	var temp = "";
	if (str == "") {
		temp += "<div id='boxscroll' class='comboDiv'><div class=\"comboOp reset\" style=\"text-align:center;padding: 0;\" onclick=\"CloseDivId();\">"
				+ errorStr + "</div></div>";
	} else {
		var objV = $(obje).val();
		if (objV.indexOf("$") > -1) {
			objV = "$" + objV;
		} else {
			objV = "$" + objV + "$";
		}
		var values = formatStr(str, "$");
		// 插入内容开始
		var divclass = "";
		var content = "";
		for (var i = 0; i < values.length; i++) {
			var disInfo = formatStr(values[i], "^");
			showVs[i] = disInfo[1];
			hideVs[i] = disInfo[0];
			if (objV.indexOf("$" + showVs[i] + "$") > -1) {
				selVs[i] = 1;
				divclass = "comboOp comboOp-selected";
			} else {
				selVs[i] = 0;
				divclass = "comboOp";
			}
			content += "<div class='" + divclass
					+ "' onclick=\"onChooseDistrictMulti(" + i + ",this);\">"
					+ showVs[i] + "</div>";
		}
		var heigth = 0;
		width = 0;
		if (values.length > 8) {
			width = 370;
			if (values.length > 30) {
				heigth = 220;
			} else if (values.length > 12) {
				heigth = 22 * (values.length / 3);
			} else {
				heigth = 88;
			}
		} else if (values.length > 4) {
			width = 250;
			heigth = 88;
		} else {
			width = 120;
			heigth = 88;
		}
		temp = "<div id='boxscroll' class='comboDiv' style='height: " + heigth
				+ "px;'>" + content + "</div>";
		var required = $(obje).data("req");
		if (!required) {
			temp += "<div class='comboDiv'><div class='comboOp reset' style=\"text-align:center;padding: 0;\" onclick=\"$(obj_).val('').next().val('');CloseDivId();\">重&nbsp;&nbsp;置</div></div>";
		}
	}
	$("#showView").html(temp).css("width", width);
	displayLayer();
}

function onChooseDistrictMulti(count, _this) {
	var _selVs = selVs;
	if (selVs[count] == 1) {
		selVs[count] = 0;
		_this.className = 'comboOp';
	} else {
		selVs[count] = 1;
		_this.className = 'comboOp comboOp-selected';
	}
	var objvalue = "";
	var obj_hvalue = "";
	for (var i = 0; i < selVs.length; i++) {
		if (selVs[i] == 1) {
			objvalue += showVs[i] + '$';
			obj_hvalue += hideVs[i] + '$';
		}
	}
	$(obj_).val(objvalue).next().val(obj_hvalue);
}

function getQZStr(id, name) {
	var str = '';
	for (var i = 0; i < qzUserDate.length; i++) {
		if (id != '' && qzUserDate[i].pid == id) {
			str += qzUserDate[i].id + '^' + qzUserDate[i].name + '^' + '$';
		}
		if (name != '' && qzUserDate[i].name==name) {
			str += qzUserDate[i].id + '^' + qzUserDate[i].name + '^'
					+ qzUserDate[i].code + '^' + qzUserDate[i].pid + '^'
					+ qzUserDate[i].pname + '^' + '$';
		}
	}
	return str;
}
function showQZHelp(obje) {
	var str = "";
	var spell = $(obje).val();
	if (spell) {
		str = getQZStr('', spell);
	}
	obj_ = obje;
	var width = $(obje).outerWidth();
	var temp = "";
	if (str == "") {
		temp += "<div id='boxscroll' class='comboDiv'><div class=\"comboOp reset\" style=\"text-align:center;padding: 0;\" onclick=\"CloseDivId();\">无此权证人员</div></div>";
	} else {
		var values = formatStr(str, "$");
		for (var i = 0; i < values.length; i++) {
			var qzInfo = formatStr(values[i], "^");
			temp += "<div class='comboOp' style='width: 130px;' onclick=\"onChooseQZHelp('"
					+ qzInfo[0]
					+ "','"
					+ qzInfo[1]
					+ "');\">"
					+ qzInfo[1]
					+ "(" + qzInfo[2] + ")(" + qzInfo[4] + ")</div>";
		}
		var heigth = 0;
		width = 0;
		if (values.length > 8) {
			width = 540;
			if (values.length > 30) {
				heigth = 220;
			} else if (values.length > 12) {
				heigth = 22 * Math.ceil(values.length / 3);
			} else {
				heigth = 88;
			}
		} else if (values.length > 4) {
			width = 370;
			heigth = 88;
		} else {
			width = 180;
			heigth = 88;
		}
		temp = "<div id='boxscroll' class='comboDiv' style='height: " + heigth
				+ "px;'>" + temp + "</div>";
	}
	$("#showView").html(temp).css("width", width);
	displayLayer();
}

function onChooseQZHelp(id, name) {
	$(obj_).val(name).next().val(id);
	CloseDivId();
}

function formatStr(str, subStr) {
	var _str = str;
	if (_str.charAt(0) == subStr) {
		_str = _str.substring(1);
	}
	if (_str.charAt(_str.length - 1) == subStr) {
		_str = _str.substring(0, _str.length - 1);
	}
	var _strA = _str.split(subStr);
	return _strA;
}
// 关闭弹出层
function CloseDivId() {
	if_checkP = 0;
	$("#showView").hide();
}

// 显示弹出层
function displayLayer() {
	var offset = $(obj_).offset();
	var l = offset.left;
	var t = offset.top;
	var h = $(obj_).outerHeight();
	var w = $(obj_).outerWidth();
	var st = $(document).scrollTop();
	// alert(l);
	// alert(t + h);
	var oh = $(document).outerHeight();
	// alert(oh);
	var ow = $(document).outerWidth();
	// alert(ow);
	var sh = $("#showView").outerHeight();
	var sw = $("#showView").outerWidth();
	// alert(sh);
	// alert(sw);
	if (oh < (t + h + sh + 10)) {
		$("#showView").css("top", (t - sh));
	} else {
		$("#showView").css("top", (t + h));
	}
	if (ow < (l + sw + 10)) {
		$("#showView").css("left", l - sw + w);
	} else {
		$("#showView").css("left", l);
	}
	$("#showView").show();

	if_checkP = 1;
	$("#boxscroll").niceScroll({
		cursorborder : "",
		cursorcolor : "#ffa5ad",
		boxzoom : false
	});
}

function checkPosition() {
	if (if_checkF == 1) {
		if_checkF = 0;
	} else if (if_checkP == 1) {
		if_checkP = 2;
	} else if (if_checkP == 2
			&& !document.getElementById("showView").contains(
					document.elementFromPoint(event.clientX, event.clientY))) {
		CloseDivId();
	}
}