package br.jus.tjms.pontoeletronico.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import br.jus.tjms.comuns.exceptions.ServiceException;
import br.jus.tjms.pontoeletronico.bean.FuncionarioEjb;
import br.jus.tjms.pontoeletronico.bean.NomeacaoExoneracao;
import br.jus.tjms.pontoeletronico.to.FuncionarioTO;

@Local
public interface FuncionarioServiceRemoto {

    // salvar um objeto funcionário
    public void salvar(FuncionarioEjb funcionario) throws ServiceException;

    // atualizar
    public void atualizar(FuncionarioEjb funcionario) throws ServiceException;

    // refresh
    public void refresh(FuncionarioEjb funcionario) throws ServiceException;

    // buscar um funcionario pela ID
    public FuncionarioTO buscarPorId(int id) throws ServiceException;

    // metodo para obter todos os funcionario
    public List<FuncionarioEjb> buscarTodos() throws ServiceException;

    // busca por matricula/empresa
    public FuncionarioTO buscarPorId(int matricula, int empresa) throws ServiceException;

    public void removerDigitais(FuncionarioEjb funcionarioejbtemp) throws ServiceException;

    public List<FuncionarioTO> buscarPorNome(String nome) throws ServiceException;

    public List<FuncionarioEjb> buscarPorEmpresa(int codigoEmpresa) throws ServiceException;

    public List<FuncionarioEjb> buscarPorCodigoComarcaSecretaria(int codigoComarcaSecretaria, int codigoEmpresa)
	    throws ServiceException;

    public List<FuncionarioEjb> buscarComDigitaisPorCodigoComarcaSecretaria(int codigoComarcaSecretaria,
	    int codigoEmpresa) throws ServiceException;

    public List<FuncionarioEjb> buscarSemDigitaisPorCodigoComarcaSecretaria(int codigoComarcaSecretaria,
	    int codigoEmpresa) throws ServiceException;

    public List<FuncionarioEjb> buscarSemDigitaisPorCodigoInstancia(int codigoInstancia, int codigoEmpresa)
	    throws ServiceException;

    public NomeacaoExoneracao buscarNomeacaoExoneracao(Date data, int matricula) throws ServiceException;

    public List<FuncionarioEjb> buscarFuncionarios(String matriculas) throws ServiceException;
    // public FuncionarioEjb buscarInformacaoDoDia(Date data, int matricula)
    // throws ServiceException;
    
    public String buscarSenhaIntranet(int matricula, int codigoEmpresa) throws ServiceException;

}
