/**
 * TituloEnLineaBean_Helper.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * cf10631.06 v81706232132
 */

package gob.pe.sunarp.extranet.webservices.bean;

public class TituloEnLineaBean_Helper {
    // Type metadata
    private static final com.ibm.ws.webservices.engine.description.TypeDesc typeDesc =
        new com.ibm.ws.webservices.engine.description.TypeDesc(TituloEnLineaBean.class);

    static {
        typeDesc.setOption("buildNum","cf10631.06");
        com.ibm.ws.webservices.engine.description.FieldDesc field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("deno");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "deno"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://beans.denominacion.solicitud.extranet.sunarp.pe.gob", "Denominacion"));
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
          new TituloEnLineaBean_Ser(
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
          new TituloEnLineaBean_Deser(
            javaType, xmlType, typeDesc);
    };

}
