<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="EditarPrefilSevlet" method="post">
		<p>Nome: <input type="text" name="nome" value="${nome}"> </p>
		<p>User: ${user} </p>
		<p>Email: <input type="text" name="email" value="${email}"> </p>
		<p>Password: <input type="password" name="password" value="${password}"> </p>
		<input type="submit" value="Salvar">
	</form>
	<form action="DeletePrefilServlet">
		<input type="submit" value="Apagar conta">
	</form>
	<form action="sessao/paginainicial.jsp">
		<input type="submit" value="Back">
	</form>

</body>
</html>