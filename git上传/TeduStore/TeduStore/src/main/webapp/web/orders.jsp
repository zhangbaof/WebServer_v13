<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>我的订单 - 学子商城</title>
    <link href="../css/orders.css" rel="stylesheet"/>
    <link href="../css/header.css" rel="stylesheet"/>
    <link href="../css/footer.css" rel="stylesheet"/>
    <link href="../css/common.css" rel="stylesheet"/>
</head>
<body>
<!-- 页面顶部-->
<c:import url="header.jsp"></c:import>
<!-- nav主导航-->
<nav id="nav">
    <ul>
        <li><a href="${pageContext.request.contextPath}/main/index.do" class="acti">首页</a></li>
        <li><a href="index.html#computer" >电脑办公</a></li>
        <li><a href="index.html#stationery" >办公文具</a></li>
    </ul>
</nav>
<!-- 我的订单导航栏-->
<div id="nav_order">
    <ul>
        <li><a href="${pageContext.request.contextPath}/main/index.do">首页</a><span>&gt;</span>订单管理</li>
    </ul>
</div>
    <!--我的订单内容区域 #container-->
    <div id="container" class="clearfix">
        <!-- 左边栏-->
        <c:import url="left_side_menu.jsp"></c:import>
         <!-- 右边栏-->
        <div class="rightsidebar_box rt">
            <!-- 商品信息标题-->
            <table id="order_list_title"  cellpadding="0" cellspacing="0" >
                <tr>
                    <th width="345">商品</th>
                    <th width="82">单价（元）</th>
                    <th width="50">数量</th>
                    <th width="82">售后</th>
                    <th width="100">实付款（元）</th>
                    <th width="90">交易状态</th>
                    <th width="92">操作</th>
                </tr>
            </table>
            <!-- 订单列表项 -->
            
            
            
            
            
    <c:forEach items="${list}" var="orderItemList">
            
       <c:forEach items="${orderItemList}" var="orderItem">
            <div id="orderItem">
              <p class="orderItem_title">
                 <span id="order_id">
                     &nbsp;&nbsp;订单编号:<a href="#">12345678910</a>
                 </span>
                  &nbsp;&nbsp;成交时间：2016-01-04 18:00&nbsp;&nbsp;
                 <span>
                     <a href="#" class="servie">
                        联系客服<img src="../images/myOrder/kefuf.gif"/>
                      </a>
                 </span>
              </p>
            </div>
              <div id="orderItem_detail">
                  <ul>
                      <li class="product">
                          <b><a href="#"><img class="cart_goods_image" src="${pageContext.request.contextPath}${orderItem.goodsImage}" /></a></b>
                          <b class="product_name lf" >
                              <a href="">${orderItem.goodsTitle }</a>
                              <br/>
                          </b>
                          <b class="product_color ">
                              颜色：深空灰
                          </b>
                      </li>
                      <li class="unit_price">
                          专属价
                          <br/>
                          ￥${orderItem.goodsPrice }
                      </li>
                      <li class="count">
                          ${orderItem.num }件
                      </li>
                      <li class="sale_support">
                          退款/退货
                          <br/>
                          我要维权
                      </li>
                      <li class=" payments_received">
                          ￥${orderItem.goodsPrice }.00
                      </li>
                      <li class="trading_status">
                          <img src="../images/myOrder/car.png" alt=""/>已发货
                          <br/>
                          <a href="orderInfo.html">订单详情</a>
                          <br/>
                          <a href="#" class="view_logistics">查看物流</a>
                      </li>
                      <li class="operate">
                          <a href="#">确认收货</a>
                      </li>
                  </ul>
              </div>
</c:forEach>
  </c:forEach>







            <!-- <div id="orderItem">
                <p class="orderItem_title">
                 <span id="order_id">
                     &nbsp;&nbsp;订单编号:<a href="#">12345678910</a>
                 </span>
                    &nbsp;&nbsp;成交时间：2016-01-04 18:00&nbsp;&nbsp;
                 <span>
                     <a href="#" class="servie">
                         联系客服<img src="../images/myOrder/kefuf.gif"/>
                     </a>
                 </span>
                </p>
            </div>
            <div id="orderItem_detail">
                <ul>
                    <li class="product">
                        <b><a href="#"><img src="../images/myOrder/product_img1.png" /></a></b>
                        <b class="product_name lf" >
                            <a href="">联想(Lenovo)YoGA5 PRO 标配版电脑 (I5-7200U 8G 512G SSD IPS)</a>
                            <br/>
                        </b>
                        <b class="product_color ">
                            颜色：深空灰
                        </b>
                    </li>
                    <li class="unit_price">
                        专属价
                        <br/>
                        ￥8800
                    </li>
                    <li class="count">
                        1件
                    </li>
                    <li class="sale_support">
                        退款/退货
                        <br/>
                        我要维权
                    </li>
                    <li class=" payments_received">
                        ￥1222.00
                    </li>
                    <li class="trading_status">
                        <img src="../images/myOrder/car.png" alt=""/>已发货
                        <br/>
                        <a href="orderInfo.html">订单详情</a>
                        <br/>
                        <a href="#" class="view_logistics">查看物流</a>
                    </li>
                    <li class="operate">
                        <a href="#">确认收货</a>
                    </li>
                </ul>
            </div>

            <div id="orderItem">
                <p class="orderItem_title">
                 <span id="order_id">
                     &nbsp;&nbsp;订单编号:<a href="#">12345678910</a>
                 </span>
                    &nbsp;&nbsp;成交时间：2016-01-04 18:00&nbsp;&nbsp;
                 <span>
                     <a href="#" class="servie">
                         联系客服<img src="../images/myOrder/kefuf.gif"/>
                     </a>
                 </span>
                </p>
            </div>
            <div id="orderItem_detail">
                <ul>
                    <li class="product">
                        <b><a href="#"><img src="../images/myOrder/product_img1.png" /></a></b>
                        <b class="product_name lf" >
                            <a href="">联想(Lenovo)YoGA5 PRO 标配版电脑 (I5-7200U 8G 512G SSD IPS)</a>
                            <br/>
                        </b>
                        <b class="product_color ">
                            颜色：深空灰
                        </b>
                    </li>
                    <li class="unit_price">
                        专属价
                        <br/>
                        ￥8800
                    </li>
                    <li class="count">
                        1件
                    </li>
                    <li class="sale_support">
                        退款/退货
                        <br/>
                        我要维权
                    </li>
                    <li class=" payments_received">
                        ￥1222.00
                    </li>
                    <li class="trading_status">
                        <img src="../images/myOrder/car.png" alt=""/>已发货
                        <br/>
                        <a href="orderInfo.html">订单详情</a>
                        <br/>
                        <a href="#" class="view_logistics">查看物流</a>
                    </li>
                    <li class="operate">
                        <a href="#">确认收货</a>
                    </li>
                </ul>
            </div> -->


















<!--分页器-->
            <div class="tcdPageCode"></div>

        </div>
    </div>

        <!--<iframe src="order_status.html" width="1000" height=500""></iframe>-->
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
<script type="text/javascript" src="../js/jquery-3.1.1.min.js"></script>
<script src="../js/index.js"></script>
<script src="../js/jquery.page.js"></script>
<script type="text/javascript" src="../js/orders.js"></script>
</html>