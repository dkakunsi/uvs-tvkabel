<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1">

	<!-- CSRF -->
    <meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>

    <title>UnitedVision</title>
    
    <!-- UnitedVIsion's CSS -->
	<link rel="stylesheet" href="<c:url value="/resources/css/unitedvision.css" />">
	
    <!-- UnitedVision's JS -->
    <script src="<c:url value="/resources/js/unitedvision.js" />"></script>
	
    <!-- UnitedVision's site JS -->
    <script src="<c:url value="/resources/js/unitedvision-site.js" />"></script>
	
    <!-- Bootstrap's CSS -->
	<link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css" />">
	
	<!-- Dashboard's CSS -->
	<link rel="stylesheet" href="<c:url value="/resources/css/dashboard.css" />">
	
	<!-- jQuery Library -->
    <script src="<c:url value="/resources/js/jquery.min.js" />"></script>
    
    <!-- Bootstrap's JS -->
    <script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
	
    <!-- Bootstrap Select JS -->
    <script src="<c:url value="/resources/js/bootstrap-select.min.js" />"></script>

    <!-- Bootstrap Select CSS -->
    <script src="<c:url value="/resources/css/bootstrap-select.min.css" />"></script>

    <!-- Bootstrap Date Picker JS -->
    <script src="<c:url value="/resources/js/bootstrap-datepicker.js" />"></script>

	<script type="text/javascript">
	  $(function () {
		  var token = $("meta[name='_csrf']").attr("content");
		  var header = $("meta[name='_csrf_header']").attr("content");
		  
		  $(document).ajaxSend(function(e, xhr, options) {
		    xhr.setRequestHeader(header, token);
		  });
	  });	
	</script>

  </head>
  <body>
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container-fluid">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">UnitedVision</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-right">
            <li><a href="<c:url value="/" />">Home</a></li>
            <li><a href="<c:url value="/perusahaan/pages" />">Profile Perusahaan</a></li>
            <li><a href="<c:url value="/logout" />">Logout</a></li>
          </ul>
        </div>
      </div>
    </nav>

    <div class="container-fluid">
      <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
          <ul class="nav nav-sidebar">
            <li><a href="<c:url value="/pegawai/pages" />">Pegawai</a></li>
            <li><a href="<c:url value="/pelanggan/pages" />">Pelanggan</a></li>
            <li><a href="<c:url value="/pembayaran/pages" />">Pembayaran</a></li>
          </ul>
          <ul class="nav nav-sidebar">
            <li><a href="<c:url value="/pelanggan/peta/pages" />">Peta Pelanggan</a></li>
            <!--
            <li><a href="<c:url value="/perusahaan/master/tagihan" />">Tagihan Perusahaan</a></li>
            <li><a href="<c:url value="/pelanggan/master/riwayat" />">Riwayat Pelanggan</a></li>
            <li><a href="<c:url value="/notification/master" />">Pemberitahuan</a></li>
            -->
          </ul>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
          <h1 class="page-header">${operator.perusahaan.nama }</h1>

		  <!-- INSERT TILES CONTENT HERE -->
		  <tiles:insertAttribute name="content" />

        </div>
      </div>
    </div>
  </body>
</html>