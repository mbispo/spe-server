package br.jus.tjms.pontoeletronico.bean;

import java.io.Serializable;

/**
 *
 * @author marcosm1
 */

public class PontoDiaPK implements Serializable {    

	private static final long serialVersionUID = 1L;

    private int codigoEmpresa;

    private int matricula;

    private int ano;

    private int mes;

    private int dia;

    public PontoDiaPK() {
    }

    public PontoDiaPK(short codigoEmpresa, int matricula, int dia, int mes, int ano) {
        this.codigoEmpresa = codigoEmpresa;
        this.matricula = matricula;
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;        
    }
    
    

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ano;
		result = prime * result + codigoEmpresa;
		result = prime * result + dia;
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
		if (!(obj instanceof PontoDiaPK))
			return false;
		final PontoDiaPK other = (PontoDiaPK) obj;
		if (ano != other.ano)
			return false;
		if (codigoEmpresa != other.codigoEmpresa)
			return false;
		if (dia != other.dia)
			return false;
		if (matricula != other.matricula)
			return false;
		if (mes != other.mes)
			return false;
		return true;
	}

	public int getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setCodigoEmpresa(int codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }
}
