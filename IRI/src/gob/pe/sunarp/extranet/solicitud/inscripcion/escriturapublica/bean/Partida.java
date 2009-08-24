package gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

/**
 * 
 * @modelguid {6BC3C47F-5B73-400A-BF08-3C2F1091A34C}
 */
public class Partida extends SunarpBean{
	
	/** @modelguid {D9D8B615-E582-4890-99F1-46C0F9F00281} */
	private String numeroPartida;

	/** @modelguid {A6570FB7-5C1F-490B-998A-E62291709027} */
	private String ficha;

	/** @modelguid {7E1CD338-2EC0-4925-ABC1-A57E03A25E17} */
	private String tomo;

	/** @modelguid {D1CBE881-9DEA-4246-B776-8CC58F1E3F7D} */
	private String foja;

	/** @modelguid {0DB10B85-B8AB-49AC-98B4-79D59E014037} */
	private String descripcionDistritoPartida;

	/** @modelguid {B4262C7A-D6A5-47E5-A7FE-6BE5EB08FB6C} */
	private String codigoDistritoPartida;

	/** @modelguid {1FB32C58-F9F7-44AE-9C6C-C7C89EA4D104} */
	private String codigoZonaRegistral;

	/** @modelguid {CC50843D-C05A-45EC-AE21-E905DF999378} */
	private String codigoOficinaRegistral;

	private String descripcionOficinaRegistral;
	
	/** @modelguid {A8BA88D1-70DB-4D89-9ACE-759DEE342386} */
	private String codigoTipoSistema;

	/** @modelguid {7A1388D1-0280-486C-94BD-F29ACD005B45} */
	private String descripcionTipoSistema;

	/**
	 * @return
	 * @modelguid {163DB633-466D-4597-B8F0-E7A48C4C5AC6}
	 */
	public String getCodigoDistritoPartida() {
		return codigoDistritoPartida;
	}

	/**
	 * @return
	 * @modelguid {4E84F968-3267-4292-ADB4-8477223F0039}
	 */
	public String getDescripcionDistritoPartida() {
		return descripcionDistritoPartida;
	}

	/**
	 * @return
	 * @modelguid {9D29FF2C-1E57-465C-9B3D-D02E3857C021}
	 */
	public String getFicha() {
		return ficha;
	}

	/**
	 * @return
	 * @modelguid {1F7DCE36-8C56-432B-9D72-BCDBF50F2EEE}
	 */
	public String getFoja() {
		return foja;
	}

	/**
	 * @return
	 * @modelguid {8505A98B-E7AB-4ACA-B15F-67B765684F59}
	 */
	public String getNumeroPartida() {
		return numeroPartida;
	}

	/**
	 * @return
	 * @modelguid {0A46672C-789F-4ED0-9B7D-1430E188D085}
	 */
	public String getTomo() {
		return tomo;
	}

	/**
	 * @param string
	 * @modelguid {25839ED1-4B16-4AE7-BA11-CA3BB8B716C0}
	 */
	public void setCodigoDistritoPartida(String string) {
		codigoDistritoPartida = string;
	}

	/**
	 * @param string
	 * @modelguid {318BD803-0D2E-40DA-91CC-D357A5A3B482}
	 */
	public void setDescripcionDistritoPartida(String string) {
		descripcionDistritoPartida = string;
	}

	/**
	 * @param string
	 * @modelguid {D5A8E35A-0561-44F0-9172-071C6D14DB2F}
	 */
	public void setFicha(String string) {
		ficha = string;
	}

	/**
	 * @param string
	 * @modelguid {62DBBAE9-9D0B-4CD1-A4E6-5F84DDA04C9C}
	 */
	public void setFoja(String string) {
		foja = string;
	}

	/**
	 * @param string
	 * @modelguid {202CD021-FFD4-42FA-86DE-8596BC023333}
	 */
	public void setNumeroPartida(String string) {
		numeroPartida = string;
	}

	/**
	 * @param string
	 * @modelguid {33532D97-E5DC-4463-8819-A547B5B2A01E}
	 */
	public void setTomo(String string) {
		tomo = string;
	}

	/**
	 * @return
	 * @modelguid {89D87153-EDB4-4D99-BA6C-6E203C663E76}
	 */
	public String getCodigoOficinaRegistral() {
		return codigoOficinaRegistral;
	}

	/**
	 * @return
	 * @modelguid {13BF25E0-A985-4F90-B6C4-6FF7C6D42FB0}
	 */
	public String getCodigoZonaRegistral() {
		return codigoZonaRegistral;
	}

	/**
	 * @param string
	 * @modelguid {2585A3E3-4EDD-4F91-888D-332859ACBECD}
	 */
	public void setCodigoOficinaRegistral(String string) {
		codigoOficinaRegistral = string;
	}

	/**
	 * @param string
	 * @modelguid {84DEB794-FD1C-46CC-A3C9-E3813A5FB6BA}
	 */
	public void setCodigoZonaRegistral(String string) {
		codigoZonaRegistral = string;
	}

	/**
	 * @return
	 * @modelguid {B93FE07E-5AA7-4199-919F-8AE4449ED81B}
	 */
	public String getCodigoTipoSistema() {
		return codigoTipoSistema;
	}

	/**
	 * @return
	 * @modelguid {FD309159-654D-49F7-9892-A1D972D78250}
	 */
	public String getDescripcionTipoSistema() {
		return descripcionTipoSistema;
	}

	/**
	 * @param string
	 * @modelguid {30DE6564-1F20-4FA8-8729-4D4080318D40}
	 */
	public void setCodigoTipoSistema(String string) {
		codigoTipoSistema = string;
	}

	/**
	 * @param string
	 * @modelguid {F600037F-E804-43F0-ABBF-3C280A61891C}
	 */
	public void setDescripcionTipoSistema(String string) {
		descripcionTipoSistema = string;
	}

	/**
	 * Gets the descripcionOficinaRegistral
	 * @return Returns a String
	 */
	public String getDescripcionOficinaRegistral() {
		return descripcionOficinaRegistral;
	}
	/**
	 * Sets the descripcionOficinaRegistral
	 * @param descripcionOficinaRegistral The descripcionOficinaRegistral to set
	 */
	public void setDescripcionOficinaRegistral(String descripcionOficinaRegistral) {
		this.descripcionOficinaRegistral = descripcionOficinaRegistral;
	}

}

