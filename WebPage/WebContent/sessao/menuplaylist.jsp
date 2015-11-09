<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Menu Playlist</title>
</head>
<body>
	<p></p>
	<form action="LogOut" method="post">
		<input type="submit" value="LogOut">
	</form>
	<p></p>
	My Playlist:
	<p />
	<td><form action="OrderASC" method="post">
		<input type="submit" value="Asc">
	</form></td>
	<td><form action="OrderDESC" method="post">
		<input type="submit" value="Desc">
	</form></td>
	<table>
		<c:forEach items="${lists}" var="list">
			<tr>
				<td><c:out value="${list.getPlaylistName()}" /></td>
			</tr>
		</c:forEach>
	</table>
	<form action="CreatePlaylist" method="post">
		<input type="text" name="listname"> <input type="submit"
			value="Create new Playlist">
	</form>
	<form action="BackToMenuServlet" method="post">
		<input type="submit" value="Back">
	</form>

	${error}
</body>
</html>