package br.jus.tjms.pontoeletronico.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sgp.T_PrtPntElt")
public class ParametroEjb implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CD_PrtPntElt", nullable = false)
	private Integer id;

	@Column(name = "DS_IntEnvDgt", nullable = false)
	private String intervaloEnvioDigitais;

	@Column(name = "DS_IntRcbDgt", nullable = false)
	private String intervaloRecebimentoDigitais;

	@Column(name = "DS_IntEnvRgsPnt", nullable = false)
	private String intervaloEnvioRegistroPonto;

	@Column(name = "DS_IntEnvRgsOpr", nullable = false)
	private String intervaloEnvioRegistroOperacoes;

	@Column(name = "NR_NvlTlrVrf", nullable = false)
	private int nivelToleranciaVerificacao;

	@Column(name = "DS_IntScrRlg", nullable = false)
	private String intervaloSincronizacaoRelogio;

	@Column(name = "NR_DiaEnvDgt", nullable = false)
	private int diasEnvioDigitais;

	@Column(name = "DS_SnhMst", nullable = false)
	private String senhaMaster;
	

	public String getSenhaMaster() {
		return senhaMaster;
	}

	public void setSenhaMaster(String senhaMaster) {
		this.senhaMaster = senhaMaster;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIntervaloEnvioDigitais() {
		return intervaloEnvioDigitais;
	}

	public void setIntervaloEnvioDigitais(String intervaloEnvioDigitais) {
		this.intervaloEnvioDigitais = intervaloEnvioDigitais;
	}

	public String getIntervaloRecebimentoDigitais() {
		return intervaloRecebimentoDigitais;
	}

	public void setIntervaloRecebimentoDigitais(String intervaloRecebimentoDigitais) {
		this.intervaloRecebimentoDigitais = intervaloRecebimentoDigitais;
	}

	public String getIntervaloEnvioRegistroPonto() {
		return intervaloEnvioRegistroPonto;
	}

	public void setIntervaloEnvioRegistroPonto(String intervaloEnvioRegistroPonto) {
		this.intervaloEnvioRegistroPonto = intervaloEnvioRegistroPonto;
	}

	public String getIntervaloEnvioRegistroOperacoes() {
		return intervaloEnvioRegistroOperacoes;
	}

	public void setIntervaloEnvioRegistroOperacoes(
			String intervaloEnvioRegistroOperacoes) {
		this.intervaloEnvioRegistroOperacoes = intervaloEnvioRegistroOperacoes;
	}

	public int getNivelToleranciaVerificacao() {
		return nivelToleranciaVerificacao;
	}

	public void setNivelToleranciaVerificacao(int nivelToleranciaVerificacao) {
		this.nivelToleranciaVerificacao = nivelToleranciaVerificacao;
	}	


    public String getIntervaloSincronizacaoRelogio() {
		return intervaloSincronizacaoRelogio;
	}

	public void setIntervaloSincronizacaoRelogio(
			String intervaloSincronizacaoRelogio) {
		this.intervaloSincronizacaoRelogio = intervaloSincronizacaoRelogio;
	}

	public int getDiasEnvioDigitais() {
		return diasEnvioDigitais;
	}

	public void setDiasEnvioDigitais(int diasEnvioDigitais) {
		this.diasEnvioDigitais = diasEnvioDigitais;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ParametroEjb)) {
            return false;
        }
        ParametroEjb other = (ParametroEjb) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

	@Override
	public String toString() {
		return "ParametroEjb [id=" + id + ", intervaloEnvioDigitais=" + intervaloEnvioDigitais
				+ ", intervaloRecebimentoDigitais=" + intervaloRecebimentoDigitais + ", intervaloEnvioRegistroPonto="
				+ intervaloEnvioRegistroPonto + ", intervaloEnvioRegistroOperacoes=" + intervaloEnvioRegistroOperacoes
				+ ", nivelToleranciaVerificacao=" + nivelToleranciaVerificacao + ", intervaloSincronizacaoRelogio="
				+ intervaloSincronizacaoRelogio + ", diasEnvioDigitais=" + diasEnvioDigitais + ", senhaMaster="
				+ senhaMaster + "]";
	}
	
}
