<!DOCTYPE html>
<html lang="en">
<head>
    <title>Test Server</title>
    <link rel="stylesheet" href="/lab4/css/loader.css">
    <link rel="stylesheet" href="/lab4/css/styles.css"/>
    <script type="text/javascript" src="/lab4/js/loader.js" defer></script>
</head>

<body onload="pageInit()">
<h1>Welcome to Evan's Test Server</h1>
<div id="loader"></div>
<div id="container">
    <div style="display: block"><p>${message}</p></div>
    <div style="margin-bottom: 25px; margin-top: 25px">
        <button type="button" class="button"
                onclick="window.location.href = 'login'">
            LOGIN
        </button>

    </div>
    <div>

        <button type="button"
                class="button"
                id="gotoAppButton"
                onclick="window.location.href='app'">
            Go To App
        </button>
    </div>

</div>
</body>
</html>