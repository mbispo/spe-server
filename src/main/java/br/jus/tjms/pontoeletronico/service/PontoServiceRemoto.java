package br.jus.tjms.pontoeletronico.service;

import java.util.List;

import javax.ejb.Local;

import br.jus.tjms.comuns.exceptions.ServiceException;
import br.jus.tjms.pontoeletronico.bean.PontoEjb;
import br.jus.tjms.pontoeletronico.bean.PontoMes;

@Local
public interface PontoServiceRemoto {

	public boolean processarPonto(List<PontoEjb> pontos);

	//salvar um objeto ponto
	public void salvarPontoMes(PontoMes ponto) throws ServiceException;

	//buscar um Ponto do Mes
	public PontoMes buscarPontoMes(int empresa, int matricula, int mes, int ano) throws ServiceException;

}
