package br.jus.tjms.pontoeletronico.bean;

import br.jus.tjms.pontoeletronico.rest.ResteasyUtil;

import java.io.Serializable;
import java.util.Date;


/**
 * @author marcosm
 */
public class PontoEjb implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4056547348072307173L;

    private Integer id;

    private Date dataHora;
    
    private Date dataHoraLocal;

    //private time Hora;    
    private Integer localidade;

    private String timeZone;

    private Boolean horarioVerao;

    private FuncionarioEjb funcionarioEjb;

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date data) {
        this.dataHora = data;
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

    public FuncionarioEjb getFuncionarioEjb() {
        return funcionarioEjb;
    }

    public void setFuncionarioEjb(FuncionarioEjb funcionarioEjb) {
        this.funcionarioEjb = funcionarioEjb;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static PontoEjb fromString(String jsonString) {
        return ResteasyUtil.createGson().fromJson(jsonString, PontoEjb.class);
    }

    @Override
    public String toString() {
        return "PontoEjb [dataHora=" + dataHora + ", dataHoraLocal=" + dataHoraLocal + ", localidade=" + localidade + ", timeZone=" + timeZone + ", horarioVerao=" + horarioVerao + ", funcionarioEjb.matricula=" + funcionarioEjb!=null?funcionarioEjb.getMatricula().toString():"null" + "]";
    }
    

}