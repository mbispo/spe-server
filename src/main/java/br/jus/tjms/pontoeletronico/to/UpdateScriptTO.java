package br.jus.tjms.pontoeletronico.to;

import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class UpdateScriptTO implements java.io.Serializable {

	private static final long serialVersionUID = -5648030322249797762L;

	private Long id;
	private String versaoBD;

	private List<UpdateScriptAcaoTO> acoes;

	public UpdateScriptTO(Long id, String versaoBD, List<UpdateScriptAcaoTO> acoes) {
		super();
		this.id = id;
		this.versaoBD = versaoBD;
		this.acoes = acoes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVersaoBD() {
		return versaoBD;
	}

	public void setVersaoBD(String versaoBD) {
		this.versaoBD = versaoBD;
	}

	public List<UpdateScriptAcaoTO> getAcoes() {
		return acoes;
	}

	public void setAcoes(List<UpdateScriptAcaoTO> acoes) {
		this.acoes = acoes;
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
		UpdateScriptTO other = this.getClass().cast(obj);
		return new EqualsBuilder().append(id, other.getId()).isEquals();
	}

	@Override
	public String toString() {
		return "UpdateScriptTO [id=" + id + ", versaoBD=" + versaoBD + ", acoes=" + acoes + "]";
	}

}