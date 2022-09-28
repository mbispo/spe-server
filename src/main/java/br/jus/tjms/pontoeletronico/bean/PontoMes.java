package br.jus.tjms.pontoeletronico.bean;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.jus.tjms.pontoeletronico.rest.ResteasyUtil;

/**
 *
 * @author marcosm1
 */
@Entity
@Table(name = "T_PntMes", schema = "sgp")
@IdClass(PontoMesId.class)
public class PontoMes implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CD_EmpSGP")
	private int codigoEmpresa;

	@Id
	@Column(name = "CD_Fnc")
	private int matricula;

	@Id
	@Column(name = "NR_Ano")
	private int ano;

	@Id
	@Column(name = "NR_Mes")
	private int mes;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "pontoMes")
	private Collection<PontoDia> pontoDiaCollection;

	public PontoMes() {

	}

	public PontoMes(int codigoEmpresa, int matricula, int mes, int ano) {
		this.codigoEmpresa = codigoEmpresa;
		this.matricula = matricula;
		this.mes = mes;
		this.ano = ano;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ano;
		result = prime * result + codigoEmpresa;
		result = prime * result + matricula;
		result = prime * result + mes;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PontoMes))
			return false;
		final PontoMes other = (PontoMes) obj;
		if (ano != other.ano)
			return false;
		if (codigoEmpresa != other.codigoEmpresa)
			return false;
		if (matricula != other.matricula)
			return false;
		if (mes != other.mes)
			return false;
		return true;
	}

	public void setCodigoEmpresa(int codigoEmpresa) {
		this.codigoEmpresa = codigoEmpresa;
	}

	public int getCodigoEmpresa() {
		return codigoEmpresa;
	}

	public void setMatricula(int matricula) {
		this.matricula = matricula;
	}

	public int getMatricula() {
		return matricula;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public int getAno() {
		return ano;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getMes() {
		return mes;
	}

	public Collection<PontoDia> getPontoDiaCollection() {
		return pontoDiaCollection;
	}

	public void setPontoDiaCollection(Collection<PontoDia> pontoDiaCollection) {
		this.pontoDiaCollection = pontoDiaCollection;
	}
	
	public static PontoMes fromString(String jsonString) {
		return ResteasyUtil.createGson().fromJson(jsonString, PontoMes.class);
	}

}