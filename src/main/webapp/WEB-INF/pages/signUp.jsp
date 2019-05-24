<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>SignUp</title>
    <link rel="stylesheet" href="/resources/css/style.css"/>
    <script src="/resources/js/jQuery.js" ></script>
    <script src="/resources/js/formsCheck.js" ></script>
</head>
<body>
<center>
    <br />

    <form action="" class="headerForm">
        <div class="welcomeHeader">
            <strong>Sign Up, please!</strong></div>
    </form>

    <br /><br />


    <form class="form">
        <br />
        <div type="hidden" id="errors" class="error"></div>

        <table align="center">
            <tr>
                <td>
                    <label id="Label1">User Name </label>
                </td>
                <td>
                    <label>
                        <input id="login" type="text" style="width: 162px" />
                    </label>
                </td>
                <td></td>
            </tr>

            <tr>
                <td>
                    Password&nbsp;&nbsp;&nbsp;
                </td>
                <td>
                    <input id="password" type="password" onkeyup="checkPasswords()" style="width: 162px" />
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
                    <input id="name" type="text" style="width: 162px" />
                </td>
                <td></td>
            </tr>

            <tr>
                <td>
                    Surname
                </td>
                <td>
                    <input id="surname" type="text" style="width: 162px" />
                </td>
                <td></td>
            </tr>
            <tr>
                <td>
                    Secret Question&nbsp;&nbsp;&nbsp;
                </td>
                <td>
                    <input id="question" type="text" value="Mother's maiden name?" style="width: 162px" />
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
            <input name="submitAuthorization" class="ilButton" type="button" value="Sign Up" onclick="mainCheckSignUpForm()" />
        </center>
        <br />
        <a class="link" href="/user/logInRedirect"> Log In </a>
        <br />
        <br />
    </form>
    <br />
</center>
</body>
</html>



