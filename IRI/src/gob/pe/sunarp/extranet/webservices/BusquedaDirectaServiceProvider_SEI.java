package gob.pe.sunarp.extranet.webservices;


public interface BusquedaDirectaServiceProvider_SEI extends java.rmi.Remote
{
  public gob.pe.sunarp.extranet.webservices.bean.ResultadoPartidaBean busquedaRmcPorNumeroFicha(java.lang.String usuario,java.lang.String clave,java.lang.String idRegistroPublico,java.lang.String idOficinaRegistral,java.lang.String numeroFicha);
  public gob.pe.sunarp.extranet.webservices.bean.ResultadoPartidaBean busquedaRmcPorNumeroPartida(java.lang.String usuario,java.lang.String clave,java.lang.String idRegistroPublico,java.lang.String idOficinaRegistral,java.lang.String numeroPartida);
  public gob.pe.sunarp.extranet.webservices.bean.ResultadoPartidaBean busquedaRmcPorNumeroTomoFolio(java.lang.String usuario,java.lang.String clave,java.lang.String idRegistroPublico,java.lang.String idOficinaRegistral,java.lang.String libro,java.lang.String numeroTomo,java.lang.String numeroFolio);
}