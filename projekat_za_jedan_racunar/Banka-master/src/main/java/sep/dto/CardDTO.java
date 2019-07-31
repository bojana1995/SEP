package sep.dto;

import java.sql.Timestamp;

public class CardDTO {

	private String pan;

	private String securityCode;

	private String expirationDate;

	private String cardHolderName;

	private String paymentId;
	
	private Integer acquirerId;
	
	private Timestamp acquirerTimestamp;
	
	private Integer issuerId;
	
	private Timestamp issuerTimestamp;

	public CardDTO() {

	}

	public Integer getIssuerId() {
		return issuerId;
	}

	public void setIssuerId(Integer issuerId) {
		this.issuerId = issuerId;
	}

	public Timestamp getIssuerTimestamp() {
		return issuerTimestamp;
	}

	public void setIssuerTimestamp(Timestamp issuerTimestamp) {
		this.issuerTimestamp = issuerTimestamp;
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

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getCardHolderName() {
		return cardHolderName;
	}

	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

}
