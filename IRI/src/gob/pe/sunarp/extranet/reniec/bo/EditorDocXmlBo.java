package gob.pe.sunarp.extranet.reniec.bo;

import gob.pe.sunarp.extranet.reniec.bean.ConsultaBean;
import gob.pe.sunarp.extranet.reniec.bean.IdentificacionBean;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.apache.xerces.jaxp.DocumentBuilderFactoryImpl;
import org.apache.xml.serialize.XMLSerializer;
import org.apache.xml.serialize.OutputFormat;
import java.io.StringWriter;

public class EditorDocXmlBo {
		
	public String escribirDocXml (IdentificacionBean iBean, ConsultaBean cBean) throws Exception
	{
		
		String retorno = "-11";
		Document 	xmlDoc  = null;
		Element 	root = null;
		Element 	nodo = null;
		Element 	nod1 = null;      
		StringWriter strWriter = null;
		XMLSerializer xmlSerializer = null;
		OutputFormat outFormat = null;
	    
		try{
			// Crea un documento XML
			DocumentBuilderFactory dbFactory = DocumentBuilderFactoryImpl.newInstance();
			DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
			xmlDoc = docBuilder.newDocument();

			root = xmlDoc.createElement("IN");      
			xmlDoc.appendChild(root);                          
	              
			nodo = xmlDoc.createElement("CONSULTA");         
			nod1 = xmlDoc.createElement("NOMBRES");
			nod1.appendChild(xmlDoc.createTextNode(formatearValor(cBean.getNo_pers())));
			nodo.appendChild(nod1);
			nod1 = xmlDoc.createElement("APPAT");
			nod1.appendChild(xmlDoc.createTextNode(formatearValor(cBean.getAp_pate())));
			nodo.appendChild(nod1);
			nod1 = xmlDoc.createElement("APMAT");
			nod1.appendChild(xmlDoc.createTextNode(formatearValor(cBean.getAp_mate())));
			nodo.appendChild(nod1);
			nod1 = xmlDoc.createElement("DNI");
			nod1.appendChild(xmlDoc.createTextNode(cBean.getNu_docu()));
			nodo.appendChild(nod1);
	              
			root.appendChild(nodo);
	              
			nodo = xmlDoc.createElement("IDENTIFICACION");         
			nod1 = xmlDoc.createElement("CODUSER");
			nod1.appendChild(xmlDoc.createTextNode(iBean.getCodUser()));
			nodo.appendChild(nod1);
			nod1 = xmlDoc.createElement("CODTRANSAC");
			nod1.appendChild(xmlDoc.createTextNode(iBean.getCodTransac()));
			nodo.appendChild(nod1);
			nod1 = xmlDoc.createElement("CODENTIDAD");
			nod1.appendChild(xmlDoc.createTextNode(iBean.getCodEntidad()));
			nodo.appendChild(nod1);
			nod1 = xmlDoc.createElement("SESION");
			nod1.appendChild(xmlDoc.createTextNode(iBean.getSesion()));
			nodo.appendChild(nod1);
	              
			root.appendChild(nodo);
	              
			xmlSerializer = new XMLSerializer();          
			strWriter = new StringWriter();
			outFormat = new OutputFormat();
			outFormat.setEncoding("ISO-8859-1");
			outFormat.setVersion("1.0");
			outFormat.setIndenting(true); 
			outFormat.setIndent(4);

			xmlSerializer.setOutputCharStream(strWriter);
			xmlSerializer.setOutputFormat(outFormat);
			xmlSerializer.serialize(xmlDoc);
			strWriter.close(); 
			retorno = strWriter.toString(); 
	        
		}catch(Exception e){
			   retorno="-20";// error de generacion XML
		}                        

		return retorno;
	}
	
	public void leerDocXml (String docXml, ConsultaBean cBean) throws Exception
	{
		cBean.setRes_val(leerNodoXml("DATO", leerNodoXml("RESPUESTA", leerNodoXml("OUT", docXml))));
	}
	
	private String escribirNodoXml (String nodo, String valor) throws Exception
	{
		String stringNodo = "<" + nodo + ">" + valor + "</" + nodo + ">";
		
		return stringNodo.trim();
	}
	
	private String leerNodoXml (String nodo, String docXml) throws Exception
	{
		int indInicial = 0;
		int indFinal = 0;
		
		indInicial = docXml.indexOf("<" + nodo + ">") + nodo.length() + 2;
		indFinal = docXml.indexOf("</" + nodo + ">");
		String stringNodo = docXml.substring(indInicial, indFinal);
		
		return stringNodo.trim();
	}
	
	private String formatearValor (String valor) throws Exception
	{
		String nuevoValor = valor;
		nuevoValor = nuevoValor.toUpperCase();
		
		return nuevoValor;
	}
}
