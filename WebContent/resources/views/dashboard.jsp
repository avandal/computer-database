<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@	page import="java.util.List, java.util.Optional" %>
<%@ page import="com.excilys.computer_database.dto.ComputerDTO, com.excilys.computer_database.util.Util" %>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href=<c:url value = "/resources/css/bootstrap.min.css" /> rel="stylesheet" media="screen">
<link href=<c:url value = "/resources/css/font-awesome.css" /> rel="stylesheet" media="screen">
<link href=<c:url value = "/resources/css/main.css" /> rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard.html"> Application - Computer Database </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <h1 id="homeTitle">
            	<c:choose>
	            	<c:when test = "${ nbComputers > 0}">
	                	${ nbComputers } Computers found
	                </c:when>
	                <c:otherwise>
	                	No computer found
	                </c:otherwise>
                </c:choose>
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="#" method="GET" class="form-inline">

                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name" />
                        <input type="submit" id="searchsubmit" value="Filter by name"
                        class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="addComputer.html">Add Computer</a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Edit</a>
                </div>
            </div>
        </div>

        <form id="deleteForm" action="#" method="POST">
            <input type="hidden" name="selection" value="">
        </form>

        <div class="container" style="margin-top: 10px;">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <!-- Variable declarations for passing labels as parameters -->
                        <!-- Table header for Computer Name -->

                        <th class="editMode" style="width: 60px; height: 22px;">
                            <input type="checkbox" id="selectall" /> 
                            <span style="vertical-align: top;">
                                 -  <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
                                        <i class="fa fa-trash-o fa-lg"></i>
                                    </a>
                            </span>
                        </th>
                        <th>
                            Computer name
                        </th>
                        <th>
                            Introduced date
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                            Discontinued date
                        </th>
                        <!-- Table header for Company -->
                        <th>
                            Company
                        </th>

                    </tr>
                </thead>
                <!-- Browse attribute computers -->
                <tbody id="results">
                	<c:forEach items = "${ webPage.indexPage() }" var="computer">
                    <tr>
                        <td class="editMode">
                            <input type="checkbox" name="cb" class="cb" value="0">
                        </td>
                        <td>
                            <a href="editComputer.html" onclick="">${ computer.getName() }</a>
                        </td>
                        <td>${ computer.getIntroduced() }</td>
                        <td>${ computer.getDiscontinued() }</td>
                        <td>${ computer.getCompanyName() }</td>

                    </tr>
                    </c:forEach>
                    
                </tbody>
            </table>
        </div>
    </section>

    <footer class="navbar-fixed-bottom">
    	<% 
    		int index = ((Integer) request.getAttribute("pageIndex"));
    		int pSize = ((Integer) request.getAttribute("pageSize"));
    		int nbComp = ((Integer) request.getAttribute("nbComputers"));
    	%>
        <div class="container text-center">
            <ul class="pagination">
                <li>
                    <a href="${webPage.previousPage('dashboard')}" aria-label="Previous">
                      <span aria-hidden="true">&laquo;</span>
                  </a>
              </li>
              
              <c:choose>
              	<c:when test = "${ pageIndex >= 3 }">
              		<% for (int i = index - 2; i < index + 3; i++) { %>
              			<li><a href="dashboard?pageIndex=<%= i %>"><%= i %></a></li>
              		<% } %>
              	</c:when>
              	<c:otherwise>
              		<% for (int i = 1; i <= 5; i++) { %>
              			<li><a href="dashboard?pageIndex=<%= i %>"><%= i %></a></li>
              		<% } %>
              	</c:otherwise>
              </c:choose>
              
              <li>
                <a href="${webPage.nextPage('dashboard')}" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        </ul>
		
        <div class="btn-group btn-group-sm pull-right" role="group" >
        	<a href="dashboard/" class="btn btn-default">5</a>
        	<button type="button" class="btn btn-default">5</button>
            <button type="button" class="btn btn-default">10</button>
            <button type="button" class="btn btn-default">20</button>
            <button type="button" class="btn btn-default">50</button>
            <button type="button" class="btn btn-default">100</button>
        </div>
		</div>
    </footer>
<script src="../js/jquery.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<script src="../js/dashboard.js"></script>

</body>
</html>