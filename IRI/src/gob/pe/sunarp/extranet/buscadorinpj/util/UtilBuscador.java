/**
 * 
 */
package gob.pe.sunarp.extranet.buscadorinpj.util;

import java.util.StringTokenizer;

/**
 * @author jbugarin
 *
 */
public class UtilBuscador {

	/**
	 * Clase utilitaria para armar las cadenas de busqueda 
	 */
	public UtilBuscador() {
		// TODO Apéndice de constructor generado automáticamente
	}
	
	public static String armaCadenaBusqueda(String cadena){
		StringBuilder cadenaBusqueda = new StringBuilder();
		StringTokenizer tokens = new StringTokenizer(cadena);
		while(tokens.hasMoreTokens()){  
		   	cadenaBusqueda.append("*"+tokens.nextToken()+"* ");
			//cadenaBusqueda.append("+RAZON_SOC:*taxi* +RAZON_SOC:*amigo*");
		   	//+RAZON_SOC:*taxi* +RAZON_SOC:*amigo*
		   	//+RAZON_SOC:*taxi* +RAZON_SOC:*amigo*
		}  
		cadena= cadenaBusqueda.toString().trim();		
		return cadena;
	}
	public static String armaCadenaBusquedaDeno(String cadena){
		StringBuilder cadenaBusqueda = new StringBuilder();
		StringTokenizer tokens = new StringTokenizer(cadena);
		while(tokens.hasMoreTokens()){  
		   	cadenaBusqueda.append("+SIGLAS:*"+tokens.nextToken()+"* ");		   	
		}  
		cadena= cadenaBusqueda.toString().trim();		
		return cadena;
	}
	
	public static String[] armaCadenaPharseQuery(String cadena){
		String linea = cadena;  
		String [] campos = linea.split("\\s+"); //separador de espacios en blanco
		return campos;
	}
}
