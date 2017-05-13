var column = $("#orderColumn").val();
var mode = $("#orderMode").val();
var img = "<img src='../images/sort.png' class='sort_btn'>";
if (mode == 'desc') {
	img = "<img src='../images/sort_down.png' class='sort_btn'>";
}
$("#" + column + "_col").prepend(img);
$(".order").click(function() {
	var id = $(this).attr("id").replace('_col', '');
	if (id == column) {
		if (mode == "asc") {
			$("#orderMode").val("desc");
		} else {
			$("#orderMode").val("asc");
		}
	} else {
		$("#orderMode").val("desc");
	}
	$("#orderColumn").val(id);
	$("#currPageNum").val("1");
	$("#report").submit();
});
$(".first").click(function() {
	$("#currPageNum").val(1);
	$("#report").submit();
});
$(".prev").click(function() {
	var num = $("#currPageNum").val();
	if (parseInt(num) <= 1) {
		num = 1;
	} else {
		num = parseInt(num) - 1;
	}
	$("#currPageNum").val(num);
	$("#report").submit();
});
$(".next").click(function() {
	var num = $("#currPageNum").val();
	var total = $("#total").val();
	if (parseInt(num) >= parseInt(total)) {
		num = total;
		if(num==0){
			num=1;
		}
	} else {
		num = parseInt(num) + 1;
	}
	$("#currPageNum").val(num);
	$("#report").submit();
});
$(".last").click(function() {
	var total = $("#total").val();
	if(total==0){
		total=1;
	}
	$("#currPageNum").val(total);
	$("#report").submit();
});
function go_onkeydown(formid) {
	var total = $("#total").val();
	if (event.keyCode == 13) {
		var num = $("#go").val();
		if (CheckNumber(trima(num)) && 1 * trima(num) > 0
				&& 1 * trima(num) <= 13296) {
			if (parseInt(trima(num)) > parseInt(total)) {
				num=total;
			}
			$("#currPageNum").val(trima(num));
			$("#" + formid).submit();
		} else {
			alert('请输入正确的数字页号!');
		}
	}
}
function ltrima(val) {
	var retstr;
	var tmpstr;
	retstr = new String();
	tmpstr = new String(val);
	var i;
	for (i = 0; i < tmpstr.length; i++) {
		if (tmpstr.substring(i, i + 1) != ' ')
			break;
	}
	;
	retstr = tmpstr.substring(i, tmpstr.length);
	return retstr;
}
function rtrima(val) {
	var retstr;
	var tmpstr;
	retstr = new String();
	tmpstr = new String(val);
	var i;
	for (i = tmpstr.length - 1; i > 0; i--) {
		if (tmpstr.substring(i, i + 1) != ' ')
			break;
	}
	;
	retstr = tmpstr.substring(0, i + 1);
	return retstr;
}
function trima(val) {
	return rtrima(ltrima(val));
}
function CheckNumber(str) {
	var checkOK = '0123456789-/. ';
	var checkStr = trima(str);
	var allValid = true;
	var decPoints = 0;
	var allNum = '';
	for (i = 0; i < checkStr.length; i++) {
		ch = checkStr.charAt(i);
		for (j = 0; j < checkOK.length; j++)
			if (ch == checkOK.charAt(j))
				break;
		if (j == checkOK.length) {
			allValid = false;
			break;
		}
		if (ch == '.') {
			allNum += '.';
			decPoints++;
		} else
			allNum += ch;
	}
	if (!allValid) {
		return (false);
	}
	if (decPoints > 1) {
		return (false);
	}
	return (true);
}