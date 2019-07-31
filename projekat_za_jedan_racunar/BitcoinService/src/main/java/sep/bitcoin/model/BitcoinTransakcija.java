package sep.bitcoin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BitcoinTransakcija {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String idIniciraneTransakcije; //id
	
	@Column(nullable = false)
	private String uuid; //(invoice id)
	
	@Column(nullable = false)
	private String naziv; //title
	
	@Column(nullable = false)
	private String vremeKreiranja; //createdAt
	
	@Column(nullable = false)
	private String status;
	
	@Column(nullable = false)
	private String uplatilac; //merchantOrderId
	
	
	public BitcoinTransakcija() {
		super();
	}

	public BitcoinTransakcija(String idIniciraneTransakcije, String uuid, String naziv, String vremeKreiranja, String status, String uplatilac) {
		super();
		this.idIniciraneTransakcije = idIniciraneTransakcije;
		this.uuid = uuid;
		this.naziv = naziv;
		this.vremeKreiranja = vremeKreiranja;
		this.status = status;
		this.uplatilac = uplatilac;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdIniciraneTransakcije() {
		return idIniciraneTransakcije;
	}

	public void setIdIniciraneTransakcije(String idIniciraneTransakcije) {
		this.idIniciraneTransakcije = idIniciraneTransakcije;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getVremeKreiranja() {
		return vremeKreiranja;
	}

	public void setVremeKreiranja(String vremeKreiranja) {
		this.vremeKreiranja = vremeKreiranja;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUplatilac() {
		return uplatilac;
	}

	public void setUplatilac(String uplatilac) {
		this.uplatilac = uplatilac;
	}
	
}
