package sep.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Transakcija {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=true)
	private String tipPlacanja;
	
	@Column(nullable=true)
	private String uspesnost;

	@Column(nullable=true)
	private String merchant_order_id;

	@Column(nullable=true)
	private String acquirerId;

	@Column(nullable=true)
	private Timestamp acquirerTimestamp;

	@Column(nullable=true)
	private String paymentId;

	@Column(nullable=true)
	private String idIniciraneTransakcije;
	
	public Transakcija() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
