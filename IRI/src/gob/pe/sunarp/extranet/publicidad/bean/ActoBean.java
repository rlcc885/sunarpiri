package gob.pe.sunarp.extranet.publicidad.bean;

import java.util.ArrayList;

public class ActoBean 
{
	private String actoDescripcion   = "&nbsp;";
	private String fechaConstitutivo = "&nbsp;";
	private double montoAfectacion;
    private String forma     = "&nbsp;";
    private String condicion = "&nbsp;";
    private String anoPlazo;
    private String mesPlazo;
    private String diaPlazo;
    private String simboloMonto;

	/**
	 * @return the simboloMonto
	 */
	public String getSimboloMonto() {
		return simboloMonto;
	}
	/**
	 * @param simboloMonto the simboloMonto to set
	 */
	public void setSimboloMonto(String simboloMonto) {
		this.simboloMonto = simboloMonto;
	}
	public String getActoDescripcion() {
		return actoDescripcion;
	}
	public void setActoDescripcion(String actoDescripcion) {
		this.actoDescripcion = actoDescripcion;
	}
	public String getCondicion() {
		return condicion;
	}
	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}
	public String getFechaConstitutivo() {
		return fechaConstitutivo;
	}
	public void setFechaConstitutivo(String fechaConstitutivo) {
		this.fechaConstitutivo = fechaConstitutivo;
	}
	public String getForma() {
		return forma;
	}
	public void setForma(String forma) {
		this.forma = forma;
	}
	
	/**
	 * @autor dbravo
	 * @fecha Jul 12, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the montoAfectacion
	 */
	public double getMontoAfectacion() {
		return montoAfectacion;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 12, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param montoAfectacion the montoAfectacion to set
	 */
	public void setMontoAfectacion(double montoAfectacion) {
		this.montoAfectacion = montoAfectacion;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 12, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the anoPlazo
	 */
	public String getAnoPlazo() {
		return anoPlazo;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 12, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param anoPlazo the anoPlazo to set
	 */
	public void setAnoPlazo(String anoPlazo) {
		this.anoPlazo = anoPlazo;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 12, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the diaPlazo
	 */
	public String getDiaPlazo() {
		return diaPlazo;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 12, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param diaPlazo the diaPlazo to set
	 */
	public void setDiaPlazo(String diaPlazo) {
		this.diaPlazo = diaPlazo;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 12, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the mesPlazo
	 */
	public String getMesPlazo() {
		return mesPlazo;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 12, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param mesPlazo the mesPlazo to set
	 */
	public void setMesPlazo(String mesPlazo) {
		this.mesPlazo = mesPlazo;
	}

}
