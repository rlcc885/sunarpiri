package gob.pe.sunarp.extranet.reniec.bo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import gob.pe.sunarp.extranet.reniec.bean.*;
import gob.pe.sunarp.extranet.solicitud.inscripcion.bean.SolicitudInscripcion;
import gob.pe.sunarp.extranet.solicitud.inscripcion.bean.PersonaNatural;

public class ValidacionIdentidadBo {

	public String validarIdentidad(SolicitudInscripcion sBean) throws Exception
	{
		String codigo = "";
		String respuesta = "";
		String sesion = "";
		PersonaNatural personaNatural = new PersonaNatural();
		ConsultaBean consultaBean = new ConsultaBean();
		ServicioBo servicioBo = new ServicioBo();
		IdentificacionBean iBean = new IdentificacionBean();
		
		iBean = (IdentificacionBean)obtenerParametros();
		
		try{
			// numero de sesion
			sesion = servicioBo.AuthenticationServicio(iBean);
			if (sesion.trim().length() <= 3){
				respuesta = servicioBo.getErrorAuthentication(sesion);
				return respuesta; 
			}
			iBean.setSesion(sesion);
			
			// Persona Natural
			ArrayList participantes = (ArrayList)sBean.getParticipantesPersonaNatural();
			Iterator e = participantes.iterator();
			
			while(e.hasNext()) {
				personaNatural = (PersonaNatural)e.next();
				System.out.println("Prueba Entrar ");
				if (personaNatural.getCodigoTipoDocumento().equals(iBean.getCodTipoDoc())) 
				{
					consultaBean.setNo_pers(personaNatural.getNombre());
					consultaBean.setAp_pate(personaNatural.getApellidoPaterno());
					consultaBean.setAp_mate(personaNatural.getApellidoMaterno());
					consultaBean.setNu_docu(personaNatural.getNumeroDocumento());
					
					codigo = servicioBo.dataVerificationServicio(iBean, consultaBean);
					System.out.println("codigo " + codigo);
					if (!codigo.trim().equals(iBean.getCodExito()))
					{
						System.out.println("codigo " + codigo);
						respuesta = respuesta + servicioBo.getErrorDataVerification(codigo, consultaBean);
					}else
					{
						respuesta = codigo;
					}
				}
			}
		} catch (Exception ex) {
			  System.out.println(ex);
		}
		return respuesta;
	}
	
	private IdentificacionBean obtenerParametros() throws Exception {
		
		IdentificacionBean iBean = new IdentificacionBean();
		WebServiceBean wsDataVerification = new WebServiceBean();
		WebServiceBean wsAuthentication = new WebServiceBean();
		
		ResourceBundle bundle = ResourceBundle.getBundle("gob.pe.sunarp.extranet.reniec.ws/WSResources");
		
		iBean.setCodUser(bundle.getString("wsConsulta.codigoUsuario"));
		iBean.setCodUser(bundle.getString("wsConsulta.codigoUsuario"));
		iBean.setCodTransac(bundle.getString("wsConsulta.codigoTransaccion"));
		iBean.setCodEntidad(bundle.getString("wsConsulta.codigoEntidad"));
		iBean.setCodTipoDoc(bundle.getString("wsConsulta.codigoTipoDocumento"));
		iBean.setCodExito(bundle.getString("wsConsulta.codigoExito"));
		iBean.setUser(bundle.getString("wsConsulta.user"));
		iBean.setPassword(bundle.getString("wsConsulta.password"));
		
		// otros parametros
		wsDataVerification.setUrl(bundle.getString("wsDataVerification.url"));
		wsDataVerification.setNameSpaceUri(bundle.getString("wsDataVerification.nameSpaceUri"));
		wsDataVerification.setServiceName(bundle.getString("wsDataVerification.serviceName"));
		wsDataVerification.setPortName(bundle.getString("wsDataVerification.portName"));
		
		wsAuthentication.setUrl(bundle.getString("wsAuthentication.url"));
		wsAuthentication.setNameSpaceUri(bundle.getString("wsAuthentication.nameSpaceUri"));
		wsAuthentication.setServiceName(bundle.getString("wsAuthentication.serviceName"));
		wsAuthentication.setPortName(bundle.getString("wsAuthentication.portName"));
		
		iBean.setWsDataVerification(wsDataVerification);
		iBean.setWsAuthentication(wsAuthentication);
		
		return iBean; 
	}
	
}
