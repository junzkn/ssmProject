<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<form action="/debug/upload" method="post" enctype="multipart/form-data">
姓名<input name="name" value="" >
密码<input name="password" value="">
文件<input name="file" type="file">
提交<input type="submit" value="进入"/>
</form>
</body>
</html>