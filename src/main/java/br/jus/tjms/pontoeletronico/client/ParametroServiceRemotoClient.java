package br.jus.tjms.pontoeletronico.client;

import com.google.gson.Gson;

import br.jus.tjms.comuns.context.Config;
import br.jus.tjms.comuns.exceptions.ServiceException;
import br.jus.tjms.pontoeletronico.bean.ParametroEjb;
import br.jus.tjms.pontoeletronico.rest.ResteasyUtil;
import br.jus.tjms.pontoeletronico.service.ParametroServiceRemoto;

public class ParametroServiceRemotoClient implements ParametroServiceRemoto {

	private String urlMain;

	private Config config;

	public static final String URL_PRODUCAO = "http://spe-server.tjms.jus.br:8080/spe-server/rest/parametro";
	public static final String URL_HOMOLOGACAO = "http://sistemashml.tjms.jus.br:8080/spe-server/rest/parametro";
	public static final String URL_LOCAL = "http://localhost:8080/spe-server/rest/parametro";

	public ParametroServiceRemotoClient(Config config, String urlMain) {
		if (config == null)
			throw new ServiceException("Atributo config é obrigatório!");
		this.config = config;
		this.urlMain = urlMain;
		if (urlMain == null || urlMain.isEmpty()) {
			this.urlMain = config.isAmbienteProducao() ? URL_PRODUCAO : URL_HOMOLOGACAO;
		}
	}

	@Override
	public ParametroEjb getParametros() throws ServiceException {
		String path = "/obter/";
		String url = urlMain + path;
		String response = ResteasyUtil.get(url, null);
		Gson gson = ResteasyUtil.createGson();
		ParametroEjb obj = gson.fromJson(response, ParametroEjb.class);

		return obj;
	}

}