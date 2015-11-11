<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>My Music</title>
</head>
<body>
	<form action="LogOut">
		<input type="submit" value="LogOut">
	</form>
	<p></p>
	A Minha musica
	<p></p>
	<table>
		<c:forEach items="${lists}" var="list">
			<tr>
				<td><a href="GoToEditMusic?music_id=${list.getId()}">${list.getTitle()}</a></td>
				<td><form action="Detach" method="post">
						<input type="hidden" name="getid" value="${list.getId()}" /> <input
							type="submit" name="teste" value="Detach">
					</form></td>
					<td><form action="AddPlaylist" method="post">
					<select name="play">
					<c:forEach items="${listplaylist}" var="playlist">
						<option value="${playlist.getId()}">${playlist.getPlaylistName()}</option>
					</c:forEach>
					</select>
						<input type="hidden" name="getid" value="${list.getId()}" /> <input
							type="submit" name="teste" value="addPlaylist">
					</form></td>
			</tr>
		</c:forEach>
	</table>
	<form action="GoToInfoMusic">
		<input type="submit" value="Add Music">
	</form>
	<form action="GoToMenuPrincipal">
		<input type="submit" value="Back">
	</form>
	${error}
</body>
</html>