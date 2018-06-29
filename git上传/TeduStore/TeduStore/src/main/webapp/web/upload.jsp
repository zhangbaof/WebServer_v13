<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>


<head>
<meta charset="UTF-8">
<title>文件上载</title>
<style>
  #selected img{
    display:block;
    width:160px;
    /* height:160px; */
    padding:5px;
    border:1px solid #ddd;
    margin:5px;
    float:left;
  }
</style>
</head>
<body>
    <h1>文件上载</h1>
    <form action="upload.do"
          enctype="multipart/form-data"
          method=POST>
     姓名：<input type="text" name="username">
     照片：<input name="userfile1" type="file">
     <input type="submit" value="上载"/>
     </form>
     
     
     
     <h2>ajax上载</h2>
     <div>
          <label for="images">选择图片</label>
         <!-- 指定multiple="multiple"属性，可以选择多个文件 -->
          <input type="file" id="images" multiple="multiple"/><br>
          <input id="ajax_upload" type="button" value="ajax上载"/>
     </div>
     <h3>选择了：</h3>
     <div id="selected">
     
     </div>
     
     
     <h2>Jquery上载</h2>
     <div>
      <label>选择图片</label>
        <input type="file" id="photos" multiple="multiple"/>
        <input type="button" id="jquery_upload" value="jquery上载" />
     </div>
     <h3>选择了：</h3>
     <div id="selected_photos"></div>
     
     
     
</body>

<script type="text/script" src="../js/jquery-3.1.1.min.js"></script>

<script>
var photos = $("#photos");
var selectedPhotos = $("#selected_photos");
var ajaxBtn = $("#jquery_upload");
photos.change(function(){
	var files = this.files;
	selectedPhotos.empty();
	for(var i = 0;i<files.length;i++){
		var f = files[i];
		var url = window.URL.createObjectURL(f);
		var img = $("<img src='"+url+"'>");
		selectedPhotos.append(img);
	}
});
ajaxBtn.click(function(){
	var url = "/uploadImages.do";
	$.ajax({
		url:url,
		type:"POST",
		data:data,
		dataType:"json",
		processData:false,//不要处理data数据
		contentType:false,//不要jquery设定ContentType
		success:function(){
			ajaxBtn.val("jquery上载");
			selectedPhotos.html(json.message);
		}
		
	});
});


</script>



<script>
	var images = document.getElementById("images");
	var div = document.getElementById("selected");
	images.onchange = function(){
		var files = this.files;
		selected.innerHTML = "";
		for(var i = 0;i<files.length;i++){
			var f = files[i];
			var img = new Image();
			var url = window.URL.createObjectURL(f);
			img.src = url;
			div.appendChild(img);
		}
	}
	
	var btn = document.getElementById("ajax_upload");
	btn.onclick = function(){
		var files = images.files;
		var frm = new FormData();
		console.log(frm);
		for(var i = 0;i<files.length;i++){
			var f = files[i];
			console.log(f);
			frm.append("images",f,f.name);
			console.log(frm);
		}
		//发起Ajax请求
		btn.value = "上载中......";
		var xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function(){
			console.log(xhr.readyState);
			
			if(xhr.readyState==4&&xhr.status==200){
				var json = JSON.parse(xhr.responseText);
				console.log(json);
				selected.innerHTML = json.message;
				btn.value="ajax上载"
			}
		};
		console.log(2);
		xhr.open("POST","uploadImages.do");
		xhr.send(frm);
	};
	
	
</script>





</html>