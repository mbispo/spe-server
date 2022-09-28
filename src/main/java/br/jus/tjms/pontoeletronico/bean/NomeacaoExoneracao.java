package br.jus.tjms.pontoeletronico.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="v_FncDia", schema="sgp")
public class NomeacaoExoneracao implements Serializable{
	
	private static final long serialVersionUID = 111083327057942604L;

	@Id 
	@Column(name="CD_OcrFnc")
	private Integer codigoOcorrencia;	
	
	@Column(name="CD_TpoPrv")
	private Integer codigoTipoProvimento;
	
	@Column(name="DS_TpoPrv")
	private String descricaoTipoProvimento;

	@Column(name="CD_NmeCrg")
	private Integer codigoNomeCargo;
	
	@Column(name="DS_NmeCrg")
	private String descricaoNomeCargo;
	
	@Column(name="CD_RdzOrnFnl")
	private Integer codigoReduzidoOrganograma;
	
	@Column(name="CD_Fnc")
	private Integer matricula;
	
	@Column(name="CD_EmpSgp")
	private Integer codigoEmpresa;
	
	@Column(name="DT_Inc")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataInicial;
	
	@Column(name="DT_Fnl")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataFinal;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((codigoOcorrencia == null) ? 0 : codigoOcorrencia.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof NomeacaoExoneracao))
			return false;
		final NomeacaoExoneracao other = (NomeacaoExoneracao) obj;
		if (codigoOcorrencia == null) {
			if (other.codigoOcorrencia != null)
				return false;
		} else if (!codigoOcorrencia.equals(other.codigoOcorrencia))
			return false;
		return true;
	}

	public Integer getCodigoTipoProvimento() {
		return codigoTipoProvimento;
	}

	public void setCodigoTipoProvimento(Integer codigoTipoProvimento) {
		this.codigoTipoProvimento = codigoTipoProvimento;
	}

	public String getDescricaoTipoProvimento() {
		return descricaoTipoProvimento;
	}

	public void setDescricaoTipoProvimento(String descricaoTipoProvimento) {
		this.descricaoTipoProvimento = descricaoTipoProvimento;
	}

	public Integer getCodigoNomeCargo() {
		return codigoNomeCargo;
	}

	public void setCodigoNomeCargo(Integer codigoNomeCargo) {
		this.codigoNomeCargo = codigoNomeCargo;
	}

	public String getDescricaoNomeCargo() {
		return descricaoNomeCargo;
	}

	public void setDescricaoNomeCargo(String descricaoNomeCargo) {
		this.descricaoNomeCargo = descricaoNomeCargo;
	}

	public Integer getMatricula() {
		return matricula;
	}

	public void setMatricula(Integer matricula) {
		this.matricula = matricula;
	}

	public Integer getCodigoEmpresa() {
		return codigoEmpresa;
	}

	public void setCodigoEmpresa(Integer codigoEmpresa) {
		this.codigoEmpresa = codigoEmpresa;
	}

	public Integer getCodigoOcorrencia() {
		return codigoOcorrencia;
	}

	public void setCodigoOcorrencia(Integer codigoOcorrencia) {
		this.codigoOcorrencia = codigoOcorrencia;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public Integer getCodigoReduzido() {
		return codigoReduzidoOrganograma;
	}

	public void setCodigoReduzido(Integer codigoReduzido) {
		this.codigoReduzidoOrganograma = codigoReduzido;
	}
}

