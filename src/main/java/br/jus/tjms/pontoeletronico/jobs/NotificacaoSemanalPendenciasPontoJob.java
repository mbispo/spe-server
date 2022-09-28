package br.jus.tjms.pontoeletronico.jobs;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;

import br.jus.tjms.pontoeletronico.client.Constantes;
import br.jus.tjms.pontoeletronico.service.impl.NotificadorPendenciasFrequenciaImpl;

@Stateless
public class NotificacaoSemanalPendenciasPontoJob implements Serializable {
	
	private static final long serialVersionUID = 1526220289096352482L;
	
	@EJB
	private NotificadorPendenciasFrequenciaImpl notificadorPendenciasFrequencia;
	
	@Inject
    private Logger log;

	//@MessageDriven(activationConfig = {@ActivationConfigProperty(propertyName = "cronTrigger", propertyValue = "0 30 12 ? * *")})
	@Schedule(second="0", minute="30", hour="12", dayOfWeek="Mon,Tue,Wed,Thu,Fri", persistent=false)
	public void execute() {
		if (!Constantes.JOBS_ATIVOS) {
			return;
		}
		
		log.info("Executando job");
		
		notificadorPendenciasFrequencia.execute();
		
	}	
}