package br.jus.tjms.pontoeletronico.client;

import java.util.List;

import com.google.gson.Gson;

import br.jus.tjms.comuns.context.Config;
import br.jus.tjms.comuns.exceptions.ServiceException;
import br.jus.tjms.pontoeletronico.bean.LogEjb;
import br.jus.tjms.pontoeletronico.rest.ResteasyUtil;
import br.jus.tjms.pontoeletronico.service.LogServiceRemoto;

public class LogServiceRemotoClient implements LogServiceRemoto {

	public static final String URL_PRODUCAO = "http://spe-server.tjms.jus.br:8080/spe-server/rest/log";
	public static final String URL_HOMOLOGACAO = "http://sistemashml.tjms.jus.br:8080/spe-server/rest/log";
	public static final String URL_LOCAL = "http://localhost:8080/spe-server/rest/log";

	private String urlMain;

	private Config config;

	public LogServiceRemotoClient(Config config, String urlMain) {
		if (config == null)
			throw new ServiceException("Atributo config é obrigatório!");
		this.setConfig(config);
		this.urlMain = urlMain;
		if (urlMain == null || urlMain.isEmpty()) {
			this.urlMain = config.isAmbienteProducao() ? URL_PRODUCAO : URL_HOMOLOGACAO;
		}
	}

	@Override
	public boolean gravar(List<LogEjb> logs) {
		String path = "/gravar-logs";
		String url = urlMain + path;

		String response = ResteasyUtil.sendObjects(url, logs);
		Gson gson = ResteasyUtil.createGson();

		boolean obj = gson.fromJson(response, boolean.class);

		return obj;
	}

	@Override
	public boolean gravar(LogEjb log) {
		String path = "/gravar";
		String url = urlMain + path;

		String response = ResteasyUtil.send(url, log);
		Gson gson = ResteasyUtil.createGson();

		boolean obj = gson.fromJson(response, boolean.class);

		return obj;
	}

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

}
