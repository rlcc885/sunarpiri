<%@ page import="org.apache.soap.*" %>
<%@ page import="org.apache.soap.server.*" %>

<h1>Stop a Service</h1>

<% 
ServiceManager serviceManager =
  org.apache.soap.server.http.ServerHTTPUtils.getServiceManagerFromContext(application);

String id = request.getParameter ("id");
if (id == null) {
  String[] serviceNames = serviceManager.list ();
  if (serviceNames.length == 0) {
    out.println ("<p>Sorry, there are no services currently registered.</p>");
  } else {
    out.println ("<p>Select the service to be stopped:</p>");
    %>
    <ul>
    <%
    for (int i = 0; i < serviceNames.length && !serviceNames[i].equals("SOAP_UNDEPLOYED_BUT_REGISTERED"); i++) {
      id = serviceNames[i];
    %>
      <li><a href="stop.jsp?id=<%=id%>"><%= id%></a></li>
    <%
    }
    %>
    </ul>
    <%
  }
} else {
  try {
    serviceManager.undeploy (id);
    out.println ("Service named '" + id + "' stopped.<BR>");
    out.println("Any further access to service '" + id+ "' will cause a SOAP fault.");
  } catch (SOAPException e) {
    out.println ("Ouch, coudn't undeploy service '" + id + "' because: ");
    e.getMessage ();
  }
}
%>
