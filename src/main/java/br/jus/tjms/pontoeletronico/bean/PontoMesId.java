/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.jus.tjms.pontoeletronico.bean;

import javax.persistence.Column;
import java.io.Serializable;

/**
 *
 * @author marcosm1
 */

public class PontoMesId implements Serializable {
    
	private static final long serialVersionUID = 1L;

    private int codigoEmpresa;

    private int matricula;

    private int ano;

    private int mes;

    public PontoMesId() {
    }

    public PontoMesId(int codigoEmpresa, int matricula, int mes, int ano) {
        this.codigoEmpresa = codigoEmpresa;
        this.matricula = matricula;
        this.mes = mes;
        this.ano = ano;        
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
		if (!(obj instanceof PontoMesId))
			return false;
		final PontoMesId other = (PontoMesId) obj;
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
}