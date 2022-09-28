/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.jus.tjms.pontoeletronico.bean;

import java.io.Serializable;

import javax.persistence.Column;

/**
 *
 * @author marcosm1
 */

public class HoraPontoOriginalPK implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Column(name = "CD_EmpSGP", nullable = false)
	private int codigoEmpresa;
	
	@Column(name = "CD_Fnc", nullable = false)
    private int matricula;
	
	@Column(name = "NR_Ano", nullable = false)
    private int ano;
	
	@Column(name = "NR_Mes", nullable = false)
    private int mes;
	
	@Column(name = "NR_Dia", nullable = false)
    private int dia;
	
	@Column(name = "HR_Etr", nullable = false)
    private int horaEntrada;
    
    
    public HoraPontoOriginalPK() {
    }

    public HoraPontoOriginalPK(int codigoEmpresa, int matricula, int dia, int mes, int ano, int horaEntrada) {
        this.codigoEmpresa = codigoEmpresa;
        this.matricula = matricula;
        this.dia = dia;
        this.mes = mes; 
        this.ano = ano;             
        this.horaEntrada = horaEntrada;
    }   
    

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ano;
		result = prime * result + codigoEmpresa;
		result = prime * result + dia;
		result = prime * result + horaEntrada;
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
		if (!(obj instanceof HoraPontoOriginalPK))
			return false;
		final HoraPontoOriginalPK other = (HoraPontoOriginalPK) obj;
		if (ano != other.ano)
			return false;
		if (codigoEmpresa != other.codigoEmpresa)
			return false;
		if (dia != other.dia)
			return false;
		if (horaEntrada != other.horaEntrada)
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

    public int getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(int horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

}
