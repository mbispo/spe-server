package br.jus.tjms.pontoeletronico.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import br.jus.tjms.pontoeletronico.rest.ResteasyUtil;
import br.jus.tjms.pontoeletronico.to.DigitalTO;
import br.jus.tjms.pontoeletronico.to.FuncionarioTO;

/**
 * 
 * Bean que sera usado para obter dados do funcionário pela aplicacao cliente
 * Contem apenas informacoes necessarias para o sistema de ponto eletronico
 * 
 * @author marcosbispo
 * 
 */

@Entity
@Table(name = "sgp.T_VFnc001")
@IdClass(FuncionarioEjbPK.class)
public class FuncionarioEjb implements Serializable {

	private static final long serialVersionUID = -8098795173962355762L;

	@Id
	@Column(name = "CD_Fnc")
	private Integer matricula;

	@Id
	@Column(name = "CD_EmpSgp")
	private Integer empresa;

	@Column(name = "NM_Fnc")
	private String nome;
	
	@Column(name = "DS_CnjOrnFnl")
	private String lotacao;

	@Column(name = "CD_CmrScrFnl")
	private Integer codigoComarcaSecretaria;
	
	@Column(name = "CD_HrrFnl")
	private Integer codigoInstancia;

	@Column(name="CD_RdzOrnFnl")
	private Integer codigoReduzido;

	@Column(name="cd_nmecrgfnl")
	private Integer codigoNomeCargo;
	
	@Column(name="CD_TpoPrvFnl")
	private Integer codigoTipoProvimento;
	
	@Column(name = "FL_IstDgt")
	private Boolean isentaDigital = false;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "funcionario")
	private List<DigitalEjb> digitais = new ArrayList<>();

	public FuncionarioEjb() {
		super();
	}
	
	public FuncionarioEjb(Integer empresa, Integer matricula, String nome, String lotacao, Integer codigoComarcaSecretaria, Integer codigoInstancia, Integer codigoReduzido, Integer codigoNomeCargo,
			Integer codigoTipoProvimento, Boolean isentaDigital, List<DigitalEjb> digitais) {
		super();
		this.matricula = matricula;
		this.empresa = empresa;
		this.nome = nome;
		this.lotacao = lotacao;
		this.codigoComarcaSecretaria = codigoComarcaSecretaria;
		this.codigoInstancia = codigoInstancia;
		this.codigoReduzido = codigoReduzido;
		this.codigoNomeCargo = codigoNomeCargo;
		this.codigoTipoProvimento = codigoTipoProvimento;
		this.isentaDigital = isentaDigital;
		this.digitais = digitais;
	}
	
	public FuncionarioEjb(Integer empresa, Integer matricula) {
		this.matricula = matricula;
		this.empresa = empresa;
	}
	
	public FuncionarioTO toTO() {
		FuncionarioTO f = new FuncionarioTO();
		f.setCodigoComarcaSecretaria(codigoComarcaSecretaria);
		f.setCodigoInstancia(codigoInstancia);
		f.setCodigoNomeCargo(codigoNomeCargo);
		f.setCodigoReduzido(codigoReduzido);
		f.setCodigoTipoProvimento(codigoTipoProvimento);
		f.setDigitais(digitaisToTO());
		f.setEmpresa(empresa);
		f.setIsentaDigital(isentaDigital);
		f.setLotacao(lotacao);
		f.setMatricula(matricula);
		f.setNome(nome);
		return f;
	}

	private List<DigitalTO> digitaisToTO() {
		List<DigitalTO> lista = new ArrayList<DigitalTO>();
		if (digitais!=null) {
			for (DigitalEjb digitalEjb : digitais) {
				lista.add(digitalEjb.toTO());
			}
		}
		return lista;
	}
	
	public FuncionarioEjb(FuncionarioTO to) {
		setCodigoComarcaSecretaria(to.getCodigoComarcaSecretaria());
		setCodigoInstancia(to.getCodigoInstancia());
		setCodigoNomeCargo(to.getCodigoNomeCargo());
		setCodigoReduzido(to.getCodigoReduzido());
		setCodigoTipoProvimento(to.getCodigoTipoProvimento());
		
		setDigitais(new ArrayList<>());
		
		if (to.getDigitais()!=null) {
			for (DigitalTO digitalTo : to.getDigitais()) {
				getDigitais().add(new DigitalEjb(digitalTo));
			}
		}
		
		setEmpresa(to.getEmpresa());
		setIsentaDigital(to.getIsentaDigital());
		setLotacao(to.getLotacao());
		setMatricula(to.getMatricula());
		setNome(to.getNome());
	}

	public Integer getId() {
		return matricula;
	}

	public void setId(Integer id) {
		setMatricula(id);
	}

	public String getNome() {
		return nome;
	}
	
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

	public Boolean isIsentaDigital() {
		return isentaDigital;
	}

	public void setIsentaDigital(Boolean isentaDigital) {
		this.isentaDigital = isentaDigital;
	}

	public void setDigitais(List<DigitalEjb> digitais) {
		this.digitais = digitais;
	}

	public List<DigitalEjb> getDigitais() {
		return digitais;
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
		FuncionarioEjb other = this.getClass().cast(obj);
		return new EqualsBuilder().append(matricula, other.getMatricula()).append(empresa, other.getEmpresa()).isEquals();
	}

	@Override
	public String toString() {
		return "FuncionarioEjb [matricula=" + matricula +", empresa="+empresa + ", nome=" + nome + ", lotacao=" + lotacao + ", codigoComarcaSecretaria=" + codigoComarcaSecretaria + ", codigoInstancia="
				+ codigoInstancia + ", codigoReduzido=" + codigoReduzido + ", codigoNomeCargo=" + codigoNomeCargo + ", codigoTipoProvimento=" + codigoTipoProvimento + ", isentaDigital="
				+ isentaDigital +"]";
	}
	
	public static FuncionarioEjb fromString(String jsonString) {
		return ResteasyUtil.createGson().fromJson(jsonString, FuncionarioEjb.class);
	}
	

}