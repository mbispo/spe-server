package br.jus.tjms.pontoeletronico.bean;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author marcosm1
 */
@Entity
@Table(name = "T_PntDia", schema="sgp")
@IdClass(PontoDiaPK.class)
public class PontoDia implements Serializable {
	
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
	
	@Id
	@Column(name = "NR_Dia")
    private int dia;
    
    @Column(name = "CD_RdzOrn")
    private Integer codigoReduzidoOrganograma;
    
    @Column(name="CD_TpoPrv")
    private Integer codigoProvimento;
    
    @Column(name="CD_NmeCrg")
    private Integer codigoNomeCargo;
    
    @Column(name = "CD_LclMnc")
    private Integer codigoMunicipio;
    
    @JoinColumns({@JoinColumn(name = "CD_EmpSGP", referencedColumnName = "CD_EmpSGP", insertable = false, updatable = false, nullable = false), @JoinColumn(name = "CD_Fnc", referencedColumnName = "CD_Fnc", insertable = false, updatable = false, nullable = false), @JoinColumn(name = "NR_Ano", referencedColumnName = "NR_Ano", insertable = false, updatable = false, nullable = false), @JoinColumn(name = "NR_Mes", referencedColumnName = "NR_Mes", insertable = false, updatable = false, nullable = false)})
    @ManyToOne
    private PontoMes pontoMes;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pontoDia")
    private Collection<HoraPonto> horaPontoCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pontoDia")
    private Collection<HoraPontoOriginal> horaPontoOriginalCollection;
    
    public PontoDia() {
    } 
    
    public PontoDia(int codigoEmpresa, int matricula, int dia, int mes, int ano) {
        this.codigoEmpresa = codigoEmpresa;
        this.matricula = matricula;
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;    
        this.horaPontoCollection = new ArrayList<HoraPonto>();
        this.horaPontoOriginalCollection = new ArrayList<HoraPontoOriginal>();
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
		if (!(obj instanceof PontoDia))
			return false;
		final PontoDia other = (PontoDia) obj;
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

	public void setDia(int dia) {
		this.dia = dia;
	}

	public int getDia() {
		return dia;
	}	

    public Integer getCodigoReduzidoOrganograma() {
        return codigoReduzidoOrganograma;
    }

    public void setCodigoReduzidoOrganograma(Integer cDRdzOrn) {
        this.codigoReduzidoOrganograma = cDRdzOrn;
    }

    public PontoMes getPontoMes() {
        return pontoMes;
    }

    public void setPontoMes(PontoMes pontoMes) {
        this.pontoMes = pontoMes;
    }

    public void setCodigoProvimento(Integer codigoProvimento) {
		this.codigoProvimento = codigoProvimento;
	}

	public Integer getCodigoProvimento() {
		return codigoProvimento;
	}

	public void setCodigoNomeCargo(Integer codigoNomeCargo) {
		this.codigoNomeCargo = codigoNomeCargo;
	}

	public Integer getCodigoNomeCargo() {
		return codigoNomeCargo;
	}
	
	public Integer getCodigoMunicipio() {
		return codigoMunicipio;
	}

	public void setCodigoMunicipio(Integer codigoMunicipio) {
		this.codigoMunicipio = codigoMunicipio;
	}    


	public Collection<HoraPonto> getHoraPontoCollection() {
        return horaPontoCollection;
    }

    public void setHoraPontoCollection(Collection<HoraPonto> horaPontoCollection) {
        this.horaPontoCollection = horaPontoCollection;
    }

	public Collection<HoraPontoOriginal> getHoraPontoOriginalCollection() {
		return horaPontoOriginalCollection;
	}

	public void setHoraPontoOriginalCollection(
			Collection<HoraPontoOriginal> horaPontoOriginalCollection) {
		this.horaPontoOriginalCollection = horaPontoOriginalCollection;
	}

        
}