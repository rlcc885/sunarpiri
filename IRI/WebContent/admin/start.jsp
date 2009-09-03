<%@ page import="org.apache.soap.*" %>
<%@ page import="org.apache.soap.server.*" %>

<h1>Start a Service</h1>

<% 
ServiceManager serviceManager =
  org.apache.soap.server.http.ServerHTTPUtils.getServiceManagerFromContext(application);

String id = request.getParameter ("id");
if (id == null) 
{
  String[] serviceNames = serviceManager.list ();
  if (serviceNames.length == 0) 
  {
    out.println ("<p>Sorry, there are no services currently stopped.</p>"); 
  } 
  else 
  {
      out.println ("<p>Select the service to be started:</p>");
      %>
      <ul>
      <%
      int i = 0;
      
      //We don't want running services.
      for (; i < serviceNames.length && !serviceNames[i].equals("SOAP_UNDEPLOYED_BUT_REGISTERED"); i++); 
      
      //Go past the tag delimiting stopped services.
      i++;
      for (; i < serviceNames.length; i++) 
      {
        id = serviceNames[i];
      %>
        <li><a href="start.jsp?id=<%=id%>"><%= id%></a></li>
      <%
      }
      %>
      </ul>
      <%
  }
} 
else 
{
  try 
  {
    //Our undeploy will return the descriptor, even for an undeployed service.
    //That way we can still get it.  Query would return service unknown.
    DeploymentDescriptor dd = serviceManager.undeploy(id);
    serviceManager.deploy(dd);
    out.println ("Service named '" + id + "' started.<BR>");
  } catch (SOAPException e) {
    out.println ("Ouch, coudn't start service '" + id + "' because: ");
    e.getMessage ();
  }
}
%>
