package br.jus.tjms.pontoeletronico.jobs;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.sql.DataSource;

import org.slf4j.Logger;

import br.jus.tjms.comuns.exceptions.DaoException;
import br.jus.tjms.pontoeletronico.bean.FuncionarioEjb;
import br.jus.tjms.pontoeletronico.client.Constantes;

@Singleton
public class ListaMatriculasImportar implements Serializable {
	
	private static final long serialVersionUID = 612260017713227953L;

	private String matriculas;
	
	private String afastados;
	
	private List<FuncionarioEjb> funcionariosImportar;
	
	@Resource(mappedName="java:jboss/datasources/PontoEletronicoEmbDS")
	private DataSource dataSource;
	
	@Inject
    private Logger log;
	

	private String qry = " Select f.CD_Fnc, DS_CmrScrFnl, f.CD_EmpSgp, f.CD_stcfncfnl, NM_Fnc, "
		+ " cd_nmecrgfnl, ds_crgFnl "
		+ " from sgp.T_CrgIsnPnt p "
		+ " join sgp.t_vfnc001 f on f.cd_nmecrgfnl = p.cd_nmecrg "
		+ " where f.cd_stcfncfnl = 1  "
		+ " and f.cd_empsgp = 1 " 
		+ " and f.CD_HrrFnl = 2 " 
		+ " and p.DT_FimIsn is null "
	    + " and f.cd_fnc not in ((	select cd_fnc from sgp.t_horpnt p " 
	    +		"	where p.nr_dia = (select substring(convert(varchar(10), getdate(),103), 1,2)) "
	    + 		"	and p.nr_mes = (select substring(convert(varchar(10), getdate(),103), 4,2)) "
	    +		"	and p.nr_ano = (select substring(convert(varchar(10), getdate(),103), 7,4))) ) "	
	    + " and f.CD_Fnc not in <afastados> "  
		+ " union all "
		+ " Select f.CD_Fnc, DS_CmrScrFnl, f.CD_EmpSgp, f.CD_stcfncfnl, NM_Fnc, cd_nmecrgfnl, ds_crgFnl"
		+ " from sgp.T_FncAtzRgsprs r"
		+ " join sgp.t_vfnc001 f on f.cd_Fnc = r.cd_Fnc "
		+ " where f.cd_stcfncfnl = 1 "
		+ " and f.cd_empsgp = 1 "
		+ " and r.DT_FimIsn is null "
		+ " and f.CD_HrrFnl = 2 " 
	    + " and f.cd_fnc not in ((	select cd_fnc from sgp.t_horpnt p " 
	    +		"	where p.nr_dia = (select substring(convert(varchar(10), getdate(),103), 1,2)) "
	    + 		"	and p.nr_mes = (select substring(convert(varchar(10), getdate(),103), 4,2)) "
	    +		"	and p.nr_ano = (select substring(convert(varchar(10), getdate(),103), 7,4))) ) "
	    + " and f.CD_Fnc not in <afastados>"
	    + " union all " 
   		+ " Select f.CD_Fnc, DS_CmrScrFnl, f.CD_EmpSgp, f.CD_stcfncfnl, NM_Fnc, cd_nmecrgfnl, ds_crgFnl" 
   		+ " from sgp.T_EspCrgIsnPnt s " 
   		+ " join sgp.t_vfnc001 f on f.cd_Espcrg = s.CD_EspCrg " 
   		+ " where f.cd_stcfncfnl = 1 	    " 
   		+ " and f.cd_empsgp = 1 " 
   		+ "	and s.DT_FimIsn is null " 
   		+ "	and f.CD_HrrFnl = 2   " 
   		+ "	and f.cd_fnc not in ((	select cd_fnc from sgp.t_horpnt p  " 
   		+ "		where p.nr_dia = (select substring(convert(varchar(10), getdate(),103), 1,2)) " 
   		+ "		and p.nr_mes = (select substring(convert(varchar(10), getdate(),103), 4,2)) " 
   		+ "	   	and p.nr_ano = (select substring(convert(varchar(10), getdate(),103), 7,4))) ) "
   		+ " and f.CD_Fnc not in <afastados>"; 	
	
	
	//retorna matricula de quem está de licença ou férias no dia
	private String qryAfastamento = " set dateformat mdy " +
		"declare @DI datetime  declare @DF datetime " +
		"declare @DA datetime " +
		"set @DI = (select substring(convert(varchar(10), getdate(),101), 1,12) + ' 00:00:00') " +
		"set @DF = (select substring(convert(varchar(10), getdate(),101), 1,12) + ' 23:59:59') " +
		"select o.CD_Fnc " +
		"from sgp.V_ocrgrl o " +
		"join sgp.t_vfnc001 f on o.cd_fnc = f.cd_fnc and f.cd_empsgp = 1 "+
		"where CD_ClsTpoOcr in (5,6,16) " + 
		"and cd_Tpoocr not in (182) " +
		"and ( o.DT_FltIni < @DI AND o.DT_FltFnl IS NULL " +
		"      OR (o.DT_FltIni BETWEEN @DI AND @DF) " +
		"      OR (o.DT_FltFnl BETWEEN @DI AND @DF) " +
		"      OR (o.DT_FltIni < @DI AND O.DT_FltFnl > @DF)) " +
		"and o.CD_EmpSgp = 1 "+
		"and f.cd_hrrfnl = 2 "+
		"order by cd_ocrfnc desc ";
	
	//pesquisa teste servidores do dsa
	//private String qry = "select CD_Fnc, NM_Fnc from sgp.t_vfnc001 where cd_cmrscrfnl = 159 and cd_vradpt = 646 and cd_fnc > 0 "; 
	
	
	public String getListaMatriculas(){
		
		log.info("Obtendo matriculas da lista de funcionarios");
		
		matriculas = "";
		try {
			getListaFuncionarios();
			for (int i = 0; i < funcionariosImportar.size(); i++) {
				if (matriculas == ""){
					matriculas = funcionariosImportar.get(i).getId().toString();
				} else {
					matriculas = matriculas +","+ funcionariosImportar.get(i).getId().toString();
				}
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return matriculas;
	}
	
	public void getListaFuncionarios() throws Exception {
		if (funcionariosImportar == null){
			funcionariosImportar = obterListaFuncionarios();
		}

	}
	
	public void getNovaListaFuncionarios() throws Exception{
		funcionariosImportar = null;
		funcionariosImportar = obterListaFuncionarios();
	}
	
	public void apagarDaLista(FuncionarioEjb funcionario){
		if (funcionariosImportar.indexOf(funcionario) >= 0) {
			funcionariosImportar.remove(funcionario);
		}
	}

	
	public List<FuncionarioEjb> obterListaFuncionarios() throws Exception {

		log.info("Obtendo funcionarios na base de dados SAD.sgp");
		afastados = null;
		afastados = obterListaAfastados();
				
		List<FuncionarioEjb> funcionarios = new ArrayList<FuncionarioEjb>();

		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try{
			qry = qry.replaceAll("<afastados>", afastados);
			
			conn = dataSource.getConnection();

			pst = conn.prepareStatement(qry);

			rs = pst.executeQuery();

			while (rs.next()) {

				FuncionarioEjb funcionario = new FuncionarioEjb();
				funcionario.setEmpresa(Constantes.EMPRESA_DEFAULT);
				funcionario.setId(rs.getInt("CD_Fnc"));
				funcionarios.add(funcionario);

			}

			return funcionarios;
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Erro ao obter funcionarios na base de dados SAD.sgp ", e);
			throw new DaoException(e.getMessage(),e);
		
		} finally {
			if(rs!=null)try{rs.close();}catch(Exception e){}
			if(pst!=null)try{pst.close();}catch(Exception e){}
			if(conn!=null)try{conn.close();}catch(Exception e){}
		}			
		
	}	
	
	private String obterListaAfastados() throws Exception{
		
		String s = "";
		
		log.info("Obtendo funcionarios afastados na base de dados SAD.sgp");

		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try{
			conn = dataSource.getConnection();

			pst = conn.prepareStatement(qryAfastamento);

			rs = pst.executeQuery();

			while (rs.next()) {
				
				if (s == ""){
					s = rs.getString("CD_Fnc");
				} else {
					s = s + ","+ rs.getString("CD_Fnc");
				}

			}
			log.info("afastados:" + s);
			return "("+s+")";
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Erro ao obter funcionarios afastados na base de dados SAD.sgp ", e);
			throw new DaoException(e.getMessage(),e);
		
		} finally {
			if(rs!=null)try{rs.close();}catch(Exception e){}
			if(pst!=null)try{pst.close();}catch(Exception e){}
			if(conn!=null)try{conn.close();}catch(Exception e){}
		}			
	}

}