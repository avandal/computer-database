<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
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
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard"> Application - Computer Database </a>
        </div>
    </header>
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right">
                        id: ${computerId}
                    </div>
                    <h1>Edit Computer</h1>

                    <form action="editComputer" method="POST">
                        <input type="hidden" value="0" id="id"/> <!-- TODO: Change this value with the computer id -->
                        <fieldset>
                        	<input type="hidden" name="computerId" value="${computerId}" />
                            <div class="form-group">
                                <label for="computerName">Computer name (${originalComputerName} originally)</label>
                                <input type="text" class="form-control" id="computerName" name="computerName" 
                                	placeholder="${originalComputerName}" value="${originalComputerName}">
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
                                <label for="introduced">Introduced date (${originalIntroduced} originally)</label>
                                <input type="text" class="form-control" id="introduced" name="introduced" 
                                	placeholder="${originalIntroduced}" value="${originalIntroduced}">
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
                                <label for="discontinued">Discontinued date (${originalDiscontinued} originally)</label>
                                <input type="text" class="form-control" id="discontinued" name="discontinued" 
                                	placeholder="${originalDiscontinued}" value="${originalDiscontinued}">
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
                                <label for="companyId">Company (${originalCompanyName} originally)</label>
                                <select class="form-control" id="companyId" name="companyId">
                                    <c:forEach items="${companyList}" var="company">
                                    	<option value="${company.getId()}" 
											<c:if test="${company.getId() eq originalCompanyId}">selected="selected"</c:if>
										>${company.getName()}</option>
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
                            <input id="add" type="submit" value="Edit" class="btn btn-primary">
                            or
                            <a href="dashboard" class="btn btn-default">Cancel</a>
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