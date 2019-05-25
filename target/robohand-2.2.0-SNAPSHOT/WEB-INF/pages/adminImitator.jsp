<%@ page import="roboticHand.Model.Action" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.gson.reflect.TypeToken" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Panel</title>
    <link href="/resources/css/style.css" type="text/css" rel="stylesheet">
    <script src="/resources/js/jQuery.js"></script>
    <script src="/resources/js/adminImitator.js"></script>
</head>
<body>
<center>

    <ul class="ulBar">
        <li class="ilBar"><a href="/action/panel" class="ilButton">Control Panel</a></li>
        <li class="ilBar"><a href="/user/userInfoRedirect" class="ilButton">User Info</a></li>
        <% if(session.getAttribute("rights").equals("S") || session.getAttribute("rights").equals("A")){
            if(session.getAttribute("rights").equals("A")){%>
        <li class="ilBar"><a class="ilButton active">Admin</a></li>
        <%}else{%>
        <li class="ilBar"><input name="action" type="button" value="Admin" class="ilButton active" />
            <%}%>
            <%if(session.getAttribute("rights").equals("S")){ %>
            <ul class="submenu">
                <li class="ilBar"><a href="/admin/users" class="ilButton adminButton">Users</a></li>
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

    <table width="59%" border="1" class="tableActions" id="tableActions">
        <tr style="text-align: center; background-color: azure">
            <td>
                Action Name
            </td>
            <td>
                MC Servo #
            </td>
            <td>
                Leap Minimum
            </td>
            <td>
                Leap Maximum
            </td>
            <td>
                Servo Direction
            </td>
            <td>
                Servo Minimum
            </td>
            <td>
                Servo Maximum
            </td>
            <td>
                Availability
            </td>
        </tr>
        <center>
            <%
                String actionsJson = (String) session.getAttribute("actions");
                Gson gson = new Gson();
                ArrayList<Action> result = gson.fromJson(actionsJson, new TypeToken<List<Action>>() {}.getType());
                for(int i = 0; i < result.size(); i++) {
            %>
            <tr>
                <td>
                    <p id="name<%=i%>"><%= result.get(i).getActionLeap()%></p>
                </td>
                <td>
                    <input id="ardInd<%=i%>" maxlength="2" value="<%= result.get(i).getHandAction()%>"  class="servoInput">
                </td>
                <td>
                    <input id="leapMin<%=i%>" maxlength="4" value="<%= result.get(i).getLeapMin()%>" class="coordsInput">
                </td>
                <td>
                    <input id="leapMax<%=i%>" maxlength="4" value="<%= result.get(i).getLeapMax()%>" class="coordsInput">
                </td>
                <td>
                    <p class="servoDirection" id="servoD<%=i%>" onclick="changeServoDirection(<%=i%>)">
                        <% if(result.get(i).getServoDirection() == 1){ %>
                            Clockwise
                        <% }else{ %>
                            Counter-clockwise
                        <% }%>
                    </p>
                </td>
                <td>
                    <input id="servoMin<%=i%>" maxlength="4" value="<%= result.get(i).getServoMin()%>" class="coordsInput">
                </td>
                <td>
                    <input id="servoMax<%=i%>" maxlength="4" value="<%= result.get(i).getServoMax()%>" class="coordsInput">
                </td>
                <td id="avail<%=i%>" style="text-align: center; <%=(result.get(i).getAvailability() == 0 ? "background-color: hotpink;" : "background-color:#2bff36;")%>" onclick="changeAvailability(<%=i%>)">
                </td>
      </tr>
      <%
          }
      %>
  </center>
        <% System.out.println("size" + result.size());%>
</table>
<input style="alignment: center" class="ilButton buttonLaunch" value="Change" type="button" onclick="sendChangedData(<%=result.size()%>)">
</center>
</body>
</html>