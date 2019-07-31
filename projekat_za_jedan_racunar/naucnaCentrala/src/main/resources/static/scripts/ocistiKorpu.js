$(document).ready(function () 
{
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
});