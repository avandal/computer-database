<%@ include file="/resources/views/imports/taglibs.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="com.excilys.computer_database.binding.dto.ComputerDTO"%>
<!DOCTYPE html>
<html>
<%@include file="/resources/views/imports/head.jsp" %>
<body>
	<%@include file="/resources/views/imports/header.jsp" %>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1><spring:message code="add_computer.title" /></h1>
					<form:form action="add" modelAttribute="computer" method="POST">
						<fieldset>
							<div class="form-group">
								<spring:message code="computer.name" var="computer_name" />
								<form:label path="name" for="computerName">${computer_name}</form:label> 
								<form:input path="name"
									type="text" name="computerName" class="form-control"
									id="computerName" placeholder="${computer_name}"
									value="${computerName}" />
								<form:errors id="invalid_name" path="name" cssClass="alert alert-danger" element="div" />
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
								<spring:message code="computer.intro" var="computer_intro" />
								<form:label path="introduced" for="introduced">${computer_intro} <br />
									<small class="text-muted">(<spring:message code="add_computer.format.please" /> 
										<i><spring:message code="add_computer.format.slash" /></i> <spring:message code="or" /> 
										<i><spring:message code="add_computer.format.dash" /></i>)
									</small>
								</form:label>
								<form:input path="introduced" type="text" name="introduced" class="form-control"
									id="introduced" placeholder="${computer_intro}"
									value="${introduced}" />
								<form:errors id="invalid_intro" path="introduced" cssClass="alert alert-danger" element="div" />
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
								<spring:message code="computer.disc" var="computer_disc" />
								<form:label path="discontinued" for="discontinued">${computer_disc} <br />
									<small class="text-muted">(<spring:message code="add_computer.format.please" /> 
										<i><spring:message code="add_computer.format.slash" /></i> <spring:message code="or" /> 
										<i><spring:message code="add_computer.format.dash" /></i>)
									</small>
								</form:label>
								<form:input path="discontinued" type="text" name="discontinued" class="form-control"
									id="discontinued" placeholder="${computer_disc}"
									value="${discontinued}" />
								<form:errors id="invalid_disc" path="introduced" cssClass="alert alert-danger" element="div" />
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
								<label for="companyId"><spring:message code="computer.comp" /></label>
								<form:select path="companyId"
									class="form-control" name="companyId" id="companyId">
									<c:forEach items="${companyList}" var="company">
<%-- 										<c:choose> --%>
<%-- 										<c:when test="${companyId == null || companyId eq '0'}"> --%>
<%-- 											<form:option value="${company.getId()}" --%>
<%-- 												<c:if test="${company.getId() eq '0'}">selected="selected"</c:if> --%>
<%-- 											>${company.getName()}</form:option> --%>
<%-- 										</c:when> --%>
<%-- 										<c:otherwise> --%>
											<option value="${company.getId()}" 
												<c:if test="${company.getId() eq companyId}">selected="selected"</c:if>
											>${company.getName()}</option>
<%-- 										</c:otherwise> --%>
<%-- 										</c:choose> --%>
									</c:forEach>
								</form:select>
								<form:errors id="invalid_comp" path="companyId" cssClass="alert alert-danger" element="div" />
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
							<input type="submit" value="<spring:message code="add_computer.add" />" class="btn btn-primary" id="add">
							<spring:message code="or" /> <a href="<c:url value = "/dashboard" />" class="btn btn-default">
							<spring:message code="cancel" /></a>
							<c:choose>
								<c:when test="${status.equals('void')}"></c:when>
								<c:when test="${status.equals('success')}">Computer successfully created!</c:when>
								<c:when test="${status.equals('failed')}">Failed creating the computer!</c:when>
								<c:otherwise></c:otherwise>
							</c:choose>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</section>
	<%@include file="/resources/views/imports/js.jsp" %>
	<script src="<c:url value="/resources/js/validator.js" />"></script>
</body>
</html>
