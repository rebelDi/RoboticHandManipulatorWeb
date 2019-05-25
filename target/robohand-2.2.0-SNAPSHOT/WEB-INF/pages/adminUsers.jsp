<%@ page import="java.util.ArrayList" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="roboticHand.Model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.gson.reflect.TypeToken" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Panel</title>
    <link href="/resources/css/style.css" type="text/css" rel="stylesheet">
    <script src="/resources/js/jQuery.js"></script>
    <script src="/resources/js/adminUsers.js"></script>
</head>
<body>
<center>

    <ul class="ulBar">
        <li class="ilBar"><a href="/action/panel" class="ilButton">Control Panel</a></li>
        <li class="ilBar"><a href="/user/userInfoRedirect" class="ilButton">User Info</a></li>
        <% String rights = (String) session.getAttribute("rights");
            if(rights.equals("S") || rights.equals("A")){
            if(rights.equals("A")){%>
        <li class="ilBar"><a class="ilButton active">Admin</a></li>
        <%}else{%>
        <li class="ilBar"><input name="action" type="button" value="Admin" class="ilButton active" />
            <%}%>
            <%if(rights.equals("S")){ %>
            <ul class="submenu">
                <li class="ilBar"><a href="/admin/actions" class="ilButton adminButton">Imitator</a></li>
            </ul>
            <%}%>
        </li>
        <%}%>
        <% if(rights.equals("U")){ %>
        <li class="ilBar"><a href="/question/" class="ilButton">Messages</a></li>
        <%} else {%>
        <li class="ilBar"><a href="/question/" class="ilButton">Messages</a></li>
        <%}%>
        <li class="ilBar"><a href="/user/logInRedirect" class="ilButton">Exit</a></li>
    </ul>
    <hr />

    <table>
        <tr>
            <td>
                <form style="float: left">
                    <a href="/admin/waitingList" style="background-color: lightpink" class="ilButton buttonLaunch">Show only users waiting for approval</a>
                </form>
            </td>
            <td>
                <form style="float: right">
                    <a href="/admin/users" style="background-color: lightpink" class="ilButton buttonLaunch">Show all users</a>
                </form>
            </td>
        </tr>
    </table>
    <br>

    <table width="59%" border="1" class="tableActions" id="tableActions">
        <tr style="text-align: center; background-color: azure">
            <td>
                Login
            </td>
            <td>
                Name
            </td>
            <td>
                Surname
            </td>
            <% if(rights.equals("A")){ %>
                <td colspan="2">
            <% }else if(rights.equals("S")){%>
                <td colspan="3">
            <% }%>
                Rights
            </td>
        </tr>
        <center>
            <%
                String usersJson = (String) session.getAttribute("users");
                Gson gson = new Gson();
                ArrayList<User> result = gson.fromJson(usersJson, new TypeToken<List<User>>() {}.getType());
                for(int i = 0; i < result.size(); i++) {
            %>
            <tr>
                <td id="<%= result.get(i).getName()%>">
                    <%= result.get(i).getLogin()%>
                </td>
                <td>
                    <%= result.get(i).getName()%>
                </td>
                <td>
                    <%= result.get(i).getSurname()%>
                </td>
                <input type="hidden" name="loginU" value="<%=result.get(i).getLogin()%>">
                <td>
                    <input id="B<%=i%>" type="button" value="Ban" class="ilButton" style="text-align: center;
                <%=(result.get(i).getRights() == 'B' ? "background-color:#ff3a3a;" : "")%>"
                           onclick="changeUserData('<%=result.get(i).getLogin()%>', 'B', <%=i%>)"/>
                </td>
                <td>
                    <input id="U<%=i%>" type="button" value="User" class="ilButton" style="text-align: center;
            <%=(result.get(i).getRights() == 'U' ? "background-color:#2bff36;" : "")%>"
                           onclick="changeUserData('<%=result.get(i).getLogin()%>', 'U', <%=i%>)" />
                </td>
                <% if(rights.equals("S")){ %>
                    <td>
                        <input id="A<%=i%>" type="button" value="Admin" class="ilButton" style="text-align: center;
                    <%=(result.get(i).getRights() == 'A' ? "background-color:#2bff36;" : "")%>"
                               onclick="changeUserData('<%=result.get(i).getLogin()%>', 'A', <%=i%>)" />
                    </td>
                <% }%>
            </tr>
            <%
                }%>
        </center>
    </table>
</center>
</body>
</html>