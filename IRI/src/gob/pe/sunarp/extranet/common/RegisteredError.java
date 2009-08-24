package gob.pe.sunarp.extranet.common;

public class RegisteredError extends SunarpBean{
	private String errorCode;//Codigo del error
	private String message; //Mensaje interpretativo del codigo
	private int level;  //Nivel del Error (0, 1, 2, 3, 4)
	private boolean email; //Flag que indica si en ocurrencia de este error se debe mandar e-mail
/**
 * RegisteredError constructor comment.
 */
public RegisteredError() {
	super();
}
/**
 * getter de la variable email.
 * Creation date: (20-Sep-01 12:51:17 PM)
 * @return boolean
 */
public boolean getEmail() {
	return email;
}
/**
 * getter de la variable errorCode.
 * Creation date: (20-Sep-01 12:48:39 PM)
 * @return java.lang.String
 */
public String getErrorCode() {
	return errorCode;
}
/**
 * getter de la variable level.
 * Creation date: (20-Sep-01 12:52:12 PM)
 * @return int
 */
public int getLevel() {
	return level;
}
/**
 * getter de la variable message.
 * Creation date: (20-Sep-01 12:49:58 PM)
 * @return java.lang.String
 */
public String getMessage() {
	return message;
}
/**
 * setter para la variable email.
 * Creation date: (20-Sep-01 12:50:39 PM)
 * @param email boolean
 */
public void setEmail(boolean email) {
	this.email = email;
	}
/**
 * setter de la variable errorCode de tipo String.
 * Creation date: (20-Sep-01 12:47:42 PM)
 */
public void setErrorCode(String errorCode) {

	this.errorCode = errorCode;
	
	}
/**
 * setter de la variable level de tipo int.
 * Creation date: (20-Sep-01 12:51:40 PM)
 * @param level int
 */
public void setLevel(int level) {
	this.level = level;
	}
/**
 * setter de la variable message de tipo String.
 * Creation date: (20-Sep-01 12:49:08 PM)
 * @param message java.lang.String
 */
public void setMessage(String message) {
	this.message = message;
	}
	
public String toString() {
	StringBuffer buffer = new StringBuffer();
	buffer.append(errorCode);
	buffer.append(": ");
	buffer.append(message);
	buffer.append(" (level:");
	buffer.append(level);
	buffer.append(")");
	if (email)
		buffer.append(" - email");
	return buffer.toString();
}
}
