<%@ page import="roboticHand.Model.Action" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.gson.reflect.TypeToken" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Main Page</title>
    <link rel="stylesheet" href="/resources/css/style.css"/>
    <script src="/resources/js/jQuery.js"></script>
    <script src="/resources/js/formsCheck.js"></script>
    <script src="/resources/js/controlMenu.js"></script>
    <script src="/resources/js/main.js"></script>
    <script src="/resources/js/leap-0.6.4.min.js"></script>
    <script src="/resources/js/leap.js"></script>
</head>
<body>
<center>
    <ul class="ulBar">
        <li class="ilBar"><a class="ilButton active">Control Panel</a></li>
        <li class="ilBar"><a href="/user/userInfoRedirect" class="ilButton">User Info</a></li>
        <% if(session.getAttribute("rights").equals("S") || session.getAttribute("rights").equals("A")){
            if(session.getAttribute("rights").equals("A")){%>
                <li class="ilBar"><a href="/admin/users" class="ilButton">Admin</a></li>
            <%}else{%>
                <li class="ilBar"><input name="action" type="button" value="Admin" class="ilButton" />
            <%}%>
                <%if(session.getAttribute("rights").equals("S")){ %>
                    <ul class="submenu">
                        <li class="ilBar"><a href="/admin/users" class="ilButton adminButton">Users</a></li>
                        <li class="ilBar"><a href="/admin/actions" class="ilButton adminButton">Imitator</a></li>
                    </ul>
                <%}%>
            </li>
        <%}%>
        <% if(session.getAttribute("rights").equals("U")){ %>
            <li class="ilBar"><a href="/question/" class="ilButton">Messages</a></li>
        <%} else {%>
            <li class="ilBar"><a href="/question/" class="ilButton">Messages</a></li>
        <%}%>
        <li class="ilBar"><a href="/user/logInRedirect" class="ilButton">Exit</a></li>
    </ul>
    <hr />
    <%if(session.getAttribute("rights").equals("B")){%>
        <p class="warningMessage">You have been banned from the manipulation of imitator!
        If you have any further questions, please, refer to administrator.</p>
    <% }else if(session.getAttribute("rights").equals("0")){%>
        <p class="warningMessage">You have not got your permission to use imitator yet!
        If you have any further questions, please, refer to administrator.</p>
    <%}else{%>
    <button onclick="checkConnection()" class="ilButton buttonLaunch" style="background-color: lightpink" >Check Connection</button>
    <br>
    <div style="visibility: hidden" id="response" class="responseConnection"></div>
    <br>
    <table width="59%" border="1" class="tableActions" id="tableActions">
        <tr style="text-align: center; background-color: azure">
            <td>
                Actions
            </td>
            <td>
                Available
            </td>
            <td>
                Data
            </td>
        </tr>
        <center>
            <%
                Gson gson = new Gson();
                ArrayList<Action> result = gson.fromJson((String) session.getAttribute("actions"), new TypeToken<List<Action>>() {}.getType());;
                for(int i = 0; i < result.size(); i++) {
            %>

            <tr>
                <td style="text-align: center">
                    <%= result.get(i).getActionLeap()%>
                </td>
                <td id="<%= i%>" style="text-align: center;
                    <%=(result.get(i).getAvailability() == 0 ? "background-color:hotpink;" : "background-color:#2bff36;")%>"
                    onclick="changeAvailability(<%= result.get(i).getAvailability()%>, <%= i%>)">
                    <input type="hidden" id="flag<%=i%>" value="<%= result.get(i).getAvailability()%>" />
                </td>
                <td style="text-align: center; width: 150px" id="<%= result.get(i).getActionLeap()%>"></td>
            </tr>
            <%
                }%>
        </center>
    </table>
    <br /><br />
    <table style="alignment: center">
        <tr>
            <td>
                <button id="pause" onclick="togglePause()" class="ilButton buttonLaunch" style="background-color: lightpink">Start</button>
            </td>
            <td style="width: 10px"></td>
            <td>
                <div class="popup" onclick="popUpShow()">
                    <input class="helpImage" type="image" src="/resources/images/help.png">
                    <span class="popuptext" id="myPopup">If you do not see any data, your Leap Motion is not working!</span>
                </div>
            </td>
        </tr>
    <%}%>
</center>
</body>
</html>