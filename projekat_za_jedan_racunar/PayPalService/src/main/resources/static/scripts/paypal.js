$(document).ready(function () 
	{
		var lokacija = window.location.href;
		var splitovanaLokacija = lokacija.split('?');
		var nameValueParoviZajedno = splitovanaLokacija[1];
		var nameValueParoviRazdvojeno = nameValueParoviZajedno.split('&');
		if(nameValueParoviRazdvojeno.length > 1){
			var payPalId = nameValueParoviRazdvojeno[0].split('=')[1];
			var payPalSecret = nameValueParoviRazdvojeno[1].split('=')[1];
			
			var paymentId = nameValueParoviRazdvojeno[2].split('=')[1];
			var token = nameValueParoviRazdvojeno[3].split('=')[1];
			var payerId = nameValueParoviRazdvojeno[4].split('=')[1];
			
			var stringZaServer = payPalId + "," + payPalSecret + "," + paymentId + "," + token + "," + payerId;
			
			$.ajax({
					async: false,
					url: "http://localhost:8762/paypal-service/paypal/completePayment",
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