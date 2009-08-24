/**
 * PartidaBean_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * cf90721.10 v53107135043
 */

package gob.pe.sunarp.extranet.webservices.bean;

public class PartidaBean_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public PartidaBean_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new gob.pe.sunarp.extranet.webservices.bean.PartidaBean();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        if (qName==QName_0_55) {
          ((PartidaBean)value).setDescripcionAreaRegistral(strValue);
          return true;}
        else if (qName==QName_0_49) {
          ((PartidaBean)value).setDescripcionOficinaRegistral(strValue);
          return true;}
        else if (qName==QName_0_56) {
          ((PartidaBean)value).setDescripcionRegistro(strValue);
          return true;}
        else if (qName==QName_0_57) {
          ((PartidaBean)value).setDescripcionZonaRegistral(strValue);
          return true;}
        else if (qName==QName_0_58) {
          ((PartidaBean)value).setNumeroFicha(strValue);
          return true;}
        else if (qName==QName_0_59) {
          ((PartidaBean)value).setNumeroFolio(strValue);
          return true;}
        else if (qName==QName_0_41) {
          ((PartidaBean)value).setNumeroPartida(strValue);
          return true;}
        else if (qName==QName_0_60) {
          ((PartidaBean)value).setNumeroTomo(strValue);
          return true;}
        else if (qName==QName_0_61) {
          ((PartidaBean)value).setDocumentoIdentidad(strValue);
          return true;}
        else if (qName==QName_0_16) {
          ((PartidaBean)value).setNumeroDocumento(strValue);
          return true;}
        else if (qName==QName_0_62) {
          ((PartidaBean)value).setParticipante(strValue);
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
        return false;
    }
    protected boolean tryElementSetFromList(javax.xml.namespace.QName qName, java.util.List listValue) {
        return false;
    }
    private final static javax.xml.namespace.QName QName_0_16 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "numeroDocumento");
    private final static javax.xml.namespace.QName QName_0_62 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "participante");
    private final static javax.xml.namespace.QName QName_0_61 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "documentoIdentidad");
    private final static javax.xml.namespace.QName QName_0_59 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "numeroFolio");
    private final static javax.xml.namespace.QName QName_0_41 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "numeroPartida");
    private final static javax.xml.namespace.QName QName_0_57 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "descripcionZonaRegistral");
    private final static javax.xml.namespace.QName QName_0_49 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "descripcionOficinaRegistral");
    private final static javax.xml.namespace.QName QName_0_55 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "descripcionAreaRegistral");
    private final static javax.xml.namespace.QName QName_0_60 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "numeroTomo");
    private final static javax.xml.namespace.QName QName_0_58 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "numeroFicha");
    private final static javax.xml.namespace.QName QName_0_56 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "descripcionRegistro");
}
