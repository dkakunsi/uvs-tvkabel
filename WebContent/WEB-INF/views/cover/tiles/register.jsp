		  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		  <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

		  <tiles:insertAttribute name="kota_datalist" />
		  <tiles:insertAttribute name="kecamatan_datalist" />
		  <tiles:insertAttribute name="kelurahan_datalist" />
		  
          <div class="inner cover">
            <h1 class="cover-heading">Mendaftar</h1>
		    <br /><br />
		    <form name="f" action='<c:url value='/registrasi' />' method="post">
		      <fieldset>
		        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	    	    <input name="nama" type="text" placeholder="Nama Perusahaan" class="form-control" /><br />
		        <input name="namaKota" list="kota" placeholder="Nama Kota" class="form-control" /><br />
		   	    <input name="namaKecamatan" list="kecamatan" placeholder="Nama Kecamatan" class="form-control" /><br />
		        <input name="namaKelurahan" list="kelurahan" placeholder="Nama Kelurahan" class="form-control" /><br />
		        <input name="telepon" type="text" placeholder="Nomor Telepon" class="form-control" /><br />
		        <input name="hp" type="text" placeholder="Nomor Seluler" class="form-control" /><br />
			    <input name="email" type="email" placeholder="Email" class="form-control" /><br />
		        <div class="form-actions">
		          <button type="submit" class="btn btn-lg btn-default">Daftar</button>
		        </div>
		      </fieldset>
		    </form>
          </div>
