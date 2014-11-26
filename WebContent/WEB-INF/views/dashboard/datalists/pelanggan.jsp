<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<datalist id="pelanggan">
  <c:forEach var="pelanggan" items="${listPelanggan}">
	<option value="${pelanggan.nama}" />
  </c:forEach>
</datalist>
