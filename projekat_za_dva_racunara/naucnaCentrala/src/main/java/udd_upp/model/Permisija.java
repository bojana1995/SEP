package udd_upp.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Permisija {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String naziv;

	//povratna veza
	@JsonIgnore
	@ManyToMany(mappedBy = "permisije", fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
	private Set<Rola> role = new HashSet<>();
	
	
	public Permisija() {
		super();
	}

	public Permisija(Long id, String naziv) {
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

	public Set<Rola> getRole() {
		return role;
	}

	public void setRole(Set<Rola> role) {
		this.role = role;
	}
	
}
