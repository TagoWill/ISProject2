<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<p>Nome: ${nome}</p>
	<p>User: ${user}</p>
	<p>Email: ${email}</p>
	<p>Password: ${password}</p>


	<form action="EditarPrefilSevlet">
		<input type="submit" value="Salvar">
	</form>
	<form action="DeletePrefilServlet">
		<input type="submit" value="Apagar conta">
	</form>
	<form action="paginainicial.jsp">
		<input type="submit" value="Back">
	</form>

</body>
</html>