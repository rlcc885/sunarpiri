/**
 * UsuarioBean_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * cf10631.06 v81706232132
 */

package gob.pe.sunarp.extranet.framework.session;

public class UsuarioBean_Deser extends gob.pe.sunarp.extranet.common.SunarpBean_Deser {
    /**
     * Constructor
     */
    public UsuarioBean_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new gob.pe.sunarp.extranet.framework.session.UsuarioBean();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        if (qName==QName_0_111) {
          ((UsuarioBean)value).setUserId(strValue);
          return true;}
        else if (qName==QName_0_112) {
          ((UsuarioBean)value).setCuentaId(strValue);
          return true;}
        else if (qName==QName_0_113) {
          ((UsuarioBean)value).setPeNatuId(strValue);
          return true;}
        else if (qName==QName_0_114) {
          ((UsuarioBean)value).setExonPago(com.ibm.ws.webservices.engine.encoding.ser.SimpleDeserializer.parseboolean(strValue));
          return true;}
        else if (qName==QName_0_115) {
          ((UsuarioBean)value).setTipoUser(strValue);
          return true;}
        else if (qName==QName_0_116) {
          ((UsuarioBean)value).setFgInterno(com.ibm.ws.webservices.engine.encoding.ser.SimpleDeserializer.parseboolean(strValue));
          return true;}
        else if (qName==QName_0_117) {
          ((UsuarioBean)value).setFgIndividual(com.ibm.ws.webservices.engine.encoding.ser.SimpleDeserializer.parseboolean(strValue));
          return true;}
        else if (qName==QName_0_118) {
          ((UsuarioBean)value).setFgAdmin(com.ibm.ws.webservices.engine.encoding.ser.SimpleDeserializer.parseboolean(strValue));
          return true;}
        else if (qName==QName_0_119) {
          ((UsuarioBean)value).setPerfilId(com.ibm.ws.webservices.engine.encoding.ser.SimpleDeserializer.parselong(strValue));
          return true;}
        else if (qName==QName_0_120) {
          ((UsuarioBean)value).setNivelAccesoId(com.ibm.ws.webservices.engine.encoding.ser.SimpleDeserializer.parselong(strValue));
          return true;}
        else if (qName==QName_0_121) {
          ((UsuarioBean)value).setCodOrg(strValue);
          return true;}
        else if (qName==QName_0_122) {
          ((UsuarioBean)value).setLinPrePago(strValue);
          return true;}
        else if (qName==QName_0_123) {
          ((UsuarioBean)value).setSaldo(com.ibm.ws.webservices.engine.encoding.ser.SimpleDeserializer.parsedouble(strValue));
          return true;}
        else if (qName==QName_0_124) {
          ((UsuarioBean)value).setOficRegistralId(strValue);
          return true;}
        else if (qName==QName_0_125) {
          ((UsuarioBean)value).setRegPublicoId(strValue);
          return true;}
        else if (qName==QName_0_126) {
          ((UsuarioBean)value).setJurisdiccionId(strValue);
          return true;}
        else if (qName==QName_0_127) {
          ((UsuarioBean)value).setPersonaId(strValue);
          return true;}
        else if (qName==QName_0_128) {
          ((UsuarioBean)value).setNum_contrato(strValue);
          return true;}
        else if (qName==QName_0_129) {
          ((UsuarioBean)value).setUserAdminOrg(strValue);
          return true;}
        else if (qName==QName_0_130) {
          ((UsuarioBean)value).setCur(strValue);
          return true;}
        else if (qName==QName_0_131) {
          ((UsuarioBean)value).setLineaPrePagoOrganizacion(strValue);
          return true;}
        else if (qName==QName_0_132) {
          ((UsuarioBean)value).setNombres(strValue);
          return true;}
        else if (qName==QName_0_133) {
          ((UsuarioBean)value).setApePat(strValue);
          return true;}
        else if (qName==QName_0_134) {
          ((UsuarioBean)value).setApeMat(strValue);
          return true;}
        else if (qName==QName_0_135) {
          ((UsuarioBean)value).setRazSocial(strValue);
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
