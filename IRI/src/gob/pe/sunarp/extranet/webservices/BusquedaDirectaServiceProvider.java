/**
 * 
 */
package gob.pe.sunarp.extranet.webservices;

import java.util.ArrayList;
import java.util.Enumeration;

import com.jcorporate.expresso.core.db.DBException;

import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.publicidad.bean.FormOutputBuscarPartida;
import gob.pe.sunarp.extranet.publicidad.bean.InputBusqDirectaBean;
import gob.pe.sunarp.extranet.publicidad.service.AccesoService;
import gob.pe.sunarp.extranet.publicidad.service.ConsultarPartidaDirectaService;
import gob.pe.sunarp.extranet.publicidad.service.impl.AccesoServiceImpl;
import gob.pe.sunarp.extranet.publicidad.service.impl.ConsultarPartidaDirectaServiceImpl;
import gob.pe.sunarp.extranet.util.Constantes;
import gob.pe.sunarp.extranet.util.ValidacionException;
import gob.pe.sunarp.extranet.webservices.bean.PartidaBean;
import gob.pe.sunarp.extranet.webservices.bean.ResultadoPartidaBean;
import gob.pe.sunarp.extranet.webservices.util.Errores;

/**
 * @author dbravo
 *
 */
public class BusquedaDirectaServiceProvider extends ServiceProvider 
{

	public ResultadoPartidaBean busquedaRmcPorNumeroFicha(String usuario, String clave, String idRegistroPublico, String idOficinaRegistral, String numeroFicha){
		
		ResultadoPartidaBean resultadoPartidaBean = new ResultadoPartidaBean();
		String session_id = "";
		String ipRemota = "";
		try
		{
			session_id = obtenerAMIdSesion(getServletRequest());
			ipRemota = obtenerIPRemota(getServletRequest());
		}catch (Exception e) 
		{
			e.printStackTrace();
			
			resultadoPartidaBean.setCodigoError(Errores.WS_GENERICO_APLICACION);
			return resultadoPartidaBean;
		}	
		/**
		 * @autor: dbravo
		 * @descripcion: Se procede a validar y recuperar los parametros necesarios, si algun parametro no fuera correcto se retornara el 
		 *              codigo respectivo
		 */
		if(usuario==null || usuario.trim().length()==0){
			resultadoPartidaBean.setCodigoError(Errores.WS_PARAMETRO_INCORRECTO_USUARIO);
			return resultadoPartidaBean; 
		}
		
		if(clave==null || clave.trim().length()==0){
			resultadoPartidaBean.setCodigoError(Errores.WS_PARAMETRO_INCORRECTO_CLAVE);
			return resultadoPartidaBean; 
		}
		
		if(idRegistroPublico==null || idRegistroPublico.trim().length()==0){
			resultadoPartidaBean.setCodigoError(Errores.WS_PARAMETRO_INCORRECTO_REGISTRO_PUBLICO_ID);
			return resultadoPartidaBean; 
		}
		
		if(idOficinaRegistral==null || idOficinaRegistral.trim().length()==0){
			resultadoPartidaBean.setCodigoError(Errores.WS_PARAMETRO_INCORRECTO_OFICINA_REGISTRAL_ID);
			return resultadoPartidaBean; 
		}
		
		if(numeroFicha==null || numeroFicha.trim().length()==0){
			resultadoPartidaBean.setCodigoError(Errores.WS_PARAMETRO_INCORRECTO_FICHA);
			return resultadoPartidaBean; 
		}
		
		/**
		 * @autor: dbravo
		 * @descripcion: Se realiza la validación de acceso del usuario.
		 */
		try{
			AccesoService accesoService = new AccesoServiceImpl();
			
			UsuarioBean usuarioBean = accesoService.validarIngreso(usuario, clave, ipRemota, session_id);
			
			if(!(usuarioBean.getPerfilId()==Constantes.PERFIL_INDIVIDUAL_EXTERNO || usuarioBean.getPerfilId()==Constantes.PERFIL_AFILIADO_EXTERNO || usuarioBean.getPerfilId()==Constantes.PERFIL_ADMIN_ORG_EXT))
			{
				resultadoPartidaBean.setCodigoError(Errores.WS_ACCESO_PERFIL_INCORRECTO);
				return resultadoPartidaBean;
			}
			
			InputBusqDirectaBean inputBusqDirectaBean = new InputBusqDirectaBean();
			inputBusqDirectaBean.setCodGrupoLibroArea(Constantes.GRUPO_LIBRO_AREA_BUSQUEDA_RMC);
			inputBusqDirectaBean.setRegPubId(idRegistroPublico);
			inputBusqDirectaBean.setOficRegId(idOficinaRegistral);
			inputBusqDirectaBean.setNumeroFicha(numeroFicha);
			
			ConsultarPartidaDirectaService consultarPartidaDirectaService = new ConsultarPartidaDirectaServiceImpl();
			
			FormOutputBuscarPartida formOutputBuscarPartida = consultarPartidaDirectaService.busquedaDirectaPorFichaRMC(ConsultarPartidaDirectaService.MEDIO_WEB_SERVICE, inputBusqDirectaBean, usuarioBean, ipRemota);
		
			ArrayList<gob.pe.sunarp.extranet.publicidad.bean.PartidaBean> respuestaService = formOutputBuscarPartida.getResultado();
			
			PartidaBean[] partidaBeans = new PartidaBean[respuestaService.size()];
			for(int x=0; respuestaService!=null && x<respuestaService.size(); x++){
				
				gob.pe.sunarp.extranet.publicidad.bean.PartidaBean partidaBean =  respuestaService.get(x);
				
				PartidaBean partidaBeanTemp = new PartidaBean();
				partidaBeanTemp.setDescripcionAreaRegistral(partidaBean.getAreaRegistralDescripcion());
				partidaBeanTemp.setDescripcionOficinaRegistral(partidaBean.getOficRegDescripcion());
				partidaBeanTemp.setDescripcionRegistro(partidaBean.getLibroDescripcion());
				partidaBeanTemp.setDescripcionZonaRegistral(partidaBean.getRegPubDescripcion());
				partidaBeanTemp.setNumeroFicha(numeroFicha);
				partidaBeanTemp.setNumeroFolio(partidaBean.getFojaId());
				partidaBeanTemp.setNumeroPartida(partidaBean.getNumPartida());
				partidaBeanTemp.setNumeroTomo(partidaBean.getTomoId());
				
				partidaBeans[x] = partidaBeanTemp;
				
			}
			resultadoPartidaBean.setMontoPagado(String.valueOf(formOutputBuscarPartida.getTarifa()));
			resultadoPartidaBean.setPartidaBeans(partidaBeans);
			
		}catch (CustomException e) 
		{
			if(e.getCodigoError().equals("E00001")){
				resultadoPartidaBean.setCodigoError(Errores.WS_ACCESO_USUARIO_CLAVE_INCORRECTO);
			}else if(e.getCodigoError().equals("E09005"))
			{
				resultadoPartidaBean.setCodigoError(Errores.WS_ACCESO_ERROR_INTEGRIDAD);
			}else if(e.getCodigoError().equals("E20004"))
			{
				resultadoPartidaBean.setCodigoError(Errores.WS_ACCESO_CUENTA_DESHABILITADA);
			}else if(e.getCodigoError().equals("E08007"))
			{
				resultadoPartidaBean.setCodigoError(Errores.WS_ACCESO_CUENTA_SESSION_ACTIVA);
			}else if(e.getCodigoError().equals("E08008"))
			{
				resultadoPartidaBean.setCodigoError(Errores.WS_ACCESO_RANGO_IP_NO_PERMITIDA);
			}else if(e.getCodigoError().equals("E08010"))
			{
				resultadoPartidaBean.setCodigoError(Errores.WS_ACCESO_NO_EXISTE_SESSION);
			}else if(e.getCodigoError().equals("E20001"))
			{
				resultadoPartidaBean.setCodigoError(Errores.WS_ACCESO_PERFIL_INCORRECTO);
			}else if(e.getCodigoError().equals("E20002"))
			{
				resultadoPartidaBean.setCodigoError(Errores.WS_ACCESO_USUARIO_NO_TIENE_SALDO);
			}else if(e.getCodigoError().equals("E08009"))
			{
				resultadoPartidaBean.setCodigoError(Errores.WS_ACCESO_FERIADO_NO_PERMITIDO);
			}else if(e.getCodigoError().equals("E20011"))
			{
				resultadoPartidaBean.setCodigoError(Errores.WS_ACCESO_CUENTA_NO_REGISTRADA);
			}else if(e.getCodigoError().equals("E20003"))
			{
				resultadoPartidaBean.setCodigoError(Errores.WS_ACCESO_PN_NO_REGISTRADA);
			}else if(e.getCodigoError().equals("E20008"))
			{
				resultadoPartidaBean.setCodigoError(Errores.WS_ACCESO_PJ_NO_REGISTRADA);
			}else if(e.getCodigoError().equals("E20009"))
			{
				resultadoPartidaBean.setCodigoError(Errores.WS_ACCESO_NO_TIENE_CONTRATO);
			}else if(e.getCodigoError().equals("E70002"))
			{
				resultadoPartidaBean.setCodigoError(Errores.WS_BUSQUEDA_NO_TIENE_RESULTADOS);
			}else{
				resultadoPartidaBean.setCodigoError(Errores.WS_GENERICO_APLICACION);
			}
			e.printStackTrace();
		}catch (ValidacionException e) 
		{
			
			resultadoPartidaBean.setCodigoError(Errores.WS_BUSQUEDA_LIMITE_RESULTADO_EXCEDIDO);
			e.printStackTrace();
			
		}catch (DBException dbe) {
			
			dbe.printStackTrace();
		} catch (Throwable ex) {
			
			ex.printStackTrace();
		}
		
		return resultadoPartidaBean;
		
	}

	public ResultadoPartidaBean busquedaRmcPorNumeroPartida(String usuario, String clave, String idRegistroPublico, String idOficinaRegistral, String numeroPartida)
	{
		ResultadoPartidaBean resultadoPartidaBean = new ResultadoPartidaBean();
		String session_id = "";
		String ipRemota = "";
		try
		{
			session_id = obtenerAMIdSesion(getServletRequest());
			ipRemota = obtenerIPRemota(getServletRequest());
		}catch (Exception e) 
		{
			e.printStackTrace();
			
			resultadoPartidaBean.setCodigoError(Errores.WS_GENERICO_APLICACION);
			return resultadoPartidaBean;
		}	
		
		
		if(usuario==null || usuario.trim().length()==0){
			resultadoPartidaBean.setCodigoError(Errores.WS_PARAMETRO_INCORRECTO_USUARIO);
			return resultadoPartidaBean; 
		}
		
		if(clave==null || clave.trim().length()==0){
			resultadoPartidaBean.setCodigoError(Errores.WS_PARAMETRO_INCORRECTO_CLAVE);
			return resultadoPartidaBean; 
		}
		
		if(idRegistroPublico==null || idRegistroPublico.trim().length()==0){
			resultadoPartidaBean.setCodigoError(Errores.WS_PARAMETRO_INCORRECTO_REGISTRO_PUBLICO_ID);
			return resultadoPartidaBean; 
		}
		
		if(idOficinaRegistral==null || idOficinaRegistral.trim().length()==0){
			resultadoPartidaBean.setCodigoError(Errores.WS_PARAMETRO_INCORRECTO_OFICINA_REGISTRAL_ID);
			return resultadoPartidaBean; 
		}
		
		if(numeroPartida==null || numeroPartida.trim().length()==0){
			resultadoPartidaBean.setCodigoError(Errores.WS_PARAMETRO_INCORRECTO_NUMERO_PARTIDA);
			return resultadoPartidaBean; 
		}
		try
		{
			AccesoService accesoService = new AccesoServiceImpl();
			UsuarioBean usuarioBean = accesoService.validarIngreso(usuario, clave, ipRemota, session_id);
			
			if(!(usuarioBean.getPerfilId()==Constantes.PERFIL_INDIVIDUAL_EXTERNO || usuarioBean.getPerfilId()==Constantes.PERFIL_AFILIADO_EXTERNO || usuarioBean.getPerfilId()==Constantes.PERFIL_ADMIN_ORG_EXT))
			{
				resultadoPartidaBean.setCodigoError(Errores.WS_ACCESO_PERFIL_INCORRECTO);
				return resultadoPartidaBean;
			}
			
			
			InputBusqDirectaBean inputBusqDirectaBean = new InputBusqDirectaBean();
			
			inputBusqDirectaBean.setCodGrupoLibroArea(Constantes.GRUPO_LIBRO_AREA_BUSQUEDA_RMC);
			inputBusqDirectaBean.setRegPubId(idRegistroPublico);
			inputBusqDirectaBean.setOficRegId(idOficinaRegistral);
			inputBusqDirectaBean.setNumeroPartida(numeroPartida);
			
			ConsultarPartidaDirectaService consultarPartidaDirectaService = new ConsultarPartidaDirectaServiceImpl();
			
			FormOutputBuscarPartida formOutputBuscarPartida = consultarPartidaDirectaService.busquedaDirectaPorPartidaRMC(ConsultarPartidaDirectaService.MEDIO_WEB_SERVICE, inputBusqDirectaBean, usuarioBean, ipRemota);
			
			ArrayList<gob.pe.sunarp.extranet.publicidad.bean.PartidaBean> respuestaService = formOutputBuscarPartida.getResultado();
			
			PartidaBean[] partidaBeans = new PartidaBean[respuestaService.size()];
			for(int x=0; respuestaService!=null && x<respuestaService.size(); x++){
				
				gob.pe.sunarp.extranet.publicidad.bean.PartidaBean partidaBean =  respuestaService.get(x);
				
				PartidaBean partidaBeanTemp = new PartidaBean();
				partidaBeanTemp.setDescripcionAreaRegistral(partidaBean.getAreaRegistralDescripcion());
				partidaBeanTemp.setDescripcionOficinaRegistral(partidaBean.getOficRegDescripcion());
				partidaBeanTemp.setDescripcionRegistro(partidaBean.getLibroDescripcion());
				partidaBeanTemp.setDescripcionZonaRegistral(partidaBean.getRegPubDescripcion());
				partidaBeanTemp.setNumeroFicha(partidaBean.getFichaId());
				partidaBeanTemp.setNumeroFolio(partidaBean.getFojaId());
				partidaBeanTemp.setNumeroPartida(numeroPartida);
				partidaBeanTemp.setNumeroTomo(partidaBean.getTomoId());
					
				partidaBeans[x] = partidaBeanTemp;
				
			}
			resultadoPartidaBean.setMontoPagado(String.valueOf(formOutputBuscarPartida.getTarifa()));
			resultadoPartidaBean.setPartidaBeans(partidaBeans);
			
		}catch (CustomException e) 
		{
			if(e.getCodigoError().equals("E00001")){
				resultadoPartidaBean.setCodigoError(Errores.WS_ACCESO_USUARIO_CLAVE_INCORRECTO);
			}else if(e.getCodigoError().equals("E09005"))
			{
				resultadoPartidaBean.setCodigoError(Errores.WS_ACCESO_ERROR_INTEGRIDAD);
			}else if(e.getCodigoError().equals("E20004"))
			{
				resultadoPartidaBean.setCodigoError(Errores.WS_ACCESO_CUENTA_DESHABILITADA);
			}else if(e.getCodigoError().equals("E08007"))
			{
				resultadoPartidaBean.setCodigoError(Errores.WS_ACCESO_CUENTA_SESSION_ACTIVA);
			}else if(e.getCodigoError().equals("E08008"))
			{
				resultadoPartidaBean.setCodigoError(Errores.WS_ACCESO_RANGO_IP_NO_PERMITIDA);
			}else if(e.getCodigoError().equals("E08010"))
			{
				resultadoPartidaBean.setCodigoError(Errores.WS_ACCESO_NO_EXISTE_SESSION);
			}else if(e.getCodigoError().equals("E20001"))
			{
				resultadoPartidaBean.setCodigoError(Errores.WS_ACCESO_PERFIL_INCORRECTO);
			}else if(e.getCodigoError().equals("E20002"))
			{
				resultadoPartidaBean.setCodigoError(Errores.WS_ACCESO_USUARIO_NO_TIENE_SALDO);
			}else if(e.getCodigoError().equals("E08009"))
			{
				resultadoPartidaBean.setCodigoError(Errores.WS_ACCESO_FERIADO_NO_PERMITIDO);
			}else if(e.getCodigoError().equals("E20011"))
			{
				resultadoPartidaBean.setCodigoError(Errores.WS_ACCESO_CUENTA_NO_REGISTRADA);
			}else if(e.getCodigoError().equals("E20003"))
			{
				resultadoPartidaBean.setCodigoError(Errores.WS_ACCESO_PN_NO_REGISTRADA);
			}else if(e.getCodigoError().equals("E20008"))
			{
				resultadoPartidaBean.setCodigoError(Errores.WS_ACCESO_PJ_NO_REGISTRADA);
			}else if(e.getCodigoError().equals("E20009"))
			{
				resultadoPartidaBean.setCodigoError(Errores.WS_ACCESO_NO_TIENE_CONTRATO);
			}else if(e.getCodigoError().equals("E70002"))
			{
				resultadoPartidaBean.setCodigoError(Errores.WS_BUSQUEDA_NO_TIENE_RESULTADOS);
			}else{
				resultadoPartidaBean.setCodigoError(Errores.WS_GENERICO_APLICACION);
			}
			e.printStackTrace();
		}catch (ValidacionException e) 
		{
			resultadoPartidaBean.setCodigoError(Errores.WS_BUSQUEDA_LIMITE_RESULTADO_EXCEDIDO);
			e.printStackTrace();
			
		}catch (DBException dbe) {
			dbe.printStackTrace();
		} catch (Throwable ex) {
			
			ex.printStackTrace();
		}
		
		return resultadoPartidaBean;
		
	}
	public ResultadoPartidaBean busquedaRmcPorNumeroTomoFolio(String usuario, String clave, String idRegistroPublico, String idOficinaRegistral,String libro ,String numeroTomo, String numeroFolio)
	{
		ResultadoPartidaBean resultadoPartidaBean = new ResultadoPartidaBean();
		String session_id = "";
		String ipRemota = "";
		try
		{
			session_id = obtenerAMIdSesion(getServletRequest());
			ipRemota = obtenerIPRemota(getServletRequest());
		}catch (Exception e) 
		{
			e.printStackTrace();
			
			resultadoPartidaBean.setCodigoError(Errores.WS_GENERICO_APLICACION);
			return resultadoPartidaBean;
		}	
		
		if(usuario==null || usuario.trim().length()==0){
			resultadoPartidaBean.setCodigoError(Errores.WS_PARAMETRO_INCORRECTO_USUARIO);
			return resultadoPartidaBean; 
		}
		
		if(clave==null || clave.trim().length()==0){
			resultadoPartidaBean.setCodigoError(Errores.WS_PARAMETRO_INCORRECTO_CLAVE);
			return resultadoPartidaBean; 
		}
		
		if(idRegistroPublico==null || idRegistroPublico.trim().length()==0){
			resultadoPartidaBean.setCodigoError(Errores.WS_PARAMETRO_INCORRECTO_REGISTRO_PUBLICO_ID);
			return resultadoPartidaBean; 
		}
		
		if(idOficinaRegistral==null || idOficinaRegistral.trim().length()==0){
			resultadoPartidaBean.setCodigoError(Errores.WS_PARAMETRO_INCORRECTO_OFICINA_REGISTRAL_ID);
			return resultadoPartidaBean; 
		}
		
		if(libro == null || libro.trim().length() == 0)
		{
			resultadoPartidaBean.setCodigoError(Errores.WS_PARAMETRO_INCORRECTO_CODIGO_LIBRO);
			return resultadoPartidaBean;
		}
		
		if(numeroTomo==null || numeroTomo.trim().length()==0)
		{
			resultadoPartidaBean.setCodigoError(Errores.WS_PARAMETRO_INCORRECTO_TOMO);
			return resultadoPartidaBean; 
		}
		if(numeroFolio==null || numeroFolio.trim().length()==0)
		{
			resultadoPartidaBean.setCodigoError(Errores.WS_PARAMETRO_INCORRECTO_FOLIO);
			return resultadoPartidaBean; 
		}
		
		try
		{
			AccesoService accesoService = new AccesoServiceImpl();
			UsuarioBean usuarioBean = accesoService.validarIngreso(usuario, clave, ipRemota, session_id);
			
			if(!(usuarioBean.getPerfilId()==Constantes.PERFIL_INDIVIDUAL_EXTERNO || usuarioBean.getPerfilId()==Constantes.PERFIL_AFILIADO_EXTERNO || usuarioBean.getPerfilId()==Constantes.PERFIL_ADMIN_ORG_EXT))
			{
				resultadoPartidaBean.setCodigoError(Errores.WS_ACCESO_PERFIL_INCORRECTO);
				return resultadoPartidaBean;
			}
						
			InputBusqDirectaBean inputBusqDirectaBean = new InputBusqDirectaBean();
			
			inputBusqDirectaBean.setCodGrupoLibroArea(Constantes.GRUPO_LIBRO_AREA_BUSQUEDA_RMC);
			inputBusqDirectaBean.setRegPubId(idRegistroPublico);
			inputBusqDirectaBean.setOficRegId(idOficinaRegistral);
			inputBusqDirectaBean.setLibro(libro);
			inputBusqDirectaBean.setTomo(numeroTomo);
			inputBusqDirectaBean.setFolio(numeroFolio);
			
			
			ConsultarPartidaDirectaService consultarPartidaDirectaService = new ConsultarPartidaDirectaServiceImpl();
			
			FormOutputBuscarPartida formOutputBuscarPartida = consultarPartidaDirectaService.busquedaDirectaPorTomoFolioRMC(ConsultarPartidaDirectaService.MEDIO_WEB_SERVICE, inputBusqDirectaBean, usuarioBean, ipRemota);
			
			ArrayList<gob.pe.sunarp.extranet.publicidad.bean.PartidaBean> respuestaService = formOutputBuscarPartida.getResultado();
			
			PartidaBean[] partidaBeans = new PartidaBean[respuestaService.size()];
			for(int x=0; respuestaService!=null && x<respuestaService.size(); x++){
				
				gob.pe.sunarp.extranet.publicidad.bean.PartidaBean partidaBean =  respuestaService.get(x);
				
				PartidaBean partidaBeanTemp = new PartidaBean();
				partidaBeanTemp.setDescripcionAreaRegistral(partidaBean.getAreaRegistralDescripcion());
				partidaBeanTemp.setDescripcionOficinaRegistral(partidaBean.getOficRegDescripcion());
				partidaBeanTemp.setDescripcionRegistro(partidaBean.getLibroDescripcion());
				partidaBeanTemp.setDescripcionZonaRegistral(partidaBean.getRegPubDescripcion());
				partidaBeanTemp.setNumeroFicha(partidaBean.getFichaId());
				partidaBeanTemp.setNumeroFolio(numeroFolio);
				partidaBeanTemp.setNumeroPartida(partidaBean.getNumPartida());
				partidaBeanTemp.setNumeroTomo(numeroTomo);
				
				partidaBeans[x] = partidaBeanTemp;
				
			}
			resultadoPartidaBean.setMontoPagado(String.valueOf(formOutputBuscarPartida.getTarifa()));
			resultadoPartidaBean.setPartidaBeans(partidaBeans);
			
		}catch (CustomException e) 
		{
			if(e.getCodigoError().equals("E00001")){
				resultadoPartidaBean.setCodigoError(Errores.WS_ACCESO_USUARIO_CLAVE_INCORRECTO);
			}else if(e.getCodigoError().equals("E09005"))
			{
				resultadoPartidaBean.setCodigoError(Errores.WS_ACCESO_ERROR_INTEGRIDAD);
			}else if(e.getCodigoError().equals("E20004"))
			{
				resultadoPartidaBean.setCodigoError(Errores.WS_ACCESO_CUENTA_DESHABILITADA);
			}else if(e.getCodigoError().equals("E08007"))
			{
				resultadoPartidaBean.setCodigoError(Errores.WS_ACCESO_CUENTA_SESSION_ACTIVA);
			}else if(e.getCodigoError().equals("E08008"))
			{
				resultadoPartidaBean.setCodigoError(Errores.WS_ACCESO_RANGO_IP_NO_PERMITIDA);
			}else if(e.getCodigoError().equals("E08010"))
			{
				resultadoPartidaBean.setCodigoError(Errores.WS_ACCESO_NO_EXISTE_SESSION);
			}else if(e.getCodigoError().equals("E20001"))
			{
				resultadoPartidaBean.setCodigoError(Errores.WS_ACCESO_PERFIL_INCORRECTO);
			}else if(e.getCodigoError().equals("E20002"))
			{
				resultadoPartidaBean.setCodigoError(Errores.WS_ACCESO_USUARIO_NO_TIENE_SALDO);
			}else if(e.getCodigoError().equals("E08009"))
			{
				resultadoPartidaBean.setCodigoError(Errores.WS_ACCESO_FERIADO_NO_PERMITIDO);
			}else if(e.getCodigoError().equals("E20011"))
			{
				resultadoPartidaBean.setCodigoError(Errores.WS_ACCESO_CUENTA_NO_REGISTRADA);
			}else if(e.getCodigoError().equals("E20003"))
			{
				resultadoPartidaBean.setCodigoError(Errores.WS_ACCESO_PN_NO_REGISTRADA);
			}else if(e.getCodigoError().equals("E20008"))
			{
				resultadoPartidaBean.setCodigoError(Errores.WS_ACCESO_PJ_NO_REGISTRADA);
			}else if(e.getCodigoError().equals("E20009"))
			{
				resultadoPartidaBean.setCodigoError(Errores.WS_ACCESO_NO_TIENE_CONTRATO);
			}else if(e.getCodigoError().equals("E70002"))
			{
				resultadoPartidaBean.setCodigoError(Errores.WS_BUSQUEDA_NO_TIENE_RESULTADOS);
			}else{
				resultadoPartidaBean.setCodigoError(Errores.WS_GENERICO_APLICACION);
			}
			
			e.printStackTrace();
		}catch (ValidacionException e) 
		{
			resultadoPartidaBean.setCodigoError(Errores.WS_BUSQUEDA_LIMITE_RESULTADO_EXCEDIDO);
			e.printStackTrace();
			
		}catch (DBException dbe) {
			dbe.printStackTrace();
		} catch (Throwable ex) {
			
			ex.printStackTrace();
		}
		
		return resultadoPartidaBean;
	}
}
