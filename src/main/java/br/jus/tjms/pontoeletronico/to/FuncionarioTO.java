package br.jus.tjms.pontoeletronico.to;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import br.jus.tjms.pontoeletronico.rest.ResteasyUtil;

public class FuncionarioTO implements Serializable {

	private static final long serialVersionUID = -9202486130898265082L;

	private Integer matricula;
	private Integer empresa;
	private String nome;
	private String lotacao;
	private Integer codigoComarcaSecretaria;
	private Integer codigoInstancia;
	private Integer codigoReduzido;
	private Integer codigoNomeCargo;
	private Integer codigoTipoProvimento;
	private Boolean isentaDigital = false;
	private List<DigitalTO> digitais;

	public Integer getMatricula() {
		return matricula;
	}

	public void setMatricula(Integer matricula) {
		this.matricula = matricula;
	}

	public Integer getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Integer empresa) {
		this.empresa = empresa;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLotacao() {
		return lotacao;
	}

	public void setLotacao(String lotacao) {
		this.lotacao = lotacao;
	}

	public Integer getCodigoComarcaSecretaria() {
		return codigoComarcaSecretaria;
	}

	public void setCodigoComarcaSecretaria(Integer codigoComarcaSecretaria) {
		this.codigoComarcaSecretaria = codigoComarcaSecretaria;
	}

	public Integer getCodigoInstancia() {
		return codigoInstancia;
	}

	public void setCodigoInstancia(Integer codigoInstancia) {
		this.codigoInstancia = codigoInstancia;
	}

	public Integer getCodigoReduzido() {
		return codigoReduzido;
	}

	public void setCodigoReduzido(Integer codigoReduzido) {
		this.codigoReduzido = codigoReduzido;
	}

	public Integer getCodigoNomeCargo() {
		return codigoNomeCargo;
	}

	public void setCodigoNomeCargo(Integer codigoNomeCargo) {
		this.codigoNomeCargo = codigoNomeCargo;
	}

	public Integer getCodigoTipoProvimento() {
		return codigoTipoProvimento;
	}

	public void setCodigoTipoProvimento(Integer codigoTipoProvimento) {
		this.codigoTipoProvimento = codigoTipoProvimento;
	}

	public Boolean getIsentaDigital() {
		return isentaDigital;
	}

	public void setIsentaDigital(Boolean isentaDigital) {
		this.isentaDigital = isentaDigital;
	}

	public List<DigitalTO> getDigitais() {
		return digitais;
	}

	public void setDigitais(List<DigitalTO> digitais) {
		this.digitais = digitais;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(matricula).append(empresa).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (!obj.getClass().isInstance(this)) {
			return false;
		}
		FuncionarioTO other = this.getClass().cast(obj);
		return new EqualsBuilder().append(matricula, other.getMatricula()).append(empresa, other.getEmpresa()).isEquals();
	}

	@Override
	public String toString() {
		return "FuncionarioTO [matricula=" + matricula + ", empresa=" + empresa + ", nome=" + nome + ", lotacao=" + lotacao + ", codigoComarcaSecretaria=" + codigoComarcaSecretaria
				+ ", codigoInstancia=" + codigoInstancia + ", codigoReduzido=" + codigoReduzido + ", codigoNomeCargo=" + codigoNomeCargo + ", codigoTipoProvimento=" + codigoTipoProvimento
				+ ", isentaDigital=" + isentaDigital + "]";
	}

	public static FuncionarioTO fromString(String jsonString) {
		return ResteasyUtil.createGson().fromJson(jsonString, FuncionarioTO.class);
	}

}