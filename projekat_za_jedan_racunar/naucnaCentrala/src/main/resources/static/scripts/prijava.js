$(document).ready(function () 
{
	$("#dugmePrijaviSe").click(function(){
		var provera = true;
		var forma = $('form[id="formaPrijaviSe"]');
		var email = forma.find('[name=email]').val();
		var lozinka = forma.find('[name=lozinka]').val();
		
		if(!email) {
			$('#divValidacija').empty();
			$('#divValidacija').append('<p style="color:red"><b>Morate uneti e-mail adresu.</b></p>');
			provera = false;
		}
		if(!lozinka) {
			$('#divValidacija').empty();
			$('#divValidacija').append('<p style="color:red"><b>Morate uneti lozinku.</b></p>');
			provera = false;
		}				
		
		if(provera) {
			$('#divValidacija').empty();	
			formData = JSON.stringify({
				email:$("#formaPrijaviSe [name='email']").val(),
				lozinka:$("#formaPrijaviSe [name='lozinka']").val()
			   });
			
			$.ajax({
				url: "https://localhost:9081/korisnik/prijava",
				type: "POST",
				data: formData,
				contentType: "application/json",
				datatype: 'json',
				crossDomain: true,
		        headers: {  'Access-Control-Allow-Origin': '*' },
		        xhrFields: {
		            withCredentials: true
		         },
				success: function(data){
					if(data){
						location.href = "https://localhost:9081/homePage.html"
							$.ajax({
								url: "https://localhost:9081/korpa/deleteAll",
								type: "GET",
								contentType: "application/json",
								datatype: 'json',
								crossDomain: true,
						        headers: {  'Access-Control-Allow-Origin': '*' },
						        xhrFields: {
						            withCredentials: true
						         },
								success: function(){
									
								},
								error: function(data){
									alert('GREŠKA PRILIKOM KORPE')
								}
							});	
					}else
						alert('Prijava na sistem naučne centrale nije uspela.');
				},
				error: function(data){
					alert('Ne postoji korisnik sa tom kombinacijom e-mail adrese i lozinke.');
				}
			});
		}
	});	
	
	$("#dugmeOdjaviSe").click(function(){
			
			$.ajax({
				url: "https://localhost:9081/korisnik/odjava",
				type: "GET",
				datatype: 'json',
				crossDomain: true,
		        headers: {  'Access-Control-Allow-Origin': '*' },
		        xhrFields: {
		            withCredentials: true
		         },
				success: function(data){
					window.location.href = "https://localhost:9081";
				},
				error: function(data){
					alert('Nije uspela odjava.');
				}
			});
		
	});	
});