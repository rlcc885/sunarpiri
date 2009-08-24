package gob.pe.sunarp.extranet.webservices;

import java.util.ArrayList;

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
import gob.pe.sunarp.extranet.webservices.bean.ActoBean;
import gob.pe.sunarp.extranet.webservices.bean.BienBean;
import gob.pe.sunarp.extranet.webservices.bean.DocumentoBean;
import gob.pe.sunarp.extranet.webservices.bean.FormaCondicionBean;
import gob.pe.sunarp.extranet.webservices.bean.ParticipanteBean;
import gob.pe.sunarp.extranet.webservices.bean.PartidaRmcBean;
import gob.pe.sunarp.extranet.webservices.bean.ResultadoDetallePartidaRmcBean;
import gob.pe.sunarp.extranet.webservices.bean.ResultadoPartidaBean;
import gob.pe.sunarp.extranet.webservices.bean.TituloBean;
import gob.pe.sunarp.extranet.webservices.bean.TituloPendienteBean;
import gob.pe.sunarp.extranet.webservices.util.Errores;

public class BusquedaDetallePartidaServiceProvider extends ServiceProvider
{
	public ResultadoDetallePartidaRmcBean busquedaPorNumeroPartida(String usuario, String clave, String numeroPartida, String idRegistroPublico, String idOficinaRegistral )
	{
		ResultadoDetallePartidaRmcBean resultadoDetallePartidaRmcBean = new ResultadoDetallePartidaRmcBean();
		String session_id = "";
		String ipRemota = "";
		try
		{
			session_id = obtenerAMIdSesion(getServletRequest());
			ipRemota = obtenerIPRemota(getServletRequest());
		}catch (Exception e) 
		{
			e.printStackTrace();
			
			resultadoDetallePartidaRmcBean.setCodigoError(Errores.WS_GENERICO_APLICACION);
			return resultadoDetallePartidaRmcBean;
		}	
		
		
		
		if(usuario==null || usuario.trim().length()==0){
			resultadoDetallePartidaRmcBean.setCodigoError(Errores.WS_PARAMETRO_INCORRECTO_USUARIO);
			return resultadoDetallePartidaRmcBean; 
		}
		if(clave==null || clave.trim().length()==0){
			resultadoDetallePartidaRmcBean.setCodigoError(Errores.WS_PARAMETRO_INCORRECTO_CLAVE);
			return resultadoDetallePartidaRmcBean; 
		}
		if(numeroPartida==null || numeroPartida.trim().length()==0){
			resultadoDetallePartidaRmcBean.setCodigoError(Errores.WS_PARAMETRO_INCORRECTO_NUMERO_PARTIDA);
			return resultadoDetallePartidaRmcBean; 
		}
		if(idRegistroPublico==null || idRegistroPublico.trim().length()==0){
			resultadoDetallePartidaRmcBean.setCodigoError(Errores.WS_PARAMETRO_INCORRECTO_REGISTRO_PUBLICO_ID);
			return resultadoDetallePartidaRmcBean; 
		}
		
		if(idOficinaRegistral==null || idOficinaRegistral.trim().length()==0){
			resultadoDetallePartidaRmcBean.setCodigoError(Errores.WS_PARAMETRO_INCORRECTO_OFICINA_REGISTRAL_ID);
			return resultadoDetallePartidaRmcBean; 
		}
		try
		{
			
			AccesoService accesoService = new AccesoServiceImpl();
			UsuarioBean usuarioBean = accesoService.validarIngreso(usuario, clave, ipRemota, session_id);
			
			if(!(usuarioBean.getPerfilId()==Constantes.PERFIL_INDIVIDUAL_EXTERNO || usuarioBean.getPerfilId()==Constantes.PERFIL_AFILIADO_EXTERNO || usuarioBean.getPerfilId()==Constantes.PERFIL_ADMIN_ORG_EXT))
			{
				resultadoDetallePartidaRmcBean.setCodigoError(Errores.WS_ACCESO_PERFIL_INCORRECTO);
				return resultadoDetallePartidaRmcBean;
			}
			
			InputBusqDirectaBean inputBusqDirectaBean = new InputBusqDirectaBean();
			
			
			inputBusqDirectaBean.setNumeroPartida(numeroPartida);
			inputBusqDirectaBean.setRegPubId(idRegistroPublico);
			inputBusqDirectaBean.setOficRegId(idOficinaRegistral);
			inputBusqDirectaBean.setArea_reg_id(Constantes.AREA_PROPIEDAD_RMC);
			
			FormOutputBuscarPartida formOutputBuscarPartida = new FormOutputBuscarPartida();  
			
			
			ConsultarPartidaDirectaService consultarPartidaDirectaService = new ConsultarPartidaDirectaServiceImpl();
			
			formOutputBuscarPartida = consultarPartidaDirectaService.busquedaDetallePartidaRMC(ConsultarPartidaDirectaService.MEDIO_WEB_SERVICE, inputBusqDirectaBean, usuarioBean, ipRemota);
			
			PartidaRmcBean partidaRmcBean = new PartidaRmcBean();
			partidaRmcBean.setNumeroPartida(formOutputBuscarPartida.getPartidaBean().getNumPartida());
			partidaRmcBean.setNumeroPartidaMigrada(formOutputBuscarPartida.getPartidaBean().getNumPartidaMigrado());
			
			ArrayList<gob.pe.sunarp.extranet.publicidad.bean.TituloPendienteBean> resultado = formOutputBuscarPartida.getResultadoTituloPendientesRMC();
			TituloPendienteBean[] tituloPendiente = new TituloPendienteBean[resultado.size()]; 
			for(int i=0; resultado!=null && i<resultado.size();i++)
			{
				gob.pe.sunarp.extranet.publicidad.bean.TituloPendienteBean tituloP = resultado.get(i);
				TituloPendienteBean tituloTemp = new TituloPendienteBean();
				tituloTemp.setDescripcionRegistroPublico(tituloP.getZonaReg());
				tituloTemp.setNumeroTitulo(tituloP.getNroTitulo());
				tituloTemp.setFechaPresentacion(tituloP.getFechaPresentacion().toString());
				tituloTemp.setFechaVencimiento(tituloP.getFechaVencimiento().toString());
				tituloTemp.setDescripcionActo(tituloP.getActoDescripcion());
				tituloTemp.setDescripcionEstado(tituloP.getEstadoDescripcion());
				tituloPendiente[i] = tituloTemp; 
			}
			partidaRmcBean.setTituloPendienteBeans(tituloPendiente);
			
			ArrayList<gob.pe.sunarp.extranet.publicidad.bean.AsientoRMCBean> resultadoAsiento = formOutputBuscarPartida.getResultadoActosRMC();
			ActoBean[] actoBean = new ActoBean[resultadoAsiento.size()];
			for(int i=0;resultadoAsiento!=null && i<resultadoAsiento.size();i++)
			{
				gob.pe.sunarp.extranet.publicidad.bean.AsientoRMCBean asiento = resultadoAsiento.get(i);
				ActoBean actoTemp = new ActoBean();
				actoTemp.setDescripcionActo(asiento.getActoBean().getActoDescripcion());
				actoTemp.setFechaActoConstitutivo(asiento.getActoBean().getFechaConstitutivo());
				actoTemp.setAnoplazo(asiento.getActoBean().getAnoPlazo());
				actoTemp.setMesplazo(asiento.getActoBean().getMesPlazo());
				actoTemp.setDiaplazo(asiento.getActoBean().getDiaPlazo());
				actoTemp.setMontoAfectacion(String.valueOf(asiento.getActoBean().getMontoAfectacion()));
				
				FormaCondicionBean formaCondicion = new FormaCondicionBean();
				formaCondicion.setDescripcionCondicion(asiento.getActoBean().getCondicion());
				formaCondicion.setDescripcionForma(asiento.getActoBean().getForma());
				
				actoTemp.setFormaCondicionBean(formaCondicion);
				
				ArrayList<gob.pe.sunarp.extranet.publicidad.bean.BienBean> resultadoBien = asiento.getBienes();
				BienBean[] bienBean = new BienBean[resultadoBien.size()];
				for(int j=0;resultadoBien!=null && j<resultadoBien.size();j++)
				{
					gob.pe.sunarp.extranet.publicidad.bean.BienBean bien = resultadoBien.get(j);
					BienBean bienTemp = new BienBean();
					bienTemp.setDescripcionBien(bien.getDescripcion());
					bienTemp.setValorizacion(bien.getMonto());
					bienBean[j] = bienTemp;
				}
				actoTemp.setBienBeans(bienBean);
				ArrayList<gob.pe.sunarp.extranet.publicidad.bean.DocumentoFuncionarioBean> resultadoDoc = asiento.getDocumentosFuncionarios();
				DocumentoBean[] documentoBean = new DocumentoBean[resultadoDoc.size()];
				
				for(int k=0; resultadoDoc!=null && k<resultadoDoc.size();k++)
				{
					gob.pe.sunarp.extranet.publicidad.bean.DocumentoFuncionarioBean funcionario = resultadoDoc.get(k);
					DocumentoBean documentoTemp = new DocumentoBean();
					documentoTemp.setDocumento(funcionario.getDocumento());
					documentoTemp.setFecha(funcionario.getFecha().toString());
					documentoTemp.setFuncionario(funcionario.getFuncionario());
					
					documentoBean[k] = documentoTemp;
					
				}
				actoTemp.setDocumentoBeans(documentoBean);
				
				ArrayList<gob.pe.sunarp.extranet.publicidad.bean.ParticipanteBean> resultadoParticipante = asiento.getParticipantes();
				ParticipanteBean[] participanteBean = new ParticipanteBean[resultadoParticipante.size()];
				for(int p=0;resultadoParticipante!=null && p<resultadoParticipante.size(); p++)
				{
					gob.pe.sunarp.extranet.publicidad.bean.ParticipanteBean participante = resultadoParticipante.get(p);
					ParticipanteBean participanteTemp = new ParticipanteBean();
					participanteTemp.setNombreParticipante(participante.getNombre());
					participanteTemp.setApellidoPaternoParticipante(participante.getApellidoPaterno());
					participanteTemp.setApellidoMaternoParticipante(participante.getApellidoMaterno());
					participanteTemp.setDescripcionAbreviadaTipoDocumento(participante.getDescripcionTipoDocumento());
					participanteTemp.setNumeroDocumento(participante.getNumeroDocumento());
					participanteTemp.setDescripcionTipoParticipacion(participante.getDescripcionTipoParticipacion());
					participanteTemp.setDomicilio(participante.getDescripcionDomicilio());
					participanteTemp.setRazonSocial(participante.getRazonSocial());
					
					participanteBean[p]=participanteTemp;
				}
				actoTemp.setParticipanteBeans(participanteBean);
				
				TituloBean tituloBean = new TituloBean();
				tituloBean.setNumeroTitulo(asiento.getTituloBean().getTitulo());
				tituloBean.setNumeroOrden(asiento.getTituloBean().getNumeroOrden());
				tituloBean.setFechaPresentacion(String.valueOf(asiento.getTituloBean().getFechaPresentacion()));
				tituloBean.setDerechosPagados(String.valueOf(asiento.getTituloBean().getMontoPagado()));
				tituloBean.setNumeroRecibos(asiento.getTituloBean().getNumeroRecibo());
				tituloBean.setDescripcionOficinaRegistral(asiento.getTituloBean().getOficReg_nombre());
				
				actoTemp.setTituloBeans(tituloBean);
				actoBean[i]=actoTemp;
			}
			partidaRmcBean.setActoBeans(actoBean);
			resultadoDetallePartidaRmcBean.setMontoPagado(String.valueOf(formOutputBuscarPartida.getTarifa()));
			resultadoDetallePartidaRmcBean.setPartidaRmcBeans(partidaRmcBean);			
			
		}catch (CustomException e) 
		{
			if(e.getCodigoError().equals("E00001")){
				resultadoDetallePartidaRmcBean.setCodigoError(Errores.WS_ACCESO_USUARIO_CLAVE_INCORRECTO);
			}else if(e.getCodigoError().equals("E09005"))
			{
				resultadoDetallePartidaRmcBean.setCodigoError(Errores.WS_ACCESO_ERROR_INTEGRIDAD);
			}else if(e.getCodigoError().equals("E20004"))
			{
				resultadoDetallePartidaRmcBean.setCodigoError(Errores.WS_ACCESO_CUENTA_DESHABILITADA);
			}else if(e.getCodigoError().equals("E08007"))
			{
				resultadoDetallePartidaRmcBean.setCodigoError(Errores.WS_ACCESO_CUENTA_SESSION_ACTIVA);
			}else if(e.getCodigoError().equals("E08008"))
			{
				resultadoDetallePartidaRmcBean.setCodigoError(Errores.WS_ACCESO_RANGO_IP_NO_PERMITIDA);
			}else if(e.getCodigoError().equals("E08010"))
			{
				resultadoDetallePartidaRmcBean.setCodigoError(Errores.WS_ACCESO_NO_EXISTE_SESSION);
			}else if(e.getCodigoError().equals("E20001"))
			{
				resultadoDetallePartidaRmcBean.setCodigoError(Errores.WS_ACCESO_PERFIL_INCORRECTO);
			}else if(e.getCodigoError().equals("E20002"))
			{
				resultadoDetallePartidaRmcBean.setCodigoError(Errores.WS_ACCESO_USUARIO_NO_TIENE_SALDO);
			}else if(e.getCodigoError().equals("E08009"))
			{
				resultadoDetallePartidaRmcBean.setCodigoError(Errores.WS_ACCESO_FERIADO_NO_PERMITIDO);
			}else if(e.getCodigoError().equals("E20011"))
			{
				resultadoDetallePartidaRmcBean.setCodigoError(Errores.WS_ACCESO_CUENTA_NO_REGISTRADA);
			}else if(e.getCodigoError().equals("E20003"))
			{
				resultadoDetallePartidaRmcBean.setCodigoError(Errores.WS_ACCESO_PN_NO_REGISTRADA);
			}else if(e.getCodigoError().equals("E20008"))
			{
				resultadoDetallePartidaRmcBean.setCodigoError(Errores.WS_ACCESO_PJ_NO_REGISTRADA);
			}else if(e.getCodigoError().equals("E20009"))
			{
				resultadoDetallePartidaRmcBean.setCodigoError(Errores.WS_ACCESO_NO_TIENE_CONTRATO);
			}else if(e.getCodigoError().equals("E70002"))
			{
				resultadoDetallePartidaRmcBean.setCodigoError(Errores.WS_BUSQUEDA_NO_TIENE_RESULTADOS);
			}else{
				resultadoDetallePartidaRmcBean.setCodigoError(Errores.WS_GENERICO_APLICACION);
			}
			
			e.printStackTrace();
		}catch (ValidacionException e) 
		{
			resultadoDetallePartidaRmcBean.setCodigoError(Errores.WS_BUSQUEDA_LIMITE_RESULTADO_EXCEDIDO);
			e.printStackTrace();
			
		}catch (DBException dbe) {
			
			dbe.printStackTrace();
		} catch (Throwable ex) {
			
			ex.printStackTrace();
		}
		
		return resultadoDetallePartidaRmcBean;
	}
}
