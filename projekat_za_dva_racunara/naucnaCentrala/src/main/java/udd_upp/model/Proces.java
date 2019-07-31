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
public class Proces {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private Date pocetak;
	
	@Column(nullable = false)
	private Date kraj;
	
	//kako modelovati kome je proces dodeljen???
	
	@Enumerated(EnumType.STRING)
	private TipProcesa tipProcesa;
	
	@OneToOne(optional = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Rad radKojiJeUProcesu; //za koji se vrsi obrada podnetog teksta

	
	public Proces() {
		super();
	}

	public Proces(Long id, Date pocetak, Date kraj, Rad radKojiJeUProcesu, TipProcesa tipProcesa) {
		super();
		this.id = id;
		this.pocetak = pocetak;
		this.kraj = kraj;
		this.radKojiJeUProcesu = radKojiJeUProcesu;
		this.tipProcesa = tipProcesa;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getPocetak() {
		return pocetak;
	}

	public void setPocetak(Date pocetak) {
		this.pocetak = pocetak;
	}

	public Date getKraj() {
		return kraj;
	}

	public void setKraj(Date kraj) {
		this.kraj = kraj;
	}

	public TipProcesa getTipProcesa() {
		return tipProcesa;
	}

	public void setTipProcesa(TipProcesa tipProcesa) {
		this.tipProcesa = tipProcesa;
	}

	public Rad getRadKojiJeUProcesu() {
		return radKojiJeUProcesu;
	}

	public void setRadKojiJeUProcesu(Rad radKojiJeUProcesu) {
		this.radKojiJeUProcesu = radKojiJeUProcesu;
	}
	
}
