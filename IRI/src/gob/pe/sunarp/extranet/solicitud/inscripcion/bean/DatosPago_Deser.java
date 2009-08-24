/**
 * DatosPago_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * cf10631.06 v81706232132
 */

package gob.pe.sunarp.extranet.solicitud.inscripcion.bean;

public class DatosPago_Deser extends gob.pe.sunarp.extranet.common.SunarpBean_Deser {
    /**
     * Constructor
     */
    public DatosPago_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new gob.pe.sunarp.extranet.solicitud.inscripcion.bean.DatosPago();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        if (qName==QName_0_138) {
          ((DatosPago)value).setCodigoFormaPago(strValue);
          return true;}
        else if (qName==QName_0_139) {
          ((DatosPago)value).setCodigoTipoPago(strValue);
          return true;}
        else if (qName==QName_0_140) {
          ((DatosPago)value).setCostoTotalServicio(com.ibm.ws.webservices.engine.encoding.ser.SimpleDeserializer.parseBigDecimal(strValue));
          return true;}
        else if (qName==QName_0_141) {
          ((DatosPago)value).setDescripcionFormaPago(strValue);
          return true;}
        else if (qName==QName_0_142) {
          ((DatosPago)value).setDescripcionTipoPago(strValue);
          return true;}
        else if (qName==QName_0_143) {
          ((DatosPago)value).setFechaPago(strValue);
          return true;}
        else if (qName==QName_0_144) {
          ((DatosPago)value).setHoraPago(strValue);
          return true;}
        else if (qName==QName_0_145) {
          ((DatosPago)value).setNumeroOperacion(strValue);
          return true;}
        return super.tryElementSetFromString(qName, strValue);
    }
    protected boolean tryAttributeSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        return super.tryAttributeSetFromString(qName, strValue);
    }
    protected boolean tryElementSetFromObject(javax.xml.namespace.QName qName, java.lang.Object objValue) {
        if (objValue == null) {
          return true;
        }
        return super.tryElementSetFromObject(qName, objValue);
    }
    protected boolean tryElementSetFromList(javax.xml.namespace.QName qName, java.util.List listValue) {
        return super.tryElementSetFromList(qName, listValue);
    }
    private final static javax.xml.namespace.QName QName_0_140 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "costoTotalServicio");
    private final static javax.xml.namespace.QName QName_0_144 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "horaPago");
    private final static javax.xml.namespace.QName QName_0_145 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "numeroOperacion");
    private final static javax.xml.namespace.QName QName_0_142 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "descripcionTipoPago");
    private final static javax.xml.namespace.QName QName_0_138 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "codigoFormaPago");
    private final static javax.xml.namespace.QName QName_0_139 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "codigoTipoPago");
    private final static javax.xml.namespace.QName QName_0_143 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "fechaPago");
    private final static javax.xml.namespace.QName QName_0_141 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "descripcionFormaPago");
}
