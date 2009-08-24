/**
 * ParticipanteBean_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * cf90721.10 v53107135043
 */

package gob.pe.sunarp.extranet.webservices.bean;

public class ParticipanteBean_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public ParticipanteBean_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new gob.pe.sunarp.extranet.webservices.bean.ParticipanteBean();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        if (qName==QName_0_11) {
          ((ParticipanteBean)value).setRazonSocial(strValue);
          return true;}
        else if (qName==QName_0_12) {
          ((ParticipanteBean)value).setDescripcionAbreviadaTipoDocumento(strValue);
          return true;}
        else if (qName==QName_0_13) {
          ((ParticipanteBean)value).setDescripcionTipoParticipacion(strValue);
          return true;}
        else if (qName==QName_0_14) {
          ((ParticipanteBean)value).setDomicilio(strValue);
          return true;}
        else if (qName==QName_0_15) {
          ((ParticipanteBean)value).setNombreParticipante(strValue);
          return true;}
        else if (qName==QName_0_16) {
          ((ParticipanteBean)value).setNumeroDocumento(strValue);
          return true;}
        else if (qName==QName_0_17) {
          ((ParticipanteBean)value).setApellidoMaternoParticipante(strValue);
          return true;}
        else if (qName==QName_0_18) {
          ((ParticipanteBean)value).setApellidoPaternoParticipante(strValue);
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
    private final static javax.xml.namespace.QName QName_0_11 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "razonSocial");
    private final static javax.xml.namespace.QName QName_0_13 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "descripcionTipoParticipacion");
    private final static javax.xml.namespace.QName QName_0_12 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "descripcionAbreviadaTipoDocumento");
    private final static javax.xml.namespace.QName QName_0_17 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "apellidoMaternoParticipante");
    private final static javax.xml.namespace.QName QName_0_18 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "apellidoPaternoParticipante");
    private final static javax.xml.namespace.QName QName_0_14 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "domicilio");
    private final static javax.xml.namespace.QName QName_0_15 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "nombreParticipante");
}
