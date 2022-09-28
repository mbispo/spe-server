package br.jus.tjms.pontoeletronico.service.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;

import br.jus.tjms.pontoeletronico.client.Constantes;
import br.jus.tjms.pontoeletronico.service.UpdateServiceRemoto;
import br.jus.tjms.pontoeletronico.to.UpdateInfoTO;
import br.jus.tjms.pontoeletronico.to.UpdateScriptAcaoTO;
import br.jus.tjms.pontoeletronico.to.UpdateScriptTO;
import br.jus.tjms.pontoeletronico.to.UpdateScriptTipoAcao;

/**
 * @autor: DDSI
 */
@Stateless(name = "updateService")
@Path("update")
@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
@LocalBean
public class UpdateServiceImpl implements UpdateServiceRemoto, Serializable {
    
	private static final long serialVersionUID = 1168399207910065671L;

	@PersistenceContext(unitName = "PontoEletronicoEmbDS")
    private EntityManager em;

    @Inject
    private Logger log;

    //--------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    // Script de atualização **da versão**
    UpdateScriptTO updateScript = new UpdateScriptTO(1l, Constantes.CLIENTE_VERSAOBD, Arrays.asList(
    	
    	new UpdateScriptAcaoTO(1l, 1, UpdateScriptTipoAcao.EXCLUIR_ARQUIVO, "organizacionalServices-2.0.61-client", "lib\\organizacionalServices-2.0.61-client.jar"),
    	new UpdateScriptAcaoTO(2l, 2, UpdateScriptTipoAcao.EXCLUIR_ARQUIVO, "tjms-base-3.0.0", "lib\\tjms-base-3.0.0.jar"),
    	new UpdateScriptAcaoTO(3l, 3, UpdateScriptTipoAcao.EXCLUIR_ARQUIVO, "gson-2.8.5", "lib\\gson-2.8.5.jar"),
    	new UpdateScriptAcaoTO(4l, 4, UpdateScriptTipoAcao.EXCLUIR_ARQUIVO, "teste", "lib\\teste.txt"),
    	new UpdateScriptAcaoTO(5l, 5, UpdateScriptTipoAcao.EXCLUIR_ARQUIVO, "teste2", "teste2.txt")
//    	
//    	new UpdateScriptAcaoTO(5l, 5, UpdateScriptTipoAcao.EXECUTAR_SQL, "sql1", "update Digital set enviado = false"),
//    	
//    	new UpdateScriptAcaoTO(6l, 6, UpdateScriptTipoAcao.EXECUTAR_ARQUIVO, "CALC", "fechaJanela.exe")
    	
    ));
    
    UpdateInfoTO updateInfo = new UpdateInfoTO(1l, Constantes.CLIENTE_VERSAO, new Date(), "spe-server versão 2.1.11", "update.zip", null, updateScript);
    //-------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    @Override
    @GET
	@Path("versao")
	public String getVersao() {
		return updateInfo.getVersao();
	}

	@Override
	@GET
	@Path("novidades")
	public String getNovidades() {
		return updateInfo.getNovidades();
	}

	@Override
	@GET
	@Path("url")
	public String getUpdateURL() {
		return updateInfo.getUpdateURL();
	}

	@Override
	@GET
	@Path("updatedata")
	public byte[] getUpdateData() {
		// TODO ler update.zip de /update/update.zip
		return null;
	}

	@Override
	@GET
	@Path("updateinfo")
	public UpdateInfoTO getUpdateInfo() {
		return updateInfo;
	}

}