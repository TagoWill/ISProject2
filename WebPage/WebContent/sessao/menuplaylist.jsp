<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

<title>Music Library - My Playlists</title>

<!-- Bootstrap core CSS -->
<link href="dist/css/bootstrap.min.css" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="dashboard.css" rel="stylesheet">
</head>

<body>
	<div class="container theme-showcase" role="main">
		<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="GoToMenuPrincipal">Music Library</a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav navbar-right">
					<li><a href="GoToProfile">${nome}</a></li>
					<li><a href="GoToSearch">Search</a></li>
					<li><a href="GoToPlaylist">My Playlists</a></li>
					<li><a href="GoToMusic">My Music</a></li>
					<li><a href="LogOut">Logout</a></li>
				</ul>
			</div>
		</div>
		</nav>
		<div class="page-header"></div>
		<div class="page-header">
		<center><h1>My Playlists</h1></center>
		</div>
			<table style="width:100%">
				<form action="CreatePlaylist" method="post">
					<tr>
						<td style="width:90%" align="left"><input type="text" class="form-control" name="listname"></td>
						<td style="width:10%" align="right"><button type="submit" class="btn btn-sm btn-primary">Create New Playlist</button></td>
					</tr>
				</form>
			</table>
			<br>
			<br>
			<table>
				<tr>
					<form action="OrderASC" method="post">
						<td><button type="submit" type="button" class="btn btn-xs btn-default">Ascending Order</button></td>
					</form>
					<form action="OrderDESC" method="post">
						<td><button type="submit" type="button" class="btn btn-xs btn-default">Descending Order</button></td>
					</form>
				</tr>
			</table>
			<div class="row">
				<div class="col-md-12">
					<table class="table table-bordered table-hover">
						<thead>
							<tr>
								<th class="text-center" style="width:50%">Playlist Name</th>
								<th class="text-center" style="width:50%">Delete</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${lists}" var="list">
								<tr align="center">
									<td><a href="GoToInfoPlaylist?playlist_id=${list.getId()}">${list.getPlaylistName()}</a>
									</td>
									<td><form action="DeletePlaylist" method="post">
											<input type="hidden" name="getid" value="${list.getId()}" />
											<button type="submit" class="btn btn-sm btn-danger">Delete</button>
									</form></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			<center><h3>
				<span class="label label-default">${error}</span>
			</h3></center>
		</div>
	</div>
	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="dist/js/bootstrap.min.js"></script>
</body>
</html>