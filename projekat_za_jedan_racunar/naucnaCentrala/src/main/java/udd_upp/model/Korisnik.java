package udd_upp.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Korisnik {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = true) 
	private String lozinka; //koautor ne mora biti registrovan
	
	@Column(nullable = false)
	private boolean aktiviranNalog; //true -> registrovan; false -> neregistrovan
	
	/*@Column(nullable = false)
	@Enumerated(EnumType.STRING)*/
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_rola", nullable = false)
	private Rola rola;
	
	@Column(nullable = false)
	private String ime;
	
	@Column(nullable = false)
	private String prezime;
	
	@Column(nullable = false)
	private String grad;
	
	@Column(nullable = false)
	private String drzava;
	
	@Column(nullable = true)
	private String titula; //imaju je urednik i recenzent
	
	//private NaucnaCentrala naucnaCentrala; //za multitenancy nacin rada
	
	@ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "korisnik_naucnaOblast", 
        joinColumns = { @JoinColumn(name = "korisnik_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "naucnaOblast_id") }
    )
	private List<NaucnaOblast> naucneOblastiKojePokriva; //vazi za urednike i recenzente
	
	@ManyToOne
    @JoinColumn(name="urednikCasopisa_id", nullable = true)
    private Casopis casopis;
	
	//povratna veza
	@JsonIgnore
	@ManyToMany(mappedBy = "recenzenti", fetch = FetchType.EAGER)
	private Set<Casopis> casopisi = new HashSet<>();
	
	
	//povratna veza
	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER)
	private List<Rad> radCijiSamAutor;
	
	
	public Korisnik() {
		super();
	}

	public Korisnik(String email, String lozinka, String ime, String prezime, String grad, String drzava, String titula, List<NaucnaOblast> naucneOblastiKojePokriva) {
		super();
		this.email = email;
		this.lozinka = lozinka;
		this.ime = ime;
		this.prezime = prezime;
		this.grad = grad;
		this.drzava = drzava;
		this.titula = titula;
		this.naucneOblastiKojePokriva = naucneOblastiKojePokriva;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

	public boolean isAktiviranNalog() {
		return aktiviranNalog;
	}

	public void setAktiviranNalog(boolean aktiviranNalog) {
		this.aktiviranNalog = aktiviranNalog;
	}

	public Rola getRola() {
		return rola;
	}

	public void setRola(Rola rola) {
		this.rola = rola;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getGrad() {
		return grad;
	}

	public void setGrad(String grad) {
		this.grad = grad;
	}

	public String getDrzava() {
		return drzava;
	}

	public void setDrzava(String drzava) {
		this.drzava = drzava;
	}

	public String getTitula() {
		return titula;
	}

	public void setTitula(String titula) {
		this.titula = titula;
	}

	public List<NaucnaOblast> getNaucneOblastiKojePokriva() {
		return naucneOblastiKojePokriva;
	}

	public void setNaucneOblastiKojePokriva(List<NaucnaOblast> naucneOblastiKojePokriva) {
		this.naucneOblastiKojePokriva = naucneOblastiKojePokriva;
	}

	public Set<Casopis> getCasopisi() {
		return casopisi;
	}

	public void setCasopisi(Set<Casopis> casopisi) {
		this.casopisi = casopisi;
	}

	public Casopis getCasopis() {
		return casopis;
	}

	public void setCasopis(Casopis casopis) {
		this.casopis = casopis;
	}


	public List<Rad> getRadCijiSamAutor() {
		return radCijiSamAutor;
	}

	public void setRadCijiSamAutor(List<Rad> radCijiSamAutor) {
		this.radCijiSamAutor = radCijiSamAutor;
	}


}
