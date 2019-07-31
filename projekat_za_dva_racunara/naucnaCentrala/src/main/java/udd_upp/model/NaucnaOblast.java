package udd_upp.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class NaucnaOblast {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String naziv;
	
	@Column(nullable = false)
	private String opis;
	
	//povratna veza
	@JsonIgnore
	@ManyToMany(mappedBy = "naucneOblasti", fetch = FetchType.EAGER)
	private Set<Casopis> casopisi = new HashSet<>();
	
	//povratna veza
	@JsonIgnore
	@ManyToMany(mappedBy = "naucneOblastiKojePokriva", fetch = FetchType.EAGER)
	private Set<Korisnik> uredniciRecenzenti = new HashSet<>();
	
	//povratna veza
	@JsonIgnore
	@OneToMany(mappedBy = "naucnaOblast", fetch = FetchType.EAGER)
	private List<Rad> rad;
	
	
	public NaucnaOblast() {
		super();
	}

	public NaucnaOblast(String naziv, String opis) {
		super();
		this.naziv = naziv;
		this.opis = opis;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public Set<Casopis> getCasopisi() {
		return casopisi;
	}

	public void setCasopisi(Set<Casopis> casopisi) {
		this.casopisi = casopisi;
	}

	public Set<Korisnik> getUredniciRecenzenti() {
		return uredniciRecenzenti;
	}

	public void setUredniciRecenzenti(Set<Korisnik> uredniciRecenzenti) {
		this.uredniciRecenzenti = uredniciRecenzenti;
	}

	public List<Rad> getRad() {
		return rad;
	}

	public void setRad(List<Rad> rad) {
		this.rad = rad;
	}

	
	
}
