<%@ page import="roboticHand.Model.Question" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.google.gson.reflect.TypeToken" %>
<%@ page import="com.google.gson.Gson" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User Messages</title>
    <link rel="stylesheet" href="/resources/css/style.css"/>
    <script src="/resources/js/main.js"></script>
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
        <li class="ilBar"><a class="ilButton active">Messages</a></li>
        <li class="ilBar"><a href="/user/logInRedirect" class="ilButton">Exit</a></li>
    </ul>
    <hr />

    <%
        Gson gson = new Gson();
        ArrayList<Question> messages = gson.fromJson((String) session.getAttribute("questions"), new TypeToken<List<Question>>() {}.getType());;
        boolean show;
    %>

    <table>
        <tr>
            <td>
                <center>
                    <table width="59%" border="1" class="tableActions" id="tableActions">
                        <tr style="text-align: center; background-color: azure">
                            <td>
                                Questions
                            </td>
                            <td>
                                Status
                            </td>
                        </tr>

                        <%if(messages.size() == 0){
                            show = false;
                        %>
                            <tr>
                                <td colspan="3">
                                    You have not asked anything yet!
                                </td>
                            </tr>
                        <%}else{
                            show = true;
                        %>
                            <%for(int i = 0; i < messages.size(); i++){%>
                                <tr style="text-align: center; background-color: white" onclick="showQuestionForUser(<%=i%>)">
                                    <td>
                                        <input id="Q<%=i%>" value="<%=messages.get(i).getQuestion()%>"readonly>
                                        <input type="hidden" id="A<%=i%>" value="<%=messages.get(i).getAnswer()%>">
                                    </td>
                                    <td>
                                        <input value="<%=messages.get(i).getStatus()%>" readonly>
                                    </td>
                                </tr>
                            <%}
                        }%>
                    </table>
                </center>
            </td>
            <td>
        <%if(show){%>
            <tr>
                <table width="59%" border="1" class="tableActions">
                    <tr style="text-align: center; background-color: azure">
                        Question
                    </tr>
                    <br />
                    <tr style="text-align: center; background-color: white">
                        <textarea id="question" cols="40" rows="20" value="" readonly></textarea>
                    </tr>
                </table>
            </tr>
            <tr>
                <table width="59%" border="1" class="tableActions">
                    <tr style="text-align: center; background-color: azure">
                        Answer
                    </tr>
                    <br />
                    <tr style="text-align: center; background-color: white">
                        <textarea id="answer" name="answer" cols="40" rows="20" value="" readonly></textarea>
                        <br/>
                    </tr>
                </table>
            </tr>
        <%}%>
        <br/><br/>
        <tr>
            <table width="59%" border="1" class="tableActions">
                <tr style="text-align: center; background-color: azure">
                    Ask a question!
                </tr>
                <br />
                <tr style="text-align: center; background-color: white">
                    <form method="post" action="/question/ask">
                        <textarea id="newQuestion" name="newQuestion" cols="40" rows="20" value=""></textarea>
                        <br/>
                        <input name="action" type="submit" value="Ask" class="ilButton">
                    </form>
                </tr>
            </table>
        </tr>
        </td>
        </tr>
    </table>
</center>
</body>
</html>