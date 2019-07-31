package udd_upp.model;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Recenzija {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String naslov;
	
	@Column(nullable = false)
	private String komentar; //tekst koji opisuje sta treba doraditi u radu, namenjen autoru
	
	@Column(nullable = true)
	private String komentarZaUrednike; //moze a ne mora da se unese
	
	@Column(nullable = false)
	private Date datum;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Preporuka preporuka;
	
	@Column(nullable = false)
	private boolean vidljivaAutoru;
	
	
	public Recenzija() {
		super();
	}

	public Recenzija(String naslov, String komentar, String komentarZaUrednike, Date datum) {
		super();
		this.naslov = naslov;
		this.komentar = komentar;
		this.komentarZaUrednike = komentarZaUrednike;
		this.datum = datum;
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

	public String getKomentar() {
		return komentar;
	}

	public void setKomentar(String komentar) {
		this.komentar = komentar;
	}

	public String getKomentarZaUrednike() {
		return komentarZaUrednike;
	}

	public void setKomentarZaUrednike(String komentarZaUrednike) {
		this.komentarZaUrednike = komentarZaUrednike;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public Preporuka getPreporuka() {
		return preporuka;
	}

	public void setPreporuka(Preporuka preporuka) {
		this.preporuka = preporuka;
	}

	public boolean isVidljivaAutoru() {
		return vidljivaAutoru;
	}

	public void setVidljivaAutoru(boolean vidljivaAutoru) {
		this.vidljivaAutoru = vidljivaAutoru;
	}
	
}
