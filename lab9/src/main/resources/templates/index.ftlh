<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Welcome!</title>
    <meta charset="utf-8"/>
</head>
<body>
<h1>Welcome</h1>

<div>

    <table>
        <#list lstDriver as lst>
            <tr>
                <td>${lst.id}</td>
                <td>${lst.name}</td>
                <td>${lst.phone}</td>
                <td>${(lst.carId)!"not provided"}</td>
            </tr>
        </#list>
    </table>
</div>
<p>
</body>
</html>