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

	<title>Playlist info</title>

	<!-- Bootstrap core CSS -->
	<link href="dist/css/bootstrap.min.css" rel="stylesheet">

	<!-- Custom styles for this template -->
	<link href="dashboard.css" rel="stylesheet">

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
     <div class="container theme-showcase" role="main">
      <nav class="navbar navbar-inverse navbar-fixed-top">
       <div class="container-fluid">
        <div class="navbar-header">
         <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
          <span class="sr-only">Toggle navigation</span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="GoToMenuPrincipal">Music Library</a>
      </div>
      <div id="navbar" class="navbar-collapse collapse">
       <ul class="nav navbar-nav navbar-right">
        <li><a href="GoToSearch">Search</a></li>
        <li><a href="GoToPlaylist">My Playlists</a></li>
        <li><a href="GoToMusic">My Music</a></li>
        <li><a href="GoToProfile">Profile</a></li>
        <li><a href="LogOut">Logout</a></li>
      </ul>
    </div>
  </div>
</nav>
<div class="page-header" >
</div>
<div class="page-header" >
	<h1>My Playlists</h1>
  <p></p>
  <table>
    <form action="EditPlaylistName" method="post">
      <tr>
        <td>Playlist Name:</td>
        <td>
          <input type="text" class="form-control" name="playlist_name" value="${name}" required>
        </td>
        <td><input type="hidden" name="playlist_id" value="${playlist_id}">
          <button type="submit" class="btn btn-s btn-primary" >Change Name</button>
        </td>
      </form>
      </tr>
    </table>

    <p></p>
    <h4>Songs</h4>

<div class="row">
  <div class="col-md-12">
    <table class="table">
      <thead>
        <tr>
          <th>Title</th>
          <th>Artist</th>
          <th>Album</th>
          <th>Year</th>
          <th>Path</th>
          <th>Remove music</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach items="${lists}" var="list">
          <tr>
           <td>${list.getTitle()}</td>
           <td>${list.getArtist()}</td>
           <td>${list.getAlbum()}</td>
           <td>${list.getYear()}</td>
           <td>${list.getPath()}</td>
          <td><form action="DetachForPlaylist" method="post">
          <input type="hidden" name="getid" value="${list.getId()}" />
          <input type="hidden" name="playlist_id" value="${playlist_id}"> <button type="submit" class="btn btn-s btn-danger" >Remove</button>
        </form></td>
     </tr>
   </c:forEach>
 </tbody>
</table>
</div>
</div>

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="dist/js/bootstrap.min.js"></script>
    <!-- Just to make our placeholder images work. Don't actually copy the next line! -->
    <script src="assets/js/vendor/holder.min.js"></script>
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="assets/js/ie10-viewport-bug-workaround.js"></script>
  </body>
  </html>