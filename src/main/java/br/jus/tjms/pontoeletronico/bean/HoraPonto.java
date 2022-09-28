package br.jus.tjms.pontoeletronico.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author marcosm1
 */
@Entity
@Table(name = "T_HorPnt", schema="sgp")
//@IdClass(HoraPontoPK.class)
public class HoraPonto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NR_Sqc", nullable=false)
    private int codigoHoraPonto;

    //@Id
    @Column(name = "CD_EmpSGP")
    private int codigoEmpresa;

    //@Id
    @Column(name = "CD_Fnc")
    private int matricula;

    //@Id
    @Column(name = "NR_Ano")
    private int ano;

    //@Id
    @Column(name = "NR_Mes")
    private int mes;

    //@Id
    @Column(name = "NR_Dia")
    private int dia;

    //@Id
    @Column(name="HR_Etr")
    private Integer horaEntrada;

    @Column(name = "HR_Sda")
    private Integer horaSaida;
    
    @Column(name="timeZoneEntrada")
    private String timeZoneEntrada;

    @Column(name="timeZoneSaida")
    private String timeZoneSaida;
    
    @Column(name="horarioVeraoEntrada")
    private Boolean horarioVeraoEntrada;

    @Column(name="horarioVeraoSaida")
    private Boolean horarioVeraoSaida;

    @JoinColumns({@JoinColumn(name = "CD_EmpSGP", referencedColumnName = "CD_EmpSGP", insertable = false, updatable = false, nullable = false), @JoinColumn(name = "CD_Fnc", referencedColumnName = "CD_Fnc", insertable = false, updatable = false, nullable = false), @JoinColumn(name = "NR_Ano", referencedColumnName = "NR_Ano", insertable = false, updatable = false, nullable = false), @JoinColumn(name = "NR_Mes", referencedColumnName = "NR_Mes", insertable = false, updatable = false, nullable = false), @JoinColumn(name = "NR_Dia", referencedColumnName = "NR_Dia", insertable = false, updatable = false, nullable = false)})
    //@JoinColumns({@JoinColumn(name = "CD_EmpSGP", referencedColumnName = "CD_EmpSGP", nullable = false), @JoinColumn(name = "CD_Fnc", referencedColumnName = "CD_Fnc", nullable = false), @JoinColumn(name = "NR_Ano", referencedColumnName = "NR_Ano", insertable = false, updatable = false,  nullable = false), @JoinColumn(name = "NR_Mes", referencedColumnName = "NR_Mes", nullable = false), @JoinColumn(name = "NR_Dia", referencedColumnName = "NR_Dia", nullable = false)})
    @ManyToOne
    private PontoDia pontoDia;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + codigoHoraPonto;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof HoraPonto))
            return false;
        final HoraPonto other = (HoraPonto) obj;
        if (codigoHoraPonto != other.codigoHoraPonto)
            return false;
        return true;
    }

    public HoraPonto() {
    }

    public HoraPonto(int codigoEmpresa, int matricula, int dia, int mes, int ano, int horaEntrada, String timeZone, Boolean horarioVerao) {
        this.codigoEmpresa = codigoEmpresa;
        this.matricula = matricula;
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
        this.horaEntrada = horaEntrada;
        this.timeZoneEntrada = timeZone;
        this.horarioVeraoEntrada = horarioVerao;
        this.horaSaida = 0;
    }
    
    public void setCodigoEmpresa(int codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }

    public int getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setAno(int ano){
        this.ano = ano;
    }

    public int getAno() {
        return ano;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getMes() {
        return mes;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getDia() {
        return dia;
    }

    public void setHoraEntrada(Integer horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public Integer getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraSaida(Integer horaSaida) {
        this.horaSaida = horaSaida;
    }

    public Integer getHoraSaida() {
        return horaSaida;
    }

    public String getTimeZoneEntrada() {
        return timeZoneEntrada;
    }
    
    public void setTimeZoneEntrada(String timeZoneEntrada) {
        this.timeZoneEntrada = timeZoneEntrada;
    }
    
    public String getTimeZoneSaida() {
        return timeZoneSaida;
    }
    
    public void setTimeZoneSaida(String timeZoneSaida) {
        this.timeZoneSaida = timeZoneSaida;
    }

    
    public Boolean getHorarioVeraoEntrada() {
        return horarioVeraoEntrada;
    }

    
    public void setHorarioVeraoEntrada(Boolean horarioVeraoEntrada) {
        this.horarioVeraoEntrada = horarioVeraoEntrada;
    }

    
    public Boolean getHorarioVeraoSaida() {
        return horarioVeraoSaida;
    }

    
    public void setHorarioVeraoSaida(Boolean horarioVeraoSaida) {
        this.horarioVeraoSaida = horarioVeraoSaida;
    }

    public PontoDia getPontoDia() {
        return pontoDia;
    }

    public void setPontoDia(PontoDia pontoDia) {
        this.pontoDia = pontoDia;
    }

    public int getCodigoHoraPonto() {
        return codigoHoraPonto;
    }

    public void setCodigoHoraPonto(int codigoHoraPonto) {
        this.codigoHoraPonto = codigoHoraPonto;
    }
    
}