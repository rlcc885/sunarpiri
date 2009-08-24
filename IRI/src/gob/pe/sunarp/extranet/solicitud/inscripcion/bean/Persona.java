package gob.pe.sunarp.extranet.solicitud.inscripcion.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

/** @modelguid {E5642A98-FE6D-469C-A3D6-778352C528E2} */
public class Persona extends SunarpBean{
	
	private String codigoTipoDocumento;
	private String descripcionTipoDocumento;
	private String numeroDocumento;
	private String codigoTipoParticipante;
	private String descripcionTipoParticipante;
	private String codigoNacionalidad;
	private String descripcionNacionalidad;
	private String codigoDepartamento;
	private String descripcionDepartamento;
	private String codigoProvincia;
	private String descripcionProvincia;
	private String codigoDistrito;
	private String descripcionDistrito;
	private String direccion;
	private String codigoPostal;
	private String codigoTipoVia;
	private String descripcionTipoVia;

	private String codigoPais;
	
	private String descripcionPais;

	/**
	 * @return
	 * @modelguid {DD177904-F0AB-4899-BA0F-5EF92B297BCA}
	 */
	public String getCodigoNacionalidad() {
		return codigoNacionalidad;
	}

	/**
	 * @return
	 * @modelguid {624D13C3-2212-486D-A613-171179539066}
	 */
	public String getCodigoTipoDocumento() {
		return codigoTipoDocumento;
	}

	/**
	 * @return
	 * @modelguid {275CFEDB-E0DB-4C94-9477-8628E99DC7F4}
	 */
	public String getCodigoTipoParticipante() {
		return codigoTipoParticipante;
	}

	/**
	 * @return
	 * @modelguid {BDD84264-C5FA-4CC7-820B-79A4AFDC15FC}
	 */
	public String getDescripcionNacionalidad() {
		return descripcionNacionalidad;
	}

	/**
	 * @return
	 * @modelguid {5DCEAF10-6E17-4AA9-99EF-00D363FBD768}
	 */
	public String getDescripcionTipoDocumento() {
		return descripcionTipoDocumento;
	}

	/**
	 * @return
	 * @modelguid {9F24B3B2-D5D4-4D86-97CA-F0B1EFC6BF8C}
	 */
	public String getDescripcionTipoParticipante() {
		return descripcionTipoParticipante;
	}

	/**
	 * @return
	 * @modelguid {2F6F8228-2976-4926-8BBB-46381F8D6F38}
	 */
	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	/**
	 * @param string
	 * @modelguid {B51C4D19-AFE0-4675-99B3-885E8F9C2C77}
	 */
	public void setCodigoNacionalidad(String string) {
		codigoNacionalidad = string;
	}

	/**
	 * @param string
	 * @modelguid {CE71C49D-FC7B-4557-B0EF-B4FA7E48978D}
	 */
	public void setCodigoTipoDocumento(String string) {
		codigoTipoDocumento = string;
	}

	/**
	 * @param string
	 * @modelguid {597455F5-6078-48D5-B214-3AFDD03E017F}
	 */
	public void setCodigoTipoParticipante(String string) {
		codigoTipoParticipante = string;
	}

	/**
	 * @param string
	 * @modelguid {28FB3E48-5687-47BC-830F-B8233662961E}
	 */
	public void setDescripcionNacionalidad(String string) {
		descripcionNacionalidad = string;
	}

	/**
	 * @param string
	 * @modelguid {6E245BB1-E681-4137-B780-A1E0AA991788}
	 */
	public void setDescripcionTipoDocumento(String string) {
		descripcionTipoDocumento = string;
	}

	/**
	 * @param string
	 * @modelguid {414AD31E-1B50-45B4-99AE-BC613CBE7A55}
	 */
	public void setDescripcionTipoParticipante(String string) {
		descripcionTipoParticipante = string;
	}

	/**
	 * @param string
	 * @modelguid {9F2B9F4D-22E8-4DAF-A7DD-E3020FDE8368}
	 */
	public void setNumeroDocumento(String string) {
		numeroDocumento = string;
	}

	/**
	 * @return
	 * @modelguid {E7B48258-9FDB-4C04-83BC-ABFE140C4638}
	 */
	public String getCodigoDepartamento() {
		return codigoDepartamento;
	}

	/**
	 * @return
	 * @modelguid {E5DB7A8C-897F-4D08-9246-2FCF5ECDA289}
	 */
	public String getCodigoDistrito() {
		return codigoDistrito;
	}

	/**
	 * @return
	 * @modelguid {8B3D37D5-BA52-47E8-9109-084027ED3C42}
	 */
	public String getCodigoPostal() {
		return codigoPostal;
	}

	/**
	 * @return
	 * @modelguid {F4EF66A9-9820-481B-92DB-F50AB022CB3C}
	 */
	public String getCodigoProvincia() {
		return codigoProvincia;
	}

	/**
	 * @return
	 * @modelguid {4834C132-9193-44F6-A16F-7EC1A64D66CF}
	 */
	public String getDescripcionDepartamento() {
		return descripcionDepartamento;
	}

	/**
	 * @return
	 * @modelguid {A2D20EC0-81A0-4BE7-8658-29BBA5D64C69}
	 */
	public String getDescripcionDistrito() {
		return descripcionDistrito;
	}

	/**
	 * @return
	 * @modelguid {0A8AC7D8-ED93-46EA-B916-3DEE2F960681}
	 */
	public String getDescripcionProvincia() {
		return descripcionProvincia;
	}

	/**
	 * @return
	 * @modelguid {3839CD57-F546-4A31-BDE6-5218EEAA67B6}
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * @param string
	 * @modelguid {D550C2AE-8D1D-4043-99D7-F36D4482AB66}
	 */
	public void setCodigoDepartamento(String string) {
		codigoDepartamento = string;
	}

	/**
	 * @param string
	 * @modelguid {2FA98726-0ED6-4CE3-9785-6627DA688F77}
	 */
	public void setCodigoDistrito(String string) {
		codigoDistrito = string;
	}

	/**
	 * @param string
	 * @modelguid {54B2DC2D-45FD-4349-8F30-E602C840FC7A}
	 */
	public void setCodigoPostal(String string) {
		codigoPostal = string;
	}

	/**
	 * @param string
	 * @modelguid {B069E5A8-5BA9-4D1E-949B-9456E33FBA82}
	 */
	public void setCodigoProvincia(String string) {
		codigoProvincia = string;
	}

	/**
	 * @param string
	 * @modelguid {852BB875-2C96-4D9D-89D8-F66542D135AB}
	 */
	public void setDescripcionDepartamento(String string) {
		descripcionDepartamento = string;
	}

	/**
	 * @param string
	 * @modelguid {E55B0DB6-3EC8-40C4-9072-2E15483941F6}
	 */
	public void setDescripcionDistrito(String string) {
		descripcionDistrito = string;
	}

	/**
	 * @param string
	 * @modelguid {B7078995-0409-4905-A2EC-F4C3AE1AF89F}
	 */
	public void setDescripcionProvincia(String string) {
		descripcionProvincia = string;
	}

	/**
	 * @param string
	 * @modelguid {8EE4EAF5-F3E1-4BD0-9177-E0FEA1675E48}
	 */
	public void setDireccion(String string) {
		direccion = string;
	}

	/**
	 * @return
	 * @modelguid {2824F599-0732-45AD-B78D-9368AE2F5250}
	 */
	public String getCodigoTipoVia() {
		return codigoTipoVia;
	}

	/**
	 * @return
	 * @modelguid {3EDC266D-1D11-4D74-A3BC-E9F23FC13807}
	 */
	public String getDescripcionTipoVia() {
		return descripcionTipoVia;
	}

	/**
	 * @param string
	 * @modelguid {186727D3-1D36-4718-AC28-D5C12D1C7423}
	 */
	public void setCodigoTipoVia(String string) {
		codigoTipoVia = string;
	}

	/**
	 * @param string
	 * @modelguid {3FF22F2C-47D3-4184-B371-C47ECF83551F}
	 */
	public void setDescripcionTipoVia(String string) {
		descripcionTipoVia = string;
	}

	/**
	 * Gets the codigoPais
	 * @return Returns a String
	 */
	public String getCodigoPais() {
		return codigoPais;
	}
	/**
	 * Sets the codigoPais
	 * @param codigoPais The codigoPais to set
	 */
	public void setCodigoPais(String codigoPais) {
		this.codigoPais = codigoPais;
	}

	/**
	 * Gets the descripcionPais
	 * @return Returns a String
	 */
	public String getDescripcionPais() {
		return descripcionPais;
	}
	/**
	 * Sets the descripcionPais
	 * @param descripcionPais The descripcionPais to set
	 */
	public void setDescripcionPais(String descripcionPais) {
		this.descripcionPais = descripcionPais;
	}

}

