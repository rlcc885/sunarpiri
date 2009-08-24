package gob.pe.sunarp.extranet.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FechaFormatter {
	private static SimpleDateFormat formatterFechaHora = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private static SimpleDateFormat formatterFechaHoraSegundos = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private static SimpleDateFormat parserDbo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	public static String deDBOaFechaHoraSegundosWeb(String dbo) throws java.text.ParseException {
		Date date = parserDbo.parse(dbo);
		return formatterFechaHoraSegundos.format(date);
	}
	
	public static String deDBOaFechaHoraWeb(String dbo) throws java.text.ParseException 
	{
		if (dbo==null)
			return "";
		if (dbo.trim().length()==0)
			return "";
		Date date = parserDbo.parse(dbo);
		return formatterFechaHora.format(date);
	}
	
	public static String deDateAFechaHoraSegundosWeb(Date date) {
		return formatterFechaHoraSegundos.format(date);
	}
	public static String deDateAFechaHoraWeb(Date date) {
		return formatterFechaHora.format(date);
	}
}

