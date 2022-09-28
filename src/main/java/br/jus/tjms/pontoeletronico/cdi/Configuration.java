package br.jus.tjms.pontoeletronico.cdi;


import javax.inject.Named;

import br.jus.tjms.comuns.context.AbstractConfig;
import br.jus.tjms.comuns2.resource.ResourceUtils;

@Named
public class Configuration extends AbstractConfig{

	private static final long serialVersionUID = -3537696652105189582L;
	
	public static final String PROPERTIES_NAME = "tjms";

	@Override
	public String getPerfilPadraoSistema() {
		return ResourceUtils.getString(PROPERTIES_NAME, "sistema.perfil");
		
	}
	
	public String getVersao() {
		return ResourceUtils.getString(PROPERTIES_NAME, "sistema.versao");
	}

	@Override
	public String getNomeSistema() {
		return ResourceUtils.getString(PROPERTIES_NAME, "sistema.nome");
	}


}