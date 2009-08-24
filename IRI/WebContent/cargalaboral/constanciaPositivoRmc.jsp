<%@page import="java.text.SimpleDateFormat"%>
<%@page import="gob.pe.sunarp.extranet.util.Constantes"%>
<%@page import="gob.pe.sunarp.extranet.publicidad.bean.TituloBean"%>
<%@page import="gob.pe.sunarp.extranet.publicidad.bean.ParticipanteBean"%>
<%@page import="gob.pe.sunarp.extranet.publicidad.bean.ConstanciaRMCBean"%>
<%@page import="gob.pe.sunarp.extranet.publicidad.bean.EmbarcacionPesqueraBean"%>
<%@page import="gob.pe.sunarp.extranet.publicidad.bean.PartidaBean"%>
<%@page import="java.util.ArrayList"%>


<%
	ArrayList listadoConstanciaBean = (ArrayList)request.getAttribute("constanciaRMC");
	StringBuffer constancia = new StringBuffer();
	StringBuffer titulos = new StringBuffer();
	constancia.append("");
	titulos.append("");
	boolean flagTitulos=false;
   
	for (int k=0; listadoConstanciaBean!=null && k<listadoConstanciaBean.size(); k++){
		ConstanciaRMCBean constanciaRMCBean = (ConstanciaRMCBean)listadoConstanciaBean.get(k);
		if(constanciaRMCBean.getModelo()!=null && constanciaRMCBean.getModelo().trim().length()>0){
			constancia.append("Clase/Modelo:   ");
			constancia.append(constanciaRMCBean.getModelo());
			constancia.append("\n");
		}
		if(constanciaRMCBean.getMarca()!=null && constanciaRMCBean.getMarca().trim().length()>0){
			constancia.append("Marca:                  ");
			constancia.append(constanciaRMCBean.getMarca());
			constancia.append("\n");
		}
		if(constanciaRMCBean.getSerie()!=null && constanciaRMCBean.getSerie().trim().length()>0){
			constancia.append("Serie:                  ");
			constancia.append(constanciaRMCBean.getSerie());
			constancia.append("\n");
		}
		if(constanciaRMCBean.getMotor()!=null && constanciaRMCBean.getMotor().trim().length()>0){
			constancia.append("Motor:                  ");
			constancia.append(constanciaRMCBean.getMotor());
			constancia.append("\n");
		}
		if(constanciaRMCBean.getPlaca()!=null && constanciaRMCBean.getPlaca().trim().length()>0){
			constancia.append("Placa:                  ");
			constancia.append(constanciaRMCBean.getPlaca());
			constancia.append("\n");
		}
		if(constanciaRMCBean.getDescripcion()!=null && constanciaRMCBean.getDescripcion().trim().length()>0){
			constancia.append("Nombre:              ");
			constancia.append(constanciaRMCBean.getDescripcion());
			constancia.append("\n");
		}
		if(constanciaRMCBean.getPartida()!=null && constanciaRMCBean.getPartida().getNumPartida()!=null && !constanciaRMCBean.getPartida().getNumPartida().trim().equals("")){
			constancia.append("\n");
			constancia.append("Se han encontrado inscripciones en la siguiente partida: \n \n");
			constancia.append("Partida N° ");
			constancia.append(constanciaRMCBean.getPartida().getNumPartida());
			if(constanciaRMCBean.getPartida().getParticipantes()!=null && constanciaRMCBean.getPartida().getParticipantes().size()>0){
				constancia.append(", con los siguientes intervinientes: \n");
				ArrayList listaParticipante=constanciaRMCBean.getPartida().getParticipantes();
				for(int i=0; i<listaParticipante.size();i++ ){
					ParticipanteBean participante=(ParticipanteBean)listaParticipante.get(i);
					constancia.append("- ");
					if(participante.getTipoPersona().equals(Constantes.PERSONA_NATURAL)){
						if(participante.getNombre()!=null)
							constancia.append(participante.getNombre()).append(" ");
						if(participante.getApellidoPaterno()!=null)
							constancia.append(participante.getApellidoPaterno()).append(" ");
						if(participante.getApellidoMaterno()!=null)
							constancia.append(participante.getApellidoMaterno());
					}else if(participante.getTipoPersona().equals(Constantes.PERSONA_JURIDICA)){
						if(participante.getRazonSocial()!=null)
							constancia.append(participante.getRazonSocial());
					}
					constancia.append(" ( ").append(participante.getDescripcionTipoParticipacion()).append(" )");
					constancia.append("\n");
				}
			}
			constancia.append("\n");
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
 