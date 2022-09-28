package br.jus.tjms.pontoeletronico.to;

import java.util.Arrays;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.jus.tjms.pontoeletronico.rest.BytesDeserializer;
import br.jus.tjms.pontoeletronico.rest.BytesSerializer;
import br.jus.tjms.pontoeletronico.rest.ResteasyUtil;

public class UpdateInfoTO implements java.io.Serializable {

	private static final long serialVersionUID = -4915967766451836845L;
	
	private Long id;
	private String versao;
	private Date data;
	private String novidades;
	private String updateURL;

	@JsonSerialize(using = BytesSerializer.class)
	@JsonDeserialize(using = BytesDeserializer.class)
	private byte[] updateData;
	
	private UpdateScriptTO updateScript;

	public UpdateInfoTO(Long id, String versao, Date data, String novidades, String updateURL, byte[] updateData, UpdateScriptTO updateScript) {
		super();
		this.id = id;
		this.versao = versao;
		this.data = data;
		this.novidades = novidades;
		this.updateURL = updateURL;
		this.updateData = updateData;
		this.updateScript = updateScript;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVersao() {
		return versao;
	}

	public void setVersao(String versao) {
		this.versao = versao;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getNovidades() {
		return novidades;
	}

	public void setNovidades(String novidades) {
		this.novidades = novidades;
	}

	public String getUpdateURL() {
		return updateURL;
	}

	public void setUpdateURL(String updateURL) {
		this.updateURL = updateURL;
	}

	public byte[] getUpdateData() {
		return updateData;
	}

	public void setUpdateData(byte[] updateData) {
		this.updateData = updateData;
	}

	public UpdateScriptTO getUpdateScript() {
		return updateScript;
	}

	public void setUpdateScript(UpdateScriptTO updateScript) {
		this.updateScript = updateScript;
	}

	public static UpdateInfoTO fromString(String jsonString) {
		return ResteasyUtil.createGson().fromJson(jsonString, UpdateInfoTO.class);
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
		UpdateInfoTO other = this.getClass().cast(obj);
		return new EqualsBuilder().append(id, other.getId()).isEquals();
	}

	@Override
	public String toString() {
		return "UpdateInfoTO [id=" + id + ", versao=" + versao + ", data=" + data + ", novidades=" + novidades + ", updateURL=" + updateURL + ", updateData=" + Arrays.toString(Arrays.copyOf(updateData, 32))
				+ ", updateScript=" + updateScript + "]";
	}

}