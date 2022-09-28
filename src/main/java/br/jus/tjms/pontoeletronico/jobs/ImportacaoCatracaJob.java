package br.jus.tjms.pontoeletronico.jobs;


import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.slf4j.Logger;

import br.jus.tjms.comuns.exceptions.DaoException;
import br.jus.tjms.pontoeletronico.bean.FuncionarioEjb;
import br.jus.tjms.pontoeletronico.bean.FuncionarioEjbPK;
import br.jus.tjms.pontoeletronico.bean.LogEjb;
import br.jus.tjms.pontoeletronico.bean.PontoEjb;
import br.jus.tjms.pontoeletronico.client.Constantes;
import br.jus.tjms.pontoeletronico.service.FuncionarioServiceRemoto;
import br.jus.tjms.pontoeletronico.service.LogServiceRemoto;
import br.jus.tjms.pontoeletronico.service.PontoServiceRemoto;


@Stateless
public class ImportacaoCatracaJob implements Serializable {
	
	private static final long serialVersionUID = 5337316066323671497L;

	@EJB
	ListaMatriculasImportar listaMatriculasImportar;
	
	@Inject
    private Logger log;
	
	@EJB
	PontoServiceRemoto pontoEjbService;
	
	@EJB
	LogServiceRemoto logEjbService;
	
	@EJB
	FuncionarioServiceRemoto funcionarioService;
	
	@PersistenceContext(unitName = "PontoEletronicoEmbDS")
	private EntityManager em;
	
	@Resource(mappedName="java:jboss/datasources/SeniorEmbDS")
	private DataSource dataSource;
	
	// a cada 30 minutos	
	@Schedule(second="0", minute="0,30", hour="4-23", dayOfWeek="Sun,Mon,Tue,Wed,Thu,Fri,Sat", persistent=false)
	public void execute() {
		
		if (!Constantes.JOBS_ATIVOS) {
			return;
		}
		
		log.info("Executando job");
		
		List <PontoEjb> pontos = new ArrayList<PontoEjb>();
		try {
			
			String matriculas = listaMatriculasImportar.getListaMatriculas();
			
			log.info("matriculas: "+matriculas);
			

			pontos = importar("("+ matriculas +")");

			
			//gravando ponto
			if (pontos.size() > 0){
				log.info("Gravando registros de ponto");	
				pontoEjbService.processarPonto(pontos);
			}			
		
		
			LogEjb  logEjb = null;
			List<LogEjb> logs = new ArrayList<LogEjb>();
		
			for (int i = 0; i < pontos.size(); i++) {

				listaMatriculasImportar.apagarDaLista(pontos.get(i).getFuncionarioEjb());

				// criando log
				logEjb = new LogEjb();
				logEjb.setClasse(ImportacaoCatracaJob.class.getName());
				logEjb.setDatahora(pontos.get(i).getDataHora());
				logEjb.setEmpresa(pontos.get(i).getFuncionarioEjb()
						.getEmpresa());
				logEjb.setMatricula(pontos.get(i).getFuncionarioEjb().getId());
				logEjb.setMensagem("Importado catraca");
				logEjb.setMetodo("importar");
				logEjb.setTipo("Informa��o");
				logEjb.setTipoOperacao("Presen�a registrada");

				logs.add(logEjb);

				// System.out.println(pontos.get(i).getFuncionarioEjb().getNome()
				// + " - " +
				// sdf.format(pontos.get(i).getDataHora()));
			}
		
			//gravando log
			if (logs.size() > 0 ){
				log.info("Gravando registros de log");
				logEjbService.gravar(logs);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//TODO pegar apenas um registro por matricula
	private String qryImportar = "select distinct c.numcad, r.numcra, "
			+ " hora = (select min(dbo.fn_inttohora((horacc*60))) " 
			+ " from dbo.R070ACC h "
			+ " where h.numcra = r.numcra "
			+ " and datacc = ? )"
			+ " from dbo.R070ACC r"
			+ " inner join dbo.R038HCH c on c.Numcra = r.Numcra"
			+ " where datacc = ? "
			+ " and c.numcad in <IN> "; 
	

	public List <PontoEjb> importar(String sqlIn) throws Exception{
		
		log.info("Obtendo registros na base de dados SENIOR");
		
		//FuncionarioService funcionarioService = ServiceFactory.getFuncionarioService();
		
		
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		Date data = Calendar.getInstance().getTime();
		
//		importar dia que n�o o atual
//		Date data = SimpleDateFormat.getDateTimeInstance().parse("20/04/2010 00:00:00");

		
		
		try{
			conn = dataSource.getConnection();

			String sql = qryImportar.replaceAll("<IN>", sqlIn);

			pst = conn.prepareStatement(sql);
			pst.setDate(1, new java.sql.Date(data.getTime()));
			pst.setDate(2, new java.sql.Date(data.getTime()));
			
			rs = pst.executeQuery();

			PontoEjb pontoEjb = null;
			List<PontoEjb> pontos = new ArrayList<PontoEjb>();
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

			while (rs.next()) {
				pontoEjb = new PontoEjb();

				String hora = rs.getString("hora");
				String dataHora = sf.format(data) + " " + hora.toString();

				pontoEjb.setDataHora(sd.parse(dataHora));
				// TODO pegar da lista em vez de buscar no banco
//				pontoEjb.setFuncionarioEjb(funcionarioService.buscarPorId(rs
//						.getInt("numcad"), Constantes.EMPRESA_DEFAULT));
				pontoEjb.setFuncionarioEjb(em.find(FuncionarioEjb.class, new FuncionarioEjbPK(rs.getInt("numcad"), Constantes.EMPRESA_DEFAULT)));
				pontoEjb.setLocalidade(Constantes.LOCALIDADE_DEFAULT);

				pontos.add(pontoEjb);

			}
			return pontos;
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Erro ao importar registros da base de dadso SENIOR ", e);
			throw new DaoException(e.getMessage(),e);
		} finally {
			if(rs!=null)try{rs.close();}catch(Exception e){}
			if(pst!=null)try{pst.close();}catch(Exception e){}
			if(conn!=null)try{conn.close();}catch(Exception e){}
		}		
		
	}

}