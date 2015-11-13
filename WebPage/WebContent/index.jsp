<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description" content="">
<meta name="author" content="">
<link rel="icon" href="../../favicon.ico">

<title>Music Library - Sign In</title>

<!-- Bootstrap core CSS -->
<link href="dist/css/bootstrap.min.css" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="signin.css" rel="stylesheet">

</head>
<body>
	<div class="container">
		<form class="form-signin" action="Login" method="post">
			<center><img src="img/playlist_album_collection.png"/></center>
			<center><h2 class="form-signin-heading">Music Library</h2></center>
			<br>
			<br>
			<label for="inputEmail" class="sr-only">Email address</label> 
				<input type="email" name="email" id="inputEmail" class="form-control" placeholder="Email address" required autofocus> 
			<label for="inputPassword" class="sr-only">Password</label> 
				<input type="password" name="password" id="inputPassword" class="form-control" placeholder="Password" required>
			<button class="btn btn-lg btn-primary btn-block" type="submit">Sign In</button>
		</form>
		<center><form class="form-signin" action="register.jsp">
			<button class="btn btn-sm btn-primary" type="submit">Sign Up</button>
		</form></center>
		<center><h3>
			<span class="label label-default">${error}</span>
		</h3></center>
	</div>
</body>
</html>