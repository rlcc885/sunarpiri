package gob.pe.sunarp.extranet.solicitud.inscripcion.bean;
import gob.pe.sunarp.extranet.common.SunarpBean;
import gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.Capital;
import gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.EscrituraPublica;
import gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.ReservaMercantil;

/** @modelguid {02AC0E50-9997-46F1-892F-7088E98587CA} */
public class SolicitudInscripcion extends SunarpBean{
	
	//Inicio:mgarate:21/11/2007
	private String ipRemota;
	//Fin:mgarate
	
	/** @modelguid {026ACE81-1F3F-47AF-BB4A-EF2863EB2027} */
	private String numeroHoja;

	/** @modelguid {BB45D88B-8349-42E7-91EF-5B531F725F9D} */
	private String anho;

	/** @modelguid {0E68FA25-5CC6-4E24-B0C8-C2DF61CE789D} */
	private String codigoArea;

	/** @modelguid {87679EB5-A0BD-4F37-AFC8-50CF645FD76F} */
	private String descripcionArea;

	/** @modelguid {15B48B92-7272-47D4-AC56-FDA37BA9EF1B} */
	private String codigoActo;

	/** @modelguid {D44628C4-37EF-49FA-9576-55E0079D6089} */
	private String descripcionActo;

	/** @modelguid {B360AAF3-F3F2-4F6C-BF20-FD59D786E9D1} */
	private String codigoLibro;

	/** @modelguid {9CD262A9-F24D-4858-BE6C-EE93C80B56DB} */
	private String descripcionLibro;

	/** @modelguid {4FDA889F-A60B-4946-8803-330EE42EAF78} */
	private String codigoZonaRegistral;

	/** @modelguid {0E507BF6-0733-4570-BBB5-63F4DDB3D148} */
	private String codigoOficinaRegistral;
	
	/** @modelguid {32CA6829-F202-4F00-BB7D-BE33798725AD} */
	private String descripcionOficinaRegistral;

	/** @modelguid {9C79F816-B36E-4E7B-ACB0-30E4D21FC6B0} */
	private String cuo;

	/** @modelguid {6B0CC498-7EA1-4EB5-9083-06AE2F376AB1} */
	private String codigoServicio;

	/** @modelguid {318B26F7-6BFA-46F7-917C-6C55D023C349} */
	private String descripcionServicio;

	/** @modelguid {C9B022B6-80B2-4B77-A4E5-3A5C9AC692BC} */
	private String secuencialOperacion;

	/** @modelguid {6103B4A3-7F8E-413D-BF32-73218323D007} */
	private Presentante presentante;

	/** @modelguid {80271EE2-12F9-4817-87FA-1C46087FEC7D} */
	private PersonaJuridica personaJuridica;

	/** @modelguid {35C5AB91-A293-4AED-AA2C-1F3F8AE15C5E} */
	private DatosPago datosPago;

	/** @modelguid {F2F0B635-EB1B-44D6-A619-8E71D98A9CC8} */
	private java.util.ArrayList participantesPersonaNatural;

	/** @modelguid {AFCFFDC3-0504-4BB4-BCE5-0C59054654B0} */
	private EscrituraPublica escrituraPublica;

	/** @modelguid {DE7E51BE-D450-491D-A3C2-66584BB9C54B} */
	private Capital capital;

	/** @modelguid {BF5F3CB4-B444-4E99-9D4A-1F3E1F811AEC} */
	private ReservaMercantil reservaMercantil;

	/**
	 * 
	 * @modelguid {CBA85252-F9E2-40D6-82B6-57AEE7B95184}
	 */
	private java.util.ArrayList participantesPersonaJuridica;

	/**
	 * 
	 * @modelguid {42F15248-3C0F-434F-AD5D-9EA103165DF4}
	 */
	private java.util.ArrayList instrumentoPublico;

	/**
	 * 
	 * @modelguid {C80CC3F1-E3EA-4AFD-BF3A-986DE7B0413D}
	 */
	private java.util.ArrayList partidas;

	/**
	 * 
	 * @modelguid {8AD8280B-1ACC-4B95-A9BE-D4D1DB14C0F1}
	 */
	private java.util.ArrayList vehiculos;

	/** @modelguid {3C8B64CB-5A32-4229-99B1-7105D151F5BD} */
	private String fechaSolicitud;

	/** @modelguid {A2AB0569-348C-4CAE-9E77-EC439022EA79} */
	private String codigoUsuario;

	/**
	 * @return
	 * @modelguid {C4079713-5B4E-4884-BCA9-AF007FE18FAD}
	 */
	public Capital getCapital() {
		return capital;
	}

	/**
	 * @return
	 * @modelguid {FF241CA1-A012-4CBE-9DA0-668E38D6A12B}
	 */
	public String getAnho() {
		return anho;
	}

	/**
	 * @return
	 * @modelguid {F3E69960-9B46-42E3-B348-3D98C06C4BD3}
	 */
	public String getCodigoActo() {
		return codigoActo;
	}

	/**
	 * @return
	 * @modelguid {B1B8EDC8-9F11-411A-9917-4123B4C1A208}
	 */
	public String getCodigoArea() {
		return codigoArea;
	}

	/**
	 * @return
	 * @modelguid {1C25EA10-F406-40BB-8FFD-7347040792BF}
	 */
	public String getCodigoLibro() {
		return codigoLibro;
	}

	/**
	 * @return
	 * @modelguid {759B0DF7-D2DB-4CFB-ABD4-B608F49539A3}
	 */
	public String getCodigoOficinaRegistral() {
		return codigoOficinaRegistral;
	}

	/**
	 * @return
	 * @modelguid {11BA59FA-8E92-47C3-A809-036810AF9AFF}
	 */
	public String getCodigoServicio() {
		return codigoServicio;
	}

	/**
	 * @return
	 * @modelguid {CB7F293E-D1EB-46CD-81D0-CF5E109571C2}
	 */
	public String getCodigoZonaRegistral() {
		return codigoZonaRegistral;
	}

	/**
	 * @return
	 * @modelguid {EF02ADDF-D008-4458-BDA1-988243DB52D9}
	 */
	public String getCuo() {
		return cuo;
	}

	/**
	 * @return
	 * @modelguid {98C466BD-7FFE-4A32-96A3-8EA9C0ED356F}
	 */
	public DatosPago getDatosPago() {
		return datosPago;
	}

	/**
	 * @return
	 * @modelguid {0219C1CB-AC1D-4627-96EF-D44CFB012307}
	 */
	public String getDescripcionActo() {
		return descripcionActo;
	}

	/**
	 * @return
	 * @modelguid {E4D36EFD-B0F1-470D-8E0E-8FDCE6D2249F}
	 */
	public String getDescripcionArea() {
		return descripcionArea;
	}

	/**
	 * @return
	 * @modelguid {D4A187A7-7152-401F-A48D-587D78379BD4}
	 */
	public String getDescripcionLibro() {
		return descripcionLibro;
	}

	/**
	 * @return
	 * @modelguid {2BD211EE-19F1-4338-AADD-29C07AD00D88}
	 */
	public String getDescripcionServicio() {
		return descripcionServicio;
	}

	/**
	 * @return
	 * @modelguid {801D9B1B-B842-4073-BE64-674404972421}
	 */
	public EscrituraPublica getEscrituraPublica() {
		return escrituraPublica;
	}

	/**
	 * @return
	 * @modelguid {40117F76-BF0C-4141-8C80-0BFD820ACB04}
	 */
	public String getNumeroHoja() {
		return numeroHoja;
	}

	/**
	 * @return
	 * @modelguid {545048A2-4F8C-4767-93E7-F7DBF44CACD5}
	 */
	public java.util.ArrayList getParticipantesPersonaNatural() {
		return participantesPersonaNatural;
	}

	/**
	 * @return
	 * @modelguid {21EE783E-796C-405A-8E7A-B9E779B469A2}
	 */
	public PersonaJuridica getPersonaJuridica() {
		return personaJuridica;
	}

	/**
	 * @return
	 * @modelguid {82143471-D1EE-420B-A80F-485FEC82AE52}
	 */
	public Presentante getPresentante() {
		return presentante;
	}

	/**
	 * @return
	 * @modelguid {A5CA0715-484A-4AD8-9C12-96089FDF874F}
	 */
	public ReservaMercantil getReservaMercantil() {
		return reservaMercantil;
	}

	/**
	 * @return
	 * @modelguid {1588D79C-68F2-4785-84F9-84FC8A9E9D6C}
	 */
	public String getSecuencialOperacion() {
		return secuencialOperacion;
	}

	/**
	 * @param accion
	 * @modelguid {6B0C281D-7B0A-4023-B8F4-AB988B830874}
	 */
	public void setCapital(Capital capital) {
		this.capital = capital;
	}

	/**
	 * @param string
	 * @modelguid {71011C27-4A10-43F6-BC4B-84CCDEA5D7D9}
	 */
	public void setAnho(String anho) {
		this.anho = anho;
	}

	/**
	 * @param string
	 * @modelguid {1F8AE07B-B9DA-4A88-9ADE-371AEF54B44A}
	 */
	public void setCodigoActo(String codigoActo) {
		this.codigoActo = codigoActo;
	}

	/**
	 * @param string
	 * @modelguid {8F4A0588-73F8-413D-ABF3-405890790645}
	 */
	public void setCodigoArea(String codigoArea) {
		this.codigoArea = codigoArea;
	}

	/**
	 * @param string
	 * @modelguid {EC98BA28-1BDA-4006-A365-957CCA065F4C}
	 */
	public void setCodigoLibro(String codigoLibro) {
		this.codigoLibro = codigoLibro;
	}

	/**
	 * @param string
	 * @modelguid {F8151B6C-EFE8-4234-B136-CD6AA16381E3}
	 */
	public void setCodigoOficinaRegistral(String codigoOficinaRegistral) {
		this.codigoOficinaRegistral = codigoOficinaRegistral;
	}

	/**
	 * @param string
	 * @modelguid {FA9C7C3D-C0F1-4513-A058-594F238861AD}
	 */
	public void setCodigoServicio(String codigoServicio) {
		this.codigoServicio = codigoServicio;
	}

	/**
	 * @param string
	 * @modelguid {79C59DD8-5BD7-45E7-8FFD-ADE8D0272B2F}
	 */
	public void setCodigoZonaRegistral(String codigoZonaRegistral) {
		this.codigoZonaRegistral = codigoZonaRegistral;
	}

	/**
	 * @param string
	 * @modelguid {B110B65A-A713-4703-8FAD-9692FFF6AD5C}
	 */
	public void setCuo(String cuo) {
		this.cuo = cuo;
	}

	/**
	 * @param pago
	 * @modelguid {5AFC2059-022F-4476-9EAF-D53C19717FF9}
	 */
	public void setDatosPago(DatosPago datosPago) {
		this.datosPago = datosPago;
	}

	/**
	 * @param string
	 * @modelguid {E91D8081-35F8-4AFE-874A-CC6ADF988DE6}
	 */
	public void setDescripcionActo(String descripcionActo) {
		this.descripcionActo = descripcionActo;
	}

	/**
	 * @param string
	 * @modelguid {2AB94135-0E83-40DF-B686-702765A40281}
	 */
	public void setDescripcionArea(String descripcionArea) {
		this.descripcionArea = descripcionArea;
	}

	/**
	 * @param string
	 * @modelguid {639B01F9-9D64-4D25-8BA0-310FAB9B5A3B}
	 */
	public void setDescripcionLibro(String descripcionLibro) {
		this.descripcionLibro = descripcionLibro;
	}

	/**
	 * @param string
	 * @modelguid {81D674CD-274F-4BA4-BB65-063FD218C370}
	 */
	public void setDescripcionServicio(String descripcionServicio) {
		this.descripcionServicio = descripcionServicio;
	}

	/**
	 * @param publica
	 * @modelguid {3B77059E-22FD-46CD-991F-900025C9EB5D}
	 */
	public void setEscrituraPublica(EscrituraPublica escrituraPublica) {
		this.escrituraPublica = escrituraPublica;
	}

	/**
	 * @param string
	 * @modelguid {08E1A598-7F6B-4D08-9710-D939B830437F}
	 */
	public void setNumeroHoja(String numeroHoja) {
		this.numeroHoja = numeroHoja;
	}

	/**
	 * @param list
	 * @modelguid {2A991C35-2740-4DE7-AF1F-D2EA1478ED26}
	 */
	public void setParticipantesPersonaNatural(java.util.ArrayList participantesPersonaNatural) {
		this.participantesPersonaNatural = participantesPersonaNatural;
	}

	/**
	 * @param juridica
	 * @modelguid {65FAA5D7-2EBC-4871-B55D-78C81001C0B3}
	 */
	public void setPersonaJuridica(PersonaJuridica personaJuridica) {
		this.personaJuridica = personaJuridica;
	}

	/**
	 * @param presentante
	 * @modelguid {7217C353-EECF-4D52-9171-92F705347550}
	 */
	public void setPresentante(Presentante presentante) {
		this.presentante = presentante;
	}

	/**
	 * @param mercantil
	 * @modelguid {F2AACB17-8C7F-4470-836F-FFF1ED8EF0A8}
	 */
	public void setReservaMercantil(ReservaMercantil reservaMercantil) {
		this.reservaMercantil = reservaMercantil;
	}

	/**
	 * @param string
	 * @modelguid {706C7900-55E7-46A0-8103-CE21709E0EB4}
	 */
	public void setSecuencialOperacion(String secuencialOperacion) {
		this.secuencialOperacion = secuencialOperacion;
	}

	/**
	 * @return
	 * @modelguid {F556CC1D-41AD-4172-8FFF-1EF716F93DFF}
	 */
	public java.util.ArrayList getParticipantesPersonaJuridica() {
		return participantesPersonaJuridica;
	}

	/**
	 * @param list
	 * @modelguid {767CD2A6-5BA8-428F-931F-0FF9D9222FD0}
	 */
	public void setParticipantesPersonaJuridica(java.util.ArrayList participantesPersonaJuridica) {
		this.participantesPersonaJuridica = participantesPersonaJuridica;
	}

	/**
	 * @return
	 * @modelguid {779C15B4-4ADF-4FED-9BBF-9DA6AD9422C8}
	 */
	public java.util.ArrayList getInstrumentoPublico() {
		return instrumentoPublico;
	}

	/**
	 * @param list
	 * @modelguid {96BB5AC5-536A-4B64-8313-0A9790CA92D7}
	 */
	public void setInstrumentoPublico(java.util.ArrayList instrumentoPublico) {
		this.instrumentoPublico = instrumentoPublico;
	}

	/**
	 * @return
	 * @modelguid {4B272CC8-5BB0-4F19-8595-BFFD66072CA2}
	 */
	public java.util.ArrayList getPartidas() {
		return partidas;
	}

	/**
	 * @param list
	 * @modelguid {5D4354F1-30C7-404E-BF7C-A9488342D10C}
	 */
	public void setPartidas(java.util.ArrayList partidas) {
		this.partidas = partidas;
	}

	/**
	 * @return
	 * @modelguid {7CC3D57D-E774-4784-A457-2D671517D09F}
	 */
	public java.util.ArrayList getVehiculos() {
		return vehiculos;
	}

	/**
	 * @param list
	 * @modelguid {7AF4AC2A-8BE7-4E11-8121-0C09CB496307}
	 */
	public void setVehiculos(java.util.ArrayList vehiculos) {
		this.vehiculos = vehiculos;
	}

	/**
	 * @return
	 * @modelguid {13A2149A-4932-44D1-B913-B8D8B2D59E3B}
	 */
	public String getFechaSolicitud() {
		return fechaSolicitud;
	}

	/**
	 * @param string
	 * @modelguid {0A205B76-05E5-43CC-95CF-04C736D64140}
	 */
	public void setFechaSolicitud(String fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	/**
	 * @return
	 * @modelguid {A2BFFDAD-60AB-40A2-9CA6-370DC4E00183}
	 */
	public String getCodigoUsuario() {
		return codigoUsuario;
	}

	/**
	 * @param string
	 * @modelguid {53762971-C1BF-4CD7-BB79-2EE58E0D2DB4}
	 */
	public void setCodigoUsuario(String codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}

	/**
	 * @return
	 * @modelguid {9BFE6A1E-92CF-4558-B131-84C0C415FE75}
	 */
	public String getDescripcionOficinaRegistral() {
		return descripcionOficinaRegistral;
	}

	/**
	 * @param string
	 * @modelguid {9E0D36DC-9EF1-4DBA-A810-0BC54F8630B9}
	 */
	public void setDescripcionOficinaRegistral(String string) {
		descripcionOficinaRegistral = string;
	}
	//Inicio:mgarate:21/11/2007
	public String getIpRemota() {
		return ipRemota;
	}

	public void setIpRemota(String ipRemota) {
		this.ipRemota = ipRemota;
	}
	//Fin:mgarate
}

