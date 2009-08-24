/**
 * ActoBean_Ser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * cf90721.10 v53107135043
 */

package gob.pe.sunarp.extranet.webservices.bean;

public class ActoBean_Ser extends com.ibm.ws.webservices.engine.encoding.ser.BeanSerializer {
    /**
     * Constructor
     */
    public ActoBean_Ser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    public void serialize(
        javax.xml.namespace.QName name,
        org.xml.sax.Attributes attributes,
        java.lang.Object value,
        com.ibm.ws.webservices.engine.encoding.SerializationContext context)
        throws java.io.IOException
    {
        context.startElement(name, addAttributes(attributes, value, context));
        addElements(value, context);
        context.endElement();
    }
    protected org.xml.sax.Attributes addAttributes(
        org.xml.sax.Attributes attributes,
        java.lang.Object value,
        com.ibm.ws.webservices.engine.encoding.SerializationContext context)
        throws java.io.IOException
    {
        return attributes;
    }
    protected void addElements(
        java.lang.Object value,
        com.ibm.ws.webservices.engine.encoding.SerializationContext context)
        throws java.io.IOException
    {
        ActoBean bean = (ActoBean) value;
        java.lang.Object propValue;
        javax.xml.namespace.QName propQName;
        {
          propQName = QName_0_19;
          propValue = bean.getFormaCondicionBean();
          serializeChild(propQName, null, 
              propValue, 
              QName_2_30,
              true,null,context);
          propQName = QName_0_20;
          propValue = bean.getBienBeans();
          serializeChild(propQName, null, 
              propValue, 
              QName_3_31,
              true,null,context);
          propQName = QName_0_21;
          propValue = bean.getDescripcionActo();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_4,
              true,null,context);
          }
          propQName = QName_0_22;
          propValue = bean.getDocumentoBeans();
          serializeChild(propQName, null, 
              propValue, 
              QName_3_32,
              true,null,context);
          propQName = QName_0_23;
          propValue = bean.getFechaActoConstitutivo();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_4,
              true,null,context);
          }
          propQName = QName_0_24;
          propValue = bean.getMontoAfectacion();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_4,
              true,null,context);
          }
          propQName = QName_0_25;
          propValue = bean.getParticipanteBeans();
          serializeChild(propQName, null, 
              propValue, 
              QName_3_33,
              true,null,context);
          propQName = QName_0_26;
          propValue = bean.getAnoplazo();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_4,
              true,null,context);
          }
          propQName = QName_0_27;
          propValue = bean.getDiaplazo();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_4,
              true,null,context);
          }
          propQName = QName_0_28;
          propValue = bean.getMesplazo();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_4,
              true,null,context);
          }
          propQName = QName_0_29;
          propValue = bean.getTituloBeans();
          serializeChild(propQName, null, 
              propValue, 
              QName_2_34,
              true,null,context);
        }
    }
    private final static javax.xml.namespace.QName QName_3_31 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://webservices.extranet.sunarp.pe.gob",
                  "ArrayOf_tns2_nillable_BienBean");
    private final static javax.xml.namespace.QName QName_0_26 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "anoplazo");
    private final static javax.xml.namespace.QName QName_0_29 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "tituloBeans");
    private final static javax.xml.namespace.QName QName_2_30 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://bean.webservices.extranet.sunarp.pe.gob",
                  "FormaCondicionBean");
    private final static javax.xml.namespace.QName QName_0_28 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "mesplazo");
    private final static javax.xml.namespace.QName QName_2_34 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://bean.webservices.extranet.sunarp.pe.gob",
                  "TituloBean");
    private final static javax.xml.namespace.QName QName_3_33 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://webservices.extranet.sunarp.pe.gob",
                  "ArrayOf_tns2_nillable_ParticipanteBean");
    private final static javax.xml.namespace.QName QName_0_27 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "diaplazo");
    private final static javax.xml.namespace.QName QName_0_24 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "montoAfectacion");
    private final static javax.xml.namespace.QName QName_0_23 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "fechaActoConstitutivo");
    private final static javax.xml.namespace.QName QName_0_20 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "bienBeans");
    private final static javax.xml.namespace.QName QName_3_32 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://webservices.extranet.sunarp.pe.gob",
                  "ArrayOf_tns2_nillable_DocumentoBean");
    private final static javax.xml.namespace.QName QName_1_4 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://www.w3.org/2001/XMLSchema",
                  "string");
    private final static javax.xml.namespace.QName QName_0_22 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "documentoBeans");
    private final static javax.xml.namespace.QName QName_0_21 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "descripcionActo");
    private final static javax.xml.namespace.QName QName_0_19 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "formaCondicionBean");
    private final static javax.xml.namespace.QName QName_0_25 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "participanteBeans");
}
