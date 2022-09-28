package br.jus.tjms.pontoeletronico.cdi;

import br.jus.tjms.comuns2.remote.ServiceClientUtils;
import br.jus.tjms.organizacional.rest.FuncionarioRestClient;
import br.jus.tjms.organizacional.service.FuncionarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import java.io.Serializable;

public class Producer implements Serializable {

	private static final long serialVersionUID = 254904919947563118L;

	@Inject
	private Configuration config;

	@Produces
	public FuncionarioService proverFuncionarioService() {
		return new FuncionarioRestClient(config,null);
	}


	@Produces
	public Logger producer(InjectionPoint ip) {
		return LoggerFactory.getLogger(ip.getMember().getDeclaringClass().getName());
	}

}
