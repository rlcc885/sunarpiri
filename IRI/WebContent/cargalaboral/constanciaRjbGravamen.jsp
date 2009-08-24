<%@page import="java.util.ArrayList"%>
<%@page import="gob.pe.sunarp.extranet.publicidad.bean.ConstanciaRjbBean"%>
<%
	ArrayList resultado = new ArrayList();
	ArrayList participante = new ArrayList();
	ArrayList documento = new ArrayList();
	if(request.getAttribute("resultado")!=null)
	{
	    resultado = (ArrayList)request.getAttribute("resultado");
		StringBuffer q = new StringBuffer();
		for(int i =0; i<resultado.size(); i++)
		{
			q.append((String)((ConstanciaRjbBean)resultado.get(i)).getDescripciónActo());
			q.append("\n");
			String fecha = (String)((ConstanciaRjbBean)resultado.get(i)).getFechaAfectacion();
			if(fecha != null && !fecha.equals("")){
				q.append("Fecha de Afectación: ");
				q.append(fecha);	
			}else{
				q.append("Sin Fecha");	
			}
			q.append("\n");	
			participante = ((ConstanciaRjbBean)resultado.get(i)).getListaParticipante();
			q.append("Contratantes: ");
			for(int j=0; j<participante.size(); j++)
			{
				q.append((String)participante.get(j));
				q.append("\n                    ");
			}
			q.append("\rMonto de Gravamen: ");
			q.append((String)((ConstanciaRjbBean)resultado.get(i)).getMontoGravamen());
			q.append("\n");
			documento =  ((ConstanciaRjbBean)resultado.get(i)).getListaDocumento();
			q.append("Documentos: ");
			for(int k=0;k<documento.size();k++)
			{
				q.append((String)documento.get(k));
				q.append("\n");
			}
			q.append("\n\n");
		}
		request.setAttribute("constancia",q.toString());	 
	}
%>