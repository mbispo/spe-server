package br.jus.tjms.pontoeletronico.service;

import java.util.Date;

public interface RelogioServiceRemoto {
	
	Date getDataHora();
	
	String getTimezone();
}
