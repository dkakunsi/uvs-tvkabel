		  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

		  <style type="text/css">
      		#map-canvas { height: 700px; margin: 0; padding: 0; border: 1;}
		  </style>

		  <script type="text/javascript"
    		src="https://maps.googleapis.com/maps/api/js?region=IN&language=in&key=AIzaSyCw_kA9o3rJ3PUiOixAMkCMP-OJivVhMPY">
    	  </script>
    	  <script type="text/javascript">
    	    var studio_icon = '/tvkabel/resources/images/studio.png';
    	    var aktif_icon = '/tvkabel/resources/images/aktif.png';
    	    var berhenti_icon = '/tvkabel/resources/images/berhenti.png';
    	    var putus_icon = '/tvkabel/resources/images/putus.png';
    	    var warning_icon = '/tvkabel/resources/images/warning.png';
    	    var unitedvision_icon = '/tvkabel/resources/images/unitedvision.png';
    	    
    	    function getIcon(status) {
        		switch(status) {
					case 'aktif': return aktif_icon;
    				case 'berhenti':return berhenti_icon;
    				case 'putus': return putus_icon;
    			}
    	    }

    	    function setMarker(map, location, image, title) {
    	    	var marker = new google.maps.Marker({
          		   	position: location,
          		   	map: map,
          		   	icon: image,
          		   	title: title
          		});
      		}
    	    
    	    function getMap() {
    			var lat = $ ('#lat').val();
    			var lng = $ ('#long').val();
    			var location = new google.maps.LatLng(lat, lng);

    			var mapOptions = {
					center: location,
					zoom: 16
			    };

				return new google.maps.Map(
	            	document.getElementById(
	                	'map-canvas'
					),
	                mapOptions
				);
    	    }
    	    
    	    function setUnitedVisionMap(map) {
				var location = new google.maps.LatLng(1.502444, 124.915389);
        		setMarker(map, location, unitedvision_icon, 'United Vision');
    	    }
    	    
    	    function setPerusahaanMap(map) {
    			var lat = $ ('#lat').val();
    			var lng = $ ('#long').val();
        		if (lat != 0 && lng !=0 ) {
        			var location = new google.maps.LatLng(lat, lng);
            		setMarker(map, location, studio_icon, 'Studio Global Vision');
        		}
    	    }

    		function initialize() {
    			var map = getMap();

				setUnitedVisionMap(map);
				setPerusahaanMap(map);
      		}
      		google.maps.event.addDomListener(window, 'load', initialize);
      		
	        $ (document).ready(function() {
	        	$ ('#btn-aktif').click(function() {
	        		var state = $ ('#aktif-state').val();
	        		
	        		if (state == 0) {
	        			$ ('#aktif-state').val(1);
	        			$ ('#berhenti-state').val(0);
	        			$ ('#putus-state').val(0);

	        			$ ('#btn-aktif').addClass('btn-success').removeClass('btn-default');
			    		$ ('#btn-berhenti').addClass('btn-default').removeClass('btn-success');
			    		$ ('#btn-putus').addClass('btn-default').removeClass('btn-success');

			    		loadPelangganMap('aktif');
	        		}
	        	});
	        	
	        	$ ('#btn-berhenti').click(function() {
	        		var state = $ ('#berhenti-state').val();
	        		
	        		if (state == 0) {
	        			$ ('#aktif-state').val(0);
	        			$ ('#berhenti-state').val(1);
	        			$ ('#putus-state').val(0);

	        			$ ('#btn-aktif').addClass('btn-default').removeClass('btn-success');
			    		$ ('#btn-berhenti').addClass('btn-success').removeClass('btn-default');
			    		$ ('#btn-putus').addClass('btn-default').removeClass('btn-success');

			    		loadPelangganMap('berhenti');
	        		}
	        	});

	        	$ ('#btn-putus').click(function() {
	        		var state = $ ('#putus-state').val();
	        		
	        		if (state == 0) {
	        			$ ('#aktif-state').val(0);
	        			$ ('#berhenti-state').val(0);
	        			$ ('#putus-state').val(1);

	        			$ ('#btn-aktif').addClass('btn-default').removeClass('btn-success');
			    		$ ('#btn-berhenti').addClass('btn-default').removeClass('btn-success');
			    		$ ('#btn-putus').addClass('btn-success').removeClass('btn-default');

			    		loadPelangganMap('putus');
	        		}
	        	});

	        	$ ('#btn-tampilkan').click(function() {
		    		var query = $ ('#query').val();

		    		if (query !== '') {
	        			$ ('#aktif-state').val(0);
	        			$ ('#berhenti-state').val(0);
	        			$ ('#putus-state').val(0);

	        			$ ('#btn-aktif').addClass('btn-default').removeClass('btn-success');
			    		$ ('#btn-berhenti').addClass('btn-default').removeClass('btn-success');
			    		$ ('#btn-putus').addClass('btn-default').removeClass('btn-success');

			        	$.ajax({
			        		type: 'GET',
			        		url: '/tvkabel/pelanggan/nama/' + query,
			        		contentType: 'application/json',
			        		success: function (result) {
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
			        		},
			        		error: function (jqXHR, textStatus, errorThrown) {
			        		  alert('Error Occured ' + textStatus + ' ' + errorThrown);
			        		}
			        	});
		    		} else {
		    			alert('Silahkan Memasukan Nama Pelanggan!');
		    		}
	        	});
	        })
	        
	        function loadPelangganMap(status) {
	        	$.ajax({
	        		type: 'GET',
	        		url: '/tvkabel/pelanggan/status/' + status,
	        		contentType: 'application/json',
	        		success: function (result) {
	        			setPelangganMap(result, status);
	        		},
	        		error: function (jqXHR, textStatus, errorThrown) {
	        		  alert('Error Occured ' + textStatus + ' ' + errorThrown);
	        		}
	        	});
	        }
	        
	        function setPelangganMap(result, status) {
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
    	  </script>

		  <input type="hidden" id="lat" value="${perusahaan.alamat.latitude }" />
		  <input type="hidden" id="long" value="${perusahaan.alamat.longitude }" />
		  <input type="hidden" id="aktif-state" value="0" />
		  <input type="hidden" id="berhenti-state" value="0" />
		  <input type="hidden" id="putus-state" value="0" />
          <div class="panel panel-default">
			<!-- Default panel contents -->
		    <div class="panel-heading">Peta Pelanggan</div>
			<div class="panel-body">
			  <div class="row placeholder">
	            <table class="table"> <!-- Bootstrap's table -->
	              <tr>
	                <th class="text-center">Tampilkan Menurut Status</th>
	                <th class="text-center">Tampilkan Pelanggan</th>
	              </tr>
				  <tr>
				    <td width="300">
					  <div class="btn-group">
						<button type="button" class="btn btn-default" id="btn-aktif">Aktif</button>
						<button type="button" class="btn btn-default" id="btn-berhenti">Berhenti</button>
						<button type="button" class="btn btn-default" id="btn-putus">Putus</button>
					  </div>
				    </td>
				   	<td>
			    	  <div class="input-group">
			       	    <input type="text" class="form-control" id="query" placeholder="Masukan Nama Pelanggan">
			       		<span class="input-group-btn">
			       	  	  <button class="btn btn-primary" type="submit" id="btn-tampilkan">Tampilkan!</button>
			    		</span>
			       	  </div>
				   	</td>
				  </tr>
				</table>
			  </div>
			  <div class="row">
				  <div id="map-canvas"></div>
			  </div>
		    </div><!-- panel-body -->
          </div><!-- panel -->
