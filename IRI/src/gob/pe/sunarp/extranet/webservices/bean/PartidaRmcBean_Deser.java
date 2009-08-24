/**
 * PartidaRmcBean_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * cf90721.10 v53107135043
 */

package gob.pe.sunarp.extranet.webservices.bean;

public class PartidaRmcBean_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public PartidaRmcBean_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new gob.pe.sunarp.extranet.webservices.bean.PartidaRmcBean();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        if (qName==QName_0_41) {
          ((PartidaRmcBean)value).setNumeroPartida(strValue);
          return true;}
        else if (qName==QName_0_42) {
          ((PartidaRmcBean)value).setNumeroPartidaMigrada(strValue);
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
        if (qName==QName_0_40) {
          if (objValue instanceof java.util.List) {
            gob.pe.sunarp.extranet.webservices.bean.ActoBean[] array = new gob.pe.sunarp.extranet.webservices.bean.ActoBean[((java.util.List)objValue).size()];
            ((java.util.List)objValue).toArray(array);
            ((PartidaRmcBean)value).setActoBeans(array);
          } else { 
            ((PartidaRmcBean)value).setActoBeans((gob.pe.sunarp.extranet.webservices.bean.ActoBean[])objValue);}
          return true;}
        else if (qName==QName_0_43) {
          if (objValue instanceof java.util.List) {
            gob.pe.sunarp.extranet.webservices.bean.TituloPendienteBean[] array = new gob.pe.sunarp.extranet.webservices.bean.TituloPendienteBean[((java.util.List)objValue).size()];
            ((java.util.List)objValue).toArray(array);
            ((PartidaRmcBean)value).setTituloPendienteBeans(array);
          } else { 
            ((PartidaRmcBean)value).setTituloPendienteBeans((gob.pe.sunarp.extranet.webservices.bean.TituloPendienteBean[])objValue);}
          return true;}
        return false;
    }
    protected boolean tryElementSetFromList(javax.xml.namespace.QName qName, java.util.List listValue) {
        return false;
    }
    private final static javax.xml.namespace.QName QName_0_40 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "actoBeans");
    private final static javax.xml.namespace.QName QName_0_41 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "numeroPartida");
    private final static javax.xml.namespace.QName QName_0_43 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "tituloPendienteBeans");
    private final static javax.xml.namespace.QName QName_0_42 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "numeroPartidaMigrada");
}
