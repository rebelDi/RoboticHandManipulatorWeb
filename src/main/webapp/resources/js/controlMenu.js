function checkConnection() {
    var ipAddress;
    if(document.getElementById("ip").value !== undefined && document.getElementById("ip").value !== ""){
        ipAddress = document.getElementById("ip").value;
    }else {
        ipAddress = "no";
    }

    $.ajax({
        type: "POST",
        url: "/action/checkConnection",
        data: ipAddress,
        success: function (data) {
            $("#response").text("Connected");
            $("#response").css("visibility", "visible");
            $("#response").css("background-color", "mediumspringgreen");
        },
        error: function (e) {
            $("#response").text("Not connected");
            $("#response").css("visibility", "visible");
            $("#response").css("background-color", "#fc5355");
        }
    })
}