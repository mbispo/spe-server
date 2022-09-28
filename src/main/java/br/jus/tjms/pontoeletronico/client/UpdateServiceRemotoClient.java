package br.jus.tjms.pontoeletronico.client;

import com.google.gson.Gson;

import br.jus.tjms.comuns.context.Config;
import br.jus.tjms.comuns.exceptions.ServiceException;
import br.jus.tjms.pontoeletronico.rest.ResteasyUtil;
import br.jus.tjms.pontoeletronico.service.UpdateServiceRemoto;
import br.jus.tjms.pontoeletronico.to.UpdateInfoTO;

public class UpdateServiceRemotoClient implements UpdateServiceRemoto {

	private String urlMain;

	private Config config;

	public static final String URL_PRODUCAO = "http://spe-server.tjms.jus.br:8080/spe-server/rest/update";
	public static final String URL_HOMOLOGACAO = "http://sistemashml.tjms.jus.br:8080/spe-server/rest/update";
	public static final String URL_LOCAL = "http://localhost:8080/spe-server/rest/update";

	public UpdateServiceRemotoClient(Config config, String urlMain) {
		if (config == null)
			throw new ServiceException("Atributo config é obrigatório!");
		this.config = config;
		this.urlMain = urlMain;
		if (urlMain == null || urlMain.isEmpty()) {
			this.urlMain = config.isAmbienteProducao() ? URL_PRODUCAO : URL_HOMOLOGACAO;
		}
	}
	
	@Override
	public String getVersao() {
		try {
			String path = "/versao/";
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
	public String getNovidades() {
		try {
			String path = "/novidades/";
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
	public String getUpdateURL() {
		try {
			String path = "/url/";
			String url = urlMain + path;
			String response = ResteasyUtil.get(url, null);
			Gson gson = ResteasyUtil.createGson();
			String obj = gson.fromJson(response, String.class);
			
			String updateUrl = urlMain.replaceAll("rest/", "") + "/" + obj;
			
			return updateUrl;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public byte[] getUpdateData() {
		return getUpdateInfo().getUpdateData();
	}

	@Override
	public UpdateInfoTO getUpdateInfo() {
		try {
			String path = "/updateinfo/";
			String url = urlMain + path;
			String response = ResteasyUtil.get(url, null);
			
			Gson gson = ResteasyUtil.createGson();
			UpdateInfoTO obj = gson.fromJson(response, UpdateInfoTO.class);

			return obj;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public static void main(String[] args) {
		String urlMain = "http://localhost:8080/spe-server/rest/update";
		String obj = "update.zip";
		String updateUrl = urlMain.replaceAll("rest/", "") + "/" + obj;
		System.out.println(updateUrl);
	}
	
}