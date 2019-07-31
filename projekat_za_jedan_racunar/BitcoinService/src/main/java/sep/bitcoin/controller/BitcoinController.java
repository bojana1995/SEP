package sep.bitcoin.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import sep.bitcoin.model.MerchantDTO;
import sep.bitcoin.model.ResponseBitcoinDTO;

@RestController
@RequestMapping(value = "/bitcoin")
public class BitcoinController {

	@RequestMapping(value = "/startPayment")
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public String startPayment(@RequestBody MerchantDTO b){
		System.out.println("\n\t\tDošao u Bitcoin.\n");
		
		Map<String, Object> mapa = new HashMap<String,Object>();
        mapa.put("order_id", UUID.randomUUID().toString());
        mapa.put("price_amount", b.getAmount());
        mapa.put("price_currency","USD");
        mapa.put("receive_currency","USD");
        mapa.put("title", b.getMerchant_id());
        mapa.put("description", "desc");
        mapa.put("callback_url", "https://api-sandbox.coingate.com/account/orders");
        mapa.put("success_url", "https://localhost:9081/uspesno.html");
        
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        
        headers.add("Authorization", "Token 8W2cFE2hUx55MHxxuisH9gigTzdP7pRjYmQsHH2V");
        HttpEntity<Map<String, Object>> entity = new HttpEntity<Map<String,Object>>(mapa, headers);
        
        ResponseBitcoinDTO response = client.postForObject("https://api-sandbox.coingate.com/v2/orders", entity, ResponseBitcoinDTO.class);
              
        HttpHeaders noviHeaders = new HttpHeaders();
        noviHeaders.add("Authorization", "Token 8W2cFE2hUx55MHxxuisH9gigTzdP7pRjYmQsHH2V");
        noviHeaders.add("Location", response.getPayment_url()); //payment url
        noviHeaders.add("id", response.getId().toString()); //id inicirane transakcije
        noviHeaders.add("uuid", response.getPayment_url().split("invoice/")[1]);
        noviHeaders.add("title", b.getMerchant_id()); //naziv casopisa koji se placa
        noviHeaders.add("created_at", response.getCreated_at());
        noviHeaders.add("status", response.getStatus()); //ovde ce uvek biti new
        noviHeaders.add("merchant_order_id", response.getOrder_id()); //uplatilac        
        System.out.println("\t\tnoviHeaders: " + noviHeaders.toString() + "\n\n");
        
        String paymentUrl = response.getPayment_url();
        String idIniciraneTransakcije = response.getId().toString();
        String uuid = response.getPayment_url().split("invoice/")[1];
        String naziv = b.getMerchant_id();
        String vremeKreiranja = response.getCreated_at();
        String status = response.getStatus();
        String uplatilac = response.getOrder_id();
        String retVal = paymentUrl + ", " + idIniciraneTransakcije + ", " + uuid + ", " + naziv + ", " + vremeKreiranja + ", " + status + ", " + uplatilac;
        return retVal;
	}
}
