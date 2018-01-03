<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Info</title>
</head>
<body>
	<table border="1" width="303">
	<tr>
		<th>ID</th>
		<th>First Name</th>
		<th>Last Name</th>
		<th>Email</th>
		<th>Supervisor</th>
		<th>Department Head</th>
		<th>Title</th>
		<th>Allotment</th>
		<th>Pending</th>
		<th>Awarded</th>		
	</tr>	
	<tr>
		<c:forEach var="info" items="${requestScope.userInfo}">		
		<td>${info}</td>
		</c:forEach>
	</tr>	
	</table>
	<form action="ReimbursementFormServlet" method="GET" name="reimburseForm">
		<input type="submit" value="Create New Reimbursement" />
	</form>
	
</body>
</html>