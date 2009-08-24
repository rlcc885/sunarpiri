package gob.pe.sunarp.extranet.solicitud.inscripcion.bean;
/** @modelguid {DB267993-2308-4907-BA48-F70427D998E9} */
public class Presentante extends PersonaNatural {
	/** @modelguid {5D63D967-4791-4324-B255-DC2EA981D412} */
	private String codigoPresentante;

	/** @modelguid {FDCF0D01-7BDE-4A68-95FB-286AF98A4F97} */
	private String codigoInstitucion;

	/** @modelguid {92FD1143-D80A-47DE-86E0-B8DF184A92D9} */
	private String descripcionInstitucion;



	/**
	 * @return
	 * @modelguid {D05EA831-9B8D-40CA-82FB-8676C3AC0B3C}
	 */
	public String getCodigoInstitucion() {
		return codigoInstitucion;
	}

	/**
	 * @return
	 * @modelguid {2410822F-818D-481D-993F-7D14311A8187}
	 */
	public String getCodigoPresentante() {
		return codigoPresentante;
	}

	/**
	 * @return
	 * @modelguid {AE394664-169D-441A-B7DC-6FB9962905BB}
	 */
	public String getDescripcionInstitucion() {
		return descripcionInstitucion;
	}

	/**
	 * @param string
	 * @modelguid {DB292CBE-3293-4221-B287-17AB1F3C4B88}
	 */
	public void setCodigoInstitucion(String string) {
		codigoInstitucion = string;
	}

	/**
	 * @param string
	 * @modelguid {6E4BE886-88AA-4B53-96CF-BA0C3ACB233A}
	 */
	public void setCodigoPresentante(String string) {
		codigoPresentante = string;
	}

	/**
	 * @param string
	 * @modelguid {0D5B3255-5D24-4FDD-9B21-65EF89686E9B}
	 */
	public void setDescripcionInstitucion(String string) {
		descripcionInstitucion = string;
	}

}

