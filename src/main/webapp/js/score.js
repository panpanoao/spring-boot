 $(function(){
        initTable();
        $('#MyForm').bootstrapValidator({
            message: 'This value is not valid',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                pid: {
                    validators: {
                        notEmpty: {
                            message: 'pid不能为空'
                        }
                    }
                },
                pname: {
                    validators: {
                        notEmpty: {
                            message: 'pname不能为空'
                        }
                    }
                },
                scoredate: {
                    validators: {
                        notEmpty: {
                            message: 'scoredate不能为空'
                        }
                    }
                },
                testscoreId: {
                    validators: {
                        notEmpty: {
                            message: 'testscoreId不能为空'
                        }
                    }
                }
            }

        });

        $._messengerDefaults = {
            extraClasses: 'messenger-fixed messenger-theme-future messenger-on-top'
        };


        (function ($) {
            window.Ewin = function () {
                var html = '<div id="[Id]" class="modal fade" role="dialog" aria-labelledby="modalLabel">' +
                    '<div class="modal-dialog modal-sm">' +
                    '<div class="modal-content">' +
                    '<div class="modal-header">' +
                    '<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>' +
                    '<h4 class="modal-title" id="modalLabel">[Title]</h4>' +
                    '</div>' +
                    '<div class="modal-body">' +
                    '<p>[Message]</p>' +
                    '</div>' +
                    '<div class="modal-footer">' +
                    '<button type="button" class="btn btn-default cancel" data-dismiss="modal">[BtnCancel]</button>' +
                    '<button type="button" class="btn btn-primary ok" data-dismiss="modal">[BtnOk]</button>' +
                    '</div>' +
                    '</div>' +
                    '</div>' +
                    '</div>';


                var dialogdHtml = '<div id="[Id]" class="modal fade" role="dialog" aria-labelledby="modalLabel">' +
                    '<div class="modal-dialog">' +
                    '<div class="modal-content">' +
                    '<div class="modal-header">' +
                    '<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>' +
                    '<h4 class="modal-title" id="modalLabel">[Title]</h4>' +
                    '</div>' +
                    '<div class="modal-body">' +
                    '</div>' +
                    '</div>' +
                    '</div>' +
                    '</div>';
                var reg = new RegExp("\\[([^\\[\\]]*?)\\]", 'igm');
                var generateId = function () {
                    var date = new Date();
                    return 'mdl' + date.valueOf();
                }
                var init = function (options) {
                    options = $.extend({}, {
                        title: "操作提示",
                        message: "提示内容",
                        btnok: "确定",
                        btncl: "取消",
                        width: 200,
                        auto: false
                    }, options || {});
                    var modalId = generateId();
                    var content = html.replace(reg, function (node, key) {
                        return {
                            Id: modalId,
                            Title: options.title,
                            Message: options.message,
                            BtnOk: options.btnok,
                            BtnCancel: options.btncl
                        }[key];
                    });
                    $('body').append(content);
                    $('#' + modalId).modal({
                        width: options.width,
                        backdrop: 'static'
                    });
                    $('#' + modalId).on('hide.bs.modal', function (e) {
                        $('body').find('#' + modalId).remove();
                    });
                    return modalId;
                }

                return {
                    alert: function (options) {
                        if (typeof options == 'string') {
                            options = {
                                message: options
                            };
                        }
                        var id = init(options);
                        var modal = $('#' + id);
                        modal.find('.ok').removeClass('btn-success').addClass('btn-primary');
                        modal.find('.cancel').hide();

                        return {
                            id: id,
                            on: function (callback) {
                                if (callback && callback instanceof Function) {
                                    modal.find('.ok').click(function () { callback(true); });
                                }
                            },
                            hide: function (callback) {
                                if (callback && callback instanceof Function) {
                                    modal.on('hide.bs.modal', function (e) {
                                        callback(e);
                                    });
                                }
                            }
                        };
                    },
                    confirm: function (options) {
                        var id = init(options);
                        var modal = $('#' + id);
                        modal.find('.ok').removeClass('btn-primary').addClass('btn-success');
                        modal.find('.cancel').show();
                        return {
                            id: id,
                            on: function (callback) {
                                if (callback && callback instanceof Function) {
                                    modal.find('.ok').click(function () { callback(true); });
                                    modal.find('.cancel').click(function () { callback(false); });
                                }
                            },
                            hide: function (callback) {
                                if (callback && callback instanceof Function) {
                                    modal.on('hide.bs.modal', function (e) {
                                        callback(e);
                                    });
                                }
                            }
                        };
                    },
                    dialog: function (options) {
                        options = $.extend({}, {
                            title: 'title',
                            url: '',
                            width: 800,
                            height: 550,
                            onReady: function () { },
                            onShown: function (e) { }
                        }, options || {});
                        var modalId = generateId();

                        var content = dialogdHtml.replace(reg, function (node, key) {
                            return {
                                Id: modalId,
                                Title: options.title
                            }[key];
                        });
                        $('body').append(content);
                        var target = $('#' + modalId);
                        target.find('.modal-body').load(options.url);
                        if (options.onReady())
                            options.onReady.call(target);
                        target.modal();
                        target.on('shown.bs.modal', function (e) {
                            if (options.onReady(e))
                                options.onReady.call(target, e);
                        });
                        target.on('hide.bs.modal', function (e) {
                            $('body').find(target).remove();
                        });
                    }
                }
            }();
        })(jQuery);

    });
function initTable() {
    $("#bootstrapTable").bootstrapTable({
        height: getHeight(),
        method: 'get',
        striped: true,
        pagination: true,
        url: 'scorelist',
        dataType: 'json',
        toolbar: '#toolbar',
        cache: false,
        sidePagination: 'server',
        limit:10,
        offset:0,
        pageList: [5,10,15,20,25],
        pageSize:5,
        pageNumber:1,
        sortable: true,
        sortOrder: "desc",
        showRefresh: true,
        showColumns: true,
        showExport: true,
        search: true,
        strictSearch:true,
        showToggle:true,
        queryParams: function queryParams(params){
            var param = {
                pageNumber: params.offset,
                pageSize: params.limit,
                pid: $('#pid').val(),
                pname: $('#pname').val()
            };
            return param;
        },
      /*  //加载成功时执行
        onLoadSuccess: function(data){
            console.log("加载成功");
        },
        //加载失败时执行
        onLoadError: function(status){
            console.log("加载数据失败"+status);
        },*/

        columns: [
            {field:"选择",
                checkbox: true,
                title:"选择",
                align:"center",
                valign:"middle"
            },{
                field: 'id',
                title: 'id',
                align: 'center',
                valign: 'middle',
                sortable: true,
                width:  '180px'
            }/*,{
                field: 'yyyy',
                title: 'yyyy',
                align: 'center',
                valign: 'middle',
                sortable: true,
                width:  '180px'
            }*/,　　　
            {
                field: 'pid',
                title: 'pid',
                align: 'center',
                valign: 'middle',
                sortable: false,
                width:  '180px',
/*                editable: {
                    type: 'text',
                    title: '<@spring.message "fnd.lang_code"/>',
                    validate: function (v) {
                        if (!v) return '语言不能为空';

                    }
                }*/
            },{
                field: 'pname',
                title: 'pname',
                align: 'center',
                valign: 'middle',
                sortable: false
            },{
                field: 'testscoreId',
                title: 'testscoreId',
                align: 'center',
                valign: 'middle',
                sortable: false
            },{
                field: 'scoredate',
                title: 'scoredate',
                align: 'center',
                valign: 'middle',
                sortable: false,
                /*       formatter: function (value, row, index) {
                           return changeDateFormat(value)
                       }*/
            },]
    });
    // 查询按钮事件
    $('#search_btn').click(function() {
        $('#bootstrapTable').bootstrapTable('refresh', {
            url :"scorelist"
        });
    })
   //删除
    $('#btn_delete').click(function () {
        var a=  $('#bootstrapTable').bootstrapTable('getSelections');
        var  model=[];
        for (var i = 0; i < a.length; i++) {
            model.push(a[i].id);
        }
        if(a.length>=1){
            Ewin.confirm({ message: "确认要删除选择的数据吗？" }).on(function (e) {
                if (e) {
                    $.ajax({
                        type: "get",//请求方式
                        data: {list: model},//参数
                        dataType: 'json', //接受数据格式
                        url: "/deletes", //地址
                        traditional: true,//接收数组类型
                        success: function (data) {
                            $.globalMessenger().post({
                                message: "操作成功",//提示信息
                                type: 'success',//消息类型。error、info、success
                                hideAfter: 2,//多长时间消失
                                showCloseButton: true,//是否显示关闭按钮
                                hideOnNavigate: true //是否隐藏导航
                            });
                            $('#bootstrapTable').bootstrapTable('refresh');
                        },
                        error: function () {
                            $.globalMessenger().post({
                                message: "删除失败",//提示信息
                                type: 'error',//消息类型。error、info、success
                                hideAfter: 2,//多长时间消失
                                showCloseButton: true,//是否显示关闭按钮
                                hideOnNavigate: true //是否隐藏导航
                            });
                        }
                    });
                }else{
                    return;
                }
            });
        }else{
            $.globalMessenger().post({
                message: "请先选中一行记录",//提示信息
                type: 'info',//消息类型。error、info、success
                hideAfter: 2,//多长时间消失
                showCloseButton:true,//是否显示关闭按钮
                hideOnNavigate: true //是否隐藏导航
            });
        }
    })

    $('#btn_add').click(function () {
        $("#MyForm input").val(null);
        $('#myModal').modal();
    })

    $('#select').click(function () {
       /*$('#chaxun').show(500);*/
        $("#chaxun").slideToggle("slow");
    })

    $('#btn_load').click(function () {
          var pid=$("#pid").val();
          var pname=$("#pname").val();
        window.open('download/ScoreExcelFile?pid=' + pid
            + '&pname=' + pname);
    })


    $('#btn_edit').click(function () {

        var a=  $('#bootstrapTable').bootstrapTable('getSelections');
        if(a.length<=0){
            $.globalMessenger().post({
                message: "请先选中一行记录",//提示信息
                type: 'error',//消息类型。error、info、success
                hideAfter: 2,//多长时间消失
                showCloseButton:true,//是否显示关闭按钮
                hideOnNavigate: true //是否隐藏导航
            });
         }else if(a.length>1){
            $.globalMessenger().post({
                message: "只能选择一行记录",//提示信息
                type: 'error',//消息类型。error、info、success
                hideAfter: 2,//多长时间消失
                showCloseButton:true,//是否显示关闭按钮
                hideOnNavigate: true //是否隐藏导航
            });
        }else{
        $("#idupdate").val(a[0].id);
        $("#pidupdate").val(a[0].pid);
        $("#pnameupdate").val(a[0].pname);
        var date=a[0].scoredate.toString().substring(0,10);
        $("#scoredateupdate").val(date);
        $("#testscoreIdupdate").val(a[0].testscoreId);
        $('#myModal').modal();
        }
    })



    $('#btn_submit').click(function () {
        $("#MyForm").bootstrapValidator('validate');//提交验证
        if ($("#MyForm").data('bootstrapValidator').isValid()) {
            //获取验证结果，如果成功，执行下面代码
            var formData=$("#MyForm").serialize();
            $.ajax({
                type: "get",//请求方式
                processData:true,//序列化
                data:formData,//参数
                dataType: 'json', //接受数据格式
                url: "/add", //地址
                traditional: true,//接收数组类型
                success: function (data,msg) {
                    $.globalMessenger().post({
                        message:'success',//提示信息
                        type: 'success',//消息类型。error、info、success
                        hideAfter: 2,//多长时间消失
                        showCloseButton: true,//是否显示关闭按钮
                        hideOnNavigate: true //是否隐藏导航
                    });
                    $('#bootstrapTable').bootstrapTable('refresh', {
                        url :"scorelist"
                    });
                },
                error: function () {
                    $.globalMessenger().post({
                        message: "保存失败",//提示信息
                        type: 'error',//消息类型。error、info、success
                        hideAfter: 2,//多长时间消失
                        showCloseButton: true,//是否显示关闭按钮
                        hideOnNavigate: true //是否隐藏导航
                    });
                }
            });
            $("#MyForm input").val(null);//清空表单
            $("#MyForm").data('bootstrapValidator').resetForm();//清空表单验证
            $('#myModal').modal('hide');//隐藏
        }else{
            //验证失败
            $.globalMessenger().post({
                message: "信息填写不完整",//提示信息
                type: 'error',//消息类型。error、info、success
                hideAfter: 2,//多长时间消失
                showCloseButton:true,//是否显示关闭按钮
                hideOnNavigate: true //是否隐藏导航
            });
        }
    })
}
//获取table的高度
function getHeight() {
    return $(window).height() - 100;
}
function changeDateFormat(cellval) {
    var dateVal = cellval + "";
    if (cellval != null) {
        var date = new Date(parseInt(dateVal.replace("/Date(", "").replace(")/", ""), 10));
        var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
        var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
        var hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
        var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
        var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
        return date.getFullYear() + "-" + month + "-" + currentDate + " " + hours + ":" + minutes + ":" + seconds;
    }
}
