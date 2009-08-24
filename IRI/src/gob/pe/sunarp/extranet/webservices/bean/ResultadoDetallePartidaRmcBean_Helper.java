/**
 * ResultadoDetallePartidaRmcBean_Helper.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * cf90721.10 v53107135043
 */

package gob.pe.sunarp.extranet.webservices.bean;

public class ResultadoDetallePartidaRmcBean_Helper {
    // Type metadata
    private static final com.ibm.ws.webservices.engine.description.TypeDesc typeDesc =
        new com.ibm.ws.webservices.engine.description.TypeDesc(ResultadoDetallePartidaRmcBean.class);

    static {
        typeDesc.setOption("buildNum","cf90721.10");
        com.ibm.ws.webservices.engine.description.FieldDesc field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("codigoError");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "codigoError"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("fechaOperacion");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "fechaOperacion"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("montoPagado");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "montoPagado"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("partidaRmcBeans");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "partidaRmcBeans"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://bean.webservices.extranet.sunarp.pe.gob", "PartidaRmcBean"));
        typeDesc.addFieldDesc(field);
    };

    /**
     * Return type metadata object
     */
    public static com.ibm.ws.webservices.engine.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static com.ibm.ws.webservices.engine.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class javaType,  
           javax.xml.namespace.QName xmlType) {
        return 
          new ResultadoDetallePartidaRmcBean_Ser(
            javaType, xmlType, typeDesc);
    };

    /**
     * Get Custom Deserializer
     */
    public static com.ibm.ws.webservices.engine.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class javaType,  
           javax.xml.namespace.QName xmlType) {
        return 
          new ResultadoDetallePartidaRmcBean_Deser(
            javaType, xmlType, typeDesc);
    };

}
