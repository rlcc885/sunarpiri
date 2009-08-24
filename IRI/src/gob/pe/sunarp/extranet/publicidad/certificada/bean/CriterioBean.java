package gob.pe.sunarp.extranet.publicidad.certificada.bean;

public class CriterioBean 
{
	private String placa;
	private String partida;
	private String zonaOficina;
	private String ficha;
	private String areaRegistral;
	private String tomo;
	private String folio;
	private String apellidoParterno;
	private String apellidoMaterno;
	private String nombre;
	private String razonSocial;
	private String sigla;
	private String numeroMatricula;
	private String nombreEmbarcacion;
	private String nombreBuque;
	private String numeroMotor;
	private String chasis;
	private String registro;
	private String[] sedesElegidas=null;
	private String sedesSQLString="";
	private String flagmetodo;
	
	public String getFlagmetodo() {
		return flagmetodo;
	}
	public void setFlagmetodo(String flagmetodo) {
		this.flagmetodo = flagmetodo;
	}
	public String getRegistro() {
		return registro;
	}
	public void setRegistro(String registro) {
		this.registro = registro;
	}
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}
	public String getApellidoParterno() {
		return apellidoParterno;
	}
	public void setApellidoParterno(String apellidoParterno) {
		this.apellidoParterno = apellidoParterno;
	}
	public String getChasis() {
		return chasis;
	}
	public void setChasis(String chasis) {
		this.chasis = chasis;
	}
	public String getFolio() {
		return folio;
	}
	public void setFolio(String folio) {
		this.folio = folio;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNombreEmbarcacion() {
		return nombreEmbarcacion;
	}
	public void setNombreEmbarcacion(String nombreEmbarcacion) {
		this.nombreEmbarcacion = nombreEmbarcacion;
	}
	public String getNumeroMatricula() {
		return numeroMatricula;
	}
	public void setNumeroMatricula(String numeroMatricula) {
		this.numeroMatricula = numeroMatricula;
	}
	public String getNumeroMotor() {
		return numeroMotor;
	}
	public void setNumeroMotor(String numeroMotor) {
		this.numeroMotor = numeroMotor;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public String getTomo() {
		return tomo;
	}
	public void setTomo(String tomo) {
		this.tomo = tomo;
	}
	public String getAreaRegistral() {
		return areaRegistral;
	}
	public void setAreaRegistral(String areaRegistral) {
		this.areaRegistral = areaRegistral;
	}
	public String getFicha() {
		return ficha;
	}
	public void setFicha(String ficha) {
		this.ficha = ficha;
	}
	public String getPartida() {
		return partida;
	}
	public void setPartida(String partida) {
		this.partida = partida;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public String getZonaOficina() {
		return zonaOficina;
	}
	public void setZonaOficina(String zonaOficina) {
		this.zonaOficina = zonaOficina;
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
	public String getNombreBuque() {
		return nombreBuque;
	}
	public void setNombreBuque(String nombreBuque) {
		this.nombreBuque = nombreBuque;
	}
}
