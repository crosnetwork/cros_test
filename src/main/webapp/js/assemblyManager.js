/**
 * Created by wangjing on 2017/5/9.
 */

$(function(){
	loadDataList(1,10);
	assembly.saveData();

})

assembly = {

    //清除所有弹出框内容
    clearAll :function(){
        $("#assemblyBox .input2").val("");
        $(".err_desc").html("");
    },


    /*
     * 新增 修改
     * id =“”新增   id=###修改
     * */
    openBox :function(id){
        if("" == id){//新增
            $("#title_names").html("添加组件");
            assembly.clearAll();
        }else{
            $("#title_names").html("编辑组件");
            assembly.clearAll();
            //通过后台读取数据 todo
            assembly.findData(id);
        }
        Messages.showBox("assemblyBox");
    },
    //界面加载数据
    findData :function(id){
        var url =getRootPath_web()+"/Intelligent/queryComponent?id="+id;
        $.ajax({
            url : url,
            type : 'post',
            async : false,
            cache : false,
            dataType : 'json',
            success : function(data) {
                if(data){
                	$("#com_id").val(id);
                    $("#key").val(data.key);
                    $("#description").val(data.description);
                    $("#comValue").val(data.comValue);
                    $("#status").val(data.status);
                }

            }
        });
    },

   /*
   * 单击保存数据
   * */
    saveData :function(id) {
        $('#form').validate({
            onFocus: function () {
                return false;
            },
            onBlur: function () {
                var $parent = this.parent();
                var _status = parseInt(this.attr('data-status'));
                if (!_status) {
                    $parent.addClass('error');
                }
                return false;
            }
        });

        $('#form').on('submit', function (event) {
            event.preventDefault();
            $(this).validate('submitValidate'); //return boolean;
            if ($(this).validate('submitValidate')) { //return true 验证通过，读取后台代码
                var json ={};
                json.com_id = $("#com_id").val();
                json.key = $("#key").val();
                json.description = $("#description").val();
                json.status = $("#status").val();
                json.comValue =$("#comValue").val();
                var url =getRootPath_web()+"/Intelligent/saveComponent";
                $.ajax({
                    url : url,
                    type : 'post',
                    async : false,
                    cache : false,
                    dataType : 'json',
                    data : {'list':JSON.stringify(json)},
                    success : function(data) {
                        Messages.alertMsg(data.success);
                        //关闭弹框
                        Messages.closeBox('assemblyBox');
                        loadDataList(1,10);//加载数据

                    }
                });

            }

        })
    },

    //批量删除
    deleteOpreat :function (){
        var chk_value =[];
        $('input[name="checkbox5"]:checked').each(function(){
            chk_value.push($(this).val());
        });

        if(chk_value.length==0){
            Messages.alertMsg("请至少勾选一条记录");
        }else{
            //以及传入一个或者多个值，到后台
            assembly.deletedata(chk_value);
        }
    },
    //执行删除数据请求
    deletedata :function (BDZID){
        var pageCurrent=$("#currPage").val();
        Messages.confirmMsg(a,"确定删除数据");
        function a(){
            //在要删除的时候出现等待的数据遮罩层
            Messages.opendWaiting();
            var url=getRootPath_web()+"/Intelligent/deleteComponent?id=" + BDZID;
            $.ajax({
                url: url,
                type: 'post',
                cache: false,
                dataType: 'json',
                success: function (data) {
                    Messages.closedWaiting();
                    Messages.alertMsg(data.success);
                    loadDataList(pageCurrent,10);
                },
                error: function () {
                    Messages.closedWaiting();
                    Messages.alertMsg("操作失败");
                }
            });
        }
    }
}


function loadDataList(pageCurrent,pagesize){
    var assemblyName =$("#assemblyName").val();
    var url =getRootPath_web()+"/Intelligent/getComponentsContent?assemblyName="+encodeURI(encodeURI(assemblyName))+"&currPage=" + pageCurrent;
    $.post(url,function(data){
        $("#tbdata").empty();
        var strHtml = [];
        var json=window.JSON.parse(data);
        $.each(json.datas,function(k,v){
            strHtml.push("<tr>");
            strHtml.push("<td name='inputtd'>");
            strHtml.push("<input type='checkbox' name='checkbox5' value='"+(v.id)+"'>");
            strHtml.push("</td>");
            strHtml.push("<td >"+(k+1)+"</td>");
            strHtml.push("<td>"+ v.key+"</td>");
            strHtml.push("<td >"+ v.description+"</td>");
            strHtml.push("<td >"+ v.comValue+"</td>");
            strHtml.push("<td >"+ v.status+"</td>");
            strHtml.push("<td>");
            strHtml.push("<a style='margin-right: 5px;' title='编辑信息'   onclick='assembly.openBox(\""+v.id+"\")'><i class='icon-edit btn_pic'></i></a>");
            strHtml.push("</td>");
            strHtml.push("</tr>");
        });
        $("#tbdata").html(strHtml.join(""));//显示处理后的数据

        $("#lbl_totalRecords").html(json.totl);
        $("#lbl_currPage").html(json.currentPage);
        $("#lbl_totalPage").html(json.totalPage);
        $("#currPage").val(json.currentPage);
        $("#totalPage").val(json.totalPage);
        ajaxDoPaging.initPaging();
        //显示table鼠标悬停样式
        $("#tbdata tr").hover(function () {
            $(this).find("td").css("background-color",  "#f7f7f7");
        }, function () {
            $(this).find("td").css("background-color", "#FFFFFF");
        });
        //eckbox全选
        $("#check_all").click(function () {
            var flag=false;
            if(document.getElementById('check_all').checked){
                flag=true;
            }
            $("input[name='checkbox5']:checkbox").each(function(){
                this.checked=flag;
            });
        });

    });
}













