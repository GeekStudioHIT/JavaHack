<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" import="java.text.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<!-- request内置对象 -->
	<h1>用户提交信息</h1>
	<hr>
	<%
		request.setCharacterEncoding("utf-8");
	%>
	用户名:<%=request.getParameter("username") %><br>
	爱好：<%
			String[] favorities = request.getParameterValues("favority");
		    for (int i=0; i<favorities.length; i++)
		    {
		    	out.println(favorities[i] + "&nbsp;&nbsp;");
		    }
	
	%>
	

</body>
</html>