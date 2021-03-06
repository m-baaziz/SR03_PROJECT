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
		<h1> Edit test: <c:out value="${test.getSubject()}" />  </h1>
		<br/>
		<br/>
		<c:if test="${sessionScope.currentUser.isAdmin()}">			
			<form method="post" action="test">
				<input type="hidden" name="_method" value="put" />
				<input type="hidden" name="subject" value="${test.getSubject()}" />
				<div> Active :
					<select name="isActive">
						<option ${test.isActive() ? 'selected' : ''} value="true">True</option>
						<option ${test.isActive() ? '' : 'selected'}  value="false">False</option>
					</select>
				</div>
				<div><input type="submit" value="Send" /></div>
			</form>
		</c:if>		
		<br/>
		<a href="test?subject=${test.getSubject()}&action=show">Return</a>
	</body>
</html>