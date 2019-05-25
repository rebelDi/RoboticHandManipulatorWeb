function changeAvailability(result, i){
    if(result === 1) {
        if (document.getElementById("flag"+i).value === "0"){
            document.getElementById(i).style.backgroundColor = "#2bff36";
            document.getElementById("flag"+i).value = "1";
        }else{
            document.getElementById(i).style.backgroundColor = "#ff3a3a";
            document.getElementById("flag"+i).value = "0";
        }
    }
}

function popUpShow() {
    const popup = document.getElementById("myPopup");
    popup.classList.toggle("show");
}

function showQuestion(questionNum){
    document.getElementById("question").value = document.getElementById("Q" + questionNum).value;
    document.getElementById("answer").value = document.getElementById("A" + questionNum).value;
    document.getElementById("userLogin").value = document.getElementById("U" + questionNum).value;
    document.getElementById("userQ").value = document.getElementById("Q" + questionNum).value;
}

function showQuestionForUser(questionNum){
    document.getElementById("question").value = document.getElementById("Q" + questionNum).value;
    document.getElementById("answer").value = document.getElementById("A" + questionNum).value;
    document.getElementById("userLogin").value = document.getElementById("U" + questionNum).value;
}