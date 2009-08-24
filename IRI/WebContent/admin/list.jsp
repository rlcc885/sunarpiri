<%@ page import="java.util.*, org.apache.soap.server.*" %>

<h1>Service Listing</h1>

<% 
  ServiceManager serviceManager =
    org.apache.soap.server.http.ServerHTTPUtils.getServiceManagerFromContext(application);

  String[] serviceNames = serviceManager.list ();
  if (serviceNames.length == 0) {
    out.println ("<p>Sorry, there are no services currently deployed.</p>");
  } else {
    out.println ("<p>Here are the registered services (select one to see details)</p>");
    
    %>
    <HR>
    <b>Active services:</b><br>    
    <ul>
    <%
    int i = 0;
    for (; i < serviceNames.length && !serviceNames[i].equals("SOAP_UNDEPLOYED_BUT_REGISTERED"); i++) {
          String id = serviceNames[i];
    %>
      <li><a href="showdetails.jsp?id=<%=id%>"><%= id%></a></li>
    <%
    }
    if(i < serviceNames.length)
    {
       %></ul><HR><%
       %><B><% out.println("Stopped services:"); %></B><ul><%
       i++;
    }

    for (; i < serviceNames.length; i++) {
      String id = serviceNames[i];
    %>
      <li><a href="showdetails.jsp?id=<%=id%>"><%= id%></a></li>
    <%
    }
    %>
    </ul>
    <HR>
    <%
  }
%>
