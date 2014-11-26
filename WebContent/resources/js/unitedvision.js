/* UnitedVision. 2014
 * Manado, Indonesia.
 * dkakunsi.unitedvision@gmail.com
 * 
 * Created by Deddy Christoper Kakunsi
 * Manado, Indonesia.
 * deddy.kakunsi@gmail.com
 * 
 * Version: 3.0.3
 */
var target = 'https://uvision-test.whelastic.net/tvkabel/api'; //Testing Server
//var target = 'https://uvision.whelastic.net/tvkabel/api'; //Production Server
var errorMessage = function (jqXHR, textStatus, errorThrown) {
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
	return localStorage.getItem('operator');
}
function setOperator(operator) {
	localStorage.setItem('operator', operator);
}
function getPerusahaan() {
	var operator = getOperator();
	return operator.perusahaan;
}
function login(username, password) {
    $.ajax({
        type: 'POST',
        url: target + '/login',
        username: username,
        password: password,
        contentType: 'application/json',
        async: false,
        success: function (result) {
            setUsername(username);
            setPassword(password);
            var setActivePegawaiAsOperator = function (result) {
                setOperator(result.model);
            }
            loadActivePegawai(setActivePegawaiAsOperator, errorMessage);

            alert('Berhasil Login');
            window.location.href = "dashboard.html";
        },
        error: errorMessage
    });
}
function loginWithData(username, password) {
	var data = {
		username: username,
		password: password
	};
	
    $.ajax({
        type: 'POST',
        url: target + '/login',
        username: username,
        password: password,
        contentType: 'application/json',
        async: false,
        data: data,
        success: function (result) {
            setUsername(username);
            setPassword(password);
            var setActivePegawaiAsOperator = function (result) {
                setOperator(result.model);
            }
            loadActivePegawai(setActivePegawaiAsOperator, errorMessage);

            alert('Berhasil Login');
            window.location.href = "dashboard.html";
        },
        error: errorMessage
    });
}
function logout() {
	$.ajax({
		type: 'GET',
		url: target + '/logout',
		username: getUsername(),
		password: getPassword(),
		contentType: 'application/json',
		success: function(result) {
			setUsername('');
			setPassword('');
			setOperator('');

			alert('Berhasil Logout');
		},
		error: function() {
			alert('Kombinasi Username/Password tidak ditemukan');
		}
	});
}
function process(url, data, method, success, error) {
	var _username = getUsername();
	var _password = getPassword();
	
	if (_username !== '' || password !== '') {
		$.ajax({
			type: method,
			url: url,
			username: _username,
			password: _password,
			contentType: 'application/json',
			processData: false,
			async: false,
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
	save(target + '/pegawai/master', data, 'POST', success, error);
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
	load(target + '/pegawai/kode/' + kode, success, error);
}
function loadPegawaiByNama(nama, success, error) {
	load(target + '/pegawai/nama/' + nama, success, error);
}
function loadListPegawaiByKode(kode, page, success, error) {
	load(target + '/pegawai/kode/' + kode + '/page/' + page, success, error)
}
function loadListPegawaiByNama(nama, page, success, error) {
	load(target + '/pegawai/nama/' + nama + '/page/' + page, success, error);
}
function loadAllPegawai(success, error) {
	load(target + '/pegawai', success, error);
}
function savePelanggan(data, success, error) {
	save(target + '/pelanggan/master', data, 'POST', success, error);
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
	load(target + '/pelanggan/kode/' + kode, success, error);
}
function loadListPelangganByKode(kode, page, success, error) {
	load(target + '/pelanggan/kode/' + kode + '/page/' + page, success, error);
}
function loadListPelangganByKodeAndStatus(kode, status, page, success, error) {
	load(target + '/pelanggan/kode/' + kode + '/status/' + status + '/page/' + page, success, error);
}
function loadPelangganByNama(nama, success, error) {
	load(target + '/pelanggan/nama/' + nama, success, error);
}
function loadListPelangganByNama(nama, page, success, error) {
	load(target + '/pelanggan/nama/' + nama + '/page/' + page, success, error);
}
function loadListPelangganByNamaAndStatus(nama, status, page, success, error) {
	load(target + '/pelanggan/nama/' + nama + '/status/' + status + '/page/' + page, success, error);
}
function loadListPelangganByStatus(status, page, success, error) {
	load(target + '/pelanggan/status/' + status + '/page/' + page, success, error);
}
function loadAllPelanggan(success, error) {
	load(target + '/pelanggan', success, error);
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
	load(target + '/pembayaran/pelanggan/kode/' + kode + '/payable', success, error);
}
function loadTagihanByNama(nama, success, error) {
	load(target + '/pembayaran/pelanggan/nama/' + nama + '/payable', success, error);
}
function loadTagihanById(id, success, error) {
	load(target + '/pembayaran/pelanggan/id/' + id + '/payable', success, error);
}
function loadPembayaranById(id, success, error) {
	load(target + '/pembayaran/' + id, success, error);
}
function loadListPembayaranByKodePegawai(kode, tanggalAwal, tanggalAkhir, page, success, error) {
	load(target + '/pembayaran/pegawai/kode/' + kode + '/awal/' + tanggalAwal + '/akhir/' + tanggalAkhir + '/page/' + page, success, error);
}
function loadListPembayaranByNamaPegawai(nama, tanggalAwal, tanggalAkhir, page, success, error) {
	load(target + '/pembayaran/pegawai/nama/' + nama + '/awal/' + tanggalAwal + '/akhir/' + tanggalAkhir + '/page/' + page, success, error);
}
function loadListPembayaranByKodePelanggan(kode, tanggalAwal, tanggalAkhir, page, success, error) {
	load(target + '/pembayaran/pelanggan/kode/' + kode + '/awal/' + tanggalAwal + '/akhir/' + tanggalAkhir + '/page/' + page, success, error);
}
function loadListPembayaranByNamaPelanggan(nama, tanggalAwal, tanggalAkhir, page, success, error) {
	load(target + '/pembayaran/pelanggan/nama/' + nama + '/awal/' + tanggalAwal + '/akhir/' + tanggalAkhir + '/page/' + page, success, error);
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
var studio_icon = 'mages/studio.png';
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
