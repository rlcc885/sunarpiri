<%@ page import="java.util.*, org.apache.soap.server.*, org.apache.soap.util.xml.*" %>

<h1>Deployed Service Information</h1>

<% 
  ServiceManager serviceManager =
    org.apache.soap.server.http.ServerHTTPUtils.getServiceManagerFromContext(application);

  String id = request.getParameter ("id");
  DeploymentDescriptor dd = null;
  try
  {
    dd = (id != null) 
          ? serviceManager.query (id) 
          : null;
  } catch (Exception e)
  {
  
    dd = (id != null) 
          ? serviceManager.undeploy (id) 
          : null;
  }
  
  String[] scopes = {"Request", "Session", "Application"}; 
 
  int EJB_TYPE = 0;
  int DB2SP_TYPE = 1;

  String pt = null;
  
  String ProviderClass = "";
  
  int ProviderType = -1;


  if (id == null) 
  {
    out.println ("<p>Huh? You hafta select a service to display ..</p>");
  } else if (dd == null) 
  {
    out.println ("<p>Service '" + id + "' is not known.</p>");
  } 
  else 
  {
    out.println ("<table border='1' width='100%'>");
    out.println ("<tr>");
    out.println ("<th colspan='2'><h2>'" + id + 
		 "' Service Deployment Descriptor</h2></th>");
    out.println ("</tr>");
    out.println ("<tr>");
    out.println ("<th>Property</th>");
    out.println ("<th>Details</th>");
    out.println ("</tr>");
    out.println ("<tr>");
    out.println ("<td>ID</td>");
    out.println ("<td>" + dd.getID()+ "</td>");
    out.println ("</tr>");
    out.println ("<tr>");
    out.println ("<td>Scope</td>");
    out.println ("<td>" + scopes[dd.getScope()]+ "</td>");
    out.println ("</tr>");
    out.println ("<tr>");
    out.println ("<td>Provider Type</td>");
    
    byte ptb = dd.getProviderType();
    Hashtable pTable = dd.getProps();


    if (ptb == DeploymentDescriptor.PROVIDER_USER_DEFINED)
    {
      //Lets determine what type of service we have
      pt = dd.getServiceClass();
      
      if (pt.indexOf("EJB") != -1)
      {
         ProviderType = EJB_TYPE;
      }
      if (pt.indexOf("WASDB2SP") != -1)
      {
         ProviderType = DB2SP_TYPE;
      }
      
    }
    else
    {
      pt = (ptb==DeploymentDescriptor.PROVIDER_JAVA) ? "java" : "script";
    }
    out.println ("<td>" + pt + "</td>");
    out.println ("</tr>");
    out.println ("<tr>");
    if (ptb == DeploymentDescriptor.PROVIDER_JAVA) 
    {
       
      if (dd.getProviderClass() != null)
      {
         ProviderClass = dd.getProviderClass();
      }
      out.println ("<td>Provider Class</td>");
      out.println ("<td>" + ProviderClass + "</td>");
      out.println ("</tr>");
      out.println ("<tr>");
      out.println ("<td>Use Static Class</td>");
      out.println ("<td>" + dd.getIsStatic()+ "</td>");
    } 
    else if(ProviderType == EJB_TYPE)
    {
      if (dd.getProviderClass() != null)
      {
         ProviderClass = dd.getProviderClass();
      }
      out.println ("<td>JNDI Name</td>");
      out.println ("<td>" + ProviderClass + "</td>");
      out.println ("</tr>");

      out.println ("<tr>");
      out.println ("<td>HomeInterface Class:</td>");
      out.println ("<td>" + (String)pTable.get("FullHomeInterfaceName") + "</td>");
      out.println ("</tr>");

      out.println ("<tr>");
      out.println ("<td>FullContextFactory:</td>");
      out.println ("<td>" + (String)pTable.get("FullContextFactoryName") + "</td>");
      out.println ("</tr>");

      out.println ("<tr>");
      out.println ("<td>ContextProviderURL:</td>");
      out.println ("<td>" + (String)pTable.get("ContextProviderURL") + "</td>");
      out.println ("</tr>");

      out.println ("<tr>");
      out.println ("<td>Use Static Class</td>");
      out.println ("<td>" + dd.getIsStatic()+ "</td>");
    
    }
    else if(ProviderType == DB2SP_TYPE)
    {

      if (pTable.get("datasourceJNDI") != null)
      {
        out.println ("<tr>");
        out.println ("<td>Datasource JNDI Name:</td>");
        out.println ("<td>" + (String)pTable.get("datasourceJNDI") + "</td>");
        out.println ("</tr>");
      }
      else
      {
        out.println ("<tr>");
        out.println ("<td>Database Driver Class::</td>");
        out.println ("<td>" + (String)pTable.get("dbDriver") + "</td>");
        out.println ("</tr>");
        
        out.println ("<tr>");
        out.println ("<td>Database URL:</td>");
        out.println ("<td>" + (String)pTable.get("dbURL") + "</td>");
        out.println ("</tr>");
      }
      if (pTable.get("userID") != null)
      {
        out.println ("<tr>");
        out.println ("<td>User ID:</td>");
        out.println ("<td>" + (String)pTable.get("userID") + "</td>");
        out.println ("</tr>");
      }
    }
    else
    {
      out.println ("<td>Scripting Language</td>");
      out.println ("<td>" + dd.getScriptLanguage () + "</td>");
      out.println ("</tr>");
      out.println ("<tr>");
      if (ptb == DeploymentDescriptor.PROVIDER_SCRIPT_FILE) {
	out.println ("<td>Filename</td>");
	out.println ("<td>" + dd.getScriptFilenameOrString () + "</td>");
      } else {
	out.println ("<td>Script</td>");
	out.println ("<td><pre>" + dd.getScriptFilenameOrString () +
		     "</pre></td>");
      }
    }
    out.println ("</tr>");
    out.println ("<tr>");
    out.println ("<td>Methods</td>");
    out.print ("<td>");
    String[] m = dd.getMethods ();
    for (int i = 0; i < m.length; i++) {
      out.print (m[i]);
      if (i < m.length-1) {
	out.print (", ");
      }
    }
    out.println ("</td>");
    out.println ("</tr>");
    out.println ("<tr>");
    out.println ("<td>Type Mappings</td>");
    out.println ("<td>");
    TypeMapping[] mappings = dd.getMappings();
    if (mappings != null) {
      for (int i = 0; i < mappings.length; i++) {
	out.print (mappings[i]);
	if (i < mappings.length-1) {
	  out.print ("<br>");
	} else {
	  break;
	}
      }
    }
    out.println ("</td>");
    out.println ("</tr>");
    out.println("<tr><td>Default Mapping Registry Class</td><td>" + (dd.getDefaultSMRClass() != null ? dd.getDefaultSMRClass() : "") + "</td></tr>");
    out.println ("</table>");
    
  }
%>
