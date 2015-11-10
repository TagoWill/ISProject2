<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Info Music</title>
</head>
<body>
	<form action="LogOut">
		<input type="submit" value="LogOut">
	</form>
	<p></p>

	<form action=EditMusic method="post">
		<p>Title: <input name="title" type="text" value="${title}"></p>
		<p>Artist: <input name="artist" type="text" value="${artist}"></p>
		<p>Album: <input name="album" type="text" value="${album}"></p>
		<p>Year: <input name="year" type="text" value="${year}"></p>
		<p>Path: ${path}"</p>
		<input type="hidden" name="getid" value="${music_id}" />
		<input type="submit" value="Save">
	</form>
	${error}
</body>
</html>