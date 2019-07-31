$(document).ready(
				function() {
					$.ajax({
						url: "https://192.168.43.196:9081/casopis/getCasopisi",
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
									$("#dugmeSortirajCasopise").hide();
									red = "Trenutno nema dostupnih naučnih časopisa.";
									$("#dostupniCasopisi").append(red);
								}else{
									$("#tabelaCasopisi").append("<thead class=\"zaglavljeTabele\"><tr><th scope=\"col\" class=\"text-center\">ISSN broj</th><th scope=\"col\" class=\"text-center\">Naziv</th><th scope=\"col\" class=\"text-center\">Iznos članarine</th><th scope=\"col\" class=\"text-center\">Tip časopisa</th><th scope=\"col\" class=\"text-center\">Članarina se naplaćuje čitaocu</th><th scope=\"col\" class=\"text-center\">Podržani tipovi plaćanja</th></tr></thead><tbody>");
									for(i = 0; i < data.length; i++) {
										var placaCitalac = false;
										if(data[i].clanarinaSeNaplacujeCitaocu==true)
											placaCitalac="da";
										else
											placaCitalac="ne";
										
										noviRed =  "<tr><td>"+ data[i].issnbroj 
												+ "</td><td>" + data[i].naziv
												+ "</td><td>" + data[i].amount
												<!--+ "</td><td>" + data[i].merchantId-->
												+ "</td><td>" + data[i].tipCasopisa
												+ "</td><td>" + placaCitalac
												+ "</td><td>" + data[i].tipoviPlacanja;
										$("#tabelaCasopisi").append(noviRed);
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
					
			        $.ajax({
						url: "https://192.168.43.196:9081/naucnaOblast/getNaucneOblasti",
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
									$("#dugmeSortirajNaucneOblasti").hide();
									red = "Trenutno nema dostupnih naučnih oblasti.";
									$("#dostupneNaucneOblasti").append(red);
								}else{
									$("#tabelaNaucneOblasti").append("<thead class=\"zaglavljeTabele\"><tr><th scope=\"col\" class=\"text-center\">Naziv</th><th scope=\"col\" class=\"text-center\">Opis</th></tr></thead><tbody>");
									for(i = 0; i < data.length; i++) {
										noviRed =  "<tr><td>"+ data[i].naziv 
												+ "</td><td>" + data[i].opis;
										$("#tabelaNaucneOblasti").append(noviRed);
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
			        
			        $.ajax({
						url: "https://192.168.43.196:9081/rad/getRadovi",
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
									$("#dugmeSortirajRadove").hide();
									red = "Trenutno nema dostupnih naučnih radova.";
									$("#dostupniRadovi").append(red);
								}else{
									$("#tabelaRadovi").append("<tbody>");
										for(i = 0; i < data.length; i++) {
											noviRed =  "<tr><td>"+ data[i].naslovRada
											+ "</td><td>" + data[i].autor
											+ "</td><td>" + data[i].apstrakt
											+ "</td><td>" + data[i].kljucneReci
											+ "</td><td>" + data[i].naucnaOblast
											+ "</td><td>" + data[i].casopisRada
											+ "</td><td>" + data[i].amount + "</td></tr>";
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
					});
				});