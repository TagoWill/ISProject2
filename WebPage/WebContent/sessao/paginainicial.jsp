<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<p>Bem-vindo ${nome} de user ${user}</p>

	<form action="">
		<input type="submit" value="PlayLists">
	</form>
	<form action="">
		<input type="submit" value="Musics">
	</form>
	<form action="PrefilServlet" method="post">
		<input type="submit" value="Prefil">
	</form>
	<form action="LogOutServlet" method="post">
		<input type="submit" value="LogOut">
	</form>
</body>
</html>