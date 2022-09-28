package br.jus.tjms.pontoeletronico.util;

import br.jus.tjms.pontoeletronico.bean.PontoEjb;
import br.jus.tjms.pontoeletronico.factory.CalendarFactory;

import java.util.Calendar;
import java.util.Comparator;

@SuppressWarnings("unchecked")
public class ComparatorPonto implements Comparator {
	
	//@Override
	public int compare(Object ponto1, Object ponto2) {
		
		PontoEjb p1 = (PontoEjb) ponto1;
		PontoEjb p2 = (PontoEjb) ponto2;
		
		Calendar c1 = CalendarFactory.getCalendar();
		c1.setTime(p1.getDataHora());
		
		Calendar c2 = CalendarFactory.getCalendar();
		c2.setTime(p2.getDataHora());
		
		if (p1.getFuncionarioEjb().getId() < p2.getFuncionarioEjb().getId()) {
			return -1;
		} else if (p1.getFuncionarioEjb().getId() > p2.getFuncionarioEjb().getId()) {
			return 1;
		}

		if (p1.getFuncionarioEjb().getEmpresa() < p2.getFuncionarioEjb().getEmpresa()) {
			return -1;
		} else if (p1.getFuncionarioEjb().getEmpresa() > p2.getFuncionarioEjb().getEmpresa()) {
			return 1;
		}
			
		if (c1.get(Calendar.YEAR) < c2.get(Calendar.YEAR)) {
			return -1;
		} else if (c1.get(Calendar.YEAR) > c2.get(Calendar.YEAR)) {
			return 1;
		}

		if (c1.get(Calendar.MONTH) < c2.get(Calendar.MONTH)) {
			return -1;
		} else if (c1.get(Calendar.MONTH) > c2.get(Calendar.MONTH)) {
			return 1;
		}
		
		if (c1.get(Calendar.DAY_OF_MONTH) < c2.get(Calendar.DAY_OF_MONTH)) {
			return -1;
		} else if (c1.get(Calendar.DAY_OF_MONTH) > c2.get(Calendar.DAY_OF_MONTH)) {
			return 1;
		}

		
		if (c1.get(Calendar.HOUR_OF_DAY) < c2.get(Calendar.HOUR_OF_DAY)) {
			return -1;
		} else if (c1.get(Calendar.HOUR_OF_DAY) > c2.get(Calendar.HOUR_OF_DAY)) {
			return 1;
		}
		
		return 0;
	}

}
