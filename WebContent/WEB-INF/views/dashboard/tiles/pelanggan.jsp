		  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		  <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
		  <tiles:insertAttribute name="kota_datalist" />
		  <tiles:insertAttribute name="kecamatan_datalist" />
		  <tiles:insertAttribute name="kelurahan_datalist" />
		  
		  <script type="text/javascript">
		  $(document).ready(function() {
				$('#btn-next').click(function(){
					var page = $('#page').val();
					page++;
					$('#page').val(page);
					cariPelanggan();
				});

				$('#btn-prev').click(function(){
					var page = $('#page').val();
					page--;
					$('#page').val(page);
					cariPelanggan();
				});

				$('#btn-cari').click(function(){
					$('#page').val(0);
					cariPelanggan();
				});

			    $ ('#btn-include').click(function() {
	    			$ ('#btn-include').addClass('btn-success').removeClass('btn-default');
		    		$ ('#btn-exclude').addClass('btn-default').removeClass('btn-success');
		    		
		    		$ ('#pembayaran').val(1);
			    });

			    $ ('#btn-exclude').click(function() {
	    			$ ('#btn-exclude').addClass('btn-success').removeClass('btn-default');
		    		$ ('#btn-include').addClass('btn-default').removeClass('btn-success');

		    		$ ('#pembayaran').val(0);
			    });
			    
			    $('#btn-ok-ubah-tunggakan').click(function(){
					var id = $('#id-pelanggan-ubah-tunggakan').val();
					var tunggakan = $('#tunggakan-pelanggan-ubah-tunggakan').val();
					
					updateTunggakan(id, tunggakan, function(result){
						if (result.message === 'Berhasil!') {
							alert('Berhasil Mengubah Tunggakan Pelanggan!');
						} else {
							alert(result.message);
						}
					}, errorMessage);
					
					resetFormUbahTunggakan();
					cariPelanggan();
			    });
			    
			    $('#btn-simpan-pelanggan').click(function(){
	        		var data = {
	        			id: $('#id-detail-pelanggan').val(),
	        			tunggakan: $('#tunggakan-detail-pelanggan').val(),
	        			status: $('#status-detail-pelanggan').val(),
	        			kode: $('#kode-detail-pelanggan').val(),
	        			nama: $('#nama-detail-pelanggan').val(),
	        			profesi: $('#profesi-detail-pelanggan').val(),
	        			namaKota: $('#nama-kota-detail-pelanggan').val(),
	        			namaKecamatan: $('#nama-kecamatan-detail-pelanggan').val(),
	        			namaKelurahan: $('#nama-kelurahan-detail-pelanggan').val(),
	        			lingkungan: $('#lingkungan-detail-pelanggan').val(),
	        			detailAlamat: $('#keterangan-detail-pelanggan').val(),
	        			telepon: $('#telepon-detail-pelanggan').val(),
	        			hp: $('#hp-detail-pelanggan').val(),
	        			email: $('#email-detail-pelanggan').val(),
	        			tanggalMulaiStr: $('#tanggal-mulai-detail-pelanggan').val(),
	        			jumlahTv: $('#jumlah-tv-detail-pelanggan').val(),
	        			iuran: $('#iuran-detail-pelanggan').val()
	        		};
	        		
	        		savePelanggan(data, function(result){
	        			if (result.message === 'Berhasil!') {
	        				alert('Berhasil Menyimpan Pelanggan!');
	        			} else {
	        				alert(result.message);
	        			}
	        			resetPelangganDetail();
	        			cariPelanggan();
	        		}, errorMessage);
			    });

				$('#btn-aktifkan-pelanggan').click(function(){
					var idPelanggan = $('#id-pelanggan-ubah-status').val();
					aktifkan(idPelanggan);
				});

				$('#btn-berhentikan-pelanggan').click(function(){
					var idPelanggan = $('#id-pelanggan-ubah-status').val();
					berhentikan(idPelanggan);
				});

				$('#btn-putuskan-pelanggan').click(function(){
					var idPelanggan = $('#id-pelanggan-ubah-status').val();
					putuskan(idPelanggan);
				});

			    $(function() {
				  	$('.selectpicker').selectpicker();
				});

			  	$(function() {
				  	$('.selectpicker').selectpicker({
				  		style: 'btn-info',
				  		size: 4
				  	});
				});
	        	
		        $(function () {
		        	$('.datepicker').datepicker();
				});
			  	
		        $(function () {
		        	$('#datetimepicker').datetimepicker();
				});
			});
		  </script>
		  
		  <input type="hidden" id="page" />
          <div class="panel panel-default">
			<!-- Default panel contents -->
		    <div class="panel-heading">Data Pelanggan</div>
			<div class="panel-body">
          	  <div class="row placeholders">
            	<div class="col-xs-6 col-sm-3 placeholder">
              	  <button class="btn btn-transclute" data-toggle="modal" data-target="#modal-detail-pelanggan">
              	    <img src="<c:url value="/resources/images/add.png" />" width="50" />
					<p>
					  <span class="text-muted">Tambah Pelanggan</span>
				  	</p>
              	  </button>
            	</div>
            	<div class="col-xs-6 col-sm-3 placeholder">
              	  <button class="btn btn-transclute" data-toggle="modal" data-target="#modal-rekap-alamat">
              	    <img src="<c:url value="/resources/images/print.png" />" width="50" />
              	    <p>
                      <span class="text-muted">Rekap Alamat</span>
                    </p>
              	  </button>
            	</div>
            	<div class="col-xs-6 col-sm-3 placeholder">
              	  <button class="btn btn-transclute" data-toggle="modal" data-target="#modal-rekap-tunggakan">
              	    <img src="<c:url value="/resources/images/print.png" />" width="50" />
              	    <p>
                      <span class="text-muted">Rekap Tunggakan</span>
                    </p>
              	  </button>
            	</div>
            	<div class="col-xs-6 col-sm-3 placeholder">
              	  <button class="btn btn-transclute" data-toggle="modal" data-target="#modal-kartu-pelanggan">
              	    <img src="<c:url value="/resources/images/print.png" />" width="50" />
              	    <p>
                      <span class="text-muted">Kartu Pelanggan</span>
                    </p>
              	  </button>
            	</div>

            	<table class="table"> <!-- class="table" defined in Bootstrap CSS -->
			  	  <tr>
			    	<td width="200">
				  	  <select class="selectpicker" name="searchBy" id="searchBy">
						<option value="" selected>- Cari Berdasarkan : -</option>
						<option value="kode">Kode</option>
						<option value="nama">Nama</option>
					  </select>
			    	</td>
			    	<td width="200">
					  <tiles:insertAttribute name="status" />
			    	</td>
			    	<td>
		      		  <div class="input-group">
		        		<input type="text" class="form-control" id="txt-query">
		        		<span class="input-group-btn">
		          		  <button class="btn btn-primary" id="btn-cari">Cari!</button>
		      			</span>
		        	  </div>
			    	</td>
				  </tr>
				</table>

            	<div class="table-responsive">
            	  <table class="table table-striped text-center" id="table-pelanggan">
                	<thead>
                	  <tr>
                    	<th class="center">Kode</th>
                    	<th class="center">Nama</th>
                    	<th class="center">Status</th>
                    	<th class="center" colspan="2">Tunggakan</th>
                    	<th>&nbsp;</th>
                    	<th>&nbsp;</th>
                    	<th>&nbsp;</th>
                	  </tr>
                	</thead>
                	<tbody>
                	</tbody>
            	  </table>
            	</div>
            	<table class="table text-center">
            	  <tr>
					<td colspan="8">
				  	  <div class="btn-group btn-group-md">
				  	  	<button class="btn btn-primary" id="btn-prev">SEBELUMNYA</button>
				  	  	<button class="btn btn-primary" id="btn-next">SELANJUTNYA</button>
				  	  </div>
				  	</td>
            	  </tr>
            	</table>
              </div><!-- row-placeholder -->
		    </div><!-- panel-body -->
          </div><!-- panel -->
		  <div class="modal fade" id="modal-detail-pelanggan" role="dialog">
		    <div class="modal-dialog">
		      <div class="modal-content">
		        <div class="modal-header">
		          <h4 class="modal-title">Data Pelanggan</h4>
		        </div>
		        <div class="modal-body">
		          <form role="form">
	    			<input type="hidden" id="id-detail-pelanggan" value="0" />
	    			<input type="hidden" id="tunggakan-detail-pelanggan" value="0" />
	    			<input type="hidden" id="status-detail-pelanggan" value="AKTIF" />
		            <div class="form-group">
		              <label for="kode-detail-pelanggan" class="control-label">Kode Pelanggan:</label>
		              <input type="text" class="form-control" id="kode-detail-pelanggan" />
		            </div>
		            <div class="form-group">
		              <label for="nama-detail-pelanggan" class="control-label">Nama Pelanggan:</label>
		              <input type="text" class="form-control" id="nama-detail-pelanggan" />
		            </div>
		            <div class="form-group">
		              <label for="profesi-detail-pelanggan" class="control-label">Profesi Pelanggan:</label>
		              <input type="text" class="form-control" id="profesi-detail-pelanggan" />
		            </div>
		            <div class="form-group">
		              <label for="nama-kota-detail-pelanggan" class="control-label">Kota (Alamat):</label>
				      <input list="kota" class="form-control" id="nama-kota-detail-pelanggan" />
		            </div>
		            <div class="form-group">
		              <label for="nama-kecamatan-detail-pelanggan" class="control-label">Alamat:</label>
				      <input list="kecamatan" class="form-control" id="nama-kecamatan-detail-pelanggan" />
		            </div>
		            <div class="form-group">
		              <label for="nama-kelurahan-detail-pelanggan" class="control-label">Kelurahan(Alamat):</label>
				      <input list="kelurahan" class="form-control" id="nama-kelurahan-detail-pelanggan" />
		            </div>
		            <div class="form-group">
		              <label for="lingkungan-detail-pelanggan" class="control-label">Lingkungan (Alamat):</label>
		              <input type="text" class="form-control" id="lingkungan-detail-pelanggan" />
		            </div>
		            <div class="form-group">
		              <label for="keterangan-detail-pelanggan" class="control-label">Keterangan (Alamat):</label>
		              <input type="text" class="form-control" id="keterangan-detail-pelanggan" />
		            </div>
		            <div class="form-group">
		              <label for="telepon-detail-pelanggan" class="control-label">Nomor Telepon:</label>
		              <input type="text" class="form-control" id="telepon-detail-pelanggan" />
		            </div>
		            <div class="form-group">
		              <label for="hp-detail-pelanggan" class="control-label">Nomor Ponsel:</label>
		              <input type="text" class="form-control" id="hp-detail-pelanggan" />
		            </div>
		            <div class="form-group">
		              <label for="email-detail-pelanggan" class="control-label">Alamat Email:</label>
		              <input type="text" class="form-control" id="email-detail-pelanggan" />
		            </div>
		            <div class="form-group">
		              <label for="tanggal-mulai-detail-pelanggan" class="control-label">Tanggal Mulai:</label>
		          	  <div class='input-group date' id='datetimepicker'>
					  	<input type="text" class="form-control datepicker" id="tanggal-mulai-detail-pelanggan" />
			      	  </div>
		            </div>
		            <div class="form-group">
		              <label for="jumlah-tv-detail-pelanggan" class="control-label">Jumlah Tv:</label>
		              <input type="text" class="form-control" id="jumlah-tv-detail-pelanggan" />
		            </div>
		            <div class="form-group">
		              <label for="iuran-detail-pelanggan" class="control-label">Iuran Bulanan:</label>
		              <input type="text" class="form-control" id="iuran-detail-pelanggan" />
		            </div>
		          </form>
		        </div><!-- modal-body -->
		        <div class="modal-footer">
		          <button class="btn btn-default" id="btn-cancel-pelanggan" data-dismiss="modal">Close</button>
				  <button class="btn btn-primary" id="btn-simpan-pelanggan" data-dismiss="modal">Simpan</button>
		        </div>
		      </div><!-- /.modal-content -->
		    </div><!-- /.modal-dialog -->
		  </div><!-- /.modal -->
		  <div class="modal fade" id="modal-rekap-alamat" role="dialog">
		    <div class="modal-dialog">
		      <div class="modal-content">
		        <div class="modal-header">
		          <h4 class="modal-title">Rekap Pelanggan Berdasarkan Alamat</h4>
		        </div>
		        <div class="modal-body">
		          <form id="form-rekap-alamat" role="form" action="<c:url value="/print/rekap/alamat" />" method="post">
			        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			            <div class="form-group">
			              <label for="status-rekap-alamat" class="control-label">Status Pelanggan:</label>
						  <tiles:insertAttribute name="status" />
			            </div>
			            <div class="form-group">
			              <label for="kelurahan-rekap" class="control-label">Kelurahan (Alamat):</label>
						  <input class="form-control" name="kelurahan" list="kelurahan" id="kelurahan-rekap" />
			            </div>
			            <div class="form-group">
			              <label for="lingkungan-rekap" class="control-label">Lingkungan (Alamat):</label>
					      <input class="form-control" name="lingkungan" id="lingkungan" />
			            </div>
		          </form>
		        </div><!-- modal-body -->
		        <div class="modal-footer">
		          <button class="btn btn-default" id="btn-cancel-rekap-alamat" data-dismiss="modal">Close</button>
				  <button class="btn btn-primary" id="btn-cetak-rekap-alamat" type="submit" form="form-rekap-alamat">Cetak</button>
		        </div>
		      </div><!-- /.modal-content -->
		    </div><!-- /.modal-dialog -->
		  </div><!-- /.modal -->
		  <div class="modal fade" id="modal-rekap-tunggakan" role="dialog">
		    <div class="modal-dialog">
		      <div class="modal-content">
		        <div class="modal-header">
		          <h4 class="modal-title">Rekap Pelanggan Berdasarkan Tunggakan</h4>
		        </div>
		        <div class="modal-body">
		          <form id="form-rekap-tunggakan" role="form" action="<c:url value="/print/rekap/tunggakan" />" method="post">
			        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		            <div class="form-group">
		              <label for="status-rekap-tunggakan" class="control-label">Status Pelanggan:</label>
	      			  <tiles:insertAttribute name="status" />
		            </div>
		            <div class="form-group">
		              <label for="tunggakan-rekap" class="control-label">Tunggakan Pelanggan:</label>
	  				  <input class="form-control" name="tunggakan" />
		            </div>
		          </form>
		        </div><!-- modal-body -->
		        <div class="modal-footer">
		          <button class="btn btn-default" id="btn-cancel-rekap-tunggakan" data-dismiss="modal">Close</button>
				  <button class="btn btn-primary" id="btn-cetak-rekap-tunggakan" form="form-rekap-tunggakan" type="submit">Cetak</button>
		        </div>
		      </div><!-- /.modal-content -->
		    </div><!-- /.modal-dialog -->
		  </div><!-- /.modal -->
		  <div class="modal fade" id="modal-kartu-pelanggan" role="dialog">
		    <div class="modal-dialog">
		      <div class="modal-content">
		        <div class="modal-header">
		          <h4 class="modal-title">Cetak Kartu Pelanggan</h4>
		        </div>
		        <div class="modal-body">
		          <form id="form-cetak-kartu-pelanggan" role="form" action="<c:url value="/print/pelanggan/kartu" />" method="post">
				    <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }" />
					<input type="hidden" name="pembayaran" id="pembayaran" value="0" />
		            <div class="form-group">
		              <label for="searchBy" class="control-label">Metode Pencarian:</label>
					  <select class="selectpicker" name="searchBy" id="searchKartuBy">
				    	<option value="">- Cari Berdasarkan : -</option>
				    	<option value="kode">Kode Pelanggan</option>
						<option value="nama">Nama Pelanggan</option>
			    	  </select>
		            </div>
		            <div class="form-group">
		              <label for="btn-opt-pembayaran" class="control-label">Menyertakan Daftar Pembayaran ?</label>
					  <div class="btn-group" id="btn-opt-pembayaran">
						<button type="button" class="btn btn-default" id="btn-include">Ya</button>
						<button type="button" class="btn btn-default" id="btn-exclude">Tidak</button>
					  </div>
		            </div>
		            <div class="form-group">
		              <label for="query" class="control-label">Kriteria Pencarian:</label>
	  				  <input type="text" class="form-control" name="query" id="query" />
		            </div>
		          </form>
		        </div><!-- modal-body -->
		        <div class="modal-footer">
		          <button class="btn btn-default" id="btn-cancel-kartu-pelanggan" data-dismiss="modal">Close</button>
		      	  <button class="btn btn-primary" id="btn-cetak-kartu-pelanggan" type="submit" form="form-cetak-kartu-pelanggan">Cetak!</button>
		        </div>
		      </div><!-- /.modal-content -->
		    </div><!-- /.modal-dialog -->
		  </div><!-- /.modal -->
		  <div class="modal fade" id="modal-ubah-tunggakan" role="dialog">
		    <div class="modal-dialog">
		      <div class="modal-content">
		        <div class="modal-header">
		          <h4 class="modal-title">Ubah Tunggakan Pelanggan Secara Manual</h4>
		        </div>
		        <div class="modal-body">
		          <form role="form">
			        <input type="hidden" id="id-pelanggan-ubah-tunggakan" />
		            <div class="form-group">
		              <label for="kode-pelanggam-ubah-tunggakan" class="control-label">Kode Pelanggan:</label>
	  				  <input class="form-control" id="kode-pelanggan-ubah-tunggakan" readonly />
		            </div>
		            <div class="form-group">
		              <label for="nama-pelanggan-ubah-tunggakan" class="control-label">Nama Pelanggan:</label>
	  				  <input class="form-control" id="nama-pelanggan-ubah-tunggakan" readonly />
		            </div>
		            <div class="form-group">
		              <label for="tunggakan-pelanggan-ubah-tunggakan" class="control-label">Tunggakan Pelanggan:</label>
	  				  <input class="form-control" id="tunggakan-pelanggan-ubah-tunggakan" />
		            </div>
		          </form>
		        </div><!-- modal-body -->
		        <div class="modal-footer">
		          <button class="btn btn-default" id="btn-cancel-ubah-tunggakan" data-dismiss="modal">Close</button>
				  <button class="btn btn-primary" id="btn-ok-ubah-tunggakan" data-dismiss="modal">Simpan</button>
		        </div>
		      </div><!-- /.modal-content -->
		    </div><!-- /.modal-dialog -->
		  </div><!-- /.modal -->
		  <div class="modal fade" id="modal-ubah-status" role="dialog">
		    <div class="modal-dialog">
		      <div class="modal-content">
		        <div class="modal-header">
		          <h4 class="modal-title">Ubah Status Pelanggan</h4>
		        </div>
		        <div class="modal-body">
		          <form role="form">
		            <input type="hidden" id="id-pelanggan-ubah-status" />
		            <div class="form-group">
					  <div class="btn-group btn-group-md" id="ubah-status">
						<button type="button" class="btn btn-warning" id="btn-aktifkan-pelanggan" data-dismiss="modal">Aktifkan</button>
						<button type="button" class="btn btn-warning" id="btn-berhentikan-pelanggan" data-dismiss="modal">Berhentikan</button>
						<button type="button" class="btn btn-warning" id="btn-putuskan-pelanggan" data-dismiss="modal">Putuskan</button>
					  </div>
		            </div>
		          </form>
		        </div><!-- modal-body -->
		        <div class="modal-footer">
		          <button class="btn btn-default" id="btn-cancel-ubah-status" data-dismiss="modal">Close</button>
		        </div>
		      </div><!-- /.modal-content -->
		    </div><!-- /.modal-dialog -->
		  </div><!-- /.modal -->
          