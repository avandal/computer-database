<header class="navbar navbar-inverse navbar-fixed-top">
	<div class="container form-horizontal">
		<a class="navbar-brand pull-left" href="<c:url value="/dashboard" />">
			<spring:message code="title" /></a>
	<span id="lang" class="pull-right">
		<a href="#" onclick="changeLanguage('en')" title="<spring:message code="language.en" />">
			<img class="lang_flag" src="<c:url value="/resources/assets/en-flag.png" />"/>
		</a>
		<a href="#" onclick="changeLanguage('fr')"  title="<spring:message code="language.fr" />">
			<img class="lang_flag" src="<c:url value="/resources/assets/fr-flag.png" />"/>
		</a>
	</span>
	<sec:authorize access="hasAnyAuthority('admin', 'guest')">
		<span id="logout" class="pull-right">
			<a class="btn btn-default pull-right" href=<c:url value="/logoutProcess" />>
				<spring:message code="login.logout" />
			</a> 
		</span>
	</sec:authorize>
	</div>
</header>
