package gob.pe.sunarp.extranet.framework.tam;

/*
sep2002h
clase usada para guardar la combinacion Usuario/Password
a ser utilizado posteriormente por la clase LoginModule
utilizada en el método validaUsuario de SecAdmin

Para mayor referencia, revisar:
http://www.tivoli.com/support/public/Prodman/public_manuals/td/AccessManagerfore-business3.9.html
documento: Authorization Java Classes Developer's Reference

Además:
http://java.sun.com/security/jaas/doc/api.html
*/

import java.io.IOException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.TextOutputCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

public class UserHandler implements CallbackHandler {

private String usuario=""; //usuario TAM
private String clave ="";  //password de usuario TAM

public UserHandler(String usuario, String clave)
{
	super();
	this.usuario = usuario;
	this.clave = clave;
}
	public void handle(Callback[] callbacks)
		throws IOException, UnsupportedCallbackException {

		for (int i = 0; i < callbacks.length; i++) {
			if (callbacks[i] instanceof TextOutputCallback) {

				// display the message according to the specified type
				TextOutputCallback toc = (TextOutputCallback) callbacks[i];
				switch (toc.getMessageType()) {
					case TextOutputCallback.INFORMATION :
						System.err.println(toc.getMessage());
						break;
					case TextOutputCallback.ERROR :
						System.err.println("ERROR in Callback: " + toc.getMessage());
						break;
					case TextOutputCallback.WARNING :
						System.err.println("WARNING in Callback: " + toc.getMessage());
						break;
					default :
						throw new IOException("Unsupported message type in Callback: " + toc.getMessageType());
				}

			} else
				if (callbacks[i] instanceof NameCallback) {

					// prompt the user for a username
					NameCallback nc = (NameCallback) callbacks[i];

					// ignore the provided defaultName
					//-System.err.print(nc.getPrompt());
					System.err.flush();
					nc.setName(this.usuario);

				} else
					if (callbacks[i] instanceof PasswordCallback) {

						// prompt the user for sensitive information
						PasswordCallback pc = (PasswordCallback) callbacks[i];
						//-System.err.print(pc.getPrompt());
						System.err.flush();
						pc.setPassword(this.clave.toCharArray());

					} else {
						throw new UnsupportedCallbackException(callbacks[i], "Unrecognized Callback");
					}
		}
	}

}