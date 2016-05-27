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
		<c:if test="${!sessionScope.currentUser.isAdmin()}">
			<h1> My Records </h1>
			<table>
				<thead>
					<tr>					
						<th>Subject</th>
						<th>Duration</th>
						<th>My Result</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${records}" var="record">
						<tr>
							<td><c:out value="${record.getSubject()}" /></td>
							<td><c:out value="${record.printDuration()}" /></td>				
							<td><c:out value="${record.scoreToStringOverTwenty()}" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<br/>
			<h3> Best Records </h3>
			<br/>
			<table>
				<thead>
					<tr>					
						<th>Subject</th>
						<th>Duration</th>
						<th>Best Results</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${bestRecords}" var="bestRecord">
						<tr>
							<td><c:out value="${bestRecord.getSubject()}" /></td>
							<td><c:out value="${bestRecord.printDuration()}" /></td>				
							<td><c:out value="${bestRecord.scoreToStringOverTwenty()}" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
		<a href="user">Back</a>		
	</body>
</html>