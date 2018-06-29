/**
 * Created by yy on 2016/12/15.
 */

$("#leftsidebar_box dt").css({"background-color":" #0AA1ED"});
$(function(){
		//隐藏菜单中所有的选项
    $("#leftsidebar_box dd").hide();
    //显示class=my_order下的所有的选项
   $("#leftsidebar_box .my_order dd").show();
    //为xxx配置了点击事件的响应
    //为各组选项的分类标题（4个）配置了点击事件的响应
    $("#leftsidebar_box dt").click(function(){
    	//为各组选项的分类标题设置背景颜色
        $("#leftsidebar_box dt").css({"background-color":"#0AA1ED"});
        //设置自身的背景颜色
        $(this).css({"background-color": "#0AA1ED"});
        //把每一项都移除class=menu_choice样式设置
        $(this).parent().find('dd').removeClass("menu_chioce");
        $("#leftsidebar_box dt img").attr("src","../images/myOrder/myOrder2.png");
        $(this).parent().find('img').attr("src","../images/myOrder/myOrder1.png");
        $(".menu_chioce").slideUp();
        $(this).parent().find('dd').slideToggle();
        $(this).parent().find('dd').addClass("menu_chioce");
        $(this).parent().siblings().children('dd').slideUp();
    });
})
//分页部分
$(".tcdPageCode").createPage({
    pageCount:6,
    current:1,
    backFn:function(p){

          }
});


