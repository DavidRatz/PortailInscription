<@monLayout.layout title="Liste des travailleurs">
	<h1 class="text-center">Espace Entreprise</h1>	
	<h2>Mes travailleurs</h2>
	<a class="btn btn-success" href="ajouterTravailleur.html?id=${idEntreprise}" role="button">Ajouter</a> <br /> <br />
	<table class="display table table-hover">
		<thead>
			<tr>
				<th>#</th>
				<th>Nom</th>
				<th>Prenom</th>
				<th>Nationalité</th>
				<th>Langue</th>
				<th>Adresse email</th>
				<th></th>
				<th></th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<#list travailleurs as travailleur>
			<tr id="afficher">
				<#if travailleursAvecAcces?size != 0>
					<#list travailleursAvecAcces as travailleursAcces>
						<#if travailleur.id == travailleursAcces.id>
							<td><span class="text-success glyphicon glyphicon-ok"></span></td>
						<#else>
							<td></td>
						</#if>
					</#list>
				<#else>
					<td></td>
				</#if>
				<td>${travailleur.nom}</td>
				<td>${travailleur.prenom}</td> 
				<td>${travailleur.nationalite}</td>
				<td>${travailleur.langue}</td>
				<td>${travailleur.adresseEmail}</td>
				<td><a role="button" class="glyphicon glyphicon-eye-open" href="ficheTravailleur.html?id=${travailleur.id}"></a></td>
				<td><a role="button" class="text-warning glyphicon glyphicon-pencil" href="modifierTravailleur.html?id=${travailleur.id}"></a></td>
				<td><a role="button" id="confirmationSuppression" class="text-danger glyphicon glyphicon-trash" href="supprimerTravailleur.html?id=${travailleur.id}"></a></td>
			</tr>
			</#list>
		</tbody>
	</table>
	<#list entrepriseSousTraitante as entreprise>
		<h2>Les travailleurs de ${entreprise.nom}</h2>
		<a class="btn btn-success" href="ajouterTravailleur.html?id=${entreprise.id}" role="button">Ajouter</a> <br /> <br />
		<table class="display table table-hover">
			<thead>
				<tr>
					<th>#</th>
					<th>Nom</th>
					<th>Prenom</th>
					<th>Nationalité</th>
					<th>Langue</th>
					<th>Adresse email</th>
					<th></th>
					<th></th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<#list travailleursSousTraitant as travailleur>
				<tr id="afficher">
					<#if travailleursAvecAcces?size != 0>
						<#list travailleursAvecAcces as travailleursAcces>
							<#if travailleur.id == travailleursAcces.id>
								<td><span class="text-success glyphicon glyphicon-ok"></span></td>
							<#else>
								<td></td>
							</#if>
						</#list>
					<#else>
						<td></td>
					</#if>
					<td>${travailleur.nom}</td>
					<td>${travailleur.prenom}</td> 
					<td>${travailleur.nationalite}</td>
					<td>${travailleur.langue}</td>
					<td>${travailleur.adresseEmail}</td>
					<td><a role="button" class="glyphicon glyphicon-eye-open" href="ficheTravailleur.html?id=${travailleur.id}"></a></td>
					<td><a role="button" class="text-warning glyphicon glyphicon-pencil" href="modifierTravailleur.html?id=${travailleur.id}"></a></td>
					<td><a role="button" id="confirmationSuppression" class="text-danger glyphicon glyphicon-trash" href="supprimerTravailleur.html?id=${travailleur.id}"></a></td>
				</tr>
				</#list>
			</tbody>
		</table>
	</#list>
</@monLayout.layout>