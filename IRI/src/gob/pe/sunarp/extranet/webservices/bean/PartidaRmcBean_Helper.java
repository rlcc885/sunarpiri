/**
 * PartidaRmcBean_Helper.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * cf90721.10 v53107135043
 */

package gob.pe.sunarp.extranet.webservices.bean;

public class PartidaRmcBean_Helper {
    // Type metadata
    private static final com.ibm.ws.webservices.engine.description.TypeDesc typeDesc =
        new com.ibm.ws.webservices.engine.description.TypeDesc(PartidaRmcBean.class);

    static {
        typeDesc.setOption("buildNum","cf90721.10");
        com.ibm.ws.webservices.engine.description.FieldDesc field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("actoBeans");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "actoBeans"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://webservices.extranet.sunarp.pe.gob", "ArrayOf_tns2_nillable_ActoBean"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("numeroPartida");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "numeroPartida"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("numeroPartidaMigrada");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "numeroPartidaMigrada"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("tituloPendienteBeans");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "tituloPendienteBeans"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://webservices.extranet.sunarp.pe.gob", "ArrayOf_tns2_nillable_TituloPendienteBean"));
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
          new PartidaRmcBean_Ser(
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
          new PartidaRmcBean_Deser(
            javaType, xmlType, typeDesc);
    };

}
