<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1>用户注册</h1>
	<hr>
	<form action="request.jsp" name="regform" methon="post">
	  <table align="center">
		<tr>
		  <td>用户名</td>
		  <td><input type=""text" name="username"/></td>
		</tr>
		<tr>
		  <td>爱好</td>
		  <td>
		    <input type="checkbox" name="favority" value="read">读书
		    <input type="checkbox" name="favority" value="music">音乐
		    <input type="checkbox" name="favority" value="movie">电影
		    <input type="checkbox" name="favority" value="internet">上网
		   </td>
		</tr>	
		<tr>
		  <td colspan="2"><input type="submit" value="提交" /></td>
		</tr>
	  </table>
	</form>
	<br>
	<br>
	<a href="request.jsp?username=李四">测试URL参数</a>

</body>
</html>