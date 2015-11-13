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

<title>Music Library - My Music</title>

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
		<center><h1>My Music</h1></center>
		</div>
			<form action="GoToInfoMusic">
				<button type="submit" class="btn btn-lg btn-primary btn-block">Add New Song</button>
			</form>
			<br>
			<br>
			<div class="row">
				<div class="col-md-12">
					<table class="table table-bordered table-hover">
						<thead>
							<tr>
								<th class="text-center">Title</th>
								<th class="text-center">Artist</th>
								<th class="text-center">Album</th>
								<th class="text-center">Year</th>
								<th class="text-center">Path</th>
								<th class="text-center">Add to Playlist</th>
								<th class="text-center">Edit info</th>
								<th class="text-center">Detach Song</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${lists}" var="list">
								<tr align="center">
									<td>${list.getTitle()}</td>
									<td>${list.getArtist()}</td>
									<td>${list.getAlbum()}</td>
									<td>${list.getYear()}</td>
									<td>${list.getPath()}</td>
									<td><form action="AddPlaylist" method="post">
											<select name="play">
												<c:forEach items="${listplaylist}" var="playlist">
													<option value="${playlist.getId()}">${playlist.getPlaylistName()}</option>
												</c:forEach>
											</select> <input type="hidden" name="getid" value="${list.getId()}" />
											<button type="submit" class="btn btn-xs btn-default">Add to Playlist</button>
										</form></td>
									<td><a href="GoToEditMusic?music_id=${list.getId()}"><button
												type="submit" class="btn btn-xs btn-default">Edit</button>
											<a></td>
									<td><form action="Detach" method="post">
											<input type="hidden" name="getid" value="${list.getId()}" />
											<button type="submit" class="btn btn-xs btn-default">Detach</button>
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