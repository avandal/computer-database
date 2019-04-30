<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="com.excilys.computer_database.binding.dto.ComputerDTO"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href=<c:url value="/resources/css/bootstrap.min.css" />
	rel="stylesheet" media="screen">
<link href=<c:url value="/resources/css/font-awesome.css" />
	rel="stylesheet" media="screen">
<link href=<c:url value="/resources/css/main.css" /> rel="stylesheet"
	media="screen">
</head>
<body>
	<%@include file="/resources/views/imports/header.jsp" %>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1><spring:message code="login.title" /></h1>
					<form action="login" method="POST">
						<fieldset>
							<div class="form-group">
								<spring:message code="login.username" var="login_username" />
								<label for="loginUsername">${login_username}</label>
								<input
									type="text" name="loginUsername" class="form-control"
									id="loginUsername" placeholder="${login_username}"
									value="${loginUsername}" />
								<c:choose>
									<c:when test = "${not empty errorUsername}">
								<div id="errorUsername" class="alert alert-danger">
									${errorUsername}
								</div>
									</c:when>
									<c:otherwise>
								<div id="errorUsername" class="alert alert-danger" style="display: none">
								</div>
									</c:otherwise>
								</c:choose>
							</div>
							<div class="form-group">
								<spring:message code="login.password" var="login_password" />
								<label for="loginUsername">${login_password}</label>
								<input
									type="text" name="loginPassword" class="form-control"
									id="loginPassword" placeholder="${login_password}"
								/>
								<c:choose>
									<c:when test = "${not empty errorPassword}">
								<div id="errorUsername" class="alert alert-danger">
									${errorPassword}
								</div>
									</c:when>
									<c:otherwise>
								<div id="errorPassword" class="alert alert-danger" style="display: none">
								</div>
									</c:otherwise>
								</c:choose>
							</div>
							<div class="actions pull-right">
								<input type="submit" value="<spring:message code="login.connect" />" class="btn btn-primary" id="connect" />
							</div>
						</fieldset>
					</form>
				</div>
			</div>
		</div>
	</section>
	
	<script src="resources/js/jquery.min.js"></script>
	<script src="resources/js/bootstrap.min.js"></script>
	<script src="resources/js/lang.js"></script>
</body>