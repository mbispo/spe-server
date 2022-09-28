package br.jus.tjms.pontoeletronico.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;

import br.jus.tjms.pontoeletronico.bean.LogEjb;
import br.jus.tjms.pontoeletronico.service.LogServiceRemoto;

@Path("log")
@Stateless
@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
public class LogServiceImpl implements Serializable, LogServiceRemoto {

	private static final long serialVersionUID = 1L;

	@Inject
	private Logger logger;
	
	@PersistenceContext(unitName = "PontoEletronicoEmbDS")
	EntityManager em;
	
	@Path("gravar-logs")
	@POST
	@Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	@Override
	public boolean gravar(List<LogEjb> logs) {
		
		try {
			for (LogEjb log : logs) {
				em.persist(log);
			}
			return true;
		} catch (Exception e) {
			logger.error("Erro em LogServiceImpl.gravar(): "+e.getMessage(),e);
			throw new RuntimeException(e.getMessage());
		}
	}

	
	@Path("gravar")
	@POST
	@Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	@Override
	public boolean gravar(LogEjb log) {
		try {
			em.persist(log);
			return true;
		} catch (Exception e) {
			logger.error("Erro em LogServiceImpl.gravar(): "+e.getMessage(),e);
			throw new RuntimeException(e.getMessage());
		}
	}

}