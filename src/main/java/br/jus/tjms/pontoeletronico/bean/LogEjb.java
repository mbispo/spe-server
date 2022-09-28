package br.jus.tjms.pontoeletronico.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

import br.jus.tjms.pontoeletronico.rest.ResteasyUtil;

/**
 * Entidade usada para armazenar o log de atividades do sistema de ponto
 * eletronico
 */
@Entity
@Table(name = "sgp.T_LogSPE")
public class LogEjb implements Serializable {

	private static final long serialVersionUID = 2904147866953408547L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date datahora;

	private String mensagem;
	private String tipo;
	private String classe;
	private String metodo;

	@Column(name="CD_EmpSGP",nullable = true)
	private Integer empresa;

	@Column(name="CD_Fnc",nullable = true)
	private Integer matricula;

	@Column(name="CD_EmpSGPAdm",nullable = true)
	private Integer empresaAdministrador;

	@Column(name="CD_FncAdm",nullable = true)
	private Integer matriculaAdministrador;

	@Column(nullable = true)
	private String tipoOperacao;

	@Column(name="CD_RdzOrn",nullable = true)
	private Integer codigoReduzidoOrganograma;

	@Column(name="CD_HstAtoEstAdm",nullable = true)
	private Integer codigoAto;

	public LogEjb() {
	}

	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	public Date getDatahora() {
		return datahora;
	}

	public void setDatahora(Date newdatahora) {
		this.datahora = newdatahora;
	}


	public Integer getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Integer empresa) {
		this.empresa = empresa;
	}

	public Integer getMatricula() {
		return matricula;
	}

	public void setMatricula(Integer matricula) {
		this.matricula = matricula;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getMetodo() {
		return metodo;
	}

	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Integer getEmpresaAdministrador() {
		return empresaAdministrador;
	}

	public void setEmpresaAdministrador(Integer empresaAdministrador) {
		this.empresaAdministrador = empresaAdministrador;
	}

	public Integer getMatriculaAdministrador() {
		return matriculaAdministrador;
	}

	public void setMatriculaAdministrador(Integer matriculaAdministrador) {
		this.matriculaAdministrador = matriculaAdministrador;
	}

	public String getTipoOperacao() {
		return tipoOperacao;
	}

	public void setTipoOperacao(String tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}

	public Integer getCodigoReduzidoOrganograma() {
		return codigoReduzidoOrganograma;
	}

	public void setCodigoReduzidoOrganograma(Integer codigoReduzidoOrganograma) {
		this.codigoReduzidoOrganograma = codigoReduzidoOrganograma;
	}

	public Integer getCodigoAto() {
		return codigoAto;
	}

	public void setCodigoAto(Integer codigoAto) {
		this.codigoAto = codigoAto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		final LogEjb other = (LogEjb) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	@Override
	public String toString() {
		return "LogEjb [id=" + id + ", datahora=" + datahora + ", mensagem=" + mensagem + ", tipo=" + tipo + ", classe=" + classe + ", metodo=" + metodo + ", empresa=" + empresa + ", matricula="
				+ matricula + ", empresaAdministrador=" + empresaAdministrador + ", matriculaAdministrador=" + matriculaAdministrador + ", tipoOperacao=" + tipoOperacao
				+ ", codigoReduzidoOrganograma=" + codigoReduzidoOrganograma + ", codigoAto=" + codigoAto + "]";
	}

	public static LogEjb fromString(String jsonString){
		return ResteasyUtil.createGson().fromJson(jsonString, LogEjb.class);
	}

}