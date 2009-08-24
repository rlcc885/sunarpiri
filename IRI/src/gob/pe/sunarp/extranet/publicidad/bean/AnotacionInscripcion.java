package gob.pe.sunarp.extranet.publicidad.bean;

public class AnotacionInscripcion 
{
	private String descripcionActo;
	private String numeroPartida;
	private String secuenciaAsiento;
	private String fechaIncripcion;
	
	public String getDescripcionActo() {
		return descripcionActo;
	}
	public void setDescripcionActo(String descripcionActo) {
		this.descripcionActo = descripcionActo;
	}
	public String getFechaIncripcion() {
		return fechaIncripcion;
	}
	public void setFechaIncripcion(String fechaIncripcion) {
		this.fechaIncripcion = fechaIncripcion;
	}
	public String getNumeroPartida() {
		return numeroPartida;
	}
	public void setNumeroPartida(String numeroPartida) {
		this.numeroPartida = numeroPartida;
	}
	public String getSecuenciaAsiento() {
		return secuenciaAsiento;
	}
	public void setSecuenciaAsiento(String secuenciaAsiento) {
		this.secuenciaAsiento = secuenciaAsiento;
	}
	
}
