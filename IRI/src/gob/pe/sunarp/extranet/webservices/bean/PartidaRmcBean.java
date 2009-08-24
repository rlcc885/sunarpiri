package gob.pe.sunarp.extranet.webservices.bean;

import java.io.Serializable;

public class PartidaRmcBean implements Serializable
{
	private static final long serialVersionUID = -8451657975313493233L;
	
	private String numeroPartida;
	private String numeroPartidaMigrada;
	private TituloPendienteBean[] tituloPendienteBeans;
	private ActoBean[] actoBeans;
	
	public ActoBean[] getActoBeans() {
		return actoBeans;
	}
	public void setActoBeans(ActoBean[] actoBeans) {
		this.actoBeans = actoBeans;
	}
	public String getNumeroPartida() {
		return numeroPartida;
	}
	public void setNumeroPartida(String numeroPartida) {
		this.numeroPartida = numeroPartida;
	}
	public String getNumeroPartidaMigrada() {
		return numeroPartidaMigrada;
	}
	public void setNumeroPartidaMigrada(String numeroPartidaMigrada) {
		this.numeroPartidaMigrada = numeroPartidaMigrada;
	}
	public TituloPendienteBean[] getTituloPendienteBeans() {
		return tituloPendienteBeans;
	}
	public void setTituloPendienteBeans(TituloPendienteBean[] tituloPendienteBeans) {
		this.tituloPendienteBeans = tituloPendienteBeans;
	}

}
