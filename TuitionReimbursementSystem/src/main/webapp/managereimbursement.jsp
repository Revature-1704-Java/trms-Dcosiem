<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Manage Reimbursements</title>
</head>
<body>
	<table border="1" width="303" id="myTable">
		<tr>
			<th>Employee ID</th>
			<th>Supervisor ID</th>
			<th>Department Head ID</th>
			<th>Reimbursement ID</th>
			<th>Employee Type</th>
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
			<th>Action</th>
		</tr>
		<tr>
			<c:forEach var="info" items="${requestScope.content}">
				<c:forEach var="innerInfo" items="${info}">
					<td>${innerInfo}</td>
				</c:forEach>
			</c:forEach>
		</tr>
	</table>
	<form method="GET" action="UserRedirectServlet">
		<input type="hidden" name="eid"
			value=<%=request.getAttribute("e_id")%>> <input type="submit"
			value="Return to main Account Page">
	</form>
</body>
</html>