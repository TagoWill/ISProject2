<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

		<form action="SignUp" method="post">
            Nome:  <input type="text" name="nome" size="20px"> <br>
            Email:  <input type="text" name="email" size="20px"> <br>
            User:  <input type="text" name="username" size="20px"> <br>
            Password:  <input type="password" name="password" size="20px"> <br>
            Password novamente:  <input type="password" name="password2" size="20px"> <br><br>
        <input type="submit" value="submit">
        </form>
        <p></p>
        <p>${error}</p><!-- Mensagem de erro -->
</body>
</html>