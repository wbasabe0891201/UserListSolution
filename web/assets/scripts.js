$(document).ready(function () {
    $("#btnOk").click(function () {
        var username = $("#username").val();

        if (username.length < 6) {
            showMessageBox("User name leght is at least 6 characters :(", true);
        } else {
            $.ajax({
                url: "UserServlet",
                data: {'username': username},
                success: function (data) {
                    if (data.success) {
                        showMessageBox("User name registered successfully! :)", false);
                    } else {
                        console.log(data);
                        showMessageBox("Sorry but the selected user name is invalid, Select a sugested name! :|", true);
                    }
                    cleanForm();
                    addSuggestedName(data.names);
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    showMessageBox("Something went wornt, sorry for that! :(", true);
                },
                dataType: "json"
            });
        }

    });
    
    $("#btnOkRestricted").click(function() {
        var restriction = $("#restricted").val();

        if (restriction.length < 6) {
            showMessageBox("New restriction leght is at least 6 characters :(", true);
        } else {
            $.ajax({
                url: "RestrictionServlet",
                data: {'restricted': restriction},
                success: function (data) {
                    if (data.success) {
                        showMessageBox("New Restriction registered successfully! :)", false);
                        cleanRestrictedForm();
                    } else {
                        console.log(data);
                        showMessageBox(data.message, true);
                    }
                    cleanForm();
                    addSuggestedName(data.names);
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    console.log(jqXHR);
                    console.log(errorThrown);
                    showMessageBox("Something went wornt, sorry for that! :(", true);
                },
                dataType: "json"
            });
        }
    });

    $("#btnClear").click(function () {
        cleanForm();
    });

    $('body').on('click', 'span.mdl-chip', function () {
        var newName = $(this).find("span").text();
        $("#username").val(newName);
    });

    $("#add_restricted_toggle").click(function () {
        var layout = document.querySelector('.mdl-layout');
        layout.MaterialLayout.toggleDrawer();
    });
});




function showMessageBox(mensaje, error) {
    var snackbarContainer = document.querySelector('.bc-message');
    var data = {message: mensaje, timeout: 4000};
    snackbarContainer.MaterialSnackbar.showSnackbar(data);
    if (error) {
        $(".bc-message").addClass("error");
    } else {
        $(".bc-message").removeClass("error");
    }
}

function cleanForm() {
    $("#username").val("");
    $("#nameList").empty();
}

function cleanRestrictedForm() {
    $("#restricted").val("");
}

function addSuggestedName(nameList) {
    nameList.forEach(function (item) {
        $("#nameList").append("<span class='mdl-chip'><span class='mdl-chip__text'>" + item + "</span></span>");
    });
}

