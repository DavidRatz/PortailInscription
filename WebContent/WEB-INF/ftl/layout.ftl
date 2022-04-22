<#macro layout title>
<!doctype html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="_csrf" content="${_csrf.token}"/>
		<!-- default header name is X-CSRF-TOKEN -->
		<meta name="_csrf_header" content="${_csrf.headerName}"/>
		<meta content="width=device-width, initial-scale=1.0" name="viewport" />
		<title>Portail d'inscription <#if title??>- ${title}</#if></title>
		<link rel="stylesheet" href="<@spring.url '/resources/bootstrap-3.3.7/css/bootstrap.min.css'/>">
		<link rel="stylesheet" href="<@spring.url '/resources/css/style.css'/>">
		<link rel="stylesheet" href="<@spring.url '/resources/css/bootstrap-datetimepicker.min.css'/>">
		<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/bs/dt-1.10.13/datatables.min.css"/>
		
		<script type="text/javascript" src="<@spring.url '/resources/js/jquery-3.1.1.min.js'/>"></script>
		<script type="text/javascript" src="<@spring.url '/resources/js/script.js'/>"></script>
		<script type="text/javascript" src="<@spring.url '/resources/js/bootstrap.min.js'/>"></script>
		<script type="text/javascript" src="<@spring.url '/resources/js/bootstrap-datetimepicker.js" charset="UTF-8'/>"></script>
		<script type="text/javascript" src="<@spring.url '/resources/js/bootstrap-datetimepicker.fr.js" charset="UTF-8'/>"></script>
		<script type="text/javascript" src="<@spring.url '/resources/js/selectionImage.js'/>"></script>
		<script type="text/javascript" src="<@spring.url '/resources/js/dateTimePickerSettings.js'/>"></script>
		<script type="text/javascript" src="https://cdn.datatables.net/v/bs/dt-1.10.13/datatables.min.js"></script>
		
	</head>
	<body>
		<nav class="navbar navbar-default">
			<div class="container-fluid">
				<!-- Brand and toggle get grouped for better mobile display -->
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
						<span class="sr-only">Toggle navigation</span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="<@spring.url '/index.html'/>">Accueil</a>
				</div>
	
				<!-- Collect the nav links, forms, and other content for toggling -->
				<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav">
						<#if nomEntreprise??>
							<li><a href="<@spring.url '/travailleur/listeTravailleur.html'/>">Travailleur</a></li>
							<li><a href="<@spring.url '/projet/listeProjet.html'/>">Projet</a></li>
							<li><a href="<@spring.url '/acces/listeAcces.html'/>">Accès</a></li>
							<li><a href="<@spring.url '/entreprise/listeSousTraitant.html'/>">Sous-traitant</a></li>
						</#if>
					</ul>
		      		<ul class="nav navbar-nav navbar-right">
		      			<li id="locale"><a href="<@spring.url '/entreprise/ajouterEntreprise.html?lang=fr'/>">Fr</a></li>
				        <#if nomEntreprise??>
					    	<li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown">${nomEntreprise} <span class="glyphicon glyphicon-chevron-down"></span></a>
					    	<ul class="dropdown-menu">
					    		<li><a href="<@spring.url '/entreprise/ficheEntreprise.html?id=${id}'/>">Profil</a></li>
					    		<li><a id="confirmationDeconnexion" href="<@spring.url '/deconnexion.html'/>">Deconnexion</a></li>
				   			</ul>
				   			</li>
				   		<#else>
				   			<li><a href="<@spring.url '/connexion.html'/>">Connexion</a></li>
					    	<li><a href="<@spring.url '/entreprise/ajouterEntreprise.html'/>">Inscription</a></li>
				    	</#if>
		      		</ul>	
				</div><!-- /.navbar-collapse -->
	  		</div><!-- /.container-fluid -->
		</nav>
		<div class="container">
			<#nested>
		</div>
		<footer class="footer">
			<div class="container">
				<span>&copy; Nom du portail - 2017</span>
			</div>
		</footer>  
	</body>
</html>
</#macro>