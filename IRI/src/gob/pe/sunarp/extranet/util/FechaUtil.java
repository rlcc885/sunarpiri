package gob.pe.sunarp.extranet.util;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FechaUtil
{


/**
  * This Method receives two parameters:
  *
  * String origin = String Date in the format "dd/mm/yyyy"
  * int    days   = number of days to add or substract to the String Date
  *
  * And this method returns:
  *
  * A String Date in the format "dd/mm/yyyy" with the resultant date
  *
  * NOTE: if you want to "subtract" days you 
  *       should provide a negative integer for the parameter "days"
 */

public static String add(String origin, int days) throws Throwable
{
	// extract the year, month and day
	String sYear = origin.substring(6, 10);
	String sMonth = origin.substring(3, 5);
	String sDay = origin.substring(0, 2);

	int iYear = Integer.parseInt(sYear);
	int iMonth = Integer.parseInt(sMonth);
	int iDay = Integer.parseInt(sDay);

	iMonth--;

	// Convert the String Date to a Calendar Object
	java.util.Calendar cal = java.util.Calendar.getInstance();
	cal.set(iYear, iMonth, iDay);

	// Add (or substract) the given days
	cal.add(java.util.Calendar.DATE, days);

	// Convert the Calendar Object to String
	StringBuffer sb = new StringBuffer();

	// Number Formaters
	java.text.DecimalFormat dformat = new java.text.DecimalFormat("00");
	java.text.DecimalFormat yformat = new java.text.DecimalFormat("0000");

	sb.append(dformat.format(cal.get(java.util.Calendar.DATE)));
	sb.append("/");
	sb.append(dformat.format(cal.get(java.util.Calendar.MONTH) + 1));
	//    sb.append(dformat.format(cal.get(java.util.Calendar.MONTH)));
	sb.append("/");
	sb.append(yformat.format(cal.get(java.util.Calendar.YEAR)));

	return sb.toString();
}
/**
  Internal Method for class Tempo
  Converts a Calendar Object to a String

  values for input parameter "format":
	0 = return a String in the format 'dd/mm/yyyy'
	1 = return a String in the format 'dd/mm/yyyy hh:mm:ss' (24 hours)
  */

private static String calendarToString(java.util.Calendar c, int format)
{
	// Number Formaters
	java.text.DecimalFormat dformat = new java.text.DecimalFormat("00");
	java.text.DecimalFormat yformat = new java.text.DecimalFormat("0000");

	String day   = dformat.format(c.get(java.util.Calendar.DATE));
	//Remember: in Java, the first month is 0 (January)
	// that is why we must add 1 to the Calender.MONTH result
	String month = dformat.format(c.get(java.util.Calendar.MONTH) + 1);
	String year  = yformat.format(c.get(java.util.Calendar.YEAR));

	StringBuffer sb = new StringBuffer();
	
	if (format == 0)
		sb.append(day).append("/").append(month).append("/").append(year);

	if (format == 1)
		{
		sb.append(day).append("/").append(month).append("/").append(year);
		//add the time
		sb.append(" ");

		sb.append(dformat.format(c.get(java.util.Calendar.HOUR_OF_DAY)));
		sb.append(":");
		sb.append(dformat.format(c.get(java.util.Calendar.MINUTE)));
		sb.append(":");
		sb.append(dformat.format(c.get(java.util.Calendar.SECOND)));
	}
    
	if (format == 10)
		sb.append(year).append(month).append(day);

	return sb.toString();
}
/**
Compares to Dates (in String format "dd/mm/yyyy")
and returns an int with the possibles values:

  -3 : DateA < DateB
   0 : DateA = DateB
  +2 : DateA > DateB

*/

public static int compare(String DateA, String DateB)
{
	//Check if they are equal
	if (DateA.compareTo(DateB) == 0)
		{
		// DateA equals DateB
		return 0;
	}

	//Convert both Strings to Calendar Objects

	// extract the year, month and day
	String sYear = DateA.substring(6, 10);
	String sMonth = DateA.substring(3, 5);
	String sDay = DateA.substring(0, 2);

	int iYear = Integer.parseInt(sYear);
	int iMonth = Integer.parseInt(sMonth);
	int iDay = Integer.parseInt(sDay);

	iMonth--;
	java.util.Calendar calendarA = java.util.Calendar.getInstance();
	calendarA.set(iYear, iMonth, iDay);

	sYear = DateB.substring(6, 10);
	sMonth = DateB.substring(3, 5);
	sDay = DateB.substring(0, 2);

	iYear = Integer.parseInt(sYear);
	iMonth = Integer.parseInt(sMonth);
	iDay = Integer.parseInt(sDay);
	iMonth--;
	java.util.Calendar calendarB = java.util.Calendar.getInstance();
	calendarB.set(iYear, iMonth, iDay);

	// compare
	boolean result = calendarA.after(calendarB);

	if (result == true)
		{
		// DateA is greater than DateB
		return +2;
	}
	else
		{
		// DateB is greater than DateA
		return -3;
	}
}

/**
 * Convert TimeStamp to a String in the format 'dd/mm/yyyy hh:mm:ss' (24 hour) 2 Oracle
 */
public static String dateTimeToStringToOracle(java.sql.Timestamp d)
{
	return stringTimeToOracleString(dateTimeToString(d));
}
/**
 * Convert TimeStamp to a String in the format 'dd/mm/yyyy hh:mm:ss' (24 hour)
 */
public static String dateTimeToString(java.sql.Timestamp d)
{
	if (d == null)
		return "00/00/0000 00:00:00";
        
	// convert Date to Calendar
	java.util.Calendar cal = java.util.Calendar.getInstance();
	cal.setTime(d);

	return calendarToString(cal,1);
}
/**
 * Convert java.util.Date to a String in the format 'dd/mm/yyyy'
 */
public static String dateToString(java.util.Date d)
{
	if (d == null)
		return "00/00/0000";
        
	// convert Date to Calendar
	java.util.Calendar cal = java.util.Calendar.getInstance();
	cal.setTime(d);

	return calendarToString(cal,0);
}
/**
 Returns a String of a Date in the format
 "dd/mm/yyyy" of the Current Date (today)
 */
public static String getCurrentDate()
{
	// convert Date to Calendar
	java.util.Calendar cal = java.util.Calendar.getInstance();
	return calendarToString(cal,0);
}



/**
 Returns a String of a Date in the format
 "yyyymmdd" of the Current Date (today)
 */
public static String getCurrentDateYYYYMMDD()
{
	// convert Date to Calendar
	java.util.Calendar cal = java.util.Calendar.getInstance();
	return calendarToString(cal,10);
}

/**
 Returns a String of a Date Time in the format
 "dd/mm/yyyy hh:mm:ss" of the Current Date (today)
 */
public static String getCurrentDateTime()
{
	// convert Date to Calendar
	Calendar cal = Calendar.getInstance();
	return calendarToString(cal,1);
}

/**
Returns the number of days between two dates
(in String format "dd/mm/yyyy")

*/

public static int getDays(String DateA, String DateB) throws Throwable
{
	int cont = 0;

	// while (this.compare(DateA, DateB) != 2)

	while (DateA.compareTo(DateB) != 0)
		{
		cont++;
		DateA = add(DateA, 1);
	}

	return cont;

}
/**
  * This Method receives three parameters
  *
  *  - day
  *  - month
  *  - year
  *
  * And returns a String Date in the format "dd/mm/yyyy"
 */

public static String getStringDate(int dd, int mm, int yy)
{
	StringBuffer sb = new StringBuffer();
    
	// Number Formaters
	java.text.DecimalFormat dformat = new java.text.DecimalFormat("00");
	java.text.DecimalFormat yformat = new java.text.DecimalFormat("0000");

	sb.append(dformat.format(dd));
	sb.append("/");
	sb.append(dformat.format(mm));
	sb.append("/");
	sb.append(yformat.format(yy));

	return sb.toString();
}
/*
Receives 6 parameters:
- dia
- mes
- ano
- hora
- minuto
- segundo

returns a String in the format needed by Oracle.
*/

public static String stringTimeToOracleString(int dd, int mm, int yy, int hour, int min, int sec)
{
	StringBuffer sb = new StringBuffer();
    
	// Number Formaters
	java.text.DecimalFormat dformat = new java.text.DecimalFormat("00");
	java.text.DecimalFormat yformat = new java.text.DecimalFormat("0000");

	sb.append(dformat.format(dd));
	sb.append("/");
	sb.append(dformat.format(mm));
	sb.append("/");
	sb.append(yformat.format(yy));
	sb.append(" ");
	sb.append(dformat.format(hour));
	sb.append(":");
	sb.append(dformat.format(min));
	sb.append(":");
	sb.append(dformat.format(sec));
	
	return stringTimeToOracleString(sb.toString());
}
/*
Receives a String in the format "dd/mm/yyyy hh:mm:ss" (24 hour) and
returns a String in the format needed by Oracle.

Example:
  "09/02/2001 18:09:08"  ---->>> "to_date('2001-02-09 18:09:08','yyyy-mm-dd HH24:MI:SS')"
*/

public static String stringTimeToOracleString(String s)
{
	if (s.equals("00/00/0000 00:00:00"))
		return "''";

	StringBuffer sb = new StringBuffer();

	sb.append("to_date('");
	sb.append(s.substring(6, 10));
	sb.append("-");
	sb.append(s.substring(3, 5));
	sb.append("-");
	sb.append(s.substring(0, 2));
	sb.append(" ");
	sb.append(s.substring(11, 13));
	sb.append(":");
	sb.append(s.substring(14, 16));
	sb.append(":");
	sb.append(s.substring(17, 19));
	sb.append("','yyyy-mm-dd HH24:MI:SS')");

	return sb.toString();
}


/*
22 octubre 2002, convierte una fecha de framework Expresso
("2002-09-22 11:11:11.??")
a una fecha tipo "dd/mm/aaaa"
*/
public static String expressoDateToUtilDate(String s)
{
	StringBuffer sb = new StringBuffer();

	sb.append(s.substring(8, 10));
	sb.append("/");
	sb.append(s.substring(5, 7));
	sb.append("/");
	sb.append(s.substring(0, 4));

	return sb.toString();
}

/*
26 octubre 2002, convierte una fecha hora de framework Expresso
("2002-09-22 11:11:11.??")
a una fecha hora tipo "dd/mm/aaaa 11:22:33"
*/
public static String expressoDateTimeToUtilDateTime(String s)
{
	StringBuffer sb = new StringBuffer();

	sb.append(s.substring(8, 10));
	sb.append("/");
	sb.append(s.substring(5, 7));
	sb.append("/");
	sb.append(s.substring(0, 4));
	sb.append(" ");
	sb.append(s.substring(11, 13));
	sb.append(":");
	sb.append(s.substring(14, 16));
	sb.append(":");
	sb.append(s.substring(17, 19));

	return sb.toString();
}
/*
Receives a String in the format "dd/mm/yyyy" and
returns a String in the format needed by Oracle.

Example:
	  "09/02/2001"  ---->>> "to_date('2001-02-09','yyyy-mm-dd')"
*/

public static String stringToOracleString(String s)
{
	if (s.equals("00/00/0000"))
		return "''";

	StringBuffer sb = new StringBuffer();

	sb.append("to_date('");
	sb.append(s.substring(6, 10));
	sb.append("-");
	sb.append(s.substring(3, 5));
	sb.append("-");
	sb.append(s.substring(0, 2));
	sb.append("','yyyy-mm-dd')");

	return sb.toString();
}


/*
2 nov 2002 - version especial para SUNARP

Receives a String in the format "dd/mm/yyyy" and
returns a String in the format needed by Oracle.

Example:
	  "09/02/2001"  ---->>> "to_date('2001-02-09 23:59:59','yyyy-mm-dd HH24:MI:SS')"
*/

public static String stringToOracleString23(String s)
{
	if (s.equals("00/00/0000"))
		return "''";

	StringBuffer sb = new StringBuffer();

	sb.append("to_date('");
	sb.append(s.substring(6, 10));
	sb.append("-");
	sb.append(s.substring(3, 5));
	sb.append("-");
	sb.append(s.substring(0, 2));
	sb.append("-");
	sb.append(s.substring(11, 13));
	sb.append(":");
	sb.append(s.substring(14, 16));
	sb.append(":");
	sb.append(s.substring(17, 19));
	sb.append("','yyyy-mm-dd HH24:MI:SS')");

	return sb.toString();
}
/**
Date Verification:
 - Check for date format "dd/mm/yyyy"
 - Also verifies that month is not greater than 12
 - And day should not be greater than 31
 */

public static boolean verifyDate(String ind)
{

	if (ind == null)
		return false;

	if (ind.length() != 10)
		return false;

	int year, month, day;
	String xyear, xmonth, xday;

	xyear = ind.substring(6, 10);
	xmonth = ind.substring(3, 5);
	xday = ind.substring(0, 2);

	try
		{
		year = Integer.parseInt(xyear);
		month = Integer.parseInt(xmonth);
		day = Integer.parseInt(xday);
	}
	catch (NumberFormatException e)
		{
		// invalid characters... return false
		return false;
	}

	if (month > 12 || month < 1)
		return false;

	if (day > 31 || day < 1)
		return false;

	// leap year
	boolean leap = false;
	if ((year % 4 == 0) || (year % 100 == 0) || (year % 400 == 0))
		leap = true;

	if ((month == 2) && (leap == true) && (day > 29))
	   return false;
       
	if ((month == 2) && (leap != true) && (day > 28))
	   return false;

	//specific days of the months
	if (   (day   > 31) && ( (month == 1) || (month == 3) || (month == 5) || (month == 7)  ||
		   (month == 8) || (month == 10) || (month == 12)   )  )
		return false;
		
	if (   (day > 30) && (  (month == 4) || (month == 6) || (month == 9) || (month == 11) )  )
		return false;
    	
	// if all tests are ok... then return true	
	return true;
}

/*
Receives a String and
returns a String in the format needed by Oracle.

Example:
	  "SYSDATE"  ---->>> "to_date(sysdate,'yyyy-mm-dd')"
*/

public static String stringToOracleString2(String s)
{
	if (s.equals("00/00/0000"))
		return "''";

	StringBuffer sb = new StringBuffer();

	sb.append("to_date(");
	sb.append(s);
	sb.append(",'yyyy-mm-dd')");

	return sb.toString();
}

	public static String toPaginado(String fecha_pagineo) throws Throwable{
		
		if(fecha_pagineo == null || fecha_pagineo.trim().length() <= 0)
			return "                    ";	//Tiene un caracter mas (no afecta nada)
			
		if(fecha_pagineo.indexOf("-") != -1)
			return fecha_pagineo.substring(8, 10) + "/" + fecha_pagineo.substring(5, 7) + "/" + fecha_pagineo.substring(0, 4) + fecha_pagineo.substring(10, 19);

		String fecha_sin_formatear = fecha_pagineo.trim();
		String fecha = fecha_sin_formatear.substring(0, fecha_sin_formatear.indexOf(" "));
		String hora = fecha_sin_formatear.substring(fecha_sin_formatear.indexOf(" ")).trim();
		
		StringBuffer fecfor = new StringBuffer();
		
		int slash[] = new int[2];
		int dos_puntos[] = new int[2];
		
		slash[0] = fecha.indexOf("/");
		slash[1] = fecha.lastIndexOf("/");
		
		if(slash[0] == -1 || slash[1] == -1)
			throw new Exception("Formato de fecha invalida");
		
		dos_puntos[0] = hora.indexOf(":");
		dos_puntos[1] = hora.lastIndexOf(":");
		
		if(dos_puntos[0] == -1 || dos_puntos[1] == -1)
			throw new Exception("Formato de fecha invalida");
		
		DecimalFormat dia_format = new DecimalFormat("00");
		DecimalFormat ano_format = new DecimalFormat("0000");
		
		int _dia = Integer.parseInt(fecha.substring(0, slash[0]));
		int _mes = Integer.parseInt(fecha.substring(slash[0] + 1, slash[1]));
		int _ano = Integer.parseInt(fecha.substring(slash[1] + 1));
		int _hor = Integer.parseInt(hora.substring(0, dos_puntos[0]));
		int _min = Integer.parseInt(hora.substring(dos_puntos[0] + 1, dos_puntos[1]));
		int _seg = Integer.parseInt(hora.substring(dos_puntos[1] + 1));
		
		fecfor.append(dia_format.format(_dia)).append("/").append(dia_format.format(_mes)).append("/").append(ano_format.format(_ano));
		fecfor.append(" ");
		fecfor.append(dia_format.format(_hor)).append(":").append(dia_format.format(_min)).append(":").append(dia_format.format(_seg));
		
		return fecfor.toString();
	}
public static java.sql.Date transformaASQLDate (String fecha) {

	int posIndex  = fecha.indexOf("/");
	int dia = Integer.parseInt(fecha.substring(0, posIndex));
	int posIndex2 = fecha.lastIndexOf("/");	
	int mes = Integer.parseInt(fecha.substring(posIndex + 1,posIndex2));
	int ano = Integer.parseInt(fecha.substring(posIndex2 + 1,fecha.length()));
	
	
	String mes_s;
	String dia_s;

	if (mes < 10)
	{
		mes_s = "0" + java.lang.String.valueOf(mes);
	}
	else
	{
		mes_s = java.lang.String.valueOf(mes);
	}

	if (dia < 10)
	{
		dia_s = "0" + java.lang.String.valueOf(dia);
	}
	else
	{
		dia_s = java.lang.String.valueOf(dia);
	}

	java.sql.Date data_s = java.sql.Date.valueOf(java.lang.String.valueOf(ano)+"-"+mes_s+"-"+dia_s);

	return data_s;
}	

public static String getStringDateAAAAMMDD(int dd, int mm, int yy)
{
	StringBuffer sb = new StringBuffer();
    
	// Number Formaters
	java.text.DecimalFormat dformat = new java.text.DecimalFormat("00");
	java.text.DecimalFormat yformat = new java.text.DecimalFormat("0000");

	sb.append(yformat.format(yy));
	sb.append(dformat.format(mm));
	sb.append(dformat.format(dd));

	return sb.toString();
}	
	
	
	public static String stringmmddyyyytoddmmyyyy(String fecha_mmddyyyy) throws Throwable{
		
		if(fecha_mmddyyyy == null || fecha_mmddyyyy.trim().length() <= 0)
			return "                    ";	//Tiene un caracter mas (no afecta nada)
			
		if(fecha_mmddyyyy.indexOf("-") != -1)
			return fecha_mmddyyyy.substring(8, 10) + "/" + fecha_mmddyyyy.substring(5, 7) + "/" + fecha_mmddyyyy.substring(0, 4) + fecha_mmddyyyy.substring(10, 19);

		String fecha_sin_formatear = fecha_mmddyyyy.trim();
		String fecha = fecha_sin_formatear.substring(0, fecha_sin_formatear.indexOf(" "));
		String hora = fecha_sin_formatear.substring(fecha_sin_formatear.indexOf(" ")).trim();
		
		StringBuffer fecfor = new StringBuffer();
		
		int slash[] = new int[2];
		int dos_puntos[] = new int[2];
		
		slash[0] = fecha.indexOf("/");
		slash[1] = fecha.lastIndexOf("/");
		
		if(slash[0] == -1 || slash[1] == -1)
			throw new Exception("Formato de fecha invalida");
		
		dos_puntos[0] = hora.indexOf(":");
		dos_puntos[1] = hora.lastIndexOf(":");
		
		if(dos_puntos[0] == -1 || dos_puntos[1] == -1)
			throw new Exception("Formato de fecha invalida");
		
		DecimalFormat dia_format = new DecimalFormat("00");
		DecimalFormat ano_format = new DecimalFormat("0000");
		
//		int _dia = Integer.parseInt(fecha.substring(0, slash[0]));
//		int _mes = Integer.parseInt(fecha.substring(slash[0] + 1, slash[1]));
		int _mes = Integer.parseInt(fecha.substring(0, slash[0]));
		int _dia = Integer.parseInt(fecha.substring(slash[0] + 1, slash[1]));
		int _ano = Integer.parseInt(fecha.substring(slash[1] + 1));
		int _hor = Integer.parseInt(hora.substring(0, dos_puntos[0]));
		int _min = Integer.parseInt(hora.substring(dos_puntos[0] + 1, dos_puntos[1]));
		int _seg = Integer.parseInt(hora.substring(dos_puntos[1] + 1));
		
		fecfor.append(dia_format.format(_dia)).append("/").append(dia_format.format(_mes)).append("/").append(ano_format.format(_ano));
		fecfor.append(" ");
		fecfor.append(dia_format.format(_hor)).append(":").append(dia_format.format(_min)).append(":").append(dia_format.format(_seg));
		
		return fecfor.toString();
	}







public static String[] getReportYears()
{
	java.util.Calendar cal = java.util.Calendar.getInstance();
	java.util.Date dd = cal.getTime();
	int a = cal.get(java.util.Calendar.YEAR);

//Modificado por:rbahamonde
//Fecha:30/12/2008
	String[] res = {""+(a-6),""+(a-5),""+(a-4),""+(a-3),""+(a-2),""+(a-1),""+(a)};     
//	String[] res = {""+(a-4),""+(a-3),""+(a-2),""+(a-1),""+(a)};     
//Fin Modificacion    
	return res;
}

public static String[] getReportMonths()
{
	String[] res = {"01","02","03","04","05","06","07","08",
					"09","10","11","12"};
	return res;
}

public static String[] getReportDays()
{
	String[] res = {"01","02","03","04","05","06","07","08",
					"09","10","11","12","13","14","15","16",
					"17","18","19","20","21","22","23","24",
					"25","26", "27", "28","29","30","31"};
	return res;
}
//Inicio:jascencio:20/06/2007
//
	public static java.sql.Timestamp invertFechaToAmerican(String fecha){
		Timestamp time=null;
		java.sql.Date xnuevaFecha=null;
		if(fecha!=null && !fecha.equals("")){
			StringBuffer nuevaFecha=new StringBuffer();
			String year=null;
			String month=null;
			String day=null;
			
			day = fecha.substring(0, 2);
			month = fecha.substring(3, 5);
			year = fecha.substring(6, 10);
			nuevaFecha.append(year).append("-").append(month).append("-").append(day);
			SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MM-dd");
			try {
				xnuevaFecha=new java.sql.Date(formatter.parse(nuevaFecha.toString()).getTime());
				time=new java.sql.Timestamp(xnuevaFecha.getTime());
			} catch (ParseException e) {
		
				e.printStackTrace();
				System.err.println("Un error al invertir la fecha y transformarlo a Date");
			}
			
		}
		
		return time;
		
	}
//	Fin:jascencio:20/06/2007

}//fin de clase

