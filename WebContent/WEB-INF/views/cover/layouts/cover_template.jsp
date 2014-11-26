<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<!DOCTYPE html>
<html>
  <head>
    <!-- Bootstrap's CSS -->
	<link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css" />">
    <!-- Cover's CSS -->
	<link rel="stylesheet" href="<c:url value="/resources/css/cover.css" />">
 
	<!-- jQuery Library -->
    <script src="<c:url value="/resources/js/jquery.min.js" />"></script>
    <!-- Bootstrap's JS -->
    <script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>

    <!-- Doc's JS -->
    <script src="<c:url value="/resources/js/docs.min.js" />"></script>

    <!-- IE Bug's JS -->
    <script src="<c:url value="/resources/js/ie10-viewport-bug-workaround.js" />"></script>
    <title>UnitedVision</title>
  </head>
  <body>
    <div class="site-wrapper">
      <div class="site-wrapper-inner">
        <div class="cover-container">
          <div class="masthead clearfix">
            <div class="inner">
              <h3 class="masthead-brand">UnitedVision</h3>
              <ul class="nav masthead-nav">
                <li><a href="<c:url value="/login" />">Login</a></li>
                <li><a href="<c:url value="/registrasi" />">Daftar</a></li>
                <li><a href="<c:url value="/guest/contact" />">Kontak</a></li>
              </ul>
            </div>
          </div>

		  <tiles:insertAttribute name="content" />

          <div class="mastfoot">
            <div class="inner">
              <p>UnitedVision. 2014</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>