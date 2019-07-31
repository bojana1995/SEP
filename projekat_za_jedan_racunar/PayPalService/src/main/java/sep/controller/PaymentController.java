package sep.controller;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paypal.api.payments.Agreement;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.ChargeModels;
import com.paypal.api.payments.Currency;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.MerchantPreferences;
import com.paypal.api.payments.Patch;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentDefinition;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.Plan;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.ShippingAddress;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import sep.dto.MerchantDTO;

@RestController
@RequestMapping(value = "/paypal")
public class PaymentController {
	
	@CrossOrigin
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	@PostMapping(value="/startPayment")
	public String startPayment(@RequestBody MerchantDTO merchant) {
		
		System.out.println("Paypal ");
		Map<String, Object> response = new HashMap<String, Object>();
	    Amount amount = new Amount();
	    amount.setCurrency("USD");
	    amount.setTotal(merchant.getAmount().toString());
	    Transaction transaction = new Transaction();
	    transaction.setAmount(amount);
	    List<Transaction> transactions = new ArrayList<Transaction>();
	    transactions.add(transaction);

	    Payer payer = new Payer();
	    payer.setPaymentMethod("paypal");

	    Payment payment = new Payment();
	    payment.setIntent("sale");
	    payment.setPayer(payer);
	    payment.setTransactions(transactions);
	    
	    RedirectUrls redirectUrls = new RedirectUrls();
	    redirectUrls.setCancelUrl("http://localhost:8762/paypal-service/greska.html");
	    redirectUrls.setReturnUrl("http://localhost:8762/paypal-service/uspesno.html?payPalId=" + merchant.getPaypalId() + "&payPalSecret=" + merchant.getPaypalSecret());
	    payment.setRedirectUrls(redirectUrls);
	    Payment createdPayment;
	    try {
	        String redirectUrl = "";
	        APIContext context = new APIContext(merchant.getPaypalId(), merchant.getPaypalSecret(), "sandbox");
	        createdPayment = payment.create(context);
	        if(createdPayment!=null){
	            List<Links> links = createdPayment.getLinks();
	            for (Links link:links) {
	                if(link.getRel().equals("approval_url")){
	                    redirectUrl = link.getHref();
	                    break;
	                }
	            }
	            response.put("status", "success");
	            response.put("redirect_url", redirectUrl);
	            return redirectUrl;
	        }
	    } catch (PayPalRESTException e) {
	        System.out.println("Error happened during payment creation!");
	    }
		return null;
	}
	
	@CrossOrigin
	@RequestMapping("/completePayment")
	public void completePayment(@RequestBody String req) {
		
		Map<String, Object> response = new HashMap();
	    Payment payment = new Payment();
	    String payPalId = req.split(",")[0];
	    String payPalSecret = req.split(",")[1];
	    
	    String paymentId = req.split(",")[2];
	    payment.setId(paymentId);
	    PaymentExecution paymentExecution = new PaymentExecution();
	    String payerId = req.split(",")[4];
	    paymentExecution.setPayerId(payerId);
	    
	    try {
	        APIContext context = new APIContext(payPalId, payPalSecret, "sandbox");
	        Payment createdPayment = payment.execute(context, paymentExecution);
	        if(createdPayment!=null){
	            response.put("status", "success");
	            response.put("payment", createdPayment);
	        }
	    } catch (PayPalRESTException e) {
	        System.err.println(e.getDetails());
	    }
	    
	}
	
	@CrossOrigin
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	@PostMapping(value="/pretplata")
	public String pretplata(@RequestBody MerchantDTO merchant) {
		
		//kreiranje plana
		Plan plan = new Plan();
		plan.setName("Pretplata za naucni casopis");
		plan.setDescription("Godisnja pretplata");
		plan.setType("fixed");
		
		PaymentDefinition paymentDefinition = new PaymentDefinition();
		paymentDefinition.setName("Regular Payments");
		paymentDefinition.setType("REGULAR");
		paymentDefinition.setFrequency("MONTH");
		paymentDefinition.setFrequencyInterval("1");
		paymentDefinition.setCycles("12");

		Currency currency = new Currency();
		currency.setCurrency("USD");
		currency.setValue(merchant.getAmount().toString());
		paymentDefinition.setAmount(currency);
		
		ChargeModels chargeModels = new com.paypal.api.payments.ChargeModels();
		chargeModels.setType("SHIPPING");
		chargeModels.setAmount(currency);
		List<ChargeModels> chargeModelsList = new ArrayList<ChargeModels>();
		chargeModelsList.add(chargeModels);
		paymentDefinition.setChargeModels(chargeModelsList);
		
		List<PaymentDefinition> paymentDefinitionList = new ArrayList<PaymentDefinition>();
		paymentDefinitionList.add(paymentDefinition);
		plan.setPaymentDefinitions(paymentDefinitionList);
		
		MerchantPreferences merchantPreferences = new MerchantPreferences();
		merchantPreferences.setSetupFee(currency);
		merchantPreferences.setCancelUrl("http://localhost:8762/paypal-service/pretplataGreska.html?merchantId=" + merchant.getIdCasopisa());
		merchantPreferences.setReturnUrl("http://localhost:8762/paypal-service/pretplataUspesno.html?payPalId=" + merchant.getPaypalId() + "&payPalSecret=" + merchant.getPaypalSecret());
		merchantPreferences.setMaxFailAttempts("0");
		merchantPreferences.setAutoBillAmount("YES");
		merchantPreferences.setInitialFailAmountAction("CONTINUE");
		plan.setMerchantPreferences(merchantPreferences);
		
		try {
			  APIContext apiContext = new APIContext(merchant.getPaypalId(), merchant.getPaypalSecret(), "sandbox");
			  // Create payment
			  Plan createdPlan = plan.create(apiContext);
			  System.out.println("Created plan with id = " + createdPlan.getId());
			  System.out.println("Plan state = " + createdPlan.getState());

			  // Set up plan activate PATCH request
			  List<Patch> patchRequestList = new ArrayList<Patch>();
			  Map<String, String> value = new HashMap<String, String>();
			  value.put("state", "ACTIVE");

			  // Create update object to activate plan
			  Patch patch = new Patch();
			  patch.setPath("/");
			  patch.setValue(value);
			  patch.setOp("replace");
			  patchRequestList.add(patch);

			  // Activate plan
			  createdPlan.update(apiContext, patchRequestList);
			  System.out.println("Plan state = " + createdPlan.getState());
			  return createAgreement(merchant, createdPlan.getId(), apiContext).toString();
			} catch (PayPalRESTException e) {
			  System.err.println(e.getDetails());
			}
		return null;
		
	}
	private URL createAgreement(MerchantDTO merchant, String planId, APIContext apiContext) {
		Agreement agreement = new Agreement();
		agreement.setName("Base Agreement");
		agreement.setDescription("Basic Agreement");
		String startDate = (new Date()).toInstant().plus(Duration.ofMinutes(2)).toString();
		agreement.setStartDate(startDate);
		
		Plan plan = new Plan();
		plan.setId(planId);
		agreement.setPlan(plan);
		
		Payer payer = new Payer();
		payer.setPaymentMethod("paypal");
		agreement.setPayer(payer);
		
		ShippingAddress shipping = new ShippingAddress();
		shipping.setLine1("111 First Street");
		shipping.setCity("Saratoga");
		shipping.setState("CA");
		shipping.setPostalCode("95070");
		shipping.setCountryCode("US");
		agreement.setShippingAddress(shipping);
		
		try {
			  agreement = agreement.create(apiContext);

			  for (Links links : agreement.getLinks()) {
			    if ("approval_url".equals(links.getRel())) {
			      URL url = new URL(links.getHref());
			      
			      return url;
			      
			    }
			  }
			} catch (PayPalRESTException e) {
			  System.err.println(e.getDetails());
			} catch (MalformedURLException e) {
			  e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
			  e.printStackTrace();
			}
		return null;
		
	}
	@CrossOrigin
	@PostMapping(value="/executePayment")
	public void executePayment(@RequestBody String req) {
		
		
		String payPalId = req.split(",")[0];
		String payPalSecret = req.split(",")[0];
		
	    String token = req.split(",")[2];
	    
	    Agreement agreement =  new Agreement();
	    agreement.setToken(token);

	    try {
	      APIContext apiContext = new APIContext(payPalId, payPalSecret, "sandbox");
	      Agreement activeAgreement = agreement.execute(apiContext, agreement.getToken());
	      System.out.println("Agreement created with ID " + activeAgreement.getId());
	    } catch (PayPalRESTException e) {
	      System.err.println(e.getDetails());
	    }
		
		
	}

}
