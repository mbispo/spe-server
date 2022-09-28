package br.jus.tjms.pontoeletronico.bean;

import java.io.Serializable;

import javax.persistence.Column;

public class FuncionarioEjbPK implements Serializable {
	
	private static final long serialVersionUID = 368025216262824233L;

	@Column(name = "CD_Fnc", nullable = false)
    private Integer matricula;
	
	@Column(name = "CD_EmpSGP", nullable = false)
    private Integer empresa;
	
	public FuncionarioEjbPK() {
	}

	public FuncionarioEjbPK(Integer matricula, Integer empresa) {
		super();
		this.matricula = matricula;
		this.empresa = empresa;
	}

	public void setMatricula(Integer matricula) {
		this.matricula = matricula;
	}

	public Integer getMatricula() {
		return matricula;
	}

	public void setEmpresa(Integer empresa) {
		this.empresa = empresa;
	}

	public Integer getEmpresa() {
		return empresa;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + empresa;
		result = prime * result + matricula;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final FuncionarioEjbPK other = (FuncionarioEjbPK) obj;
		if (empresa != other.empresa)
			return false;
		if (matricula != other.matricula)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FuncionarioEjbPK [matricula=" + matricula + ", empresa=" + empresa + "]";
	}
	
}