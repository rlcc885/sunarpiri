package gob.pe.sunarp.extranet.webservices;

import java.util.ArrayList;

import com.jcorporate.expresso.core.db.DBException;

import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.publicidad.bean.FormOutputBuscarPartida;
import gob.pe.sunarp.extranet.publicidad.bean.InputBusqIndirectaBean;
import gob.pe.sunarp.extranet.publicidad.service.AccesoService;
import gob.pe.sunarp.extranet.publicidad.service.ConsultarNacionalIndicePartidasPersonaJuridicaService;
import gob.pe.sunarp.extranet.publicidad.service.ConsultarNacionalIndicePartidasPersonaNaturalService;
import gob.pe.sunarp.extranet.publicidad.service.ConsultarNacionalIndicePartidasxBienesService;
import gob.pe.sunarp.extranet.publicidad.service.ConsultarNacionalIndicePartidasxTipoNumeroDocumentoService;
import gob.pe.sunarp.extranet.publicidad.service.impl.AccesoServiceImpl;
import gob.pe.sunarp.extranet.publicidad.service.impl.ConsultarNacionalIndicePartidasPersonaJuridicaServiceImpl;
import gob.pe.sunarp.extranet.publicidad.service.impl.ConsultarNacionalIndicePartidasPersonaNaturalServiceImpl;
import gob.pe.sunarp.extranet.publicidad.service.impl.ConsultarNacionalIndicePartidasxBienesServiceImpl;
import gob.pe.sunarp.extranet.publicidad.service.impl.ConsultarNacionalIndicePartidasxTipoNumeroDocumentoServiceImpl;
import gob.pe.sunarp.extranet.util.Constantes;
import gob.pe.sunarp.extranet.util.ValidacionException;
import gob.pe.sunarp.extranet.webservices.bean.PartidaBean;
import gob.pe.sunarp.extranet.webservices.bean.ResultadoPartidaBean;
import gob.pe.sunarp.extranet.webservices.util.Errores;

public class BusquedaNacionalIndicesServiceProvider extends ServiceProvider
{
	public ResultadoPartidaBean	busquedaRmcPorPersonaNatural(String usuario, String clave, String apellidoPaterno, String apellidoMaterno, String nombre)
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
		
		if(usuario==null || usuario.trim().length()==0)
		{
			resultadoPartidaBean.setCodigoError(Errores.WS_PARAMETRO_INCORRECTO_USUARIO);
			return resultadoPartidaBean; 
		}	
		if(clave==null || clave.trim().length()==0)
		{
			resultadoPartidaBean.setCodigoError(Errores.WS_PARAMETRO_INCORRECTO_CLAVE);
			return resultadoPartidaBean; 
		}
		if(apellidoPaterno==null || apellidoPaterno.trim().length()==0)
		{
			resultadoPartidaBean.setCodigoError(Errores.WS_PARAMETRO_INCORRECTO_APE_PAT);
			return resultadoPartidaBean;
		}
		if(apellidoMaterno==null || apellidoMaterno.trim().length()==0)
		{
			if(nombre==null || nombre.trim().length()==0)
			{
				resultadoPartidaBean.setCodigoError(Errores.WS_PARAMETRO_NOMBRE_APEMAT_NO_INGRESADO);
				return resultadoPartidaBean;
			}
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
			
			InputBusqIndirectaBean inputBusqIndirectaBean = new InputBusqIndirectaBean(); 
			
			inputBusqIndirectaBean.setArea1ApeMat(apellidoMaterno);
			inputBusqIndirectaBean.setArea1ApePat(apellidoPaterno);
			inputBusqIndirectaBean.setArea1Nombre(nombre);
			inputBusqIndirectaBean.setComboArea(Constantes.AREA_PROPIEDAD_RMC);
			inputBusqIndirectaBean.setCodGrupoLibroArea(Constantes.CODIGO_LIBRO_AREA_MACIONAL_RMC);
			
			FormOutputBuscarPartida formOutputBuscarPartida = new FormOutputBuscarPartida();  
			
			ConsultarNacionalIndicePartidasPersonaNaturalService consultaNacionalPartidasPN = new ConsultarNacionalIndicePartidasPersonaNaturalServiceImpl();
			formOutputBuscarPartida = consultaNacionalPartidasPN.busquedaIndicePersonaNaturalSIGC(ConsultarNacionalIndicePartidasPersonaNaturalService.MEDIO_WEB_SERVICE, inputBusqIndirectaBean, usuarioBean, ipRemota, session_id);
			
			ArrayList<gob.pe.sunarp.extranet.publicidad.bean.PartidaBean> respuestaService = formOutputBuscarPartida.getResultado();
			
			PartidaBean[] partidaBeans = new PartidaBean[respuestaService.size()];
			for(int i=0; respuestaService!=null && i < respuestaService.size();i++ )
			{
				gob.pe.sunarp.extranet.publicidad.bean.PartidaBean partidaBean = respuestaService.get(i);
				
				PartidaBean  partidaBeanTemp = new PartidaBean();
				partidaBeanTemp.setDescripcionZonaRegistral(partidaBean.getRegPubDescripcion());
				partidaBeanTemp.setDescripcionOficinaRegistral(partidaBean.getOficRegDescripcion());
				partidaBeanTemp.setNumeroPartida(partidaBean.getNumPartida());
				partidaBeanTemp.setNumeroFicha(partidaBean.getFichaId());
				partidaBeanTemp.setNumeroTomo(partidaBean.getTomoId());
				partidaBeanTemp.setNumeroFolio(partidaBean.getFojaId());
				partidaBeanTemp.setDescripcionAreaRegistral(partidaBean.getAreaRegistralDescripcion());
				partidaBeanTemp.setParticipante(partidaBean.getParticipacionDescripcion());
				partidaBeanTemp.setDocumentoIdentidad(partidaBean.getParticipanteTipoDocumentoDescripcion());
				partidaBeanTemp.setNumeroDocumento(partidaBean.getParticipanteNumeroDocumento());
				
				partidaBeans[i] = partidaBeanTemp;
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
	public ResultadoPartidaBean	busquedaPorPersonaJuridica(String usuario, String clave, String razonSocial, String siglas)
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
		
		if(usuario==null || usuario.trim().length()==0)
		{
			resultadoPartidaBean.setCodigoError(Errores.WS_PARAMETRO_INCORRECTO_USUARIO);
			return resultadoPartidaBean; 
		}	
		if(clave==null || clave.trim().length()==0)
		{
			resultadoPartidaBean.setCodigoError(Errores.WS_PARAMETRO_INCORRECTO_CLAVE);
			return resultadoPartidaBean; 
		}
		if(razonSocial==null || razonSocial.trim().length()==0)
		{
			if(siglas==null || siglas.trim().length()==0)
			{
				resultadoPartidaBean.setCodigoError(Errores.WS_PARAMETRO_SIGLA_RAZON_NO_INGRESADO);
				return resultadoPartidaBean;
			}
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
			
			InputBusqIndirectaBean inputBusqIndirectaBean = new InputBusqIndirectaBean(); 
			
			inputBusqIndirectaBean.setArea2Razon1(razonSocial);
			inputBusqIndirectaBean.setArea2Siglas(siglas);
			inputBusqIndirectaBean.setComboArea(Constantes.AREA_PROPIEDAD_RMC);
			inputBusqIndirectaBean.setCodGrupoLibroArea(Constantes.CODIGO_LIBRO_AREA_MACIONAL_RMC);
			
			FormOutputBuscarPartida formOutputBuscarPartida = new FormOutputBuscarPartida();  
			
			ConsultarNacionalIndicePartidasPersonaJuridicaService consultaIndicePartidasPJ = new ConsultarNacionalIndicePartidasPersonaJuridicaServiceImpl();
			
			formOutputBuscarPartida = consultaIndicePartidasPJ.busquedaIndicePersonaJuridicaSIGC(ConsultarNacionalIndicePartidasPersonaJuridicaService.MEDIO_WEB_SERVICE, inputBusqIndirectaBean, usuarioBean, ipRemota, session_id);
			
			ArrayList<gob.pe.sunarp.extranet.publicidad.bean.PartidaBean> respuestaService = formOutputBuscarPartida.getResultado();
			
			PartidaBean[] partidaBeans = new PartidaBean[respuestaService.size()];
			for(int i=0; respuestaService!=null && i < respuestaService.size();i++ )
			{
				gob.pe.sunarp.extranet.publicidad.bean.PartidaBean partidaBean = respuestaService.get(i);
				
				PartidaBean  partidaBeanTemp = new PartidaBean();
				partidaBeanTemp.setDescripcionZonaRegistral(partidaBean.getRegPubDescripcion());
				partidaBeanTemp.setDescripcionOficinaRegistral(partidaBean.getOficRegDescripcion());
				partidaBeanTemp.setNumeroPartida(partidaBean.getNumPartida());
				partidaBeanTemp.setNumeroFicha(partidaBean.getFichaId());
				partidaBeanTemp.setNumeroTomo(partidaBean.getTomoId());
				partidaBeanTemp.setNumeroFolio(partidaBean.getFojaId());
				partidaBeanTemp.setDescripcionAreaRegistral(partidaBean.getAreaRegistralDescripcion());
				partidaBeanTemp.setParticipante(partidaBean.getParticipacionDescripcion());
				partidaBeanTemp.setDocumentoIdentidad(partidaBean.getParticipanteTipoDocumentoDescripcion());
				partidaBeanTemp.setNumeroDocumento(partidaBean.getParticipanteNumeroDocumento());
				
				partidaBeans[i] = partidaBeanTemp;
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
	public ResultadoPartidaBean	busquedaPorTipoNumeroDocumento(String usuario, String clave, String codigoTipoDocumento, String numeroDocumento)
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
		
		if(usuario==null || usuario.trim().length()==0)
		{
			resultadoPartidaBean.setCodigoError(Errores.WS_PARAMETRO_INCORRECTO_USUARIO);
			return resultadoPartidaBean; 
		}	
		if(clave==null || clave.trim().length()==0)
		{
			resultadoPartidaBean.setCodigoError(Errores.WS_PARAMETRO_INCORRECTO_CLAVE);
			return resultadoPartidaBean; 
		}
		if(codigoTipoDocumento==null || codigoTipoDocumento.trim().length()==0)
		{
			resultadoPartidaBean.setCodigoError(Errores.WS_PARAMETRO_INCORRECTO_TIPO_DOCUMENTO);
			return resultadoPartidaBean;
		}
		if(numeroDocumento==null || numeroDocumento.trim().length()==0)
		{
			resultadoPartidaBean.setCodigoError(Errores.WS_PARAMETRO_INCORRECTO_NUMERO_DOCUMENTO);
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
			
			InputBusqIndirectaBean inputBusqIndirectaBean = new InputBusqIndirectaBean(); 
			
			inputBusqIndirectaBean.setTipoDocumento(codigoTipoDocumento);
			inputBusqIndirectaBean.setNumeroDocumento(numeroDocumento);
			inputBusqIndirectaBean.setComboArea(Constantes.AREA_PROPIEDAD_RMC);
			inputBusqIndirectaBean.setCodGrupoLibroArea(Constantes.CODIGO_LIBRO_AREA_MACIONAL_RMC);
			
			FormOutputBuscarPartida formOutputBuscarPartida = new FormOutputBuscarPartida();  
			
			ConsultarNacionalIndicePartidasxTipoNumeroDocumentoService consultaNacionalPartidasDoc = new ConsultarNacionalIndicePartidasxTipoNumeroDocumentoServiceImpl();
			
			formOutputBuscarPartida = consultaNacionalPartidasDoc.busquedaIndiceTipoNumeroDocumentoSIGC(ConsultarNacionalIndicePartidasxTipoNumeroDocumentoService.MEDIO_WEB_SERVICE, inputBusqIndirectaBean, usuarioBean, ipRemota, session_id);
			
			ArrayList<gob.pe.sunarp.extranet.publicidad.bean.PartidaBean> respuestaService = formOutputBuscarPartida.getResultado();
			
			PartidaBean[] partidaBeans = new PartidaBean[respuestaService.size()];
			for(int i=0; respuestaService!=null && i < respuestaService.size();i++ )
			{
				gob.pe.sunarp.extranet.publicidad.bean.PartidaBean partidaBean = respuestaService.get(i);
				
				PartidaBean  partidaBeanTemp = new PartidaBean();
				partidaBeanTemp.setDescripcionZonaRegistral(partidaBean.getRegPubDescripcion());
				partidaBeanTemp.setDescripcionOficinaRegistral(partidaBean.getOficRegDescripcion());
				partidaBeanTemp.setNumeroPartida(partidaBean.getNumPartida());
				partidaBeanTemp.setNumeroFicha(partidaBean.getFichaId());
				partidaBeanTemp.setNumeroTomo(partidaBean.getTomoId());
				partidaBeanTemp.setNumeroFolio(partidaBean.getFojaId());
				partidaBeanTemp.setDescripcionAreaRegistral(partidaBean.getAreaRegistralDescripcion());
				partidaBeanTemp.setParticipante(partidaBean.getParticipacionDescripcion());
				partidaBeanTemp.setDocumentoIdentidad(partidaBean.getParticipanteTipoDocumentoDescripcion());
				partidaBeanTemp.setNumeroDocumento(partidaBean.getParticipanteNumeroDocumento());
				
				partidaBeans[i] = partidaBeanTemp;
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
	public ResultadoPartidaBean	busquedaPorBien(String usuario, String clave, String numeroPlaca, String numeroMatricula, String nombre, String numeroSerie)
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
		
		if(usuario==null || usuario.trim().length()==0)
		{
			resultadoPartidaBean.setCodigoError(Errores.WS_PARAMETRO_INCORRECTO_USUARIO);
			return resultadoPartidaBean; 
		}	
		if(clave==null || clave.trim().length()==0)
		{
			resultadoPartidaBean.setCodigoError(Errores.WS_PARAMETRO_INCORRECTO_CLAVE);
			return resultadoPartidaBean; 
		}
		if(numeroPlaca==null || numeroPlaca.trim().length()==0)
		{
			if(numeroMatricula==null || numeroMatricula.trim().length()==0)
			{
				if(nombre==null || nombre.trim().length()==0)
				{
					if(numeroSerie==null || numeroSerie.trim().length()==0)
					{
						resultadoPartidaBean.setCodigoError(Errores.WS_PARAMETRO_PLACA_OTRO_NO_INGRESADO);
						return resultadoPartidaBean;
					}
				}
			}
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
			
			InputBusqIndirectaBean inputBusqIndirectaBean = new InputBusqIndirectaBean(); 
			
			inputBusqIndirectaBean.setNumeroPlaca(numeroPlaca);
			inputBusqIndirectaBean.setNumeroMatricula(numeroMatricula);
			inputBusqIndirectaBean.setNombreBien(nombre);
			inputBusqIndirectaBean.setNumeroSerie(numeroSerie);
			inputBusqIndirectaBean.setCodGrupoLibroArea(Constantes.CODIGO_LIBRO_AREA_MACIONAL_RMC);
			inputBusqIndirectaBean.setSedesElegidas(Constantes.SEDES_PORDEFECTO_BUSQUEDA_RMC);
			inputBusqIndirectaBean.setComboArea(Constantes.AREA_PROPIEDAD_RMC);
			
			FormOutputBuscarPartida formOutputBuscarPartida = new FormOutputBuscarPartida();  
			
			ConsultarNacionalIndicePartidasxBienesService consultaNacionalPartidasBien = new ConsultarNacionalIndicePartidasxBienesServiceImpl();
			
			formOutputBuscarPartida = consultaNacionalPartidasBien.busquedaIndiceBienesSIGC(ConsultarNacionalIndicePartidasxBienesService.MEDIO_WEB_SERVICE, inputBusqIndirectaBean, usuarioBean, ipRemota, session_id);
			
			ArrayList<gob.pe.sunarp.extranet.publicidad.bean.PartidaBean> respuestaService = formOutputBuscarPartida.getResultado();
			
			PartidaBean[] partidaBeans = new PartidaBean[respuestaService.size()];
			for(int i=0; respuestaService!=null && i < respuestaService.size();i++ )
			{
				gob.pe.sunarp.extranet.publicidad.bean.PartidaBean partidaBean = respuestaService.get(i);
				
				PartidaBean  partidaBeanTemp = new PartidaBean();
				partidaBeanTemp.setDescripcionZonaRegistral(partidaBean.getRegPubDescripcion());
				partidaBeanTemp.setDescripcionOficinaRegistral(partidaBean.getOficRegDescripcion());
				partidaBeanTemp.setNumeroPartida(partidaBean.getNumPartida());
				partidaBeanTemp.setNumeroFicha(partidaBean.getFichaId());
				partidaBeanTemp.setNumeroTomo(partidaBean.getTomoId());
				partidaBeanTemp.setNumeroFolio(partidaBean.getFojaId());
				partidaBeanTemp.setDescripcionAreaRegistral(partidaBean.getAreaRegistralDescripcion());
				partidaBeanTemp.setParticipante(partidaBean.getParticipacionDescripcion());
				partidaBeanTemp.setDocumentoIdentidad(partidaBean.getParticipanteTipoDocumentoDescripcion());
				partidaBeanTemp.setNumeroDocumento(partidaBean.getParticipanteNumeroDocumento());
				
				partidaBeans[i] = partidaBeanTemp;
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
