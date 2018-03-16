<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge" charset="UTF-8" />
    <title>组件管理</title>
    <link rel="stylesheet" type="text/css"  href="<%=request.getContextPath()%>/css/font-awesome.css">
    <link rel="stylesheet" type="text/css"  href="<%=request.getContextPath()%>/css/ztree/demo.css">
    <link rel="stylesheet" type="text/css"  href="<%=request.getContextPath()%>/css/ztree/zTreeStyle/zTreeStyle.css">
    <link rel="stylesheet" type="text/css"  href="<%=request.getContextPath()%>/css/prompt.css">
    <link rel="stylesheet" type="text/css"  href="<%=request.getContextPath()%>/css/public.css">
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-validate.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/ajax-json-paging.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/util.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/assemblyManager.js"></script>

</head>
<body>
<div class="content">
    <ul class="ul_float">
        <li><label class="label">组件名称：</label>
            <div class="input-group" ><input name="name" id="assemblyName" type="text" class="input" ></div>
        </li>
        <li style="padding-top: 3px;">
            <button class="btn btn-red" type="button" style="margin-bottom:0px;padding: 5px 20px;"  onclick="loadDataList(1,10)">查询</button>
            <button class="btn btn-yellow" type="button" style="margin-bottom:0px;padding: 5px 20px;" onclick="assembly.openBox('')">新增</button>
            <button class="btn btn-yellow" type="button" style="margin-bottom:0px;padding: 5px 20px;" onclick="assembly.deleteOpreat()">删除</button>
        </li>
    </ul>
    <table class="table">
        <thead>
        <tr>
            <th width="2%" style="height:30px;line-height:30px;" valign="middle" >
                <input class="first_marquee" type="checkbox" name="check_all" id="check_all">
            </th>
            <th style="width:3%;">序号</th>
            <th style="width:17%;">字段</th>
            <th style="width:27%;">描述</th>
            <th style="width:42%;">内容</th>
            <th style="width:3%;">状态</th>
            <th style="width:8%;">操作</th>
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



<!--弹出 组件 框-->
<div id="assemblyBox" class="update_box" style="display: none; z-index: 1003;width: 500px;">
    <div class="top_info">
        <p id="title_names">添加组件</p>
        <span onclick="Messages.closeBox('assemblyBox');"></span>
    </div>
    <div><input class="input2 required" id="com_id" style="visibility:hidden" /></div>
    <div class="information" style="padding: 10px 30px;display: inline-block;">
        <div style="width:100%;display: inline-block;">
            <form id="form">
                <div style="width:100%;display:inline-block;float:left;">
		
                    <div class="text_user">
                        <label><span class="spancolor">*</span>字段</label>
                        <input class="input2 required" id="key"   data-valid="isNots||between:5-20" data-error="不能输入特殊字符||请输入5-20个字符" >
                        <span class="err_desc"></span>
                    </div>
                    <div class="text_user">
                        <label><span class="spancolor">*</span>描述</label>
                        <input class="input2 required"  id="description" data-valid="isNots||between:5-20" data-error="不能输入特殊字符||请输入5-20个字符" >
                        <span class="err_desc"></span>
                    </div>
                    <div class="text_user">
                        <label><span class="spancolor">*</span>内容</label>
                        <input class="input2 required"  id="comValue" data-valid="isNots||between:5-20" data-error="不能输入特殊字符||请输入5-20个字符" >
                        <span class="err_desc"></span>
                    </div>
                    <div class="text_user">
                        <label><span class="spancolor">*</span>状态</label>
                        <select class="input2" id="status"  >
                            <option value="1">启用</option>
                            <option value="2">禁用</option>
                        </select>
                        <span class="err_desc"></span>
                    </div>
                </div>
                <div id="btngroup" class="text-usser" style="float: left;margin: 15px 0px 30px 0px; text-align: center;margin-left:30px; width: 100%; display: inline-block;">
                    <button class="sub_btn" id="btn1"  >确定 </button>
                    <button class="sub_btn"   style="background: #fbfbfb; border: 1px solid #ddd;color: #777 !important;" onclick="Messages.closeBox('assemblyBox');">取消 </button>
                </div>
            </form>
        </div>
    </div>
</div>


</body>
</html>