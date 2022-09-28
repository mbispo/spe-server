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

@Entity
@Table(name = "ponto", schema = "sgp")
public class Ponto implements Serializable {

    private static final long serialVersionUID = -3435418117182676199L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date dataHora;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date dataHoraLocal;

    private Integer localidade;

    private Integer empresa;

    private Integer matricula;

    private String timeZone;

    private Boolean horarioVerao;

    private Boolean processado;

    public Ponto() {
        super();
    }

    public Ponto(Date dataHora, Date dataHoraLocal, Integer localidade, Integer empresa, Integer matricula, String timeZone, Boolean horarioVerao) {
        super();
        this.dataHora = dataHora;
        this.dataHoraLocal = dataHoraLocal;
        this.localidade = localidade;
        this.empresa = empresa;
        this.matricula = matricula;
        this.timeZone = timeZone;
        this.horarioVerao = horarioVerao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public Date getDataHoraLocal() {
        return dataHoraLocal;
    }

    public void setDataHoraLocal(Date dataHoraLocal) {
        this.dataHoraLocal = dataHoraLocal;
    }

    public Integer getLocalidade() {
        return localidade;
    }

    public void setLocalidade(Integer localidade) {
        this.localidade = localidade;
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

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public Boolean getHorarioVerao() {
        return horarioVerao;
    }

    public void setHorarioVerao(Boolean horarioVerao) {
        this.horarioVerao = horarioVerao;
    }

    public Boolean getProcessado() {
        return processado;
    }

    public void setProcessado(Boolean processado) {
        this.processado = processado;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dataHora == null) ? 0 : dataHora.hashCode());
        result = prime * result + ((dataHoraLocal == null) ? 0 : dataHoraLocal.hashCode());
        result = prime * result + ((empresa == null) ? 0 : empresa.hashCode());
        result = prime * result + ((horarioVerao == null) ? 0 : horarioVerao.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((localidade == null) ? 0 : localidade.hashCode());
        result = prime * result + ((matricula == null) ? 0 : matricula.hashCode());
        result = prime * result + ((processado == null) ? 0 : processado.hashCode());
        result = prime * result + ((timeZone == null) ? 0 : timeZone.hashCode());
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
        Ponto other = (Ponto) obj;
        if (dataHora == null) {
            if (other.dataHora != null)
                return false;
        } else
            if (!dataHora.equals(other.dataHora))
                return false;
        if (dataHoraLocal == null) {
            if (other.dataHoraLocal != null)
                return false;
        } else
            if (!dataHoraLocal.equals(other.dataHoraLocal))
                return false;
        if (empresa == null) {
            if (other.empresa != null)
                return false;
        } else
            if (!empresa.equals(other.empresa))
                return false;
        if (horarioVerao == null) {
            if (other.horarioVerao != null)
                return false;
        } else
            if (!horarioVerao.equals(other.horarioVerao))
                return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else
            if (!id.equals(other.id))
                return false;
        if (localidade == null) {
            if (other.localidade != null)
                return false;
        } else
            if (!localidade.equals(other.localidade))
                return false;
        if (matricula == null) {
            if (other.matricula != null)
                return false;
        } else
            if (!matricula.equals(other.matricula))
                return false;
        if (processado == null) {
            if (other.processado != null)
                return false;
        } else
            if (!processado.equals(other.processado))
                return false;
        if (timeZone == null) {
            if (other.timeZone != null)
                return false;
        } else
            if (!timeZone.equals(other.timeZone))
                return false;
        return true;
    }

    @Override
    public String toString() {
        return "Ponto [id=" + id + ", dataHora=" + dataHora + ", dataHoraLocal=" + dataHoraLocal + ", localidade=" + localidade + ", empresa=" + empresa + ", matricula=" + matricula + ", timeZone=" + timeZone + ", horarioVerao=" + horarioVerao + ", processado=" + processado + "]";
    }

}