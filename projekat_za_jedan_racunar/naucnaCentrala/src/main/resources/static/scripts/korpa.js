$(document).ready(
				function() {
					$.ajax({
						url: "https://localhost:9081/korpa/getAll",
						type: "GET",
						contentType: "application/json",
						datatype: 'json',
						crossDomain: true,
				        headers: {  'Access-Control-Allow-Origin': '*' },
				        xhrFields: {
				            withCredentials: true
				         },
						success: function(data){
							
							if(data){
								$("#tabelaRadoviKorpa").append("<tbody>");
								for(i = 0; i < data.length; i++) {
									noviRed =  "<tr><td>"+ data[i].naslovRada
									+ "</td><td>" + data[i].autor
									+ "</td><td>" + data[i].apstrakt
									+ "</td><td>" + data[i].kljucneReci
									+ "</td><td>" + data[i].naucnaOblast
									+ "</td><td>" + data[i].casopisRada
									+ "</td><td>" + data[i].amount;
									
									$("#tabelaRadoviKorpa").append(noviRed);
								}
								$("#tabelaRadoviKorpa").append("</tbody>");
								if(data[0] != null){
									var dugmad = '';
									if(data[0].tipPlacanja.includes("BANKA")){
										dugmad = dugmad + "<input id=\"bankaBtn\" type=\"button\" class=\"btn btn-success\"  value=\"Platna kartica\"  onclick =\"kupiPlatnomKarticom("+data[0].ukupnaCena + ',\'' + data[0].merchant_id+ "\')\">&nbsp";
									}
									if(data[0].tipPlacanja.includes("PAYPAL")){
										dugmad = dugmad + "<input id=\"paypalBtn\" type=\"button\" class=\"btn btn-success\"  value=\"Paypal\"  onclick =\"paypal(" + data[0].merchant_id  + ',\'' + data[0].paypalId +'\',\''+ data[0].paypalSecret+'\','+  data[0].ukupnaCena + ")\">&nbsp"
										
									}
									if(data[0].tipPlacanja.includes("BITCOIN")){
										dugmad = dugmad + "<input id=\"bitcoinBtn\" type=\"button\" class=\"btn btn-success\"  value=\"Bitcoin\"  onclick =\"bitcoin(\'" +data[0].nazivCasopisa + '\',' +  data[0].ukupnaCena + ")\">"
										
									}
									$("#ukupnaCena").append(data[0].ukupnaCena);
									$("#korpaPlacanjeDiv").append(dugmad);
								}else{
									document.getElementById("ukupnaCena").innerHTML = "";
									document.getElementById("nacinPlacanjaLabela").innerHTML = "";								
								}
							}else
								alert("Greška prilikom ucitavanja radova.");
						},
						error: function(data){
							alert('GREŠKA PRILIKOM PREUZIMANJA LISTE NAUČNIH ČASOPISA.')
						}
					});
	});


function kupiPlatnomKarticom(amount,merchant_id) {
	var data = JSON.stringify({
			"merchant_id": merchant_id,
			"amount": amount,
			"merchant_password": "password"
		});
		var path = "banka";
		$.ajax({
			async: false,
			url: "https://localhost:5522/zahtev/"+path,
	        type: "POST",
	        contentType: "application/json",
	        data: data,
	        dataType: "text",
	        crossDomain: true,
	        headers: {  'Access-Control-Allow-Origin': '*' },
	        success: function (data2) {
	        	if(data2!=null){
	        		/*alert("Bićete preusmereni na sledeću adresu:\n" + data2);*/
	        		window.location.href = data2;
	        	}
	        },
	        /*success: function (data) {
	        	window.location.href="https://localhost:9081/banka.html?paymentId="+data.paymentId;	
	        },*/
	        error: function (jqxhr, textStatus, errorThrown) {
	        	alert("GREŠKA PRILIKOM POKUŠAJA PLAĆANJA PREKO BANKE: " + textStatus);
	            
	        }
		});
	}

	function paypal(merchant_id,paypalId, paypalSecret, amount) {
		var data = JSON.stringify({
			"merchant_id": merchant_id,
			"amount": amount,
			"merchant_password": "password",
			"paypalId" : paypalId,
			"paypalSecret" : paypalSecret
		});
		var path = "paypal";
		$.ajax({
			async: false,
			url: "https://localhost:5522/zahtev/" + path,
	        type: "POST",
	        contentType: "application/json",
	        data: data,
	        dataType: "text",
	        crossDomain: true,
	        headers: {  'Access-Control-Allow-Origin': '*' },
	        success: function (data2) {
	        	if(data2!=null){
	        		/*alert("Bićete preusmereni na sledeću adresu:\n" + data2);*/
	        		window.location.href = data2;
	        	}
	        },
	        error: function (jqxhr, textStatus, errorThrown) {
	        	alert("GREŠKA PRILIKOM POKUŠAJA PLAĆANJA PREKO PAYPAL-A: " + textStatus);
	        }
		});
	}

	function bitcoin(naziv, amount) {
		var data = JSON.stringify({
			"merchant_id": naziv,
			"amount": amount
		});
		var path = "bitcoin";
		$.ajax({
			async: false,
			url: "https://localhost:5522/zahtev/" + path,
	        type: "POST",
	        contentType: "application/json",
	        data: data,
	        dataType: "text",
	        crossDomain: true,
	        headers: {  'Access-Control-Allow-Origin': '*' },
	        success: function (data) {
	        	var paymentUrl = data.split(', ')[0];
	        	/*alert("Bićete preusmereni na sledeću adresu:\n" + paymentUrl);*/
	        	window.location.href = paymentUrl;
	        	
	        	var splitovan = data.split(', ');
	        	var podaciZaTransakciju = splitovan[1] + ', ' + splitovan[2] + ', ' + splitovan[3] + ', ' + splitovan[4] + ', ' + splitovan[5] + ', ' + splitovan[6];
	        	//alert('podaciZaTransakciju:\n' + podaciZaTransakciju);
	        	$.ajax({
					async: false,
					url: "http://localhost:8762/bitcoin-service/bitcoinTransakcija/dodaj",
			        type: "POST",
			        contentType: "application/json",
			        data: podaciZaTransakciju,
			        dataType: "text",
			        crossDomain: true,
			        headers: {  'Access-Control-Allow-Origin': '*' },
			        success: function (transakcija) {
			        	//alert('transakcija: ' + transakcija);
			        	//alert('Transakcija je uspešno kreirana.');
			        },
			        error: function (jqxhr, textStatus, errorThrown) {
			        	alert('GREŠKA PRILIKOM POKUŠAJA KREIRANJA TRANSAKCIJE.');
			        }
				});
	        },
	        error: function (jqxhr, textStatus, errorThrown) {
	        	alert("GREŠKA PRILIKOM POKUŠAJA PLAĆANJA POMOĆU BITCOIN-A: " + textStatus);
	        }
		});
		
	}
			