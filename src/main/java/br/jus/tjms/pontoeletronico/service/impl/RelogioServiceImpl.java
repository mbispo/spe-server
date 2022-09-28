package br.jus.tjms.pontoeletronico.service.impl;

import java.io.Serializable;
import java.util.Date;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.jus.tjms.pontoeletronico.client.Constantes;
import br.jus.tjms.pontoeletronico.factory.CalendarFactory;
import br.jus.tjms.pontoeletronico.service.RelogioServiceRemoto;

@Path("relogio")
@Stateless(name = "relogioService")
@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
@LocalBean
public class RelogioServiceImpl implements Serializable, RelogioServiceRemoto {

	private static final long serialVersionUID = 1L;
	
	@GET
	@Path("hora")
	public Date getDataHora() {
		return CalendarFactory.getCalendar().getTime();
	}
	
	@GET
	@Path("tz")
	public String getTimezone() {
		return Constantes.TIME_ZONE;
	}
}
