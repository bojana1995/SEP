package udd_upp.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Rola {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String naziv;

	@ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "rola_permisija", 
        joinColumns = { @JoinColumn(name = "rola_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "permisija_id") }
    )
	protected List<Permisija> permisije;
	
	
	public Rola() {
		super();
	}

	public Rola(Long id, String naziv) {
		super();
		this.id = id;
		this.naziv = naziv;
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

	public List<Permisija> getPermisije() {
		return permisije;
	}

	public void setPermisije(List<Permisija> permisije) {
		this.permisije = permisije;
	}
	
}
