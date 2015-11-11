<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search Music</title>
</head>
<body>
	<form action="LogOut">
		<input type="submit" value="LogOut">
	</form>
	<p></p>
	<form action="SearchMusic" method="post">
		<p>Search:</p>
  		<select name="tipo">
    		<option value="0">Title</option>
    		<option value="1">Artist</option>
  		</select>
  		<input type="text" name="search_input" value="${search_input}">
		<input type="submit" value="Go">
	</form>
	<p></p>
	<p></p>
	<table>
		<c:forEach items="${lists}" var="list">
			<tr>
				<td><a href="GoToSearch?title=${list.getId()}">${list.getTitle()}</a></td>
				<td><a href="GoToSearch?artist=${list.getId()}">${list.getArtist()}</a></td>
			</tr>
		</c:forEach>
	</table>
	<form action="GoToMenuPrincipal">
		<input type="submit" value="Back">
	</form>
</body>
</html>