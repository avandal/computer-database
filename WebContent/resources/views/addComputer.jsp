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
			<a class="navbar-brand" href="dashboard"> Application -
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
								<label for="computerName">Computer name</label> 
								<input
									type="text" name="computerName" class="form-control"
									id="computerName" placeholder="Computer name"
									value="${computerName}" required>
								<c:choose>
									<c:when test = "${not empty errorName}">
								<div id="errorName" class="alert alert-danger">
									${errorName}
								</div>
									</c:when>
									<c:otherwise>
								<div id="errorName" class="alert alert-danger" style="display: none">
								</div>
									</c:otherwise>
								</c:choose>
							</div>
							<div class="form-group">
								<label for="introduced">Introduced date <br />
									<small class="text-muted">(Please follow these formats: 
										<i>dd/mm/yyyy</i> or <i>yyyy-mm-dd</i>)
									</small>
								</label>
								<input type="text" name="introduced" class="form-control"
									id="introduced" placeholder="Introduced date"
									value="${introduced}">
								<c:choose>
									<c:when test = "${not empty errorIntroduced}">
								<div id="errorIntroduced" class="alert alert-danger">
									${errorIntroduced}
								</div>
									</c:when>
									<c:otherwise>
								<div id="errorIntroduced" class="alert alert-danger" style="display: none">
								</div>
									</c:otherwise>
								</c:choose>
							</div>
							<div class="form-group">
								<label for="discontinued">Discontinued date <br />
									<small class="text-muted">(Please follow these formats: 
										<i>dd/mm/yyyy</i> or <i>yyyy-mm-dd</i>)
									</small>
								</label>
								<input type="text" name="discontinued" class="form-control"
									id="discontinued" placeholder="Discontinued date"
									value="${discontinued}">
								<c:choose>
									<c:when test = "${not empty errorDiscontinued}">
								<div id="errorDiscontinued" class="alert alert-danger">
									${errorDiscontinued}
								</div>
									</c:when>
									<c:otherwise>
								<div id="errorDiscontinued" class="alert alert-danger" style="display: none">
								</div>
									</c:otherwise>
								</c:choose>
							</div>
							<div class="form-group">
									${companyId}
								<label for="companyId">Company</label>
								<select
									class="form-control" name="companyId" id="companyId">
									<c:forEach items="${companyList}" var="company">
										<c:choose>
										<c:when test="${companyId == null || companyId eq '0'}">
											<option value="${company.getId()}" 
												<c:if test="${company.getId() eq '0'}">selected="selected"</c:if>
											>${company.getName()}</option>
										</c:when>
										<c:otherwise>
											<option value="${company.getId()}" 
												<c:if test="${company.getId() eq companyId}">selected="selected"</c:if>
											>${company.getName()}</option>
										</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
								<c:choose>
									<c:when test = "${not empty errorCompany}">
								<div id="errorCompany" class="alert alert-danger">
									${errorCompany}
								</div>
									</c:when>
									<c:otherwise>
								<div id="errorCompany" class="alert alert-danger" style="display: none">
								</div>
									</c:otherwise>
								</c:choose>
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