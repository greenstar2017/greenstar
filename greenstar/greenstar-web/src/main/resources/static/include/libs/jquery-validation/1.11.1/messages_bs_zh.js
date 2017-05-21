/*
 * Translated default messages for the jQuery validation plugin.
 * Locale: ZH (Chinese, 中文 (Zhōngwén), 汉语, 漢語)
 */
jQuery.extend(jQuery.validator.messages, {
        required: "必选字段",
		remote: "请修正该字段",
		email: "请输入正确格式的电子邮件",
		url: "请输入合法的网址",
		date: "请输入合法的日期",
		dateISO: "请输入合法的日期 (ISO).",
		number: "请输入合法的数字",
		digits: "只能输入整数",
		creditcard: "请输入合法的信用卡号",
		equalTo: "请再次输入相同的值",
		accept: "请输入拥有合法后缀名的字符串",
		maxlength: jQuery.validator.format("请输入一个长度最多是 {0} 的字符串"),
		minlength: jQuery.validator.format("请输入一个长度最少是 {0} 的字符串"),
		rangelength: jQuery.validator.format("请输入一个长度介于 {0} 和 {1} 之间的字符串"),
		range: jQuery.validator.format("请输入一个介于 {0} 和 {1} 之间的值"),
		max: jQuery.validator.format("请输入一个最大为 {0} 的值"),
		min: jQuery.validator.format("请输入一个最小为 {0} 的值")
});

jQuery.extend(jQuery.validator.defaults, {
    errorElement: "span"
});

//长度
jQuery.validator.addMethod("strength", function(value, element) { 
     if(value.length<6 || value.length>16){
     	 return this.optional(element) || false;
     }else{
     	 return true;
     }
}, "长度为6-16个字符");
//空格
jQuery.validator.addMethod("nospace", function(value, element) {       
     return this.optional(element) || /^\S+$/g.test(value);       
}, "密码不能包含空格"); 
//纯数字
jQuery.validator.addMethod("allnum", function(value, element) {
	if(value.length==6){
		var isNum=value.replace(/\d/g,"").length==0	? false:true;
    	 return this.optional(element) ||isNum;
     }else{
     	return true;
     }
}, "密码不能是6位以下纯数字");

//密码弱
jQuery.validator.addMethod("isweak", function(value, element) {
	if(value.length>=6){
		var all=value.length;
		var num=all-value.replace(/[0-9]/g,"").length;//数字
		var xx=all-value.replace(/[a-z]/g,"").length;//字母
		var dx=all-value.replace(/[A-Z]/g,"").length;
		var bd=(all-num-xx-dx);//标点
		if(num>1){num=1;}
		if(xx>1){xx=1;}
		if(dx>1){dx=1;}
		if(bd>1){bd=1;}
		var score=(num*30)+(xx*30)+(dx*30)+(bd*30);
		if(score<0){E=0}
		if(score>100){E=100}
		var isweak=true;
		if(score<=30){
			isweak=false;
		}
    	 return this.optional(element) ||isweak;
     }else{
     	return true;
     }
}, "密码太弱");
