package gob.pe.sunarp.extranet.publicidad.bean;

import java.util.ArrayList;

public class InputPMasivaRelacionalBean 
{
	private String marca;
	private String modelo;
	private String anoFabricacionDesde;
	private String anoFabricacionHasta;
	private String fechaInscripcionDesde;
	private String fechaInscripcionHasta;
	private String codTipoVehiculo;
	private String codTipoCombustible;
	private String codTipoActoCausal;
	private String nombreEmbarcacionPesquera;
	private String codTipoEmbarcacion;
	private String codCapitania;
	private String nombreBuque;
	private String codTipoAeronave;
	private String montoGarantiaDesde;
	private String montoGarantiaHasta;
	private String registro;
	private String agrupación;
	private String flagPagineo;
	private String zonaRegistral;
	private String colorVeh;
	private String flagRespuesta;
	private int salto=1;
	private String resultado;
	//pagineo
	private int tipoBusqueda;
	private String action="";
	private int pagSiguiente=-1;
	private int pagAnterior=-1;
	private String cantidadRegistros;
//	valores registros del x al y
	private int ndel=0;
	private int nal=0;
	private boolean flagEstado=false;
	private String descripcionAgrupacion;
	private String descripcionTipoVehiculo;
	private String descripcionTipoComb;
	private String descripcionTipoAct;
	private String descripcionTipoAeronave;
	private String descripcionCapitania;
	private String descripcionTipoEmb;
	private String[] sedesElegidas=null;
	private String sedesSQLString="";
	private String cadenaZona;
	private String tipLibro;
	private String tipActo;
	private String ordenamiento;
	private String precio;
	private String fechaAct;
	private String saldo;
	private String cadenaQuery;
	
	public String getCadenaQuery() {
		return cadenaQuery;
	}
	public void setCadenaQuery(String cadenaQuery) {
		this.cadenaQuery = cadenaQuery;
	}
	public String getSaldo() {
		return saldo;
	}
	public void setSaldo(String saldo) {
		this.saldo = saldo;
	}
	public String getPrecio() {
		return precio;
	}
	public void setPrecio(String precio) {
		this.precio = precio;
	}
	public String getOrdenamiento() {
		return ordenamiento;
	}
	public void setOrdenamiento(String ordenamiento) {
		this.ordenamiento = ordenamiento;
	}
	public String getCadenaZona() {
		return cadenaZona;
	}
	public void setCadenaZona(String cadenaZona) {
		this.cadenaZona = cadenaZona;
	}
	public String[] getSedesElegidas() {
		return sedesElegidas;
	}
	public void setSedesElegidas(String[] sedesElegidas) {
		this.sedesElegidas = sedesElegidas;
	}
	public String getSedesSQLString() {
		return sedesSQLString;
	}
	public void setSedesSQLString(String sedesSQLString) {
		this.sedesSQLString = sedesSQLString;
	}
	public String getDescripcionTipoAeronave() {
		return descripcionTipoAeronave;
	}
	public void setDescripcionTipoAeronave(String descripcionTipoAeronave) {
		this.descripcionTipoAeronave = descripcionTipoAeronave;
	}
	public String getDescripcionTipoAct() {
		return descripcionTipoAct;
	}
	public void setDescripcionTipoAct(String descripcionTipoAct) {
		this.descripcionTipoAct = descripcionTipoAct;
	}
	public String getDescripcionTipoComb() {
		return descripcionTipoComb;
	}
	public void setDescripcionTipoComb(String descripcionTipoComb) {
		this.descripcionTipoComb = descripcionTipoComb;
	}
	public String getDescripcionTipoVehiculo() {
		return descripcionTipoVehiculo;
	}
	public void setDescripcionTipoVehiculo(String descripcionTipoVehiculo) {
		this.descripcionTipoVehiculo = descripcionTipoVehiculo;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getCantidadRegistros() {
		return cantidadRegistros;
	}
	public void setCantidadRegistros(String cantidadRegistros) {
		this.cantidadRegistros = cantidadRegistros;
	}
	public boolean isFlagEstado() {
		return flagEstado;
	}
	public void setFlagEstado(boolean flagEstado) {
		this.flagEstado = flagEstado;
	}
	public int getNal() {
		return nal;
	}
	public void setNal(int nal) {
		this.nal = nal;
	}
	public int getNdel() {
		return ndel;
	}
	public void setNdel(int ndel) {
		this.ndel = ndel;
	}
	public int getPagAnterior() {
		return pagAnterior;
	}
	public void setPagAnterior(int pagAnterior) {
		this.pagAnterior = pagAnterior;
	}
	public int getPagSiguiente() {
		return pagSiguiente;
	}
	public void setPagSiguiente(int pagSiguiente) {
		this.pagSiguiente = pagSiguiente;
	}
	public int getTipoBusqueda() {
		return tipoBusqueda;
	}
	public void setTipoBusqueda(int tipoBusqueda) {
		this.tipoBusqueda = tipoBusqueda;
	}
	public String getResultado() {
		return resultado;
	}
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	public String getColorVeh() {
		return colorVeh;
	}
	public void setColorVeh(String colorVeh) {
		this.colorVeh = colorVeh;
	}
	public String getAgrupación() {
		return agrupación;
	}
	public void setAgrupación(String agrupación) {
		this.agrupación = agrupación;
	}
	public String getAnoFabricacionDesde() {
		return anoFabricacionDesde;
	}
	public void setAnoFabricacionDesde(String anoFabricacionDesde) {
		this.anoFabricacionDesde = anoFabricacionDesde;
	}
	public String getAnoFabricacionHasta() {
		return anoFabricacionHasta;
	}
	public void setAnoFabricacionHasta(String anoFabricacionHasta) {
		this.anoFabricacionHasta = anoFabricacionHasta;
	}
	public String getCodCapitania() {
		return codCapitania;
	}
	public void setCodCapitania(String codCapitania) {
		this.codCapitania = codCapitania;
	}
	public String getCodTipoActoCausal() {
		return codTipoActoCausal;
	}
	public void setCodTipoActoCausal(String codTipoActoCausal) {
		this.codTipoActoCausal = codTipoActoCausal;
	}
	public String getCodTipoAeronave() {
		return codTipoAeronave;
	}
	public void setCodTipoAeronave(String codTipoAeronave) {
		this.codTipoAeronave = codTipoAeronave;
	}
	public String getCodTipoCombustible() {
		return codTipoCombustible;
	}
	public void setCodTipoCombustible(String codTipoCombustible) {
		this.codTipoCombustible = codTipoCombustible;
	}
	public String getCodTipoEmbarcacion() {
		return codTipoEmbarcacion;
	}
	public void setCodTipoEmbarcacion(String codTipoEmbarcacion) {
		this.codTipoEmbarcacion = codTipoEmbarcacion;
	}
	public String getCodTipoVehiculo() {
		return codTipoVehiculo;
	}
	public void setCodTipoVehiculo(String codTipoVehiculo) {
		this.codTipoVehiculo = codTipoVehiculo;
	}
	public String getFechaInscripcionDesde() {
		return fechaInscripcionDesde;
	}
	public void setFechaInscripcionDesde(String fechaInscripcionDesde) {
		this.fechaInscripcionDesde = fechaInscripcionDesde;
	}
	public String getFechaInscripcionHasta() {
		return fechaInscripcionHasta;
	}
	public void setFechaInscripcionHasta(String fechaInscripcionHasta) {
		this.fechaInscripcionHasta = fechaInscripcionHasta;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 27, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the flagPagineo
	 */
	public String getFlagPagineo() {
		return flagPagineo;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 27, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param flagPagineo the flagPagineo to set
	 */
	public void setFlagPagineo(String flagPagineo) {
		this.flagPagineo = flagPagineo;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getNombreBuque() {
		return nombreBuque;
	}
	public void setNombreBuque(String nombreBuque) {
		this.nombreBuque = nombreBuque;
	}
	public String getNombreEmbarcacionPesquera() {
		return nombreEmbarcacionPesquera;
	}
	public void setNombreEmbarcacionPesquera(String nombreEmbarcacionPesquera) {
		this.nombreEmbarcacionPesquera = nombreEmbarcacionPesquera;
	}
	public String getRegistro() {
		return registro;
	}
	public void setRegistro(String registro) {
		this.registro = registro;
	}
	public String getZonaRegistral() {
		return zonaRegistral;
	}
	public void setZonaRegistral(String zonaRegistral) {
		this.zonaRegistral = zonaRegistral;
	}
	public String getMontoGarantiaDesde() {
		return montoGarantiaDesde;
	}
	public void setMontoGarantiaDesde(String montoGarantiaDesde) {
		this.montoGarantiaDesde = montoGarantiaDesde;
	}
	public String getMontoGarantiaHasta() {
		return montoGarantiaHasta;
	}
	public void setMontoGarantiaHasta(String montoGarantiaHasta) {
		this.montoGarantiaHasta = montoGarantiaHasta;
	}
	public int getSalto() {
		return salto;
	}
	public void setSalto(int salto) {
		this.salto = salto;
	}
	public String getFlagRespuesta() {
		return flagRespuesta;
	}
	public void setFlagRespuesta(String flagRespuesta) {
		this.flagRespuesta = flagRespuesta;
	}
	public String getDescripcionCapitania() {
		return descripcionCapitania;
	}
	public void setDescripcionCapitania(String descripcionCapitania) {
		this.descripcionCapitania = descripcionCapitania;
	}
	public String getDescripcionTipoEmb() {
		return descripcionTipoEmb;
	}
	public void setDescripcionTipoEmb(String descripcionTipoEmb) {
		this.descripcionTipoEmb = descripcionTipoEmb;
	}
	public String getTipActo() {
		return tipActo;
	}
	public void setTipActo(String tipActo) {
		this.tipActo = tipActo;
	}
	public String getTipLibro() {
		return tipLibro;
	}
	public void setTipLibro(String tipLibro) {
		this.tipLibro = tipLibro;
	}
	public String getFechaAct() {
		return fechaAct;
	}
	public void setFechaAct(String fechaAct) {
		this.fechaAct = fechaAct;
	}
	/**
	 * @return the descripcionAgrupacion
	 */
	public String getDescripcionAgrupacion() {
		return descripcionAgrupacion;
	}
	/**
	 * @param descripcionAgrupacion the descripcionAgrupacion to set
	 */
	public void setDescripcionAgrupacion(String descripcionAgrupacion) {
		this.descripcionAgrupacion = descripcionAgrupacion;
	}
}
