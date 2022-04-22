<@monLayout.layout title="fiche détaillée">
	<h1 class="text-center">Espace Entreprise</h1>
	
	<h2>Fiche détaillée d'une entreprise</h2>
	<h3>Profil</h3>
	<h3 class="text-center"><b>${entreprise.nom}</b></h3>
	<p>Numéro BCE : ${entreprise.numeroBCE}</p>
	<div class="well col-sm-6">
		<h4>Personne de contact</h4>
		<div class="col-sm-3">Nom : </div> <div class="col-sm-9">${entreprise.nomContact}</div>
		<div class="col-sm-3">Prenom : </div> <div class="col-sm-9">${entreprise.prenomContact}</div>
		<div class="col-sm-3">Tel / Gsm : </div> <div class="col-sm-9">${entreprise.telContact}</div>
		<div class="col-sm-3">Email : </div> <div class="col-sm-9">${entreprise.emailContact}</div>
	</div>
	<div class="well col-sm-6">
		<h4>Adresse du siège social</h4>
		<div>Rue ${entreprise.rue}, ${entreprise.numero}</div>
		<div>${entreprise.codePostal} ${entreprise.localite}</div>
		<div>${entreprise.pays}</div>
	</div>
	<div class="clearfix"></div>
	<p>Type : ${entreprise.type}</p>
	<#-- <p>Nom du sous-traitant ma�tre : ${entreprise.nomSousTraitant}</p> -->
	<#if entreprise.etat == "En attente">
		<p>Etat : <span class="label label-warning">${entreprise.etat}</span></p>
	<#elseif entreprise.etat == "Validée">
		<p>Etat : <span class="label label-success">${entreprise.etat}</span></p>
	<#else>
		<p>Etat : <span class="label label-danger">${entreprise.etat}</span></p>
	</#if>
	<#--<p>Date de validation : ${entreprise.dateValidation}</p> -->
	<#-- <p>Commentaire : ${entreprise.commentaire}</p> -->
	<p>Raison de la demande : ${entreprise.raisonDemande}</p>
	<p>Numéro de référence : ${entreprise.numeroReference}</p>
	<#if entreprise.remarque?has_content>
	<p>Remarque : ${entreprise.remarque}</p>
	</#if>
	<div class="well">
		<h4>Documents</h4>
		<#if documents??>
			<#list documents as doc>
				<p>${doc.nom} : ${doc.nomFichier}</p>
			</#list>
		</#if>
	</div>
	<a class="btn btn-warning" href="modifierEntreprise.html?id=${entreprise.id}" role="button">Modifier</a>
	<a class="btn btn-danger" href="supprimerEntreprise.html?id=${entreprise.id}" role="button">Supprimer</a>
</@monLayout.layout>