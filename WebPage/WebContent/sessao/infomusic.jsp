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

	<form action="UpLoadMusic" method="post">
		<p>Title: <input name="title" type="text" value="${music.title}"></p>
		<p>Artist: <input name="artist" type="text" value="${music.artist}"></p>
		<p>Album: <input name="album" type="text" value="${music.album}"></p>
		<input type="file" name="file" size="50" />
		<input type="submit" value="Upload">
	</form>
</body>
</html>