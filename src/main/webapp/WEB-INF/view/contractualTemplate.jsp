<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge" charset="UTF-8" />
    <title>合约模板</title>
    <link rel="stylesheet" type="text/css"  href="<%=request.getContextPath()%>/css/font-awesome.css">
    <link rel="stylesheet" type="text/css"  href="<%=request.getContextPath()%>/css/ztree/demo.css">
    <link rel="stylesheet" type="text/css"  href="<%=request.getContextPath()%>/css/ztree/zTreeStyle/zTreeStyle.css">
    <link rel="stylesheet" type="text/css"  href="<%=request.getContextPath()%>/css/prompt.css">
    <link rel="stylesheet" type="text/css"  href="<%=request.getContextPath()%>/css/public.css">
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/ztree/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/ztree/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/ztree/jquery.ztree.excheck.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/ztree/jquery.ztree.exedit.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/ajax-json-paging.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/util.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/ztreeOper.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/contractualTemplate.js"></script>

    <style>
        div.zTreeDemoBackground {
            width: 250px;
            height: 362px;
            text-align: left;
            display: inline-block;
        }
        ul.z2 {
            margin-top: 10px;
            border: 1px solid #617775;
            width: 100%;
            height: 360px;
            overflow-y: auto;
            overflow-x: auto;
        }


    </style>
</head>
<body>
<div class="content">
    <ul class="ul_float">
        <li><label class="label">关键字：</label>
            <div class="input-group" ><input name="name" id="contractualName" type="text" class="input" ></div>
        </li>
        <li style="padding-top: 3px;">
            <button class="btn btn-red" type="button" style="margin-bottom:0px;padding: 5px 20px;"  onclick="loadDataList(1,10)">查询</button>
            <button class="btn btn-yellow" type="button" style="margin-bottom:0px;padding: 5px 20px;" onclick="contractual.openBox('')">新增</button>
        </li>
    </ul>
    <table class="table">
        <thead>
        <tr>
			<th style="width:3%;">序号</th>
            <th style="width:17%;">字段</th>
            <th style="width:27%;">描述</th>
            <th style="width:42%;">内容</th>
            <th style="width:8%;">操作</th
        </tr>
        </thead>
        <tbody id="tbdata">
        </tbody>
    </table>
    <!--table分页-->
    <div class="tune_page tune_page_top">
        <div style="display:inline;">
            共有&nbsp;<label id="lbl_totalRecords" style="width:auto;">73</label>&nbsp;条记录，
            每页显示 <input id="show_page_size" name="PAGE_SIZE" style="width: 25px;" value="10" disabled="">条记录，
            当前第<label id="lbl_currPage" style="width:auto;">1</label>/<label id="lbl_totalPage" style="width:auto;">8</label>页
        </div>
        <div style="display:inline; float: right;">
            <input id="first" type="button" class="mybtn first_page" onclick="ajaxDoPaging.jumpPage('first')" value="首页" disabled="disabled">
            <input id="up" type="button" class="mybtn previous_page " onclick="ajaxDoPaging.jumpPage('up')" disabled="disabled">
            <input id="next" type="button" class="mybtn next_page " onclick="ajaxDoPaging.jumpPage('next')">
            <input id="last" type="button" class="mybtn last_page " onclick="ajaxDoPaging.jumpPage('last')" value="尾页">
            <input name="currPage" id="currPage" type="hidden" value="1">
            <input name="totalPage" id="totalPage" type="hidden" value="8">
        </div>
    </div>

</div>


<!--添加,编辑合约-->
<div id="contractualBox" class="update_box" style="display: none; z-index: 1003;width: 750px;">
    <div class="top_info">
        <p id="title_names">添加合约</p>
        <span onclick="Messages.closeBox('contractualBox');"></span>
    </div>
    <div class="information" style="padding: 10px 30px;display: inline-block;">
        <div style="width:100%;display: inline-block;margin-bottom: 20px;">
            <div class="zTreeDemoBackground left" style="float: left;">
                <label>组件：</label>
                <ul id="treeDemo1" class="ztree"></ul>
            </div>
            <div  style="display: inline-block; width: 175px;float: left;text-align: center; height: 391px; line-height: 391px;" >
                <i class="icon-arrow-right" style="color: #7f7a79;font-size: 50px;"></i>
            </div>
            <div class="zTreeDemoBackground right" style="float: right;width: 225px;">
                <label>合约模板：</label>
                <ul id="treeDemo2" class="ztree"></ul>
            </div>
        </div>
        <div  class="text-usser" style="float: left;margin: 15px 0px 30px 0px; text-align: center;margin-left:30px; width: 100%; display: inline-block;">
            <button class="sub_btn" id="save"  >确定 </button>
            <button class="sub_btn"   style=" background: #fbfbfb; border: 1px solid #ddd;color: #777 !important;" onclick="Messages.closeBox('contractualBox');">取消 </button>
        </div>
    </div>
</div>



<!--预览合约 ztree-->
<div id="findBox" class="update_box" style="display: none; z-index: 1003;width: 750px;">
    <div class="top_info">
        <p>预览合约</p>
        <span onclick="Messages.closeBox('findBox');"></span>
    </div>
    <div class="information" style="padding: 10px 30px;display: inline-block;">
        <div style="width:100%;display: inline-block;margin-bottom: 20px;">
            <div class="zTreeDemoBackground left" style="width: 100%;">
                <label>合约详情查看：</label>
                <ul id="treeDemo3" class="ztree z2"></ul>
            </div>
        </div>
    </div>
</div>

</body>
</html>