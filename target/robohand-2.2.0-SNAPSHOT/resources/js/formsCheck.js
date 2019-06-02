var flag = true;

function checkPasswords() {
    flag = true;

    var password = document.getElementById('password').value;
    var repPassword = document.getElementById('confirmPassword').value;
    if (password.length === 0) {
        document.getElementById("messagePass").innerHTML = "";
    }

    // Create an array and push all possible values that you want in password
    var matchedCase = [];
    matchedCase.push("[A-Z]");      // Uppercase Alphabet
    matchedCase.push("[0-9]");      // Numbers
    matchedCase.push("[a-z]");     // Lowercase Alphabet

    // Check the conditions
    var ctr = 0;
    for (var i = 0; i < matchedCase.length; i++) {
        if (new RegExp(matchedCase[i]).test(password)) {
            ctr++;
        }
    }
    // Display it
    var color = "";
    var strength = "";
    switch (ctr) {
        case 0:
        case 1:
        case 2:
            strength = "Very Weak";
            color = "red";
            break;
        case 3:
            strength = "Medium";
            color = "orange";
            break;
        case 4:
            strength = "Strong";
            color = "green";
            break;
    }
    document.getElementById("messagePass").innerHTML = strength;
    document.getElementById("messagePass").style.color = color;

    flag = document.getElementById("messagePass").style.color !== "red";

    if(password.length >= 6 && document.getElementById('confirmPassword').value.length  >= 0) {
        if (password === repPassword) {
            document.getElementById('messageConf').style.color = 'green';
            document.getElementById('messageConf').innerHTML = 'Passwords are matching';
        } else {
            document.getElementById('messageConf').style.color = 'red';
            document.getElementById('messageConf').innerHTML = 'Passwords are not matching';
            flag = false;
        }
    }else{
        flag = false;
    }
};

function mainCheckSignUpForm() {
    checkPasswords();
    if(!flag || document.getElementById('login').value.length === 0
        || document.getElementById('name').value.length === 0
        || document.getElementById('surname').value.length === 0
        || document.getElementById('question').value.length === 0
        || document.getElementById('answer').value.length === 0){
        errorsDisplay("text", "Check all fields, something is unacceptable!");
    }else{
        errorsDisplay("hidden", "");
        var user = [document.getElementById('login').value,
                    document.getElementById('password').value,
                    document.getElementById('name').value,
                    document.getElementById('surname').value,
                    document.getElementById('question').value,
                    document.getElementById('answer').value];

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/user/signUp",
            data: JSON.stringify(user),
            cache: false,
            success: function (data) {
                alert("You are all set!");
                window.location = '/user/logInRedirect'
            },
            error: function(e) {
                errorsDisplay("text", "This login has been registered earlier or something went wrong!");
            }
        });

    }
}

function errorsDisplay(type, text) {
    document.getElementById('errors').type = type;
    document.getElementById('errors').innerText = text;
}

function mainCheckLoginForm() {
    var login = document.getElementById('login').value;
    var password = document.getElementById('password').value;
    var user = [login, password];

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/user/login",
        data: JSON.stringify(user),
        cache: false,
        success: function (data) {
            window.location = '/action/panel';
        },
        error: function(e) {
            errorsDisplay("text", "You provided wrong login or password!");
        }
    });
}

function checkNull(value) {
    return value === null;
}

function checkNullWithValue(value) {
    if(checkNull(document.getElementById(value))){
        // alert(value);
        return "";
    }else{
        return document.getElementById(value).value;
    }
}

function editCheckForm() {
    flag = true;

    if(!checkNull(document.getElementById('password')) &&
        !checkNull(document.getElementById('confirmPassword'))){
        var newPassword = document.getElementById('password').value;
        var confirmedPassword = document.getElementById('confirmPassword').value;
        if(newPassword.length !== 0 && confirmedPassword.length !== 0) {
            checkPasswords();
        }
    }

    if(flag) {
        var oldPassword;
        var answer;

        if(document.getElementById('oldPassword') === 0 && document.getElementById('answer').length === 0){
            errorsDisplay("text", "To change something you have to input old password or answer on question");
        }else{
            oldPassword = checkNullWithValue("oldPassword");
            answer = checkNullWithValue("answer");
            var login = checkNullWithValue("login");
            var question = checkNullWithValue("question");
            var surname = checkNullWithValue("surname");
            var name = checkNullWithValue("name");


            var newData = [login, oldPassword, newPassword, name, surname, question, answer];

            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/user/edit",
                data: JSON.stringify(newData),
                cache: false,
                success: function (data) {
                    if(data.toString().length !== 0) {
                        alert(data.toString());
                    }
                }
            });
        }
    }
}