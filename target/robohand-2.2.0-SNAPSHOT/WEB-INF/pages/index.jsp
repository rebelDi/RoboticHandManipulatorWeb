<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Authorization</title>
    <link rel="stylesheet" href="/resources/css/style.css"/>
    <script src="/resources/js/jQuery.js" ></script>
    <script src="/resources/js/formsCheck.js" ></script>
</head>
<body>
<center>
    <br />
    <form action="" class="headerForm">
        <div class="welcomeHeader">
            <strong>Welcome to Robotic Hand Control Panel!</strong></div>
    </form>

    <br /><br /><br />

    <form class="form">
        <br />
        <div type="hidden" id="errors" class="error"></div>
        <table align="center">
            <tr>
                <td>
                    Login
                </td>
                <td>
                    <input id="login" type="text" style="width: 162px" />
                </td>
            </tr>
            <tr>
                <td>
                    Password
                </td>
                <td>
                    <input id="password" type="password" style="width: 162px" />
                </td>
            </tr>
        </table>
        <center>
            <input name="submitAuthorization" class="ilButton" type="button" value="Log In" onclick="mainCheckLoginForm()" />
        </center>
        <br />
        <a class="link" href="/user/signUpRedirect"> Sign Up </a>
        <br />
        <br />
    </form>
    <br />
</center>
</body>
</html>