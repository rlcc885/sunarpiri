package gob.pe.sunarp.extranet.solicitud.inscripcion.bean;
/** @modelguid {4DA408E7-3071-4670-A8EB-6E842E508D4C} */
public class PersonaNatural extends Persona {

	private String codigoEstadoCivil;
	private String descripcionEstadoCivil;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String nombre;
	private String fechaNacimiento;
	private String codigoCargoOcupacion;
	private String descripcionCargoOcupacion;
	private String correoElectronico;
	private String fechaCargo;
	/**
	 * SE ADICIONAN LOS CAMPOS: 
	 * 
	 * - indicadorRepresentante, 
	 * - nombreConyuge, 
	 * - valorParticipacion,
	 * - porcentajeParticipacion,
	 * - numeroPartidaEmpresa
	 * - codigoTipoParticipantePNSUNAT
	 * 
	 * SAUL VASQUEZ
	 */

	private String indicadorRepresentante;
	private String nombreConyuge;
	private String valorParticipacion;
	private String porcentajeParticipacion;
	private String numeroPartidaEmpresa;
	private String descripcionProfesional;
	private String codigoTipoParticipantePNSUNAT;		

	/**
	 * @return
	 * @modelguid {71319ABC-7918-483C-9BBE-3B096769FF9E}
	 */
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	/**
	 * @return
	 * @modelguid {2D7B8CDF-C523-4F9A-B99D-288584689DE5}
	 */
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	/**
	 * @return
	 * @modelguid {A2BB0936-38BE-45D0-A620-9A8FFD21F8AA}
	 */
	public String getCodigoCargoOcupacion() {
		return codigoCargoOcupacion;
	}

	/**
	 * @return
	 * @modelguid {D58B3F2C-666B-4974-964B-644E8A957BD0}
	 */
	public String getCodigoEstadoCivil() {
		return codigoEstadoCivil;
	}

	/**
	 * @return
	 * @modelguid {2E884597-C6AC-407A-B83C-CD642F3F0220}
	 */
	public String getCorreoElectronico() {
		return correoElectronico;
	}

	/**
	 * @return
	 * @modelguid {6A9326A0-5309-464D-8034-CE0D0BDDB2AA}
	 */
	public String getDescripcionCargoOcupacion() {
		return descripcionCargoOcupacion;
	}

	/**
	 * @return
	 * @modelguid {F4CF210A-EE2E-4E08-A7EA-6172DAEE6857}
	 */
	public String getDescripcionEstadoCivil() {
		return descripcionEstadoCivil;
	}

	/**
	 * @return
	 * @modelguid {67B3BDE3-738B-4183-9CCF-F3BA19FEA5EC}
	 */
	public String getFechaCargo() {
		return fechaCargo;
	}

	/**
	 * @return
	 * @modelguid {54781A26-0944-4DE7-BFC5-4BE4647AA204}
	 */
	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	/**
	 * @return
	 * @modelguid {82E8CEC6-2081-48D5-B2E3-E103AA8745DD}
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param string
	 * @modelguid {FA4CA636-E519-4D32-B97D-044E6756DBBD}
	 */
	public void setApellidoMaterno(String string) {
		apellidoMaterno = string;
	}

	/**
	 * @param string
	 * @modelguid {CFE0D019-096A-4661-85EE-98DD318EF703}
	 */
	public void setApellidoPaterno(String string) {
		apellidoPaterno = string;
	}

	/**
	 * @param string
	 * @modelguid {F158E9B5-1C60-4682-8A5D-6A6D93B6F477}
	 */
	public void setCodigoCargoOcupacion(String string) {
		codigoCargoOcupacion = string;
	}

	/**
	 * @param string
	 * @modelguid {812FBB7D-813F-4D7F-A14A-C8FE5492214E}
	 */
	public void setCodigoEstadoCivil(String string) {
		codigoEstadoCivil = string;
	}

	/**
	 * @param string
	 * @modelguid {49DDF3B6-275B-479C-B493-322A3CCE3C8E}
	 */
	public void setCorreoElectronico(String string) {
		correoElectronico = string;
	}

	/**
	 * @param string
	 * @modelguid {DB888E06-2B1A-4759-B128-0B0AC9B755AA}
	 */
	public void setDescripcionCargoOcupacion(String string) {
		descripcionCargoOcupacion = string;
	}

	/**
	 * @param string
	 * @modelguid {B9D4BC99-B0F2-40EE-87DE-A7C002580AAB}
	 */
	public void setDescripcionEstadoCivil(String string) {
		descripcionEstadoCivil = string;
	}

	/**
	 * @param string
	 * @modelguid {A82CDD00-B82A-4180-A6BC-3C2B5EF0DBFB}
	 */
	public void setFechaCargo(String string) {
		fechaCargo = string;
	}

	/**
	 * @param string
	 * @modelguid {A97CDBF1-2AC3-483A-91F8-C8EE0F4C3197}
	 */
	public void setFechaNacimiento(String string) {
		fechaNacimiento = string;
	}

	/**
	 * @param string
	 * @modelguid {6B7E2C16-C315-46D0-93A8-A614814070F1}
	 */
	public void setNombre(String string) {
		nombre = string;
	}

	public String getIndicadorRepresentante() {
		return indicadorRepresentante;
	}

	public void setIndicadorRepresentante(String indicadorRepresentante) {
		this.indicadorRepresentante = indicadorRepresentante;
	}

	public String getNombreConyuge() {
		return nombreConyuge;
	}

	public void setNombreConyuge(String nombreConyuge) {
		this.nombreConyuge = nombreConyuge;
	}

	public String getNumeroPartidaEmpresa() {
		return numeroPartidaEmpresa;
	}

	public void setNumeroPartidaEmpresa(String numeroPartidaEmpresa) {
		this.numeroPartidaEmpresa = numeroPartidaEmpresa;
	}

	public String getPorcentajeParticipacion() {
		return porcentajeParticipacion;
	}

	public void setPorcentajeParticipacion(String porcentajeParticipacion) {
		this.porcentajeParticipacion = porcentajeParticipacion;
	}

	public String getValorParticipacion() {
		return valorParticipacion;
	}

	public void setValorParticipacion(String valorParticipacion) {
		this.valorParticipacion = valorParticipacion;
	}

	public String getDescripcionProfesional() {
		return descripcionProfesional;
	}

	public void setDescripcionProfesional(String descripcionProfesional) {
		this.descripcionProfesional = descripcionProfesional;
	}

	public String getCodigoTipoParticipantePNSUNAT() {
		return codigoTipoParticipantePNSUNAT;
	}

	public void setCodigoTipoParticipantePNSUNAT(
			String codigoTipoParticipantePNSUNAT) {
		this.codigoTipoParticipantePNSUNAT = codigoTipoParticipantePNSUNAT;
	}

}

