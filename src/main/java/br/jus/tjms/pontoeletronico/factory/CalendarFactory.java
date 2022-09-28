package br.jus.tjms.pontoeletronico.factory;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import br.jus.tjms.pontoeletronico.client.Constantes;

public class CalendarFactory {

	public static Calendar getCalendar() {

		TimeZone tz = TimeZone.getTimeZone(Constantes.TIME_ZONE);
		TimeZone.setDefault(tz);

		return GregorianCalendar.getInstance(tz);

	}

}
