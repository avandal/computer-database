<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page import="com.excilys.computer_database.dto.ComputerDTO"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href=<c:url value = "/resources/css/bootstrap.min.css" />
	rel="stylesheet" media="screen">
<link href=<c:url value = "/resources/css/font-awesome.css" />
	rel="stylesheet" media="screen">
<link href=<c:url value = "/resources/css/main.css" /> rel="stylesheet"
	media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard.html"> Application -
				Computer Database </a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>Add Computer</h1>
					<form action="addComputer" method="POST">
						<fieldset>
							<div class="form-group">
								<label for="computerName">Computer name</label> <input
									type="text" name="computerName" class="form-control"
									id="computerName" placeholder="Computer name" required>
								<div id="errorName" class="alert alert-danger"></div>
							</div>
							<div class="form-group">
								<label for="introduced">Introduced date <br /> <small
									class="text-muted">(Please follow these formats: 
									<i>dd/mm/yyyy</i> or <i>yyyy-mm-dd</i>)
								</small></label> <input type="text" name="introduced" class="form-control"
									id="introduced" placeholder="Introduced date">
								<div id="errorIntroduced" class="alert alert-danger"
									style="display: none"></div>
							</div>
							<div class="form-group">
								<label for="discontinued">Discontinued date <br /> <small
									class="text-muted">(Please follow these formats: 
									<i>dd/mm/yyyy</i> or <i>yyyy-mm-dd</i>)
								</small></label> <input type="text" name="discontinued" class="form-control"
									id="discontinued" placeholder="Discontinued date">
								<div id="errorDiscontinued" class="alert alert-danger"
									style="display: none"></div>
							</div>
							<div class="form-group">
								<label for="companyId">Company</label> <select
									class="form-control" name="companyId" id="companyId">
									<c:forEach items="${companyList}" var="company">
										<option value="${company.getId()}">${company.getName()}</option>
									</c:forEach>
								</select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Add" class="btn btn-primary" id="add">
							or <a href=<c:url value = "dashboard" /> class="btn btn-default">Cancel</a>
							<c:choose>
								<c:when test="${status.equals('void')}"></c:when>
								<c:when test="${status.equals('success')}">Computer successfully created!</c:when>
								<c:when test="${status.equals('failed')}">Failed creating the computer!</c:when>
								<c:otherwise></c:otherwise>
							</c:choose>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
	<script src="resources/js/jquery.min.js"></script>
	<script src="resources/js/bootstrap.min.js"></script>
	<script src="resources/js/validator.js"></script>
</body>
</html>