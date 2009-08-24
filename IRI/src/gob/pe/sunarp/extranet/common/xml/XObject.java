package gob.pe.sunarp.extranet.common.xml;

import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import gob.pe.sunarp.extranet.common.SunarpBean;

import java.io.*;
import java.util.*;

public class XObject extends SunarpBean{
	private Node root = null;
	private Document doc = null;
	private NodeList hijos = null;
	
	/**
	 * XObject constructor comment.
	 */
	public XObject() {
		super();
	}
	/**
	 * XObject constructor comment.
	 */
	public String getAttribute(String name) {
		return root.getAttributes().getNamedItem(name).getNodeValue();
	}
	/**
	 * XObject constructor comment.
	 */
	public XObject getElement(String nombre) throws XObjectException {
		for (int i = 0; i < hijos.getLength(); i++) {
			Node hijo = hijos.item(i);
			if ((hijo.getNodeType() == Node.ELEMENT_NODE)
				&& (hijo.getNodeName().equals(nombre))) {
				XObject xHijo = new XObject();
				xHijo.setRoot(hijo);
				return xHijo;
			}
		}
		return null;
	}
	/**
	 * XObject constructor comment.
	 */
	public XObject[] getElements(String nombre) throws XObjectException {
		Vector vector = new Vector();
		int len = hijos.getLength();
		for (int i = 0; i < len; i++) {
			Node hijo = hijos.item(i);
			if ((hijo.getNodeType() == Node.ELEMENT_NODE)
				&& (hijo.getNodeName().equals(nombre))) {
				XObject xHijo = new XObject();
				xHijo.setRoot(hijo);
				vector.addElement(xHijo);
			}
		}
		XObject[] resultado = new XObject[vector.size()];
		for (int i = 0; i < vector.size(); i++)
			resultado[i] = (XObject) vector.elementAt(i);

		return resultado;
	}
	/**
	 * XObject constructor comment.
	 */
	public String getElementTextValue(String elementName) throws XObjectException{
		XObject element = getElement(elementName);
		if (element == null) return null;
		String texto = element.getTextValue();
		return texto == null ? "" : texto;
	}
	/**
	 * XObject constructor comment.
	 */
	public String getTextValue() {
		for (int i = 0; i < hijos.getLength(); i++) {
			Node hijo = hijos.item(i);
			if (hijo.getNodeType() == Node.TEXT_NODE) {
				return hijo.getNodeValue();
			}
		}
		return null;
	}
	/**
	 * XObject constructor comment.
	 */
	public XObject[] getXObjects(String nombre,Class clase) throws XObjectException {
		Vector vector = new Vector();
		int len = hijos.getLength();
		for (int i = 0; i < len; i++) {
			Node hijo = hijos.item(i);
			if ((hijo.getNodeType() == Node.ELEMENT_NODE)
				&& (hijo.getNodeName().equals(nombre))) {
				try {
					XObject xHijo = (XObject) clase.newInstance();
					xHijo.setRoot(hijo);
					vector.addElement(xHijo);
				} catch (Exception e) {
					e.fillInStackTrace();
					throw new XObjectException("No se pudo crear el XObject especificado.");
				}
			}
		}
		XObject[] resultado = new XObject[vector.size()];
		for (int i = 0; i < vector.size(); i++)
			resultado[i] = (XObject) vector.elementAt(i);

		return resultado;
	}
	/**
	 * XObject constructor comment.
	 */
	public void setRoot(Node nodo) throws XObjectException {
		if (nodo.getNodeType() == Node.DOCUMENT_NODE) {
			doc = (Document) nodo;
			root = ((Document) nodo).getDocumentElement();
		} else
			if (nodo.getNodeType() == Node.ELEMENT_NODE) {
				root = nodo;
				doc = null;
			} else
				throw new XObjectException("No se pudo cargar Nodo: Tipo de nodo invalido.");

		hijos = root.getChildNodes();
	}
	/**
	 * XObject constructor comment.
	 */
	public void setXML(Reader xml) throws XObjectException {
		try {
			org.apache.xerces.parsers.DOMParser parser = new org.apache.xerces.parsers.DOMParser();
			parser.parse(new InputSource(xml));
			setRoot(parser.getDocument());
		} catch (Exception e) {
			throw new XObjectException("No se pudo cargar XML: " + e.getMessage());
		}
	}
	/**
	 * XObject constructor comment.
	 */
	public void setXML(String xml) throws XObjectException {
		setXML(new StringReader(xml));
	}
	/**
	 * XObject constructor comment.
	 */
	public void setXMLFile(String xmlFileName)
		throws XObjectException, FileNotFoundException {
		setXML(new FileReader(xmlFileName));
	}
}

