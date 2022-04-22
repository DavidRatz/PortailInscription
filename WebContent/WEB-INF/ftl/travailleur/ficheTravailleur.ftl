<@monLayout.layout title="fiche détaillée">
	<h1 class="text-center">Espace Entreprise</h1>
	
	<h2>Fiche détaillée d'un travailleur</h2>
	<h3>Informations personnelles</h3>
	<div class="col-sm-4">
		<img height="200" src="<@spring.url '/resources/image/${travailleur.photo}'/>" alt="photo de ${travailleur.nom} ${travailleur.prenom}" />
	</div>
	<div>
		<#if travailleur.numeroReference != 0>
			<p>Numéro de référence : ${travailleur.numeroReference}</p>
		</#if>
		<div class="col-sm-2">Entreprise : </div> <div class="col-sm-6">${nomEntreprise}</div>
		<div class="col-sm-2">Nom : </div> <div class="col-sm-6">${travailleur.nom}</div>
		<div class="col-sm-2">Prenom : </div> <div class="col-sm-6">${travailleur.prenom}</div>
		<div class="col-sm-2">Date de naissance : </div> <div class="col-sm-6">${travailleur.dateNaissance}</div>
		<div class="col-sm-2">Nationalité : </div> <div class="col-sm-6">${travailleur.nationalite}</div>
		<div class="col-sm-2">Langue : </div> <div class="col-sm-6">${travailleur.langue}</div>
		<div class="col-sm-2">Email : </div> <div class="col-sm-6">${travailleur.adresseEmail}</div>
		<div class="col-sm-2">Tel / Gsm : </div> <div class="col-sm-6">${travailleur.telephone}</div>
	</div>
	<#-- <p>Date de début de validité : ${acces.dateDebutValidite}</p>
	<p>Date de fin de validité : ${acces.dateFinValidite}</p>
	<p>Etat : ${acces.etatDemande}</p>
	<#if acces.dateValidation??>
		<p>Date de validation : ${ acces.dateValidation}</p>
	</#if>-->
	<div class="clearfix"></div>
	<h3>Documents</h3>
	<#if documents??>
		<#list documents as doc>
			<div class="well">
			<h4>${doc.nom}</h4>
			<p>Fichier : ${doc.nomFichier}</p>
			<p>Date de validité : ${doc.dateValidite}</p>
			</div>
		</#list>
	</#if>
	<h3>Les accès du travailleur</h3>
	<table class="table table-hover">
		<thead>
			<tr>
				<th>Nom du projet</th>
				<th>Date de début</th>
				<th>Date de fin</th>
			</tr>
		</thead>
		<tbody>
			<#list acces as a>
			<tr>
				<td>${a.projet.nom}</td>
				<td>${a.dateDebut}</td>
				<td>${a.dateFin}</td>
			</tr>
			</#list>
		</tbody>
	</table>
	<hr />
	<#if travailleur.dateValidation??>
	<p>Date de validation : ${travailleur.dateValidation}</p>
	</#if>
	<p>Date et heure de création : ${travailleur.dateCreation}</p>
	<#if travailleur.dateModification??>
	<p>Date et heure de modification : ${travailleur.dateModification}</p>
	</#if>
	<p>Utilisateur ayant effectué la derniére modification : ${travailleur.entreprise.nomContact} ${travailleur.entreprise.prenomContact}</p>
	<#-- <p>Date de validité du controle d'accès : ${acces.dateValidationControleAcces}</p>
	<p>Date et heure de création : ${acces.dateCreation}</p>-->

	<a class="btn btn-warning" href="modifierTravailleur.html?id=${travailleur.id}" role="button">Modifier</a>
	<a class="btn btn-danger" href="supprimerTravailleur.html?id=${travailleur.id}" role="button">Supprimer</a>
</@monLayout.layout>