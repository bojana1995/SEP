$(document).ready(function(){
				$.ajax({
					url : "https://localhost:9081/korisnik/dekriptovanjeEmailAdrese",
					crossDomain: true,
			        headers: {  'Access-Control-Allow-Origin': '*' },
			        xhrFields: {
			            withCredentials: true
			         }
				}).then(function(data){
					var decryptedString = data;
				});
				
				$.ajax({
					url : "https://localhost:9081/korisnik/getTrenutnoAktivan",
					type: "GET",
					crossDomain: true,
			        headers: {  'Access-Control-Allow-Origin': '*' },
			        xhrFields: {
			            withCredentials: true
			         },
					success: function(data){
						if(data.rola.id == 1){ //ADMIN
							$("#dugmeDodajCasopis").show();
							$("#dugmeDodajNaucnuOblast").show();
							$("#dugmeDodajRad").hide();
						}else if(data.rola.id == 7){ //CITALAC
							$("#dugmeDodajCasopis").hide();
							$("#dugmeDodajNaucnuOblast").hide();
							$("#dugmeDodajRad").hide();
						}else{
							$("#dugmeDodajCasopis").hide();
							$("#dugmeDodajNaucnuOblast").hide();
							$("#dugmeDodajRad").show();
						}
					},
					error: function(data){
						alert('GREŠKA PRILIKOM PRIKAZIVANJA DUGMIĆA.');
					}
				}).then(function(data){
					var imeKor = "<tr><th scope=\"col\">" + data.ime + "</th></tr>";
					var prezimeKor = "<tr><th scope=\"col\">" + data.prezime + "</th></tr>";
					//var emailKor = "<tr><th scope=\"col\">" + data.email + "</th></tr>";
					var gradKor = "<tr><th scope=\"col\">" + data.grad + "</th></tr>";
					var drzavaKor = "<tr><th scope=\"col\">" + data.drzava + "</th></tr>";
					var titulaKor = "<tr><th scope=\"col\">" + data.titula + "</th></tr>";
					
					$("#tabelaInformacijeOKorisniku").append(imeKor);
					$("#tabelaInformacijeOKorisniku").append(prezimeKor);
					//$("#tabelaInformacijeOKorisniku").append(emailKor);
					$("#tabelaInformacijeOKorisniku").append(gradKor);
					$("#tabelaInformacijeOKorisniku").append(drzavaKor);
					$("#tabelaInformacijeOKorisniku").append(titulaKor);
				});
				
				$.ajax({
					url : "https://localhost:9081/korisnik/getTrenutnoAktivan",
					type: "GET",
					crossDomain: true,
			        headers: {  'Access-Control-Allow-Origin': '*' },
			        xhrFields: {
			            withCredentials: true
			         }
				}).then(function(korisnik){
					$.ajax({
						url: "https://localhost:9081/naucnaOblast/getNaucneOblasti",
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
								if(data.length == 0){
									red = "Trenutno nema dostupnih naučnih oblasti.";
									$("#labelaNaucneOblasti").append(red);
									$("#labelaNaucneOblasti").show();
								}else{
									if(korisnik.rola.id != 1){ //svi ostali
										red = "Trenutno dostupne naučne oblasti:";
										$("#labelaNaucneOblasti").append(red);
										$("#labelaNaucneOblasti").show();
										$("#tabelaNaucneOblasti").append("<thead class=\"zaglavljeTabele\"><tr><th scope=\"col\" class=\"text-center\">Naziv</th><th scope=\"col\" class=\"text-center\">Opis</th></tr></thead><tbody>");
									
										for(i = 0; i < data.length; i++) {
											noviRed =  "<tr><td>"+ data[i].naziv 
													+ "</td><td>" + data[i].opis;									
											$("#tabelaNaucneOblasti").append(noviRed);
										}
									} else { //ADMIN
										red = "Trenutno dostupne naučne oblasti:";
										$("#labelaNaucneOblasti").append(red);
										$("#labelaNaucneOblasti").show();
										$("#tabelaNaucneOblasti").append("<thead class=\"zaglavljeTabele\"><tr><th scope=\"col\" class=\"text-center\">Naziv</th><th scope=\"col\" class=\"text-center\">Opis</th><th scope=\"col\" class=\"text-center\" colspan=\"2\"></th></tr></thead><tbody>");
									
										for(i = 0; i < data.length; i++) {
											noviRed =  "<tr><td>"+ data[i].naziv 
													+ "</td><td>" + data[i].opis
													+ "</td><td>" + "<input id=\"dugmeAzurirajNaucnuOblast\" type=\"button\" class=\"btn btn-warning\" style=\"float:right\" value=\"Ažuriraj\" data-toggle=\"modal\" data-target=\"#modalniAzurirajNaucnuOblast\" onclick =\"azurirajNaucnuOblast(" + data[i].id + ")\">"
													+ "</td><td>" + "<input id=\"dugmeObrisiNaucnuOblast\" type=\"button\" class=\"btn btn-danger\" style=\"float:right\" value=\"Obriši\" onclick =\"obrisiNaucnuOblast(" + data[i].id + ")\">";									
											$("#tabelaNaucneOblasti").append(noviRed);
										}
									}
									$("#tabelaNaucneOblasti").append("</tbody>");
								}
							}else
								alert("Greška prilikom pokušaja prikazivanja liste naučnih oblasti.");
						},
						error: function(data){
							alert('GREŠKA PRILIKOM PREUZIMANJA LISTE NAUČNIH OBLASTI.')
						}
					});
				});
				
				$.ajax({
					url : "https://localhost:9081/korisnik/getTrenutnoAktivan",
					type: "GET",
					crossDomain: true,
			        headers: {  'Access-Control-Allow-Origin': '*' },
			        xhrFields: {
			            withCredentials: true
			         }
				}).then(function(korisnik){
					$.ajax({
						url: "https://localhost:9081/casopis/getCasopisi",
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
								if(data.length == 0){
									red = "Trenutno nema dostupnih naučnih časopisa.";
									$("#labelaCasopisi").append(red);
									$("#labelaCasopisi").show();
								}else{
									if(korisnik.rola.id == 1){ //ADMIN
										$("#divKorpa").hide();
										red = "Trenutno dostupni naučni časopisi:";
										$("#labelaCasopisi").append(red);
										$("#labelaCasopisi").show();
										$("#tabelaCasopisi").append("<thead class=\"zaglavljeTabele\"><tr><th scope=\"col\" class=\"text-center\">ISSN broj</th><th scope=\"col\" class=\"text-center\">Naziv</th><th scope=\"col\" class=\"text-center\">Iznos članarine</th><th scope=\"col\" class=\"text-center\">Tip časopisa</th><th scope=\"col\" class=\"text-center\" colspan=\"2\"></th></tr></thead><tbody>");
										for(i = 0; i < data.length; i++) {
											noviRed =  "<tr><td>"+ data[i].issnbroj 
													+ "</td><td>" + data[i].naziv
													+ "</td><td>" + data[i].amount
													+ "</td><td>" + data[i].tipCasopisa
													+ "</td><td>" + "<input id=\"dugmeAzurirajCasopis\" type=\"button\" class=\"btn btn-warning\" style=\"float:right\" value=\"Ažuriraj\" data-toggle=\"modal\" data-target=\"#modalniAzurirajCasopis\" onclick =\"azurirajCasopis(" + data[i].id + ")\">"
													+ "</td><td>" + "<input id=\"dugmeObrisiCasopis\" type=\"button\" class=\"btn btn-danger\" style=\"float:right\" value=\"Obriši\" onclick =\"obrisiCasopis(" + data[i].id + ")\">";
											
											$("#tabelaCasopisi").append(noviRed);
										}
									} else if(korisnik.rola.id == 2 || korisnik.rola.id == 3 || korisnik.rola.id == 4){ //GLAVNI_UREDNIK , Urednik i recenzent
										red = "Trenutno dostupni naučni časopisi:";
										$("#labelaCasopisi").append(red);
										$("#labelaCasopisi").show();
										$("#tabelaCasopisi").append("<thead class=\"zaglavljeTabele\"><tr><th scope=\"col\" class=\"text-center\">ISSN broj</th><th scope=\"col\" class=\"text-center\">Naziv</th><th scope=\"col\" class=\"text-center\">Iznos članarine</th><th scope=\"col\" class=\"text-center\">Tip časopisa</th><th scope=\"col\" class=\"text-center\" colspan=\"2\"></th></tr></thead><tbody>");
										for(i = 0; i < data.length; i++) {
											noviRed =  "<tr><td>"+ data[i].issnbroj 
													+ "</td><td>" + data[i].naziv
													+ "</td><td>" + data[i].amount
													+ "</td><td>" + data[i].tipCasopisa
													+ "</td><td>" + "<input id=\"dugmeAzurirajCasopis\" type=\"button\" class=\"btn btn-warning\" style=\"float:right\" value=\"Ažuriraj\" data-toggle=\"modal\" data-target=\"#modalniAzurirajCasopis\" onclick =\"azurirajCasopis(" + data[i].id + ")\">"
													+ "</td><td>" + "<input id=\"dugmeObrisiCasopis\" type=\"button\" class=\"btn btn-danger\" style=\"float:right\" value=\"Obriši\" onclick =\"obrisiCasopis(" + data[i].id + ")\">";
											$("#tabelaCasopisi").append(noviRed);
										}
									} else if(korisnik.rola.id == 5){ //AUTOR
										$("#divKorpa").hide();
										red = "Trenutno dostupni naučni časopisi:";
										$("#labelaCasopisi").append(red);
										$("#labelaCasopisi").show();
										$("#tabelaCasopisi").append("<thead class=\"zaglavljeTabele\"><tr><th scope=\"col\" class=\"text-center\">ISSN broj</th><th scope=\"col\" class=\"text-center\">Naziv</th><th scope=\"col\" class=\"text-center\">Iznos članarine</th><th scope=\"col\" class=\"text-center\">Tip časopisa</th></th></tr></thead><tbody>");
										for(i = 0; i < data.length; i++) {
											//if(data[i].tipCasopisa == "OPEN_ACCESS"){
												noviRed =  "<tr><td>"+ data[i].issnbroj 
												+ "</td><td>" + data[i].naziv
												+ "</td><td>" + data[i].amount
												+ "</td><td>" + data[i].tipCasopisa;
												//+ "</td><td>" + "<input id=\"dugmeAzurirajCasopis\" type=\"button\" class=\"btn btn-warning\" style=\"float:right\" value=\"Ažuriraj\" data-toggle=\"modal\" data-target=\"#modalniAzurirajCasopis\" onclick =\"azurirajCasopis(" + data[i].id + ")\">";
												//+ "</td><td>" + "<input id=\"dugmeObrisiCasopis\" type=\"button\" class=\"btn btn-danger\" style=\"float:right\" value=\"Obriši\" onclick =\"obrisiCasopis(" + data[i].id + ")\">";
												/*if(data[i].tipoviPlacanja.includes("BANKA")){
													noviRed =  noviRed + "</td><td>" + "<input id=\"dugmeKupiCasopis\" type=\"button\" class=\"btn btn-success\" style=\"float:right\" value=\"Kartica\" onclick =\"kupiCasopis("+data[i].id+',' + data[i].amount + ',\'' + data[i].merchant_password+ "\')\">";
												}if(data[i].tipoviPlacanja.includes("PAYPAL")){
													noviRed =  noviRed + "</td><td>" + "<input id=\"dugmePayPalCasopis\" type=\"button\" class=\"btn btn-success\" style=\"float:right\" value=\"PayPal\" onclick =\"paypal("+data[i].id+',\'' + data[i].paypalId + '\',\''+ data[i].paypalSecret + '\',' + data[i].amount + ',\'' + data[i].merchant_password+ "\')\">";														
												}if(data[i].tipoviPlacanja.includes("BITCOIN")){
													noviRed= noviRed	+ "</td><td>" + "<input id=\"dugmeBitcoinCasopis\" type=\"button\" class=\"btn btn-success\" style=\"float:right\" value=\"Bitcoin\" onclick =\"bitcoin(\'"+ data[i].naziv + '\'' + ',\'' + data[i].amount+ "\')\">";
													
												}*/
												//noviRed = noviRed + "</td><td>" + "<input id=\"dugmeKupiCasopis\" type=\"button\" class=\"btn btn-success\" style=\"float:right\" value=\"Kupi\" onclick =\"kupiCasopis("+data[i].merchant_id+',' + data[i].amount + ',\'' + data[i].merchant_password+ "\')\">";
												
												$("#tabelaCasopisi").append(noviRed);
											//}
										}
									} else if(korisnik.rola.id == 6){ //KOAUTOR
										red = "Trenutno dostupni naučni časopisi:";
										$("#labelaCasopisi").append(red);
										$("#labelaCasopisi").show();
										$("#tabelaCasopisi").append("<thead class=\"zaglavljeTabele\"><tr><th scope=\"col\" class=\"text-center\">ISSN broj</th><th scope=\"col\" class=\"text-center\">Naziv</th><th scope=\"col\" class=\"text-center\">Iznos članarine</th><th scope=\"col\" class=\"text-center\">Tip časopisa</th><th scope=\"col\" class=\"text-center\" colspan=\"2\"></th></tr></thead><tbody>");
										for(i = 0; i < data.length; i++) {
											noviRed =  "<tr><td>"+ data[i].issnbroj 
													+ "</td><td>" + data[i].naziv
													+ "</td><td>" + data[i].amount
													+ "</td><td>" + data[i].tipCasopisa
													+ "</td><td>" + "<input id=\"dugmeAzurirajCasopis\" type=\"button\" class=\"btn btn-warning\" style=\"float:right\" value=\"Ažuriraj\" data-toggle=\"modal\" data-target=\"#modalniAzurirajCasopis\" onclick =\"azurirajCasopis(" + data[i].id + ")\">"
													+ "</td><td>" + "<input id=\"dugmeObrisiCasopis\" type=\"button\" class=\"btn btn-danger\" style=\"float:right\" value=\"Obriši\" onclick =\"obrisiCasopis(" + data[i].id + ")\">";
											$("#tabelaCasopisi").append(noviRed);
										}
									} else { //CITALAC
										red = "Trenutno dostupni naučni časopisi:";
										$("#labelaCasopisi").append(red);
										$("#labelaCasopisi").show();
										$("#tabelaCasopisi").append("<thead class=\"zaglavljeTabele\"><tr><th scope=\"col\" class=\"text-center\">ISSN broj</th><th scope=\"col\" class=\"text-center\">Naziv</th><th scope=\"col\" class=\"text-center\">Iznos članarine</th><th scope=\"col\" class=\"text-center\">Tip časopisa</th><th scope=\"col\" colspan=\"3\" class=\"text-center\"></th></th></tr></thead><tbody>");
										for(i = 0; i < data.length; i++) {
											if(data[i].tipCasopisa == "SA_PRETPLATOM"){
												var payPalSubscribeBtn = "";
												noviRed =  "<tr><td>"+ data[i].issnbroj 
												+ "</td><td>" + data[i].naziv
												+ "</td><td>" + data[i].amount
												+ "</td><td>" + data[i].tipCasopisa;
												if(data[i].tipoviPlacanja.includes("BANKA")){
													noviRed = noviRed + "</td><td>" + "<input id=\"dugmeKupiCasopis\" type=\"button\" class=\"btn btn-success\" style=\"float:right\" value=\"Kartica\" onclick =\"kupiCasopis("+data[i].merchantId+',' + data[i].amount + ',\'' + data[i].merchantPassword+ "\')\">";
												}
												if(data[i].tipoviPlacanja.includes("PAYPAL")){
													noviRed = noviRed  + "</td><td>" + "<input id=\"dugmePayPalCasopis\" type=\"button\" class=\"btn btn-success\" style=\"float:right\" value=\"PayPal\" onclick =\"paypal("+data[i].id+',\'' + data[i].paypalId + '\',\''+ data[i].paypalSecret + '\',' + data[i].amount + ',\'' + data[i].merchantPassword+ "\')\">";
													noviRed = noviRed + "<input id=\"dugmePayPalPretplataCasopis\" type=\"button\" class=\"btn btn-success\" style=\"float:right\" value=\"PayPal pretplata\" onclick =\"paypalPretplata("+data[i].id+',\'' + data[i].paypalId + '\',\''+ data[i].paypalSecret + '\',' + data[i].amount + ")\">";
												}
												if(data[i].tipoviPlacanja.includes("BITCOIN")){
													noviRed = noviRed  + "</td><td>" + "<input id=\"dugmeBitcoinCasopis\" type=\"button\" class=\"btn btn-success\" style=\"float:right\" value=\"Bitcoin\" onclick =\"bitcoin(\'"+ data[i].naziv + '\'' + ',\'' + data[i].amount+ "\')\">";
												}
												//noviRed = noviRed + "</td><td>" + "<input id=\"dugmeKupiCasopis\" type=\"button\" class=\"btn btn-success\" style=\"float:right\" value=\"Pretplati se\" onclick =\"kupiCasopis("+data[i].merchant_id+',' + data[i].amount + ',\'' + data[i].merchant_password+ "\')\">";
												
												$("#tabelaCasopisi").append(noviRed);	
											}	
										}									
									}
								$("#tabelaCasopisi").append("</tbody>");
								}
							}else
								alert("Greška prilikom pokušaja prikazivanja liste naučnih časopisa.");
						},
						error: function(data){
							alert('GREŠKA PRILIKOM PREUZIMANJA LISTE NAUČNIH ČASOPISA.')
						}
					});
				});
			});
			
			//cccccccccccccccccccccccccccccccccccccccccccccccccccccc
			$.ajax({
					url : "https://localhost:9081/korisnik/getTrenutnoAktivan",
					type: "GET",
					crossDomain: true,
			        headers: {  'Access-Control-Allow-Origin': '*' },
			        xhrFields: {
			            withCredentials: true
			         }
				}).then(function(korisnik){
					$.ajax({
						url: "https://localhost:9081/rad/getRadovi",
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
								if(data.length == 0){
									red = "Trenutno nema dostupnih naučnih radova.";
									$("#labelaRadovi").append(red);
									$("#labelaRadovi").show();
								}else{
									if(korisnik.rola.id == 1){ //ADMIN
										red = "Trenutno dostupni naučni radovi:";
										$("#labelaRadovi").append(red);
										$("#labelaRadovi").show();
										$("#tabelaRadovi").append("<tbody>");
										for(i = 0; i < data.length; i++) {
											noviRed =  "<tr><td>"+ data[i].naslovRada
											+ "</td><td>" + data[i].autor
											+ "</td><td>" + data[i].apstrakt
											+ "</td><td>" + data[i].kljucneReci
											+ "</td><td>" + data[i].naucnaOblast
											+ "</td><td>" + data[i].casopisRada
											+ "</td><td>" + data[i].amount
													+ "</td><td>" + "<input id=\"dugmeAzurirajRad\" type=\"button\" class=\"btn btn-warning\" style=\"float:right\" value=\"Ažuriraj\" data-toggle=\"modal\" data-target=\"#modalniAzurirajRad\" onclick =\"azurirajRad(" + data[i].id + ")\">"
													+ "</td><td>" + "<input id=\"dugmeObrisiRad\" type=\"button\" class=\"btn btn-danger\" style=\"float:right\" value=\"Obriši\" onclick =\"obrisiRad(" + data[i].id + ")\"/> </td></tr>";										
											$("#tabelaRadovi").append(noviRed);
										}
									} else if(korisnik.rola.id == 2){ //GLAVNI_UREDNIK
										red = "Trenutno dostupni naučni radovi:";
										$("#labelaRadovi").append(red);
										$("#labelaRadovi").show();
										$("#tabelaRadovi").append("<thead class=\"zaglavljeTabele\"><tr><th scope=\"col\" class=\"text-center\">Naslov</th><th scope=\"col\" class=\"text-center\">Autor</th><th scope=\"col\" class=\"text-center\">Apstrakt</th><th scope=\"col\" class=\"text-center\">Ključni pojmovi</th><th scope=\"col\" class=\"text-center\">Naučna oblast</th><th scope=\"col\" class=\"text-center\">Status</th><th scope=\"col\" class=\"text-center\" colspan=\"2\"></th></tr></thead><tbody>");
										for(i = 0; i < data.length; i++) {
											noviRed =  "<tr><td>"+ data[i].naslov
													+ "</td><td>" + data[i].autor.ime + " " + data[i].autor.prezime
													+ "</td><td>" + data[i].apstrakt
													+ "</td><td>" + data[i].kljucniPojmovi
													+ "</td><td>" + data[i].naucnaOblast.naziv
													+ "</td><td>" + data[i].statusRada
													+ "</td><td>" + "<input id=\"dugmeAzurirajRad\" type=\"button\" class=\"btn btn-warning\" style=\"float:right\" value=\"Ažuriraj\" data-toggle=\"modal\" data-target=\"#modalniAzurirajRad\" onclick =\"azurirajRad(" + data[i].id + ")\">"
													+ "</td><td>" + "<input id=\"dugmeObrisiRad\" type=\"button\" class=\"btn btn-danger\" style=\"float:right\" value=\"Obriši\" onclick =\"obrisiRad(" + data[i].id + ")\">";										
											$("#tabelaRadovi").append(noviRed);
										}
									} else if(korisnik.rola.id == 3){ //UREDNIK
										red = "Trenutno dostupni naučni radovi:";
										$("#labelaRadovi").append(red);
										$("#labelaRadovi").show();
										$("#tabelaRadovi").append("<tbody>");
										for(i = 0; i < data.length; i++) {
											noviRed =  "<tr><td>"+ data[i].naslovRada
											+ "</td><td>" + data[i].autor
											+ "</td><td>" + data[i].apstrakt
											+ "</td><td>" + data[i].kljucneReci
											+ "</td><td>" + data[i].naucnaOblast
											+ "</td><td>" + data[i].casopisRada
													+ "</td><td>" + "<input id=\"dugmeAzurirajRad\" type=\"button\" class=\"btn btn-warning\" style=\"float:right\" value=\"Ažuriraj\" data-toggle=\"modal\" data-target=\"#modalniAzurirajRad\" onclick =\"azurirajRad(" + data[i].id + ")\">"
													+ "</td><td>" + "<input id=\"dugmeObrisiRad\" type=\"button\" class=\"btn btn-danger\" style=\"float:right\" value=\"Obriši\" onclick =\"obrisiRad(" + data[i].id + ")\"/> </td></tr>";										
											$("#tabelaRadovi").append(noviRed);
										}
									} else if(korisnik.rola.id == 4){ //RECENZENT
										red = "Trenutno dostupni naučni radovi:";
										$("#labelaRadovi").append(red);
										$("#labelaRadovi").show();
										$("#tabelaRadovi").append("<tbody>");
										for(i = 0; i < data.length; i++) {
											noviRed =  "<tr><td>"+ data[i].naslovRada
											+ "</td><td>" + data[i].autor
											+ "</td><td>" + data[i].apstrakt
											+ "</td><td>" + data[i].kljucneReci
											+ "</td><td>" + data[i].naucnaOblast
											+ "</td><td>" + data[i].casopisRada
													+ "</td><td>" + "<input id=\"dugmeAzurirajRad\" type=\"button\" class=\"btn btn-warning\" style=\"float:right\" value=\"Ažuriraj\" data-toggle=\"modal\" data-target=\"#modalniAzurirajRad\" onclick =\"azurirajRad(" + data[i].id + ")\">"
													+ "</td><td>" + "<input id=\"dugmeObrisiRad\" type=\"button\" class=\"btn btn-danger\" style=\"float:right\" value=\"Obriši\" onclick =\"obrisiRad(" + data[i].id + ")\">";										
											$("#tabelaRadovi").append(noviRed);
										}
									} else if(korisnik.rola.id == 5){ //AUTOR
										red = "Trenutno dostupni naučni radovi:";
										$("#labelaRadovi").append(red);
										$("#labelaRadovi").show();
										$("#tabelaRadovi").append("<tbody>");
										for(i = 0; i < data.length; i++) {
											noviRed =  "<tr><td>"+ data[i].naslovRada
											+ "</td><td>" + data[i].autor
											+ "</td><td>" + data[i].apstrakt
											+ "</td><td>" + data[i].kljucneReci
											+ "</td><td>" + data[i].naucnaOblast
											+ "</td><td>" + data[i].casopisRada
											+ "</td><td>" + data[i].amount;
											var username = korisnik.ime + " "+ korisnik.prezime;
											if(data[i].autor == username){
												noviRed = noviRed + "</td><td>" + "<input id=\"dugmeAzurirajRad\" type=\"button\" class=\"btn btn-warning\" style=\"float:right\" value=\"Ažuriraj\" data-toggle=\"modal\" data-target=\"#modalniAzurirajRad\" onclick =\"azurirajRad(" + data[i].id + ")\">"
												+ "</td><td>" + "<input id=\"dugmeObrisiRad\" type=\"button\" class=\"btn btn-danger\" style=\"float:right\" value=\"Obriši\" onclick =\"obrisiRad(" + data[i].id + ")\"/> </td></tr>";										
									
											}else{
												noviRed = noviRed + "</td><td></td></tr>";
											}
											$("#tabelaRadovi").append(noviRed);
										}
									} else if(korisnik.rola.id == 6){ //KOAUTOR
										red = "Trenutno dostupni naučni radovi:";
										$("#labelaRadovi").append(red);
										$("#labelaRadovi").show();
										$("#tabelaRadovi").append("<tbody>");
										for(i = 0; i < data.length; i++) {
											noviRed =  "<tr><td>"+ data[i].naslovRada
											+ "</td><td>" + data[i].autor
											+ "</td><td>" + data[i].apstrakt
											+ "</td><td>" + data[i].kljucneReci
											+ "</td><td>" + data[i].naucnaOblast
											+ "</td><td>" + data[i].casopisRada
													+ "</td><td>" + "<input id=\"dugmeAzurirajRad\" type=\"button\" class=\"btn btn-warning\" style=\"float:right\" value=\"Ažuriraj\" data-toggle=\"modal\" data-target=\"#modalniAzurirajRad\" onclick =\"azurirajRad(" + data[i].id + ")\">"
													+ "</td><td>" + "<input id=\"dugmeObrisiRad\" type=\"button\" class=\"btn btn-danger\" style=\"float:right\" value=\"Obriši\" onclick =\"obrisiRad(" + data[i].id + ")\">";										
											$("#tabelaRadovi").append(noviRed);
										}
									} else { //CITALAC
										red = "Trenutno dostupni naučni radovi:";
										$("#labelaRadovi").append(red);
										$("#labelaRadovi").show();
										$("#tabelaRadovi").append("<tbody>");
										for(i = 0; i < data.length; i++) {
											noviRed =  "<tr><td>"+ data[i].naslovRada
											+ "</td><td>" + data[i].autor
											+ "</td><td>" + data[i].apstrakt
											+ "</td><td>" + data[i].kljucneReci
											+ "</td><td>" + data[i].naucnaOblast
											+ "</td><td>" + data[i].casopisRada
											+ "</td><td>" + data[i].amount;
											if(data[i].isOpenAccess == false){
												noviRed = noviRed + "</td><td>" + "<input id=\"btnDodajUKorpu"+data[i].id+ "\" type=\"button\" class=\"btn btn-warning\" style=\"float:right\" value=\"Dodaj u korpu\" onclick =\"dodajUKorpu(" + data[i].id + ")\"></td></tr>";												
											}else{
												noviRed = noviRed + "<td></td></tr>"
											}
											$("#tabelaRadovi").append(noviRed);
										}
									}
									$("#tabelaRadovi").append("</tbody>");
								}
							}else
								alert("Greška prilikom pokušaja prikazivanja liste naučnih radova.");
						},
						error: function(data){
							alert('GREšKA PRILIKOM PREUZIMANJA LISTE NAUČNIH RADOVA.');
						}
					});
				});
			//cccccccccccccccccccccccccccccccccccccccccccccccccccccc
			
			/*$.ajax({
				url: "https://localhost:9081/rad/getRadovi",
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
						if(data.length == 0){
							red = "Trenutno nema dostupnih naučnih radova.";
							$("#labelaRadovi").append(red);
							$("#labelaRadovi").show();
						}else{
							red = "Trenutno dostupni naučni radovi:";
							$("#labelaRadovi").append(red);
							$("#labelaRadovi").show();
							$("#tabelaRadovi").append("<thead class=\"zaglavljeTabele\"><tr><th scope=\"col\" class=\"text-center\">Naslov</th><th scope=\"col\" class=\"text-center\">Autor</th><th scope=\"col\" class=\"text-center\">Apstrakt</th><th scope=\"col\" class=\"text-center\">Ključni pojmovi</th><th scope=\"col\" class=\"text-center\">Naučna oblast</th><th scope=\"col\" class=\"text-center\">Status</th><th scope=\"col\" class=\"text-center\" colspan=\"2\"></th></tr></thead><tbody>");
							for(i = 0; i < data.length; i++) {
								noviRed =  "<tr><td>"+ data[i].naslov
										+ "</td><td>" + data[i].autor.ime + " " + data[i].autor.prezime
										+ "</td><td>" + data[i].apstrakt
										+ "</td><td>" + data[i].kljucniPojmovi
										+ "</td><td>" + data[i].naucnaOblast.naziv
										+ "</td><td>" + data[i].statusRada
										+ "</td><td>" + "<input id=\"dugmeAzurirajRad\" type=\"button\" class=\"btn btn-warning\" style=\"float:right\" value=\"Ažuriraj\" data-toggle=\"modal\" data-target=\"#modalniAzurirajRad\" onclick =\"azurirajRad(" + data[i].id + ")\">"
										+ "</td><td>" + "<input id=\"dugmeObrisiRad\" type=\"button\" class=\"btn btn-danger\" style=\"float:right\" value=\"Obriši\" onclick =\"obrisiRad(" + data[i].id + ")\">";										
								$("#tabelaRadovi").append(noviRed);
							}
							$("#tabelaRadovi").append("</tbody>");
						}
					}else
						alert("Greška prilikom pokušaja prikazivanja liste naučnih radova.");
				},
				error: function(data){
					alert('GREšKA PRILIKOM PREUZIMANJA LISTE NAUČNIH RADOVA.');
				}
			});*/
		
			$("#dugmeOdjaviSe").click(function(){
				localStorage.clear();
				$.ajax({
					url: "https://localhost:9081/korisnik/odjava",
					type: "POST",
					data: null,
					contentType: "application/json",
					datatype: 'json',
					crossDomain: true,
			        headers: {  'Access-Control-Allow-Origin': '*' },
			        xhrFields: {
			            withCredentials: true
			         },
					success: function(data){
						location.href = "https://localhost:9081/index.html"
					}
				});
			});			
			
			/*$.ajax({
				url : "https://localhost:9081/korisnik/getTrenutnoAktivan",
				crossDomain: true,
		        headers: {  'Access-Control-Allow-Origin': '*' },
		        xhrFields: {
		            withCredentials: true
		         },
			}).then(function(data){
				if(data.uloga=="UREDNIK" || data.uloga=="GLAVNI_UREDNIK"){
					$.ajax({
						url: "https://localhost:9081/korisnik/getNaucneOblastiKojePokrivaUrednik",
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
								if(data.length == 0){
									$("#pretragaNaucnihOblastiPoNaslovu").hide();
									$("#pretraziNaucneOblastiNaslov").hide();
									$('#dugmePretraziNaucneOblasti').hide();
									$("#dugmeSortirajNaucneOblasti").hide();
									red = "Trenutno ne pokrivate nijednu naučnu oblast.";
									$("#prazno").append(red);
								}else{
									$("#tabelaNaucneOblasti").append("<thead class=\"thead-dark\"><tr><th scope=\"col\" class=\"text-center\">Naziv</th><th scope=\"col\" class=\"text-center\">Opis</th><th></th></tr></thead><tbody>");
									for(i = 0; i < data.length; i++) {
										noviRed =  "<tr><td>"+ data[i].naziv 
												+ "</td><td>" + data[i].opis
												+ "</td><td><img src=\"slike/obrisi.png\" height=\"20\" width=\"20\"/><input type=\"button\" class=\"btn btn-link\" style=\"float:right\" value =\"Izbriši\" onclick =\"izbrisiNaucnuOblast(" + data[i].id + ")\"/></td>"
										$("#tabelaNaucneOblasti").append(noviRed);
									}
									$("#tabelaNaucneOblasti").append("</tbody>");
								}
							}else
								alert("Greška prilikom pokušaja prikazivanja liste naučnih oblasti koje pokrivate.");
						},
						error: function(data){
							alert('GREšKA PRILIKOM PREUZIMANJA LISTE NAUČNIH OBLASTI KOJE POKRIVATE.')
						}
					});
				} else {
					$("#pretragaNaucnihOblastiPoNaslovu").hide();
					$("#pretraziNaucneOblastiNaslov").hide();
					$('#dugmePretraziNaucneOblasti').hide();
					$("#dugmeSortirajNaucneOblasti").hide();
				}
			});*/
			
			
			 function azurirajCasopis(id){
				 $("#modalniAzurirajCasopis").show();
				
				 $.ajax({
					url: "https://localhost:9081/casopis/" + id,
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
							$('#nazivA').focus();
							$('#issnbrojA').val(data.issnbroj);
							$('#nazivA').val(data.naziv);
							$('#merchantIdA').val(data.merchantId);
							$('#merchantPasswordA').val(data.merchantPassword);
							$('#amountA').val(data.amount);
						}else{
							alert("Prikazivanje podataka o časopisu koji želite da ažurirate nije uspelo.");
						}
					},
					error: function(data){
						alert('GREŠKA PRILIKOM POKUŠAJA PRIKAZIVANJA PODATAKA O ČASOPISU KOJI ŽELITE DA AŽURIRATE.');
					}
				});
				
				$("#dugmeAzurirajCasopisModalni").click(function(){
					var provera = true;
					var forma = $('form[id="formaAzurirajCasopis"]');
					var issnbroj = forma.find('[name=issnbroj]').val();
					var naziv = forma.find('[name=naziv]').val();
					var merchantId = forma.find('[name=merchantId]').val();
					var merchantPassword = forma.find('[name=merchantPassword]').val();
					var amount = forma.find('[name=amount]').val();
					
					if(!naziv) {
						$('#divValidacijaCasopis').empty();
						$('#divValidacijaCasopis').append('<p style="color:red"><b>Morate uneti naziv.</b></p>');
						provera = false;
					}
					if(!merchantId) {
						$('#divValidacijaCasopis').empty();
						$('#divValidacijaCasopis').append('<p style="color:red"><b>Morate uneti oznaku poslodavca.</b></p>');
						provera = false;
					}
					if(!merchantPassword) {
						$('#divValidacijaCasopis').empty();
						$('#divValidacijaCasopis').append('<p style="color:red"><b>Morate uneti lozinku poslodavca.</b></p>');
						provera = false;
					}
					var passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&]{10,30}/;
					if(merchantPassword.length > 30 || merchantPassword.length<10 || !isNaN(merchantPassword) || (!passwordRegex.test(merchantPassword))
					   || merchantPassword == "123456" || merchantPassword == "password" || merchantPassword == "12345678" || merchantPassword == "qwerty" || merchantPassword == "12345" || 
					   merchantPassword == "123456789" || merchantPassword == "letmein" || merchantPassword == "1234567" || merchantPassword == "football" || merchantPassword == "iloveyou" || 
					   merchantPassword == "admin" || merchantPassword == "welcome" || merchantPassword == "monkey" || merchantPassword == "login" || merchantPassword == "abc123" || 
					   merchantPassword == "starwars" || merchantPassword == "123123" || merchantPassword == "dragon" || merchantPassword == "passw0rd" || merchantPassword == "master" || 
					   merchantPassword == "hello" || merchantPassword == "freedom" || merchantPassword == "whatever" || merchantPassword == "qazwsx" || merchantPassword == "trustno1" || 
					   merchantPassword == "123456789@Bs"){
					   		$('#divValidacijaCasopis').empty();
							$('#divValidacijaCasopis').append('<p style="color:red"><b>Lozinka mora ispoštovati određenu formu.</b></p>');
							provera = false;
					}
					if(!amount) {
						$('#divValidacijaCasopis').empty();
						$('#divValidacijaCasopis').append('<p style="color:red"><b>Morate uneti iznos članarine.</b></p>');
						provera = false;
					}
					if(amount < 0) {
						$('#divValidacijaCasopis').empty();
						$('#divValidacijaCasopis').append('<p style="color:red"><b>Iznos članarine mora biti nenegativan broj.</b></p>');
						provera = false;
					}
					
					if(provera) {
						$("#dugmeAzurirajCasopisModalni").prop("disabled",true);
						
						$('#divValidacijaCasopis').empty();
						
						formData = JSON.stringify({
							issnbroj:$("#formaAzurirajCasopis [name='issnbroj']").val(),
							naziv:$("#formaAzurirajCasopis [name='naziv']").val(),
							merchantId:$("#formaAzurirajCasopis [name='merchantId']").val(),
							merchantPassword:$("#formaAzurirajCasopis [name='merchantPassword']").val(),
							amount:$("#formaAzurirajCasopis [name='amount']").val()
						});
						
						$.ajax({
							url: "https://localhost:9081/casopis/azuriraj/" + id,
							type: "PUT",
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
									alert("Naučni časopis je uspešno ažuriran.");
									location.href = "https://localhost:9081/homePage.html"
								}else{
									alert("Ažuriranje naučnog časopisa nije uspelo.");
								}
							},
							error: function(data){
								alert('GREŠKA PRILIKOM POKUŠAJA AŽURIRANJA NAUČNOG ČASOPISA.');
							}
						});
					}
				});	
			}
			 
			 function obrisiCasopis(id){
				$.ajax({	
					url: "https://localhost:9081/casopis/obrisi/" + id,
					type: "DELETE",
					contentType: "application/json",
					datatype: 'json',
					crossDomain: true,
			        headers: {  'Access-Control-Allow-Origin': '*' },
			        xhrFields: {
			            withCredentials: true
			         },
					success: function(data){
						alert("Naučni časopis je uspešno obrisan.");
						location.href = "https://localhost:9081/homePage.html"
					},
					error: function(data){
						alert("GREšKA PRILIKOM POKUšAJA BRISANJA NAUČNOG ČASOPISA.");
					}
				});			
			}
			 
			 function azurirajNaucnuOblast(id){
				 	$("#modalniAzurirajNaucnuOblast").show();
					
					$.ajax({
						url: "https://localhost:9081/naucnaOblast/" + id,
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
								$('#nazivNOA').focus();
								$('#nazivNOA').val(data.naziv);
								$('#opisNOA').val(data.opis);
							}else{
								alert("Prikazivanje podataka o naučnoj oblasti koji želite da ažurirate nije uspelo.");
							}
						},
						error: function(data){
							alert('GREŠKA PRILIKOM POKUŠAJA PRIKAZIVANJA PODATAKA O NAUČNOJ OBLASTI KOJU ŽELITE DA AŽURIRATE.');
						}
					});
					
					$("#dugmeAzurirajNaucnuOblastModalni").click(function(){
						var provera = true;
						var formaNO = $('form[id="formaAzurirajNaucnuOblast"]');
						var nazivNO = formaNO.find('[name=nazivNO]').val();
						var opisNO = formaNO.find('[name=opisNO]').val();
						
						if(!nazivNO) {
							$('#divValidacijaNO').empty();
							$('#divValidacijaNO').append('<p style="color:red"><b>Morate uneti naziv.</b></p>');
							provera = false;
						}
						if(!opisNO) {
							$('#divValidacijaNO').empty();
							$('#divValidacijaNO').append('<p style="color:red"><b>Morate uneti opis.</b></p>');
							provera = false;
						}
						
						if(provera) {
							$("#dugmeAzurirajNaucnuOblastModalni").prop("disabled",true);
							
							$('#divValidacijaNO').empty();
							
							formData = JSON.stringify({
								naziv:$("#formaAzurirajNaucnuOblast [name='nazivNO']").val(),
								opis:$("#formaAzurirajNaucnuOblast [name='opisNO']").val(),
							});
							
							$.ajax({
								url: "https://localhost:9081/naucnaOblast/azuriraj/" + id,
								type: "PUT",
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
										alert("Naučna oblast je uspešno ažurirana.");
										location.href = "https://localhost:9081/homePage.html"
									}else{
										alert("Ažuriranje naučne oblasti nije uspelo.");
									}
								},
								error: function(data){
									alert('GREŠKA PRILIKOM POKUŠAJA AŽURIRANJA NAUČNE OBLASTI.');
								}
							});
						}
					});	
				}
				 
				 function obrisiNaucnuOblast(id){
					$.ajax({	
						url: "https://localhost:9081/naucnaOblast/obrisi/" + id,
						type: "DELETE",
						contentType: "application/json",
						datatype: 'json',
						crossDomain: true,
				        headers: {  'Access-Control-Allow-Origin': '*' },
				        xhrFields: {
				            withCredentials: true
				         },
						success: function(data){
							alert("Naučna oblast je uspešno obrisana.");
							location.href = "https://localhost:9081/homePage.html"
						},
						error: function(data){
							alert("GREšKA PRILIKOM POKUšAJA BRISANJA NAUČNE OBLASTI.");
						}
					});			
				}
				 
				 function azurirajRad(id){
					    $("#modalniAzurirajRad").show();
						
						$.ajax({
							url: "https://localhost:9081/rad/" + id,
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
									$("#naucnaOblastRA option:selected").val(data.naucnaOblast.id);
									//$("#pdfRadnaVerzijaRA").val(data.radnaVerzijaPDF);
									//$("#pdfFinalnaVerzijaRA").val(data.finalnaVerzijaPDF);
									$('#naslovRA').focus();
									$('#naslovRA').val(data.naslov);
									$('#kljucniPojmoviRA').val(data.kljucniPojmovi);
									$('#apstraktRA').val(data.apstrakt);
								}else{
									alert("Prikazivanje podataka o naučnom radu koji želite da ažurirate nije uspelo.");
								}
							},
							error: function(data){
								alert('GREŠKA PRILIKOM POKUŠAJA PRIKAZIVANJA PODATAKA O NAUČNOM RADU KOJI ŽELITE DA AŽURIRATE.');
							}
						});
						
						 $.ajax({
								url: "https://localhost:9081/naucnaOblast/getNaucneOblasti",
								type: "GET",
								contentType: "application/json",
								datatype: 'json',
								crossDomain: true,
						        headers: {  'Access-Control-Allow-Origin': '*' },
						        xhrFields: {
						            withCredentials: true
						         },
								success: function(data){
									$("#naucnaOblastRA").empty();
									var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
									var naucneOblasti = $("#naucnaOblastRA");
									$.each(list, function(index, naziv) {
										var li = $('<option value="'+naziv.naziv+'">' + naziv.naziv + ' </option>');
										$(naucneOblasti).append(li);
									});
									$("#naucnaOblastRA").attr("disabled", true); 
								},
								error: function(data){
									alert('GREŠKA PRILIKOM PREUZIMANJA LISTE NAUČNIH OBLASTI.')
								}
							});
						
						$("#dugmeAzurirajRadModalni").click(function(){
							var provera = true;
							var formaR = $('form[id="formaAzurirajRad"]');
							var naslovR = formaR.find('[name=naslovR]').val();
							var kljucniPojmoviR = formaR.find('[name=kljucniPojmoviR]').val();
							var apstraktR = formaR.find('[name=apstraktR]').val();
							//var pdfRadnaVerzijaR = $("pdfRadnaVerzijaR").text();
							//var pdfFinalnaVerzijaR = $("pdfFinalnaVerzijaR").text();
							
							if(!naslovR) {
								$('#divValidacijaRad').empty();
								$('#divValidacijaRad').append('<p style="color:red"><b>Morate uneti naslov.</b></p>');
								provera = false;
							}
							if(!kljucniPojmoviR) {
								$('#divValidacijaRad').empty();
								$('#divValidacijaRad').append('<p style="color:red"><b>Morate uneti ključne pojmove.</b></p>');
								provera = false;
							}
							if(!apstraktR) {
								$('#divValidacijaRad').empty();
								$('#divValidacijaRad').append('<p style="color:red"><b>Morate uneti apstrakt.</b></p>');
								provera = false;
							}
							/*if(pdfRadnaVerzijaR == "") {
								$('#divValidacijaRad').empty();
								$('#divValidacijaRad').append('<p style="color:red"><b>Morate odabrati radnu verziju rada.</b></p>');
								provera = false;
							}*/
							
							if(provera) {
								$("#dugmeAzurirajRadModalni").prop("disabled",true);
								
								$('#divValidacijaRad').empty();
								
								//var izabranaNaucnaOblast = $("#naucnaOblastRA option:selected").text();
								
								formData = JSON.stringify({
									naslov:$("#formaAzurirajRad [name='naslovR']").val(),
									kljucniPojmovi:$("#formaAzurirajRad [name='kljucniPojmoviR']").val(),
									apstrakt:$("#formaAzurirajRad [name='apstraktR']").val(),
									//pdfFinalnaVerzija:pdfRadnaVerzijaR,
									//pdfRadnaVerzija:pdfRadnaVerzijaR,
									//naucnaOblast:izabranaNaucnaOblast
								});
								
								$.ajax({
									url: "https://localhost:9081/rad/azuriraj/" + id,
									type: "PUT",
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
											alert("Naučni rad je uspešno ažuriran.");
											location.href = "https://localhost:9081/homePage.html"
										}else{
											alert("Ažuriranje naučnog rada nije uspelo.");
										}
									},
									error: function(data){
										alert('GREŠKA PRILIKOM POKUŠAJA AŽURIRANJA NAUČNOG RADA.');
									}
								});
							}
						});	
					}
					 
					 function obrisiRad(id){
						 alert('id rada koji brisem: ' + id);
						 
						$.ajax({	
							url: "https://localhost:9081/rad/obrisi/" + id,
							type: "DELETE",
							contentType: "application/json",
							datatype: 'json',
							crossDomain: true,
					        headers: {  'Access-Control-Allow-Origin': '*' },
					        xhrFields: {
					            withCredentials: true
					         },
							success: function(data){
								alert("Naučni rad je uspešno obrisan.");
								location.href = "https://localhost:9081/homePage.html"
							},
							error: function(data){
								alert("GREšKA PRILIKOM POKUšAJA BRISANJA NAUČNOG RADA.");
							}
						});			
					}
					 
				function azurirajKorisnickePodatke(){
					$("#modalniAzurirajKorisnickePodatke").show();
					
					$.ajax({
						url : "https://localhost:9081/korisnik/dekriptovanjeEmailAdrese",
						crossDomain: true,
				        headers: {  'Access-Control-Allow-Origin': '*' },
				        xhrFields: {
				            withCredentials: true
				         }
					}).then(function(data){
						decryptedString = data;
					});
					
					$.ajax({
						url : "https://localhost:9081/korisnik/getTrenutnoAktivan",
						crossDomain: true,
				        headers: {  'Access-Control-Allow-Origin': '*' },
				        xhrFields: {
				            withCredentials: true
				         }
					}).then(function(data){
						$("#emailKPA").val(decryptedString);
						$("#lozinka1KPA").val(data.lozinka);
						$("#lozinka2KPA").val(data.lozinka);
						$("#imeKPA").val(data.ime);
						$("#prezimeKPA").val(data.prezime);
						$("#gradKPA").val(data.grad);
						$("#drzavaKPA").val(data.drzava);
						$("#titulaKPA").val(data.titula);
						
						if(data.titula == "")
							$("#titulaKPA").attr('readonly', true);
					});
					
					$("#dugmeAzurirajKorisnickePodatkeModalni").click(function(){
						var provera = true;
						var forma = $('form[id="formaAzurirajKorisnickePodatke"]');
						var email = forma.find('[name=emailKP]').val();
						var lozinka1 = forma.find('[name=lozinka1KP]').val();
						var lozinka2 = forma.find('[name=lozinka2KP]').val();
						var ime = forma.find('[name=imeKP]').val();
						var prezime = forma.find('[name=prezimeKP]').val();
						var grad = forma.find('[name=gradKP]').val();
						var drzava = forma.find('[name=drzavaKP]').val();
						var titula = forma.find('[name=titulaKP]').val();
										
						if(!lozinka1) {
							$('#divValidacijaKP').empty();
							$('#divValidacijaKP').append('<p style="color:red"><b>Morate uneti lozinku.</b></p>');
							provera = false;
						}
						var passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&]{10,30}/;
						if(lozinka1.length > 30 || lozinka1.length<10 || !isNaN(lozinka1) || (!passwordRegex.test(lozinka1))
						   || lozinka1 == "123456" || lozinka1 == "password" || lozinka1 == "12345678" || lozinka1 == "qwerty" || lozinka1 == "12345" || 
						   lozinka1 == "123456789" || lozinka1 == "letmein" || lozinka1 == "1234567" || lozinka1 == "football" || lozinka1 == "iloveyou" || 
						   lozinka1 == "admin" || lozinka1 == "welcome" || lozinka1 == "monkey" || lozinka1 == "login" || lozinka1 == "abc123" || 
						   lozinka1 == "starwars" || lozinka1 == "123123" || lozinka1 == "dragon" || lozinka1 == "passw0rd" || lozinka1 == "master" || 
						   lozinka1 == "hello" || lozinka1 == "freedom" || lozinka1 == "whatever" || lozinka1 == "qazwsx" || lozinka1 == "trustno1" || 
						   lozinka1 == "123456789@Bs"){
						   		$('#divValidacijaKP').empty();
								$('#divValidacijaKP').append('<p style="color:red"><b>Uneta lozinka se ne sme nalaziti na listi najčešće korišćenih lozinki.</b></p>');
								provera = false;
						}
						if(!lozinka2) {
							$('#divValidacijaKP').empty();
							$('#divValidacijaKP').append('<p style="color:red"><b>Ponovo morate uneti lozinku.</b></p>');
							provera = false;
						}
						if(lozinka2.length > 30 || lozinka2.length<10 || !isNaN(lozinka2) || (!passwordRegex.test(lozinka2))
							|| lozinka2 == "123456" || lozinka2 == "password" || lozinka2 == "12345678" || lozinka2 == "qwerty" || lozinka2 == "12345" || 
							lozinka2 == "123456789" || lozinka2 == "letmein" || lozinka2 == "1234567" || lozinka2 == "football" || lozinka2 == "iloveyou" || 
							lozinka2 == "admin" || lozinka2 == "welcome" || lozinka2 == "monkey" || lozinka2 == "login" || lozinka2 == "abc123" || 
							lozinka2 == "starwars" || lozinka2 == "123123" || lozinka2 == "dragon" || lozinka2 == "passw0rd" || lozinka2 == "master" || 
							lozinka2 == "hello" || lozinka2 == "freedom" || lozinka2 == "whatever" || lozinka2 == "qazwsx" || lozinka2 == "trustno1" || 
							lozinka2 == "123456789@Bs"){
								$('#divValidacijaKP').empty();
								$('#divValidacijaKP').append('<p style="color:red"><b>Uneta lozinka se ne sme nalaziti na listi najčešće korišćenih lozinki.</b></p>');
								provera = false;
						}
						if(lozinka1 != lozinka2) {
							$('#divValidacijaKP').empty();
							$('#divValidacijaKP').append('<p style="color:red"><b>Lozinka mora biti ista u oba polja.</b></p>');
							provera = false;
						}
						if(!ime) {
							$('#divValidacijaKP').empty();
							$('#divValidacijaKP').append('<p style="color:red"><b>Morate uneti ime.</b></p>');
							provera = false;
						}
						if(!prezime) {
							$('#divValidacijaKP').empty();
							$('#divValidacijaKP').append('<p style="color:red"><b>Morate uneti prezime.</b></p>');
							provera = false;
						}
						if(!grad) {
							$('#divValidacijaKP').empty();
							$('#divValidacijaKP').append('<p style="color:red"><b>Morate uneti naziv grada.</b></p>');
							provera = false;
						}
						if(!drzava) {
							$('#divValidacijaKP').empty();
							$('#divValidacijaKP').append('<p style="color:red"><b>Morate uneti naziv države.</b></p>');
							provera = false;
						}		
						
						if(provera) {
							$("#dugmeAzurirajKorisnickePodatke").prop("disabled",true);
							
							$('#divValidacijaKP').empty();
							
							formData = JSON.stringify({
								email:$("#formaAzurirajKorisnickePodatke [name='emailKP']").val(),
								lozinka:$("#formaAzurirajKorisnickePodatke [name='lozinka1KP']").val(),
								ime:$("#formaAzurirajKorisnickePodatke [name='imeKP']").val(),
								prezime:$("#formaAzurirajKorisnickePodatke [name='prezimeKP']").val(),
								grad:$("#formaAzurirajKorisnickePodatke [name='gradKP']").val(),
								drzava:$("#formaAzurirajKorisnickePodatke [name='drzavaKP']").val(),
								titula:$("#formaAzurirajKorisnickePodatke [name='titulaKP']").val()
							   });
							
							$.ajax({
								url: "https://localhost:9081/korisnik/azurirajKorisnika",
								type: "PUT",
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
										alert("Korisnički podaci su uspešno ažurirani.");
										location.href = "https://localhost:9081/homePage.html"				
									}else
										alert("Ažuriranje korisničkih podataka nije uspelo.");
								},
								error: function(data){
									alert('GREŠKA PRILIKOM POKUŠAJA AŽURIRANJA KORISNIČKIH PODATAKA!!!');
								}
							});
						}
					});
				}
		
					 //SEP - placanje
					 function kupiCasopis(merchant_id, amount, merchant_password) {
						var data = JSON.stringify({
								"merchant_id": merchant_id,
								"amount": amount,
								"merchant_password": merchant_password
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

						function paypal(merchant_id,paypalId, paypalSecret, amount, merchant_password) {
							var data = JSON.stringify({
								"merchant_id": merchant_id,
								"amount": amount,
								"merchant_password": merchant_password,
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
						
						function paypalPretplata(merchant_id,paypalId, paypalSecret, amount) {
							var data = JSON.stringify({
								"idCasopisa" : merchant_id,
								"merchant_id": merchant_id,
								"amount": amount,
								"paypalId" : paypalId,
								"paypalSecret" : paypalSecret
							});
							var path = "paypal";
							$.ajax({
								async: false,
								url: "http://localhost:8762/paypal-service/paypal/pretplata",
						        type: "POST",
						        contentType: "application/json",
						        data: data,
						        dataType: "text",
						        crossDomain: true,
						        headers: {  'Access-Control-Allow-Origin': '*' },
						        success: function (data2) {
						        	if(data2!=null){
						        		/*alert("Bićete preusmereni na sledeću adresu:\n" + data2);*/
						        		location.href = data2;
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

function dodajUKorpu(id){
	var dugmeValue = document.getElementById("btnDodajUKorpu"+id).getAttribute("value");
	if(dugmeValue==="Dodaj u korpu"){
		var dugme = document.getElementById("btnDodajUKorpu"+id);
		dugme.setAttribute("value", "Izbaci iz korpe");
		$.ajax({
			async: false,
			url: "https://localhost:9081/korpa/add/" + id,
	        type: "POST",
	        crossDomain: true,
	        headers: {  'Access-Control-Allow-Origin': '*' },
	        success: function () {
	        	
	        },
	        error: function (jqxhr, textStatus, errorThrown) {
	        	alert("GREŠKA " + textStatus);
	        }
		});
	}else if(dugmeValue==="Izbaci iz korpe"){
		var dugme = document.getElementById("btnDodajUKorpu"+id);
		dugme.setAttribute("value", "Dodaj u korpu");
		$.ajax({
			async: false,
			url: "https://localhost:9081/korpa/delete/" + id,
	        type: "POST",
	        crossDomain: true,
	        headers: {  'Access-Control-Allow-Origin': '*' },
	        success: function () {
	        	
	        },
	        error: function (jqxhr, textStatus, errorThrown) {
	        	alert("GREŠKA " + textStatus);
	        }
		});
	}
}
						