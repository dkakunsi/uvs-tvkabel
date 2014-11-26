<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<datalist id="kode_pelanggan">
  <c:forEach var="pelanggan" items="${listPelanggan}">
	<option value="${pelanggan.kode}" />
  </c:forEach>
</datalist>
