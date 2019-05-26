function changeUserData(login, rights, i) {
    if(rights === 'B'){
        changeStyle("B"+i, "#ff3a3a");
        changeStyle("U"+i, "#333");
        changeStyle("A"+i, "#333");
    }else if(rights === 'U'){
        changeStyle("U"+i, "#2bff36");
        changeStyle("B"+i, "#333");
        changeStyle("A"+i, "#333");

    }else{
        changeStyle("A"+i, "#2bff36");
        changeStyle("U"+i, "#333");
        changeStyle("B"+i, "#333");
    }

    // var user = [login, rights];
    $.ajax({
        type: "POST",
        url: "/admin/userRightsEdit",
        data: {user : JSON.stringify({login : login, rights : rights})},
        cache: false
    });
}

function changeStyle(element, color) {
    if(document.getElementById(element) != null) {
        document.getElementById(element).style.backgroundColor = color;
    }
}