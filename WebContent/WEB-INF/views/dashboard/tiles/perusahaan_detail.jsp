		  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		  <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
		  <tiles:insertAttribute name="kota_datalist" />
		  <tiles:insertAttribute name="kecamatan_datalist" />
		  <tiles:insertAttribute name="kelurahan_datalist" />

		  <script type="text/javascript">
			$(document).ready(function(){
				$('#btn-simpan-perusahaan').click(function(){
					var id = $('#id').val();
					var kode = $('#kode').val();
					var nama = $('#nama').val();
					var namaKota = $('#namaKota').val();
					var namaKecamatan = $('#namaKecamatan').val();
					var namaKelurahan = $('#namaKelurahan').val();
					var telepon = $('#telepon').val();
					var hp = $('#hp').val();
					var email = $('#email').val();
					var iuran = $('#iuran').val();
					
					var data = {
						id: id,
						kode: kode,
						nama: nama,
						namaKota: namaKota,
						namaKecamatan: namaKecamatan,
						namaKelurahan: namaKelurahan,
						telepon: telepon,
						hp: hp,
						email: email,
						iuran: iuran
					}
					
					savePerusahaan(data, function(result){
						if (result.message === 'Berhasil!') {
							alert('Berhasil Menyimpan Data Perusahaan');
						} else {
							alert(result.message);
						}
					}, errorMessage);
				});
			});
		  </script>
		  
		  <c:choose>
			<c:when test="${message != NULL || message != '' }">
          	  <div class="row placeholders">
          	  	${message }
           	  </div>
			</c:when>
		  </c:choose>

          <div class="panel panel-default">
			<!-- Default panel contents -->
		    <div class="panel-heading">Detail Pegawai</div>
			<div class="panel-body">
			  <input id="id" type="hidden" value="${perusahaan.id }" />
			  <input id="kode" type="hidden" value="${perusahaan.kode }" />
				<table class="table"> <!-- Bootstrap's table -->
				  <tr>
				    <td>Nama Perusahaan</td>
				    <td colspan="2">
				      <input id="nama" type="text" class="form-control" value="${perusahaan.nama }" />
				    </td>
				  </tr>
				  <tr>
				    <td>Kota</td>
				    <td colspan="2">
				      <input id="namaKota" list="kota" class="form-control" value="${perusahaan.namaKota }" />
				    </td>
				  </tr>
				  <tr>
				    <td>Kecamatan</td>
				    <td colspan="2">
				      <input id="namaKecamatan" list="kecamatan" class="form-control" value="${perusahaan.namaKecamatan }" />
				    </td>
				  </tr>
				  <tr>
				    <td>Kelurahan</td>
				    <td colspan="2">
				      <input id="namaKelurahan" list="kelurahan" class="form-control" value="${perusahaan.namaKelurahan }" />
				    </td>
				  </tr>
				  <tr>
				    <td>Telepon</td>
				    <td colspan="2">
				      <input id="telepon" type="text" class="form-control" value="${perusahaan.telepon }" />
				    </td>
				  </tr>
				  <tr>
				    <td>Handphone</td>
				    <td colspan="2">
				      <input id="hp" type="text" class="form-control" value="${perusahaan.hp }" />
				    </td>
				  </tr>
				  <tr>
				    <td>Email</td>
				    <td colspan="2">
				      <input id="email" type="email" class="form-control" value="${perusahaan.email }" />
				    </td>
				  </tr>
				  <tr>
				    <td colspan="3">&nbsp;</td>
				  </tr>
				  <tr>
				    <td>Iuran</td>
				    <td>
				      <input id="iuran" type="text" class="form-control" value="${perusahaan.iuran }" readonly="readonly" />
				    </td>
				    <td><b>/ Data Pembayaran</b></td>
				  </tr>
				  <tr>
				    <td>Total Tagihan</td>
				    <td colspan="2">
				      <input type="text" class="form-control" value="${tagihanPerusahaan }" readonly="readonly" />
				    </td>
				  </tr>
				  <tr>
				    <td>&nbsp;</td>
				    <td colspan="2">
					  <button class="btn btn-transclute" id="btn-simpan-perusahaan">
              	    	<img src="<c:url value="/resources/images/save.png" />" width="30" />
						Simpan
					  </button>
				    </td>
				  </tr>
				</table>
		    </div>
          </div>
