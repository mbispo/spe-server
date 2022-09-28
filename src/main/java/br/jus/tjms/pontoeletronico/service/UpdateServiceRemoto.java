package br.jus.tjms.pontoeletronico.service;

import br.jus.tjms.pontoeletronico.to.UpdateInfoTO;

public interface UpdateServiceRemoto {

	// executa rest getVersao, getNovidades, getURL, etc, obtendo versão;
	public String getVersao();
	
	public String getNovidades();
	
	public String getUpdateURL();
	
	public byte[] getUpdateData();
	
	public UpdateInfoTO getUpdateInfo();
	
}
