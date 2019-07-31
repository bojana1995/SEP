function dodajRad(){
	var naslovRada = $("#naslovRadaInput").val();
	var kljucneReci = $("#kljucniPojmoviInput").val();
	var apstrakt = $("#apstraktInput").val();
	var amount = $("#amount").val();
	var naucnaOblast = $("#naucnaOblastSelect").find('option:selected').attr('id');
	var casopis = $("#casopisRadaSelect").find('option:selected').attr('id');
	
	var radDTO = JSON.stringify({
		naslovRada: naslovRada,
		kljucneReci: kljucneReci,
		apstrakt: apstrakt,
		naucnaOblast: naucnaOblast,
		casopisRada: casopis,
		amount: amount
	});
	
	 $.ajax({
			url: "https://192.168.43.196:9081/rad/dodajRad",
			type: "POST",
			contentType: "application/json",
			data: radDTO,
			crossDomain: true,
	        headers: {  'Access-Control-Allow-Origin': '*' },
	        xhrFields: {
	            withCredentials: true
	         },
			success: function(){
				alert('WOOW DODO SI');
				uploadData();
				location.href = "https://192.168.43.196:9081/homePage.html"
			},
			error: function(){
				alert('GREŠKA PRILIKOM PREUZIMANJA LISTE CASOPISA!!!')
			}
	});
}
