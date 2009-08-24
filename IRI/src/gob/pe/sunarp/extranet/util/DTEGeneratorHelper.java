/*
 * Creado el 08-sep-06
 * by Luis Suarez
 * 
 * ACL - PentaSecurity
 * 
 */
package gob.pe.sunarp.extranet.util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Luis Suarez
 *
 */
public class DTEGeneratorHelper {

	private static String BLANK_SPACE = " ";
	private static String EOL = "\n";


	/**
	 * Este metodo agrega un Long, lo alinea a la derecha
	 * 
	 * @param element Elemento a agregar a la linea
	 * @param line Linea a la cual se agrega el elemento
	 * @param length longitud que tiene que ocupar el elemento en la linea
	 */
	public static void addSection(Long element, StringBuffer line, int length) {
		
		//si el elemento es nulo colocamos espacion vacios
		if(element == null){
			for(int x = 0; x < length; x++){
				line.append(BLANK_SPACE);
			}
		} else {
			//si el elemento no es nulo
			String elementString = element.toString();
			int elementLength = elementString.length();	
		
			//si longitud del elemento es mayor o igual que los espacios a utilizar
			//se colocan los que entren	
			if(elementLength >= length){
				line.append(elementString.substring(0, length));
			} else {
				//si longitud del elemento es menor que los espacios a utilizar
				//se rellenan con blancos  luego  se coloca el elemento
				for(int x = 0; x < (length-elementLength); x++){
					line.append(BLANK_SPACE);
				}
				line.append(elementString);				
			}
		}
	}

	/**
	 * Este metodo agrega un String, lo alinea a la izquierda
	 * 
	 * @param element Elemento a agregar a la linea
	 * @param line Linea a la cual se agrega el elemento
	 * @param length longitud que tiene que ocupar el elemento en la linea
	 */
	public static void addSection(String element, StringBuffer line, int length) {
		
		//si el elemento es nulo colocamos espacion vacios
		if(element == null){
			for(int x = 0; x < length; x++){
				line.append(BLANK_SPACE);
			}
		} else {
			//si el elemento no es nulo
			int elementLength = element.length();	
		
			//si longitud del elemento es mayor o igual que los espacios a utilizar
			//se colocan los que entren	
			if(elementLength >= length){
				line.append(element.substring(0, length));
			} else {
				//si longitud del elemento es menor que los espacios a utilizar
				//se coloca el elemento luego se rellenan con blancos
				line.append(element);				
				for(int x = 0; x < (length-elementLength); x++){
					line.append(BLANK_SPACE);
				}
			}
		}
	}

	/**
	 * Este metodo agrega un timestamp, el tamanio siempre es 8
	 * 
	 * @param element Elemento a agregar a la linea
	 * @param line Linea a la cual se agrega el elemento
	 */
	public static void addSection(Timestamp element, StringBuffer line) {
		
		//si el elemento es nulo colocamos espacion vacios
		if(element == null){
			for(int x = 0; x < 8; x++){
				line.append(BLANK_SPACE);
			}
		} else {
			String elemetString = new SimpleDateFormat("yyyyMMdd").format(new Date(element.getTime()));
			line.append(elemetString);
		}
	}

	/**
	 * Este metodo agrega un BigDecimal, se alinea a la derecha
	 * 
	 * @param element Elemento a agregar a la linea
	 * @param line Linea a la cual se agrega el elemento
	 */
	public static void addSection(BigDecimal element, StringBuffer line, int length, boolean whitDecimals, int cantidadDecimales){
		
		//si el elemento es nulo colocamos espacion vacios
		if(element == null){
			for(int x = 0; x < length; x++){
				line.append(BLANK_SPACE);
			}
		} else {

			//si el elemento no es nulo
			//si necesita que se muestren decimales
			if(whitDecimals){

				//TODO

			} else {
			//si no necesita que se muestren decimales	
				String elementString = new DecimalFormat("#").format(element.doubleValue());
				int elementLength = elementString.length();	
				//si longitud del elemento es mayor o igual que los espacios a utilizar
				//se colocan los que entren	
				if(elementLength >= length){
					line.append(elementString.substring(0, length));
				} else {
					//si longitud del elemento es menor que los espacios a utilizar
					//se rellenan con blancos  luego  se coloca el elemento
					for(int x = 0; x < (length-elementLength); x++){
						line.append(BLANK_SPACE);
					}
					line.append(elementString);				
				}
			}
		}
	}

	public static void addEOL(StringBuffer line){
		line.append(EOL);
	}

	public static String montoAsWords(BigDecimal monto){
		
		String montoAsWords = "";
		
		//millones
		if (monto.compareTo(new BigDecimal("2000000")) != -1){
			montoAsWords = montoAsWords(div(monto, new BigDecimal("1000000"))) + "millones " + montoAsWords(mod(monto, new BigDecimal("1000000")));
		} else if (monto.compareTo(new BigDecimal("1000000")) != -1){
			montoAsWords = " un millon "+ montoAsWords(mod(monto, new BigDecimal("1000000")));
		}
		//miles
		else if (monto.compareTo(new BigDecimal("2000")) != -1){
			montoAsWords = montoAsWords(div(monto, new BigDecimal("1000")))+"mil "+montoAsWords(mod(monto, new BigDecimal("1000")));
		}
		else if (  monto.compareTo(new BigDecimal("1000")) != -1  )
		{
			 montoAsWords = "mil "+montoAsWords(mod(monto, new BigDecimal("1000"))  );
		}
		else
		{
			 if (  (monto.compareTo(new BigDecimal("900")) != -1 ) && (monto.compareTo(new BigDecimal("999")) != 1 ) )
			 {
				  montoAsWords = "novecientos "+montoAsWords(mod(monto, new BigDecimal("100"))  );
			 }
			 else if (  (monto.compareTo(new BigDecimal("800")) != -1 ) && (monto.compareTo(new BigDecimal("899")) != 1 ) )
			 {
				  montoAsWords = "ochocientos "+montoAsWords(mod(monto, new BigDecimal("100"))  );
			 }
			 else if (  (monto.compareTo(new BigDecimal("700")) != -1 ) && (monto.compareTo(new BigDecimal("799")) != 1 ) )
			 {
				  montoAsWords = "setecientos "+montoAsWords(mod(monto, new BigDecimal("100"))  );
			 }
			 else if (  (monto.compareTo(new BigDecimal("600")) != -1 ) && (monto.compareTo(new BigDecimal("699")) != 1 ) )
			 {
				  montoAsWords = "seicientos "+montoAsWords(mod(monto, new BigDecimal("100"))  );
			 }
			 else if (  (monto.compareTo(new BigDecimal("500")) != -1 ) && (monto.compareTo(new BigDecimal("599")) != 1 ) )
			 {
				  montoAsWords = "quinientos "+montoAsWords(mod(monto, new BigDecimal("100"))  );
			 }
			 else if (  (monto.compareTo(new BigDecimal("400")) != -1 ) && (monto.compareTo(new BigDecimal("499")) != 1 ) )
			 {
				  montoAsWords = "cuatrocientos "+montoAsWords(mod(monto, new BigDecimal("100"))  );
			 }
			 else if (  (monto.compareTo(new BigDecimal("300")) != -1 ) && (monto.compareTo(new BigDecimal("399")) != 1 ) )
			 {
				  montoAsWords = "trecientos "+montoAsWords(mod(monto, new BigDecimal("100"))  );
			 }
			 else if (  (monto.compareTo(new BigDecimal("200")) != -1 ) && (monto.compareTo(new BigDecimal("299")) != 1 ) )
			 {
				  montoAsWords = "doscientos "+montoAsWords(mod(monto, new BigDecimal("100"))  );
			 }
			 else if (  (monto.compareTo(new BigDecimal("100")) == 0) )
			 {
				  montoAsWords = "cien "+montoAsWords(mod(monto, new BigDecimal("100"))  );
			 }
			 else if (  (monto.compareTo(new BigDecimal("101")) != -1 ) && (monto.compareTo(new BigDecimal("199")) != 1 ) )
			 {
				  montoAsWords = "ciento "+montoAsWords(mod(monto, new BigDecimal("100"))  );
			 }
			 else if (  (monto.compareTo(new BigDecimal("90")) == 0) )
			 {
				  montoAsWords = "noventa "+montoAsWords(mod(monto, new BigDecimal("10"))  );
			 }
			 else if (  (monto.compareTo(new BigDecimal("91")) != -1 ) && (monto.compareTo(new BigDecimal("99")) != 1 ) )
			 {
				  montoAsWords = "noventa y "+montoAsWords(mod(monto, new BigDecimal("10"))  );
			 }
			 else if (  (monto.compareTo(new BigDecimal("80")) == 0) )
			 {
				  montoAsWords = "ochenta "+montoAsWords(mod(monto, new BigDecimal("10"))  );
			 }
			 else if (  (monto.compareTo(new BigDecimal("81")) != -1 ) && (monto.compareTo(new BigDecimal("89")) != 1 ) )
			 {
				  montoAsWords = "ochenta y "+montoAsWords(mod(monto, new BigDecimal("10"))  );
			 }
			 else if (  (monto.compareTo(new BigDecimal("71")) != -1 ) && (monto.compareTo(new BigDecimal("79")) != 1 ) )
			 {
				  montoAsWords = "setenta y "+montoAsWords(mod(monto, new BigDecimal("10"))  );
			 }
			 else if (  (monto.compareTo(new BigDecimal("70")) == 0) )
			 {
				  montoAsWords = "setenta "+montoAsWords(mod(monto, new BigDecimal("10"))  );
			 }
			 else if (  (monto.compareTo(new BigDecimal("60")) == 0)  )
			 {
				  montoAsWords = "sesenta "+montoAsWords(mod(monto, new BigDecimal("10"))  );
			 }
			 else if (  (monto.compareTo(new BigDecimal("61")) != -1 ) && (monto.compareTo(new BigDecimal("69")) != 1 ) )
			 {
				  montoAsWords = "sesenta y "+montoAsWords(mod(monto, new BigDecimal("10"))  );
			 }
			 else if (  (monto.compareTo(new BigDecimal("50")) == 0) )
			 {
				  montoAsWords = "cincuenta "+montoAsWords(mod(monto, new BigDecimal("10"))  );
			 }
			 else if (  (monto.compareTo(new BigDecimal("51")) != -1 ) && (monto.compareTo(new BigDecimal("59")) != 1 ) )
			 {
				  montoAsWords = "cincuenta y "+montoAsWords(mod(monto, new BigDecimal("10"))  );
			 }
			 else if (  (monto.compareTo(new BigDecimal("40")) == 0) )
			 {
				  montoAsWords = "cuarenta "+montoAsWords(mod(monto, new BigDecimal("10"))  );
			 }
			 else if (  (monto.compareTo(new BigDecimal("41")) != -1 ) && (monto.compareTo(new BigDecimal("49")) != 1 ) )
			 {
				  montoAsWords = "cuarenta y "+montoAsWords(mod(monto, new BigDecimal("10"))  );
			 }
			 else if (  (monto.compareTo(new BigDecimal("31")) != -1 ) && (monto.compareTo(new BigDecimal("39")) != 1 ) )
			 {
				  montoAsWords = "treinta y "+montoAsWords(mod(monto, new BigDecimal("10"))  );
			 }
			 else if (  (monto.compareTo(new BigDecimal("30")) == 0) )
			 {
				  montoAsWords = "treinta "+montoAsWords(mod(monto, new BigDecimal("10"))  );
			 }
			 else if (  (monto.compareTo(new BigDecimal("21")) != -1 ) && (monto.compareTo(new BigDecimal("29")) != 1 )  )
			 {
				  montoAsWords = "veinti"+montoAsWords(mod(monto, new BigDecimal("10"))  );
			 }
			 else if (  (monto.compareTo(new BigDecimal("20")) == 0) )
			 {
				  montoAsWords = "veinte "+montoAsWords(mod(monto, new BigDecimal("10"))  );
			 }
			 else if (  monto.compareTo(new BigDecimal("19")) == 0 )
			 {
				  montoAsWords = "diecinueve ";
			 }
			 else if (  monto.compareTo(new BigDecimal("18")) == 0 )
			 {
				  montoAsWords = "dieciocho ";
			 }
			 else if (  monto.compareTo(new BigDecimal("17")) == 0 )
			 {
				  montoAsWords = "diecisiete ";
			 }
			 else if (  monto.compareTo(new BigDecimal("16")) == 0 )
			 {
				  montoAsWords = "dieciseis ";
			 }
			 else if (  monto.compareTo(new BigDecimal("15")) == 0 )
			 {
				  montoAsWords = "quince ";
			 }
			 else if (  monto.compareTo(new BigDecimal("13")) == 0 )
			 {
				  montoAsWords = "catorce ";
			 }
			 else if (  monto.compareTo(new BigDecimal("13")) == 0 )
			 {
				  montoAsWords = "trece ";
			 }
			 else if (  monto.compareTo(new BigDecimal("12")) == 0 )
			 {
				  montoAsWords = "doce ";
			 }
			 else if (  monto.compareTo(new BigDecimal("11")) == 0 )
			 {
				  montoAsWords = "once ";
			 }
			 else if (  monto.compareTo(new BigDecimal("10")) == 0 )
			 {
				  montoAsWords = "diez ";
			 }
			 else if (  monto.compareTo(new BigDecimal("9")) == 0 )
			 {
				  montoAsWords = "nueve ";
			 }
			 else if (  monto.compareTo(new BigDecimal("8")) == 0 )
			 {
				  montoAsWords = "ocho ";
			 }
			 else if (  monto.compareTo(new BigDecimal("7")) == 0 )
			 {
				  montoAsWords = "siete ";
			 }
			 else if (  monto.compareTo(new BigDecimal("6")) == 0 )
			 {
				  montoAsWords = "seis ";
			 }
			 else if (  monto.compareTo(new BigDecimal("5")) == 0 )
			 {
				  montoAsWords = "cinco ";
			 }
			 else if (  monto.compareTo(new BigDecimal("4")) == 0 )
			 {
				  montoAsWords = "cuatro ";
			 }
			 else if (  monto.compareTo(new BigDecimal("3")) == 0 )
			 {
				  montoAsWords = "tres ";
			 }
			 else if (  monto.compareTo(new BigDecimal("2")) == 0 )
			 {
				  montoAsWords = "dos ";
			 }
			 else if (  monto.compareTo(new BigDecimal("1")) == 0 )
			 {
				  montoAsWords = "un ";
			 }
			 else
			 {
				  montoAsWords = "";
			 }
		}
		
		return montoAsWords;
		
	}

	private static BigDecimal mod(BigDecimal dividendo, BigDecimal divisor){
	
		BigDecimal cociente = dividendo.divide(divisor, 0, BigDecimal.ROUND_FLOOR);
		BigDecimal residuo = dividendo.subtract(cociente.multiply(divisor));
		return residuo;

	}

	private static BigDecimal div(BigDecimal dividendo, BigDecimal divisor){
	
		BigDecimal cociente = dividendo.divide(divisor, 0, BigDecimal.ROUND_FLOOR);
		return cociente;
	}


}
