		  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		  <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
		  <tiles:insertAttribute name="pegawai_datalist" />
		  <tiles:insertAttribute name="pelanggan_datalist" />
		  <tiles:insertAttribute name="pelanggankode_datalist" />
		  <tiles:insertAttribute name="bulan_datalist" />
		  
		  <script type="text/javascript">
		    $(document).ready(function(){
				$('#btn-next').click(function(){
					var page = $('#page').val();
					page++;
					$('#page').val(page);
		    		cariPembayaran();
				});

				$('#btn-prev').click(function(){
					var page = $('#page').val();
					page--;
					$('#page').val(page);
		    		cariPembayaran();
				});

				$('#btn-cari-pembayaran').click(function(){
		    		$('#page').val(0);
		    		cariPembayaran();
		    	});
		    	
				$('#btn-simpan-form-pembayaran').click(function(){
			    	var idPelanggan = $("#id-pelanggan-form-pembayaran").val();
			    	
			    	if (idPelanggan != 0) {
				    	var idPegawai = $("#id-pegawai-form-pembayaran").val();
				    	var jumlahPembayaran = $("#jumlah-bayar-pelanggan-form-pembayaran").val();
				    	var data = {
				    		idPelanggan: idPelanggan, 
				    		idPegawai: idPegawai, 
				    		jumlahPembayaran: jumlahPembayaran
				    	};

				    	savePembayaran(data, function(result){
				    		if (result.message === 'Berhasil!') {
				    			alert('Berhasil Menyimpan Pembayaran');
				    		} else {
				    			alert(result.message);
				    		}
				    	}, errorMessage);
				    	loadTagihanById(idPelanggan, usePromiseTagihan, errorMessage);
			    	} else {
			    		alert("Silahkan Pilih Pelanggan!");
			    	}
				});
				
				$('#btn-simpan-detail-pembayaran').click(function(){
			    	var data = {
			    		id: $("#id-detail-pembayaran").val(), 
			    		idPegawai: $("#id-pegawai-detail-pembayaran").val(), 
			    		jumlahPembayaran: $("#jumlah-bayar-detail-pembayaran").val()
			    	};
			    	
			    	updatePembayaran(data, function(result){
			    		if (result.message === 'Berhasil!') {
			    			alert('Berhasil Mengubah Detail Pembayaran');
			    		} else {
			    			alert(result.message);
			    		}
			    	}, errorMessage);
			    	cariPembayaran();
				});
				
		    	$ ('#btn-tagihan-rekap-bulan').click(function() {
		    		$ ('#jenis-rekap-bulan').val('tagihan');
		    		$ ('#btn-tagihan-rekap-bulan').addClass('btn-success').removeClass('btn-default');
		    		$ ('#btn-pembayaran-rekap-bulan').addClass('btn-default').removeClass('btn-success');
		    	})
		    	
		    	$ ('#btn-pembayaran-rekap-bulan').click(function() {
		    		$ ('#jenis-rekap-bulan').val('tgl_bayar')
		    		$ ('#btn-pembayaran-rekap-bulan').addClass('btn-success').removeClass('btn-default');
		    		$ ('#btn-tagihan-rekap-bulan').addClass('btn-default').removeClass('btn-success');
		    	})

			    $("#nama-pegawai-form-pembayaran").change(function(){
					var nama = $("#nama-pegawai-form-pembayaran").val();
					var setIdPegawai = function(result) {
						$('#id-pegawai-form-pembayaran').val(result.model.id);
					}
					loadPegawaiByNama(nama, setIdPegawai, errorMessage);
			    });

			    $("#nama-pegawai-detail-pembayaran").change(function(){
					var nama = $("#nama-pegawai-detail-pembayaran").val();
					var setIdPegawai = function(result) {
						$('#id-pegawai-detail-pembayaran').val(result.model.id);
					}
					loadPegawaiByNama(nama, setIdPegawai, errorMessage);
			    });

			    $("#kode-pelanggan-form-pembayaran").change(function(){
					var kode = $("#kode-pelanggan-form-pembayaran").val();
					loadPelangganByKode(kode, usePromisePelanggan, errorMessage);
					loadTagihanByKode(kode, usePromiseTagihan, errorMessage);
			    });
			    
			    $("#nama-pelanggan-form-pembayaran").change(function(){
					var nama = $("#nama-pelanggan-form-pembayaran").val();
					loadPelangganByNama(nama, usePromisePelanggan, errorMessage);
					loadTagihanByNama(nama, usePromiseTagihan, errorMessage);
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
		        	$('#tanggalAwalPicker').datetimepicker();
				});
			  	
		        $(function () {
		        	$('#tanggalAkhirPicker').datetimepicker();
				});
		    });
	      </script>

		  <input type="hidden" id="page" />
          <div class="panel panel-default">
			<!-- Default panel contents -->
		    <div class="panel-heading">Data Pembayaran</div>
			<div class="panel-body">
          	  <div class="row placeholders">
            	<div class="col-xs-6 col-sm-3 placeholder">
              	  <button class="btn btn-transclute" type="button" data-toggle="modal" data-target="#modal-form-pembayaran">
              	    <img src="<c:url value="/resources/images/add.png" />" width="50" />
					<p>
					  <span class="text-muted">Tambah Pembayaran</span>
				  	</p>
              	  </button>
            	</div>
            	<div class="col-xs-6 col-sm-3 placeholder">
              	  <button class="btn btn-transclute" type="button" data-toggle="modal" data-target="#modal-rekap-bulan">
              	    <img src="<c:url value="/resources/images/print.png" />" width="50" />
              	    <p>
                      <span class="text-muted">Rekap Bulanan</span>
                    </p>
              	  </button>
            	</div>
            	<div class="col-xs-6 col-sm-3 placeholder">
              	  <button class="btn btn-transclute" type="button" data-toggle="modal" data-target="#modal-rekap-tahun">
              	    <img src="<c:url value="/resources/images/print.png" />" width="50" />
              	    <p>
                      <span class="text-muted">Rekap Tahunan</span>
                    </p>
              	  </button>
            	</div>
            	<div class="col-xs-6 col-sm-3 placeholder">
              	  <button class="btn btn-transclute" type="button" data-toggle="modal" data-target="#modal-rekap-hari">
              	    <img src="<c:url value="/resources/images/print.png" />" width="50" />
              	    <p>
                      <span class="text-muted">Rekap Harian</span>
                    </p>
              	  </button>
            	</div>

            	<table class="table"> <!-- Bootstrap's table -->
				  <tr>
			    	<td width="200">
					  <select class="selectpicker" id="searchBy">
					  	<option value="" selected>- Cari Berdasarkan : -</option>
					   	<option value="kode pegawai">Kode Pegawai</option>
						<option value="nama pegawai">Nama Pegawai</option>
					    <option value="kode pelanggan">Kode Pelanggan</option>
						<option value="nama pelanggan">Nama Pelanggan</option>
				      </select>
			    	</td>
			    	<td width="200">
					  <input type='text' class="form-control datepicker" id="tanggalAwal" placeholder="Tanggal Awal" />
			    	</td>
			    	<td width="200">
        			  <input type='text' class="form-control datepicker" id="tanggalAkhir" placeholder="Tanggal Akhir" />
			    	</td>
			    	<td>
			    	  <div class="input-group">
			      		<input type="text" class="form-control" id="query">
			      		<span class="input-group-btn">
			      		  <button class="btn btn-primary" id="btn-cari-pembayaran">Cari!</button>
						</span>
			    	  </div>
					</td>
				  </tr>
				</table>

            	<div class="table-responsive">
            	  <table class="table table-striped text-center" id="table-pembayaran">
                	<thead>
                	  <tr>
                    	<th class="center">Kode</th>
                    	<th class="center">Pelanggan</th>
                    	<th class="center">Pegawai</th>
                    	<th class="center">Tagihan</th>
                    	<th class="center">Tanggal Bayar</th>
                    	<th class="center">Jumlah Bayar</th>
						<th>&nbsp;</th>
                	  </tr>
                	</thead>
                	<tbody>
					</tbody>
            	  </table>
            	</div>
            	<table class="table text-center">
            	  <tr>
					<td colspan="7">
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
		  <div class="modal fade" id="modal-form-pembayaran" role="dialog">
		    <div class="modal-dialog">
		      <div class="modal-content">
		        <div class="modal-header">
		          <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
		          <h4 class="modal-title">Form Pembayaran</h4>
		        </div>
		        <div class="modal-body">
		          <form role="form">
				    <input type="hidden" id="id-pelanggan-form-pembayaran" />
				    <input type="hidden" id="id-pegawai-form-pembayaran" value="${pegawai.id }" /><!-- Needed as default value -->
		            <div class="form-group">
		              <label for="kode-pelanggan-form-pembayaran" class="control-label">Kode Pelanggan:</label>
		              <input list="kode_pelanggan" class="form-control" id="kode-pelanggan-form-pembayaran" />
		            </div>
		            <div class="form-group">
		              <label for="nama-pelanggan-form-pembayaran" class="control-label">Nama Pelanggan:</label>
		              <input list="pelanggan" class="form-control" id="nama-pelanggan-form-pembayaran" />
		            </div>
		            <div class="form-group">
		              <label for="profesi-pelanggan-form-pembayaran" class="control-label">Profesi Pelanggan:</label>
		              <input type="text" class="form-control" id="profesi-pelanggan-form-pembayaran" readonly />
		            </div>
		            <div class="form-group">
		              <label for="tagihan-pelanggan-form-pembayaran" class="control-label">Tagihan Pelanggan:</label>
		              <input type="text" class="form-control" id="tagihan-pelanggan-form-pembayaran" readonly />
		            </div>
		            <div class="form-group">
		              <label for="tanggal-bayar-pelanggan-form-pembayaran" class="control-label">Tanggal Pembayaran:</label>
		              <input type="text" class="form-control" id="tanggal-bayar-pelanggan-form-pembayaran" value="${now }" readonly />
		            </div>
		            <div class="form-group">
		              <label for="jumlah-bayar-pelanggan-form-pembayaran" class="control-label">Jumlah Pembayaran:</label>
		              <input type="text" class="form-control" id="jumlah-bayar-pelanggan-form-pembayaran" readonly />
		            </div>
		            <div class="form-group">
		              <label for="nama-pegawai-form-pembayaran" class="control-label">Nama Pegawai:</label>
		              <input list="pegawai" class="form-control" id="nama-pegawai-form-pembayaran" value="${pegawai.nama }" /><!-- Needed as default value -->
		            </div>
		          </form>
		        </div><!-- modal-body -->
		        <div class="modal-footer">
		          <button class="btn btn-default" id="btn-cancel-form-pembayaran" data-dismiss="modal">Close</button>
				  <button class="btn btn-primary" id="btn-simpan-form-pembayaran">Simpan</button>
		        </div>
		      </div><!-- /.modal-content -->
		    </div><!-- /.modal-dialog -->
		  </div><!-- /.modal -->
		  <div class="modal fade" id="modal-detail-pembayaran" role="dialog">
		    <div class="modal-dialog">
		      <div class="modal-content">
		        <div class="modal-header">
		          <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
		          <h4 class="modal-title">Detail Pembayaran</h4>
		        </div>
		        <div class="modal-body">
		          <form role="form">
					<input type="hidden" id="id-detail-pembayaran" />
					<input type="hidden" id="id-pegawai-detail-pembayaran" />
		            <div class="form-group">
		              <label for="kode-referensi-detail-pembayaran" class="control-label">Kode Pembayaran:</label>
		              <input type="text" class="form-control" id="kode-referensi-detail-pembayaran" readonly />
		            </div>
		            <div class="form-group">
		              <label for="kode-pelanggan-detail-pembayaran" class="control-label">Kode Pelanggan:</label>
		              <input type="text" class="form-control" id="kode-pelanggan-detail-pembayaran" readonly />
		            </div>
		            <div class="form-group">
		              <label for="nama-pelanggan-detail-pembayaran" class="control-label">Nama Pelanggan:</label>
		              <input type="text" class="form-control" id="nama-pelanggan-detail-pembayaran" readonly />
		            </div>
		            <div class="form-group">
		              <label for="profesi-pelanggan-detail-pembayaran" class="control-label">Profesi Pelanggan:</label>
		              <input type="text" class="form-control" id="profesi-pelanggan-detail-pembayaran" readonly />
		            </div>
		            <div class="form-group">
		              <label for="nama-pegawai-detail-pembayaran" class="control-label">Nama Pegawai:</label>
		              <input type="text" list="pegawai" class="form-control" id="nama-pegawai-detail-pembayaran" />
		            </div>
		            <div class="form-group">
		              <label for="tanggal-bayar-detail-pembayaran" class="control-label">Tanggal Pembayaran:</label>
		              <input type="text" class="form-control" id="tanggal-bayar-detail-pembayaran" readonly />
		            </div>
		            <div class="form-group">
		              <label for="tagihan-detail-pembayaran" class="control-label">Tagihan:</label>
		              <input type="text" class="form-control" id="tagihan-detail-pembayaran" readonly />
		            </div>
		            <div class="form-group">
		              <label for="jumlah-bayar-detail-pembayaran" class="control-label">Jumlah Pembayaran:</label>
		              <input type="text" class="form-control" id="jumlah-bayar-detail-pembayaran" />
		            </div>
		          </form>
		        </div><!-- modal-body -->
		        <div class="modal-footer">
		          <button class="btn btn-default" id="btn-cancel-detail-pembayaran" data-dismiss="modal">Close</button>
				  <button class="btn btn-primary" id="btn-simpan-detail-pembayaran" data-dismiss="modal">Simpan</button>
		        </div>
		      </div><!-- /.modal-content -->
		    </div><!-- /.modal-dialog -->
		  </div><!-- /.modal -->
		  <div class="modal fade" id="modal-rekap-hari" role="dialog">
		    <div class="modal-dialog">
		      <div class="modal-content">
		        <div class="modal-header">
		          <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
		          <h4 class="modal-title">Rekap Pembayaran Menurut Hari</h4>
		        </div>
		        <div class="modal-body">
		          <form role="form" action="<c:url value="/print/rekap/hari" />" method="post" id="form-rekap-hari">
			  		<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }" />
		            <div class="form-group">
		              <label for="searchBy" class="control-label">Metode Pencarian:</label>
					  <select class="selectpicker" name="searchBy">
					  	<option value="" selected>- Cari Berdasarkan : -</option>
					   	<option value="kode">Kode Pegawai</option>
						<option value="nama">Nama Pegawai</option>
				      </select>
		            </div>
		            <div class="form-group">
		              <label for="tanggal" class="control-label">Hari:</label>
					  <input type='text' class="form-control datepicker" placeholder="Pilih Hari" name="tanggal"/>
		            </div>
		            <div class="form-group">
		              <label for="query" class="control-label">Pencarian:</label>
	  				  <input type="text" class="form-control" name="query" />
		            </div>
		          </form>
		        </div><!-- modal-body -->
		        <div class="modal-footer">
		          <button class="btn btn-default" id="btn-cancel-pegawai" data-dismiss="modal">Close</button>
				  <button class="btn btn-primary" id="btn-cetak-pegawai" form="form-rekap-hari">Cetak</button>
		        </div>
		      </div><!-- /.modal-content -->
		    </div><!-- /.modal-dialog -->
		  </div><!-- /.modal -->
		  <div class="modal fade" id="modal-rekap-bulan" role="dialog">
		    <div class="modal-dialog">
		      <div class="modal-content">
		        <div class="modal-header">
		          <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
		          <h4 class="modal-title">Rekap Pembayaran Menurut Bulan</h4>
		        </div>
		        <div class="modal-body">
		          <form role="form" action="<c:url value="/print/rekap/bulan" />" method="post" id="form-rekap-bulan">
				    <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }" />
				    <input type="hidden" id="jenis-rekap-bulan" name="jenis" value="tagihan" />
		            <div class="form-group">
		              <label for="jenis-rekap-bulan" class="control-label">Jenis Rekap:</label>
					  <div class="btn-group" id="jenis-rekap-bulan">
						<button type="button" id="btn-tagihan-rekap-bulan" class="btn btn-default">Tagihan</button>
						<button type="button" id="btn-pembayaran-rekap-bulan" class="btn btn-default">Pembayaran</button>
					  </div>
		            </div>
		            <div class="form-group">
		              <label for="bulan-rekap-bulan" class="control-label">Bulan:</label>
	  				  <input list="bulan" class="form-control" name="bulan" placeholder="Pilih Bulan" id="bulan-rekap-bulan" />
		            </div>
		            <div class="form-group">
		              <label for="tahun-rekap-bulan" class="control-label">Tahun:</label>
	  				  <input class="form-control" name="tahun" type="text" placeholder="Masukan Tahun" id="tahun-rekap-bulan" />
		            </div>
		          </form>
		        </div><!-- modal-body -->
		        <div class="modal-footer">
		          <button class="btn btn-default" id="btn-cancel-rekap-bulan" data-dismiss="modal">Close</button>
				  <button class="btn btn-primary" id="btn-simpan-rekap-bulan" form="form-rekap-bulan">Cetak</button>
		        </div>
		      </div><!-- /.modal-content -->
		    </div><!-- /.modal-dialog -->
		  </div><!-- /.modal -->
		  <div class="modal fade" id="modal-rekap-tahun" role="dialog">
		    <div class="modal-dialog">
		      <div class="modal-content">
		        <div class="modal-header">
		          <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
		          <h4 class="modal-title">Rekap Pembayaran Menurut Tahun</h4>
		        </div>
		        <div class="modal-body">
		          <form role="form" action="<c:url value="/print/rekap/tahun" />" method="post" id="form-rekap-tahun">
					<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }" />
		            <div class="form-group">
		              <label for="tahun-rekap-tahun" class="control-label">Tahun:</label>
	  				  <input type="text" class="form-control" name="tahun" placeholder="Masukan Tahun" id="tahun-rekap-tahun" />
		            </div>
		          </form>
		        </div><!-- modal-body -->
		        <div class="modal-footer">
		          <button class="btn btn-default" id="btn-cancel-rekap-tahun" data-dismiss="modal">Close</button>
				  <button class="btn btn-primary" id="btn-simpan-rekap-tahun" form="form-rekap-tahun">Cetak</button>
		        </div>
		      </div><!-- /.modal-content -->
		    </div><!-- /.modal-dialog -->
		  </div><!-- /.modal -->
		  