<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<datalist id="kota">
  <c:forEach var="kota" items="${listKota}">
    <option value="${kota.nama }" />
  </c:forEach>
</datalist>
