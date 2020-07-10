<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="_menu.jsp"></jsp:include>

<div id = "draftCreate" style="display: block; text-align: center">
    <form method="POST" style="display: inline-block" action="${pageContext.request.contextPath}/draftCreation">
        <input type = "hidden" name = "redirectId"/>
        <table>
            <tr>
                <td>Enter a name for your draft:</td>
                <td><input type="text" name = "draftName"/></td>
            </tr>
            <tr>
                <td>Enter the username for your players:</td>
            </tr>
            <tr>
                <td><input type="text" name = "player1"/></td>
            </tr>
            <tr>
                <td><input type="text" name = "player2"/></td>
            </tr>
            <tr>
                <td><input type="text" name = "player3"/></td>
            </tr>
            <tr>
                <td><input type="text" name = "player4"/></td>
            </tr>
            <tr>
                <td><input type="text" name = "player5"/></td>
            </tr>
            <tr>
                <td><input type="text" name = "player6"/></td>
            </tr>
            <tr>
                <td><input type="text" name = "player7"/></td>
            </tr>
            <tr>
                <td><input type="text" name = "player8"/></td>
            </tr>
        </table>
    </form>
</div>

</body>
</html>
