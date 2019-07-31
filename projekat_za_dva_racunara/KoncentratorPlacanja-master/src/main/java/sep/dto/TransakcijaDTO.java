package sep.dto;

import java.sql.Timestamp;

public class TransakcijaDTO {
		
	private String tipPlacanja;
	
	private String uspesnost;
	
	private String merchant_order_id;
	
	private String acquirerId;
	
	private Timestamp acquirerTimestamp;
	
	private String paymentId;
	
	private String idIniciraneTransakcije;
	
	public TransakcijaDTO() {
		
	}

	public String getTipPlacanja() {
		return tipPlacanja;
	}

	public void setTipPlacanja(String tipPlacanja) {
		this.tipPlacanja = tipPlacanja;
	}

	public String getUspesnost() {
		return uspesnost;
	}

	public void setUspesnost(String uspesnost) {
		this.uspesnost = uspesnost;
	}

	public String getMerchant_order_id() {
		return merchant_order_id;
	}

	public void setMerchant_order_id(String merchant_order_id) {
		this.merchant_order_id = merchant_order_id;
	}

	public String getAcquirerId() {
		return acquirerId;
	}

	public void setAcquirerId(String acquirerId) {
		this.acquirerId = acquirerId;
	}

	public Timestamp getAcquirerTimestamp() {
		return acquirerTimestamp;
	}

	public void setAcquirerTimestamp(Timestamp acquirerTimestamp) {
		this.acquirerTimestamp = acquirerTimestamp;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getIdIniciraneTransakcije() {
		return idIniciraneTransakcije;
	}

	public void setIdIniciraneTransakcije(String idIniciraneTransakcije) {
		this.idIniciraneTransakcije = idIniciraneTransakcije;
	}
	
	
	
	
}
