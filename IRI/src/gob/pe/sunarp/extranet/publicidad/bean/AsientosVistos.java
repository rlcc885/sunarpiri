package gob.pe.sunarp.extranet.publicidad.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

import java.util.Vector;

public class AsientosVistos extends SunarpBean{
	
	private Vector vistos = new Vector();
	
	public void addVisto(String indexSubclass, long objetoID) {
		vistos.addElement(indexSubclass + objetoID);
	}
	
	public boolean fueVisto(String indexSubclass, long objetoID) {
		return vistos.contains(indexSubclass + objetoID);
	}
	
	public void limpiar() {
		vistos.clear();
	}
}

