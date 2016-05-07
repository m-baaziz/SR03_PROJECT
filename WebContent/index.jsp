<html>
	<head>
		<meta charset='UTF-8' />
		<title> SR03 Project Website </title>
	</head>
	<body>
		<h1> Welcome Boyaa ! </h1>
		<table>
			<thead>
				<tr>
					<th>Email</th>
					<th>Password</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${users}" var="user">
					<tr>
						<td><c:out value="${user.getEmail()}" /></td>
						<td><c:out value="${user.getPassword()}" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</body>
</html>