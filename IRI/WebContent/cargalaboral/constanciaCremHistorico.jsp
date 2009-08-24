<%@page import="gob.pe.sunarp.extranet.publicidad.bean.PersonaJuridicaBean"%>
<%@page import="gob.pe.sunarp.extranet.publicidad.bean.AeronaveBean"%>
<%@page import="gob.pe.sunarp.extranet.publicidad.bean.BuquesBean"%>
<%@page import="gob.pe.sunarp.extranet.publicidad.bean.EmbarcacionPesqueraBean"%>
<%@page import="gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.Vehiculo"%>
<%@page import="gob.pe.sunarp.extranet.publicidad.bean.PartidaBean"%>
<%@page import="gob.pe.sunarp.extranet.publicidad.bean.TituloPendienteBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.pe.sunarp.extranet.publicidad.bean.ConstanciaCremBean"%>

<%
	if(request.getAttribute("constanciaCrem")!=null)
	{
		ConstanciaCremBean constanciaCremBean = (ConstanciaCremBean)request.getAttribute("constanciaCrem");
		ArrayList ListadoPartidasRMC = constanciaCremBean.getListadoPartidasRMC();
		ArrayList ListadoTitulosPendientesRMC = constanciaCremBean.getListadoTitulosPendientesRMC();
		ArrayList ListadoAsientosVehicular = constanciaCremBean.getListadoAsientosVehicular();
		ArrayList ListadoTitulosPendientesVehicular = constanciaCremBean.getListadoTitulosPendientesVehicular();
		ArrayList ListadoAsientosEmbarcacionPesquera = constanciaCremBean.getListadoAsientosEmbarcacionPesquera();
		ArrayList ListadoTitulosPendientesEmbarcacionPesquera = constanciaCremBean.getListadoTitulosPendientesEmbarcacionPesquera();
		ArrayList ListadoAsientosBuques = constanciaCremBean.getListadoAsientosBuques();
		ArrayList ListadoTitulosPendientesBuques = constanciaCremBean.getListadoTitulosPendientesBuques();
		ArrayList ListadoAsientosAeronaves = constanciaCremBean.getListadoAsientosAeronaves();
		ArrayList ListadoTitulosPendientesAeronaves = constanciaCremBean.getListadoTitulosPendientesAeronaves();
		ArrayList ListadoAsientosPersonasJuridicas = constanciaCremBean.getListadoAsientosPersonasJuridicas();
		ArrayList ListadoTitulosPendientesPersonasJuridicas = constanciaCremBean.getListadoTitulosPendientesPersonasJuridicas();
		
		StringBuffer constancia = new StringBuffer();
		constancia.append("");
		constancia.append("RMC \n");
		constancia.append("I.- REGISTRO MOBILIARIO DE CONTRATOS \n");
		constancia.append("TITULOS PENDIENTES \n");
		
		if (ListadoTitulosPendientesRMC.size()==0){
			constancia.append("NO \n");
		}else{
			for (int k=0; k<ListadoTitulosPendientesRMC.size(); k++){
				TituloPendienteBean objTituloPendienteRMC = (TituloPendienteBean)ListadoTitulosPendientesRMC.get(k);
				constancia.append(objTituloPendienteRMC.getAaTitulo()).append("-").append(objTituloPendienteRMC.getNroTitulo());
				if (objTituloPendienteRMC.getActoDescripcion()!=null)
					constancia.append(" (").append(objTituloPendienteRMC.getActoDescripcion()).append(" ) \n");
				else
					constancia.append(" (").append(" ").append(" ) \n");
			}
		}	
		constancia.append("\n");
		String str="";
		if (ListadoPartidasRMC.size()==0){
			constancia.append("NO registra \n");
		}else{
			for (int t=0; t<ListadoPartidasRMC.size();){
				PartidaBean partidaBean = (PartidaBean)ListadoPartidasRMC.get(t);
				str= String.valueOf(++t);
				constancia.append(str).append(". Partida Electr�nica N� ").append(partidaBean.getNumPartida()).append(" Registro Mobiliario de Contratos \n");
				if (partidaBean.getNumPartidaMigrado()!= null && (!partidaBean.getNumPartidaMigrado().equals(""))){
					constancia.append("(").append(partidaBean.getNumPartidaMigrado()).append(" ").append(partidaBean.getLibroDescripcionMigrado()).append(") \n");
				}	
				constancia.append("Tipo de Participaci�n: ").append(partidaBean.getParticipacionDescripcion()).append("\n");
				constancia.append("\n");
			}
		}
			
		constancia.append("\n");
		constancia.append("----------------------------------------------------------------------\n");
		
		constancia.append("RJB \n");
		constancia.append("II.- REGISTRO DE PROPIEDAD VEHICULAR \n");
		constancia.append("TITULOS PENDIENTES \n");
		if (ListadoTitulosPendientesVehicular.size()==0){
			constancia.append("NO \n");
		}else{
			for (int i=0; i<ListadoTitulosPendientesVehicular.size(); i++){
				TituloPendienteBean objTituloPendienteRMC = (TituloPendienteBean)ListadoTitulosPendientesVehicular.get(i);
				constancia.append(objTituloPendienteRMC.getAaTitulo()).append("-").append(objTituloPendienteRMC.getNroTitulo());
				if (objTituloPendienteRMC.getActoDescripcion()!=null)
					constancia.append(" (").append(objTituloPendienteRMC.getActoDescripcion()).append(" ) \n");
				else
					constancia.append(" (").append(" ").append(" ) \n");
			}
		}	
		constancia.append("\n");
		String str2="";
		if (ListadoAsientosVehicular.size()==0){
			constancia.append("NO registra \n");
		}else{
			for (int q=0; q<ListadoAsientosVehicular.size();){
				Vehiculo vehiculoBean = (Vehiculo)ListadoAsientosVehicular.get(q);
				str2= String.valueOf(++q);
				constancia.append(str2).append(". Tipo de Acto: ").append(vehiculoBean.getDescripcionSubActo()).append("\n");
				if (vehiculoBean.getClase()!= null)
					constancia.append("  ").append("Clase: ").append(vehiculoBean.getClase()).append("\n");
				if (vehiculoBean.getMarca()!= null)	
					constancia.append("  ").append("Marca: ").append(vehiculoBean.getMarca()).append("\n");
				if (vehiculoBean.getSerie()!= null)	
					constancia.append("  ").append("Serie: ").append(vehiculoBean.getSerie()).append("\n");
				if (vehiculoBean.getMotor() != null)	
				    constancia.append("  ").append("Motor: ").append(vehiculoBean.getMotor()).append("\n");
				if (vehiculoBean.getPlaca()!= null)    
			   		constancia.append("  ").append("Placa: ").append(vehiculoBean.getPlaca()).append("\n");
			   	if (vehiculoBean.getNumeroPartida()!=null)	
			   		constancia.append("  ").append("Partida Electr�nica N�: ").append(vehiculoBean.getNumeroPartida()).append("\n");
			   	if (vehiculoBean.getOficinaRegistral()!= null)	
			   		constancia.append("  ").append("Oficina Registral: ").append(vehiculoBean.getOficinaRegistral()).append("\n");
			   	if (vehiculoBean.getTipoParticipacion() != null)	
					constancia.append("  ").append("Tipo de Participaci�n: ").append(vehiculoBean.getTipoParticipacion()).append("\n");
				if (vehiculoBean.getEstadoParticipante() != null){	
					if (vehiculoBean.getEstadoParticipante().equals("0")){
						constancia.append("  ").append("PARTICIPACI�N INACTIVA").append("\n");
					}	
				}	
				constancia.append("\n");
			}
		}	
		constancia.append("\n");
		constancia.append("----------------------------------------------------------------------\n");
		
		constancia.append("III.- REGISTRO DE EMBARCACIONES PESQUERAS \n");
		constancia.append("TITULOS PENDIENTES \n");
		if (ListadoTitulosPendientesEmbarcacionPesquera.size()==0){
			constancia.append("NO \n");
		}else{
			for (int r=0; r<ListadoTitulosPendientesEmbarcacionPesquera.size(); r++){
				TituloPendienteBean objTituloPendienteRMC = (TituloPendienteBean)ListadoTitulosPendientesEmbarcacionPesquera.get(r);
				constancia.append(objTituloPendienteRMC.getAaTitulo()).append("-").append(objTituloPendienteRMC.getNroTitulo());
				if (objTituloPendienteRMC.getActoDescripcion()!=null)
					constancia.append(" (").append(objTituloPendienteRMC.getActoDescripcion()).append(" ) \n");
				else
					constancia.append(" (").append(" ").append(" ) \n");
			}
		}	
		
		constancia.append("\n");
		String str3="";
		if (ListadoAsientosEmbarcacionPesquera.size()==0){
			constancia.append("NO registra \n");
		}else{
			for (int s=0; s<ListadoAsientosEmbarcacionPesquera.size();){
				EmbarcacionPesqueraBean embarcacionBean = (EmbarcacionPesqueraBean)ListadoAsientosEmbarcacionPesquera.get(s);
				str3= String.valueOf(++s);
				constancia.append(str3).append(". Tipo de Acto: ").append(embarcacionBean.getDescripci�nActo()).append("\n");
				if (embarcacionBean.getN�meroMatricula()!= null)
					constancia.append("  ").append("Matr�cula: ").append(embarcacionBean.getN�meroMatricula()).append("\n");
				if (embarcacionBean.getNombreEmbarcacion() != null)	
				    constancia.append("  ").append("Nombre/Modelo: ").append(embarcacionBean.getNombreEmbarcacion()).append("\n");
				if (embarcacionBean.getTipoEmbarcaci�n() != null)    
			   		constancia.append("  ").append("Tipo: ").append(embarcacionBean.getTipoEmbarcaci�n()).append("\n");
			   	if (embarcacionBean.getFechaInscripci�n() != null)	
			   		constancia.append("  ").append("Fecha de Inscripci�n: ").append(embarcacionBean.getFechaInscripci�n()).append("\n");
			   	if (embarcacionBean.getN�meroPartida()!=null)	
			   		constancia.append("  ").append("Partida Electr�nica N�: ").append(embarcacionBean.getN�meroPartida()).append("\n");
			   	if (embarcacionBean.getOficinaRegistral() != null)	
			   		constancia.append("  ").append("Oficina Registral: ").append(embarcacionBean.getNombreOficinaRegistral()).append("\n");
			   	if (embarcacionBean.getTipoParticipaci�n() != null)	
					constancia.append("  ").append("Tipo de Participaci�n: ").append(embarcacionBean.getTipoParticipaci�n()).append("\n");
				if (embarcacionBean.getEstadoParticipacion() != null){	
					if (embarcacionBean.getEstadoParticipacion().equals("0")){
						constancia.append("  ").append("PARTICIPACI�N INACTIVA").append("\n");
					}	
				}	
				constancia.append("\n");
			}
		}	
		constancia.append("\n");
		constancia.append("----------------------------------------------------------------------\n");
		
		constancia.append("IV.- REGISTRO DE BUQUES \n");
		constancia.append("TITULOS PENDIENTES \n");
		if (ListadoTitulosPendientesBuques.size()==0){
			constancia.append("NO \n");
		}else{
			for (int m=0; m<ListadoTitulosPendientesBuques.size(); m++){
				TituloPendienteBean objTituloPendienteRMC = (TituloPendienteBean)ListadoTitulosPendientesBuques.get(m);
				constancia.append(objTituloPendienteRMC.getAaTitulo()).append("-").append(objTituloPendienteRMC.getNroTitulo());
				if (objTituloPendienteRMC.getActoDescripcion()!=null)
					constancia.append(" (").append(objTituloPendienteRMC.getActoDescripcion()).append(" ) \n");
				else
					constancia.append(" (").append(" ").append(" ) \n");
			}
		}	
		
		constancia.append("\n");
		String str4="";
		if (ListadoAsientosBuques.size()==0){
			constancia.append("NO registra \n");
		}else{
			for (int n=0; n<ListadoAsientosBuques.size();){
				BuquesBean buquesBean = (BuquesBean)ListadoAsientosBuques.get(n);
				str4= String.valueOf(++n);
				constancia.append(str4).append(". Tipo de Acto: ").append(buquesBean.getDescripci�nActo()).append("\n");
				if (buquesBean.getN�meroMatricula()!= null)
					constancia.append("  ").append("Matr�cula: ").append(buquesBean.getN�meroMatricula()).append("\n");
				if (buquesBean.getNombreBuque()!= null)	
				    constancia.append("  ").append("Nombre/Modelo: ").append(buquesBean.getNombreBuque()).append("\n");
				if (buquesBean.getFechaInscripci�n() != null)    
			   		constancia.append("  ").append("Fecha de Inscripci�n: ").append(buquesBean.getFechaInscripci�n()).append("\n");
			   	if (buquesBean.getN�meroPartida()!= null)	
			   		constancia.append("  ").append("Partida Electr�nica N�: ").append(buquesBean.getN�meroPartida()).append("\n");
			   	if (buquesBean.getOficinaRegistral() != null)	
			   		constancia.append("  ").append("Oficina Registral: ").append(buquesBean.getNombreOficinaRegistral()).append("\n");
			   	if (buquesBean.getTipoParticipaci�n() != null)	
					constancia.append("  ").append("Tipo de Participaci�n: ").append(buquesBean.getTipoParticipaci�n()).append("\n");
				if (buquesBean.getEstadoParticipacion() != null){	
					if (buquesBean.getEstadoParticipacion().equals("0")){
						constancia.append("  ").append("PARTICIPACI�N INACTIVA").append("\n");
					}	
				}	
				constancia.append("\n");
			}
		}	
		constancia.append("\n");
		constancia.append("----------------------------------------------------------------------\n");
		
		constancia.append("V.- REGISTRO DE AERONAVES \n");
		constancia.append("TITULOS PENDIENTES \n");
		if (ListadoTitulosPendientesAeronaves.size()==0){
			constancia.append("NO \n");
		}else{
			for (int p=0; p<ListadoTitulosPendientesAeronaves.size(); p++){
				TituloPendienteBean objTituloPendienteRMC = (TituloPendienteBean)ListadoTitulosPendientesAeronaves.get(p);
				constancia.append(objTituloPendienteRMC.getAaTitulo()).append("-").append(objTituloPendienteRMC.getNroTitulo());
				if (objTituloPendienteRMC.getActoDescripcion()!=null)
					constancia.append(" (").append(objTituloPendienteRMC.getActoDescripcion()).append(" ) \n");
				else
					constancia.append(" (").append(" ").append(" ) \n");
			}
		}	
		
		constancia.append("\n");
		String str5="";
		if (ListadoAsientosAeronaves.size()==0){
			constancia.append("NO registra \n");
		}else{
			for (int v=0; v<ListadoAsientosAeronaves.size(); ){
				AeronaveBean aeronaveBean = (AeronaveBean)ListadoAsientosAeronaves.get(v);
				str5= String.valueOf(++v);
				constancia.append(str5).append(". Tipo de Acto: ").append(aeronaveBean.getDescripci�nActo()).append("\n");
				if (aeronaveBean.getN�meroMatricula() != null)
					constancia.append("  ").append("Matr�cula: ").append(aeronaveBean.getN�meroMatricula()).append("\n");
				if (aeronaveBean.getN�meroSerie() != null)	
				    constancia.append("  ").append("Serie: ").append(aeronaveBean.getN�meroSerie()).append("\n");
				if (aeronaveBean.getTipoAeronave() != null)    
				    constancia.append("  ").append("Tipo: ").append(aeronaveBean.getTipoAeronave()).append("\n");
				if (aeronaveBean.getFechaInscripci�n() != null)    
			   		constancia.append("  ").append("Fecha de Inscripci�n: ").append(aeronaveBean.getFechaInscripci�n()).append("\n");
			   	if (aeronaveBean.getN�meroPartida() != null)	
			   		constancia.append("  ").append("Partida Electr�nica N�: ").append(aeronaveBean.getN�meroPartida()).append("\n");
			   	if (aeronaveBean.getOficinaRegistral() != null)	
			   		constancia.append("  ").append("Oficina Registral: ").append(aeronaveBean.getNombreOficinaRegistral()).append("\n");
			   	if (aeronaveBean.getTipoParticipaci�n() != null)	
					constancia.append("  ").append("Tipo de Participaci�n: ").append(aeronaveBean.getTipoParticipaci�n()).append("\n");
				if (aeronaveBean.getEstadoParticipacion() != null){	
					if (aeronaveBean.getEstadoParticipacion().equals("0")){
						constancia.append("  ").append("PARTICIPACI�N INACTIVA").append("\n");
					}	
				}	
				constancia.append("\n");
			}
		}	
		constancia.append("\n");
		constancia.append("----------------------------------------------------------------------\n");
		
		constancia.append("VI.- PARTICIPACIONES - REGISTRO DE PERSONAS JUR�DICAS \n");
		constancia.append("TITULOS PENDIENTES \n");
		if (ListadoTitulosPendientesPersonasJuridicas.size()==0){
			constancia.append("NO \n");
		}else{
			for (int b=0; b<ListadoTitulosPendientesPersonasJuridicas.size(); b++){
				TituloPendienteBean objTituloPendienteRMC = (TituloPendienteBean)ListadoTitulosPendientesPersonasJuridicas.get(b);
				constancia.append(objTituloPendienteRMC.getAaTitulo()).append("-").append(objTituloPendienteRMC.getNroTitulo());
				if (objTituloPendienteRMC.getActoDescripcion()!=null)
					constancia.append(" (").append(objTituloPendienteRMC.getActoDescripcion()).append(" ) \n");
				else
					constancia.append(" (").append(" ").append(" ) \n");
			}
		}	
		
		constancia.append("\n");
		String str6="";
		if (ListadoAsientosPersonasJuridicas.size()==0){
			constancia.append("NO registra \n");
		}else{
			for (int c=0; c<ListadoAsientosPersonasJuridicas.size();){
				PersonaJuridicaBean personajuridicaBean = (PersonaJuridicaBean)ListadoAsientosPersonasJuridicas.get(c);
				str6= String.valueOf(++c);
				constancia.append(str6).append(". Raz�n Social: ").append(personajuridicaBean.getRazonSocial()).append("\n");
				if (personajuridicaBean.getN�meroPartida()!= null)
			   		constancia.append("  ").append("Partida Electr�nica N�: ").append(personajuridicaBean.getN�meroPartida()).append("\n");
			   	if (personajuridicaBean.getTipoParticipaci�n() != null)	
					constancia.append("  ").append("Tipo de Participaci�n: ").append(personajuridicaBean.getTipoParticipaci�n()).append("\n");
				if (personajuridicaBean.getEstadoParticipacion() != null){	
					if (personajuridicaBean.getEstadoParticipacion().equals("0")){
						constancia.append("  ").append("PARTICIPACI�N INACTIVA").append("\n");
					}	
				}	
				constancia.append("\n");
			}
		}	
		constancia.append("\n");
		constancia.append("----------------------------------------------------------------------\n");
		
		request.setAttribute("constancia", constancia.toString());
	}
 %>
 