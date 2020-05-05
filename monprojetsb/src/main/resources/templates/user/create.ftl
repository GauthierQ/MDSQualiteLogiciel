<#import "/spring.ftl" as spring/>
<head>
    <#include "../includable/bootstrap.ftl">
</head>
<body>
    <H1>User Create</H1>
    <a href="index">User List</a>
    <br>

    <form action="<@spring.url '/user/create'/>" method="POST">
        Firstname:<br>
        <input type="text" name="firstname" value="">
        <br>
        Lastname:<br>
        <input type="text" name="lastname" value="">
        <br>
        <input type="submit" value="Submit">
    </form>
</body>
