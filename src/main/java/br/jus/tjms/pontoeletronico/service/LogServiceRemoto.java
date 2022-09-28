package br.jus.tjms.pontoeletronico.service;

import java.util.List;

import br.jus.tjms.pontoeletronico.bean.LogEjb;

public interface LogServiceRemoto {

    boolean gravar(List<LogEjb> logs);

    boolean gravar(LogEjb log);

}