/**
 * ActoBean_Helper.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * cf90721.10 v53107135043
 */

package gob.pe.sunarp.extranet.webservices.bean;

public class ActoBean_Helper {
    // Type metadata
    private static final com.ibm.ws.webservices.engine.description.TypeDesc typeDesc =
        new com.ibm.ws.webservices.engine.description.TypeDesc(ActoBean.class);

    static {
        typeDesc.setOption("buildNum","cf90721.10");
        com.ibm.ws.webservices.engine.description.FieldDesc field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("formaCondicionBean");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "formaCondicionBean"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://bean.webservices.extranet.sunarp.pe.gob", "FormaCondicionBean"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("bienBeans");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "bienBeans"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://webservices.extranet.sunarp.pe.gob", "ArrayOf_tns2_nillable_BienBean"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("descripcionActo");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "descripcionActo"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("documentoBeans");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "documentoBeans"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://webservices.extranet.sunarp.pe.gob", "ArrayOf_tns2_nillable_DocumentoBean"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("fechaActoConstitutivo");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "fechaActoConstitutivo"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("montoAfectacion");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "montoAfectacion"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("participanteBeans");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "participanteBeans"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://webservices.extranet.sunarp.pe.gob", "ArrayOf_tns2_nillable_ParticipanteBean"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("anoplazo");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "anoplazo"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("diaplazo");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "diaplazo"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("mesplazo");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "mesplazo"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("tituloBeans");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("", "tituloBeans"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://bean.webservices.extranet.sunarp.pe.gob", "TituloBean"));
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
          new ActoBean_Ser(
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
          new ActoBean_Deser(
            javaType, xmlType, typeDesc);
    };

}
