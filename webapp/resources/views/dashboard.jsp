<%@ include file="/resources/views/imports/taglibs.jsp" %>
<%@ page import="com.excilys.computer_database.binding.dto.ComputerDTO"%>
<!DOCTYPE html>
<html>
<%@include file="/resources/views/imports/head.jsp" %>
<body>
	<%@include file="/resources/views/imports/header.jsp" %>

	<section id="main">
	
		<div class="container">
			<h1 id="homeTitle">
				<c:choose>
					<c:when test="${ nbComputers > 0}">
	                	${ nbComputers } <spring:message code="dashboard.nb_computer" /> - ${webPage.firstId()}/${webPage.lastId()}
	                </c:when>
					<c:otherwise>
	                	<spring:message code="dashboard.no_computer" />
	                </c:otherwise>
				</c:choose>
			</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="<c:url value="/dashboard" />" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="<spring:message code="dashboard.search.placeholder" />" /> <input
							type="submit" id="searchsubmit" value="<spring:message code="dashboard.search.value" />"
							class="btn btn-primary" />
					</form>
				</div>
				<sec:authorize access="hasAuthority('admin')">
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer"
						href=<c:url value="computer/new" />><spring:message code="dashboard.add_computer" /></a> 
						<a class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode();"><spring:message code="dashboard.edit_computer" /></a>
						<a class="btn btn-default" id="view" href="#"
						onclick="$.fn.toggleEditMode();" style="display: none"><spring:message code="dashboard.view"/></a>
				</div>
				</sec:authorize>
			</div>
		</div>

		<form id="deleteForm" action="delete" method="POST">
			<input type="hidden" name="selection" value="">

			<div class="container" style="margin-top: 10px;">
				<table class="table table-striped table-bordered">
					<thead>
						<tr>
							<!-- Variable declarations for passing labels as parameters -->
							<!-- Table header for Computer Name -->
							<sec:authorize access="hasAuthority('admin')">
							<th class="editMode" style="width: 60px; height: 22px;"><input
								type="checkbox" id="selectall" /> <span
								style="vertical-align: top;"> - <a href="#"
									id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
										class="fa fa-trash-o fa-lg"></i>
								</a>
							</span></th>
							</sec:authorize>
							<th>
								<ul class="list-inline sort-mode">
									<li>
										<a href="${webPage.orderByNameDesc()}" title="<spring:message code="dashboard.sort.name" /> (desc.)"><i class="fa fa-chevron-up" style="font-size: 10px"></i></a><br />
										<a href="${webPage.orderByNameAsc()}" title="<spring:message code="dashboard.sort.name" /> (asc.)"><i class="fa fa-chevron-down" style="font-size: 10px"></i></a>
									</li>
									<li><spring:message code="computer.name" /></li>
								</ul>
							</th>
							<th>
								<ul class="list-inline sort-mode">
									<li>
										<a href="${webPage.orderByIntroDesc()}" title="<spring:message code="dashboard.sort.intro" /> (desc.)"><i class="fa fa-chevron-up" style="font-size: 10px"></i></a><br />
										<a href="${webPage.orderByIntroAsc()}" title="<spring:message code="dashboard.sort.intro" /> (asc.)"><i class="fa fa-chevron-down" style="font-size: 10px"></i></a>
									</li>
									<li><spring:message code="computer.intro" /></li>
								</ul>
							</th>
							<!-- Table header for Discontinued Date -->
							<th>
								<ul class="list-inline sort-mode">
									<li>
										<a href="${webPage.orderByDiscDesc()}" title="<spring:message code="dashboard.sort.disc" /> (desc.)"><i class="fa fa-chevron-up" style="font-size: 10px"></i></a><br />
										<a href="${webPage.orderByDiscAsc()}" title="<spring:message code="dashboard.sort.disc" /> (asc.)"><i class="fa fa-chevron-down" style="font-size: 10px"></i></a>
									</li>
									<li><spring:message code="computer.disc" /></li>
								</ul>
							</th>
							<!-- Table header for Company -->
							<th>
								<ul class="list-inline sort-mode">
									<li>
										<a href="${webPage.orderByCompDesc()}" title="<spring:message code="dashboard.sort.comp" /> (desc.)"><i class="fa fa-chevron-up" style="font-size: 10px"></i></a><br />
										<a href="${webPage.orderByCompAsc()}" title="<spring:message code="dashboard.sort.comp" /> (asc.)"><i class="fa fa-chevron-down" style="font-size: 10px"></i></a>
									</li>
									<li><spring:message code="computer.comp" /></li>
								</ul>
							</th>
						</tr>
					</thead>
					<!-- Browse attribute computers -->
					<tbody id="results">
						<c:forEach items="${ webPage.indexPage() }" var="computer">
							<tr>
								<sec:authorize access="hasAuthority('admin')">
								<td class="editMode"><input type="checkbox" name="cb"
									class="cb" value="${computer.getId()}"></td>
								<td>
								</sec:authorize>
								<sec:authorize access="hasAuthority('admin')">
								<a href="<c:url value="computer/edit" />?computerId=${computer.getId()}" onclick="">${ computer.getName() }</a>
								</sec:authorize>
								<sec:authorize access="hasAnyAuthority('guest')">
									${ computer.getName() }
								</sec:authorize>
								</td>
								<td>${ computer.getIntroduced() }</td>
								<td>${ computer.getDiscontinued() }</td>
								<td>${ computer.getCompanyName() }</td>
	
							</tr>
						</c:forEach>
	
					</tbody>
				</table>
			</div>
		</form>
	</section>

	<footer class="navbar-fixed-bottom">
		<% 
    		int index = ((Integer) request.getAttribute("pageIndex"));
    		int pSize = ((Integer) request.getAttribute("pageSize"));
    		int nbComp = ((Integer) request.getAttribute("nbComputers"));
    	%>
		<div class="container text-center">
			<ul class="pagination">
				<c:if test="${webPage.getFirstIndex() > 1}">
					<li><a href="${webPage.previousPage()}"
						aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
					</a></li>
				</c:if>

				<c:forEach var="i" begin="${webPage.getFirstIndex()}"
					end="${webPage.getLastIndex()}">
					<li class="<c:if test="${webPage.getIndex() == i}">active</c:if>"><a href="${webPage.indexAt(i)}">${i}</a></li>
				</c:forEach>
				
				<c:if test="${webPage.getLastIndex() < webPage.getPageCount()}">
					<li><a href="${webPage.nextPage()}"
						aria-label="Next"> <span aria-hidden="true">&raquo;</span>
					</a></li>
				</c:if>
			</ul>

			<div class="btn-group btn-group-sm pull-right" role="group">

				<c:forEach items="${webPage.sizes()}" var="pSize">
					<a href="${webPage.setPageSize(pSize.getSize())}"
						class="btn btn-default">${pSize.getSize()}</a>
				</c:forEach>

			</div>
		</div>
	</footer>
	<%@include file="/resources/views/imports/js.jsp" %>
	<script src="<c:url value="/resources/js/dashboard.js" />"></script>
</body>
</html>
