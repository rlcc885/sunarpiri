package gob.pe.sunarp.extranet.webservices;


public interface BusquedaDetallePartidaServiceProvider_SEI extends java.rmi.Remote
{
  public gob.pe.sunarp.extranet.webservices.bean.ResultadoDetallePartidaRmcBean busquedaPorNumeroPartida(java.lang.String usuario,java.lang.String clave,java.lang.String numeroPartida,java.lang.String idRegistroPublico,java.lang.String idOficinaRegistral);
}