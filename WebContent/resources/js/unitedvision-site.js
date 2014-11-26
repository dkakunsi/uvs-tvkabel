function showMessage(message) {
	if (message !== 'Berhasil!') {
		alert(message);
	}
}

var setTablePegawai = function(result) {
	showMessage(result.message);

	var listModel = result.listModel;
	var count = result.count;
	var total = result.total;
	var index;
	for (index = 0; index < listModel.length; index++) {
		var pegawai = listModel[index];
		
		$("#table-pegawai").find('tbody').append(
			"<tr>" +
				"<td>" + pegawai.kode + "</td>" +
  				"<td>" + pegawai.nama + "</td>" +
  				"<td>" + pegawai.role + "</td>" +
  				"<td>" + pegawai.username + "</td>" +
  				"<td>" + pegawai.password + "</td>" +
  				"<td width=\"110\">" + 
				  "<div class=\"btn-group btn-group-xs\">" +
						"<button type=\"button\" class=\"btn btn-danger\" onclick=\"detailPegawai(" + pegawai.id + ")\" data-toggle=\"modal\" data-target=\"#modal-detail-pegawai\">Detail</button>" +
						"<button type=\"button\" class=\"btn btn-danger\" onclick=\"hapusPegawai(" + pegawai.id + ")\">Hapus</button>" +
				  "</div>" +
  				"</td>" +
			  "</tr>"
	    );
	}
	$("#table-pegawai").find('tbody').append(
		"<tr>" +
			"<td colspan=\"6\">" +
				"<b>" + count + " / " + total + "</b>" +
			"</td>" +
		"</tr>"
	);
}

function cariPegawai() {
	var searchBy = $('#searchBy').val();
	var query = $('#query').val();
	var page = $('#page').val();

	if (searchBy === '') {
		alert('Silahkan Pilih Metode Pencarian!');
	} else if (searchBy === 'kode') {
		$('#table-pegawai > tbody tr').remove();
		loadListPegawaiByKode(query, page, setTablePegawai, errorMessage);
	} else if (searchBy === 'nama') {
		$('#table-pegawai > tbody tr').remove();
		loadListPegawaiByNama(query, page, setTablePegawai, errorMessage);
	}
}

function hapusPegawai(id) {
	if (window.confirm("Apakah anda yakin ingin menghapus data pegawai ?")) {
		deletePegawai(id, function(result){
			if (result.message === 'Berhasil!') {
				alert('Berhasil Menghapus Pelanggan');
			} else {
				alert(result.message);
			}
			
			resetPegawaiDetail();
			cariPegawai();
		}, errorMessage);
  	}
}
  	
function detailPegawai(id) {
	var setPegawaiDetail = function(result) {
		var model = result.model;

		$('#id').val(model.id);
		$('#status').val(model.status);
		$('#kode').val(model.kode);
		$('#nama').val(model.nama);
		$('#role').val(model.role).change();
		$('#username').val(model.username);
		$('#password').val(model.password);
	};
	loadPegawaiById(id, setPegawaiDetail, errorMessage);
}

function resetPegawaiDetail() {
	$('#id').val(0);
	$('#status').val('AKTIF');
	$('#kode').val('');
	$('#nama').val('');
	$('#role').val('').change();
	$('#username').val('');
	$('#password').val('');
}

var setTablePelanggan = function setTablePelanggan(result) {
	showMessage(result.message);
	
	var listModel = result.listModel;
	var count = result.count;
	var total = result.total;
	var index;
	for (index = 0; index < listModel.length; index++) {
		var pelanggan = listModel[index];
		
		$("#table-pelanggan").find('tbody').append(
			"<tr>" +
				"<td>" + pelanggan.kode + "</td>" +
  				"<td>" + pelanggan.nama + "</td>" +
  				"<td>" + pelanggan.status + "</td>" +
  				"<td>" + pelanggan.tunggakan + "</td>" +
  				"<td width=\"100\">Rp " + pelanggan.tunggakan * pelanggan.iuran +"</td>" +
  				"<td width=\"150\">" + 
				  "<div class=\"btn-group btn-group-xs\">" +
						"<button type=\"button\" class=\"btn btn-danger\" onclick=\"detailPelanggan(" + pelanggan.id + ")\" data-toggle=\"modal\" data-target=\"#modal-detail-pelanggan\">Detail</button>" +
						"<button type=\"button\" class=\"btn btn-danger\" onclick=\"hapusPelanggan(" + pelanggan.id + ")\">Hapus</button>" +
				  "</div>" +
  				"</td>" +
  				"<td width=\"250\">" +
				  "<div class=\"btn-group btn-group-xs\">" +
						"<button type=\"button\" class=\"btn btn-success\" onclick=\"ubahStatus(" + pelanggan.id + ")\" data-toggle=\"modal\" data-target=\"#modal-ubah-status\">Ubah Status</button>" +
						"<button type=\"button\" class=\"btn btn-success\" onclick=\"ubahTunggakan(" + pelanggan.id + ")\" data-toggle=\"modal\" data-target=\"#modal-ubah-tunggakan\">Ubah Tunggakan</button>" +
				  "</div>" +
  				"</td>" +
			  "</tr>"
	    );
	}
	$("#table-pelanggan").find('tbody').append(
		"<tr>" +
			"<td colspan=\"8\">" +
				"<b>" + count + " / " + total + "</b>" +
			"</td>" +
		"</tr>"
	);
}

function cariPelanggan() {
	var searchBy = $("#searchBy").val();
	var status = $('#status').val();
	var query = $('#txt-query').val();
	var page = $('#page').val();

	if (searchBy === '' && status === '') {
		alert('Silahkan Pilih Metode Pencarian dan/atau Status Pelanggan!');
	} else if (searchBy === 'kode' && status === '') {
		$('#table-pelanggan > tbody tr').remove();
		loadListPelangganByKode(query, page, setTablePelanggan, errorMessage);
	} else if (searchBy === 'kode' && status !== '') {
		$('#table-pelanggan > tbody tr').remove();
		loadListPelangganByKodeAndStatus(query, status, page, setTablePelanggan, errorMessage);
	} else if (searchBy === 'nama' && status === '') {
		$('#table-pelanggan > tbody tr').remove();
		loadListPelangganByNama(query, page, setTablePelanggan, errorMessage);
	} else if (searchBy === 'nama' && status !== '') {
		$('#table-pelanggan > tbody tr').remove();
		loadListPelangganByNamaAndStatus(query, status, page, setTablePelanggan, errorMessage);
	} else if (searchBy === '' && status !== '') {
		$('#table-pelanggan > tbody tr').remove();
		loadListPelangganByStatus(status, page, setTablePelanggan, errorMessage);
	}
}

function hapusPelanggan(id) {
	if (window.confirm("Apakah anda yakin ingin menghapus data pelanggan ?")) {
		deletePelanggan(id, function(result){
			if (result.message === 'Berhasil!') {
				alert('Berhasil Menghapus Pelanggan!');
			} else {
				alert(result.message);
			}
		}, errorMessage);
  	}
}
 

function ubahStatus(id) {
	$('#id-pelanggan-ubah-status').val(id);
}

function aktifkan(id) {
	if (window.confirm("Apakah anda yakin ingin mengaktifkan pelanggan ?")) {
		activatePelanggan(id, function(result){
			if (result.message === 'Berhasil!') {
				alert('Berhasil Mengaktifkan Pelanggan');
			} else {
				alert(result.message);
			}
			cariPelanggan();
		}, errorMessage);
  	}
}
  	
function berhentikan(id) {
	if (window.confirm("Apakah anda yakin ingin memberhentikan pelanggan ?")) {
		passivatePelanggan(id, function(result){
			if (result.message === 'Berhasil!') {
				alert('Berhasil Memberhentikan Pelanggan');
			} else {
				alert(result.message);
			}
			cariPelanggan();
		}, errorMessage);
  	}
}

function putuskan(id) {
	if (window.confirm("Apakah anda yakin ingin memutuskan pelanggan ?")) {
		banPelanggan(id, function(result){
			if (result.message === 'Berhasil!') {
				alert('Berhasil Memutuskan Pelanggan');
			} else {
				alert(result.message);
			}
			cariPelanggan();
		}, errorMessage);
  	}
}
  	
function detailPelanggan(id) {
	var setDetailPelanggan = function(result){
		var model = result.model;
		
		$('#id-detail-pelanggan').val(model.id);
		$('#tunggakan-detail-pelanggan').val(model.tunggakan);
		$('#status-detail-pelanggan').val(model.status);
		$('#kode-detail-pelanggan').val(model.kode);
		$('#nama-detail-pelanggan').val(model.nama);
		$('#profesi-detail-pelanggan').val(model.profesi);
		$('#nama-kota-detail-pelanggan').val(model.namaKota);
		$('#nama-kecamatan-detail-pelanggan').val(model.namaKecamatan);
		$('#nama-kelurahan-detail-pelanggan').val(model.namaKelurahan);
		$('#lingkungan-detail-pelanggan').val(model.lingkungan);
		$('#keterangan-detail-pelanggan').val(model.detailAlamat);
		$('#telepon-detail-pelanggan').val(model.telepon);
		$('#hp-detail-pelanggan').val(model.hp);
		$('#email-detail-pelanggan').val(model.email);
		$('#tanggal-mulai-detail-pelanggan').val(model.tanggalMulaiStr);
		$('#jumlah-tv-detail-pelanggan').val(model.jumlahTv);
		$('#iuran-detail-pelanggan').val(model.iuran);
	};
	loadPelangganById(id, setDetailPelanggan, errorMessage);
}

function resetPelangganDetail() {
	$('#id-detail-pelanggan').val('');
	$('#tunggakan-detail-pelanggan').val('');
	$('#status-detail-pelanggan').val('');
	$('#kode-detail-pelanggan').val('');
	$('#nama-detail-pelanggan').val('');
	$('#profesi-detail-pelanggan').val('');
	$('#nama-kota-detail-pelanggan').val('');
	$('#nama-kecamatan-detail-pelanggan').val('');
	$('#nama-kelurahan-detail-pelanggan').val('');
	$('#lingkungan-detail-pelanggan').val('');
	$('#keterangan-detail-pelanggan').val('');
	$('#telepon-detail-pelanggan').val('');
	$('#hp-detail-pelanggan').val('');
	$('#email-detail-pelanggan').val('');
	$('#tanggal-mulai-detail-pelanggan').val('');
	$('#jumlah-tv-detail-pelanggan').val('');
	$('#iuran-detail-pelanggan').val('');
}

function ubahTunggakan(id) {
	var setFormTunggakan = function(result) {
		var model = result.model;
		
		$('#id-pelanggan-ubah-tunggakan').val(model.id);
		$('#kode-pelanggan-ubah-tunggakan').val(model.kode);
		$('#nama-pelanggan-ubah-tunggakan').val(model.nama);
		$('#tunggakan-pelanggan-ubah-tunggakan').val(model.tunggakan);
	};
	loadPelangganById(id, setFormTunggakan, errorMessage);
}

function resetFormUbahTunggakan() {
	$('#id-pelanggan-ubah-tunggakan').val('');
	$('#kode-pelanggan-ubah-tunggakan').val('');
	$('#nama-pelanggan-ubah-tunggakan').val('');
	$('#tunggakan-pelanggan-ubah-tunggakan').val('');
}

var setTablePembayaran = function setTablePembayaran(result) {
	showMessage(result.message);

	var listModel = result.listModel;
	var count = result.count;
	var total = result.total;
	var index;
	for (index = 0; index < listModel.length; index++) {
		var pembayaran = listModel[index];

		$("#table-pembayaran").find('tbody').append(
				  "<tr>" +
	  				"<td>" + pembayaran.kode + "</td>" +
	  				"<td>" + pembayaran.namaPelanggan + "</td>" +
	  				"<td>" + pembayaran.namaPegawai + "</td>" +
	  				"<td>" + pembayaran.tagihanStr + "</td>" +
	  				"<td>" + pembayaran.tanggalBayarStr + "</td>" +
	  				"<td>" + pembayaran.jumlahBayar + "</td>" +
	  				"<td width=\"120\">" +
					  "<div class=\"btn-group btn-group-xs\">" +
						"<button type=\"button\" class=\"btn btn-danger\" onclick=\"detailPembayaran(" + pembayaran.id + ")\" data-toggle=\"modal\" data-target=\"#modal-detail-pembayaran\">Detail</button>" +
						"<button type=\"button\" class=\"btn btn-danger\" onclick=\"hapusPembayaran(" + pembayaran.id + ")\">Hapus</button>" +
					  "</div>" +
	 				"</td>" +
  			  "</tr>"
		);
	}
	$("#table-pembayaran").find('tbody').append(
			"<tr>" +
				"<td colspan=\"7\">" +
					"<b>" + count + " / " + total + "</b>" +
				"</td>" +
			"</tr>"
	);
}

function cariPembayaran() {
	var searchBy = $('#searchBy').val();
	var tanggalAwal = $('#tanggalAwal').val();
	var tanggalAkhir = $('#tanggalAkhir').val();
	var query = $('#query').val();
	var page = $('#page').val();
	var re = new RegExp('/');
	tanggalAwal = tanggalAwal.replace(re, '-');
	tanggalAwal = tanggalAwal.replace(re, '-');
	tanggalAkhir = tanggalAkhir.replace(re, '-');
	tanggalAkhir = tanggalAkhir.replace(re, '-');
	
	if (searchBy === '') {
		alert('Silahkan memilih metode pencarian!');
	} else if(searchBy === 'kode pegawai') {
		$('#table-pembayaran > tbody tr').remove();
		loadListPembayaranByKodePegawai(query, tanggalAwal, tanggalAkhir, page, setTablePembayaran, errorMessage);
	} else if(searchBy === 'nama pegawai') {
		$('#table-pembayaran > tbody tr').remove();
		loadListPembayaranByNamaPegawai(query, tanggalAwal, tanggalAkhir, page, setTablePembayaran, errorMessage);
	} else if(searchBy === 'kode pelanggan') {
		$('#table-pembayaran > tbody tr').remove();
		loadListPembayaranByKodePelanggan(query, tanggalAwal, tanggalAkhir, page, setTablePembayaran, errorMessage);
	} else if(searchBy === 'nama pelanggan') {
		$('#table-pembayaran > tbody tr').remove();
		loadListPembayaranByNamaPelanggan(query, tanggalAwal, tanggalAkhir, page, setTablePembayaran, errorMessage);
	}
}

function detailPembayaran(id) {
	var setPembayaranDetail = function(result) {
		var model = result.model;
		
		$('#id-detail-pembayaran').val(model.id);
		$('#id-pegawai-detail-pembayaran').val(model.idPegawai);
		$('#kode-referensi-detail-pembayaran').val(model.kode);
		$('#kode-pelanggan-detail-pembayaran').val(model.kodePelanggan);
		$('#nama-pelanggan-detail-pembayaran').val(model.namaPelanggan);
		$('#profesi-pelanggan-detail-pelanggan').val(model.profesiPelanggan);
		$('#nama-pegawai-detail-pembayaran').val(model.namaPegawai);
		$('#tanggal-bayar-detail-pembayaran').val(model.tanggalBayarStr);
		$('#tagihan-detail-pembayaran').val(model.tagihanStr);
		$('#jumlah-bayar-detail-pembayaran').val(model.jumlahBayar);
	}
	
	loadPembayaranById(id, setPembayaranDetail, errorMessage);
}

function hapusPembayaran(id) {
	if (window.confirm("Apakah anda yakin ingin menghapus data pembayaran ?")) {
		deletePembayaran(id, function(result){
			if (result.message === 'Berhasil!') {
				alert('Berhasil Menghapus Pembayaran!');
			} else {
				alert(result.message);
			}
			cariPembayaran();
		}, errorMessage);
  	}
}

function cetakKartuPelanggan(data, success, error) {
	save('/tvkabel/print/pelanggan/kartu', data, 'POST', success, error);
}

var usePromisePelanggan = function usePromisePelanggan(result) {
	var model = result.model;
	
	$('#id-pelanggan-form-pembayaran').val(model.id);
	$('#kode-pelanggan-form-pembayaran').val(model.kode);
	$('#nama-pelanggan-form-pembayaran').val(model.nama);
	$('#jumlah-bayar-pelanggan-form-pembayaran').val(model.iuran);
	$('#profesi-pelanggan-form-pembayaran').val(model.profesi);

	showMessage(result.message);
}

var usePromiseTagihan = function usePromiseTagihan(result) {
	var model = result.model;
	var tagihan = model.bulan + '-' + model.tahun;
	
	$('#tagihan-pelanggan-form-pembayaran').val(tagihan);

	showMessage(result.message);
}
