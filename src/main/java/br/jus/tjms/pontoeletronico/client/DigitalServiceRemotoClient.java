package br.jus.tjms.pontoeletronico.client;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.WebApplicationException;

import org.apache.commons.lang.NotImplementedException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import br.jus.tjms.comuns.context.Config;
import br.jus.tjms.comuns.exceptions.ServiceException;
import br.jus.tjms.pontoeletronico.to.DigitalTO;
import br.jus.tjms.pontoeletronico.to.FuncionarioTO;
import br.jus.tjms.pontoeletronico.bean.DigitalEjb;
import br.jus.tjms.pontoeletronico.rest.ResteasyUtil;
import br.jus.tjms.pontoeletronico.service.DigitalServiceRemoto;

public class DigitalServiceRemotoClient implements DigitalServiceRemoto {

	private String urlMain;

	private Config config;

	public static final String URL_PRODUCAO = "http://spe-server.tjms.jus.br:8080/spe-server/rest/digital";
	public static final String URL_HOMOLOGACAO = "http://sistemashml.tjms.jus.br:8080/spe-server/rest/digital";
	public static final String URL_LOCAL = "http://localhost:8080/spe-server/rest/digital";

	public DigitalServiceRemotoClient(Config config, String urlMain) {
		if (config == null)
			throw new ServiceException("Atributo config é obrigatório!");
		this.config = config;
		this.urlMain = urlMain;
		if (urlMain == null || urlMain.isEmpty()) {
			this.urlMain = config.isAmbienteProducao() ? URL_PRODUCAO : URL_HOMOLOGACAO;
		}
	}

	public Config getConfig() {
		return config;
	}

	@Override
	public List<FuncionarioTO> buscarFuncionariosComNovasDigitais(int ndias) throws WebApplicationException {
		String path = "/buscar/funcionarios-com-novas-digitais/dias/" + ndias;
		String url = urlMain + path;
		String response = ResteasyUtil.get(url, null);
		Gson gson = ResteasyUtil.createGson();
		Type listType = new TypeToken<List<FuncionarioTO>>() {
		}.getType();
		List<FuncionarioTO> lista = gson.fromJson(response, listType);

		return lista;
	}

	@Override
	public List<FuncionarioTO> buscarFuncionariosComNovasDigitaisPorLotacao(int ndias, int codigoComarcaSecretaria, int codigoEmpresa) throws WebApplicationException {
		String path = "/buscar/funcionarios-novas-digitais-por-lotacao/dias/" + ndias + "/comarca-secretaria/" + codigoComarcaSecretaria + "/empresa/" + codigoEmpresa;
		String url = urlMain + path;
		String response = ResteasyUtil.get(url, null);
		Gson gson = ResteasyUtil.createGson();
		Type listType = new TypeToken<List<FuncionarioTO>>() {
		}.getType();
		List<FuncionarioTO> lista = gson.fromJson(response, listType);

		return lista;
	}

	@Override
	public List<FuncionarioTO> buscarFuncionariosComNovasDigitaisPorInstancia(int ndias, int codigoInstancia, int codigoEmpresa) throws WebApplicationException {
		String path = "/buscar/funcionarios-novas-digitais-por-instancia/dias/" + ndias + "/instancia/" + codigoInstancia + "/empresa/" + codigoEmpresa;
		String url = urlMain + path;
		String response = ResteasyUtil.get(url, null);
		Gson gson = ResteasyUtil.createGson();
		Type listType = new TypeToken<List<FuncionarioTO>>() {
		}.getType();
		List<FuncionarioTO> lista = gson.fromJson(response, listType);

		return lista;
	}

	@Override
	public List<FuncionarioTO> buscarFuncionariosSemDigitaisPorLotacao(int codigoComarcaSecretaria, int codigoEmpresa) throws WebApplicationException {

		String path = "/buscar/funcionarios-sem-digitais-por-lotacao/comarca/" + codigoComarcaSecretaria + "/empresa/" + codigoEmpresa;
		String url = urlMain + path;
		String response = ResteasyUtil.get(url, null);
		Gson gson = ResteasyUtil.createGson();
		Type listType = new TypeToken<List<FuncionarioTO>>() {
		}.getType();
		List<FuncionarioTO> lista = gson.fromJson(response, listType);

		return lista;
	}

	@Override
	public List<FuncionarioTO> buscarFuncionariosSemDigitaisPorInstancia(int codigoInstancia, int codigoEmpresa) throws WebApplicationException {
		String path = "/buscar/funcionarios-sem-digitais-por-instancia/instancia/" + codigoInstancia + "/empresa/" + codigoEmpresa;
		String url = urlMain + path;
		String response = ResteasyUtil.get(url, null);
		Gson gson = ResteasyUtil.createGson();
		Type listType = new TypeToken<List<FuncionarioTO>>() {
		}.getType();
		List<FuncionarioTO> lista = gson.fromJson(response, listType);

		return lista;
	}

	@Override
	public boolean gravar(List<DigitalTO> digitais) throws WebApplicationException {
		String path = "/gravar";
		String url = urlMain + path;

		String response = ResteasyUtil.sendObjects(url, digitais);
		Gson gson = ResteasyUtil.createGson();

		boolean obj = gson.fromJson(response, boolean.class);

		return obj;

	}

	@Override
	public boolean gravarPorFuncionario(List<FuncionarioTO> funcionarios) throws WebApplicationException {
		String path = "/gravar-funcionario";
		String url = urlMain + path;

		String response = ResteasyUtil.sendObjects(url, funcionarios);
		Gson gson = ResteasyUtil.createGson();

		boolean obj = gson.fromJson(response, boolean.class);

		return obj;
	}

	@Override
	public boolean gravar(DigitalTO digital) {
		String path = "/gravar-digital";
		String url = urlMain + path;

		Map<String, Object> parametros = new HashMap();
		parametros.put("digital", digital);

		String response = ResteasyUtil.post(url, parametros);
		Gson gson = ResteasyUtil.createGson();

		boolean obj = gson.fromJson(response, boolean.class);

		return obj;
	}

	@Override
	public void salvar(DigitalTO digital) throws ServiceException {
		String path = "/salvar";
		String url = urlMain + path;

		Map<String, Object> parametros = new HashMap();
		parametros.put("digital", digital);

		ResteasyUtil.post(url, parametros);

	}

	@Override
	public DigitalTO buscarPorId(int id) throws ServiceException {
		String path = "buscar/digital-por-id/" + id;
		String url = urlMain + path;
		String response = ResteasyUtil.get(url, null);
		Gson gson = ResteasyUtil.createGson();
		DigitalTO obj = gson.fromJson(response, DigitalTO.class);

		return obj;
	}

	@Override
	public List<DigitalTO> buscarDigitaisPorFuncionario(FuncionarioTO funcionarioTO) {
		String path = "/buscar-digitais-por-funcionario";
		String url = urlMain + path;

		Map<String, Object> parametros = new HashMap();
		parametros.put("funcionarioTO", funcionarioTO);

		String response = ResteasyUtil.post(url, parametros);
		Gson gson = ResteasyUtil.createGson();
		Type listType = new TypeToken<List<DigitalTO>>() {
		}.getType();
		List<DigitalTO> lista = gson.fromJson(response, listType);

		return lista;
	}
	
	@Override
	public List<DigitalEjb> buscarTodos() throws ServiceException {
		throw new NotImplementedException();
	}

	@Override
	public void remover(DigitalEjb digital) throws ServiceException {
		throw new NotImplementedException();

	}

	@Override
	public List<DigitalEjb> buscarNovas(int ndias) throws ServiceException {
		throw new NotImplementedException();
	}

	@Override
	public List<DigitalEjb> buscarNovasPorLotacao(int ndias, int codigoComarcaSecretaria, int codigoEmpresa) throws ServiceException {
		throw new NotImplementedException();
	}

	@Override
	public List<DigitalEjb> buscarNovasPorInstancia(int ndias, int codigoInstancia, int codigoEmpresa) throws ServiceException {
		throw new NotImplementedException();
	}
	

}