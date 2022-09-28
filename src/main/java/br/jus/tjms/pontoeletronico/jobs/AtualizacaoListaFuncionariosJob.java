package br.jus.tjms.pontoeletronico.jobs;


import java.io.Serializable;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;

import br.jus.tjms.pontoeletronico.client.Constantes;

@Stateless
public class AtualizacaoListaFuncionariosJob implements Serializable {
	
	private static final long serialVersionUID = -5409346013167480082L;

	@EJB
	ListaMatriculasImportar listaMatriculasImportar;
	
	@Inject
    private Logger log;

	@Schedule(second="0", minute="0", hour="7", persistent=false)
	public void execute() {
		if (!Constantes.JOBS_ATIVOS) {
			return;
		}
		
		log.info("Executando job");
		
		try {
			
			listaMatriculasImportar.getNovaListaFuncionarios();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

}