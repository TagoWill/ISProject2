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

	<title>Signup</title>

	<!-- Bootstrap core CSS -->
	<link href="dist/css/bootstrap.min.css" rel="stylesheet">

	<!-- Custom styles for this template -->
	<link href="signin.css" rel="stylesheet">

	<!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
	<!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
	<script src="assets/js/ie-emulation-modes-warning.js"></script>

	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
      <![endif]-->
  </head>

  <body>

  	<div class="container">

  		<form class="form-signin" action="SignUp" method="post">
  			<h2 class="form-signin-heading">Sign up</h2>
  			<label for="inputEmail" class="sr-only">Nome</label>
  			<input type="text" name="nome" id="inputEmail" class="form-control" placeholder="Nome" required autofocus>
  			<label for="inputPassword" class="sr-only">Email</label>
  			<input type="email" name="email" id="inputPassword" class="form-control" placeholder="Email address" required>
  			<label for="inputPassword" class="sr-only">User</label>
  			<input type="text" name="username" id="inputPassword" class="form-control" placeholder="User name" required>
  			<label for="inputPassword" class="sr-only">Password</label>
  			<input type="password" name="password" id="inputPassword" class="form-control" placeholder="Password" required>
  			<label for="inputPassword" class="sr-only">Password again</label>
  			<input type="password" name="password2" id="inputPassword" class="form-control" placeholder="Password again" required>
  			<button class="btn btn-lg btn-primary btn-block" type="submit">Sign up</button>
  		</form>
  		<form class="form-signin" action="index.jsp">
			<input class="btn btn-lg btn-primary btn-block" type="submit" value="back">
		</form>

  	</div> <!-- /container -->


  	<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
  	<script src="assets/js/ie10-viewport-bug-workaround.js"></script>
  </body>
  </html>
