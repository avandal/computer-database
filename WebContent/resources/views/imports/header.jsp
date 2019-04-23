<header class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<a class="navbar-brand" href=<c:url value="dashboard" />>
			<spring:message code="title" /></a>
	</div>
	<div id="lang">
		<a href="#" onclick="changeLanguage('en')" title="<spring:message code="language.en" />">
			<img class="lang_flag" src="<c:url value="/resources/assets/en-flag.png" />"/>
		</a>
		<a href="#" onclick="changeLanguage('fr')"  title="<spring:message code="language.fr" />">
			<img class="lang_flag" src="<c:url value="/resources/assets/fr-flag.png" />"/>
		</a>
	</div>
</header>