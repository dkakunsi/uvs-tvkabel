		  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		  
          <div class="inner cover">
            <h1 class="cover-heading">Silahkan Masuk</h1>
		    <br /><br />
		    <div>
			  <c:choose>
				<c:when test="${param.error }">
			      Invalid username and password.
			      <br /><br />
			    </c:when>
			    <c:when test="${param.logout }">
			      You have been logged out.
			      <br /><br />
			    </c:when>
			  </c:choose>
		      <form name="f" action='<c:url value='/login' />' method="post">
		        <fieldset>
		          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		          <input type="text" class="form-control" id="username" name="username" placeholder="Masukan Username" />
		          <br />
		          <input type="password" class="form-control" id="password" name="password" placeholder="Masukan Password"/>
		          <br />
		          <div class="form-actions">
		            <button type="submit" class="btn btn-lg btn-default">Masuk</button>
		          </div>
		        </fieldset>
		      </form>
		    </div>
          </div>
