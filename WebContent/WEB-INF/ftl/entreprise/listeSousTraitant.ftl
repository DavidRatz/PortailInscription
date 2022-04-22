<@monLayout.layout title="Liste des entreprise sous-traitante">
	<h1 class="text-center">Espace Entreprise</h1>	
	<h2>Mes entreprises sous-traitantes</h2>
	<a class="btn btn-success" href="ajouterEntreprise.html" role="button">Ajouter</a> <br />
	<br />
		<table class="table table-hover" id="entrepriseSousTraitante">
			<thead>
				<tr>
					<th>Numéro BCE</th>
					<th>Nom</th>
					<th>Rue</th>
					<th>Numero</th>
					<th>Code postal</th>
					<th>Localité</th>
					<th>Pays</th>
					<th>Type</th>
					<th></th>
					<th></th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<#list entrepriseSousTraitante as entreprise>
				<tr id="afficher">
					<td>${entreprise.numeroBCE}</td>
					<td>${entreprise.nom}</td>
					<td>${entreprise.rue}</td> 
					<td>${entreprise.numero}</td>
					<td>${entreprise.codePostal}</td>
					<td>${entreprise.localite}</td>
					<td>${entreprise.pays}</td>
					<td>${entreprise.type}</td>
					<td><a role="button" class="glyphicon glyphicon-eye-open" href="ficheEntreprise.html?id=${entreprise.id}"></a></td>
					<td><a role="button" class="text-warning glyphicon glyphicon-pencil" href="modifierEntreprise.html?id=${entreprise.id}"></a></td>
					<td><a role="button" id="confirmationSuppression" class="text-danger glyphicon glyphicon-trash" href="supprimerEntreprise.html?id=${entreprise.id}"></a></td>
				</tr>
				</#list>
			</tbody>
		</table>
</@monLayout.layout>