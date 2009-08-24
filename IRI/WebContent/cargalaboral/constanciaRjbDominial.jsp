<%@page import="gob.pe.sunarp.extranet.publicidad.bean.ConstanciaRjbBean"%>
<%@page import="java.util.ArrayList"%>

<%  
	ArrayList resultado = new ArrayList();
	ArrayList participante = new ArrayList();
	ArrayList titulo = new ArrayList();
	if(request.getAttribute("resultado")!=null)
	{
		resultado = (ArrayList)request.getAttribute("resultado");
		StringBuffer q = new StringBuffer();
		
		for(int i =0; i<resultado.size(); i++)
		{
			q.append((String)((ConstanciaRjbBean)resultado.get(i)).getDescripciónActo());
			q.append("\n");
				
			participante = ((ConstanciaRjbBean)resultado.get(i)).getListaParticipante();
			if(participante==null)
			{
				q.append("Propietario(s): ");
				ArrayList listadoPropietarios = ((ConstanciaRjbBean)resultado.get(i)).getNombrePropietario();
				String auxpropietario = null;
				for (int z=0; z<listadoPropietarios.size();z++){
					auxpropietario = (String)listadoPropietarios.get(z);
					if(auxpropietario != null){
					    if (z>0){
					       q.append(", ");
					       q.append(auxpropietario);
					    }else{
							q.append(auxpropietario);
						}	
					}
				}
			}else
			{
				q.append("Nombres: ");
				for(int j=0; j<participante.size(); j++)
				{
					q.append((String)participante.get(j));
					q.append("\n      ");
				}
			}
			q.append("\rTitulo: ");
			q.append((String)((ConstanciaRjbBean)resultado.get(i)).getAnoTitulo());
			q.append("-");
			q.append((String)((ConstanciaRjbBean)resultado.get(i)).getNumeroTitulo());
			q.append("\n");
			q.append("Fecha: ");
			q.append((String)((ConstanciaRjbBean)resultado.get(i)).getFechaInscripcion());
			q.append("\n\n");
			
		}
		request.setAttribute("constancia",q.toString());
	}
	
%>