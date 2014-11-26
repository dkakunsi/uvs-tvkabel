<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<datalist id="kecamatan">
  <c:forEach var="kecamatan" items="${listKecamatan }">
    <option value="${kecamatan.nama }" />
  </c:forEach>
</datalist>
