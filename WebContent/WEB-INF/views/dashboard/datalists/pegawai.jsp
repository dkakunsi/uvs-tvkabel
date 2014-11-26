<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<datalist id="pegawai">
  <c:forEach var="pegawai" items="${listPegawai }">
	<option value="${pegawai.nama }" />
  </c:forEach>
</datalist>
