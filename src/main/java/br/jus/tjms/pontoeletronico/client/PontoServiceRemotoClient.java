package br.jus.tjms.pontoeletronico.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import br.jus.tjms.comuns.context.Config;
import br.jus.tjms.comuns.exceptions.ServiceException;
import br.jus.tjms.pontoeletronico.bean.PontoEjb;
import br.jus.tjms.pontoeletronico.bean.PontoMes;
import br.jus.tjms.pontoeletronico.rest.ResteasyUtil;
import br.jus.tjms.pontoeletronico.service.PontoServiceRemoto;

public class PontoServiceRemotoClient implements PontoServiceRemoto {

	private String urlMain;

	private Config config;

	public static final String URL_PRODUCAO = "http://spe-server.tjms.jus.br:8080/spe-server/rest/ponto";
	public static final String URL_HOMOLOGACAO = "http://sistemashml.tjms.jus.br:8080/spe-server/rest/ponto";
	public static final String URL_LOCAL = "http://localhost:8080/spe-server/rest/ponto";

	public PontoServiceRemotoClient(Config config, String urlMain) {
		if (config == null)
			throw new ServiceException("Atributo config é obrigatório!");
		this.config = config;
		this.urlMain = urlMain;
		if (urlMain == null || urlMain.isEmpty()) {
			this.urlMain = config.isAmbienteProducao() ? URL_PRODUCAO : URL_HOMOLOGACAO;
		}
	}

	@Override
	public PontoMes buscarPontoMes(int empresa, int matricula, int mes, int ano) throws ServiceException {
		String path = "/buscar/empresa/" + empresa + "/matricula/" + matricula + "/mes/" + mes + "/ano/" + ano;
		String url = urlMain + path;
		String response = ResteasyUtil.get(url, null);
		Gson gson = ResteasyUtil.createGson();
		PontoMes obj = gson.fromJson(response, PontoMes.class);

		return obj;
	}

	@Override
	public boolean processarPonto(List<PontoEjb> pontos) {

		String path = "/processar";
		String url = urlMain + path;

		String response = ResteasyUtil.sendObjects(url, pontos);
		Gson gson = ResteasyUtil.createGson();

		boolean obj = gson.fromJson(response, boolean.class);

		return obj;
	}

	@Override
	public void salvarPontoMes(PontoMes ponto) throws ServiceException {
		String path = "/salvar";
		String url = urlMain + path;

		Map<String, Object> parametros = new HashMap();
		parametros.put("pontoMes", ponto);

		ResteasyUtil.post(url, parametros);

	}

}
