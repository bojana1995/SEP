$(document).ready(function () 
	{
		var lokacija = window.location.href;
		var splitovanaLokacija = lokacija.split('?');
		var nameValueParoviZajedno = splitovanaLokacija[1];
		var nameValueParoviRazdvojeno = nameValueParoviZajedno.split('&');
		var payPalId, payPalSecret, token;
		if(nameValueParoviRazdvojeno.length > 2){
			payPalId = nameValueParoviRazdvojeno[0].split('=')[1];
	
			payPalSecret = nameValueParoviRazdvojeno[1].split('=')[1];
			token = nameValueParoviRazdvojeno[2].split('=')[1];
			
			var stringZaServer = payPalId + "," + payPalSecret + "," + token;
			
			$.ajax({
					async: false,
					url: "http://localhost:8762/paypal-service/paypal/executePayment",
			        type: "POST",
			        contentType: "text/plain",
			        data: stringZaServer,
			        crossDomain: true,
			        headers: {  'Access-Control-Allow-Origin': '*' },
			        success: function () {
			        	
			        },
			        error: function (jqxhr, textStatus, errorThrown) {
			        	alert("juhuuuuuuu");
			            
			        }
				});
		}
		
	
	});