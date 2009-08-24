package gob.pe.sunarp.extranet.solicitud.inscripcion.reservanombre.bean;
import gob.pe.sunarp.extranet.common.SunarpBean;
import gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago;

/**
 * 
 * @modelguid {8B233188-1688-47D8-9C0F-93DE693038AB}
 */
public class SolicitudBusqueda extends SunarpBean{
	
	
	/** @modelguid {30F24D0A-FDD7-4BCB-816E-757F0E27FE2D} */
	private String codigoUsuario;
	/** @modelguid {E3A1261A-A736-4BD6-BD63-CCF43F7CEC18} */
	private String codigoServicio;
	/** @modelguid {779917D8-4260-450F-BED7-843ACDDB4FAE} */
	private String descripcionServicio;
	/** @modelguid {4F273BE4-FC39-431C-A996-BF1ACAB49DAE} */
	private String cuo;
	/** @modelguid {442D49F8-BE0A-4F82-8FFA-3324CC9AD92D} */
	private String codigoInstitucion;
	/** @modelguid {80B82078-BA88-4531-BB81-969619FD286D} */
	private String descripcionInstitucion;
	/** @modelguid {E5A18DEC-76AD-485A-AD5D-1D78AFD24A8C} */
	private String secuencialOperacion;
	
	/**
	 * 
	 * @modelguid {EA4590BE-E974-4780-B956-ABADCE7BA7F2}
	 */
	private java.util.ArrayList filtroBusqueda;

	/**
	 * 
	 * @modelguid {9672B852-390E-4973-8A17-5C43166E122F}
	 */
	private DatosPago datosPago;

	
	/**
	 * @return
	 * @modelguid {7F54B352-E039-43B3-8789-CE32C8C179D4}
	 */
	public String getCodigoServicio() {
		return codigoServicio;
	}

	/**
	 * @return
	 * @modelguid {C41623B5-802F-4493-A448-2F2721D16484}
	 */
	public String getCodigoUsuario() {
		return codigoUsuario;
	}

	/**
	 * @return
	 * @modelguid {864EE591-F018-46C6-AD2C-629C07B2A82C}
	 */
	public String getDescripcionServicio() {
		return descripcionServicio;
	}

	/**
	 * @param string
	 * @modelguid {03D1559A-0F58-40D6-BE67-F7A531820391}
	 */
	public void setCodigoServicio(String string) {
		codigoServicio = string;
	}

	/**
	 * @param string
	 * @modelguid {E044D7F9-B503-4B92-AC34-2233AFB291D7}
	 */
	public void setCodigoUsuario(String string) {
		codigoUsuario = string;
	}

	/**
	 * @param string
	 * @modelguid {28D86E78-1F44-40F7-9570-86148BC67E9B}
	 */
	public void setDescripcionServicio(String string) {
		descripcionServicio = string;
	}


	/**
	 * @return
	 * @modelguid {FA51B980-BA76-4255-9966-90642FB11B57}
	 */
	public java.util.ArrayList getFiltroBusqueda() {
		return filtroBusqueda;
	}


	/**
	 * @param list
	 * @modelguid {6D6FA435-18B8-4DCC-B87C-E1CD6D1192DE}
	 */
	public void setFiltroBusqueda(java.util.ArrayList list) {
		filtroBusqueda = list;
	}



	/**
	 * @return
	 * @modelguid {6FC64379-FFF1-48E9-AB20-B8E89E2B5ADC}
	 */
	public String getCodigoInstitucion() {
		return codigoInstitucion;
	}

	/**
	 * @return
	 * @modelguid {D4B7A6BA-D2CE-45BE-9FE8-7F90CAD7584F}
	 */
	public String getCuo() {
		return cuo;
	}

	/**
	 * @return
	 * @modelguid {DA4324EA-403A-48F3-A4D3-A08261422726}
	 */
	public String getDescripcionInstitucion() {
		return descripcionInstitucion;
	}

	/**
	 * @param string
	 * @modelguid {A2FA155C-8108-4C4F-9C07-9B09618E86D4}
	 */
	public void setCodigoInstitucion(String string) {
		codigoInstitucion = string;
	}

	/**
	 * @param string
	 * @modelguid {B4E24D11-7F68-4C37-BD02-8047990C9B74}
	 */
	public void setCuo(String string) {
		cuo = string;
	}

	/**
	 * @param string
	 * @modelguid {D4079FA1-A4FB-4199-990A-4AEC0E47BA1C}
	 */
	public void setDescripcionInstitucion(String string) {
		descripcionInstitucion = string;
	}

	/**
	 * @return
	 * @modelguid {68454D55-1B01-48B6-9695-F338B4E66AEE}
	 */
	public String getSecuencialOperacion() {
		return secuencialOperacion;
	}

	/**
	 * @param string
	 * @modelguid {16A44C56-036A-429A-BBE9-FEB7C45D250B}
	 */
	public void setSecuencialOperacion(String string) {
		secuencialOperacion = string;
	}

	/**
	 * @return
	 */
	public DatosPago getDatosPago() {
		return datosPago;
	}

	/**
	 * @param pago
	 */
	public void setDatosPago(DatosPago pago) {
		datosPago = pago;
	}

}

