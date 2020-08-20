<%@ page language="java" contentType="text/html; charset=US-ASCII"
         pageEncoding="US-ASCII" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
    <title>Error Page</title>
</head>
<body>
<% if(response.getStatus() == 500){ %>
<font color="red">Error: <%=exception.getMessage() %></font><br>

<%-- include login page --%>
<%@ include file="userCreationView.jsp"%>
<%}else {%>
Hi There, error code is <%=response.getStatus() %><br>
Please go to <a href="/_menu.jsp">home page</a>
<%} %>
</body>
</html>