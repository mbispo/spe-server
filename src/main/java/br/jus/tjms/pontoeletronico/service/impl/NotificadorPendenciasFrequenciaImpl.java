package br.jus.tjms.pontoeletronico.service.impl;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.sql.DataSource;

import org.slf4j.Logger;

import br.jus.tjms.comuns2.utils.Utilz;
import br.jus.tjms.msn.Messenger;

@Stateless(name="notificadorPendenciasFrequencia")
public class NotificadorPendenciasFrequenciaImpl implements  Serializable {

    private static final long serialVersionUID = 1L;
	
	private static final String SGP_DESC = "SGP.desc";
	private static final String SGP_PASSWORD = "SGP.password";
	private static final String SGP_USER = "SGP.user";

	@Resource(mappedName="java:jboss/datasources/PontoEletronicoEmbDS")
	private DataSource dataSource;
	
	@Inject
    private Logger log;

	public void execute(){
		
		PreparedStatement stm = null;
		
		try {
			
			ResourceBundle rb = ResourceBundle.getBundle("br.gov.ms.tj.ddsi.msn_config");
			
			// TODO verificar incompatibilidade da versão do messenger com comuns:
			/*
			<dependency>
			  <groupId>br.jus.tjms</groupId>
			  <artifactId>tjms-messenger</artifactId>
			  <version>0.0.1</version>
			</dependency>
			 */
			
			//Messenger msn = new Messenger(rb.getString(SGP_USER), rb.getString(SGP_PASSWORD), rb.getString(SGP_DESC));
			
			Calendar c = Calendar.getInstance();
			int ano = c.get(Calendar.YEAR);
			int mes = c.get(Calendar.MONTH);
			int dia = c.get(Calendar.DAY_OF_MONTH);
			
			if(dia <= 10){
				mes--;
			}
			
			String mesAtual = DateFormatSymbols.getInstance().getMonths()[mes];
			
			stm = buscarPontosComDebitos(mes, ano);			
			
			ResultSet result = stm.executeQuery();
			StringBuilder sb = new StringBuilder("LISTA DE funcionárioS\n");
			String msg = "Prezado(a) servidor(a), identificamos que existe alguma pendencia com o registro do seu ponto no mes de " + mesAtual.toUpperCase() +".\nPor favor, verifique seus pontos na Intranet.\nObs: Caso o mesmo esteja irregular por motivo de viagens com recebimento de diarias, desconsidere esta mensagem.";
			int i = 0;
			while(result.next()){
				i++;
				sb.append(result.getString(1) + "\n");
				try {
					//msn.sendMessage(result.getString(1), msg);
				} catch (Exception e) {
					sb.append(result.getString(1) + "- ERRO MSN - "+ e.getMessage() +"\n");
				}
				try {
					Utilz.enviarEmailHtml(msg, "Pendências na frequência", "notificador.sgp@tjms.jus.br", result.getString(1) + "@tjms.jus.br", "mail.intranet");
				} catch (Exception e) {
					sb.append(result.getString(1) + "- ERRO EMAIL - "+ e.getMessage() +"\n");
				}				
			}
            Utilz.enviarEmailHtml(sb.toString(), "Envio de pendencias na frequencia - " + i + " - " + mesAtual + " - " + new Date(), "notificador.sgp@tjms.jus.br", "joao.monteiro@tjms.jus.br", "mail.intranet");
		} catch (Exception e) {
			System.out.println("Exceção lançada - NotificadorPendenciasFrequenciaImpl -> " + e);
			e.printStackTrace();
			
		} finally {
			try {
				if(stm != null){
					stm.close();
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}			
		}
	}

	private PreparedStatement buscarPontosComDebitos(int mes, int ano) throws Exception {
		//String sql = "select f.nm_lgn from sgp.t_pntmes p join sgp.t_vfnc001 f on (f.cd_fnc = p.cd_fnc and f.cd_empsgp = p.cd_empsgp) where (nr_ttlfltdsc > 0 or VL_TtlAtrSdaCHIInjusDsc > 0) and f.nm_lgn is not null and f.cd_tpoprv not in(10,11,14,16,17,19,37) and nr_ano = ? and nr_mes = ?";
		String sql = "select f.nm_lgn " +
					 "from sgp.t_pntmes p join sgp.t_vfnc001 f on (f.cd_fnc = p.cd_fnc and f.cd_empsgp = p.cd_empsgp) " +
					 "where ((isNull(NR_TtlFltDsc,0) > 0 ) or (isNull(VL_TtlAtrSdaCHIInjusDsc,0) - isNull(VL_TtlPgtoAtrSdaCHICmpDsc,0) > 0)) and" +
					 " 		f.nm_lgn is not null and f.cd_tpoprv not in (3,4,9,10,11,14,16,17,21,19,22,27,29,33,36,37) and nr_ano = ? and nr_mes = ?";
		PreparedStatement stm = dataSource.getConnection().prepareStatement(sql);
		stm.setInt(1, ano);
		stm.setInt(2, mes+1);		
		return stm;		
	}
}