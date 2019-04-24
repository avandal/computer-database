<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="com.excilys.computer_database.dto.ComputerDTO"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
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
    <%@include file="/resources/views/imports/header.jsp" %>
    
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right">
                        id: ${computerId}
                    </div>
                    <h1><spring:message code="edit_computer.title" /></h1>

                    <form:form action="editComputer" modelAttribute="computer" method="POST">
                        <fieldset>
                        	<form:input path="id" type="hidden" name="computerId" value="${computerId}" />
                            <div class="form-group">
                                <form:label path="name" for="computerName"><spring:message code="computer.name" /> (${originalComputerName} <spring:message code="edit_computer.originally" />)</form:label>
                                <form:input path="name" type="text" class="form-control" id="computerName" name="computerName" 
                                	placeholder="${originalComputerName}" value="${originalComputerName}" />
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
                                <form:label path="introduced" for="introduced"><spring:message code="computer.intro" /> (${originalIntroduced} 
                                	<spring:message code="edit_computer.originally" />)</form:label>
                                <form:input path="introduced" type="text" class="form-control" id="introduced" name="introduced" 
                                	placeholder="${originalIntroduced}" value="${originalIntroduced}" />
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
                                <form:label path="discontinued" for="discontinued"><spring:message code="computer.disc" /> (${originalDiscontinued} 
                                	<spring:message code="edit_computer.originally" />)</form:label>
                                <form:input path="discontinued" type="text" class="form-control" id="discontinued" name="discontinued" 
                                	placeholder="${originalDiscontinued}" value="${originalDiscontinued}" />
                                <form:errors id="invalid_disc" path="discontinued" cssClass="alert alert-danger" element="div" />
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
                                <form:label path="companyId" for="companyId"><spring:message code="computer.comp" /> (${originalCompanyName} 
                                	<spring:message code="edit_computer.originally" />)</form:label>
                                <form:select path="companyId" class="form-control" id="companyId" name="companyId">
                                    <c:forEach items="${companyList}" var="company">
                                    	<option value="${company.getId()}" 
											<c:if test="${company.getId() eq originalCompanyId}">selected="selected"</c:if>
										>${company.getName()}</option>
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
                            <input id="add" type="submit" value="<spring:message code="edit_computer.edit" />" class="btn btn-primary">
                            <spring:message code="or" />
                            <a href="dashboard" class="btn btn-default"><spring:message code="cancel" /></a>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </section>
    <script src="resources/js/jquery.min.js"></script>
	<script src="resources/js/bootstrap.min.js"></script>
	<script src="resources/js/validator.js"></script>
	<script src="resources/js/lang.js"></script>
</body>
</html>