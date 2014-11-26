<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<datalist id="kelurahan">
  <c:forEach var="kelurahan" items="${listKelurahan }">
    <option value="${kelurahan.nama }" />
  </c:forEach>
</datalist>
