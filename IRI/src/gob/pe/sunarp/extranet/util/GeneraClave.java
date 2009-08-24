package gob.pe.sunarp.extranet.util;

public class GeneraClave {
	public static String generaNuevaClave(String userId) {
		String sPassword = null;

		while (true) {

			int numeroCaracteres = (int) Math.floor(Math.random() * 5) + 6;
			int posicionDigito =
				(int) Math.floor(Math.random() * (numeroCaracteres - 2)) + 1;

			StringBuffer password = new StringBuffer();
			for (int i = 0; i < numeroCaracteres; i++) {
				if (posicionDigito == i) {
					int digito = (int) Math.floor(Math.random() * 10);
					password.append(digito);
				} else {
					char caracter =
						(char) ((int) Math.floor(Math.random() * ('Z' - 'A' + 1)) + 'A');
					password.append(caracter);
				}
			}

			sPassword = password.toString();

			//generar hasta que el user sea diferente al password				
			if (!sPassword.equals(userId)) {
				break;
			} else {
				continue;
			}
		}
		return sPassword;
	}
}