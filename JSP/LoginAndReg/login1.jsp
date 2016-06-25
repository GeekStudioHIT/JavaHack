<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" import="java.util.*" import="java.text.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1>用户登录</h1>
	<hr>
	
	<form action="index.jsp" name="loginform" method="post">
	<!-- 这是html注释的练习 -->
	<table align="center">
		<tr>
			<td>用户名</td>
			<td><input tyoe="text" name="username"></td>
		</tr>
		<tr>
			<td>密码</td>
			<td><input tyoe="text" name="password"></td>
		</tr>
		<tr>
			<td colspan="2"><input type="submit" value="登录"></td>
		</tr>
	</table>
	</form>
	
	
	<%
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		String s = sdf.format(new Date());
	%>
	今天是<%=s %>
	
	<table>
		<%
			out.println("<h2>静夜思<h2>");
			out.println("窗前明月光<br>");
			out.println("疑是地上霜<br>");
			out.println("举头望明月<br>");
			out.println("低头思故乡<br>");
		%>
		缓冲区大小<%=out.getBufferSize() %>byte<br>
		缓冲区剩余大小<%=out.getRemaining() %>byte<br>
		是否自动清除缓冲区<%=out.isAutoFlush() %><br>
		
	
	</table>

</body>
</html>