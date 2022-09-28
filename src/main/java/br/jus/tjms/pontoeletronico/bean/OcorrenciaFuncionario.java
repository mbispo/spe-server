/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.jus.tjms.pontoeletronico.bean;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author marcosm1
 */
@Entity
@Table(name = "T_OcrFnc")
@NamedQueries({@NamedQuery(name = "TOcrFnc.findByCDOcrFnc", query = "SELECT t FROM OcorrenciaFuncionario t WHERE t.cDOcrFnc = :cDOcrFnc"),
               @NamedQuery(name = "TOcrFnc.findByDTOcrFnc", query = "SELECT t FROM OcorrenciaFuncionario t WHERE t.dTOcrFnc = :dTOcrFnc"),
               @NamedQuery(name = "TOcrFnc.findByDTCnlRlt", query = "SELECT t FROM OcorrenciaFuncionario t WHERE t.dTCnlRlt = :dTCnlRlt"),
               @NamedQuery(name = "TOcrFnc.findByCDHstAto", query = "SELECT t FROM OcorrenciaFuncionario t WHERE t.cDHstAto = :cDHstAto"),
               @NamedQuery(name = "TOcrFnc.findByCDHrr", query = "SELECT t FROM OcorrenciaFuncionario t WHERE t.cDHrr = :cDHrr"),
               @NamedQuery(name = "TOcrFnc.findByCDSmbCrg", query = "SELECT t FROM OcorrenciaFuncionario t WHERE t.cDSmbCrg = :cDSmbCrg"),
               @NamedQuery(name = "TOcrFnc.findByCDRdzCrg", query = "SELECT t FROM OcorrenciaFuncionario t WHERE t.cDRdzCrg = :cDRdzCrg"),
               @NamedQuery(name = "TOcrFnc.findByCDTpoPrv", query = "SELECT t FROM OcorrenciaFuncionario t WHERE t.cDTpoPrv = :cDTpoPrv"),
               @NamedQuery(name = "TOcrFnc.findByCDRdzOrn", query = "SELECT t FROM OcorrenciaFuncionario t WHERE t.cDRdzOrn = :cDRdzOrn"),
               @NamedQuery(name = "TOcrFnc.findByCDRdzOrnEsp", query = "SELECT t FROM OcorrenciaFuncionario t WHERE t.cDRdzOrnEsp = :cDRdzOrnEsp"),
               @NamedQuery(name = "TOcrFnc.findByCDUsrInl", query = "SELECT t FROM OcorrenciaFuncionario t WHERE t.cDUsrInl = :cDUsrInl"),
               @NamedQuery(name = "TOcrFnc.findByCDUsrAlt", query = "SELECT t FROM OcorrenciaFuncionario t WHERE t.cDUsrAlt = :cDUsrAlt"),
               @NamedQuery(name = "TOcrFnc.findByDTAltOcr", query = "SELECT t FROM OcorrenciaFuncionario t WHERE t.dTAltOcr = :dTAltOcr"),
               @NamedQuery(name = "TOcrFnc.findBySGSmbCrg", query = "SELECT t FROM OcorrenciaFuncionario t WHERE t.sGSmbCrg = :sGSmbCrg"),
               @NamedQuery(name = "TOcrFnc.findByCDNmeCrg", query = "SELECT t FROM OcorrenciaFuncionario t WHERE t.cDNmeCrg = :cDNmeCrg"),
               @NamedQuery(name = "TOcrFnc.findByCDRdzOrnOrg", query = "SELECT t FROM OcorrenciaFuncionario t WHERE t.cDRdzOrnOrg = :cDRdzOrnOrg"),
               @NamedQuery(name = "TOcrFnc.findByCDHstAtoEstAdm", query = "SELECT t FROM OcorrenciaFuncionario t WHERE t.cDHstAtoEstAdm = :cDHstAtoEstAdm"),
               @NamedQuery(name = "TOcrFnc.findByDSObs", query = "SELECT t FROM OcorrenciaFuncionario t WHERE t.dSObs = :dSObs"),
               @NamedQuery(name = "TOcrFnc.findByNRBno", query = "SELECT t FROM OcorrenciaFuncionario t WHERE t.nRBno = :nRBno"),
               @NamedQuery(name = "TOcrFnc.findByCdRgmTrl", query = "SELECT t FROM OcorrenciaFuncionario t WHERE t.cdRgmTrl = :cdRgmTrl"),
               @NamedQuery(name = "TOcrFnc.findByCDOcrOrg", query = "SELECT t FROM OcorrenciaFuncionario t WHERE t.cDOcrOrg = :cDOcrOrg"),
               @NamedQuery(name = "TOcrFnc.findByCDCrgOtrOga", query = "SELECT t FROM OcorrenciaFuncionario t WHERE t.cDCrgOtrOga = :cDCrgOtrOga"),
               @NamedQuery(name = "TOcrFnc.findByIDSmbCrg", query = "SELECT t FROM OcorrenciaFuncionario t WHERE t.iDSmbCrg = :iDSmbCrg"),
               @NamedQuery(name = "TOcrFnc.findByCDLclMnc", query = "SELECT t FROM OcorrenciaFuncionario t WHERE t.cDLclMnc = :cDLclMnc"),
               @NamedQuery(name = "TOcrFnc.findByDTIni", query = "SELECT t FROM OcorrenciaFuncionario t WHERE t.dTIni = :dTIni"),
               @NamedQuery(name = "TOcrFnc.findByCDHrrOrg", query = "SELECT t FROM OcorrenciaFuncionario t WHERE t.cDHrrOrg = :cDHrrOrg"),
               @NamedQuery(name = "TOcrFnc.findByCDTpoFro", query = "SELECT t FROM OcorrenciaFuncionario t WHERE t.cDTpoFro = :cDTpoFro"),
               @NamedQuery(name = "TOcrFnc.findByCDTpoPrvOrg", query = "SELECT t FROM OcorrenciaFuncionario t WHERE t.cDTpoPrvOrg = :cDTpoPrvOrg"),
               @NamedQuery(name = "TOcrFnc.findByDTFim", query = "SELECT t FROM OcorrenciaFuncionario t WHERE t.dTFim = :dTFim"),
               @NamedQuery(name = "TOcrFnc.findByNRDia", query = "SELECT t FROM OcorrenciaFuncionario t WHERE t.nRDia = :nRDia"),
               @NamedQuery(name = "TOcrFnc.findByIDCrg", query = "SELECT t FROM OcorrenciaFuncionario t WHERE t.iDCrg = :iDCrg"),
               @NamedQuery(name = "TOcrFnc.findByDTCnlRltFnl", query = "SELECT t FROM OcorrenciaFuncionario t WHERE t.dTCnlRltFnl = :dTCnlRltFnl"),
               @NamedQuery(name = "TOcrFnc.findByIDAuxOcr", query = "SELECT t FROM OcorrenciaFuncionario t WHERE t.iDAuxOcr = :iDAuxOcr"),
               @NamedQuery(name = "TOcrFnc.findByFLRgsPnt", query = "SELECT t FROM OcorrenciaFuncionario t WHERE t.fLRgsPnt = :fLRgsPnt")})
public class OcorrenciaFuncionario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "CD_OcrFnc", nullable = false)
    private Integer cDOcrFnc;
    @Column(name = "DT_OcrFnc")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dTOcrFnc;
    @Column(name = "DT_CnlRlt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dTCnlRlt;
    @Column(name = "CD_HstAto")
    private Integer cDHstAto;
    @Column(name = "CD_Hrr")
    private Integer cDHrr;
    @Column(name = "CD_SmbCrg")
    private Short cDSmbCrg;
    @Column(name = "CD_RdzCrg")
    private Integer cDRdzCrg;
    @Column(name = "CD_TpoPrv")
    private Short cDTpoPrv;
    @Column(name = "CD_RdzOrn")
    private Integer cDRdzOrn;
    @Column(name = "CD_RdzOrnEsp")
    private Integer cDRdzOrnEsp;
    @Column(name = "CD_UsrInl")
    private Integer cDUsrInl;
    @Column(name = "CD_UsrAlt")
    private Integer cDUsrAlt;
    @Column(name = "DT_AltOcr")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dTAltOcr;
    @Column(name = "SG_SmbCrg")
    private String sGSmbCrg;
    @Column(name = "CD_NmeCrg")
    private Integer cDNmeCrg;
    @Column(name = "CD_RdzOrnOrg")
    private Integer cDRdzOrnOrg;
    @Column(name = "CD_HstAtoEstAdm")
    private Integer cDHstAtoEstAdm;
    @Column(name = "DS_Obs")
    private String dSObs;
    @Column(name = "NR_Bno")
    private Short nRBno;
    @Column(name = "Cd_RgmTrl")
    private Short cdRgmTrl;
    @Column(name = "CD_OcrOrg")
    private Integer cDOcrOrg;
    @Column(name = "CD_CrgOtrOga")
    private Integer cDCrgOtrOga;
    @Column(name = "ID_SmbCrg")
    private String iDSmbCrg;
    @Column(name = "CD_LclMnc")
    private Integer cDLclMnc;
    @Column(name = "DT_Ini")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dTIni;
    @Column(name = "CD_HrrOrg")
    private Short cDHrrOrg;
    @Column(name = "CD_TpoFro")
    private Short cDTpoFro;
    @Column(name = "CD_TpoPrvOrg")
    private Short cDTpoPrvOrg;
    @Column(name = "DT_Fim")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dTFim;
    @Column(name = "NR_Dia")
    private Short nRDia;
    @Column(name = "ID_Crg")
    private String iDCrg;
    @Column(name = "DT_CnlRltFnl")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dTCnlRltFnl;
    @Column(name = "ID_AuxOcr")
    private Short iDAuxOcr;
    @Column(name = "FL_RgsPnt")
    private Boolean fLRgsPnt;
    @JoinColumn(name = "CD_TpoOcr", referencedColumnName = "CD_TpoOcr")
    @ManyToOne
    private TipoOcorrencia cDTpoOcr;

    public OcorrenciaFuncionario() {
    }

    public OcorrenciaFuncionario(Integer cDOcrFnc) {
        this.cDOcrFnc = cDOcrFnc;
    }

    public Integer getCDOcrFnc() {
        return cDOcrFnc;
    }

    public void setCDOcrFnc(Integer cDOcrFnc) {
        this.cDOcrFnc = cDOcrFnc;
    }

    public Date getDTOcrFnc() {
        return dTOcrFnc;
    }

    public void setDTOcrFnc(Date dTOcrFnc) {
        this.dTOcrFnc = dTOcrFnc;
    }

    public Date getDTCnlRlt() {
        return dTCnlRlt;
    }

    public void setDTCnlRlt(Date dTCnlRlt) {
        this.dTCnlRlt = dTCnlRlt;
    }

    public Integer getCDHstAto() {
        return cDHstAto;
    }

    public void setCDHstAto(Integer cDHstAto) {
        this.cDHstAto = cDHstAto;
    }

    public Integer getCDHrr() {
        return cDHrr;
    }

    public void setCDHrr(Integer cDHrr) {
        this.cDHrr = cDHrr;
    }

    public Short getCDSmbCrg() {
        return cDSmbCrg;
    }

    public void setCDSmbCrg(Short cDSmbCrg) {
        this.cDSmbCrg = cDSmbCrg;
    }

    public Integer getCDRdzCrg() {
        return cDRdzCrg;
    }

    public void setCDRdzCrg(Integer cDRdzCrg) {
        this.cDRdzCrg = cDRdzCrg;
    }

    public Short getCDTpoPrv() {
        return cDTpoPrv;
    }

    public void setCDTpoPrv(Short cDTpoPrv) {
        this.cDTpoPrv = cDTpoPrv;
    }

    public Integer getCDRdzOrn() {
        return cDRdzOrn;
    }

    public void setCDRdzOrn(Integer cDRdzOrn) {
        this.cDRdzOrn = cDRdzOrn;
    }

    public Integer getCDRdzOrnEsp() {
        return cDRdzOrnEsp;
    }

    public void setCDRdzOrnEsp(Integer cDRdzOrnEsp) {
        this.cDRdzOrnEsp = cDRdzOrnEsp;
    }

    public Integer getCDUsrInl() {
        return cDUsrInl;
    }

    public void setCDUsrInl(Integer cDUsrInl) {
        this.cDUsrInl = cDUsrInl;
    }

    public Integer getCDUsrAlt() {
        return cDUsrAlt;
    }

    public void setCDUsrAlt(Integer cDUsrAlt) {
        this.cDUsrAlt = cDUsrAlt;
    }

    public Date getDTAltOcr() {
        return dTAltOcr;
    }

    public void setDTAltOcr(Date dTAltOcr) {
        this.dTAltOcr = dTAltOcr;
    }

    public String getSGSmbCrg() {
        return sGSmbCrg;
    }

    public void setSGSmbCrg(String sGSmbCrg) {
        this.sGSmbCrg = sGSmbCrg;
    }

    public Integer getCDNmeCrg() {
        return cDNmeCrg;
    }

    public void setCDNmeCrg(Integer cDNmeCrg) {
        this.cDNmeCrg = cDNmeCrg;
    }

    public Integer getCDRdzOrnOrg() {
        return cDRdzOrnOrg;
    }

    public void setCDRdzOrnOrg(Integer cDRdzOrnOrg) {
        this.cDRdzOrnOrg = cDRdzOrnOrg;
    }

    public Integer getCDHstAtoEstAdm() {
        return cDHstAtoEstAdm;
    }

    public void setCDHstAtoEstAdm(Integer cDHstAtoEstAdm) {
        this.cDHstAtoEstAdm = cDHstAtoEstAdm;
    }

    public String getDSObs() {
        return dSObs;
    }

    public void setDSObs(String dSObs) {
        this.dSObs = dSObs;
    }

    public Short getNRBno() {
        return nRBno;
    }

    public void setNRBno(Short nRBno) {
        this.nRBno = nRBno;
    }

    public Short getCdRgmTrl() {
        return cdRgmTrl;
    }

    public void setCdRgmTrl(Short cdRgmTrl) {
        this.cdRgmTrl = cdRgmTrl;
    }

    public Integer getCDOcrOrg() {
        return cDOcrOrg;
    }

    public void setCDOcrOrg(Integer cDOcrOrg) {
        this.cDOcrOrg = cDOcrOrg;
    }

    public Integer getCDCrgOtrOga() {
        return cDCrgOtrOga;
    }

    public void setCDCrgOtrOga(Integer cDCrgOtrOga) {
        this.cDCrgOtrOga = cDCrgOtrOga;
    }

    public String getIDSmbCrg() {
        return iDSmbCrg;
    }

    public void setIDSmbCrg(String iDSmbCrg) {
        this.iDSmbCrg = iDSmbCrg;
    }

    public Integer getCDLclMnc() {
        return cDLclMnc;
    }

    public void setCDLclMnc(Integer cDLclMnc) {
        this.cDLclMnc = cDLclMnc;
    }

    public Date getDTIni() {
        return dTIni;
    }

    public void setDTIni(Date dTIni) {
        this.dTIni = dTIni;
    }

    public Short getCDHrrOrg() {
        return cDHrrOrg;
    }

    public void setCDHrrOrg(Short cDHrrOrg) {
        this.cDHrrOrg = cDHrrOrg;
    }

    public Short getCDTpoFro() {
        return cDTpoFro;
    }

    public void setCDTpoFro(Short cDTpoFro) {
        this.cDTpoFro = cDTpoFro;
    }

    public Short getCDTpoPrvOrg() {
        return cDTpoPrvOrg;
    }

    public void setCDTpoPrvOrg(Short cDTpoPrvOrg) {
        this.cDTpoPrvOrg = cDTpoPrvOrg;
    }

    public Date getDTFim() {
        return dTFim;
    }

    public void setDTFim(Date dTFim) {
        this.dTFim = dTFim;
    }

    public Short getNRDia() {
        return nRDia;
    }

    public void setNRDia(Short nRDia) {
        this.nRDia = nRDia;
    }

    public String getIDCrg() {
        return iDCrg;
    }

    public void setIDCrg(String iDCrg) {
        this.iDCrg = iDCrg;
    }

    public Date getDTCnlRltFnl() {
        return dTCnlRltFnl;
    }

    public void setDTCnlRltFnl(Date dTCnlRltFnl) {
        this.dTCnlRltFnl = dTCnlRltFnl;
    }

    public Short getIDAuxOcr() {
        return iDAuxOcr;
    }

    public void setIDAuxOcr(Short iDAuxOcr) {
        this.iDAuxOcr = iDAuxOcr;
    }

    public Boolean getFLRgsPnt() {
        return fLRgsPnt;
    }

    public void setFLRgsPnt(Boolean fLRgsPnt) {
        this.fLRgsPnt = fLRgsPnt;
    }

    public TipoOcorrencia getCDTpoOcr() {
        return cDTpoOcr;
    }

    public void setCDTpoOcr(TipoOcorrencia cDTpoOcr) {
        this.cDTpoOcr = cDTpoOcr;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cDOcrFnc != null ? cDOcrFnc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OcorrenciaFuncionario)) {
            return false;
        }
        OcorrenciaFuncionario other = (OcorrenciaFuncionario) object;
        if ((this.cDOcrFnc == null && other.cDOcrFnc != null) || (this.cDOcrFnc != null && !this.cDOcrFnc.equals(other.cDOcrFnc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.jus.tjms.ddsi.sgp.bean.TOcrFnc[cDOcrFnc=" + cDOcrFnc + "]";
    }

}
