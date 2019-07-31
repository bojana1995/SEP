package udd_upp.dto;

import java.sql.Timestamp;

public class URLDTO {
	
	public String url;
	
	private Integer merchant_order_id;
	
	private Integer acquirerId;
	
	private Timestamp acquirerTimestamp;
	
	private Integer paymentId;

	public URLDTO() {

	}
	
	public Integer getMerchant_order_id() {
		return merchant_order_id;
	}

	public void setMerchant_order_id(Integer merchant_order_id) {
		this.merchant_order_id = merchant_order_id;
	}

	public Integer getAcquirerId() {
		return acquirerId;
	}

	public void setAcquirerId(Integer acquirerId) {
		this.acquirerId = acquirerId;
	}

	public Timestamp getAcquirerTimestamp() {
		return acquirerTimestamp;
	}

	public void setAcquirerTimestamp(Timestamp acquirerTimestamp) {
		this.acquirerTimestamp = acquirerTimestamp;
	}

	public Integer getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
