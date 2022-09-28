package br.jus.tjms.pontoeletronico.service;

import java.util.List;

import br.jus.tjms.comuns.exceptions.ServiceException;
import br.jus.tjms.pontoeletronico.bean.DigitalEjb;
import br.jus.tjms.pontoeletronico.to.DigitalTO;
import br.jus.tjms.pontoeletronico.to.FuncionarioTO;

/**
 * @autor: DDSI
 */
public interface DigitalServiceRemoto {

    // retorna para cliente

    public List<FuncionarioTO> buscarFuncionariosComNovasDigitais(int ndias);

    public List<FuncionarioTO> buscarFuncionariosComNovasDigitaisPorLotacao(int ndias, int codigoComarcaSecretaria, int codigoEmpresa);

    public List<FuncionarioTO> buscarFuncionariosComNovasDigitaisPorInstancia(int ndias, int codigoInstancia, int codigoEmpresa);

    public List<FuncionarioTO> buscarFuncionariosSemDigitaisPorLotacao(int codigoComarcaSecretaria, int codigoEmpresa);

    public List<FuncionarioTO> buscarFuncionariosSemDigitaisPorInstancia(int codigoInstancia, int codigoEmpresa);

    // recebe digitais do cliente, insere ou atualiza

    public boolean gravar(List<DigitalTO> digitais);

    public boolean gravarPorFuncionario(List<FuncionarioTO> funcionarios);

    public boolean gravar(DigitalTO digital);

    public void salvar(DigitalTO digital) throws ServiceException;

    public List<DigitalEjb> buscarTodos() throws ServiceException;

    public void remover(DigitalEjb digital) throws ServiceException;

    public DigitalTO buscarPorId(int id) throws ServiceException;

    public List<DigitalEjb> buscarNovas(int ndias) throws ServiceException;

    public List<DigitalEjb> buscarNovasPorLotacao(int ndias, int codigoComarcaSecretaria, int codigoEmpresa) throws ServiceException;

    public List<DigitalEjb> buscarNovasPorInstancia(int ndias, int codigoInstancia, int codigoEmpresa) throws ServiceException;

    public List<DigitalTO> buscarDigitaisPorFuncionario(FuncionarioTO funcionarioTO);

}
