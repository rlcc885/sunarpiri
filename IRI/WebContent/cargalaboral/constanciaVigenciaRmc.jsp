<%@page import="java.text.SimpleDateFormat"%>
<%@page import="gob.pe.sunarp.extranet.util.Constantes"%>
<%@page import="gob.pe.sunarp.extranet.publicidad.bean.TituloBean"%>
<%@page import="gob.pe.sunarp.extranet.publicidad.bean.ParticipanteBean"%>
<%@page import="gob.pe.sunarp.extranet.publicidad.bean.ConstanciaRMCBean"%>
<%@page import="gob.pe.sunarp.extranet.publicidad.bean.EmbarcacionPesqueraBean"%>
<%@page import="gob.pe.sunarp.extranet.publicidad.bean.PartidaBean"%>
<%@page import="java.util.ArrayList"%>


<%
	ArrayList listadoConstanciaBean = (ArrayList)request.getAttribute("constanciaVigenciaRMC");
	StringBuffer constancia = new StringBuffer();
	StringBuffer titulos = new StringBuffer();
	constancia.append("");
	titulos.append("");
	String nombreRepresentante="";
	String nombreRepre="";
	String apePatRepre="";
	String apeMatRepre="";
	String razonSocialRepre="";
	boolean flagTitulos=false;
	String tipoPer="";
	
	if(listadoConstanciaBean!=null &&listadoConstanciaBean.size()>0 ){
		ConstanciaRMCBean constanciaInicialRMCBean = (ConstanciaRMCBean)listadoConstanciaBean.get(0);
		if(constanciaInicialRMCBean.getPartida()!=null && constanciaInicialRMCBean.getPartida().getNumPartida()!=null && !constanciaInicialRMCBean.getPartida().getNumPartida().trim().equals("")){
			if(constanciaInicialRMCBean.getApePat()!=null && constanciaInicialRMCBean.getApePat().trim().length()>0){
				tipoPer= Constantes.PERSONA_NATURAL;
				if(constanciaInicialRMCBean.getNombres()!=null && constanciaInicialRMCBean.getNombres().trim().length()>0)
					nombreRepre=constanciaInicialRMCBean.getNombres();
				if(constanciaInicialRMCBean.getApeMat()!=null && constanciaInicialRMCBean.getApeMat().trim().length()>0)
					apeMatRepre=constanciaInicialRMCBean.getApeMat();
				apePatRepre=constanciaInicialRMCBean.getApePat();
			}else if(constanciaInicialRMCBean.getRazonSocial()!=null && constanciaInicialRMCBean.getRazonSocial().trim().length()>0){
				tipoPer= Constantes.PERSONA_JURIDICA;
				razonSocialRepre=constanciaInicialRMCBean.getRazonSocial();
			}
				
	}
	for (int k=0; listadoConstanciaBean!=null && k<listadoConstanciaBean.size(); k++){
		ConstanciaRMCBean constanciaRMCBean = (ConstanciaRMCBean)listadoConstanciaBean.get(k);
		if(constanciaRMCBean.getPartida()!=null && constanciaRMCBean.getPartida().getNumPartida()!=null && constanciaRMCBean.getPartida().getNumPartida().trim().length()>0){
			if((tipoPer.equalsIgnoreCase(Constantes.PERSONA_JURIDICA) && constanciaRMCBean.getRazonSocial()!=null && razonSocialRepre.equalsIgnoreCase(constanciaRMCBean.getRazonSocial()))	||
				(tipoPer.equalsIgnoreCase(Constantes.PERSONA_NATURAL) && constanciaRMCBean.getApePat()!=null && constanciaRMCBean.getApeMat()!=null && constanciaRMCBean.getNombres()!=null && nombreRepre.equalsIgnoreCase(constanciaRMCBean.getNombres())
				&& apePatRepre.equalsIgnoreCase(constanciaRMCBean.getApePat())&& apeMatRepre.equalsIgnoreCase(constanciaRMCBean.getApeMat()))){
					constancia.append("Partida N° ");
					constancia.append(constanciaRMCBean.getPartida().getNumPartida());
					constancia.append("; vigente");
					if(constanciaRMCBean.getPartida().getDescEjecucion()!=null && constanciaRMCBean.getPartida().getDescEjecucion().length()>0){
						constancia.append(" para ");
						constancia.append(constanciaRMCBean.getPartida().getDescEjecucion());
						
						
						}
						constancia.append(" \n");
					if(constanciaRMCBean.getTitulos()!=null && constanciaRMCBean.getTitulos().size()>0){
						titulos.append("Se han encontrado los siguientes títulos pendientes con la partida N° "+constanciaRMCBean.getPartida().getNumPartida()+": \n");
						ArrayList listaTitulo =constanciaRMCBean.getTitulos();
						for (int m=0; m<listaTitulo.size();m++){
							TituloBean titulo = (TituloBean)listaTitulo.get(m);
							titulos.append("- Titulo N° ");
							if(titulo.getTitulo()!=null && titulo.getTitulo().length()>0)
								titulos.append(titulo.getTitulo());
							if(titulo.getFecPresent()!=null && titulo.getFecPresent().length()>0 && titulo.getFechaPresentacion()!=null){
							SimpleDateFormat sd= new SimpleDateFormat("dd/MM/yyyy");
							String fecha="";
							fecha=sd.format(titulo.getFechaPresentacion());
								titulos.append(", fecha de presentación: ");
								titulos.append(fecha);
							}
							titulos.append("\n");
						}
						
						flagTitulos=true;
					}
				}
		}
		}
			
	

	constancia.append("\n \n En las que \n\n");
	if(tipoPer.equalsIgnoreCase(Constantes.PERSONA_NATURAL))
		constancia.append(nombreRepre).append(" ").append(apePatRepre).append(" ").append(apeMatRepre).append("\n");
	else if (tipoPer.equalsIgnoreCase(Constantes.PERSONA_JURIDICA))
		constancia.append(razonSocialRepre).append("\n " );
		
	

	}
	if(!flagTitulos){
		titulos.append("Se deja constancia que realizada la búsqueda en el índice de títulos ingresados al Registro de Mobiliario de Contratos, hasta \n");
		titulos.append("las 24 horas anteriores a la expedición del presente certificado no se han encontrado títulos pendientes, respecto a dichas \n");
		titulos.append("partidas. ");
	}
	request.setAttribute("constancia", constancia.toString());
	request.setAttribute("titulos", titulos.toString());
 %>
 