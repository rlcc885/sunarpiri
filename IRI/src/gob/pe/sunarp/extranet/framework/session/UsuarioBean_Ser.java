/**
 * UsuarioBean_Ser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * cf10631.06 v81706232132
 */

package gob.pe.sunarp.extranet.framework.session;

public class UsuarioBean_Ser extends gob.pe.sunarp.extranet.common.SunarpBean_Ser {
    /**
     * Constructor
     */
    public UsuarioBean_Ser(
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
        UsuarioBean bean = (UsuarioBean) value;
        java.lang.Object propValue;
        javax.xml.namespace.QName propQName;
        {
          propQName = QName_0_111;
          propValue = bean.getUserId();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_4,
              true,null,context);
          }
          propQName = QName_0_112;
          propValue = bean.getCuentaId();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_4,
              true,null,context);
          }
          propQName = QName_0_113;
          propValue = bean.getPeNatuId();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_4,
              true,null,context);
          }
          propQName = QName_0_114;
          propValue = new java.lang.Boolean(bean.getExonPago());
          serializeChild(propQName, null, 
              propValue, 
              QName_1_136,
              true,null,context);
          propQName = QName_0_115;
          propValue = bean.getTipoUser();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_4,
              true,null,context);
          }
          propQName = QName_0_116;
          propValue = new java.lang.Boolean(bean.getFgInterno());
          serializeChild(propQName, null, 
              propValue, 
              QName_1_136,
              true,null,context);
          propQName = QName_0_117;
          propValue = new java.lang.Boolean(bean.getFgIndividual());
          serializeChild(propQName, null, 
              propValue, 
              QName_1_136,
              true,null,context);
          propQName = QName_0_118;
          propValue = new java.lang.Boolean(bean.getFgAdmin());
          serializeChild(propQName, null, 
              propValue, 
              QName_1_136,
              true,null,context);
          propQName = QName_0_119;
          propValue = new java.lang.Long(bean.getPerfilId());
          serializeChild(propQName, null, 
              propValue, 
              QName_1_137,
              true,null,context);
          propQName = QName_0_120;
          propValue = new java.lang.Long(bean.getNivelAccesoId());
          serializeChild(propQName, null, 
              propValue, 
              QName_1_137,
              true,null,context);
          propQName = QName_0_121;
          propValue = bean.getCodOrg();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_4,
              true,null,context);
          }
          propQName = QName_0_122;
          propValue = bean.getLinPrePago();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_4,
              true,null,context);
          }
          propQName = QName_0_123;
          propValue = new java.lang.Double(bean.getSaldo());
          serializeChild(propQName, null, 
              propValue, 
              QName_1_101,
              true,null,context);
          propQName = QName_0_124;
          propValue = bean.getOficRegistralId();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_4,
              true,null,context);
          }
          propQName = QName_0_125;
          propValue = bean.getRegPublicoId();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_4,
              true,null,context);
          }
          propQName = QName_0_126;
          propValue = bean.getJurisdiccionId();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_4,
              true,null,context);
          }
          propQName = QName_0_127;
          propValue = bean.getPersonaId();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_4,
              true,null,context);
          }
          propQName = QName_0_128;
          propValue = bean.getNum_contrato();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_4,
              true,null,context);
          }
          propQName = QName_0_129;
          propValue = bean.getUserAdminOrg();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_4,
              true,null,context);
          }
          propQName = QName_0_130;
          propValue = bean.getCur();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_4,
              true,null,context);
          }
          propQName = QName_0_131;
          propValue = bean.getLineaPrePagoOrganizacion();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_4,
              true,null,context);
          }
          propQName = QName_0_132;
          propValue = bean.getNombres();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_4,
              true,null,context);
          }
          propQName = QName_0_133;
          propValue = bean.getApePat();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_4,
              true,null,context);
          }
          propQName = QName_0_134;
          propValue = bean.getApeMat();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_4,
              true,null,context);
          }
          propQName = QName_0_135;
          propValue = bean.getRazSocial();
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
    private final static javax.xml.namespace.QName QName_1_101 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://www.w3.org/2001/XMLSchema",
                  "double");
    private final static javax.xml.namespace.QName QName_0_134 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "apeMat");
    private final static javax.xml.namespace.QName QName_0_120 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "nivelAccesoId");
    private final static javax.xml.namespace.QName QName_0_128 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "num_contrato");
    private final static javax.xml.namespace.QName QName_0_130 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "cur");
    private final static javax.xml.namespace.QName QName_0_111 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "userId");
    private final static javax.xml.namespace.QName QName_0_131 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "lineaPrePagoOrganizacion");
    private final static javax.xml.namespace.QName QName_0_127 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "personaId");
    private final static javax.xml.namespace.QName QName_1_137 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://www.w3.org/2001/XMLSchema",
                  "long");
    private final static javax.xml.namespace.QName QName_0_113 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "peNatuId");
    private final static javax.xml.namespace.QName QName_0_117 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "fgIndividual");
    private final static javax.xml.namespace.QName QName_0_135 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "razSocial");
    private final static javax.xml.namespace.QName QName_0_116 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "fgInterno");
    private final static javax.xml.namespace.QName QName_1_4 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://www.w3.org/2001/XMLSchema",
                  "string");
    private final static javax.xml.namespace.QName QName_0_129 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "userAdminOrg");
    private final static javax.xml.namespace.QName QName_0_122 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "linPrePago");
    private final static javax.xml.namespace.QName QName_0_115 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "tipoUser");
    private final static javax.xml.namespace.QName QName_0_125 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "regPublicoId");
    private final static javax.xml.namespace.QName QName_1_136 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://www.w3.org/2001/XMLSchema",
                  "boolean");
    private final static javax.xml.namespace.QName QName_0_124 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "oficRegistralId");
    private final static javax.xml.namespace.QName QName_0_118 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "fgAdmin");
    private final static javax.xml.namespace.QName QName_0_112 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "cuentaId");
    private final static javax.xml.namespace.QName QName_0_133 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "apePat");
    private final static javax.xml.namespace.QName QName_0_121 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "codOrg");
    private final static javax.xml.namespace.QName QName_0_123 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "saldo");
    private final static javax.xml.namespace.QName QName_0_132 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "nombres");
    private final static javax.xml.namespace.QName QName_0_126 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "jurisdiccionId");
    private final static javax.xml.namespace.QName QName_0_119 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "perfilId");
    private final static javax.xml.namespace.QName QName_0_114 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "",
                  "exonPago");
}
