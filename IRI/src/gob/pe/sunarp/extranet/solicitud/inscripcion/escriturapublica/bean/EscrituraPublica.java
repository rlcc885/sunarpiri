package gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

/** @modelguid {2A8E3C11-17B0-44A0-BC98-4B81551C3755} */
public class EscrituraPublica extends SunarpBean{
	
	/** @modelguid {1CA50BAD-0815-4ACF-AD78-BC7D2A05212A} */
	private String documentoEscrituraPublica;

	/** @modelguid {97E8141F-197F-4C59-95DB-B3E5F225E597} */
	private String nombreArchivo;
	
	/**
	 * @return
	 * @modelguid {77C70D47-9F83-40AC-B27E-D899E7771B56}
	 */
	public String getDocumentoEscrituraPublica() {
		return documentoEscrituraPublica;
	}

	/**
	 * @param string
	 * @modelguid {5F391B63-9613-4440-99BE-56C7B2C59E65}
	 */
	public void setDocumentoEscrituraPublica(String string) {
		documentoEscrituraPublica = string;
	}

	/**
	 * @return
	 * @modelguid {FED538A6-686F-4C21-AFE9-D504AE077AE4}
	 */
	public String getNombreArchivo() {
		return nombreArchivo;
	}

	/**
	 * @param string
	 * @modelguid {075BD3AD-B33D-4F4E-999C-32E7AF97620A}
	 */
	public void setNombreArchivo(String string) {
		nombreArchivo = string;
	}

}


