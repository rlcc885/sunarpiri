/**
 * TituloBean_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * cf90721.10 v53107135043
 */

package gob.pe.sunarp.extranet.webservices.bean;

public class TituloBean_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public TituloBean_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new gob.pe.sunarp.extranet.webservices.bean.TituloBean();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        if (qName==QName_0_48) {
          ((TituloBean)value).setDerechosPagados(strValue);
          return true;}
        else if (qName==QName_0_49) {
          ((TituloBean)value).setDescripcionOficinaRegistral(strValue);
          return true;}
        else if (qName==QName_0_50) {
          ((TituloBean)value).setFechaInscripcion(strValue);
          return true;}
        else if (qName==QName_0_37) {
          ((TituloBean)value).setFechaPresentacion(strValue);
          return true;}
        else if (qName==QName_0_51) {
          ((TituloBean)value).setNumeroOrden(strValue);
          return true;}
        else if (qName==QName_0_52) {
          ((TituloBean)value).setNumeroRecibos(strValue);
          return true;}
        else if (qName==QName_0_39) {
          ((TituloBean)value).setNumeroTitulo(strValue);
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
    private final static javax.xml.namespace.QName QName_0_52 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "numeroRecibos");
    private final static javax.xml.namespace.QName QName_0_39 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "numeroTitulo");
    private final static javax.xml.namespace.QName QName_0_51 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "numeroOrden");
    private final static javax.xml.namespace.QName QName_0_48 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "derechosPagados");
    private final static javax.xml.namespace.QName QName_0_50 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "fechaInscripcion");
    private final static javax.xml.namespace.QName QName_0_49 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "descripcionOficinaRegistral");
    private final static javax.xml.namespace.QName QName_0_37 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "fechaPresentacion");
}
