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

    <table width="50%" align="center">
        <thead>
        <tr>
            <th>ID</th>
            <th>NAME</th>
            <th>PHONE</th>
            <th>CAR_ID</th>
        </tr>
        </thead>
        <#list lstDriver as lst>
            <tr align="center">
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