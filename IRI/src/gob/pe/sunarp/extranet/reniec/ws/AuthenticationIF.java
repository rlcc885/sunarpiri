package gob.pe.sunarp.extranet.reniec.ws;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AuthenticationIF extends Remote{

	public String getTicket (String valor1, String valor2) throws RemoteException;
	public String changePin (String valor1, String valor2, String valor3) throws RemoteException;
	public String getDTD () throws RemoteException;
	public String getSecureCode () throws RemoteException;
	public String loginValidation (String valor1, String valor2, String valor3) throws RemoteException;
	public String registerUser (String valor1, String valor2, String valor3, String valor4, String valor5, String valor6) throws RemoteException;
}
