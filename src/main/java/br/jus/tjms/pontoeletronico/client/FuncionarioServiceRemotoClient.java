package br.jus.tjms.pontoeletronico.client;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.NotImplementedException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import br.jus.tjms.comuns.context.Config;
import br.jus.tjms.comuns.exceptions.ServiceException;
import br.jus.tjms.pontoeletronico.bean.FuncionarioEjb;
import br.jus.tjms.pontoeletronico.bean.NomeacaoExoneracao;
import br.jus.tjms.pontoeletronico.rest.ResteasyUtil;
import br.jus.tjms.pontoeletronico.service.FuncionarioServiceRemoto;
import br.jus.tjms.pontoeletronico.to.FuncionarioTO;

public class FuncionarioServiceRemotoClient implements FuncionarioServiceRemoto {

	private String urlMain;

	private Config config;

	public static final String URL_PRODUCAO = "http://spe-server.tjms.jus.br:8080/spe-server/rest/funcionario";
	public static final String URL_HOMOLOGACAO = "http://sistemashml.tjms.jus.br:8080/spe-server/rest/funcionario";
	public static final String URL_LOCAL = "http://localhost:8080/spe-server/rest/funcionario";

	public FuncionarioServiceRemotoClient(Config config, String urlMain) {
		if (config == null)
			throw new ServiceException("Atributo config é obrigatório!");
		this.config = config;
		this.urlMain = urlMain;
		if (urlMain == null || urlMain.isEmpty()) {
			this.urlMain = config.isAmbienteProducao() ? URL_PRODUCAO : URL_HOMOLOGACAO;
		}
	}

	@Override
	public FuncionarioTO buscarPorId(int id) throws ServiceException {
		try {
			String path = "/buscar/" + id;
			String url = urlMain + path;
			String response = ResteasyUtil.get(url, null);
			
			Gson gson = ResteasyUtil.createGson();
			FuncionarioTO obj = gson.fromJson(response, FuncionarioTO.class);

			return obj;
		} catch (Exception e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public FuncionarioTO buscarPorId(int matricula, int empresa) throws ServiceException {
		try {
			String path = "/buscar/matricula/" + matricula + "/empresa/" + empresa;
			String url = urlMain + path;
			String response = ResteasyUtil.get(url, null);
			Gson gson = ResteasyUtil.createGson();
			FuncionarioTO obj = gson.fromJson(response, FuncionarioTO.class);
			return obj;
		} catch (Exception e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public String buscarSenhaIntranet(int matricula, int codigoEmpresa) throws ServiceException {
		try {
			// @Path("buscar-senha-intranet/matricula/{matricula}/empresa/{empresa}")
			String path = "/buscar-senha-intranet/matricula/" + matricula + "/empresa/" + codigoEmpresa;
			String url = urlMain + path;
			String response = ResteasyUtil.get(url, null);
			Gson gson = ResteasyUtil.createGson();
			String obj = gson.fromJson(response, String.class);
			return obj;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<FuncionarioTO> buscarPorNome(String nome) throws ServiceException {
		try {
			String path = "/buscar/nome/" + nome;
			String url = urlMain + path;
			String response = ResteasyUtil.get(url, null);
			Gson gson = ResteasyUtil.createGson();
			Type listType = new TypeToken<List<FuncionarioTO>>() {
			}.getType();
			List<FuncionarioTO> lista = gson.fromJson(response, listType);

			return lista;

		} catch (Exception e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public List<FuncionarioEjb> buscarPorEmpresa(int codigoEmpresa) throws ServiceException {
		try {
			String path = "/buscar/empresa/" + codigoEmpresa;
			String url = urlMain + path;
			String response = ResteasyUtil.get(url, null);
			Gson gson = ResteasyUtil.createGson();
			Type listType = new TypeToken<List<FuncionarioEjb>>() {
			}.getType();
			List<FuncionarioEjb> lista = gson.fromJson(response, listType);

			return lista;
		} catch (Exception e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public List<FuncionarioEjb> buscarPorCodigoComarcaSecretaria(int codigoComarcaSecretaria, int codigoEmpresa) throws ServiceException {
		try {
			String path = "/buscar/comarca-secretaria/" + codigoComarcaSecretaria + "/empresa/" + codigoEmpresa;
			String url = urlMain + path;
			String response = ResteasyUtil.get(url, null);
			Gson gson = ResteasyUtil.createGson();
			Type listType = new TypeToken<List<FuncionarioEjb>>() {
			}.getType();
			List<FuncionarioEjb> lista = gson.fromJson(response, listType);

			return lista;

		} catch (Exception e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public List<FuncionarioEjb> buscarComDigitaisPorCodigoComarcaSecretaria(int codigoComarcaSecretaria, int codigoEmpresa) throws ServiceException {
		try {
			String path = "/buscar-com-digitais/comarca-secretaria/" + codigoComarcaSecretaria + "/empresa/" + codigoEmpresa;
			String url = urlMain + path;
			String response = ResteasyUtil.get(url, null);
			Gson gson = ResteasyUtil.createGson();
			Type listType = new TypeToken<List<FuncionarioEjb>>() {
			}.getType();
			List<FuncionarioEjb> lista = gson.fromJson(response, listType);

			return lista;
		} catch (Exception e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public List<FuncionarioEjb> buscarSemDigitaisPorCodigoComarcaSecretaria(int codigoComarcaSecretaria, int codigoEmpresa) throws ServiceException {
		try {
			String path = "/buscar-sem-digitais/comarca-secretaria/" + codigoComarcaSecretaria + "/empresa/" + codigoEmpresa;
			String url = urlMain + path;
			String response = ResteasyUtil.get(url, null);
			Gson gson = ResteasyUtil.createGson();
			Type listType = new TypeToken<List<FuncionarioEjb>>() {
			}.getType();
			List<FuncionarioEjb> lista = gson.fromJson(response, listType);
			return lista;
		} catch (Exception e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public List<FuncionarioEjb> buscarSemDigitaisPorCodigoInstancia(int codigoInstancia, int codigoEmpresa) throws ServiceException {
		try {
			String path = "/buscar-sem-digitais/instancia/" + codigoInstancia + "/empresa/" + codigoEmpresa;
			String url = urlMain + path;
			String response = ResteasyUtil.get(url, null);
			Gson gson = ResteasyUtil.createGson();
			Type listType = new TypeToken<List<FuncionarioEjb>>() {
			}.getType();
			List<FuncionarioEjb> lista = gson.fromJson(response, listType);
			return lista;
		} catch (Exception e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public NomeacaoExoneracao buscarNomeacaoExoneracao(Date data, int matricula) throws ServiceException {
		try {
			String path = "/buscar/nomeacao-exoneracao";
			String url = urlMain + path;

			Map<String, Object> parametros = new HashMap<>();
			parametros.put("data", data);
			parametros.put("matricula", matricula);

			String response = ResteasyUtil.post(url, parametros);

			Gson gson = ResteasyUtil.createGson();
			NomeacaoExoneracao obj = gson.fromJson(response, NomeacaoExoneracao.class);
			return obj;

		} catch (Exception e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public List<FuncionarioEjb> buscarFuncionarios(String matriculas) throws ServiceException {
		try {
			String path = "/buscar/matriculas/" + matriculas;
			String url = urlMain + path;
			String response = ResteasyUtil.get(url, null);
			Gson gson = ResteasyUtil.createGson();
			Type listType = new TypeToken<List<FuncionarioEjb>>() {
			}.getType();
			List<FuncionarioEjb> lista = gson.fromJson(response, listType);
			return lista;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void salvar(FuncionarioEjb funcionario) throws ServiceException {
		throw new NotImplementedException();
	}

	@Override
	public void atualizar(FuncionarioEjb funcionario) throws ServiceException {
		throw new NotImplementedException();		
	}

	@Override
	public void refresh(FuncionarioEjb funcionario) throws ServiceException {
		throw new NotImplementedException();		
	}

	@Override
	public List<FuncionarioEjb> buscarTodos() throws ServiceException {
		throw new NotImplementedException();
	}

	@Override
	public void removerDigitais(FuncionarioEjb funcionarioejbtemp) throws ServiceException {
		throw new NotImplementedException();
	}

}