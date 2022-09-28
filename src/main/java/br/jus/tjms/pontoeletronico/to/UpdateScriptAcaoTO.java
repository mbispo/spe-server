package br.jus.tjms.pontoeletronico.to;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class UpdateScriptAcaoTO implements java.io.Serializable {

	private static final long serialVersionUID = -8081886807510436138L;

	private Long id;
	private Integer sequencia;
	private UpdateScriptTipoAcao tipoAcao;
	private String chave;
	private String valor;

	public UpdateScriptAcaoTO(Long id, Integer sequencia, UpdateScriptTipoAcao tipoAcao, String chave, String valor) {
		super();
		this.id = id;
		this.sequencia = sequencia;
		this.tipoAcao = tipoAcao;
		this.chave = chave;
		this.valor = valor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getSequencia() {
		return sequencia;
	}

	public void setSequencia(Integer sequencia) {
		this.sequencia = sequencia;
	}

	public UpdateScriptTipoAcao getTipoAcao() {
		return tipoAcao;
	}

	public void setTipoAcao(UpdateScriptTipoAcao tipoAcao) {
		this.tipoAcao = tipoAcao;
	}

	public String getChave() {
		return chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(id).toHashCode();
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
		UpdateScriptAcaoTO other = this.getClass().cast(obj);
		return new EqualsBuilder().append(id, other.getId()).isEquals();
	}

	@Override
	public String toString() {
		return "UpdateScriptAcaoTO [id=" + id + ", sequencia=" + sequencia + ", tipoAcao=" + tipoAcao + ", chave=" + chave + ", valor=" + valor + "]";
	}

}