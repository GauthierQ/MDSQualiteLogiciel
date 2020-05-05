<#import "/spring.ftl" as spring/>

<head>
    <#include "../includable/bootstrap.ftl">
</head>
<body>
    <H1>User Detail</H1>
    <a href="../index">User List</a>
    <br>
    <p>User Id: ${user.id}</p>
    <p>User Firstname: ${user.firstname}</p>
    <p>User Lastname: ${user.lastname}</p>

    <p>Product List: </p>
    <table class="table table-bordered table-hover">
        <tr>
        <th>Name</th>
        <th>Price</th>
        </tr>

        <#list items as item>
            <tr>
                <td>${item.name}</td>
                <td>${item.price}</td>
            </tr>
        </#list>
    </table>
</body>
