function checkConnection() {
    $.ajax({
        type: "POST",
        url: "/action/checkConnection",
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