<%@ page language="java" contentType="text/html; charset=US-ASCII"
         pageEncoding="US-ASCII" errorPage="error.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>

<jsp:include page="_menu.jsp"></jsp:include>

<div class="login">
    <h3>User Creation Page</h3>

    <p style="color: #ff0000;">${errorString}</p>

    <div class="form">
        <form method="POST" action="${pageContext.request.contextPath}/userCreation">
            <input type="hidden" name="redirectId" value="${param.redirectId}" />
            <table border="0">
                <tr>
                    <td>User Name</td>
                    <td><input type="text" name="userName" value= "${user.userName}" /> </td>
                </tr>
                <tr>
                    <td>Password</td>
                    <td><input type="password" name="password" value= "${user.password}" /> </td>
                </tr>

                <tr>
                    <td colspan ="2">
                        <input type="submit" value= "Submit" />
                        <a href="${pageContext.request.contextPath}/">Cancel</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
</body>
</html>