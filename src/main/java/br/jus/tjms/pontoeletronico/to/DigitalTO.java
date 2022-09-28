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

public class DigitalTO implements java.io.Serializable {

	private static final long serialVersionUID = -6416251511725005878L;

	private Integer id;
	private Date dataCriacao;
	private Date dataModificacao;

	@JsonSerialize(using = BytesSerializer.class)
	@JsonDeserialize(using = BytesDeserializer.class)
	private byte[] imagem;

	@JsonSerialize(using = BytesSerializer.class)
	@JsonDeserialize(using = BytesDeserializer.class)
	private byte[] imagemProcessada;
	
	private Integer matricula;
	private Integer empresa;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Date getDataModificacao() {
		return dataModificacao;
	}

	public void setDataModificacao(Date dataModificacao) {
		this.dataModificacao = dataModificacao;
	}

	public byte[] getImagem() {
		return imagem;
	}

	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}

	public byte[] getImagemProcessada() {
		return imagemProcessada;
	}

	public void setImagemProcessada(byte[] imagemProcessada) {
		this.imagemProcessada = imagemProcessada;
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
		DigitalTO other = this.getClass().cast(obj);
		return new EqualsBuilder().append(id, other.getId()).isEquals();
	}

	@Override
	public String toString() {
		return "DigitalTO [id=" + id + ", matricula=" + matricula + ", empresa=" + empresa + ", dataCriacao=" + dataCriacao + ", dataModificacao=" + dataModificacao
				+ ", imagem=" + Arrays.toString(Arrays.copyOf(imagem, 32)) + "..., imagemProcessada=" + Arrays.toString(Arrays.copyOf(imagemProcessada, 32))
				+ "...]";
	}
	
	public static DigitalTO fromString(String jsonString) {
		return ResteasyUtil.createGson().fromJson(jsonString, DigitalTO.class);
	}

}