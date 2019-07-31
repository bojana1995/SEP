package sep.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import sep.dto.MerchantDTO;
import sep.model.PayPalClient;

@RestController
@RequestMapping(value = "/zahtev")
public class KoncentratorPlacanjaController {

	PayPalClient payPalClient = new PayPalClient();
	
	@Autowired
	private RestTemplate restTemplate;

	private static final Logger logger = LoggerFactory.getLogger(KoncentratorPlacanjaController.class);

	@CrossOrigin
	@RequestMapping(value = "/{path}", method = RequestMethod.POST)
	public String startPayment(@RequestBody MerchantDTO merchant, @PathVariable String path) {
		System.out.println("\n\t\tDošao u Koncentrator. Nacin placanja: " + path);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		merchant.setMerchant_order_id((int) (Math.random() * 1000000000));
		Date date = new Date();
		Timestamp ts = new Timestamp(date.getTime());
		merchant.setMerchant_timestamp(ts);
		
		HttpEntity<MerchantDTO> entity = new HttpEntity<>(merchant, headers);
		String serviceToCall = path + "-service";
		String retVal = restTemplate.postForObject("http://localhost:8762/" + serviceToCall+"/"
				+path+"/startPayment", entity,
				String.class);
		if (retVal == null) {
			logger.info("\n\t\tNeuspesno plaćanje, nacin placanja: " + path + "\n");
		} else {
			logger.info("\n\t\tZapočeto plaćanje, nacin placanja: " + path + "\n");
		}
		return retVal;
	}

}
