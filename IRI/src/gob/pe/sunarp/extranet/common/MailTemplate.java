package gob.pe.sunarp.extranet.common;

import java.io.*;
import java.util.*;
/**
 * Esta clase permite cargar un archivo texto con, típicamente, un texto de
 * e-mail en el cual la primera línea es el subject y el resto el body.
 * Las variables del texto son llamadas "tokens", y estan encerrados
 * entre corchetes "[]".
 * Una vez cargado el texto con tokens (template), se puede obtener un
 * texto en el cual se reemplaza el token con un valor segun un hashtable
 * 
 * Por ejemplo, si tenemos un archivo texto cuyo contenido es:
 * 		Estimado señor [NOMBRE]
 *		Gracias por comprar en tiendas [TIENDA]
 * Lo podemos cargar con el constructor y luego, el método getText, con
 * un hashtable con los siguientes valores:
 * 		NOMBRE=Willy E. Coyote
 * 		TIENDA=ACME
 * Tendriamos el siguiente texto:
 * 		Estimado señor Willy E.Coyote
 *		Gracias por comprar en tiendas ACME
 *
 * Los valores de los token son por default la cadena nula.
 */
public class MailTemplate {

	/**
	* Caracter que marca el inicio de un Token
	*/
	public static final String BEGIN_TOKEN = "[";
	/**
	* Caracter que marca el fin de un Token
	*/
	public static final String END_TOKEN = "]";

	// Guarda el texto del template partido por los inicios y fin de token
	// En donde estaba el nombre del token le pone "" para luego llenarlo
	// con el valor asignado al token
	private Vector acumulador = new Vector();
	// Guarda el nombre del token relacionado con la posición en el que
	// está dentro del vector
	private Hashtable tokensIndices = new Hashtable();

	/**
	 * Lee el archivo texto mandado como parámetro y lo parte en los
	 * delimitadores del token, guardando los textos fuera del token
	 * en el acumulador, dejando espacio en las posiciones en las cuales
	 * tienen que ir los valores que va a tomar los token.
	 * En caso el archivo pasado como parametro no pudo ser leido o su
	 * tamaño es mayor a 2GB, se bota un IllegalArgumentException()
	 */
	public MailTemplate(String fileName) {
		super();

		// nos aseguramos que el archivo pueda entrar en un arreglo de bytes
		// para esto, el archivo tiene que ser menor al maximo integer = 2GB
		File file = new File(fileName);
		long longitud = file.length();
		if (longitud > Integer.MAX_VALUE) throw new IllegalArgumentException(fileName + " es mayor a 2 GB.");

		// leemos el archivo y lo cargamos en un string
		String body;
		try {
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));

			byte[] bytes = new byte[(int)file.length()];
			in.read(bytes);

			body = new String(bytes);
		} catch (IOException e) {
			// Si el archivo no pudo ser leido, entonces, se interpreta que el
			// archivo no es válido
			throw new IllegalArgumentException(fileName + " no pudo ser leido por el siguiente error: " + e.toString());
		}

		// el cursor nos ayuda en marcar hasta donde hemos procesado en cada
		// iteracion del bloque while. Al principio está al inicio
		int cursor = 0;

		// en cada iteracion se procesa un texto antes de un token y, de haber
		// un token.
		while (true) {

			// begin nos marca el inicio del token y el fin del texto antes
			// del token
			int begin = body.indexOf(BEGIN_TOKEN, cursor);
			// si begin = -1, significa que no hay mas tokens
			// y el texto antes del token se extiende hasta el final
			if (begin == -1) begin = body.length();
	
			// agregamos el texto antes del token al acumulador
			String antesToken = body.substring(cursor, begin);
			if (!antesToken.equals("")) {
				acumulador.addElement(antesToken);
			}

			// end nos marca el fin del token y la posicion del cursor en
			// la siguiente iteracion
			int end = body.indexOf(END_TOKEN, cursor);
			// no hay mas tokens y ya se proceso el texto antes del token
			// se sale.
			if (end == -1) break;

			// agregamos el token
			String token = body.substring(begin + 1, end);
			if (!token.equals("")) {
				// al acumulador lo agregamos como texto vacio
				// es decir, por default, el valor de cualquier token
				// es la cadena vacia
				acumulador.addElement("");
				// en el hashtable guardamos en que posicion dentro del
				// acumulador se va a guardar el valor del token
				tokensIndices.put(token, new Integer(acumulador.size()-1));
			}

			// aumentamos el cursor
			cursor = end+1;

		}
	}
	/**
	 * Reemplaza los valores de los tokens en el acumulador
	 * Por lo valores pasados en el hashtable identificados por el
	 * nombre del token
	 * Si en el hashtable se pasa un nombre de token no conocido, se ignora
	 * Si no estan todos los tokens del template, los que no estén se
	 * quedan con su valor default
	 */
	public String[] getText(Hashtable tokenValue) {

		// copia local del acumulador para que pueda ser modificada
		Vector acumulador = (Vector)this.acumulador.clone();

		// lista de los token pasados como parametro
		Enumeration tokens = tokenValue.keys();

		// por cada token pasado como parametro
		while (tokens.hasMoreElements()) {

			Object token = tokens.nextElement();

			// buscamos el indice del token
			Integer indice = (Integer)tokensIndices.get(token);

			// si indice == null, significa que el token pasado en el
			// hashtable parámetro no existe.
			if (indice != null) {
				// ponemos en el acumulador en el indice indicado el valor del token
				acumulador.setElementAt(tokenValue.get(token), indice.intValue());
			}
		}

		// a este punto, ya tenemos todos los token que se pudieron
		// con su valor.
		
		// concatenamos todo el acumulador en un StringBuffer
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < acumulador.size(); i++) {
			buffer.append(acumulador.elementAt(i));
		}

		// hasta el primer salto de línea es el subject, el resto es el body
		String texto = buffer.toString();
		int saltoLinea1 = texto.indexOf("\r\n");
		int saltoLinea2 = texto.indexOf("\n");
		int finSubject;
		int inicioBody;
		if (saltoLinea1 == -1) {
			finSubject = saltoLinea2;
			inicioBody = finSubject + 1;
		} else if (saltoLinea2 == -1) {
			finSubject = saltoLinea1;
			inicioBody = finSubject + 2;
		} else {
			finSubject = Math.min(saltoLinea1, saltoLinea2);
			inicioBody = finSubject + (finSubject == saltoLinea1 ? 2 : 1);
		}

		String[] resultado = new String[2];

		resultado[0] = texto.substring(0, finSubject);
		resultado[1] = texto.substring(inicioBody);

		return resultado;
	}
}

