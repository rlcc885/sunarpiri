package gob.pe.sunarp.extranet.publicidad.bean;

public class ParticipanteBean 
{
	private String apellidoPaterno   = "&nbsp;";
    private String apellidoMaterno   = "&nbsp;";
    private String nombre            = "&nbsp;";
    private String razonSocial       = "&nbsp;";
    private String tipoPersona       = "&nbsp;";
    private String descripcionTipoDocumento   = "&nbsp;";
    private String numeroDocumento   = "&nbsp;";
    private String descripcionTipoParticipacion   = "&nbsp;";
    private String descripcionDomicilio   = "&nbsp;";
    
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}
	public String getDescripcionDomicilio() {
		return descripcionDomicilio;
	}
	public void setDescripcionDomicilio(String descripcionDomicilio) {
		this.descripcionDomicilio = descripcionDomicilio;
	}
	public String getDescripcionTipoDocumento() {
		return descripcionTipoDocumento;
	}
	public void setDescripcionTipoDocumento(String descripcionTipoDocumento) {
		this.descripcionTipoDocumento = descripcionTipoDocumento;
	}
	public String getDescripcionTipoParticipacion() {
		return descripcionTipoParticipacion;
	}
	public void setDescripcionTipoParticipacion(String descripcionTipoParticipacion) {
		this.descripcionTipoParticipacion = descripcionTipoParticipacion;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getTipoPersona() {
		return tipoPersona;
	}
	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

}
