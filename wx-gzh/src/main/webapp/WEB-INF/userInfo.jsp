<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>用户信息</title>
</head>
<body>
<img src="${user.picPath}" style="text-align: center;width: 50px;height: 50px"><br/>
姓名：${user.name}<br/>
账号：${user.userName}<br/>
密码：${user.passWord}<br/>
地址：${user.address}<br/>
</body>
</html>