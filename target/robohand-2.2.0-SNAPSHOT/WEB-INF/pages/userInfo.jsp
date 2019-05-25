<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Change Your Info</title>
    <link href="/resources/css/style.css" type="text/css" rel="stylesheet">
    <script src="/resources/js/jQuery.js"></script>
    <script type="text/javascript" src="/resources/js/formsCheck.js" ></script>
</head>
<body>
<center>
    <ul class="ulBar">
        <li class="ilBar"><a href="/action/panel" class="ilButton">Control Panel</a></li>
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
    <form class="form">
        <br />
        <div type="hidden" id="errors" class="error"></div>

        <table align="center">
            <tr>
                <td>
                    <label>User Name </label>
                </td>
                <td>
                    <label>
                        <input id="login" type="text" style="width: 162px" value="${login}"/>
                    </label>
                </td>
                <td></td>
            </tr>

            <tr>
                <td>
                    Old Password&nbsp;&nbsp;&nbsp;
                </td>
                <td>
                    <input id="oldPassword" type="password" style="width: 162px" />
                </td>
                <td>
                </td>
            </tr>

            <tr>
                <td>
                    New Password&nbsp;&nbsp;&nbsp;
                </td>
                <td>
                    <input id="password" type="password" style="width: 162px" />
                </td>
                <td>
                    <span id='messagePass'></span>
                </td>
            </tr>

            <tr>
                <td>
                    Confirm Password
                </td>
                <td>
                    <input id="confirmPassword" type="password" onkeyup="checkPasswords()" style="width: 162px" />
                </td>
                <td>
                    <span id='messageConf'></span>
                </td>
            </tr>

            <tr>
                <td>
                    Name
                </td>
                <td>
                    <input id="name" type="text" style="width: 162px" value="${name}"/>
                </td>
                <td></td>
            </tr>

            <tr>
                <td>
                    Surname
                </td>
                <td>
                    <input id="surname" type="text" style="width: 162px" value="${surname}" />
                </td>
                <td></td>
            </tr>
            <tr>
                <td>
                    Secret Question&nbsp;&nbsp;&nbsp;
                </td>
                <td>
                    <input id="question" type="text" value="Mother's maiden name?" style="width: 162px"  value="${question}"/>
                </td>
                <td></td>
            </tr>

            <tr>
                <td>
                    Secret Answer&nbsp;&nbsp;&nbsp;
                </td>
                <td>
                    <input id="answer" type="text" style="width: 162px" />
                </td>
                <td></td>
            </tr>
        </table>
        <center>
            <input class="ilButton" type="button" value="Edit" onclick="editCheckForm()" />
        </center>
        <br />
        <br />
    </form>
</center>
</body>
</html>