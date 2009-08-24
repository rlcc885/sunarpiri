package gob.pe.sunarp.extranet.solicitud.inscripcion.bean;
/** @modelguid {866B0D13-D130-47AE-BEF1-CEF15651B3F3} */
public class PersonaJuridica extends Persona {

	private String razonSocial;
	private String siglas;
	private String codigoTipoSociedad;
	private String descripcionTipoSociedad;
	private String codigoTipoSociedadAnonima;
	private String descripcionTipoSociedadAnonima;
	private String indicadorRUC;
	private String numeroPartida;
	private String codigoZonaRegistral;
	private String codigoOficinaRegistral;

	/**
	 * SE ADICIONAN LOS CAMPOS: 
	 * 
	 * - indicadorRepresentante, 
	 * - codigoTipoParticipantePJSUNAT
	 * 
	 * SAUL VASQUEZ - AVATAR GLOBAL
	 */

	private String indicadorRepresentante;
	private String codigoTipoParticipantePJSUNAT;

	/**
	 * @return
	 * @modelguid {AC9F08C3-3234-44ED-A046-A0EAF1BA09FD}
	 */
	public String getCodigoTipoSociedad() {
		return codigoTipoSociedad;
	}

	/**
	 * @return
	 * @modelguid {7C1CE26A-17B6-4F45-A56E-DFAF50792D40}
	 */
	public String getCodigoTipoSociedadAnonima() {
		return codigoTipoSociedadAnonima;
	}

	/**
	 * @return
	 * @modelguid {441C024B-2264-4020-A32A-9529859FAF69}
	 */
	public String getDescripcionTipoSociedad() {
		return descripcionTipoSociedad;
	}

	/**
	 * @return
	 * @modelguid {14EB5630-56C9-4ACD-A14C-F00710B799AA}
	 */
	public String getDescripcionTipoSociedadAnonima() {
		return descripcionTipoSociedadAnonima;
	}

	/**
	 * @return
	 * @modelguid {298A8B82-3CE1-4A26-8CD8-55BB927D44FA}
	 */
	public String getRazonSocial() {
		return razonSocial;
	}

	/**
	 * @return
	 * @modelguid {2A19C119-0C41-4A04-940D-F9615515F13B}
	 */
	public String getSiglas() {
		return siglas;
	}

	/**
	 * @param string
	 * @modelguid {B621D1AF-E727-47D7-BC02-F1D036F3A4B4}
	 */
	public void setCodigoTipoSociedad(String string) {
		codigoTipoSociedad = string;
	}

	/**
	 * @param string
	 * @modelguid {4D4A789A-40B2-4CF7-B355-7E57BE1C8CD0}
	 */
	public void setCodigoTipoSociedadAnonima(String string) {
		codigoTipoSociedadAnonima = string;
	}

	/**
	 * @param string
	 * @modelguid {2522692F-B4CC-4C80-BF64-14CB2BA8657B}
	 */
	public void setDescripcionTipoSociedad(String string) {
		descripcionTipoSociedad = string;
	}

	/**
	 * @param string
	 * @modelguid {B3585B34-5F39-4333-A3E5-DD84C0EF2381}
	 */
	public void setDescripcionTipoSociedadAnonima(String string) {
		descripcionTipoSociedadAnonima = string;
	}

	/**
	 * @param string
	 * @modelguid {5B860992-71E2-4F4A-BC91-BAAC4358D205}
	 */
	public void setRazonSocial(String string) {
		razonSocial = string;
	}

	/**
	 * @param string
	 * @modelguid {A900E92B-EF1B-46B8-AD81-60D2DEF4F0C8}
	 */
	public void setSiglas(String string) {
		siglas = string;
	}

	/**
	 * @return
	 * @modelguid {46023426-C53F-4BF9-A9E1-6630B5AC36AC}
	 */
	public String getCodigoOficinaRegistral() {
		return codigoOficinaRegistral;
	}

	/**
	 * @return
	 * @modelguid {E22C7564-94A9-416E-A0E3-3DAA456342A8}
	 */
	public String getCodigoZonaRegistral() {
		return codigoZonaRegistral;
	}

	/**
	 * @return
	 * @modelguid {816F0C80-D5B8-4E37-BECC-B9DF81B861CF}
	 */
	public String getIndicadorRUC() {
		return indicadorRUC;
	}

	/**
	 * @return
	 * @modelguid {013AD071-AB48-42B9-9069-B98886F5872D}
	 */
	public String getNumeroPartida() {
		return numeroPartida;
	}

	/**
	 * @param string
	 * @modelguid {00448EDB-0B9C-40BA-8BA9-405F9C7ABFDF}
	 */
	public void setCodigoOficinaRegistral(String string) {
		codigoOficinaRegistral = string;
	}

	/**
	 * @param string
	 * @modelguid {4FBD577D-B813-4677-888F-6B2C80676CD5}
	 */
	public void setCodigoZonaRegistral(String string) {
		codigoZonaRegistral = string;
	}

	/**
	 * @param string
	 * @modelguid {8BBEC8A9-E35D-4193-B95D-5B1E76FB9DF9}
	 */
	public void setIndicadorRUC(String string) {
		indicadorRUC = string;
	}

	/**
	 * @param string
	 * @modelguid {AA111347-44E9-4922-96BC-C1CD00A2F0BC}
	 */
	public void setNumeroPartida(String string) {
		numeroPartida = string;
	}

	public String getIndicadorRepresentante() {
		return indicadorRepresentante;
	}

	public void setIndicadorRepresentante(String indicadorRepresentante) {
		this.indicadorRepresentante = indicadorRepresentante;
	}

	public String getCodigoTipoParticipantePJSUNAT() {
		return codigoTipoParticipantePJSUNAT;
	}

	public void setCodigoTipoParticipantePJSUNAT(
			String codigoTipoParticipantePJSUNAT) {
		this.codigoTipoParticipantePJSUNAT = codigoTipoParticipantePJSUNAT;
	}

}

