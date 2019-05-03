<%@ include file="/resources/views/imports/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<%@include file="/resources/views/imports/head.jsp" %>
<body>
	<%@include file="/resources/views/imports/header.jsp" %>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1><spring:message code="login.signup.title" /></h1>
					<form action="create" method="POST">
						<fieldset>
							<div class="form-group">
								<spring:message code="login.username" var="login_username" />
								<label for="username">${login_username}</label>
								<input
									type="text" name="username" class="form-control"
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
								<label for="password">${login_password}</label>
								<input
									type="password" name="password" class="form-control"
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
								<input type="submit" value="<spring:message code="login.signup_btn" />" class="btn btn-primary" id="signup" /><a class="btn btn-default" id="cancel" style="margin-left: 5px"
						href=<c:url value="/login" />><spring:message code="cancel" /></a>
							</div>
						</fieldset>
					</form>
				</div>
			</div>
		</div>
	</section>
	<%@include file="/resources/views/imports/js.jsp" %>
</body>
