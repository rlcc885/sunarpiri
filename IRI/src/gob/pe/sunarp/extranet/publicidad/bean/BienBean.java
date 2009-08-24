package gob.pe.sunarp.extranet.publicidad.bean;

public class BienBean 
{
	private String descripcion   = "";
    private String tipo          = "";
    private String nsBien        = "&nbsp;";
    private String cantidad      = "";
    private String monto   = "";
   
    private String numeroPlaca   = "&nbsp;";
    private String numeroSerie   = "&nbsp;";
    private String numeroMotor   = "&nbsp;";
    private String modelo        = "";
    private String marca   = "";
    
	public String getCantidad() {
		return cantidad;
	}
	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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
	public String getMonto() {
		return monto;
	}
	public void setMonto(String monto) {
		this.monto = monto;
	}
	public String getNsBien() {
		return nsBien;
	}
	public void setNsBien(String nsBien) {
		this.nsBien = nsBien;
	}
	public String getNumeroMotor() {
		return numeroMotor;
	}
	public void setNumeroMotor(String numeroMotor) {
		this.numeroMotor = numeroMotor;
	}
	public String getNumeroPlaca() {
		return numeroPlaca;
	}
	public void setNumeroPlaca(String numeroPlaca) {
		this.numeroPlaca = numeroPlaca;
	}
	public String getNumeroSerie() {
		return numeroSerie;
	}
	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie = numeroSerie;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
