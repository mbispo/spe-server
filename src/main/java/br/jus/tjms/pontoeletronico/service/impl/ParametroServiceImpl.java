package br.jus.tjms.pontoeletronico.service.impl;


import java.io.Serializable;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;

import br.jus.tjms.comuns.exceptions.DaoException;
import br.jus.tjms.comuns.exceptions.ServiceException;
import br.jus.tjms.pontoeletronico.bean.ParametroEjb;
import br.jus.tjms.pontoeletronico.service.ParametroServiceRemoto;

@Stateless(name = "parametroService")
@Path("parametro")
@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
@LocalBean
public class ParametroServiceImpl implements ParametroServiceRemoto, Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "PontoEletronicoEmbDS")
	private EntityManager em;

	@Inject
	private Logger log;
	
	@GET
    @Path("obter")
	public ParametroEjb getParametros() throws ServiceException {
		try {
			ParametroEjb p = em.find(ParametroEjb.class, 1);
			
			log.info("ParametroServiceImpl.getParametros(): parametros encontrados, retornando =  "+p.toString());
			
			return p;
		} catch (DaoException e) {
			log.error("Erro em ParametroServiceImpl.getParametros(): "+e.getMessage(),e);
			throw new ServiceException(e);
		}
		
	}

}
