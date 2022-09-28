package br.jus.tjms.pontoeletronico.service;

import br.jus.tjms.comuns.exceptions.ServiceException;
import br.jus.tjms.pontoeletronico.bean.ParametroEjb;

public interface ParametroServiceRemoto {

	public ParametroEjb getParametros() throws ServiceException;

}
