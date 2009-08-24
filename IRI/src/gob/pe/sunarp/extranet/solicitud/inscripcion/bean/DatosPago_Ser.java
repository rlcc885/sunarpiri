/**
 * DatosPago_Ser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * cf10631.06 v81706232132
 */

package gob.pe.sunarp.extranet.solicitud.inscripcion.bean;

public class DatosPago_Ser extends gob.pe.sunarp.extranet.common.SunarpBean_Ser {
    /**
     * Constructor
     */
    public DatosPago_Ser(
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
        attributes = super.addAttributes(attributes, value, context);
        return attributes;
    }
    protected void addElements(
        java.lang.Object value,
        com.ibm.ws.webservices.engine.encoding.SerializationContext context)
        throws java.io.IOException
    {
        super.addElements(value, context);
        DatosPago bean = (DatosPago) value;
        java.lang.Object propValue;
        javax.xml.namespace.QName propQName;
        {
          propQName = QName_0_138;
          propValue = bean.getCodigoFormaPago();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_4,
              true,null,context);
          }
          propQName = QName_0_139;
          propValue = bean.getCodigoTipoPago();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_4,
              true,null,context);
          }
          propQName = QName_0_140;
          propValue = bean.getCostoTotalServicio();
          serializeChild(propQName, null, 
              propValue, 
              QName_1_146,
              true,null,context);
          propQName = QName_0_141;
          propValue = bean.getDescripcionFormaPago();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_4,
              true,null,context);
          }
          propQName = QName_0_142;
          propValue = bean.getDescripcionTipoPago();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_4,
              true,null,context);
          }
          propQName = QName_0_143;
          propValue = bean.getFechaPago();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_4,
              true,null,context);
          }
          propQName = QName_0_144;
          propValue = bean.getHoraPago();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_4,
              true,null,context);
          }
          propQName = QName_0_145;
          propValue = bean.getNumeroOperacion();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_4,
              true,null,context);
          }
        }
    }
    private final static javax.xml.namespace.QName QName_0_138 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "codigoFormaPago");
    private final static javax.xml.namespace.QName QName_0_142 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "descripcionTipoPago");
    private final static javax.xml.namespace.QName QName_0_145 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "numeroOperacion");
    private final static javax.xml.namespace.QName QName_1_146 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://www.w3.org/2001/XMLSchema",
                  "decimal");
    private final static javax.xml.namespace.QName QName_0_144 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "horaPago");
    private final static javax.xml.namespace.QName QName_0_141 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "descripcionFormaPago");
    private final static javax.xml.namespace.QName QName_0_143 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "fechaPago");
    private final static javax.xml.namespace.QName QName_1_4 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://www.w3.org/2001/XMLSchema",
                  "string");
    private final static javax.xml.namespace.QName QName_0_140 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "costoTotalServicio");
    private final static javax.xml.namespace.QName QName_0_139 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "codigoTipoPago");
}
