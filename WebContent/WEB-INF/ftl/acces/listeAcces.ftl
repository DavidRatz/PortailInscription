<@monLayout.layout title="Liste des acces">
	<h1 class="text-center">Espace Entreprise</h1>	
	<h2>Les travailleurs ayant un accès pour un projet</h2>
	<a class="btn btn-success" href="ajouterAcces.html" role="button">Ajouter</a> <br /> <br />
		<table class="table table-hover" id="acces">
			<thead>
				<tr>
					<th>Nom du projet</th>
					<th>Nom du travailleur</th>
					<th>Prenom du travailleur</th>
					<th>Date de début</th>
					<th>Date de fin</th>
					<th></th>
					<th></th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<#list acces as a>
				<tr id="afficher">
					<td>${a.projet.nom}</td>
					<td>${a.travailleur.nom}</td> 
					<td>${a.travailleur.prenom}</td>
					<td>${a.dateDebut}</td>
					<td>${a.dateFin}</td>
					<td><a role="button" class="glyphicon glyphicon-eye-open" href="ficheAcces.html?id=${a.id}"></a></td>
					<td><a role="button" class="text-warning glyphicon glyphicon-pencil" href="<@spring.url '/acces/modifierAcces.html?id=${a.id}'/>"></a></td>
					<td><a role="button" id="confirmationSuppression" class="text-danger glyphicon glyphicon-trash" href="supprimerAcces.html?id=${a.id}"></a></td>
				</tr>
				</#list>
			</tbody>
		</table>
</@monLayout.layout>