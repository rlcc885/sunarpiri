<%@page import="java.text.SimpleDateFormat"%>
<%@page import="gob.pe.sunarp.extranet.util.Constantes"%>
<%@page import="gob.pe.sunarp.extranet.publicidad.bean.TituloBean"%>
<%@page import="gob.pe.sunarp.extranet.publicidad.bean.ParticipanteBean"%>
<%@page import="gob.pe.sunarp.extranet.publicidad.bean.ConstanciaRMCBean"%>
<%@page import="gob.pe.sunarp.extranet.publicidad.bean.EmbarcacionPesqueraBean"%>
<%@page import="gob.pe.sunarp.extranet.publicidad.bean.PartidaBean"%>
<%@page import="java.util.ArrayList"%>


<%
	ArrayList listadoConstanciaBean = (ArrayList)request.getAttribute("constanciaGravamenRMC");
	StringBuffer constancia = new StringBuffer();
	StringBuffer titulos = new StringBuffer();
	constancia.append("");
	titulos.append("");
	boolean flagTitulos=false;
	
	for (int k=0; listadoConstanciaBean!=null && k<listadoConstanciaBean.size(); k++){
		ConstanciaRMCBean constanciaRMCBean = (ConstanciaRMCBean)listadoConstanciaBean.get(k);
		if(constanciaRMCBean.getPartida()!=null && constanciaRMCBean.getPartida().getNumPartida()!=null && !constanciaRMCBean.getPartida().getNumPartida().trim().equals("")){
			constancia.append("- Partida N° ");
			constancia.append(constanciaRMCBean.getPartida().getNumPartida());
			
			if(constanciaRMCBean.getPartida().getNumPartidaMigrado()!=null && constanciaRMCBean.getPartida().getNumPartidaMigrado().length()>0){
				constancia.append(" ( P.E. Nº ");
				constancia.append(constanciaRMCBean.getPartida().getNumPartidaMigrado());
				if(constanciaRMCBean.getPartida().getLibroDescripcionMigrado()!=null && constanciaRMCBean.getPartida().getLibroDescripcionMigrado().length()>0){
					constancia.append(" del Ex " ).append(constanciaRMCBean.getPartida().getLibroDescripcionMigrado());
					
				}
				constancia.append(" ) ");
			}else {
				if(constanciaRMCBean.getPartida().getLibroDescripcion()!=null && constanciaRMCBean.getPartida().getLibroDescripcion().length()>0){
					constancia.append(" ");
					constancia.append(constanciaRMCBean.getPartida().getLibroDescripcion());
				}
			}
			constancia.append(" \n");
		}
		
		if(constanciaRMCBean.getTitulos()!=null && constanciaRMCBean.getTitulos().size()>0){
			ArrayList listaTitulo =constanciaRMCBean.getTitulos();
			titulos.append("Se han encontrado los siguientes títulos pendientes con la partida N° "+constanciaRMCBean.getPartida().getNumPartida()+": \n");
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
	if(!flagTitulos){
		titulos.append("Se deja constancia que realizada la búsqueda en el índice de títulos ingresados al Registro de Mobiliario de Contratos, hasta \n");
		titulos.append("las 24 horas anteriores a la expedición del presente certificado no se han encontrado títulos pendientes, respecto a dichas \n");
		titulos.append("partidas. ");
	}

	
	
	request.setAttribute("constancia", constancia.toString());
	request.setAttribute("titulos", titulos.toString());
	
 %>
 