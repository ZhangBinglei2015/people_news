/**
 * @ 自有后台公共js
 * @ date	2014-09-03 13:26:29
 */
// var baseUrl = 'http://yinglian.fogoo.cc';
var baseUrl = 'http://yinglian.52yingzheng.com';
//var baseUrl = 'http://www.inglian.com';
var taskInfoPage;

$(function(){
    window.isCroll = null;
	init();
});
/*webviewJavascriptBridge*/
var responseCallbacks = {};
var registerCallbacks = {};
WebViewJavascriptBridge = {
    callHandler : function(handlerName,data,callback){
        var callbackId = handlerName + new Date().getTime();
        responseCallbacks = {};
        responseCallbacks[callbackId] = callback;
        var data = {data:data, callbackId:callbackId};
        if(typeof data === 'object') data = JSON.stringify(data);
        jsToAndroid[handlerName](data);     // 将数据发送给安卓
    },
    registerHandler : function(handlerName,callback){
        registerCallbacks[handlerName] = callback;
        var data = {registerCallbacks:registerCallbacks[handlerName]};
        // jsToAndroid[handlerName](data);
    },
    init : function(){}
};
// 接受安卓端的回调和数据
function AndroidToJs(boole,callbackId,response){
    if(boole){
        responseCallbacks[callbackId](response);
        delete responseCallbacks[callbackId];
    }else{
        registerCallbacks[callbackId](response);
        delete registerCallbacks[callbackId];
    }
};
// 连接安卓端
function connectNative(callback) {
    if (window.WebViewJavascriptBridge) {
        callback(WebViewJavascriptBridge)
    } else {
        document.addEventListener('WebViewJavascriptBridgeReady', function() {callback(WebViewJavascriptBridge)}, false)
    }
}
//var bridge = null;
/*bridge = {
	callHandler:function(a,b,c){
		c();
	},
	init : function(){}
};*/

// function connectNative(callback) {
//     if (window.WebViewJavascriptBridge) {
//         callback(WebViewJavascriptBridge)
//     } else {
//         document.addEventListener('WebViewJavascriptBridgeReady', function() {callback(WebViewJavascriptBridge)}, false)
//         //callback(bridge)
//     }
// }
function userInfoInit(){
    var userInfo = {};
    bridge.callHandler('readData',{storeName:'userInfo',handleName:'userInfo'}, function(response){});
    bridge.registerHandler('userInfo',function(response,data) {
//    var response = '{token:1223, uid :6, userPhone : 13800138000}';
        response = eval('(' + response + ')');
        userInfo.token = response.token;
        userInfo.uid = response.uid;
        userInfo.userPhone = response.userPhone;
        return userInfo;
    });
};

/**
 * Jquery插件
 * 设置、移除按钮disabled
 * $(button).setDisabled(); $(button).removeDisabled();
 **/
$.fn.extend({
	setDisabled : function(){
		$(this).attr('disabled','disabled');
	},
	removeDisabled : function(){
		$(this).removeAttr('disabled');
	}
});
// 获取url中的参数
function getUrlArg(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if (r != null) return unescape(r[2]); return null;
}
// 时间戳转换
function getDate(tm){
    var a=new Date(parseInt(tm) * 1000)
    var b, c,d;
    b=a.getMonth()+1;
    d= a.getDate();
    if(b<10){
        b = '0'+b;
    }
    if(d<10){
        d = '0' +d;
    }
    c=a.getFullYear()+"-"+b+"-"+d;
    return c;
}
// 获取时间
function getTime(tm){
    var a=new Date(parseInt(tm) * 1000);
    var b, c,d;
    b=a.getHours();
    d=a.getMinutes();
    if(b<10){
        b = '0' +b;
    }
    if(d<10){
        d = '0'+d;
    }
    c=b+":"+d;
    return c;
}
// 获取时间戳
function transDate(){
    var oldTime = (new Date("2011/11/11 20:10:10")).getTime(); //得到毫秒数
    return oldTime;
}

function init(){
	if($('#wrapper').size()<1) return;
    isCroll = new IScroll('#wrapper', { mouseWheel: true, click: true,preventDefault:false });
    document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
}

/*封装公用提示框（单按钮）
   @param text提示文本内容
   @param callback 点击确定时的执行方法
*/ 
function showDialog( text,callback ){
	$.dialog({
		content: text ,
		type:1,
		ok:'确定',
		init: function() {
		}, 
		messageCallback: function() {
			if(callback)
				callback();
			return true;
		}
	});
}

function showDialogTwo( text,callback){
	$.dialog({
		content: text ,
		type:2,                       //type=2---两个按钮
		init: function() {            //给弹窗初始化/弹窗加载后的回调函数
		}, 
		messageCallback: function() { //点击确定按钮后的回调函数
			if(callback)
				callback();
			return true;
		},
		cancelCallback: function() {  //点击取消按钮后的回调函数
     
		} 
	});
}

function showDialogTime( text,callback ){
	$.dialog({
		content: text ,
		type:0,
		init: function() {
		}, 
		messageCallback: function() {
			if(callback)
				callback();
			return true;
		}
	});
}

/*
 *验证
 *@param 验证当前class下面的所有元素 flag标示传过来的参数的处理方式 1是当做字符串处理,可以不传
*/
 function baseVerify(idstrs,callback,flag){
  var idstr=[];
  if(flag){
	idstr=idstrs.split(",");
  }else{
	idstr=$(idstrs).find("[require]");
  }
  for(var i=0;i<idstr.length;i++){
	if($(idstr[i]).attr("require")=="true" && ($.trim($(idstr[i]).val())=="" || $.trim($(idstr[i]).val())== $(idstr[i]).attr('placeholder') ) ){
	   // alert($(idstr[i]).attr("msg"));
	   var msg = $(idstr[i]).attr("msg");
	   if(callback && typeof callback === 'function') callback(msg);
	   return false;
	}
    if(!new RegExp($(idstr[i]).attr("datatype")).test($(idstr[i]).val())){
       // alert($(idstr[i]).attr("msg1"));
	   var msg = $(idstr[i]).attr("msg");
	   if(callback && typeof callback === 'function') callback(msg);
       return false;
    }
 }
   return true;
}

// 验证手机号
function isPhoneNumber(num){
	return(/^1[3|4|5|7|8][0-9]\d{8}$/.test(num));
}
// 验证邮箱
function isEmail(str){
    var reg = /^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/;
    return reg.test(str);
}
// iscroll 刷新&加载
if($('#wrapperAppend').size()>0){
    var myScroll;
    var pullUpEl = $('#pullUp'),pullDownEl = $('#pullDown');
    var Downcount = 0 ,Upcount = 0;
    var loadingStep = 0;//加载状态0默认，1显示加载状态，2执行加载数据，只有当为0时才能再次加载，这是防止过快拉动刷新
    var page = 1;
    // 初始化
    myScroll = new IScroll('#wrapperAppend', {
        probeType: 2,
        shrinkScrollbars:'scale',// 当滚动边界之外的滚动条是由少量的收缩。'clip' or 'scale'.
         click: true ,// 允许点击事件
        
        momentum:true// 允许有惯性滑动
    });
    // var holderHeight = ('height',$(window).height()-$('#scroller').height()+2);
    // $('<div class="J_holde" style="height:'+holderHeight+'px"></div>').appendTo('#scroller');
    function iscroll(option){
        loaded();
        function loaded() {
            pullDownEl['class'] = pullDownEl.attr('class');
            pullDownEl.attr('class','').css({'height':0,'opacity':0});
            pullUpEl['class'] = pullUpEl.attr('class');
            pullUpEl.attr('class','').hide();
            //滚动时
            myScroll.on('scroll', function(){
                if(loadingStep == 0 && !pullDownEl.attr('class').match('flip|loading') && !pullUpEl.attr('class').match('flip|loading')  && (option.refresh || option.addData)){
                    if(this.y>0 && option.refresh){
                        pullDownEl.css({'height':22+this.y>>0,'opacity':this.y/10});
                        if (this.y > 20) {
                            //下拉刷新效果
                            pullDownEl.attr('class',pullDownEl['class'])
                            pullDownEl.css({'opacity':1});
                            myScroll.refresh();
                            pullDownEl.addClass('flip');
                            loadingStep = 1;
                        }
                    }else if (this.y < (this.maxScrollY - 30) && option.addData) {
                        //上拉刷新效果
                        pullUpEl.attr('class',pullUpEl['class'])
                        pullUpEl.show();
                        myScroll.refresh();
                        pullUpEl.addClass('flip');
                        loadingStep = 1;
                    }
                }
            });
            //滚动完毕
            myScroll.on('scrollEnd',function(){
                if(option.refresh) pullDownEl.animate({'height':0,'opacity':0},100,'linear');
                if(loadingStep == 1){
                    if (pullUpEl.attr('class').match('flip|loading') && option.addData) {
                        pullUpEl.removeClass('flip').addClass('loading');
                        loadingStep = 2;
                        option.addData();
                    }else if(pullDownEl.attr('class').match('flip|loading') && option.refresh){
                        pullDownEl.removeClass('flip').addClass('loading');
                        loadingStep = 2;
                        option.refresh();
                    }
                }
            });
        }
        document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
    }

    // 下拉刷新初始化
    function initPullDown(){
        pullDownEl.removeClass('loading');
        pullDownEl['class'] = pullDownEl.attr('class');
        pullDownEl.attr('class','').css({'height':0,'opacity':0});
        myScroll.refresh();
        loadingStep = 0;
    }
    // 上拉加载初始化
    function initPullUp(){
        pullUpEl.removeClass('loading');
        pullUpEl['class'] = pullUpEl.attr('class');
        pullUpEl.attr('class','').hide();
        myScroll.refresh();
        loadingStep = 0;
        if(taskInfoPage){
            page = requsetData.page;
        }
        ++page;
        requsetData.page = page;
    }

}
// 服务器时间倒计时
// 用例：需获取服务器当前时间，任务开始时间
/*
var nowtime = '2015-1-14 12:00';
var endtime = '2015-1-14 13:00';
$('#timer').attr('nowtime',nowtime);
$('#timer').attr('endtime',endtime);
$('#timer').addClass('setTime');
countDownTime();
*/

function countDownTime(fn) {
    var timeSwitch = true;
    $(".setTime").each(function () {
        var endtime = parseInt($(this).attr("endtime"));          //取结束日期(秒值)
        var nowtime = parseInt($(this).attr("nowtime"));          //今天的日期(秒值)服务器时间
        var youtime = endtime - nowtime;                                    //还有多久(秒值)
        var seconds = youtime;
        var minutes = Math.floor(seconds / 60);
        var hours = Math.floor(minutes / 60);
        var days = Math.floor(hours / 24);
        var CDay = days;
        var CHour = hours % 24;
        var CMinute = minutes % 60;
        var CSecond = Math.floor(seconds % 60);                             //"%"是取余运算，可以理解为60进一后取余数，然后只要余数。

        if (endtime <= nowtime) {
            if(typeof fn == 'function' && fn){
                fn();
            }else{
                $(this).html('计时结束');
            }
            timeSwitch = false;
        } else {
            $(this).html(days + "天" + CHour + "小时" + CMinute + "分" + CSecond + "秒后可以再做一次"); //输出有天数的数据
            //当前时间变化
            $(this).attr("nowtime", nowtime+1);
        }
    });
    if(timeSwitch){
        setTimeout("countDownTime("+fn+")", 1000);
    }
}
//js时间戳格式转2015-1-14 12:00
function transformTime(needtime) {
    var oks = new Date(needtime);
    var year = oks.getFullYear();
    var month = oks.getMonth() + 1;
    var date = oks.getDate();
    var hour = oks.getHours();
    var minute = oks.getMinutes();
    var second = oks.getSeconds();
    return year + '-' + month + '-' + date + ' ' + hour + ':' + minute + ':' + second;
    //return month + '/' + date + '/' + year + ' ' + hour + ':' + minute + ':' + second;
}

function addLoading(){
    if(('#floatingCirclesG').size >0){
        $('#floatingCirclesG').parent().remove();
    }else{
        var html = '<div class="loading"><div id="floatingCirclesG"><div class="f_circleG" id="frotateG_01"></div><div class="f_circleG" id="frotateG_02"></div><div class="f_circleG" id="frotateG_03"></div><div class="f_circleG" id="frotateG_04"></div><div class="f_circleG" id="frotateG_05"></div><div class="f_circleG" id="frotateG_06"></div> <div class="f_circleG" id="frotateG_07"></div><div class="f_circleG" id="frotateG_08"></div></div><span></span></div>';
        $('body').append(html);
    }

}

function removeLoading(){
    $('#floatingCirclesG').parent().remove();
}

function increaseNum(obj,increaseStr,lth){
    if(lth == undefined){
        lth = 5;
    }
    lth < 3 ? lth =3 : lth;
    var num = parseInt(increaseStr),a=0,b = null,t=null,s = increaseStr/15,str0='';
    if(increaseStr < 10){s = 1;}
    t=setInterval(function(){
        a+=Math.ceil(s);
        if(a>=num){
            clearInterval(t);
            a = num;
        }
        obj.html(supplementZero(a.toString(),5));
    },100);
}

function increaseNum2(obj,increaseStr,lth){
    if(lth == undefined){
        lth = 5;
    }
    lth < 3 ? lth =3 : lth;
    var num = parseInt(increaseStr);
    obj.html(supplementZero(num.toString(),lth));
}
/**
 * 字符串补零
 * @name supplementZero
 * @param  {string}    str       数字字符串
 * @param  {number}    num       字符串length的最大值
 * @return {string}    补零后的新字符串
 */
function supplementZero(str,num){
   if(str.length<=num){
        var str0='';
        var a=num-str.length;
        for(var i=0;i<a;i++){
            str0+='0';
        }
        var newStr=str0+str;
        return newStr;
    } 
}
/**
 * 翻转效果
 * @name   drawIncome
 * @param  {object}    obj          翻牌对象
 * @param  {srting}    targetNum    目标值
 * @function
 */
function drawIncome(obj,targetNum){
    var drawEle=obj.find('p');
    var drawEle_h=obj.eq(0).height();
    var len=drawEle.length;
    var len2=drawEle.length;
    var t2=null;
    var t3=null;
    var numTarget=targetNum;
    numTarget=supplementZero(numTarget,drawEle.length);
    //alert(numTarget);
    clearInterval(t2);
    t2=setInterval(function(){
        len--;
        if(len<=0){
            len=0;
            clearInterval(t2);
            setTimeout(function(){
                clearInterval(t3);
                t3=setInterval(function(){
                    len2--;
                    var a=numTarget.charAt(len2);
                    //alert(a);
                    if(len2<=0){
                        len2=0;
                        clearInterval(t3);
                    }
                    drawEle.eq(len2).attr('style','');
                    drawEle.eq(len2).attr('style','-webkit-transform: translateY(-'+a*drawEle_h+'px);')
                },100);
            },300);
        }
        drawEle.eq(len).attr('style','-webkit-animation:scrollText1 .1s infinite  cubic-bezier(1,0,0.5,0);');
    },100);
}


$('.c_btnOne,.c_btnTwo').on('touchstart',function(){
    $(this).addClass('bg');
}).on('touchend',function(){
    $(this).removeClass('bg');
});