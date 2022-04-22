<@monLayout.layout title="Liste des projets">
	<h1 class="text-center">Espace Entreprise</h1>	
	<h2>Mes projets</h2>
	<a class="btn btn-success" href="ajouterProjet.html" role="button">Ajouter</a> <br /><br />
		<table class="table table-hover" id="projet">
			<thead>
				<tr>
					<th>Nom</th>
					<th>Rue</th>
					<th>Numero</th>
					<th>Code Postal</th>
					<th>Localite</th>
					<th>Pays</th>
					<th></th>
					<th></th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<#list projets as projet>
				<tr id="afficher">
					<td>${projet.nom}</td>
					<td>${projet.rue}</td> 
					<td>${projet.numero}</td>
					<td>${projet.codePostal}</td>
					<td>${projet.localite}</td>
					<td>${projet.pays}</td>
					<td><a role="button" class="glyphicon glyphicon-eye-open" href="ficheProjet.html?id=${projet.id}"></a></td>
					<td><a role="button" class="text-warning glyphicon glyphicon-pencil" href="modifierProjet.html?id=${projet.id}"></a></td>
					<td><a role="button" id="confirmationSuppression" class="text-danger glyphicon glyphicon-trash" href="supprimerProjet.html?id=${projet.id}"></a></td>
				</tr>
				</#list>
			</tbody>
		</table>
</@monLayout.layout>