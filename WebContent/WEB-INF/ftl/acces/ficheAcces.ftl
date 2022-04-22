<@monLayout.layout title="fiche acces">
	<h1 class="text-center">Espace Entreprise</h1>
	
	<h2>Fiche d'accès pour le projet : <b>${acces.projet.nom}</b></h2>
	<p>Nom du travailleur : ${acces.travailleur.nom}</p>
	<p>Prénom du travailleur : ${acces.travailleur.prenom}</p>
	<p>Date de début : ${acces.dateDebut}</p>
	<p>Date de fin : ${acces.dateFin}</p>
	<a class="btn btn-warning" href="modifierAcces.html?id=${acces.id}" role="button">Modifier</a>
	<a class="btn btn-danger" href="supprimerAcces.html?id=${acces.id}" role="button">Supprimer</a>
</@monLayout.layout>