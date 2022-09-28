/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.jus.tjms.pontoeletronico.bean;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author marcosm1
 */
@Entity
@Table(name = "T_TpoOcr")
@NamedQueries({@NamedQuery(name = "TTpoOcr.findByCDTpoOcr", query = "SELECT t FROM TTpoOcr t WHERE t.cDTpoOcr = :cDTpoOcr"), @NamedQuery(name = "TTpoOcr.findByDSTpoOcr", query = "SELECT t FROM TTpoOcr t WHERE t.dSTpoOcr = :dSTpoOcr"), @NamedQuery(name = "TTpoOcr.findByDSLgdAtbDta", query = "SELECT t FROM TTpoOcr t WHERE t.dSLgdAtbDta = :dSLgdAtbDta"), @NamedQuery(name = "TTpoOcr.findBySGVrv", query = "SELECT t FROM TTpoOcr t WHERE t.sGVrv = :sGVrv"), @NamedQuery(name = "TTpoOcr.findBySTDgtCrg", query = "SELECT t FROM TTpoOcr t WHERE t.sTDgtCrg = :sTDgtCrg"), @NamedQuery(name = "TTpoOcr.findBySTDgtLtc", query = "SELECT t FROM TTpoOcr t WHERE t.sTDgtLtc = :sTDgtLtc"), @NamedQuery(name = "TTpoOcr.findByDSRtnTpoOcr", query = "SELECT t FROM TTpoOcr t WHERE t.dSRtnTpoOcr = :dSRtnTpoOcr"), @NamedQuery(name = "TTpoOcr.findByCDEmpSGP", query = "SELECT t FROM TTpoOcr t WHERE t.cDEmpSGP = :cDEmpSGP"), @NamedQuery(name = "TTpoOcr.findByHRDfl", query = "SELECT t FROM TTpoOcr t WHERE t.hRDfl = :hRDfl"), @NamedQuery(name = "TTpoOcr.findBySTLncOcr", query = "SELECT t FROM TTpoOcr t WHERE t.sTLncOcr = :sTLncOcr"), @NamedQuery(name = "TTpoOcr.findByDSLgdAtbDtaIni", query = "SELECT t FROM TTpoOcr t WHERE t.dSLgdAtbDtaIni = :dSLgdAtbDtaIni"), @NamedQuery(name = "TTpoOcr.findByDSLgdAtbDtaFim", query = "SELECT t FROM TTpoOcr t WHERE t.dSLgdAtbDtaFim = :dSLgdAtbDtaFim"), @NamedQuery(name = "TTpoOcr.findByDSOcrEfe", query = "SELECT t FROM TTpoOcr t WHERE t.dSOcrEfe = :dSOcrEfe"), @NamedQuery(name = "TTpoOcr.findBySTPrv", query = "SELECT t FROM TTpoOcr t WHERE t.sTPrv = :sTPrv"), @NamedQuery(name = "TTpoOcr.findByDSLgdAtbNum", query = "SELECT t FROM TTpoOcr t WHERE t.dSLgdAtbNum = :dSLgdAtbNum"), @NamedQuery(name = "TTpoOcr.findByFLDcm", query = "SELECT t FROM TTpoOcr t WHERE t.fLDcm = :fLDcm"), @NamedQuery(name = "TTpoOcr.findByDSLgnAtbDtaCnlFnl", query = "SELECT t FROM TTpoOcr t WHERE t.dSLgnAtbDtaCnlFnl = :dSLgnAtbDtaCnlFnl"), @NamedQuery(name = "TTpoOcr.findByDSSQLOcrOrg", query = "SELECT t FROM TTpoOcr t WHERE t.dSSQLOcrOrg = :dSSQLOcrOrg"), @NamedQuery(name = "TTpoOcr.findByFLVcnPrd", query = "SELECT t FROM TTpoOcr t WHERE t.fLVcnPrd = :fLVcnPrd"), @NamedQuery(name = "TTpoOcr.findByCDTpoFltDta", query = "SELECT t FROM TTpoOcr t WHERE t.cDTpoFltDta = :cDTpoFltDta"), @NamedQuery(name = "TTpoOcr.findByCDObrDcmFrq", query = "SELECT t FROM TTpoOcr t WHERE t.cDObrDcmFrq = :cDObrDcmFrq"), @NamedQuery(name = "TTpoOcr.findByFLRgsPnt", query = "SELECT t FROM TTpoOcr t WHERE t.fLRgsPnt = :fLRgsPnt"), @NamedQuery(name = "TTpoOcr.findByDSCdgTpoOcrAss", query = "SELECT t FROM TTpoOcr t WHERE t.dSCdgTpoOcrAss = :dSCdgTpoOcrAss"), @NamedQuery(name = "TTpoOcr.findByIDTipoRAIS", query = "SELECT t FROM TTpoOcr t WHERE t.iDTipoRAIS = :iDTipoRAIS"), @NamedQuery(name = "TTpoOcr.findByIDCodigoRAIS", query = "SELECT t FROM TTpoOcr t WHERE t.iDCodigoRAIS = :iDCodigoRAIS")})
public class TipoOcorrencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "CD_TpoOcr", nullable = false)
    private Short cDTpoOcr;
    @Column(name = "DS_TpoOcr", nullable = false)
    private String dSTpoOcr;
    @Column(name = "DS_LgdAtbDta")
    private String dSLgdAtbDta;
    @Column(name = "SG_Vrv")
    private String sGVrv;
    @Column(name = "ST_DgtCrg")
    private Short sTDgtCrg;
    @Column(name = "ST_DgtLtc")
    private Short sTDgtLtc;
    @Column(name = "DS_RtnTpoOcr")
    private String dSRtnTpoOcr;
    @Column(name = "CD_EmpSGP", nullable = false)
    private short cDEmpSGP;
    @Column(name = "HR_Dfl", nullable = false)
    private String hRDfl;
    @Column(name = "ST_LncOcr", nullable = false)
    private char sTLncOcr;
    @Column(name = "DS_LgdAtbDtaIni")
    private String dSLgdAtbDtaIni;
    @Column(name = "DS_LgdAtbDtaFim")
    private String dSLgdAtbDtaFim;
    @Column(name = "DS_OcrEfe")
    private String dSOcrEfe;
    @Column(name = "ST_Prv")
    private Character sTPrv;
    @Column(name = "DS_LgdAtbNum")
    private String dSLgdAtbNum;
    @Column(name = "FL_Dcm")
    private Boolean fLDcm;
    @Column(name = "DS_LgnAtbDtaCnlFnl")
    private String dSLgnAtbDtaCnlFnl;
    @Column(name = "DS_SQLOcrOrg")
    private String dSSQLOcrOrg;
    @Column(name = "FL_VcnPrd", nullable = false)
    private boolean fLVcnPrd;
    @Column(name = "CD_TpoFltDta")
    private Short cDTpoFltDta;
    @Column(name = "CD_ObrDcmFrq")
    private Short cDObrDcmFrq;
    @Column(name = "FL_RgsPnt")
    private Boolean fLRgsPnt;
    @Column(name = "DS_CdgTpoOcrAss")
    private String dSCdgTpoOcrAss;
    @Column(name = "ID_TipoRAIS")
    private Integer iDTipoRAIS;
    @Column(name = "ID_CodigoRAIS")
    private Integer iDCodigoRAIS;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cDTpoOcr")
    private Collection<OcorrenciaFuncionario> tOcrFncCollection;
    public TipoOcorrencia() {
    }
    public TipoOcorrencia(Short cDTpoOcr) {
        this.cDTpoOcr = cDTpoOcr;
    }
    public TipoOcorrencia(Short cDTpoOcr, String dSTpoOcr, short cDEmpSGP, String hRDfl, char sTLncOcr, boolean fLVcnPrd) {
        this.cDTpoOcr = cDTpoOcr;
        this.dSTpoOcr = dSTpoOcr;
        this.cDEmpSGP = cDEmpSGP;
        this.hRDfl = hRDfl;
        this.sTLncOcr = sTLncOcr;
        this.fLVcnPrd = fLVcnPrd;
    }
    public Short getCDTpoOcr() {
        return cDTpoOcr;
    }

    public void setCDTpoOcr(Short cDTpoOcr) {
        this.cDTpoOcr = cDTpoOcr;
    }

    public String getDSTpoOcr() {
        return dSTpoOcr;
    }

    public void setDSTpoOcr(String dSTpoOcr) {
        this.dSTpoOcr = dSTpoOcr;
    }

    public String getDSLgdAtbDta() {
        return dSLgdAtbDta;
    }

    public void setDSLgdAtbDta(String dSLgdAtbDta) {
        this.dSLgdAtbDta = dSLgdAtbDta;
    }

    public String getSGVrv() {
        return sGVrv;
    }

    public void setSGVrv(String sGVrv) {
        this.sGVrv = sGVrv;
    }

    public Short getSTDgtCrg() {
        return sTDgtCrg;
    }

    public void setSTDgtCrg(Short sTDgtCrg) {
        this.sTDgtCrg = sTDgtCrg;
    }

    public Short getSTDgtLtc() {
        return sTDgtLtc;
    }

    public void setSTDgtLtc(Short sTDgtLtc) {
        this.sTDgtLtc = sTDgtLtc;
    }

    public String getDSRtnTpoOcr() {
        return dSRtnTpoOcr;
    }

    public void setDSRtnTpoOcr(String dSRtnTpoOcr) {
        this.dSRtnTpoOcr = dSRtnTpoOcr;
    }

    public short getCDEmpSGP() {
        return cDEmpSGP;
    }

    public void setCDEmpSGP(short cDEmpSGP) {
        this.cDEmpSGP = cDEmpSGP;
    }

    public String getHRDfl() {
        return hRDfl;
    }

    public void setHRDfl(String hRDfl) {
        this.hRDfl = hRDfl;
    }

    public char getSTLncOcr() {
        return sTLncOcr;
    }

    public void setSTLncOcr(char sTLncOcr) {
        this.sTLncOcr = sTLncOcr;
    }

    public String getDSLgdAtbDtaIni() {
        return dSLgdAtbDtaIni;
    }

    public void setDSLgdAtbDtaIni(String dSLgdAtbDtaIni) {
        this.dSLgdAtbDtaIni = dSLgdAtbDtaIni;
    }

    public String getDSLgdAtbDtaFim() {
        return dSLgdAtbDtaFim;
    }

    public void setDSLgdAtbDtaFim(String dSLgdAtbDtaFim) {
        this.dSLgdAtbDtaFim = dSLgdAtbDtaFim;
    }

    public String getDSOcrEfe() {
        return dSOcrEfe;
    }

    public void setDSOcrEfe(String dSOcrEfe) {
        this.dSOcrEfe = dSOcrEfe;
    }

    public Character getSTPrv() {
        return sTPrv;
    }

    public void setSTPrv(Character sTPrv) {
        this.sTPrv = sTPrv;
    }

    public String getDSLgdAtbNum() {
        return dSLgdAtbNum;
    }

    public void setDSLgdAtbNum(String dSLgdAtbNum) {
        this.dSLgdAtbNum = dSLgdAtbNum;
    }

    public Boolean getFLDcm() {
        return fLDcm;
    }

    public void setFLDcm(Boolean fLDcm) {
        this.fLDcm = fLDcm;
    }

    public String getDSLgnAtbDtaCnlFnl() {
        return dSLgnAtbDtaCnlFnl;
    }

    public void setDSLgnAtbDtaCnlFnl(String dSLgnAtbDtaCnlFnl) {
        this.dSLgnAtbDtaCnlFnl = dSLgnAtbDtaCnlFnl;
    }

    public String getDSSQLOcrOrg() {
        return dSSQLOcrOrg;
    }

    public void setDSSQLOcrOrg(String dSSQLOcrOrg) {
        this.dSSQLOcrOrg = dSSQLOcrOrg;
    }

    public boolean getFLVcnPrd() {
        return fLVcnPrd;
    }

    public void setFLVcnPrd(boolean fLVcnPrd) {
        this.fLVcnPrd = fLVcnPrd;
    }

    public Short getCDTpoFltDta() {
        return cDTpoFltDta;
    }

    public void setCDTpoFltDta(Short cDTpoFltDta) {
        this.cDTpoFltDta = cDTpoFltDta;
    }

    public Short getCDObrDcmFrq() {
        return cDObrDcmFrq;
    }

    public void setCDObrDcmFrq(Short cDObrDcmFrq) {
        this.cDObrDcmFrq = cDObrDcmFrq;
    }

    public Boolean getFLRgsPnt() {
        return fLRgsPnt;
    }

    public void setFLRgsPnt(Boolean fLRgsPnt) {
        this.fLRgsPnt = fLRgsPnt;
    }

    public String getDSCdgTpoOcrAss() {
        return dSCdgTpoOcrAss;
    }

    public void setDSCdgTpoOcrAss(String dSCdgTpoOcrAss) {
        this.dSCdgTpoOcrAss = dSCdgTpoOcrAss;
    }

    public Integer getIDTipoRAIS() {
        return iDTipoRAIS;
    }

    public void setIDTipoRAIS(Integer iDTipoRAIS) {
        this.iDTipoRAIS = iDTipoRAIS;
    }

    public Integer getIDCodigoRAIS() {
        return iDCodigoRAIS;
    }

    public void setIDCodigoRAIS(Integer iDCodigoRAIS) {
        this.iDCodigoRAIS = iDCodigoRAIS;
    }

    public Collection<OcorrenciaFuncionario> getTOcrFncCollection() {
        return tOcrFncCollection;
    }

    public void setTOcrFncCollection(Collection<OcorrenciaFuncionario> tOcrFncCollection) {
        this.tOcrFncCollection = tOcrFncCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cDTpoOcr != null ? cDTpoOcr.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoOcorrencia)) {
            return false;
        }
        TipoOcorrencia other = (TipoOcorrencia) object;
        if ((this.cDTpoOcr == null && other.cDTpoOcr != null) || (this.cDTpoOcr != null && !this.cDTpoOcr.equals(other.cDTpoOcr))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.jus.tjms.ddsi.sgp.bean.TTpoOcr[cDTpoOcr=" + cDTpoOcr + "]";
    }

}