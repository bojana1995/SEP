package udd_upp.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Rad {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String naslov;
	
	@Column(nullable = false)
	private String kljucniPojmovi;
	
	@Column(nullable = false)
	private double amount;
	
	@Column(nullable = false)
	private String apstrakt;
	
	@Column(nullable = false)
	private String fajlName;
	
	@ManyToOne( fetch = FetchType.EAGER)
	private Korisnik autor; //ne znam da li treba da postoji i ovo polje...
	
	@ManyToOne(fetch = FetchType.EAGER)
	private NaucnaOblast naucnaOblast; //u koju se rad primarno klasifikuje
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	private Casopis casopis;
	

	public Rad() {
		super();
	}

	public Rad(String naslov, String kljucniPojmovi, String apstrakt, String finalnaVerzijaPDF,
			NaucnaOblast naucnaOblast,double amount) {
		super();
		this.naslov = naslov;
		this.kljucniPojmovi = kljucniPojmovi;
		this.apstrakt = apstrakt;
		this.fajlName = finalnaVerzijaPDF;
		this.naucnaOblast = naucnaOblast;
		this.amount = amount;
	}
	
	

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNaslov() {
		return naslov;
	}

	public void setNaslov(String naslov) {
		this.naslov = naslov;
	}

	public String getKljucniPojmovi() {
		return kljucniPojmovi;
	}

	public void setKljucniPojmovi(String kljucniPojmovi) {
		this.kljucniPojmovi = kljucniPojmovi;
	}

	public String getApstrakt() {
		return apstrakt;
	}

	public void setApstrakt(String apstrakt) {
		this.apstrakt = apstrakt;
	}

	public Korisnik getAutor() {
		return autor;
	}

	public void setAutor(Korisnik autor) {
		this.autor = autor;
	}


	public NaucnaOblast getNaucnaOblast() {
		return naucnaOblast;
	}

	public void setNaucnaOblast(NaucnaOblast naucnaOblast) {
		this.naucnaOblast = naucnaOblast;
	}

	public Casopis getCasopis() {
		return casopis;
	}

	public void setCasopis(Casopis casopis) {
		this.casopis = casopis;
	}

	public String getFajlName() {
		return fajlName;
	}

	public void setFajlName(String fajlName) {
		this.fajlName = fajlName;
	}

}
