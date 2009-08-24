package gob.pe.sunarp.extranet.reniec.ws;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DataVerificationIF extends Remote{

	public String dataVerification (String xmlDocumento) throws RemoteException;
	public String getDatavalidate (String xmlDocumento) throws RemoteException;
}
