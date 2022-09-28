package br.jus.tjms.pontoeletronico.bean;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

import br.jus.tjms.pontoeletronico.rest.ResteasyUtil;
import br.jus.tjms.pontoeletronico.to.DigitalTO;

@Entity
@Table(name = "sgp.T_Dgt")
public class DigitalEjb implements java.io.Serializable {

	private static final long serialVersionUID = 8625782475871604765L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "CD_Dgt", nullable = false)
	private Integer id;

	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@Column(name = "DT_Crc", nullable = false)
	private Date dataCriacao;

	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@Column(name = "DT_Mdf", nullable = false)
	private Date dataModificacao;

	@Column(name = "IM_Dgt", nullable = false)
	private byte[] imagem;

	@Column(name = "IM_DgtPrc", nullable = false)
	private byte[] imagemProcessada;

	@ManyToOne
	@JoinColumns({
        @JoinColumn(referencedColumnName="CD_EmpSGP", name="CD_EmpSGP", nullable = false),
        @JoinColumn(referencedColumnName="CD_Fnc", name="CD_Fnc", nullable = false)
    })
	private FuncionarioEjb funcionario;

	public DigitalEjb(Date dataCriacao, Date dataModificacao, byte[] imagem, byte[] imagemProcessada,
			FuncionarioEjb funcionario) {
		super();
		this.dataCriacao = dataCriacao;
		this.dataModificacao = dataModificacao;
		this.imagem = imagem;
		this.imagemProcessada = imagemProcessada;
		this.funcionario = funcionario;
	}
	
	public DigitalTO toTO() {
		DigitalTO d = new DigitalTO();
		
		d.setDataCriacao(dataCriacao);
		d.setDataModificacao(dataModificacao);
		d.setId(id);
		d.setImagem(imagem);
		d.setImagemProcessada(imagemProcessada);
		d.setMatricula(funcionario.getMatricula());
		d.setEmpresa(funcionario.getEmpresa());
		
		return d;
	}
	
	public DigitalEjb(DigitalTO to) {
		super();
		if (to.getId()!=null) this.id = to.getId();
		this.dataCriacao = to.getDataCriacao();
		this.dataModificacao = to.getDataModificacao();
		this.imagem = to.getImagem();
		this.imagemProcessada = to.getImagemProcessada();
		this.funcionario = new FuncionarioEjb(to.getEmpresa(), to.getMatricula());
	}

	public DigitalEjb() {
		super();
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public FuncionarioEjb getFuncionario() {
		return funcionario;
	}

    public void setFuncionario(FuncionarioEjb funcionario) {
		this.funcionario = funcionario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataCriacao == null) ? 0 : dataCriacao.hashCode());
		result = prime * result
				+ ((dataModificacao == null) ? 0 : dataModificacao.hashCode());
		result = prime * result
				+ ((funcionario == null) ? 0 : funcionario.hashCode()); 
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + Arrays.hashCode(imagem);
		result = prime * result + Arrays.hashCode(imagemProcessada);
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
		DigitalEjb other = (DigitalEjb) obj;
		if (dataCriacao == null) {
			if (other.dataCriacao != null)
				return false;
		} else if (!dataCriacao.equals(other.dataCriacao))
			return false;
		if (dataModificacao == null) {
			if (other.dataModificacao != null)
				return false;
		} else if (!dataModificacao.equals(other.dataModificacao))
			return false;
		if (funcionario == null) {
			if (other.funcionario != null)
				return false;
		} else if (!funcionario.equals(other.funcionario))
			return false; 
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (!Arrays.equals(imagem, other.imagem))
			return false;
		if (!Arrays.equals(imagemProcessada, other.imagemProcessada))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DigitalEjb [id=" + id + ", dataCriacao=" + dataCriacao + ", dataModificacao=" + dataModificacao
				+ ", imagem=" + Arrays.toString(Arrays.copyOf(imagem, 32)) + "..., imagemProcessada=" + Arrays.toString(Arrays.copyOf(imagemProcessada, 32))
				+ "...]";
	}
	
	public static DigitalEjb fromString(String jsonString) {
		return ResteasyUtil.createGson().fromJson(jsonString, DigitalEjb.class);
	}

}