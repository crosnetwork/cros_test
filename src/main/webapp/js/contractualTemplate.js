/**
 * Created by wangjing on 2017/5/9.
 */

$(function(){
	loadDataList(1,10);
    contractual.saveData();

})

contractual = {

    //清除所有弹出框内容
    clearAll :function(){
        $("#treeDemo").empty();
        $("#treeDemo2").empty();
        $("#treeDemo3").empty();
    },

    /*
     * 新增 修改
     * id =“”新增   id=###修改
     * */
    openBox :function(id){
        contractual.clearAll();
        if("" == id){//新增
            $("#title_names").html("添加合约");
        }else{
            $("#title_names").html("编辑合约");
        }
        //通过后台读取数据 todo
        contractual.findData(id);
        Messages.showBox("contractualBox");
    },
    //新增，编辑 界面加载数据
    findData :function(id){
       /* var url = "";
        if("" == id){//新增
            url =getRootPath_web()+"/##/####";
        }else{
            url =getRootPath_web()+"/##/####?id="+id;
        }
         $.ajax({
             url : url,
             type : 'post',
             async : false,
             cache : false,
             dataType : 'json',
             success : function(data) {
                 if(data){
                    //加载 tree todo
                 }
             }
         });*/
        var Nodesleft =[
            { id:1, pId:0, name:"parent node 1", open:true},
            { id:11, pId:0, name:"leaf node 1-1"},
            { id:12, pId:0, name:"leaf node 1-2"},
            { id:13, pId:0, name:"leaf node 1-3"},
            { id:2, pId:0, name:"parent node 2", open:true},
            { id:21, pId:0, name:"leaf node 2-1"},
            { id:22, pId:0, name:"leaf node 2-2"},
            { id:23, pId:0, name:"leaf node 2-3"},
            { id:3, pId:0, name:"parent node 3", open:true },
            { id:31, pId:0, name:"leaf node 3-1"},
            { id:32, pId:0, name:"leaf node 3-2"},
            { id:33, pId:0, name:"leaf node 3-3"}
        ];
        var Nodesright =[
            { id:1, pId:0, name:"parent node 1",a:"1111", open:true},
            { id:11, pId:1, name:"leaf node 1-1",a:"1112"},
            { id:12, pId:1, name:"leaf node 1-2",a:"1113"},
            { id:13, pId:1, name:"leaf node 1-3",a:"1114"},
            { id:2, pId:0, name:"parent node 2",a:"1115", open:true},
            { id:21, pId:2, name:"leaf node 2-1",a:"1116"},
            { id:22, pId:2, name:"leaf node 2-2",a:"1117"},
            { id:23, pId:2, name:"leaf node 2-3",a:"1118"},
            { id:3, pId:0, name:"parent node 3",a:"1119", open:true },
            { id:31, pId:3, name:"leaf node 3-1",a:"11110"},
            { id:32, pId:3, name:"leaf node 3-2",a:"11113"},
            { id:33, pId:3, name:"leaf node 3-3",a:"1114"}
        ];
        $.fn.zTree.init($("#treeDemo1"), setting1,Nodesleft );
        //如果是新增，那么Nodesright是空的
        $.fn.zTree.init($("#treeDemo2"), setting2, Nodesright);


    },
    /*
    * 预览合约
    * */
    findContra : function(id){
        //查看界面数据加载
        /*var url =getRootPath_web()+"/##/####?id="+id;
        $.ajax({
            url : url,
            type : 'post',
            async : false,
            cache : false,
            dataType : 'json',
            success : function(data) {
                if(data){
                    //加载 tree todo
                }

            }
        });*/
        var Nodes3 =[
            { id:1, pId:0, name:"pNode 1",a:"1111", open:true},
            { id:11, pId:1, name:"pNode 11",a:"1111"},
            { id:111, pId:11, name:"leaf node 111",a:"1111"},
            { id:112, pId:11, name:"leaf node 112",a:"1111"},
            { id:113, pId:11, name:"leaf node 113",a:"1111"},
            { id:114, pId:11, name:"leaf node 114",a:"1111"},
            { id:12, pId:1, name:"pNode 12",a:"1111"},
            { id:121, pId:12, name:"leaf node 121",a:"1111"},
            { id:122, pId:12, name:"leaf node 122",a:"1111"},
            { id:123, pId:12, name:"leaf node 123",a:"1111"},
            { id:124, pId:12, name:"leaf node 124",a:"1111"},
            { id:13, pId:1, name:"pNode 13 - no child",a:"1111"},
            { id:2, pId:0, name:"pNode 2",a:"1111"},
            { id:21, pId:2, name:"pNode 21",a:"1111", open:true},
            { id:211, pId:21, name:"leaf node 211",a:"1111"},
            { id:212, pId:21, name:"leaf node 212",a:"1111"},
            { id:213, pId:21, name:"leaf node 213",a:"1111"},
            { id:214, pId:21, name:"leaf node 214",a:"1111"},
            { id:22, pId:2, name:"pNode 22",a:"1111"},
            { id:221, pId:22, name:"leaf node 221",a:"1111"},
            { id:222, pId:22, name:"leaf node 222",a:"1111"},
            { id:223, pId:22, name:"leaf node 223",a:"1111"},
            { id:224, pId:22, name:"leaf node 224",a:"1111"},
            { id:23, pId:2, name:"pNode 23",a:"1111"},
            { id:231, pId:23, name:"leaf node 231",a:"1111"},
            { id:232, pId:23, name:"leaf node 232",a:"1111"},
            { id:233, pId:23, name:"leaf node 233",a:"1111"},
            { id:234, pId:23, name:"leaf node 234",a:"1111"},
            { id:3, pId:0, name:"pNode 3 - no child",a:"1111"}
        ];
        $.fn.zTree.init($("#treeDemo3"), setting3, Nodes3);
        Messages.showBox("findBox");
    },




    /*
     * 单击保存数据
     * */
    saveData :function() {
        $("#save").click(function(){
            var ztree =  $.fn.zTree.getZTreeObj("treeDemo2");
            var nodes = ztree.getNodes();
            var json = {};
            var arr = [];
            for(var i=0; i<ztree.transformToArray(nodes).length; i++){
                json.id = ztree.transformToArray(nodes)[i].id;
                json.pId = ztree.transformToArray(nodes)[i].pId;
                json.name = ztree.transformToArray(nodes)[i].name;
                json.a = ztree.transformToArray(nodes)[i].a;
    //此处的open属性是父级菜单是否打开的标识，在初始化的时候，后台拼接的时候加上open，表面该节点为父级，为打开状态
                arr.push(json);
            }
            console.log(arr);//改值传入后台
            /*var url =getRootPath_web()+"/##/####";
             $.ajax({
                 url : url,
                 type : 'post',
                 async : false,
                 cache : false,
                 dataType : 'json',
                 data : {'list':JSON.stringify(arr)},
                 success : function(data) {
                     if(data){
                     //加载 tree todo
                     }

                 }
             });*/
        })
    }
}


function loadDataList(pageCurrent,pagesize){
    var contractualName =$("#contractualName").val();
    var url =getRootPath_web()+"/Intelligent/getAllModels?contractualName="+encodeURI(encodeURI(contractualName))+"&currPage=" + pageCurrent;
    $.post(url,function(data){
        $("#tbdata").empty();
        var strHtml = [];
        var json=window.JSON.parse(data);
        $.each(json.datas,function(k,v){
            strHtml.push("<tr>");
            strHtml.push("<td >"+(k+1)+"</td>");
            strHtml.push("<td>"+ v.key+"</td>");
            strHtml.push("<td >"+ v.description+"</td>");
            strHtml.push("<td >"+ v.comValue+"</td>");
            strHtml.push("<td>");
            strHtml.push("<a style='margin-right: 5px;' title='编辑信息'   onclick='contractual.openBox(\""+v.id+"\")'><i class='icon-edit btn_pic'></i></a>");
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

    });
}











