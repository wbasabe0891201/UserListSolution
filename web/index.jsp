<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>

        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
        <link rel="stylesheet" href="https://code.getmdl.io/1.3.0/material.indigo-pink.min.css">
        <link href="assets/styles.css" rel="stylesheet" type="text/css"/>

        <script src="http://code.jquery.com/jquery-2.2.3.min.js"></script>

        <script defer src="https://code.getmdl.io/1.3.0/material.min.js"></script>
        <script src="assets/scripts.js" type="text/javascript"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome to Spring Web MVC project</title>
    </head>

    <body style="background-color: #dfdfdf">
        <div class="mdl-color--white mdl-shadow--2dp" style="position: relative; top: 200px; max-width: 350px; margin: 0 auto; padding: 30px; text-align: center">
            <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                <input class="mdl-textfield__input" type="text" id="username">
                <label class="mdl-textfield__label" for="username">Please wirte your user name</label>
            </div>
            <div style="text-align: center;">
                <button id="btnOk" class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored mdl-js-ripple-effect">
                    Ok
                </button>
            </div>
        </div>

        <div id="demo-toast-example" class="mdl-js-snackbar mdl-snackbar bc-message">
            <div class="mdl-snackbar__text"></div>
            <button class="mdl-snackbar__action" type="button"></button>
        </div>
    </body>
</html>
