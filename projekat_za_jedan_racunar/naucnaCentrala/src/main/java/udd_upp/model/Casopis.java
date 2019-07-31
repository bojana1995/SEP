package udd_upp.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Marko
 *
 */
@Entity
public class Casopis {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String naziv;
	
	@Column(nullable = false)
	private int ISSNBroj; //osmocifreni kod
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private TipCasopisa tipCasopisa;
	
	@Column(nullable = true)
	private String paypalSecret;
	
	@Column(nullable = true)
	private String paypalId;
	
	@Column(nullable = false) //nacin naplate clanarine
	private boolean clanarinaSeNaplacujeCitaocu; // true -> tip je SA_PRETPLATOM; false -> tip je OPEN_ACCESS
	
	//@Lob
	//@JsonIgnore
	@Column(nullable = true)
	//@Enumerated(EnumType.STRING)
	private ArrayList<TipPlacanja> tipoviPlacanja; // (SEP)
	
	@Column(nullable = false)
	private double amount; //iznosClanarine (SEP)
	
	@Column(nullable = false)
	private String merchantId; //Long? (SEP)
	
	@Column(nullable = false)
	private String merchantPassword; // (SEP)
	
	@Transient
	private String pan;
	
	@Transient
	private String sigurnosniKod;
	
	@ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "casopis_naucnaOblast", 
        joinColumns = { @JoinColumn(name = "casopis_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "naucnaOblast_id") }
    )
	private Set<NaucnaOblast> naucneOblasti = new HashSet<>();
	
	@ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "casopis_recenzenti", 
            joinColumns = { @JoinColumn(name = "casopis_id") }, 
            inverseJoinColumns = { @JoinColumn(name = "recenzent_id") }
        )
	private Set<Korisnik> recenzenti = new HashSet<>();
	
	@OneToMany(fetch = FetchType.LAZY)
	private List<Rad> radovi;
	

	
	//povratna veza
	@JsonIgnore
	@OneToMany(mappedBy = "casopis", fetch = FetchType.EAGER)
	private Set<Korisnik> urednici;

	
	public Casopis() {
		super();
	}

	public Casopis(String naziv, int iSSNBroj, TipCasopisa tipCasopisa, ArrayList<TipPlacanja> tipoviPlacanja, double amount, String merchantId,
			String merchantPassword, Set<NaucnaOblast> naucneOblasti,String paypalId, String paypalSecret) {
		super();
		this.naziv = naziv;
		ISSNBroj = iSSNBroj;
		this.tipCasopisa = tipCasopisa;
		this.tipoviPlacanja = tipoviPlacanja;
		this.amount = amount;
		this.merchantId = merchantId;
		this.merchantPassword = merchantPassword;
		this.naucneOblasti = naucneOblasti;
		this.paypalSecret = paypalSecret;
		this.paypalId = paypalId;
	}
	
	

	public String getPaypalSecret() {
		return paypalSecret;
	}

	public void setPaypalSecret(String paypalSecret) {
		this.paypalSecret = paypalSecret;
	}

	public String getPaypalId() {
		return paypalId;
	}

	public void setPaypalId(String paypalId) {
		this.paypalId = paypalId;
	}
	
	

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getSigurnosniKod() {
		return sigurnosniKod;
	}

	public void setSigurnosniKod(String sigurnosniKod) {
		this.sigurnosniKod = sigurnosniKod;
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

	public int getISSNBroj() {
		return ISSNBroj;
	}

	public void setISSNBroj(int iSSNBroj) {
		ISSNBroj = iSSNBroj;
	}

	public TipCasopisa getTipCasopisa() {
		return tipCasopisa;
	}

	public void setTipCasopisa(TipCasopisa tipCasopisa) {
		this.tipCasopisa = tipCasopisa;
	}

	public boolean isClanarinaSeNaplacujeCitaocu() {
		return clanarinaSeNaplacujeCitaocu;
	}

	public void setClanarinaSeNaplacujeCitaocu(boolean clanarinaSeNaplacujeCitaocu) {
		this.clanarinaSeNaplacujeCitaocu = clanarinaSeNaplacujeCitaocu;
	}

	public ArrayList<TipPlacanja> getTipoviPlacanja() {
		return tipoviPlacanja;
	}

	public void setTipoviPlacanja(ArrayList<TipPlacanja> tipoviPlacanja) {
		this.tipoviPlacanja = tipoviPlacanja;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantPassword() {
		return merchantPassword;
	}

	public void setMerchantPassword(String merchantPassword) {
		this.merchantPassword = merchantPassword;
	}

	public Set<NaucnaOblast> getNaucneOblasti() {
		return naucneOblasti;
	}

	public void setNaucneOblasti(Set<NaucnaOblast> naucneOblasti) {
		this.naucneOblasti = naucneOblasti;
	}

	public Set<Korisnik> getRecenzenti() {
		return recenzenti;
	}

	public void setRecenzenti(Set<Korisnik> recenzenti) {
		this.recenzenti = recenzenti;
	}


	public List<Rad> getRadovi() {
		return radovi;
	}

	public void setRadovi(List<Rad> radovi) {
		this.radovi = radovi;
	}


	public Set<Korisnik> getUrednici() {
		return urednici;
	}

	public void setUrednici(Set<Korisnik> urednici) {
		this.urednici = urednici;
	}

}
