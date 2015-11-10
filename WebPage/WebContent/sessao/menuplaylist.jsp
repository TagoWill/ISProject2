<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Menu Playlist</title>
</head>
<body>
	<p></p>
	<form action="LogOut">
		<input type="submit" value="LogOut">
	</form>
	<p></p>
	My Playlist:
	<p />
	<form action="OrderASC" method="post">
		<input type="submit" value="Asc">
	</form>
	<form action="OrderDESC" method="post">
		<input type="submit" value="Desc">
	</form>
	<table>
		<c:forEach items="${lists}" var="list">
			<tr>
				<td>${list.getPlaylistName()}</td>
				<td><form action="DeletePlaylist" method="post">
						<input type="hidden" name="getid" value="${list.getId()}" /> <input
							type="submit" name="teste" value="Delete">
					</form></td>
			<!-- <td><c:out value="${list.getPlaylistName()}" /></td>
				<td><href="${list.getPlaylistName()} value="Delete" /></td>  -->
				</tr>
		</c:forEach>
	</table>
	<form action="CreatePlaylist" method="post">
		<input type="text" name="listname"> <input type="submit"
			value="Create new Playlist">
	</form>
	<form action="GoToMenuPrincipal">
		<input type="submit" value="Back">
	</form>

	${error}
</body>
</html>