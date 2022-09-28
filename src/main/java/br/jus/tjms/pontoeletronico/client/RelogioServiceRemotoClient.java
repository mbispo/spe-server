package br.jus.tjms.pontoeletronico.client;

import java.io.Serializable;
import java.util.Date;

import com.google.gson.Gson;

import br.jus.tjms.comuns.context.Config;
import br.jus.tjms.comuns.exceptions.ServiceException;
import br.jus.tjms.pontoeletronico.rest.ResteasyUtil;
import br.jus.tjms.pontoeletronico.service.RelogioServiceRemoto;

public class RelogioServiceRemotoClient implements RelogioServiceRemoto, Serializable {

	private static final long serialVersionUID = 1L;

	private String urlMain;	

	private Config config;

	public static final String URL_PRODUCAO = "http://spe-server.tjms.jus.br:8080/spe-server/rest/relogio";
	public static final String URL_HOMOLOGACAO = "http://sistemashml.tjms.jus.br:8080/spe-server/rest/relogio";
	public static final String URL_LOCAL = "http://localhost:8080/spe-server/rest/relogio";

	public RelogioServiceRemotoClient(Config config, String urlMain) {
		if (config == null)
			throw new ServiceException("Atributo config é obrigatório!");
		this.config = config;
		this.urlMain = urlMain;
		if (urlMain == null || urlMain.isEmpty()) {
			this.urlMain = config.isAmbienteProducao() ? URL_PRODUCAO : URL_HOMOLOGACAO;
		}
	}

	@Override
	public Date getDataHora() {

		String path = "/hora";
		String url = urlMain + path;

		String response = ResteasyUtil.get(url, null);

		Gson gson = ResteasyUtil.createGson();
		Date obj = gson.fromJson(response, Date.class);

		return obj;
	}

	@Override
	public String getTimezone() {
		String path = "/tz";
		String url = urlMain + path;

		String response = ResteasyUtil.get(url, null);
		System.out.println("\n\n\n");
		System.out.println("/tz, timezone retornado para o cliente: ");
		System.out.println(this.getClass().getName()+".getTimezone: response = "+response);
		System.out.println("\n\n\n");
		
		return response;
	}

	public Config getConfig() {
		return config;
	}

}
