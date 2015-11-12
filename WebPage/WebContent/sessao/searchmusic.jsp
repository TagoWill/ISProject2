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

  <title>Search Music</title>

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
              <a class="navbar-brand" href="#">Music Library</a>
            </div>
            <div id="navbar" class="navbar-collapse collapse">
              <ul class="nav navbar-nav navbar-right">
                <li><a href="GoToSearch">Search</a></li>
                <li><a href="GoToPlaylist">My PlayLists</a></li>
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
            <td>${list.getTitle()}</td>
            <td>${list.getArtist()}</td>
            <td>${list.getAlbum()}</td>
            <td>${list.getYear()}</td>
            <td><form action="AddPlaylist" method="post">
             <select name="play">
               <c:forEach items="${listplaylist}" var="playlist">
               <option value="${playlist.getId()}">${playlist.getPlaylistName()}</option>
             </c:forEach>
           </select>
           <input type="hidden" name="goto" value="gotosearch" />
           <input type="hidden" name="getid" value="${list.getId()}" /> <input
           type="submit" name="teste" value="addPlaylist">
         </form></td>
       </tr>
     </c:forEach>
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