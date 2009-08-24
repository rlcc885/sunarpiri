package gob.pe.sunarp.extranet.solicitud.inscripcion.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

/**
 * 
 * @modelguid {89C108CE-35E2-42C7-8B1A-E94928CA6927}
 */
public class ActoSolicitudInscripcion extends SunarpBean{
	
	/** @modelguid {6144619F-01D3-48D1-895F-C28F34FA2630} */
	private String codigoArea;

	/** @modelguid {D3A5E8BE-E737-4209-AD39-0F1958D2D6C0} */
	private String codigoLibro;

	/** @modelguid {9FA95D5D-755A-47CA-B798-45E01E3EC82B} */
	private String codigoActo;

	/** @modelguid {898A04BA-00E8-4A0A-A1FA-A8C090316CDA} */
	private String codigoRubro;



	/**
	 * @return
	 * @modelguid {CBA0DCFE-8207-4C98-AADA-08CDE8F31C50}
	 */
	public String getCodigoActo() {
		return codigoActo;
	}

	/**
	 * @return
	 * @modelguid {98800DA0-A8A1-4B43-93DD-E0F726897D4B}
	 */
	public String getCodigoArea() {
		return codigoArea;
	}

	/**
	 * @return
	 * @modelguid {39F9C994-384C-431E-9141-157DCEEE4B24}
	 */
	public String getCodigoLibro() {
		return codigoLibro;
	}

	/**
	 * @return
	 * @modelguid {7A061610-3E24-4ABD-A767-FC1280E62954}
	 */
	public String getCodigoRubro() {
		return codigoRubro;
	}

	/**
	 * @param string
	 * @modelguid {72028684-C311-433F-B98E-B97B38F184F6}
	 */
	public void setCodigoActo(String string) {
		codigoActo = string;
	}

	/**
	 * @param string
	 * @modelguid {7C9E39BC-1570-4C48-8864-3E4832D1BC70}
	 */
	public void setCodigoArea(String string) {
		codigoArea = string;
	}

	/**
	 * @param string
	 * @modelguid {7543A2C4-1C01-41FF-AC27-42A1B57F392B}
	 */
	public void setCodigoLibro(String string) {
		codigoLibro = string;
	}

	/**
	 * @param string
	 * @modelguid {BE195B67-E8A2-4F49-A589-CC606CE3F5A5}
	 */
	public void setCodigoRubro(String string) {
		codigoRubro = string;
	}

}

