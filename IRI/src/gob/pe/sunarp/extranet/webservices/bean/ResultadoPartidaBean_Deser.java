/**
 * ResultadoPartidaBean_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * cf90721.10 v53107135043
 */

package gob.pe.sunarp.extranet.webservices.bean;

public class ResultadoPartidaBean_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public ResultadoPartidaBean_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new gob.pe.sunarp.extranet.webservices.bean.ResultadoPartidaBean();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        if (qName==QName_0_0) {
          ((ResultadoPartidaBean)value).setCodigoError(strValue);
          return true;}
        else if (qName==QName_0_1) {
          ((ResultadoPartidaBean)value).setFechaOperacion(strValue);
          return true;}
        else if (qName==QName_0_2) {
          ((ResultadoPartidaBean)value).setMontoPagado(strValue);
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
        if (qName==QName_0_53) {
          if (objValue instanceof java.util.List) {
            gob.pe.sunarp.extranet.webservices.bean.PartidaBean[] array = new gob.pe.sunarp.extranet.webservices.bean.PartidaBean[((java.util.List)objValue).size()];
            ((java.util.List)objValue).toArray(array);
            ((ResultadoPartidaBean)value).setPartidaBeans(array);
          } else { 
            ((ResultadoPartidaBean)value).setPartidaBeans((gob.pe.sunarp.extranet.webservices.bean.PartidaBean[])objValue);}
          return true;}
        return false;
    }
    protected boolean tryElementSetFromList(javax.xml.namespace.QName qName, java.util.List listValue) {
        return false;
    }
    private final static javax.xml.namespace.QName QName_0_2 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "montoPagado");
    private final static javax.xml.namespace.QName QName_0_53 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "partidaBeans");
    private final static javax.xml.namespace.QName QName_0_0 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "codigoError");
    private final static javax.xml.namespace.QName QName_0_1 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "fechaOperacion");
}
