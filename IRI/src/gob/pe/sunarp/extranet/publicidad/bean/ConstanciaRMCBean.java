package gob.pe.sunarp.extranet.publicidad.bean;

import java.util.ArrayList;

public class ConstanciaRMCBean {
    private String modelo;
	private String marca;
	private String serie;
	private String motor;
	private String placa;
	private String descripcion;
	private String apePat;
	private String apeMat;
	private String tipoDocIdentidad;
	private String numDocIdentidad;
	private String razonSocial;
	private String siglas;
	private String nombres;

    private PartidaBean partida;
    private ArrayList titulos;
	public ArrayList getTitulos() {
		return titulos;
	}
	public void setTitulos(ArrayList titulos) {
		this.titulos = titulos;
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
	public String getMotor() {
		return motor;
	}
	public void setMotor(String motor) {
		this.motor = motor;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public String getSerie() {
		return serie;
	}
	public void setSerie(String serie) {
		this.serie = serie;
	}
	public PartidaBean getPartida() {
		return partida;
	}
	public void setPartida(PartidaBean partida) {
		this.partida = partida;
	}
	public String getApeMat() {
		return apeMat;
	}
	public void setApeMat(String apeMat) {
		this.apeMat = apeMat;
	}
	public String getApePat() {
		return apePat;
	}
	public void setApePat(String apePat) {
		this.apePat = apePat;
	}
	public String getNumDocIdentidad() {
		return numDocIdentidad;
	}
	public void setNumDocIdentidad(String numDocIdentidad) {
		this.numDocIdentidad = numDocIdentidad;
	}
	public String getTipoDocIdentidad() {
		return tipoDocIdentidad;
	}
	public void setTipoDocIdentidad(String tipoDocIdentidad) {
		this.tipoDocIdentidad = tipoDocIdentidad;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getSiglas() {
		return siglas;
	}
	public void setSiglas(String siglas) {
		this.siglas = siglas;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}


}
