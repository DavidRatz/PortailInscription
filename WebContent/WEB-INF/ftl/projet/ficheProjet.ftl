<@monLayout.layout title="fiche projet">
	<h1 class="text-center">Espace Entreprise</h1>
	
	<h2>Fiche détaillée du projet de l'entreprise ${projet.entreprise.nom}</h2>
	<p>Nom : ${projet.nom}</p>
	<p>Type : ${projet.type.nom}</p>
	<p>Rue ${projet.rue}, ${projet.numero}</p>
	<p>${projet.codePostal} ${projet.localite}</p>
	<p>${projet.pays}</p>
	<a class="btn btn-warning" href="modifierProjet.html?id=${projet.id}" role="button">Modifier</a>
	<a class="btn btn-danger" href="supprimerProjet.html?id=${projet.id}" role="button">Supprimer</a>
</@monLayout.layout>