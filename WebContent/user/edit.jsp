<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>  
  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">  

<html>
	<head>
		<meta charset='UTF-8' />
		<title> SR03 Project Website </title>
	</head>
	<body>
		<h1> <c:out value="${user.getName()}" /> profile edition </h1>
		<br/>
		<br/>
		<form action="user" method="post">
			<input type="hidden" name="_method" value="put" />
			<table>
				<tbody>
					<tr><td>Name : </td><td><input name="name" type="text" value="${user.getName()}" /></td></tr>
					<tr><td>Type : </td>
					<td>
						<select name="type" value="${user.getType()}">
							<option ${user.getType() == 'administrator' ? 'selected' : ''} value="administrator">Administrator</option>
							<option ${user.getType() == 'intern' ? 'selected' : ''} value="intern">Intern</option>
						</select>
					</td></tr>                     
					<tr><td>Company : </td><td><input name="company" type="text" value="${user.getCompany()}" /></td></tr>               
					<tr><td>Phone : </td><td><input name="phone" type="text" value="${user.getPhone()}" /></td></tr>                   
					<tr><td>Active : </td>
					<td>
						<select name="isActive">
							<option ${user.isActive() ? 'selected' : ''} value="true">True</option>
							<option ${user.isActive() ? '' : 'selected'}  value="false">False</option>
						</select>
					</td></tr>
				</tbody>
			</table>
			<input type="submit" value="Edit" />
		</form>
		<br/>
		<a href="user?action=show">Return</a>
	</body>
</html>