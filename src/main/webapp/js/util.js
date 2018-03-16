/*************弹出框遮罩层**************/
Messages = {
/*
* 一级z-index：1000 、box=1001
* 二级z-index：1005 、box=1006
* 三级（提示框）z-index：1009 、box=1010
* 四级（数据量偏大加载时遮罩层）9999
* 
* 
* */	
	//打开一级弹框
	showBox : function(id) {
	   $("#" + id).slideDown("fast");
	   this.openShadeDiv();
	},
	
	closeBox : function(id) {
	   $("#" + id).slideUp("fast");
	   $("#shadeDiv").remove();
	},	
	//打开二级弹框
	showBox2 : function(id) {
	   $("#" + id).slideDown("fast");
	   this.openShadeDiv2();
	},
	
	closeBox2 : function(id) {
	   $("#" + id).slideUp("fast");
	   $("#shadeDiv2").remove();
	},		
	
	//用于封装的提示框遮罩层
	opendShadeDiv:function(){
		this.openShadeDiv3();
	},
	closeShadeDiv:function(id){
		$("#" + id).slideUp("fast");
		$("#shadeDiv3").remove();
	},
	
	 //数据量加载时遮罩层
    opendWaiting:function(){
        this.openShadeDiv4();
    },
    closedWaiting:function(){
    	$("#shadeDiv4").remove();
    },
    
	
	
    // 遮罩（一级弹框遮罩层）
    openShadeDiv : function() {
        var wnd = $(window);
        var doc = $(document);
        var wHeight = 0;
        var wWidth = 0;
        if (wnd.height() > doc.height()) {// 当高度小于一
            wHeight = wnd.height();
            wWidth = doc.width();
        } else {
            wWidth = doc.width();
            wHeight = doc.height();
        }
        var strDiv = "<div id=shadeDiv >"
            + "<table align='center'  style='margin-top: "
            + (wHeight / 2 - 11)
            + "px ' >"
            + "<tr>"
            + "<td>"
                //+ "<img style='height:22px;width:22px' src='../images/loading4.gif'/>"
            + "</td>" + "<td style='font-size: 15px;'>" //+ "数据加载中..."
            + "</td>" + "</tr>" + "</table>" + "</div>";
        $("body").append(strDiv);// 创建遮罩层
        $("body").find("#shadeDiv").width(wWidth).height(wHeight).css({
            position : "absolute",
            top : "0px",
            left : "0px",
            background : "#ccc",
            filter : "Alpha(opacity=50);",
            opacity : "0.3",
            zIndex : "1000"
        });
    },
    //遮罩2（二级弹框遮罩层）
    openShadeDiv2: function () {
        var wnd = $(window);
        var doc = $(document);
        var wHeight = 0;
        var wWidth = 0;
        if (wnd.height() > doc.height()) {//当高度小于一
            wHeight = wnd.height();
            wWidth = doc.width();
        } else {
            wWidth = doc.width();
            wHeight = doc.height();
        }
        var strDiv = $("<div id=shadeDiv2 >" +
            "<table align='center'  style='margin-top: " + (wHeight / 2 - 11) + "px ' >" +
            "<tr>" +
            "<td>" +
            "</td>" +
            "<td style='font-size: 15px;'>" +
            //"数据加载中..." +
            "</td>" +
            "</tr>" +
            "</table>" +
            "</div>");
        $("body").append(strDiv);//创建遮罩层
        $(strDiv)
            .width(wWidth)
            .height(wHeight)
            .css({
                position: "absolute",
                top: "0px", left: "0px",
                background: "#ccc",
                filter: "Alpha(opacity=50);",
                opacity: "0.3", zIndex: "1005"
            });
    },
    //遮罩3（封装提示框遮罩层）
    openShadeDiv3: function () {
        var wnd = $(window);
        var doc = $(document);
        var wHeight = 0;
        var wWidth = 0;
        if (wnd.height() > doc.height()) {//当高度小于一
            wHeight = wnd.height();
            wWidth = doc.width();
        } else {
            wWidth = doc.width();
            wHeight = doc.height();
        }
        var strDiv = $("<div id=shadeDiv3 >" +
            "<table align='center'  style='margin-top: " + (wHeight / 2 - 11) + "px ' >" +
            "<tr>" +
            "<td>" +
            "</td>" +
            "<td style='font-size: 15px;'>" +
            //"数据加载中..." +
            "</td>" +
            "</tr>" +
            "</table>" +
            "</div>");
        $("body").append(strDiv);//创建遮罩层
        $(strDiv)
            .width(wWidth)
            .height(wHeight)
            .css({
                position: "absolute",
                top: "0px", left: "0px",
                background: "#ccc",
                filter: "Alpha(opacity=50);",
                opacity: "0.3", zIndex: "1009"
            });
    },
    //遮罩（数据加载时间等待遮罩层）
    openShadeDiv4 : function() {
        var wnd = $(window);
        var doc = $(document);
        var wHeight = 0;
        var wWidth = 0;
        if (wnd.height() > doc.height()) {// 当高度小于一
            wHeight = wnd.height();
            wWidth = doc.width();
        } else {
            wWidth = doc.width();
            wHeight = doc.height();
        }
        var strDiv = "<div id=shadeDiv4 >"
            + "<table align='center'  style='width: 100%;text-align: center;margin-top: "
            + (wHeight / 2)
            + "px ' >"
            + "<tr>"
            + "<td>"
            + "<img style='' src='"+getRootPath_web()+"/images/dd2.gif'/>"
            + "</td>" + "<td style='font-size: 15px;'>" //+ "数据加载中..."
            + "</td>" + "</tr>" + "</table>" + "</div>";
        $("body").append(strDiv);// 创建遮罩层
        $("body").find("#shadeDiv4").width(wWidth).height(wHeight).css({
            position : "absolute",
            top : "0px",
            left : "0px",
            background : "#ccc",
            filter : "Alpha(opacity=50);",
            opacity : "0.3",
            zIndex : "9999"
        });
    },
    

	fadeOutBox: function (id) {
		   $("#shadeDiv").remove();
		   $("#" + id).fadeOut();
	},
	fadeOutBox2: function (id) {
		   $("#shadeDiv2").remove();
		   $("#" + id).fadeOut();
	},

/*-------------------------------封装提示框----------------------------------------*/
	
    /*
     * 封装提示框【确定按钮】
     * msg页面需要提示的信息，cancel_callback 点确定后执行的方法
     * */
    alertMsg : function(msg){
        var tit = "提示信息";
        var popUpDiv = "<div id='messagesLayer3' class='xiugai_box prompt_box2'>"+
            "<div class='police_top'>"+
            "<p id='title2'>"+tit+"</p>"+
            "<span onclick='Messages.closeShadeDiv(\"messagesLayer3\");'></span>"+
            "</div>"+
            "<div class='prompt_main'>"+
            "<span class='prompt_icon2'></span>"+
            "<p style='margin-left: 0px;'>"+msg+"</p>"+
            "</div>"+
            "<div class='widget_foot'>"+
            "<div class='control_prompt2'>"+
            "<a onclick='Messages.closeShadeDiv(\"messagesLayer3\");'  class='control_queding_prompt' > 确定</a>"+
            "</div>"+
            "</div>"+
            "</div>";
        $("#messagesLayer3").remove();
        $("body").append(popUpDiv);
        Messages.opendShadeDiv();
        $("#messagesLayer3").slideDown(200);

    },
    /*
     * 封装提示框【确定按钮】
     * msg页面需要提示的信息，cancel_callback 点确定后执行的方法
     * */
    okMsg : function(msg,cancel_callback){
        var tit = "提示信息";
        var msgs = "";
        if("" == msg){
            msgs = "操作成功！";
        }else{
            msgs = msg;
        }
        msg = "一句话描述提示内容！";
        var popUpDiv = $("<div id='messagesLayer' class='xiugai_box prompt_box2'>"+
            "<div class='police_top'>"+
            "<p id='title2'>"+tit+"</p>"+
                // "<span></span>"+
            "</div>"+
            "<div class='prompt_main'>"+
            "<span class='prompt_icon2'></span>"+
            "<p>"+msgs+"</p>"+
            "</div>"+
            "<div class='widget_foot'>"+
            "<div class='control_prompt22'>"+
            "<a id='ok_btn'  class='control_queding_prompt' > 确定</a>"+
            "</div>"+
            "</div>"+
            "</div>");
        $("#messagesLayer").remove();
        $("body").append(popUpDiv);
        Messages.opendShadeDiv();
        $(popUpDiv).slideDown(200);
        /*----------------------------------------------------------------*/
        if (typeof(cancel_callback) == 'function'){
            $('#ok_btn').click(cancel_callback);
        }
    },
    
   
    /*     普通提示
     * 简单显示消息并带确任,取消按钮
     * */
    confirmMsg : function(cancel_callback,msg){
        var tit = "普通提示";
        var msgs = "";
        if("" == msg){
            msgs = "一句话描述提示内容！";
        }else{
            msgs = msg;
        }

        var popUpDiv = "<div id='messagesLayer2' class='xiugai_box prompt_box1'>"+
            "<div class='police_top'>"+
            "<p id='title1'>"+tit+"</p>"+
                // "<span></span>"+
            "</div>"+
            "<div class='prompt_main'>"+
            "<span class='prompt_icon1'></span>"+
            "<p>"+msgs+"</p>"+
            "</div>"+
            "<div class='widget_foot'>"+
            "<div class='control_prompt1'>"+
            "<a id='ok_btn2' class='control_queding_prompt' >确定</a>"+
            "<a onclick='Messages.closeShadeDiv(\"messagesLayer2\");' class='control_quxiao'> 取消</a>"+
            "</div>"+
            "</div>"+
            "</div>";

        $("#messagesLayer2").remove();
        this.opendShadeDiv();
        $("body").append(popUpDiv);
        $("#messagesLayer2").slideDown(200);
        /*----------------------------------------------------------------*/
        $('#ok_btn2').click(function(){
            Messages.closeShadeDiv("messagesLayer2");
            if (typeof(cancel_callback) == 'function'){
                cancel_callback();
            }
        });
    },

    //查看个人信息
    searchBox1:function(id){
        //$("#title_name2").html("个人信息");
        var url = getRootPath_web()+"/user/queryById?idAll="+id;
        $.post(url,function(data){
            $.each(JSON.parse(data),function(k,v){
            	$("#name1").html(v.name);//v.operator//用户名称
                $("#username1").html(v.username);//v.operator//用户账号
                $("#email1").html(v.email);//v.operator//邮箱
                $("#phone1").html(v.phone);//v.operator//电话
                $("#userdatas").html(v.userdatas3);
                $("#stateChild").html(v.status);
            });
        });
        Messages.showBox("manData");
    }

};

/*************获取url字符串***************/

function getUrlUpdate(id) {
	var url = "";
	var url2 = "" ;
		url = getRootPath_web()+"/img/uploadQKL";
	var fileObj = document.getElementById(id).files[0]; // 获取文件对象
		// FormData 对象
		   var form = new FormData();
		   form.append("author", "hooyes");      // 可以增加表单数据
		   form.append("file", fileObj);       // 文件对象
		   $.ajax({
	        url : url,
	        type : 'POST',
	        async : false,
	        cache : false,
	        data : form,
	        processData : false,
	        contentType : false
	    }).done(function(json) {
	 	   var jsons=window.JSON.parse(json);
		        url2=jsons.url;
		        
	    }, 'json').fail(function() {
	        alert("保存失败");
	    });

	   
	   return url2;
}
function getcurWwwPath_web() {
    //获取当前网址，如： http://localhost:8080/usolv-risk-control/auth/login
    var curWwwPath = window.document.location.href;
    //获取主机地址之后的目录，如： usolv-risk-control/auth/login
    var pathName = window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8080
    var localhostPaht = curWwwPath.substring(0, pos);
    return (localhostPaht+"/");
}
//type = 1 得到url的hash名 type =2 得到url的真实名
function getUrlAdr(urlStr,type) {
	var arr=urlStr.split("|");
	var curWwwPath = getcurWwwPath_web() ;
	if (type == 1){
		return (curWwwPath + arr[0]);
	}else if(type == 2){
		return arr[1];
	}
	    return -1;
}



/*----------获取值列表---------------*/
function getSelectOption(id, url,type) {
	$.getJSON(url, function(data) {
		$("#" + id).empty();
		var strHtml = [];
		if(type==0){
			strHtml.push("<option value=''>------请选择-----</option>");
		}
		$.each(data, function(k, v) {
			strHtml.push("<option value='" + v.id + "'>" + v.state
					+ "</option>");
		});
		$("#" + id).html(strHtml.join(""));// 显示处理后的数据
	});
}

/*--------------tree-----------------*/


function bindtree_user(url, treeId,key) {
	$.post(url,{},function(data){
		var dataObj = window.JSON.parse(data);
		//var dataObj = eval("(" + data + ")");
        var treeObj =jQuery.fn.zTree.init(jQuery("#"+treeId), setting , dataObj.data);
        var nn = treeObj.getCheckedNodes();
        for (var i = 0, l = nn.length; i < l; i++) {
        	treeObj.checkNode(nn[i], true, true);
        }
        
        var ids = dataObj.ids;
        var roleId = dataObj.roleId;
        var roleName = dataObj.roleName;
        var roleStatus = dataObj.roleStatus;
       // treeObj.selectNode(node,false);//默认定位到哪个节点
        if(key==1){
        	$("#rolename1").val(roleName);
        	$("#status1").val(roleStatus);
        	$("#rolebox").attr("roleId",roleId);
        	updatename=roleName;
        }else if(key==0){
        	$("#rolename1").val("");
        	$("#status1").val(1);
        	$("#rolebox").attr("roleId","");
        	$("#rolename1").next("span").html("");
        	 toSureContr = 1;
        	updatename = "";
        }else{
        	$("#rolename1").val(roleName);
        	$("#rolename1").attr("readonly","readonly");
        	$("#status1").val(roleStatus);
        	$("#status1").attr("readonly","readonly");
        	$("#rolebox").attr("roleId","");
        	updatename=roleName;
        }
        //treeObj.expandNode(node, true, true, true);
	 });
}

function bindtree_user2(url, treeId,key) {
	$.post(url,{},function(data){
		var dataObj = window.JSON.parse(data);
		//var dataObj = eval("(" + data + ")");
        var treeObj =jQuery.fn.zTree.init(jQuery("#"+treeId), setting2 , dataObj.data);
        var nn = treeObj.getCheckedNodes();
        for (var i = 0, l = nn.length; i < l; i++) {
        	treeObj.checkNode(nn[i], true, true);
        }
        
        var ids = dataObj.ids;
        var roleId = dataObj.roleId;
        var roleName = dataObj.roleName;
        var roleStatus = dataObj.roleStatus;
       // treeObj.selectNode(node,false);//默认定位到哪个节点
        if(key==1){
        	$("#rolename1").val(roleName);
        	$("#status1").val(roleStatus);
        	$("#rolebox").attr("roleId",roleId);
        	updatename=roleName;
        }else if(key==0){
        	$("#rolename1").val("");
        	$("#status1").val(1);
        	$("#rolebox").attr("roleId","");
        	$("#rolename1").next("span").html("");
        	 toSureContr = 1;
        	updatename = "";
        }else{
        	$("#rolename1").val(roleName);
        	$("#rolename1").attr("readonly","readonly");
        	$("#status1").val(roleStatus);
        	$("#status1").attr("readonly","readonly");
        	$("#rolebox").attr("roleId","");
        	updatename=roleName;
        }
        //treeObj.expandNode(node, true, true, true);
	 });
}
function bindtree_role(url, treeId,key) {
	$.post(url,{},function(data){
		var dataObj = window.JSON.parse(data);
		//var dataObj = eval("(" + data + ")");
        var treeObj =jQuery.fn.zTree.init(jQuery("#"+treeId), setting , dataObj.data);
        var nn = treeObj.getCheckedNodes();
       /* for (var i = 0, l = nn.length; i < l; i++) {
        	treeObj.checkNode(nn[i], true, true);
        }*/
        
        var ids = dataObj.ids;
        var roleId = dataObj.roleId;
        var roleName = dataObj.roleName;
        var roleStatus = dataObj.roleStatus;
       // treeObj.selectNode(node,false);//默认定位到哪个节点
        if(key==1){
        	$("#rolename1").val(roleName);
        	$("#status1").val(roleStatus);
        	$("#rolebox").attr("roleId",roleId);
        	updatename=roleName;
        }else if(key==0){
        	$("#rolename1").val("");
        	$("#status1").val(1);
        	$("#rolebox").attr("roleId","");
        	$("#rolename1").next("span").html("");
        	 toSureContr = 1;
        	updatename = "";
        }else{
        	$("#rolename2").val(roleName);
        	$("#rolename2").attr("readonly","readonly");
        	if("1" == roleStatus){
        		$("#status2").val("启用");
        	}else{
        		$("#status2").val("禁用");
        	}
        	
        	$("#status2").attr("readonly","readonly");
        	$("#rolebox").attr("roleId","");
        	updatename=roleName;
        }
        //treeObj.expandNode(node, true, true, true);
	 });
}

function bindtreeUser(url, treeId,key) {
	$.post(url,{},function(data){
		var dataObj = window.JSON.parse(data);
		//var dataObj = eval("(" + data + ")");
        var treeObj =jQuery.fn.zTree.init(jQuery("#"+treeId), setting , dataObj.data);
     
        //treeObj.expandNode(node, true, true, true);
	 });
}

function bindtreeUser2(url, treeId,key) {
	$.post(url,{},function(data){
		var dataObj = window.JSON.parse(data);
		//var dataObj = eval("(" + data + ")");
        var treeObj =jQuery.fn.zTree.init(jQuery("#"+treeId), setting2 , dataObj.data);
     
        //treeObj.expandNode(node, true, true, true);
	 });
}

var setting = {
		check : {
			enable : true,
		/*
		 * chkStyle: "checkbox", chkboxType: { "Y": "p", "N": "s" }
		 */
		},
		view : {
			selectedMulti : false
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		callback : {
			onCheck : function(e, treeId, treeNode) {
				// alert("当前选中的节点《"+treeNode.name+"》，节点id是："+treeNode.id+"。。。值已给，自己修改着用哈。。。"+treeNode.GXM);
			},
			onClick : function(e, treeId, treeNode) {
				//alert("当前选中的节点《" + treeNode.name + "》，节点id是：" + treeNode.id
				//		+ "。。。值已给，自己修改着用哈。。。" + treeNode.GXM);
			}
		}
	};

var setting2 = {
		check : {
			enable : true,
		
		  chkStyle: "checkbox", chkboxType: { "Y": "", "N": "" }
		
		},
		view : {
			selectedMulti : false
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		callback : {
			onCheck : function(e, treeId, treeNode) {
				// alert("当前选中的节点《"+treeNode.name+"》，节点id是："+treeNode.id+"。。。值已给，自己修改着用哈。。。"+treeNode.GXM);
			},
			onClick : function(e, treeId, treeNode) {
				//alert("当前选中的节点《" + treeNode.name + "》，节点id是：" + treeNode.id
				//		+ "。。。值已给，自己修改着用哈。。。" + treeNode.GXM);
			}
		}
	};

//字符串处理
stringProcessing = {
	 
	    /* 去除重复的元素*/
	    uniqueArray : function (data){
	        data = data || [];
	        var a = {};
	        for(var i = 0; i<data.length; i++){
	            var v = data[i];
	            if(typeof a[v] == 'undefined'){
	                a[v] = 1;
	            }
	        }
	        data.length = 0;
	        for(var i in a){
	            data[data.length] =i;
	        }
	        return data;
	    },
	    /**
	     * 将null转换为空字符串;
	     */
	    null2Empty: function (str,lengthVal) {
	        var len = lengthVal;//截取字符串长度，超过长度显示点点
	        var temp = "";
	        if (str != null) {
	            //alert(str.length);
	            if (str.length > len) {
	                temp = str.replace(/\s+/g, '');
	                temp = temp.replace(/\>/g, '');
	                temp = temp.replace(/\</g, '');
	                temp = temp.substring(0, len) + "...";
	            } else {
	                if (str == 'null') {
	                    tmp = '';
	                } else {
	                    temp = str;
	                }
	            }
	        }
	        return temp;
	    }
};


//不能输入特殊字符
var reg = /[\%|\'|\|\"|\?|\#|\;|\,\，\；\“\‘]+/;

//鼠标去验证
focus_validate = {
    // 只能输入X到Y间字符
    between_x_y :function(id,errP,x,y){
        $("#"+id).focus(function(){
            $(this).next(errP).html("");

        }).blur(function(){
            if($(this).val().length <x || $(this).val().length >y){
                $(this).next(errP).html("输入"+x+"-"+y+"间字符");
                return;
            }else if(reg.test($(this).val())){
                $(this).next(errP).html("不能输入特殊字符");
                return;
            }
        });
    },
    //密码验证
    password_5_15 :function(id,str){
		   if("" == str){
			   str = "请输入5-15字符字母或数字!";
			}
		    var regex = /^[a-zA-Z0-9]{5,15}$/;
		    $("#"+id).focus(function(){
		        $(this).next("span").html("");
	
		    }).blur(function(){
		        if(!regex.test($(this).val())){
		            $(this).next("span").html(str);
		            return;
		        }
		    });
	 },
    //联系电话不能为空，正则验证
	 valiTel :function(id,str){
		 if("" == str){
			 str = "请输入正确手机或座机电话！";
		}
	   
	    var regex =/^((0([\d]{2,3}))-([\d]{7,8})|([\d]{7,8}))$/;// 验证电话号码： 正确格式：xxx/xxxx-xxxxxxx/xxxxxxxx；
	    var regex2 =/^[0]?1([3,5,7,8][\d]{9})$/;// 验证手机号码
	    $("#"+id).focus(function(){
	        $(this).next("span").html("");

	    }).blur(function(){
	        var me = this;
	        var isSuccess = regex.test(me.value) || regex2.test(me.value);
	        if(!isSuccess ){
	            $(this).next("span").html(str);
	        }
	    });
	},
	//电子邮箱，可以为空，正则验证
	 valiEmail_null : function(id,str){
		 if("" == str){
			 str = "请输入有效电子邮箱！";
		}
	    
	    var regex =/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
	    $("#"+id).focus(function(){
	        $(this).next("span").html("");

	    }).blur(function(){
	    	if($("#"+id).val() !="" && $("#"+id).val() !=null){
		        if("" == $(this).val()  || null == $(this).val() ){
		        	$(this).next("span").html("");
		        }else if(!regex.test($(this).val())){
		        	$(this).next("span").html(str);
		        }
	    	}
	    });
	},
	//4.电子邮箱，不能为空，正则验证
	 valiEmail : function(id,str){
		 if("" == str){
			 str = "请输入有效电子邮箱！";
		}
	    
	    var regex =/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
	    $("#"+id).focus(function(){
	        $(this).next("span").html("");

	    }).blur(function(){
	        if(!regex.test($(this).val())){
	        	$(this).next("span").html(str);
	        }

	    });
	},

}

//点确定时去验证
confirm_validate = {
    // 只能输入X到Y间字符,不能输入特殊字符
    between_x_y :function(id,errP,x,y){
        var flag = true;
        if($("#"+id).val().length <x || $("#"+id).val().length >y){
            $("#"+id).next(errP).html("输入"+x+"-"+y+"间字符");
            flag = false;
        }else if(reg.test($("#"+id).val())){
            $("#"+id).next(errP).html("不能输入特殊字符");
            flag = false;
        }else{
        	 $("#"+id).next(errP).html("");
        }
        return flag
    },
    between2_x_y :function(id,errP,x,y){
        var flag = true;
        if($("#"+id).val().length <x || $("#"+id).val().length >y){
            $("#"+id).next(errP).html("输入"+x+"-"+y+"间字符");
            flag = false;
        }
        return flag;
    },
    
    password_5_15 :function(id,str){
    	var flag = true;
		if("" == str){
			str = "请输入5-15字符的字母或数字!";
		}
	    var regex = /^[a-zA-Z0-9]{5,15}$/;
	    if(!regex.test($("#"+id).val())){
	    	 $("#"+id).next("span").html(str);
	    	 flag = false;
	    }
	    return flag;
	},
	 name_1_15 :function(id,str){
	    	var flag = true;
			if("" == str){
				str = "请输入1-15字符字母或数字!";
			}
		    var regex = /^[a-zA-Z0-9]{1,15}$/;
		    if(!regex.test($("#"+id).val())){
		    	 $("#"+id).next("span").html(str);
		    	 flag = false;
		    }
		    return flag;
	},

  //3.联系电话不能为空，正则验证
	 valiTel :function(id,str){
		 var flag = true;
		 if("" == str){
			 str = "请输入正确手机或座机电话！";
		  }
	    var regex =/^((0([\d]{2,3}))-([\d]{7,8})|([\d]{7,8}))$/;// 验证电话号码： 正确格式：xxx/xxxx-xxxxxxx/xxxxxxxx；
	    var regex2 =/^[0]?1([3,5,7,8][\d]{9})$/;// 验证手机号码
	    var isSuccess = regex.test($("#"+id).val()) || regex2.test($("#"+id).val());
       if(!isSuccess ){
	       	$("#"+id).next("span").html(str);
	       	flag = false;
       }
       return flag;
	},
	//4.电子邮箱，不能为空，正则验证
	 valiEmail : function(id,str){
		 var flag = true;
		 if("" == str){
			 str = "请输入有效电子邮箱！";
		  }
	    var regex =/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;

           if(!regex.test($("#"+id).val()) ){
	           	$("#"+id).next("span").html(str);
	           	flag = false;
            }
           if($("#"+id).val().length > 50){
	           	$("#"+id).next("span").html("不可超过50个字符！");
	           	flag = false;
           }

	    return flag;
	},
	//4.电子邮箱，为空，正则验证
	 valiEmail_null : function(id,str){
		 var flag = true;
		 if("" == str){
			 str = "请输入有效电子邮箱！";
		  }
	    var regex =/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
	    if($("#"+id).val() !="" && $("#"+id).val() !=null){
          if(!regex.test($("#"+id).val()) ){
	           	$("#"+id).next("span").html(str);
	           	flag = false;
           }
          if($("#"+id).val().length > 50){
	           	$("#"+id).next("span").html("不可超过50个字符！");
	           	flag = false;
          }
	    }
	    return flag;
	}
}


function getRootPath_web() {
    //获取当前网址，如： http://localhost:8080/usolv-risk-control/auth/login
    var curWwwPath = window.document.location.href;
    //获取主机地址之后的目录，如： usolv-risk-control/auth/login
    var pathName = window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8080
    var localhostPaht = curWwwPath.substring(0, pos);
    //获取带"/"的项目名，如：/usolv-risk-control
    //alert(pathName);
    var path  = new Array();
    path = pathName.split("/");
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    if(path[1].indexOf("-") > 0 ){
        console.log(localhostPaht + projectName);
        return (localhostPaht + projectName);
    }else{
        console.log(localhostPaht + projectName);
        return (localhostPaht);
    }
}

function myBrowser(){
    var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
    var isOpera = userAgent.indexOf("Opera") > -1;
    if (isOpera) {
        return "Opera"
    }; //判断是否Opera浏览器
    if (userAgent.indexOf("Firefox") > -1) {
        return "FF";
    } //判断是否Firefox浏览器
    if (userAgent.indexOf("Chrome") > -1){
  return "Chrome";
 }
    if (userAgent.indexOf("Safari") > -1) {
        return "Safari";
    } //判断是否Safari浏览器
    if (userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera) {
        return "IE";
    }; //判断是否IE浏览器
}













