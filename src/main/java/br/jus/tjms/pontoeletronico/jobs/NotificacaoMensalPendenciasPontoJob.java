package br.jus.tjms.pontoeletronico.jobs;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;

import br.jus.tjms.pontoeletronico.client.Constantes;
import br.jus.tjms.pontoeletronico.service.impl.NotificadorPendenciasFrequenciaImpl;

//esse job não está sendo executado, somente o semanal

@Stateless 
public class NotificacaoMensalPendenciasPontoJob implements Serializable {
	
	private static final long serialVersionUID = 3469139864704903520L;
	
	@EJB
	private NotificadorPendenciasFrequenciaImpl notificadorPendenciasFrequencia;
	
	@Inject
    private Logger log;

	//@Schedule(second="0", minute="30", hour="12", dayOfMonth="Last", persistent=false)
	public void execute() {
		if (!Constantes.JOBS_ATIVOS) {
			return;
		}
		
		log.info("Executando job");
		
		notificadorPendenciasFrequencia.execute();
	}
}