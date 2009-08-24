/**
 * ActoBean_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * cf90721.10 v53107135043
 */

package gob.pe.sunarp.extranet.webservices.bean;

public class ActoBean_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public ActoBean_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new gob.pe.sunarp.extranet.webservices.bean.ActoBean();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        if (qName==QName_0_21) {
          ((ActoBean)value).setDescripcionActo(strValue);
          return true;}
        else if (qName==QName_0_23) {
          ((ActoBean)value).setFechaActoConstitutivo(strValue);
          return true;}
        else if (qName==QName_0_24) {
          ((ActoBean)value).setMontoAfectacion(strValue);
          return true;}
        else if (qName==QName_0_26) {
          ((ActoBean)value).setAnoplazo(strValue);
          return true;}
        else if (qName==QName_0_27) {
          ((ActoBean)value).setDiaplazo(strValue);
          return true;}
        else if (qName==QName_0_28) {
          ((ActoBean)value).setMesplazo(strValue);
          return true;}
        return false;
    }
    protected boolean tryAttributeSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        return false;
    }
    protected boolean tryElementSetFromObject(javax.xml.namespace.QName qName, java.lang.Object objValue) {
        if (objValue == null) {
          return true;
        }
        if (qName==QName_0_19) {
          ((ActoBean)value).setFormaCondicionBean((gob.pe.sunarp.extranet.webservices.bean.FormaCondicionBean)objValue);
          return true;}
        else if (qName==QName_0_20) {
          if (objValue instanceof java.util.List) {
            gob.pe.sunarp.extranet.webservices.bean.BienBean[] array = new gob.pe.sunarp.extranet.webservices.bean.BienBean[((java.util.List)objValue).size()];
            ((java.util.List)objValue).toArray(array);
            ((ActoBean)value).setBienBeans(array);
          } else { 
            ((ActoBean)value).setBienBeans((gob.pe.sunarp.extranet.webservices.bean.BienBean[])objValue);}
          return true;}
        else if (qName==QName_0_22) {
          if (objValue instanceof java.util.List) {
            gob.pe.sunarp.extranet.webservices.bean.DocumentoBean[] array = new gob.pe.sunarp.extranet.webservices.bean.DocumentoBean[((java.util.List)objValue).size()];
            ((java.util.List)objValue).toArray(array);
            ((ActoBean)value).setDocumentoBeans(array);
          } else { 
            ((ActoBean)value).setDocumentoBeans((gob.pe.sunarp.extranet.webservices.bean.DocumentoBean[])objValue);}
          return true;}
        else if (qName==QName_0_25) {
          if (objValue instanceof java.util.List) {
            gob.pe.sunarp.extranet.webservices.bean.ParticipanteBean[] array = new gob.pe.sunarp.extranet.webservices.bean.ParticipanteBean[((java.util.List)objValue).size()];
            ((java.util.List)objValue).toArray(array);
            ((ActoBean)value).setParticipanteBeans(array);
          } else { 
            ((ActoBean)value).setParticipanteBeans((gob.pe.sunarp.extranet.webservices.bean.ParticipanteBean[])objValue);}
          return true;}
        else if (qName==QName_0_29) {
          ((ActoBean)value).setTituloBeans((gob.pe.sunarp.extranet.webservices.bean.TituloBean)objValue);
          return true;}
        return false;
    }
    protected boolean tryElementSetFromList(javax.xml.namespace.QName qName, java.util.List listValue) {
        return false;
    }
    private final static javax.xml.namespace.QName QName_0_26 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "anoplazo");
    private final static javax.xml.namespace.QName QName_0_29 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "tituloBeans");
    private final static javax.xml.namespace.QName QName_0_28 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "mesplazo");
    private final static javax.xml.namespace.QName QName_0_27 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "diaplazo");
    private final static javax.xml.namespace.QName QName_0_24 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "montoAfectacion");
    private final static javax.xml.namespace.QName QName_0_23 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "fechaActoConstitutivo");
    private final static javax.xml.namespace.QName QName_0_20 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "bienBeans");
    private final static javax.xml.namespace.QName QName_0_22 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "documentoBeans");
    private final static javax.xml.namespace.QName QName_0_21 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "descripcionActo");
    private final static javax.xml.namespace.QName QName_0_19 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "formaCondicionBean");
    private final static javax.xml.namespace.QName QName_0_25 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "participanteBeans");
}
