/**
 * 
 */
package gob.pe.sunarp.extranet.solicitud.denominacion.beans;

import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago;
import gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaJuridica;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author jbugarin
 *
 */
public class Denominacion implements Serializable {

	private static final long serialVersionUID = 1L;
	private String indicadorSeleccion;
	private String descSeleccion;
	private String indicadorDenominacion;
	private String servicio;
	private String descServicio;
	private Double monto;
	private ArrayList listaDenominaciones;
	private Juridica personaJuridica;
	private Presentante presentante;
	private String numeroHoja;
	private String anio;
	private ArrayList listaParticipantes;
	private String codigoUsuario;
	private String fechaProceso;
	private String codigoArea;
	private String descripcionArea;
	private String coRegiPres;
	private String coOficRegiPres;
	private DatosPago datosPago;
	private String codigoActo;
	private String descActo;
	private String codigoLibro;
	private String descLibro;
	private String cuo;
	private String persAutoizadaPres;
	private String indicadorRegistrado;
	private UsuarioBean usuario;
	private String numeroTitulo;
	private String refNumTitu;
	private String anioTitu;
	private String fechaPresTitu;
	//para motivos de WS
	private RazonSocial razonSocial;
	private Participantes participantes;
	//para el seguimiento de hojas de presentacion generado su titulo
	private String mensaje;
	private String estado;
	private String descOficina;
	

	public String getDescOficina() {
		return descOficina;
	}

	public void setDescOficina(String descOficina) {
		this.descOficina = descOficina;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	/**
	 * @return el participantes
	 */
	public Participantes getParticipantes() {
		return participantes;
	}

	/**
	 * @param participantes el participantes a establecer
	 */
	public void setParticipantes(Participantes participantes) {
		this.participantes = participantes;
	}

	public String getRefNumTitu() {
		return refNumTitu;
	}

	public void setRefNumTitu(String refNumTitu) {
		this.refNumTitu = refNumTitu;
	}

	public UsuarioBean getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioBean usuario) {
		this.usuario = usuario;
	}

	public String getIndicadorRegistrado() {
		return indicadorRegistrado;
	}

	public void setIndicadorRegistrado(String indicadorRegistrado) {
		this.indicadorRegistrado = indicadorRegistrado;
	}

	public String getPersAutoizadaPres() {
		return persAutoizadaPres;
	}

	public void setPersAutoizadaPres(String persAutoizadaPres) {
		this.persAutoizadaPres = persAutoizadaPres;
	}

	public String getCuo() {
		return cuo;
	}

	public void setCuo(String cuo) {
		this.cuo = cuo;
	}

	public String getCodigoActo() {
		return codigoActo;
	}

	public void setCodigoActo(String codigoActo) {
		this.codigoActo = codigoActo;
	}

	public String getCodigoLibro() {
		return codigoLibro;
	}

	public void setCodigoLibro(String codigoLibro) {
		this.codigoLibro = codigoLibro;
	}

	public String getDescActo() {
		return descActo;
	}

	public void setDescActo(String descActo) {
		this.descActo = descActo;
	}

	public String getDescLibro() {
		return descLibro;
	}

	public void setDescLibro(String descLibro) {
		this.descLibro = descLibro;
	}

	public DatosPago getDatosPago() {
		return datosPago;
	}

	public void setDatosPago(DatosPago datosPago) {
		this.datosPago = datosPago;
	}

	public String getCodigoArea() {
		return codigoArea;
	}

	public void setCodigoArea(String codigoArea) {
		this.codigoArea = codigoArea;
	}

	public String getCoOficRegiPres() {
		return coOficRegiPres;
	}

	public void setCoOficRegiPres(String coOficRegiPres) {
		this.coOficRegiPres = coOficRegiPres;
	}

	public String getCoRegiPres() {
		return coRegiPres;
	}

	public void setCoRegiPres(String coRegiPres) {
		this.coRegiPres = coRegiPres;
	}

	public String getDescripcionArea() {
		return descripcionArea;
	}

	public void setDescripcionArea(String descripcionArea) {
		this.descripcionArea = descripcionArea;
	}

	public String getFechaProceso() {
		return fechaProceso;
	}

	public void setFechaProceso(String fechaProceso) {
		this.fechaProceso = fechaProceso;
	}

	public String getCodigoUsuario() {
		return codigoUsuario;
	}

	public void setCodigoUsuario(String codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}

	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public String getNumeroHoja() {
		return numeroHoja;
	}

	public void setNumeroHoja(String numeroHoja) {
		this.numeroHoja = numeroHoja;
	}

	public Presentante getPresentante() {
		return presentante;
	}

	public void setPresentante(Presentante presentante) {
		this.presentante = presentante;
	}

	public Juridica getPersonaJuridica() {
		return personaJuridica;
	}

	public void setPersonaJuridica(Juridica personaJuridica) {
		this.personaJuridica = personaJuridica;
	}

	public Denominacion() {
		// TODO Auto-generated constructor stub
	}
	
	public Double getMonto() {
		return monto;
	}
	public void setMonto(Double monto) {
		this.monto = monto;
	}
	public String getServicio() {
		return servicio;
	}
	public void setServicio(String servicio) {
		this.servicio = servicio;
	}
	public String getIndicadorSeleccion() {
		return indicadorSeleccion;
	}
	public void setIndicadorSeleccion(String indicadorSeleccion) {
		this.indicadorSeleccion = indicadorSeleccion;
	}

	public String getInidcadorDenominacion() {
		return indicadorDenominacion;
	}

	public void setInidcadorDenominacion(String inidcadorDenominacion) {
		this.indicadorDenominacion = inidcadorDenominacion;
	}

	public ArrayList getListaDenominaciones() {
		return listaDenominaciones;
	}

	public void setListaDenominaciones(ArrayList listaDenominaciones) {
		this.listaDenominaciones = listaDenominaciones;
	}

	public ArrayList getListaParticipantes() {
		return listaParticipantes;
	}

	public void setListaParticipantes(ArrayList listaParticipantes) {
		this.listaParticipantes = listaParticipantes;
	}

	public String getDescServicio() {
		return descServicio;
	}

	public void setDescServicio(String descServicio) {
		this.descServicio = descServicio;
	}

	public String getDescSeleccion() {
		return descSeleccion;
	}

	public void setDescSeleccion(String descSeleccion) {
		this.descSeleccion = descSeleccion;
	}

	public String getNumeroTitulo() {
		return numeroTitulo;
	}

	public void setNumeroTitulo(String numeroTitulo) {
		this.numeroTitulo = numeroTitulo;
	}

	public String getAnioTitu() {
		return anioTitu;
	}

	public void setAnioTitu(String anioTitu) {
		this.anioTitu = anioTitu;
	}

	public String getFechaPresTitu() {
		return fechaPresTitu;
	}

	public void setFechaPresTitu(String fechaPresTitu) {
		this.fechaPresTitu = fechaPresTitu;
	}

	/**
	 * @return el razonSocial
	 */
	public RazonSocial getRazonSocial() {
		return razonSocial;
	}

	/**
	 * @param razonSocial el razonSocial a establecer
	 */
	public void setRazonSocial(RazonSocial razonSocial) {
		this.razonSocial = razonSocial;
	}

	
}
