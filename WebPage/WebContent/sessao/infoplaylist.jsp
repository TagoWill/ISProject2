<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
		<p>
			<input type="text" name="playlist_name" value="${name}" required>
		</p>
		<input type="hidden" name="playlist_id" value="${playlist_id}">
		<input type="submit" value="Change Name">
	</form>
	<p></p>
	Musics of this playlist
	<p></p>
	<table>
		<c:forEach items="${lists}" var="list">
			<tr>
				<td>${list.getTitle()}</td>
				<td><form action="DetachForPlaylist" method="post">
						<input type="hidden" name="getid" value="${list.getId()}" />
						<input type="hidden" name="playlist_id" value="${playlist_id}"> <input
							type="submit" name="teste" value="Remove">
					</form></td>
				</tr>
		</c:forEach>
	</table>
	<form action="GoToPlaylist">
		<input type="submit" value="Back">
	</form>
	${error}
</body>
</html>