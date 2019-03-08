<!--导入EL表达式-->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <style type="text/css">
        #btn_upload{
            width: 68px;
            height: 34px;
        }
        #btn_upload input{
            width:0px;
            height:0px;
            position: absolute;
            left: -5px;
            border-style: none;
        }
        #btn_upload input:hover{
            border-style: none;
        }


    </style>
    <!--引入js和css样式-->
    <script src="webjars/jquery/3.1.1/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="webjars/bootstrap/3.3.5/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="../../css/bootstrap-table.css"/>
    <link rel="stylesheet" href="../../css/bootstrap-table.min.css"/>
    <script src="../../js/bootstrap-table.js"></script>
    <script src="../../js/bootstrap-table-zh-CN.min.js"></script>
    <script src="../../js/messenger.min.js"></script>
    <script src="../../js/bootstrapValidator.min.js"></script>
    <script src="../../js/moment.js"></script>
    <link rel="stylesheet" href="../../css/messenger.css"/>
    <link rel="stylesheet" href="../../css/messenger-theme-future.css"/>
    <link rel="stylesheet" href="../../css/bootstrapValidator.min.css"/>
    <!--本类js-->
    <script src="../../js/score.js"></script>
    <!--响应式布局-->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>测试</title>
</head>
<body>
    <div class="alert alert-success" id="select">
        查询条件
    </div>

    <div class="panel-body form-group" style="margin-bottom:0px;" id="chaxun" hidden=hidden>
        <label class="col-sm-1 control-label" style="text-align: right; margin-top:5px">pid：</label>
        <div class="col-sm-2">
            <input type="text" class="form-control" name="pid" id="pid"/>
        </div>
        <label class="col-sm-1 control-label" style="text-align: right; margin-top:5px">pname：</label>
        <div class="col-sm-2">
            <input type="text" class="form-control" name="pname" id="pname"/>
        </div>
        <div class="col-sm-2 col-sm-offset-4">
            <button class="btn btn-info " style="width: 150px;" id="search_btn">查询</button>
        </div>
    </div>
    <div id="tablediv">
<table id="bootstrapTable" class="table table-hover"></table>
    </div>
<div id="toolbar" class="btn-group pull-right" style="margin-right: 20px;">
    <button id="btn_edit" type="button" class="btn btn-success" style=" border-radius: 0">
        <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
    </button>
    <button id="btn_delete" type="button" class=" btn btn-danger ">
        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
    </button>
    <button id="btn_add" type="button" class="btn btn-warning">
        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
    </button>
    <button id="btn_load" type="button" class="btn btn-info">
        <span class="glyphicon glyphicon-log-out" aria-hidden="true"></span>导出
    </button>
</div>
　　
<!--添加-->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header alert alert-success">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">×</span></button>
                <h4 class="modal-title" id="myModalLabel">新增</h4>
            </div>
            <form id="MyForm">
                <div class="modal-body">
                    <input type="text" name="id" hidden="hidden" id="idupdate">
                    <div class="form-group">
                        <label>pid</label>
                        <input type="text" name="pid" class="form-control" placeholder="pid" id="pidupdate" >
                    </div>
                    <div class="form-group">
                        <label>pname</label>
                        <input type="text" name="pname" class="form-control"   id="pnameupdate" placeholder="pname" >
                    </div>
                    <div class="form-group ">
                        <label>scoredate</label>
                        <input type="date" name="scoredate" class="form-control" id="scoredateupdate" placeholder="scoredate">
                    </div>
                    <div class="form-group">
                        <label>testscoreId</label>
                        <input type="text" name="testscoreId" class="form-control" id="testscoreIdupdate" placeholder="testscoreId">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"><span
                            class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭
                    </button>
                    <button type="button" id="btn_submit" class="btn btn-primary"><span
                            class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>保存
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>

</body>
</html>
