<#import "/spring.ftl" as spring/>

<head>
    <#include "../includable/bootstrap.ftl">
</head>
<body>
    <H1>Product Detail</H1>
    <a href="../index">Product List</a>
    <br>
    <p>Product Id: ${product.id}</p>
    <p>Product Name: ${product.name}</p>
    <p>Product Price: ${product.price}</p>
</body>
