<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Welcome Page</title>
</head>
<body>
	<div align="center" style="margin-top: 50px;">
		<img src="img/playlist_album_collection.png" /> 
		<form action="Login" method="post">
				Please enter your email: <input type="text" name="email" size="20px" required> <br> 
				Please enter your Password: <input type="password" name="password" size="20px" required> <br>
			<br> <input type="submit" value="login">
		</form>
		<form action="register.jsp">
			<input type="submit" value="SignUp">
		</form>

		<p>${error}</p>
		<!-- Mensagem de erro -->

	</div>
</body>
</html>