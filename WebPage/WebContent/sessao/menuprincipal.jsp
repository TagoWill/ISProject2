<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Menu Principal</title>
</head>
<body>

	<p>Bem-vindo ${nome} de user ${user}</p>

	<form action="GoToPlaylist" >
		<input type="submit" value="My PlayLists">
	</form>
	<form action="GoToMusic" >
		<input type="submit" value="My Musics">
	</form>
	<form action="GoToSearch" >
		<input type="submit" value="Search">
	</form>
	<form action="GoToProfile" >
		<input type="submit" value="Prefil">
	</form>
	<form action="LogOut" >
		<input type="submit" value="LogOut">
	</form>
</body>
</html>