package br.jus.tjms.pontoeletronico.service.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.sql.DataSource;
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
import br.jus.tjms.pontoeletronico.bean.FuncionarioEjb;
import br.jus.tjms.pontoeletronico.bean.FuncionarioEjbPK;
import br.jus.tjms.pontoeletronico.bean.NomeacaoExoneracao;
import br.jus.tjms.pontoeletronico.service.FuncionarioServiceRemoto;
import br.jus.tjms.pontoeletronico.to.FuncionarioTO;

@Stateless(name = "funcionarioService")
@Path("funcionario")
@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
@LocalBean
public class FuncionarioServiceImpl implements FuncionarioServiceRemoto, Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "PontoEletronicoEmbDS")
	private EntityManager em;
	
    @Resource(mappedName="java:jboss/datasources/SegurancaEmbDS")
    private DataSource segurancaDS;	

	@Inject
	private Logger log;

	@POST
	@Path("remover-digitais")
	public void removerDigitais(@FormParam("funcionarioejb") FuncionarioEjb funcionarioejb) throws ServiceException {
		try {

			String jpql = "delete from DigitalEjb d where d.funcionario = :funcionario";

			Query q = em.createQuery(jpql);
			q.setParameter("funcionario", funcionarioejb);

			int i = q.executeUpdate();

			log.warn("FuncionarioDaoImpl.removerDigitais(): nº de digitais removidas = " + i);

		} catch (Exception e) {
			log.error("Erro em FuncionarioDaoImpl.removerDigitais(): " + e.getMessage(), e);
			throw new ServiceException("erro_nao_foi_possivel_remover_digitais");
		}
	}

	// metodo para obter todos os funcionarios
	@POST
	@Path("buscar-todos")
	public List<FuncionarioEjb> buscarTodos() throws ServiceException {
		List<FuncionarioEjb> funcionarios = new ArrayList<FuncionarioEjb>();

		try {
			funcionarios = em.createQuery("Select f from FuncionarioEjb f").getResultList();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return funcionarios;
	}

	@POST
	@Path("salvar")
	public void salvar(@FormParam("funcionario") FuncionarioEjb funcionario) throws ServiceException {
		try {
			em.merge(funcionario);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		}
	}

	@POST
	@Path("atualizar")
	public void atualizar(@FormParam("funcionario") FuncionarioEjb funcionario) throws ServiceException {
		try {
			em.merge(funcionario);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		}
	}

	@POST
	@Path("refresh")
	public void refresh(@FormParam("funcionario") FuncionarioEjb funcionario) throws ServiceException {
		try {
			em.refresh(funcionario);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		}
	}

	@GET
	@Path("buscar/{id}")
	public FuncionarioTO buscarPorId(@PathParam("id") int id) throws ServiceException {
		FuncionarioEjb funcionario = null;

		try {
			funcionario = em.find(FuncionarioEjb.class, new FuncionarioEjbPK(id, 1));

		} catch (Exception ex) {
			throw new ServiceException(ex);
		}
		return funcionario!=null?funcionario.toTO():null;
	}

	@GET
	@Path("buscar/matricula/{matricula}/empresa/{empresa}")
	public FuncionarioTO buscarPorId(@PathParam("matricula") int matricula, @PathParam("empresa") int empresa) throws ServiceException {

		try {
			FuncionarioEjb f = em.find(FuncionarioEjb.class, new FuncionarioEjbPK(matricula, empresa));
			return f.toTO();
		} catch (Exception e) {
			log.error("Erro em FuncionarioDaoImpl.buscarPorId(): " + e.getMessage(), e);
			throw new ServiceException("erro_nao_foi_possivel_buscar");
		}

	}

	@GET
	@Path("buscar-com-digitais/comarca-secretaria/{codigoComarcaSecretaria}/empresa/{empresa}")
	public List<FuncionarioEjb> buscarComDigitaisPorCodigoComarcaSecretaria(@PathParam("codigoComarcaSecretaria") int codigoComarcaSecretaria, @PathParam("empresa") int codigoEmpresa)
			throws ServiceException {
		List<FuncionarioEjb> funcionarios = new ArrayList<FuncionarioEjb>();

		try {
			String jpql = "select f from FuncionarioEjb f where f.codigoComarcaSecretaria = :codigoComarcaSecretaria and f.empresa = :codigoEmpresa and (SIZE(f.digitais) > 0)";
			Query query = em.createQuery(jpql);

			query.setParameter("codigoComarcaSecretaria", codigoComarcaSecretaria);
			query.setParameter("codigoEmpresa", codigoEmpresa);

			funcionarios = (List<FuncionarioEjb>) query.getResultList();

			return funcionarios;

		} catch (Exception e) {
			log.error("Erro em FuncionarioDaoImpl.buscarComDigitaisPorCodigoComarcaSecretaria(): " + e.getMessage(), e);
			throw new ServiceException("erro_nao_foi_possivel_buscar");
		}

	}

	@GET
	@Path("buscar/comarca-secretaria/{codigoComarcaSecretaria}/empresa/{codigoEmpresa}")
	public List<FuncionarioEjb> buscarPorCodigoComarcaSecretaria(@PathParam("codigoComarcaSecretaria") int codigoComarcaSecretaria, @PathParam("codigoEmpresa") int codigoEmpresa)
			throws ServiceException {
		List<FuncionarioEjb> funcionarios = new ArrayList<FuncionarioEjb>();

		try {
			String jpql = "select f from FuncionarioEjb f where f.codigoComarcaSecretaria = :codigoComarcaSecretaria and f.empresa = :codigoEmpresa";
			Query query = em.createQuery(jpql);

			query.setParameter("codigoComarcaSecretaria", codigoComarcaSecretaria);
			query.setParameter("codigoEmpresa", codigoEmpresa);

			funcionarios = (List<FuncionarioEjb>) query.getResultList();

			return funcionarios;
		} catch (Exception e) {
			log.error("Erro em FuncionarioDaoImpl.buscarPorCodigoComarcaSecretaria(): " + e.getMessage(), e);
			throw new ServiceException("erro_nao_foi_possivel_buscar");
		}
	}

	@GET
	@Path("buscar/empresa/{codigoEmpresa}")
	public List<FuncionarioEjb> buscarPorEmpresa(@PathParam("codigoEmpresa") int codigoEmpresa) throws ServiceException {

		List<FuncionarioEjb> funcionarios = new ArrayList<FuncionarioEjb>();

		try {
			String jpql = "select f from FuncionarioEjb f where f.empresa = :codigoEmpresa";
			Query query = em.createQuery(jpql);

			query.setParameter("codigoEmpresa", codigoEmpresa);

			funcionarios = (List<FuncionarioEjb>) query.getResultList();

			return funcionarios;
		} catch (Exception e) {
			log.error("Erro em FuncionarioDaoImpl.buscarPorEmpresa(): " + e.getMessage(), e);
			throw new ServiceException("erro_nao_foi_possivel_buscar");
		}
	}

	@GET
	@Path("buscar/nome/{nome}")
	public List<FuncionarioTO> buscarPorNome(@PathParam("nome") String nome) throws ServiceException {
		List<FuncionarioEjb> funcionarios = new ArrayList<FuncionarioEjb>();

		try {
			String jpql = "select f from FuncionarioEjb f where UPPER(f.nome) like UPPER('%" + nome + "%')";
			Query query = em.createQuery(jpql);

			funcionarios = (List<FuncionarioEjb>) query.getResultList();

			return toTOs(funcionarios);

		} catch (DaoException e) {
			log.error("Erro em FuncionarioDaoImpl.buscarPorNome(): " + e.getMessage(), e);
			throw new ServiceException("erro_nao_foi_possivel_buscar");
		}
	}
	
	

	@GET
	@Path("buscar-sem-digitais/comarca-secretaria/{codigoComarcaSecretaria}/empresa/{codigoEmpresa}")
	public List<FuncionarioEjb> buscarSemDigitaisPorCodigoComarcaSecretaria(@PathParam("codigoComarcaSecretaria") int codigoComarcaSecretaria, @PathParam("codigoEmpresa") int codigoEmpresa)
			throws ServiceException {
		List<FuncionarioEjb> funcionarios = new ArrayList<FuncionarioEjb>();

		try {
			String jpql = "select f from FuncionarioEjb f where f.codigoComarcaSecretaria = :codigoComarcaSecretaria and f.empresa = :codigoEmpresa and (SIZE(f.digitais) = 0)";
			Query query = em.createQuery(jpql);

			query.setParameter("codigoComarcaSecretaria", codigoComarcaSecretaria);
			query.setParameter("codigoEmpresa", codigoEmpresa);

			funcionarios = (List<FuncionarioEjb>) query.getResultList();

			return funcionarios;
		} catch (DaoException e) {
			log.error("Erro em FuncionarioDaoImpl.buscarSemDigitaisPorCodigoComarcaSecretaria(): " + e.getMessage(), e);
			throw new ServiceException("erro_nao_foi_possivel_buscar");
		}
	}

	@GET
	@Path("buscar-sem-digitais/instancia/{codigoInstancia}/empresa/{codigoEmpresa}")
	public List<FuncionarioEjb> buscarSemDigitaisPorCodigoInstancia(@PathParam("codigoInstancia") int codigoInstancia, @PathParam("codigoEmpresa") int codigoEmpresa) throws ServiceException {
		List<FuncionarioEjb> funcionarios = new ArrayList<FuncionarioEjb>();

		try {
			String jpql = "select f from FuncionarioEjb f where f.codigoInstancia = :codigoInstancia and f.empresa = :codigoEmpresa and (SIZE(f.digitais) = 0)";
			Query query = em.createQuery(jpql);

			query.setParameter("codigoInstancia", codigoInstancia);
			query.setParameter("codigoEmpresa", codigoEmpresa);

			funcionarios = (List<FuncionarioEjb>) query.getResultList();

			return funcionarios;
		} catch (Exception e) {
			log.error("Erro em FuncionarioDaoImpl.buscarSemDigitaisPorCodigoInstancia(): " + e.getMessage(), e);
			throw new ServiceException("erro_nao_foi_possivel_buscar");
		}

	}

	@POST
	@Path("buscar/nomeacao-exoneracao")
	public NomeacaoExoneracao buscarNomeacaoExoneracao(@FormParam("data") Date data, @FormParam("matricula") int matricula) throws ServiceException {

		NomeacaoExoneracao ne;

		try {
			Query query = em.createQuery("select ne from NomeacaoExoneracao ne where ne.matricula = " + matricula + " and ne.codigoEmpresa = 1 "
					+ " and :data between ne.dataInicial and isnull(ne.dataFinal,current_date())");
			query.setParameter("data", data, TemporalType.TIMESTAMP);
			ne = (NomeacaoExoneracao) query.getSingleResult();
			return ne;

		} catch (Exception e) {
			log.error("Erro em FuncionarioDaoImpl.buscarNomeacaoExoneracao(): " + e.getMessage(), e);
			throw new ServiceException("erro_buscar_nomeacao_exoneracao");
		}
	}

	@GET
	@Path("buscar/matriculas/{matriculas}")
	public List<FuncionarioEjb> buscarFuncionarios(@PathParam("matriculas") String matriculas) throws ServiceException {

		List<FuncionarioEjb> funcionarios;

		try {
			// as vezes não seta o parâmetro no in, por isso as matriculas estão
			// concatenadas
			Query query = em.createQuery("Select f from FuncionarioEjb f where f.matricula in (" + matriculas + ") and f.empresa = 1");
			funcionarios = query.getResultList();
			return funcionarios;
		} catch (DaoException ex) {
			throw new ServiceException(ex);
		}

	}
	
	@GET
	@Path("buscar-senha-intranet/matricula/{matricula}/empresa/{empresa}")
	public String buscarSenhaIntranet(@PathParam("matricula") int matricula, @PathParam("empresa") int empresa) {
		log.info("FuncionarioEjbServiceImpl.buscarSenhaIntranet(): buscando senha da intranet - matricula: " + matricula + ", empresa: " + empresa);

		String senha = "";
		
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
		
		try {
		    
		    conn = segurancaDS.getConnection();

            String sql = "select senha from pessoa where matricula = ? and empresa = ? and desativado = 0 and desatualizada = 0";

            pst = conn.prepareStatement(sql);
            pst.setInt(1, matricula);
            pst.setInt(2, empresa);
            
            rs = pst.executeQuery();

            if (rs.next()) {
                senha = rs.getString("senha");
            }

			if (senha == null) {
				senha = "";
				log.warn("FuncionarioEjbServiceImpl.buscarSenhaIntranet(): senha vazia... ");
			} else {
				log.info("FuncionarioEjbServiceImpl.buscarSenhaIntranet(): senha encontrada... ");
			}

		} catch (Exception e) {
			log.error("Erro ao buscar senha da intranet: " + e.getMessage(), e);
			senha = "";
		}

		return senha;
	}
	
    private List<FuncionarioTO> toTOs(List<FuncionarioEjb> listafnc) {
    	List<FuncionarioTO> retorno = new ArrayList<>();
    	
    	for (FuncionarioEjb f : listafnc) {
			retorno.add(f.toTO());
		}
    	
		return retorno;
	}	

}