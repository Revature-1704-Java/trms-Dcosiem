<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Reimbursement Query</title>
</head>
<body>
	<table border="1" width="303">
				<tr>
					<th>Reimbursement ID</th>
					<th>Event Type</th>
					<th>Description</th>
					<th>Location</th>
					<th>Justification</th>
					<th>Date of Event</th>
					<th>Grade</th>
					<th>Cost</th>
					<th>Time Submitted</th>
					<th>Time Missed</th>
					<th>Status</th>
					<th>Denied Reason</th>
				</tr>
		
			<c:forEach var="info" items="${requestScope.content}">
				<c:forEach var="innerInfo" items="${info}">		
					<td>${innerInfo}</td>
				</c:forEach>				
			</c:forEach>		
	</table>

	<button onclick="goBack()">Go Back</button>

	<script>
		function goBack() {
			window.history.back();
		}
	</script>
</body>
</html>