package udd_upp.dto;

import org.springframework.web.multipart.MultipartFile;

public class RadDTO {
	private Long id;
	private String naslovRada;
	private String naucnaOblast;
	private String autor;
	private String casopisRada;
	private String kljucneReci;
	private String apstrakt;
	private Double amount;
	private Boolean isOpenAccess;
	private String tipPlacanja;
	private MultipartFile[] files;
	private Double ukupnaCena;
	private String paypalId;
	private String paypalSecret;
	private String merchant_id;
	private String nazivCasopisa;
	
	public RadDTO() {
		
	}
	
	

	public String getPaypalId() {
		return paypalId;
	}



	public void setPaypalId(String paypalId) {
		this.paypalId = paypalId;
	}



	public String getPaypalSecret() {
		return paypalSecret;
	}



	public void setPaypalSecret(String paypalSecret) {
		this.paypalSecret = paypalSecret;
	}



	public String getMerchant_id() {
		return merchant_id;
	}



	public void setMerchant_id(String merchant_id) {
		this.merchant_id = merchant_id;
	}



	public String getNazivCasopisa() {
		return nazivCasopisa;
	}



	public void setNazivCasopisa(String nazivCasopisa) {
		this.nazivCasopisa = nazivCasopisa;
	}



	public Double getUkupnaCena() {
		return ukupnaCena;
	}

	public void setUkupnaCena(Double ukupnaCena) {
		this.ukupnaCena = ukupnaCena;
	}

	public Boolean getIsOpenAccess() {
		return isOpenAccess;
	}

	public void setIsOpenAccess(Boolean isOpenAccess) {
		this.isOpenAccess = isOpenAccess;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public MultipartFile[] getFiles() {
		return files;
	}

	public void setFiles(MultipartFile[] files) {
		this.files = files;
	}

	public String getNaslovRada() {
		return naslovRada;
	}

	public void setNaslovRada(String naslovRada) {
		this.naslovRada = naslovRada;
	}

	public String getNaucnaOblast() {
		return naucnaOblast;
	}

	public void setNaucnaOblast(String naucnaOblast) {
		this.naucnaOblast = naucnaOblast;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getCasopisRada() {
		return casopisRada;
	}

	public void setCasopisRada(String casopisRada) {
		this.casopisRada = casopisRada;
	}

	public String getKljucneReci() {
		return kljucneReci;
	}

	public void setKljucneReci(String kljucneReci) {
		this.kljucneReci = kljucneReci;
	}

	public String getApstrakt() {
		return apstrakt;
	}

	public void setApstrakt(String apstrakt) {
		this.apstrakt = apstrakt;
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
	
	
	
	

}
