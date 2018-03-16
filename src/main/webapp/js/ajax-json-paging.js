/**
 * Created by wangjing on 2015/10/20.
 */
/*数据分页*/
$(function(){
    ajaxDoPaging.initPaging();
    var rex = /^[-\+]?\d+$/; //正则表达式判断是否是整数.
    $("#show_page_size").blur(function(){
        if ($.trim(this.value)=="") {
            $(this).css("color","red").focus();
            alert("每页显示记录数必须是整数,且大于0.");
            return;
        } else if (!rex.test(this.value) || parseInt(this.value)<1) {
            $(this).css("color","red").focus();
            alert("每页显示记录数必须是整数,且大于0.");
            return;
        } else{
            $(this).css("color","black");
        }
    });
})

/*定义 ajax 异步翻页对象*/
ajaxDoPaging ={
    /*
    *初始化分页
    * */
    initPaging : function(){
    	//获取当前总页数
        var totalPage = parseInt($("#totalPage").val());
        var currPage = parseInt($("#currPage").val());
        //如果总页数为<=1时，所有页面按钮都被禁用，不能进行翻页
        if(totalPage <= 1){//没有数据的情况下，所有的按钮都是禁用的
            this.enablePageBtn(true,true,true,true);
         }else{
             if(parseInt(currPage) <= 1){
                 this.enablePageBtn(true,true,false,false);
             }else if(parseInt(currPage) == parseInt(totalPage)){
                 this.enablePageBtn(false,false,true,true);
             }
         }
    },

    /*
    * 点击翻页按钮时执行事件
    * */
    jumpPage : function(btnId){
        var currPage = parseInt($("#currPage").val());
        var totalPage = parseInt($("#totalPage").val()); //数据加载的时候，隐藏区域赋值的总页数， 页面显示区域总页数lbl_totalPage
        var currpageCount =0; //除了首页，将要获取该值==（跳转过去后的当前码-1）*10
        var op = btnId;
        if(op == 'first'){
            //如果是首页，那么当前页就是1，【首页】【上一页】不能单击，【下一页】【尾页】可以使用
            this.enablePageBtn(true, true, false, false);
            //给页面当前页面赋值
            $("#currPage").val(1); //隐藏区域的当前页面值
            $("#lbl_currPage").html('1');//页面能够看到的区域的当前页码值
            currpageCount=0;
            //接下来，此处写你的方法 todo
                    //do something...
        }
        if(op == 'next'){
            if(currPage+1 == totalPage){//当前页是最后一页
               //如果是最后一页，那么当前页就是1，【下一页】【尾页】不能单击，【首页】【上一页】可以使用
                this.enablePageBtn(false,false,true,true);
                //给页面当前页面赋值
                $("#currPage").val(totalPage); //隐藏区域的当前页面值
                $("#lbl_currPage").html(totalPage);//页面能够看到的区域的当前页码值
                //接下来，此处写你的方法 todo
                currpageCount = (totalPage-1)*10; //你要的参数 （跳转过去的页码-1）*10
            }else{
                this.enablePageBtn(false,false,null,null);
                //给页面当前页面赋值
                $("#currPage").val(currPage+1); //隐藏区域的当前页面值
                $("#lbl_currPage").html(currPage+1);//页面能够看到的区域的当前页码值
                //接下来，此处写你的方法 todo
                currpageCount = currPage*10; //我跳转过去的页面，也就是在之前的页码上面+1，
            }
        }
        if(op == 'up'){//点击上一页
            if((currPage-1) <=1){ //上一页跳到第一页
                this.enablePageBtn(true,true,false,false);
                $("#currPage").val(1); //隐藏区域的当前页面值
                $("#lbl_currPage").html(1);
                //接下来，此处写你的方法 todo
                currpageCount = (currPage-1-1)*10;
            }else{
                this.enablePageBtn(null,null,false,false);
                $("#currPage").val(currPage-1); //隐藏区域的当前页面值
                $("#lbl_currPage").html(currPage-1);
                //接下来，此处写你的方法 todo
                currpageCount = (currPage-1-1)*10;
            }
        }
        if(op == 'last'){
            this.enablePageBtn(false,false,true,true);
            $("#currPage").val(totalPage); //隐藏区域的当前页面值
            $("#lbl_currPage").html(totalPage);
            currpageCount = (totalPage-1)*10;
            //接下来，此处写你的方法 todo
        }
        if(op == 'goto'){ //点击跳转到某一页
            var rex = /^[-\+]?\d+$/; //正则表达式判断是否是整数.
            var toPage = $.trim($("#toPage").val());
            if(toPage == ''){
                alert("请输入跳转的页数且必须是正整数");
                return;
            }
            if(!rex.test(toPage)){
                alert("跳转的页数必须是整数");
                return;
            }
            if(parseFloat(toPage) < 1){
                alert("跳转的页数必须是整数且大于0");
                return;
            }
            if((parseFloat(toPage) > totalPage)){//当跳转页小于1时
                alert("跳转的页数必须是整数且最大也数为"+totalPage);
                return;
            }
            //跳转到首页时
            /*if((parseFloat(toPage)==1)){
                this.excute(1,true,true,false,false);
            }else if(parseFloat(toPage) == totalPage){//跳转到尾页时
                this.excute(totalPage,false,false,true,true);
            }else{//否则按钮的状态全部可用
                this.excute(toPage,false,false,false,false);
            }*/
        }
        this.loaddata($("#currPage").val(), 10);
    },
    /*
     * 加载后台数据
     * @param currpageCount mysql分页查询下标
     * @param pagesize 页面条数
     */
    loaddata : function(currpageCount,pagesize){
        //ajax执行送数据到后台
    	loadDataList(currpageCount,pagesize);
    },
    /*
    * 启用翻页
    * @param {object} toPage 将要跳转到指定的页
    * @param {object} isFirst 首页按钮是否可用
    * @param {object} isUp 上一页按钮是否可用
    * @param {object} isNext 下一页按钮是否可用
    * @param {object} isLast 尾页按钮是否可用
    * */
    excute : function(toPage,isFirst,isUp,isNext,isLast){
        //ajax执行送数据到后台
        if(toPage > 0){
            //this.ajaxPOST(toPage);

            $("#currPage").val(toPage >0?toPage:1);
            $("#lbl_currPage").html(toPage >0?toPage:1);
            //改变按钮状态
            this.enablePageBtn(isFirst,isUp,isNext,isLast);
        }
    },

    /**
     * 改变翻页按钮的状态.
     * @param {Object} isFirst 首页按钮是否可用
     * @param {Object} isUp 上一页按钮是否可用
     * @param {Object} isNext 下一页按钮是否可用
     * @param {Object} isLast 尾页按钮是否可用
     */
    enablePageBtn : function(isFirst, isUp, isNext, isLast) {
        if(isFirst != undefined && isFirst != null)
            $("#first").attr("disabled", isFirst);
        if(isUp != undefined && isUp != null)
            $("#up").attr("disabled", isUp);
        if(isNext != undefined && isNext != null)
            $("#next").attr("disabled", isNext);
        if(isLast != undefined && isLast != null)
            $("#last").attr("disabled", isLast);
    },

    /*
    * ajax 发送数据到后台
    * @param {object} toPage 将要跳转到指定的页
    * */
    ajaxPOST : function(toPage){
        //添加锁屏动作
        Messages.opendWaiting();//tools.js
        $("pageIndexNo").val(toPage);
        var url = $("#PageForm").attr("action");//此处action路径
        var jsonPara = [];
        var normalPara=[];
        jsonPara.push("{");
        $("#PageForm input[type='hidden']").each(function(){
            jsonPara.push('"'+ $.trim((this.name)) +'":"'+ $.trim((this.value)) +'",');
            normalPara.push($.trim((this.name)) +'='+ $.trim((this.value)) +'&');
        });
        jsonPara.push('"indexNo":"'+toPage+'"');
        jsonPara.push("}");
        var paras = normalPara.join("");
        paras += "jsonParams=" + jsonPara.join("");

        paras =  jsonUtils.formToJson("PageForm");//json-utils.js
        $.ajax({
            type : 'POST',
            url : url,
            cache : false,
            data : paras,
            complete : function(res) {//请求完成后
                if(res != null && $.trim(res.responseText) != "null" && $.trim(res.responseText) != ""){
                    var json = eval("("+res.responseText+")");
                    ajaxDoPaging.reflash(json);
                    $("#dynamicData").empty().html(formationData(json));//此处你需要重新加载table表单，按照之前读取数据的模式更改此处。
                    //关闭锁屏
                    Messages.closedWaiting();//tools.js
                }
            }
        })

    },
    /*
    * 刷新翻页表单中的数据，及翻页的按钮的状态
    * @param {object} json 后台返回json格式数据（后台返回的必须是翻页对象的json格式数据）
    * */
    reflash : function(json){
        $("#lbl_totalRecords").html(json==null? 0: json.count);
        $("#lbl_currPage").html(json ==null? 1: json.indexNo);
        $("#lbl_totalPage").html(json ==null? 0: json.pageTotal);
        $("#show_page_size").val(json == null ? 0: json.pageSize);
        $("#totalRecords").val(json == null ? 0: json.count);
        $("#currPage").val(json == null ? 1: json.indexNo);
        $("#totalPage").val(json == null ? 0: json.pageTotal);

        if(json !=null && json.dataMap!= undefined && json.dataMap.length>0 && json.pageTotal > 1){
            //如果当前页等于总页数时,禁用下一页和尾页按钮
            if(parseInt(json.indexNo) == parseInt(json.pageTotal)){
                $("#next").attr("disabled",true);
                $("#last").attr("disabled",true);
            }else{
                $("#next").attr("disabled",false);
                $("#last").attr("disabled",false);
                $("#goto").attr("disabled",false);
            }
        }else{
            this.excute(-1, true, true, true, true);
            $("#goto").attr("disabled",true);
        }
    }

}

function updatePageSize(){
    ajaxDoPaging.enablePageBtn(false,true,true,true);
}





























































