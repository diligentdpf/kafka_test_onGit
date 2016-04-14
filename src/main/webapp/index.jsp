<%@ page language="java" contentType="text/html; charset=UTF-8"  
    pageEncoding="UTF-8"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">  
<html>  
<head>  

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
<title>Insert title here</title>  
<script type="text/javascript" language="javascript" src="JS/jquery.js" />  
<script type="text/javascript">  
    i = 1;  
    j = 1;  
    $(document).ready(function(){
          
        $('#btn_add2').click(function(){  
            document.getElementById("newUpload2_1").innerHTML+=
            	'<div id="div_'+j+'"><input  name="file_'+j+'" type="file"  /><input type="button" value="删除"  onclick="del_2('+j+')"/></div>';  
              j = j + 1;  
        });  
    });  
    
    function addInnerHtml(){
    	document.getElementById("newUpload2_1").innerHTML+=
        	'<div id="div_'+j+'"><input  name="file_'+j+'" type="file"  /><input type="button" value="删除"  onclick="del_2('+j+')"/></div>';  
          j = j + 1; 
    }
      
    function del_2(o){  
         document.getElementById("newUpload2").removeChild(document.getElementById("div_"+o));  
    }  
  
</script>  
</head>  
<body>  
    <hr align="left" width="60%" color="#FF0000" size="3">  
    <br>  
    <br>  
     <h1>springMVC包装类上传文件</h1>   
    <form name="userForm2" action="upload" enctype="multipart/form-data" method="post">  
        <div id="newUpload2">  
            <input type="file" name="file">  
        </div>
         <div id="newUpload2_1">  
            <input type="file" name="file_1">  
        </div>  
        <input type="hidden" name="userId" value="1220151026103643093" >
        <input type="hidden" name="type" value="1">
        <input type="button" id="btn_add2" value="增加一行" >  
        <input type="submit" value="上传" >      
    </form>   
    <h2>上传多个文件 实例</h2>  
    <form action="upload" method="post"  
        enctype="multipart/form-data">
        <p>  
            选择文件:<input type="file" name="files">  
        <p>  
            选择文件:<input type="file" name="files1">  
        <p>  
            选择文件:<input type="file" name="files2">  
        <p>  
            <input type="submit" value="提交">  
    </form> 
</body>  
</html>  