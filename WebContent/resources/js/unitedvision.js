/* UnitedVision. 2014
 * Manado, Indonesia.
 * dkakunsi.unitedvision@gmail.com
 * 
 * Created by Deddy Christoper Kakunsi
 * Manado, Indonesia.
 * deddy.kakunsi@gmail.com
 * 
 * Version: 3.0.*
 */
var target = 'https://uvision-test.whelastic.net/tvkabel/api'; //Testing Server
//var target = 'https://uvision.whelastic.net/tvkabel/api'; //Production Server

// Please Wait (loading) modal definition.
// call myApp.showPleaseWait() to show it,
// and myApp.hidePleaseWait() to hide it.
var myApp = function () {
    var pleaseWaitDiv = $('<div class="modal fade" id="pleaseWaitDialog" role="dialog"><div class="modal-dialog"><div class="modal-content modal-cover"><div class="modal-header"><h1 class="modal-title">Loading</h1></div><div class="modal-body"><div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div><div class="modal-footer"></div></div><!-- /.modal-content --></div><!-- /.modal-dialog --></div><!-- /.modal -->');
    return {
        showPleaseWait: function () {
            pleaseWaitDiv.modal('show');
        },
        hidePleaseWait: function () {
            pleaseWaitDiv.modal('hide');
        }
    };
} ();
// OnBeforeAjaxRequest is used to show please wait (loading modal)
// don't forget to hide it in every success and error callback
OnBeforeAjaxRequest = function () {
    myApp.showPleaseWait();
};
// Default error callback
var errorMessage = function (jqXHR, textStatus, errorThrown) {
    myApp.hidePleaseWait();
    alert('Error : ' + textStatus + ' - ' + errorThrown);
}

function getUsername() {
	return localStorage.getItem('username');
}
function setUsername(username) {
	localStorage.setItem('username', username);
}
function getPassword() {
	return localStorage.getItem('password');
}
function setPassword(password) {
	localStorage.setItem('password', password);
}
function getOperator() {
    var operator = localStorage.getItem('operator');
    return JSON.parse(operator);
}
function setOperator(operator) {
	localStorage.setItem('operator', JSON.stringify(operator));
}
function getPerusahaan() {
	var operator = getOperator();
	return operator.perusahaanModel;
}
function getIdPerusahaan() {
	var perusahaan = getPerusahaan();
	return perusahaan.id;
}
function isLogin() {
	var operator = getOperator();
	if (operator === '' || operator === undefined || operator === null)
		return false;
	return true;
}
function login(username, password) {
	var data = {
		username: username,
		password: password
	};

	$.ajax({
	    beforeSend: OnBeforeAjaxRequest,
	    type: 'POST',
	    url: target + '/login',
	    username: username,
	    password: password,
	    contentType: 'application/json',
	    xhrFields: {
	        withCredentials: true
	    },
	    data: JSON.stringify(data),
	    success: function (result) {
	        myApp.hidePleaseWait();
	        if (result.message === 'Berhasil!') {
	            setUsername(username);
	            setPassword(password);
	            setOperator(result.model);

	            alert('Berhasil Login - Selamat Datang ' + result.model.nama + ' dari ' + result.model.perusahaanModel.nama);
	            window.location.href = "dashboard.html";
	        } else {
	            alert(result.message);
	        }
	    },
	    error: errorMessage
	});
}
function logout() {
    $.ajax({
        type: 'POST',
        beforeSend: OnBeforeAjaxRequest,
        url: target + '/logout',
        username: getUsername(),
        password: getPassword(),
        contentType: 'application/json',
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            myApp.hidePleaseWait();
            setUsername('');
            setPassword('');
            setOperator('');

            alert('Berhasil Logout');
        },
        error: errorMessage
    });
}
function process(url, data, method, success, error) {
	var _username = getUsername();
	var _password = getPassword();
	
	if (_username !== '' || password !== '') {
		$.ajax({
            beforeSend: OnBeforeAjaxRequest,
			type: method,
			url: url,
			username: _username,
			password: _password,
			contentType: 'application/json',
            xhrFields: {
                withCredentials: true
            },
			processData: false,
			data: JSON.stringify(data),
			success: success,
			error: error
		});
	} else {
		
	}
}
function save(url, data, method, success, error) {
	process(url, data, method, success, error);
}
function load(url, success, error) {
	process(url, '', 'GET', success, error);
}
function savePerusahaan(data, success, error) {
	save(target + '/perusahaan/master', data, 'POST', success, error);
}
function loadPerusahaanById(id, success, error) {
	load(target + '/perusahaan/id/' + id, success, error);
}
function loadPerusahaanByKode(kode, success, error) {
	load(target + '/perusahaan/kode/' + kode, success, error);
}
function loadActivePerusahaan(success, error) {
	load(target + '/perusahaan/active', success, error);
}
function loadRekapPerusahaan(perusahaan, success, error) {
	load(target + '/perusahaan/' + perusahaan + '/rekap', success, error);
}
function loadRekapAktifPerusahaan(success, error) {
	load(target + '/perusahaan/active/rekap', success, error);
}
function loadAllPerusahaan(success, error) {
	load(target + '/perusahaan', success, error);
}
function registrasiPerusahaan(data, success, error) {
	save(target + '/perusahaan/registrasi', data, 'POST', success, error);
}
function savePegawai(data, success, error) {
	save(target + '/pegawai/perusahaan/' + getIdPerusahaan() + '/master', data, 'POST', success, error);
}
function deletePegawai(id, success, error) {
	save(target + '/pegawai/removed/master', id, 'POST', success, error);
}
function loadActivePegawai(success, error) {
	load(target + '/pegawai/active', success, error);
}
function loadPegawaiById(id, success, error) {
	load(target + '/pegawai/id/' + id, success, error);
}
function loadPegawaiByKode(kode, success, error) {
	load(target + '/pegawai/perusahaan/' + getIdPerusahaan() + '/kode/' + kode, success, error);
}
function loadPegawaiByNama(nama, success, error) {
	load(target + '/pegawai/perusahaan/' + getIdPerusahaan() + '/nama/' + nama, success, error);
}
function loadListPegawaiByKode(kode, page, success, error) {
	load(target + '/pegawai/perusahaan/' + getIdPerusahaan() + '/kode/' + kode + '/page/' + page, success, error)
}
function loadListPegawaiByNama(nama, page, success, error) {
	load(target + '/pegawai/perusahaan/' + getIdPerusahaan() + '/nama/' + nama + '/page/' + page, success, error);
}
function loadAllPegawai(success, error) {
	load(target + '/pegawai/perusahaan/' + getIdPerusahaan(), success, error);
}
function savePelanggan(data, success, error) {
	save(target + '/pelanggan/perusahaan/' + getIdPerusahaan() + '/master', data, 'POST', success, error);
}
function updateTunggakan(id, data, success, error) {
	save(target + '/pelanggan/' + id + '/tunggakan/master', data, 'PUT', success, error);
}
function setPelangganMapLocation(id, latitude, longitude, success, error) {
	save(target + '/' + id + '/location/' + latitude + '/' + longitude, '', "PUT", success, error);
}
function activatePelanggan(id, success, error) {
	save(target + '/pelanggan/activated/master', id, 'PUT', success, error);
}
function passivatePelanggan(id, success, error) {
	save(target + '/pelanggan/passivated/master', id, 'PUT', success, error);
}
function banPelanggan(id, success, error) {
	save(target + '/pelanggan/banned/master', id, 'PUT', success, error);
}
function deletePelanggan(id, success, error) {
	save(target + '/pelanggan/removed/master', id, 'PUT', success, error);
}
function loadPelangganById(id, success, error) {
	load(target + '/pelanggan/id/' + id, success, error);
}
function loadPelangganByKode(kode, success, error) {
	load(target + '/pelanggan/perusahaan/' + getIdPerusahaan() + '/kode/' + kode, success, error);
}
function loadListPelangganByKode(kode, page, success, error) {
	load(target + '/pelanggan/perusahaan/' + getIdPerusahaan() + '/kode/' + kode + '/page/' + page, success, error);
}
function loadListPelangganByKodeAndStatus(kode, status, page, success, error) {
	load(target + '/pelanggan/perusahaan/' + getIdPerusahaan() + '/kode/' + kode + '/status/' + status + '/page/' + page, success, error);
}
function loadPelangganByNama(nama, success, error) {
	load(target + '/pelanggan/perusahaan/' + getIdPerusahaan() + '/nama/' + nama, success, error);
}
function loadListPelangganByNama(nama, page, success, error) {
	load(target + '/pelanggan/perusahaan/' + getIdPerusahaan() + '/nama/' + nama + '/page/' + page, success, error);
}
function loadListPelangganByNamaAndStatus(nama, status, page, success, error) {
	load(target + '/pelanggan/perusahaan/' + getIdPerusahaan() + '/nama/' + nama + '/status/' + status + '/page/' + page, success, error);
}
function loadListPelangganByStatus(status, page, success, error) {
	load(target + '/pelanggan/perusahaan/' + getIdPerusahaan() + '/status/' + status + '/page/' + page, success, error);
}
function loadAllPelanggan(success, error) {
	load(target + '/pelanggan/perusahaan/' + getIdPerusahaan(), success, error);
}
function savePembayaran(data, success, error) {
	save(target + '/pembayaran/master', data, 'POST', success, error);
}
function updatePembayaran(data, success, error) {
	save(target + '/pembayaran/master', data, 'PUT', success, error);
}
function deletePembayaran(id, success, error) {
	save(target + '/pembayaran/master', id, 'DELETE', success, error);
}
function loadTagihanByKode(kode, success, error) {
	load(target + '/pembayaran/perusahaan/' + getIdPerusahaan() + '/pelanggan/kode/' + kode + '/payable', success, error);
}
function loadTagihanByNama(nama, success, error) {
	load(target + '/pembayaran/perusahaan/' + getIdPerusahaan() + '/pelanggan/nama/' + nama + '/payable', success, error);
}
function loadTagihanById(id, success, error) {
	load(target + '/pembayaran/pelanggan/id/' + id + '/payable', success, error);
}
function loadPembayaranById(id, success, error) {
	load(target + '/pembayaran/' + id, success, error);
}
function loadListPembayaranByKodePegawai(kode, tanggalAwal, tanggalAkhir, page, success, error) {
	load(target + '/pembayaran/perusahaan/' + getIdPerusahaan() + '/pegawai/kode/' + kode + '/awal/' + tanggalAwal + '/akhir/' + tanggalAkhir + '/page/' + page, success, error);
}
function loadListPembayaranByNamaPegawai(nama, tanggalAwal, tanggalAkhir, page, success, error) {
	load(target + '/pembayaran/perusahaan/' + getIdPerusahaan() + '/pegawai/nama/' + nama + '/awal/' + tanggalAwal + '/akhir/' + tanggalAkhir + '/page/' + page, success, error);
}
function loadListPembayaranByKodePelanggan(kode, tanggalAwal, tanggalAkhir, page, success, error) {
	load(target + '/pembayaran/perusahaan/' + getIdPerusahaan() + '/pelanggan/kode/' + kode + '/awal/' + tanggalAwal + '/akhir/' + tanggalAkhir + '/page/' + page, success, error);
}
function loadListPembayaranByNamaPelanggan(nama, tanggalAwal, tanggalAkhir, page, success, error) {
	load(target + '/pembayaran/perusahaan/' + getIdPerusahaan() + '/pelanggan/nama/' + nama + '/awal/' + tanggalAwal + '/akhir/' + tanggalAkhir + '/page/' + page, success, error);
}
function loadAllKota(success, error) {
	load(target + '/alamat/kota', success, error);
}
function loadAllKecamatan(success, error) {
	load(target + '/alamat/kecamatan', success, error);
}
function loadListKecamatanByKota(kota, success, error) {
	load(target + '/alamat/kecamatan/kota/' + kota, success, error);
}
function loadAllKelurahan(success, error) {
	load(target + '/alamat/kelurahan', success, error);
}
function loadListKelurahanByKecamatan(kecamatan, success, error) {
	load(target + '/kelurahan/kecamatan/' + kecamatan, success, error);
}

//MAPS Library
var aktif_icon = 'images/aktif.png';
var berhenti_icon = 'images/berhenti.png';
var putus_icon = 'images/putus.png';
var studio_icon = 'images/studio.png';
var warning_icon = 'images/warning.png';
var unitedvision_icon = 'images/unitedvision.png';

function getIcon(status) {
	switch(status) {
		case 'aktif': return aktif_icon;
    	case 'berhenti':return berhenti_icon;
    	case 'putus': return putus_icon;
    }
}
function getMap() {
	var perusahaan = getPerusahaan();
	var lat = perusahaan.latitude;
    var lng = perusahaan.longitude;
	//var lat = 1.502444; //debug purposes
    //var lng = 124.915389; //debug purposes
    var location = new google.maps.LatLng(lat, lng);

    var mapOptions = {
		center: location,
		zoom: 16
	};

	return new google.maps.Map( $('#map-canvas'), mapOptions );
}
function setMarker(map, location, image, title) {
	var marker = new google.maps.Marker({
		position: location,
        map: map,
        icon: image,
        title: title
	});
}
function setUnitedVisionMap(map) {
	var location = new google.maps.LatLng(1.502444, 124.915389);
	setMarker(map, location, unitedvision_icon, 'United Vision');
}
function setPerusahaanMap(map) {
	var perusahaan = getPerusahaan();
	var lat = perusahaan.latitude;
    var lng = perusahaan.longitude;
	if (lat != 0 && lng !=0 ) {
		var location = new google.maps.LatLng(lat, lng);
		setMarker(map, location, studio_icon, perusahaan.nama);
	}
}
function loadPelangganMap(status) {
	var success = function(result){
		var map = getMap();
		var icon = getIcon(status);
        		
		setUnitedVisionMap(map);
		setPerusahaanMap(map);

		var index;
		for	(index = 0; index < result.length; index++) {
			var lat = result[index].alamat.latitude;
			var lng = result[index].alamat.longitude;

			if (lat == 0 && lng == 0)
				continue;

			var nama = result[index].nama;
			var pelanggan_location = new google.maps.LatLng(lat, lng);
	        		
			setMarker(map, pelanggan_location, icon, nama);
		}
	}

	load(target + '/pelanggan/status/' + status, success, errorMessage);
}
function tampilkanPeta(query) {
	var success = function(result){
		alert(result.message);
		var map = getMap();

		setUnitedVisionMap(map);
		setPerusahaanMap(map);

		var model = result.model;
		var lat = model.latitude;
		var lng = model.longitude;

		if (lat != 0 && lng != 0) {
			var icon = getIcon(model.status.toLowerCase());
			var nama = model.nama;
			var pelanggan_location = new google.maps.LatLng(lat, lng);
					        		
			setMarker(map, pelanggan_location, icon, nama);
		}
	}
	
	load(target + '/pelanggan/nama/' + query, success, errorMessage);
}
