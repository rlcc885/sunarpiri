package gob.pe.sunarp.extranet.solicitud.inscripcion.reservanombre.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

/** @modelguid {304F8D31-0539-4FD2-A444-19DFCF6A8651} */
public class ResultadoBusqueda extends SunarpBean{
	

	/** @modelguid {3CAD258A-A534-4978-B65D-E166F2BBF812} */
	private String codigoZonaRegistral;
	
	/** @modelguid {D1307ABD-4B80-40BC-9F68-54E71EDC90F8} */
	private String descripcionZonaRegistral;

	/** @modelguid {28599805-F6EB-4BD4-AFFA-9E32AAF2597A} */
	private String codigoOficinaRegistral;

	/** @modelguid {66E1FA6C-C8F9-42B7-AB93-C23DC3AD2E5B} */
	private String descripcionOficinaRegistral;

	/** @modelguid {D78C16BA-5F9C-49B8-AEF2-E71A28F370FC} */
	private String razonSocial;
	
	/** @modelguid {60F9161C-D984-4068-A90E-5D151A6157EB} */
	private String numeroPartidaTitulo;

	/**
	 * @return
	 * @modelguid {34CE327D-436C-4903-AD02-2FC039538451}
	 */
	public String getCodigoOficinaRegistral() {
		return codigoOficinaRegistral;
	}

	/**
	 * @return
	 * @modelguid {7347FB48-D155-4BC6-9D68-4E9D1C784B00}
	 */
	public String getDescripcionOficinaRegistral() {
		return descripcionOficinaRegistral;
	}

	/**
	 * @return
	 * @modelguid {778EC0B4-6BBF-4407-BFD9-460AF85475D7}
	 */
	public String getRazonSocial() {
		return razonSocial;
	}

	/**
	 * @param string
	 * @modelguid {42A320C7-87DA-4C62-B6E0-40338307BEC4}
	 */
	public void setCodigoOficinaRegistral(String string) {
		codigoOficinaRegistral = string;
	}

	/**
	 * @param string
	 * @modelguid {6284AB4A-367B-442C-B66E-B6424522B02F}
	 */
	public void setDescripcionOficinaRegistral(String string) {
		descripcionOficinaRegistral = string;
	}

	/**
	 * @param string
	 * @modelguid {FB23D1FC-EE38-406F-9700-E915EBD5CA73}
	 */
	public void setRazonSocial(String string) {
		razonSocial = string;
	}

	/**
	 * @return
	 * @modelguid {77026EF0-6F0E-4AC2-A45F-8703AAA12334}
	 */
	public String getCodigoZonaRegistral() {
		return codigoZonaRegistral;
	}

	/**
	 * @return
	 * @modelguid {41D0B53F-E16A-4AA0-BB99-C6CDEE6D3D68}
	 */
	public String getDescripcionZonaRegistral() {
		return descripcionZonaRegistral;
	}

	/**
	 * @param string
	 * @modelguid {327334BA-5D6C-43B2-8502-C98616949827}
	 */
	public void setCodigoZonaRegistral(String string) {
		codigoZonaRegistral = string;
	}

	/**
	 * @param string
	 * @modelguid {8C7C9758-727D-47FD-B9C6-A27EF7472EFF}
	 */
	public void setDescripcionZonaRegistral(String string) {
		descripcionZonaRegistral = string;
	}

	/**
	 * @return
	 * @modelguid {9B3E2F60-B1D0-4818-BB4B-E4E7E7DF2884}
	 */
	public String getNumeroPartidaTitulo() {
		return numeroPartidaTitulo;
	}

	/**
	 * @param string
	 * @modelguid {7A7E4796-C5AF-4846-B5F6-A107B7FCC847}
	 */
	public void setNumeroPartidaTitulo(String string) {
		numeroPartidaTitulo = string;
	}

}

