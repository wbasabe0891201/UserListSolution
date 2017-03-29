$(document).ready(function () {
    $("#btnOk").click(function () {
        var username = $("#username").val();

        if (username.length < 6) {
            mostrarMensaje("User name leght is at least 6 characters :(", true);
        } else {
            $.ajax({
                url: "UserServlet",
                success: function (data) {
                    if (data.success) {
                        mostrarMensaje("User name registered successfully! :)", false);
                    }
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    mostrarMensaje("Something went wornt, sorry for that! :(", true);
                },
                dataType: "json"
            });
        }

    });
});


function mostrarMensaje(mensaje, error) {
    var snackbarContainer = document.querySelector('.bc-message');
    var data = {message: mensaje, timeout: 4000};
    snackbarContainer.MaterialSnackbar.showSnackbar(data);
    if (error) {
        $(".bc-message").addClass("error");
    } else {
        $(".bc-message").removeClass("error");
    }
}
