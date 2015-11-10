<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Playlist Information</title>
</head>
<body>
	<form action="LogOut">
		<input type="submit" value="LogOut">
	</form>
	<p></p>
	<form action="EditPlaylistName" method="post">
		<p>Playlist Name:</p>
		<p><input type="text" name="playlist_name" value="${name}"></p>
		<input type="hidden" name="playlist_id" value="${playlist_id}">
		<input type="submit" value="Change Name">
	</form>
	${error}
</body>
</html>