<%@ page language="java" 
	contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" 
		content="text/html; charset=utf-8">
    <title>我的订单 - 达内学子商城</title>
    <link href="../css/orders.css" rel="stylesheet"/>
    <link href="../css/header.css" rel="stylesheet"/>
    <link href="../css/footer.css" rel="stylesheet"/>
    <link href="../css/personage.css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="../css/icon/css/bootstrap.min.css">
	<link href="../css/icon/css/cropper.min.css" rel="stylesheet">
	<link href="../css/icon/css/sitelogo.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="../css/icon/css/font-awesome.min.css">
</head>
<body>
<!-- 页面顶部-->
<c:import url="/web/header.jsp"></c:import>

<!-- nav主导航-->
<nav id="nav">
    <ul>
        <li><a href="index.html" class="acti">首页</a></li>
        <li><a href="index.html#computer" >电脑办公</a></li>
        <li><a href="index.html#stationery" >办公文具</a></li>
    </ul>
</nav>
<!-- 我的订单导航栏-->
<div id="nav_order">
    <ul>
        <li><a href="${pageContext.request.contextPath}/main/index.do">首页</a><span>&gt;</span>个人中心</li>
    </ul>
</div>
<!--我的订单内容区域 #container-->
<div id="container" class="clearfix">
    <!-- 左边栏-->
    <c:import url="left_side_menu.jsp"></c:import>
    <!-- 右边栏-->
    <!--个人信息头部-->
    <div class="rightsidebar_box rt">
        <div class="rs_header">
            <span class="rs_header_active">
            	<a href="###">我的信息</a>
            </span>
            <span>
            	<a href="user_password.do">安全管理</a>
            </span>
        </div>
        <!--个人信息具体内容 -->
        <div class="rs_content">
            <!--用户名-->
            <div class="rs_content_username">
                <span class="same">用户名：</span>
                <span class="same rs_username">${user.username }</span>
                <input id="username"  
                	class="ed_username" value="" style="display: none;"/>
                <span class="change_username same_click">更改用户名</span>
            </div>
            <!--绑定电话-->
            <div class="rs_content_tel">
                <span class="same">绑定电话：</span>
                <input id="phone" type="text" value="${user.phone }"/>
            </div>
            <!--绑定邮箱-->
            <div class="rs_content_mail">
                <span class="same">绑定邮箱：</span>
                <input id="email"
                	 class="ed_email" value="" style="display: none;"/>
                <span class="rs_mail">${user.email }</span>
                <span class="same_click change_mail">更改邮箱</span>
            </div>
            <!--保存按钮-->
            <div>
                <a href="###"
                	onclick="doSave()" class="save">保存更改</a>
            </div>
        </div>
    </div>
</div>
<!-- 头像插件 -->
<div class="modal fade" id="avatar-modal" aria-hidden="true" aria-labelledby="avatar-modal-label" role="dialog" tabindex="-1"> 
    <div class="modal-dialog modal-lg"> 
        <div class="modal-content"> 
            <!--<form class="avatar-form" action="upload-logo.php" enctype="multipart/form-data" method="post">--> 
            <form class="avatar-form"> 
                <div class="modal-header"> 
                    <button class="close" data-dismiss="modal" type="button">×</button> 
                    <h4 class="modal-title" id="avatar-modal-label">上传图片</h4> 
                </div> 
                <div class="modal-body"> 
                    <div class="avatar-body"> 
                        <div class="avatar-upload"> 
                            <input class="avatar-src" name="avatar_src" type="hidden"> 
                            <input class="avatar-data" name="avatar_data" type="hidden"> 
                            <label for="avatarInput" style="line-height: 35px;">图片上传</label> 
                            <button class="btn btn-info"  type="button" style="height: 35px;" onClick="$('input[id=avatarInput]').click();">请选择图片</button> 
                            <span id="avatar-name"></span> 
                            <input class="avatar-input hide" id="avatarInput" name="avatar_file" type="file"></div> 
                        <div class="row"> 
                            <div class="col-md-9"> 
                                <div class="avatar-wrapper"></div> 
                            </div> 
                            <div class="col-md-3"> 
                                <div class="avatar-preview preview-lg" id="imageHead"></div> 
                                <!--<div class="avatar-preview preview-md"></div> 
                        <div class="avatar-preview preview-sm"></div>--> 
                            </div> 
                        </div> 
                        <div class="row avatar-btns"> 
                            <div class="col-md-4"> 
                                <div class="btn-group"> 
                                    <button class="btn btn-info fa fa-undo" data-method="rotate" data-option="-90" type="button" title="Rotate -90 degrees"> 向左旋转</button> 
                                </div> 
                                <div class="btn-group"> 
                                    <button class="btn  btn-info fa fa-repeat" data-method="rotate" data-option="90" type="button" title="Rotate 90 degrees"> 向右旋转</button> 
                                </div> 
                            </div> 
                            <div class="col-md-5" style="text-align: right;">                                 
                                <button class="btn btn-info fa fa-arrows" data-method="setDragMode" data-option="move" type="button" title="移动"> 
                                <span class="docs-tooltip" data-toggle="tooltip" title="" data-original-title="$().cropper("setDragMode", "move")"> 
                                </span> 
                              </button> 
                              <button type="button" class="btn btn-info fa fa-search-plus" data-method="zoom" data-option="0.1" title="放大图片"> 
                                <span class="docs-tooltip" data-toggle="tooltip" title="" data-original-title="$().cropper("zoom", 0.1)"> 
                                  <!--<span class="fa fa-search-plus"></span>--> 
                                </span> 
                              </button> 
                              <button type="button" class="btn btn-info fa fa-search-minus" data-method="zoom" data-option="-0.1" title="缩小图片"> 
                                <span class="docs-tooltip" data-toggle="tooltip" title="" data-original-title="$().cropper("zoom", -0.1)"> 
                                  <!--<span class="fa fa-search-minus"></span>--> 
                                </span> 
                              </button> 
                              <button type="button" class="btn btn-info fa fa-refresh" data-method="reset" title="重置图片"> 
                                    <span class="docs-tooltip" data-toggle="tooltip" title="" data-original-title="$().cropper("reset")" aria-describedby="tooltip866214"> 
                               </button> 
                            </div> 
                            <div class="col-md-3"> 
                                <button id="button_save" class="btn btn-info btn-block avatar-save fa fa-save" type="button" data-dismiss="modal"> 保存修改</button> 
                            </div> 
                        </div> 
                    </div> 
                </div> 
            </form> 
        </div> 
    </div> 
</div> 
<!-- 品质保障，私人定制等-->
<div id="foot_box">
    <div class="icon1 lf">
        <img src="../images/footer/icon1.png" alt=""/>

        <h3>品质保障</h3>
    </div>
    <div class="icon2 lf">
        <img src="../images/footer/icon2.png" alt=""/>

        <h3>私人定制</h3>
    </div>
    <div class="icon3 lf">
        <img src="../images/footer/icon3.png" alt=""/>

        <h3>学员特供</h3>
    </div>
    <div class="icon4 lf">
        <img src="../images/footer/icon4.png" alt=""/>

        <h3>专属特权</h3>
    </div>
</div>
<!-- 页面底部-->
<div class="foot_bj">
    <div id="foot">
        <div class="lf">
             <p class="footer1"><img src="../images/footer/logo.png" alt="" class=" footLogo"/></p>
             <p class="footer2"><img src="../images/footer/footerFont.png" alt=""/></p>
        </div>
        <div class="foot_left lf">
            <ul>
                <li><a href="#"><h3>买家帮助</h3></a></li>
                <li><a href="#">新手指南</a></li>
                <li><a href="#">服务保障</a></li>
                <li><a href="#">常见问题</a></li>
            </ul>
            <ul>
                <li><a href="#"><h3>商家帮助</h3></a></li>
                <li><a href="#">商家入驻</a></li>
                <li><a href="#">商家后台</a></li>
            </ul>
            <ul>
                <li><a href="#"><h3>关于我们</h3></a></li>
                <li><a href="#">关于达内</a></li>
                <li><a href="#">联系我们</a></li>
                <li>
                    <img src="../images/footer/wechat.png" alt=""/>
                    <img src="../images/footer/sinablog.png" alt=""/>
                </li>
            </ul>
        </div>
        <div class="service">
            <p>学子商城客户端</p>
            <img src="../images/footer/ios.png" class="lf">
            <img src="../images/footer/android.png" alt="" class="lf"/>
        </div>
        <div class="download">
            <img src="../images/footer/erweima.png">
        </div>
		<!-- 页面底部-备案号 #footer -->
        <div class="record">
            &copy;2017 达内集团有限公司 版权所有 京ICP证xxxxxxxxxxx
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="../js/jquery.min.js"></script>
<script src="../js/index.js"></script>
<script src="../js/jquery.page.js"></script>
<script type="text/javascript" src="../js/orders.js"></script>
<script type="text/javascript" src="../js/personal.js"></script>
<script src="../js/icon/bootstrap.min.js"></script>
<script src="../js/icon/cropper.js"></script>
<script src="../js/icon/sitelogo.js"></script>
<script src="../js/icon/html2canvas.min.js" type="text/javascript" charset="utf-8"></script> 
<script type="text/javascript" language="javascript">
function doSave() {
	var username = $("#username").val();
	var phone = $("#phone").val();
	var email = $("#email").val();
	
	// *** 此处忽略对输入框中的数据的验证 ***
	
	$.ajax({
		"url":"update_user_info.do",
		"data": {
				"username":username,
				"phone":phone,
				"email":email
			},
		"type":"POST",
		"dataType":"json",
		"success":function(obj) {
			if (obj.state == 1) {
				alert("保存成功！");
			} else {
				alert("发生未知错误！请联系系统管理员！");
			}
		}
	});
}

$(function(){
	showMenuItem(".count_managment");
});

</script>
</html>