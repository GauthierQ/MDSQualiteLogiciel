
<#import "/spring.ftl" as spring />

<head>
<#include"../includable/bootstrap.ftl">
</head>

<body>
	<#ifpage??>
	<h1>${page}</h1>
	</#if> <a href="create">Create new</a>
	<table class="table table-bordered table-hover">
		<tr>
			<th>Name</th>
			<th>Price</th>
		</tr>
		<#list users as user>
		<tr>
			<td>${user.firstname}</td>
			<td><a href="show/${user["id"]}">Show</a></td>
			<td>
				<form action="delete" method="POST">
					<input type="hidden" name="id" value="${user["id"]}"> <input
						type="submit" value="delete" />
				</form>
			</td>
		</tr>
		</#list>
	</table>
</body>
</html>