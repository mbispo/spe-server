package br.jus.tjms.pontoeletronico.cdi;

import java.io.Serializable;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.jus.tjms.comuns.context.AbstractApplicationContext;

@Named("appContext")
@SessionScoped
public class ApplicationContext extends AbstractApplicationContext implements Serializable {

	private static final long serialVersionUID = 418064929334523741L;

	@Inject
	private Configuration config;

	@PostConstruct
	public void init() {
		Locale.setDefault(new Locale("pt", "BR"));
	}

	public String getVersao() {
		return config.getVersao();
	}

}
