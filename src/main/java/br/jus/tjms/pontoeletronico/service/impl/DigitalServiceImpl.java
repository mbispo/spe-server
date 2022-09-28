package br.jus.tjms.pontoeletronico.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;

import br.jus.tjms.comuns.exceptions.DaoException;
import br.jus.tjms.comuns.exceptions.ServiceException;
import br.jus.tjms.pontoeletronico.bean.DigitalEjb;
import br.jus.tjms.pontoeletronico.bean.FuncionarioEjb;
import br.jus.tjms.pontoeletronico.bean.FuncionarioEjbPK;
import br.jus.tjms.pontoeletronico.service.DigitalServiceRemoto;
import br.jus.tjms.pontoeletronico.service.FuncionarioServiceRemoto;
import br.jus.tjms.pontoeletronico.to.DigitalTO;
import br.jus.tjms.pontoeletronico.to.FuncionarioTO;

/**
 * @autor: DDSI
 */
@Stateless(name = "digitalService")
@Path("digital")
@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
@LocalBean
public class DigitalServiceImpl implements DigitalServiceRemoto, Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName = "PontoEletronicoEmbDS")
    private EntityManager em;

    @Inject
    private Logger log;

    @Inject
    private FuncionarioServiceRemoto funcionarioService;
    
    @POST
    @Path(value = "salvar")
    public void salvar(@FormParam("digital") DigitalTO digital) throws ServiceException {

        try {
        	
        	DigitalEjb d = new DigitalEjb(digital);
        	FuncionarioEjb f = em.find(FuncionarioEjb.class, new FuncionarioEjbPK(digital.getMatricula(), digital.getEmpresa()));
        	d.setFuncionario(f);
            em.merge(d);
            
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServiceException(ex);
        }
    }

    // metodo para obter todas as digitais
    public List<DigitalEjb> buscarTodos() throws ServiceException {
        List<DigitalEjb> digitais = new ArrayList<DigitalEjb>();

        try {
            digitais = em.createQuery(
                    "Select d from DigitalEjb d ")
                    .getResultList();
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
        return digitais;
    }

    public void remover(DigitalEjb digital) throws ServiceException {
        try {
            em.remove(digital);
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }

    }
    
    @GET
    @Path(value = "buscar/digital-por-id/{id}")
    public DigitalTO buscarPorId(@PathParam("id") int id) throws ServiceException {
        DigitalEjb digital = null;

        try {
            digital = em.find(DigitalEjb.class, id);
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
        return digital.toTO();
    }

    public List<DigitalEjb> buscarNovasPorLotacao(int ndias, int codigoComarcaSecretaria, int codigoEmpresa) throws ServiceException {

        List<DigitalEjb> digitais = new ArrayList<DigitalEjb>();
        Query query;

        try {

            String queryPorData = "select d from DigitalEjb d where (d.dataCriacao >= :dataCriacao or d.dataModificacao >= :dataModificacao) and d.funcionario.codigoComarcaSecretaria = :codigoComarcaSecretaria and d.funcionario.empresa = :codigoEmpresa";
            String querySemData = "select d from DigitalEjb d where d.funcionario.codigoComarcaSecretaria = :codigoComarcaSecretaria and d.funcionario.empresa = :codigoEmpresa";

            if (ndias > 0) {

                query = em.createQuery(queryPorData);

                Calendar c = new GregorianCalendar();
                c.setTime(new Date());

                c.add(Calendar.DATE, ndias * -1);

                query.setParameter("dataCriacao", c.getTime(), TemporalType.TIMESTAMP);
                query.setParameter("dataModificacao", c.getTime(), TemporalType.TIMESTAMP);

                query.setParameter("codigoComarcaSecretaria", codigoComarcaSecretaria);
                query.setParameter("codigoEmpresa", codigoEmpresa);


                digitais = (List<DigitalEjb>) query.getResultList();

            } else {

                query = em.createQuery(querySemData);

                query.setParameter("codigoComarcaSecretaria", codigoComarcaSecretaria);
                query.setParameter("codigoEmpresa", codigoEmpresa);

                digitais = (List<DigitalEjb>) query.getResultList();

            }

            return digitais;

        } catch (Exception ex) {
            throw new ServiceException(ex);
        }

    }

    public List<DigitalEjb> buscarNovasPorInstancia(int ndias, int codigoInstancia, int codigoEmpresa) throws ServiceException {

        List<DigitalEjb> digitais = new ArrayList<DigitalEjb>();
        Query query;

        String queryPorData = "select d from DigitalEjb d where (d.dataCriacao >= :dataCriacao or d.dataModificacao >= :dataModificacao) and d.funcionario.codigoInstancia = :codigoInstancia and d.funcionario.empresa = :codigoEmpresa";
        String querySemData = "select d from DigitalEjb d where d.funcionario.codigoInstancia = :codigoInstancia and d.funcionario.empresa = :codigoEmpresa";

        try {

            if (ndias > 0) {

                query = em.createQuery(queryPorData);

                Calendar c = new GregorianCalendar();
                c.setTime(new Date());

                c.add(Calendar.DATE, ndias*-1);

                query.setParameter("dataCriacao", c.getTime(),TemporalType.TIMESTAMP);
                query.setParameter("dataModificacao", c.getTime(),TemporalType.TIMESTAMP);

                query.setParameter("codigoInstancia", codigoInstancia);
                query.setParameter("codigoEmpresa", codigoEmpresa);

                digitais = (List<DigitalEjb>)query.getResultList();

            } else {

                query = em.createQuery(querySemData);

                query.setParameter("codigoInstancia", codigoInstancia);
                query.setParameter("codigoEmpresa", codigoEmpresa);

                digitais = (List<DigitalEjb>)query.getResultList();

            }

            return digitais;
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }

    
    @POST
    @Path("buscar-digitais-por-funcionario" )
    @Override
    public List<DigitalTO> buscarDigitaisPorFuncionario(@FormParam("funcionarioTO") FuncionarioTO funcionarioTO) {
        Query query =  em.createQuery("select d from DigitalEjb d where d.funcionario = :funcionario");
        query.setParameter("funcionario", new FuncionarioEjb(funcionarioTO));
        List<DigitalEjb> lista = query.getResultList();
        return (toTOs(lista));
    }

	public List<DigitalEjb> buscarNovasBanco(int ndias) throws DaoException {

        List<DigitalEjb> lista;
        Query query;

        String queryPorData = "select d from DigitalEjb d where d.dataCriacao >= :dataCriacao or d.dataModificacao >= :dataModificacao";

        try {

            if (ndias > 0) {

                query = em.createQuery(queryPorData);

                Calendar c = new GregorianCalendar();
                c.setTime(new Date());

                c.add(Calendar.DATE, ndias * -1);

                query.setParameter("dataCriacao", c.getTime(), TemporalType.TIMESTAMP);
                query.setParameter("dataModificacao", c.getTime(), TemporalType.TIMESTAMP);

                lista = (List<DigitalEjb>) query.getResultList();

            } else {

                lista = buscarTodos();

            }

            return lista;

        } catch (Exception e) {
            log.error("Erro em DigitalDaoImpl.buscarNovas(): " + e.getMessage(), e);
            throw new DaoException("erro_nao_foi_possivel_buscar");
        }

    }

    /**
     * Retorna para o cliente as digitais que existem no servidor que sao novas,
     * ou seja, foram cadastradas nos ultimos n dias
     */
    public List<DigitalEjb> buscarNovas(int ndias) {

        List<DigitalEjb> lista = null;
        List<FuncionarioEjb> listafnc = null;

        try {
            lista = this.buscarNovasBanco(ndias);

            if (lista.size() > 0) {
                listafnc = new ArrayList<FuncionarioEjb>();

                for (DigitalEjb digitalEjb : lista) {
                    if (listafnc.indexOf(digitalEjb.getFuncionario()) == -1) {
                        listafnc.add(digitalEjb.getFuncionario());
                    }
                }
            }

        } catch (ServiceException e) {
            log.error("Erro ao buscar novas digitais: " + e.getMessage(), e);
            return null;
        }

        return lista;

    }

    /**
     * Retorna os funcionários que tiveram uma ou mais digitais cadastradas/alteradas nos ultimos ndias
     */
    @GET
    @Path(value = "buscar/funcionarios-com-novas-digitais/dias/{ndias}")
    public List<FuncionarioTO> buscarFuncionariosComNovasDigitais(@PathParam("ndias") int ndias) {

        List<DigitalEjb> listadigitais = null;
        List<FuncionarioTO> listafnc = null;

        try {
        	
            listadigitais = this.buscarNovasBanco(ndias);

                if (listadigitais.size() > 0) {
                listafnc = new ArrayList<FuncionarioTO>();

                for (DigitalEjb digitalEjb : listadigitais) {
                    if (listafnc.indexOf(digitalEjb.getFuncionario()) == -1) {
                        listafnc.add(digitalEjb.getFuncionario().toTO());
                    }
                }
            }
        } catch (ServiceException e) {
            log.error("Erro ao buscar funcionários com novas digitais: " + e.getMessage(), e);
            return null;
        }

        return listafnc;
    }

    /**
     * Retorna os funcionários que tiveram uma ou mais digitais cadastradas/alteradas nos ultimos ndias,
     * que estejam lotados na unidade organizacional especificada
     */
    @GET
    @Path(value = "buscar/funcionarios-novas-digitais-por-lotacao/dias/{ndias}/comarca-secretaria/{codigoComarcaSecretaria}/empresa/{codigoEmpresa}")
    public List<FuncionarioTO> buscarFuncionariosComNovasDigitaisPorLotacao(@PathParam("ndias") int ndias, @PathParam("codigoComarcaSecretaria") int codigoComarcaSecretaria, @PathParam("codigoEmpresa") int codigoEmpresa) {

        List<DigitalEjb> listadigitais = null;
        List<FuncionarioTO> listafnc = null;

        log.info("[DigitalEjbService.buscarFuncionariosComNovasDigitaisPorLotacao] (int ndias = " + ndias + ", int codigoComarcaSecretaria = " + codigoComarcaSecretaria + ", int codigoEmpresa = " + codigoEmpresa + ")");

        try {
            listadigitais = this.buscarNovasPorLotacao(ndias, codigoComarcaSecretaria, codigoEmpresa);

            if (listadigitais.size() > 0) {
                listafnc = new ArrayList<FuncionarioTO>();

                for (DigitalEjb digitalEjb : listadigitais) {
                    if (listafnc.indexOf(digitalEjb.getFuncionario()) == -1) {
                        listafnc.add(digitalEjb.getFuncionario().toTO());
                    }
                }
            }
        } catch (ServiceException e) {
            log.error("Erro ao buscar funcionários com novas digitais por lotação: " + e.getMessage(), e);
            return null;
        }

        log.info("[DigitalEjbService.buscarFuncionariosComNovasDigitaisPorInstancia] retornando listafnc, tamanho = " + (listafnc != null ? listafnc.size() : "lista nula..."));

        return listafnc;
    }

    /**
     * Retorna os funcionários que tiveram uma ou mais digitais cadastradas/alteradas nos ultimos ndias,
     * que estejam lotados na instancia especificada
     */
    @GET
    @Path(value = "buscar/funcionarios-novas-digitais-por-instancia/dias/{ndias}/instancia/{codigoInstancia}/empresa/{codigoEmpresa}")
    public List<FuncionarioTO> buscarFuncionariosComNovasDigitaisPorInstancia(@PathParam("ndias") int ndias, @PathParam("codigoInstancia") int codigoInstancia, @PathParam("codigoEmpresa") int codigoEmpresa) {
        List<DigitalEjb> listadigitais = null;
        List<FuncionarioTO> listafnc = null;

        log.info("[DigitalEjbService.buscarFuncionariosComNovasDigitaisPorInstancia] (int ndias = " + ndias + ", int codigoInstancia = " + codigoInstancia + ", int codigoEmpresa = " + codigoEmpresa + ")");

        try {

            listadigitais = this.buscarNovasPorInstancia(ndias, codigoInstancia, codigoEmpresa);

            if (listadigitais.size() > 0) {
                listafnc = new ArrayList<FuncionarioTO>();

                for (DigitalEjb digitalEjb : listadigitais) {
                    if (listafnc.indexOf(digitalEjb.getFuncionario()) == -1) {
                        listafnc.add(digitalEjb.getFuncionario().toTO());
                    }
                }
            }
        } catch (ServiceException e) {
            log.error("Erro ao buscar funcionários com novas digitais por instância: " + e.getMessage(), e);
            return null;
        }

        log.info("[DigitalEjbService.buscarFuncionariosComNovasDigitaisPorInstancia] retornando listafnc, tamanho = " + (listafnc != null ? listafnc.size() : "lista nula..."));

        return listafnc;
    }

    /**
     * Retorna os funcionários que Não tem digitais lotados na unidade organizacional especificada
     */
    @GET
    @Path(value = "buscar/funcionarios-sem-digitais-por-lotacao/comarca/{comarca}/empresa/{codigoEmpresa}")
    public List<FuncionarioTO> buscarFuncionariosSemDigitaisPorLotacao(@PathParam("comarca") int codigoComarcaSecretaria, @PathParam("codigoEmpresa") int codigoEmpresa) {
        List<FuncionarioEjb> listafnc = null;

        try {

            listafnc = funcionarioService.buscarSemDigitaisPorCodigoComarcaSecretaria(codigoComarcaSecretaria, codigoEmpresa);

        } catch (ServiceException e) {
            log.error("Erro ao buscar funcionários sem digitais por lotacao: " + e.getMessage(), e);
            return null;
        }

        return funcionariosToTOs(listafnc);
    }

	/**
     * Retorna os funcionários que Não tem digitais lotados na instancia especificada
     */
    @GET
    @Path(value = "buscar/funcionarios-sem-digitais-por-instancia/instancia/{codigoInstancia}/empresa/{codigoEmpresa}")
    public List<FuncionarioTO> buscarFuncionariosSemDigitaisPorInstancia(@PathParam("codigoInstancia") int codigoInstancia, @PathParam("codigoEmpresa") int codigoEmpresa) {
        List<FuncionarioEjb> listafnc = null;

        try {

            listafnc = funcionarioService.buscarSemDigitaisPorCodigoInstancia(codigoInstancia, codigoEmpresa);

        } catch (ServiceException e) {
            log.error("Erro ao buscar funcionários sem digitais por instancia: " + e.getMessage(), e);
            return null;
        }

        return funcionariosToTOs(listafnc);
    }


    /**
     * Grava no banco de dados as digitais da lista contida no parametro
     * digitais (acrescenta  as digitais  ja existentes)
     */
    @POST
    @Path("gravar")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public boolean gravar(List<DigitalTO> digitais) {
        try {

            for (DigitalTO digitalTO : digitais) {
                this.salvar(digitalTO);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Erro ao gravar digitais: " + e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
    }

    private List<DigitalEjb> buscarDigitaisEjbPorFuncionario(FuncionarioEjb funcionario) {
        Query query =  em.createQuery("select d from DigitalEjb d where d.funcionario = :funcionario");
        query.setParameter("funcionario", funcionario);
        List<DigitalEjb> lista = query.getResultList();
        return (lista);
    }
    
	private FuncionarioEjb buscarFuncionarioEjbPorId(int matricula, int empresa) throws ServiceException {

		try {
			FuncionarioEjb f = em.find(FuncionarioEjb.class, new FuncionarioEjbPK(matricula, empresa));
			return f;
		} catch (Exception e) {
			throw new ServiceException("erro_nao_foi_possivel_buscar");
		}

	}
    
    @POST
    @Path(value = "gravar-funcionario")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public boolean gravarPorFuncionario(List<FuncionarioTO> funcionarios) {

        try {

            log.warn("[DigitalEjbService.gravarPorFuncionario] Iniciando... ");

            for (FuncionarioTO funcionarioTO : funcionarios) {
                
                log.warn("[DigitalEjbService.gravarPorFuncionario] ---------------------------------------------------------------------------------------------------------");
                
                // obtem instancia nova do funcionário
                FuncionarioEjb funcionarioEjbTemp = buscarFuncionarioEjbPorId(funcionarioTO.getMatricula(), funcionarioTO.getEmpresa());
                List<DigitalEjb> digitaisAtuais = buscarDigitaisEjbPorFuncionario(funcionarioEjbTemp);
                
                log.warn("[DigitalEjbService.gravarPorFuncionario] nº de digitais atuais: " + (digitaisAtuais!=null?digitaisAtuais.size():0));
                
                log.warn("[DigitalEjbService.gravarPorFuncionario] gravando novas digitais do funcionário: empresa=" + funcionarioEjbTemp.getEmpresa() + "; matricula="+funcionarioEjbTemp.getMatricula()+"; nome="+ funcionarioEjbTemp.getNome());
                
                log.warn("[DigitalEjbService.gravarPorFuncionario] nº de digitais enviadas: " + (funcionarioTO.getDigitais()!=null?funcionarioTO.getDigitais().size():0));

                // adiciona as digitais que foram enviadas (se houver alguma)
                if (funcionarioTO.getDigitais()!=null && funcionarioTO.getDigitais().size()>0) {

                    // força a remoção das digitais  atuais (somente se vier alguma digital do cliente)
                    log.warn("[DigitalEjbService.gravarPorFuncionario] removendo digitais atuais do funcionário...");
                    funcionarioService.removerDigitais(funcionarioEjbTemp);
                    
                    log.warn("[DigitalEjbService.gravarPorFuncionario] " + funcionarioTO.getDigitais().size() + " digitais foram enviadas pelo cliente, gravando...");

                    for (DigitalTO d : funcionarioTO.getDigitais()) {

                        // adiciona as digitais enviadas pelo cliente
                        this.salvar(d);

                        log.warn("[DigitalEjbService.gravarPorFuncionario] digital gravada: " + d.toString());

                    }
                    
                    digitaisAtuais = buscarDigitaisEjbPorFuncionario(funcionarioEjbTemp);
                    
                    log.warn("[DigitalEjbService.gravarPorFuncionario] nº de digitais final: " + (digitaisAtuais!=null?digitaisAtuais.size():0));

                }

                log.warn("[DigitalEjbService.gravarPorFuncionario] ---------------------------------------------------------------------------------------------------------");

            }

            log.warn("[DigitalEjbService.gravarPorFuncionario] Concluído.");

            return true;
        } catch (Exception e) {
            log.warn("Erro ao gravar digitais por funcionário: " + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Grava a digital do funcionário (acrescenta as digitais ja existentes)
     */   
    @POST
    @Path(value = "gravar-digital")
    public boolean gravar(@FormParam("digital") DigitalTO digital) {
        try {
            this.salvar(digital);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Erro ao gravar digital: " + e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
    }
    
    
	private List<DigitalTO> toTOs(List<DigitalEjb> lista) {
		List<DigitalTO> retorno = new ArrayList<>();
		if (lista!=null) {
			for (DigitalEjb d : lista) {
				retorno.add(d.toTO());
				
			}
		}
		return retorno;
	}
	
    private List<FuncionarioTO> funcionariosToTOs(List<FuncionarioEjb> listafnc) {
    	List<FuncionarioTO> retorno = new ArrayList<>();
    	
    	for (FuncionarioEjb f : listafnc) {
			retorno.add(f.toTO());
		}
    	
		return retorno;
	}

}