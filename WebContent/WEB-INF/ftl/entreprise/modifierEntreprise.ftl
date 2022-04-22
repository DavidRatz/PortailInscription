<@monLayout.layout title="Modifier entreprise">
	<h1 class="text-center">Espace Entreprise</h1>
	
	<legend class="text-center">Modification d'une entreprise</legend></h1>
	<div class="well">
		<p>Les champs obligatoires sont suivis d'un astérisque (*)</p>
	</div>
	<form name="entreprise" action="modifierEntreprise.html?id=${entreprise.id}" method="post">

		<div class="form-group">
			<label class="col-sm-2 control-label" for="numeroBCE">Numéro BCE : *</label>
			<div class="col-sm-6">
				<@spring.formInput "entreprise.numeroBCE" 'class="form-control"' />	
				<@spring.showErrors "<br>" />	 
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label" for="nom">Nom de l'entreprise : *</label>
			<div class="col-sm-6">
				<@spring.formInput "entreprise.nom" 'class="form-control"' />	
				<@spring.showErrors "<br>" />	 
			</div>
		</div>
		<legend>Personne de contact</legend>
		<div class="form-group">
			<label class="col-sm-2 control-label" for="nomContact">Nom : *</label>
			<div class="col-sm-6">
				<@spring.formInput "entreprise.nomContact" 'class="form-control"' />	
				<@spring.showErrors "<br>" />	 
			</div>
		</div> 
		<div class="form-group">
			<label class="col-sm-2 control-label" for="prenomContact">Prenom : *</label>
			<div class="col-sm-6">
				<@spring.formInput "entreprise.prenomContact" 'class="form-control"' />	
				<@spring.showErrors "<br>" />	 
			</div>
		</div> 
		<div class="form-group">
			<label class="col-sm-2 control-label" for="telContact">Telephone/GSM : *</label>
			<div class="col-sm-6">
				<@spring.formInput "entreprise.telContact" 'class="form-control"' />	
				<@spring.showErrors "<br>" />	 
			</div>
		</div> 
		<div class="form-group">
			<label class="col-sm-2 control-label" for="emailContact">Email : *</label>
			<div class="input-group col-sm-7">
				<span class="input-group-addon"><span class="glyphicon glyphicon-envelope"></span></span>
				<@spring.formInput "entreprise.emailContact" 'class="form-control"' />	
				<@spring.showErrors "<br>" />	 
			</div>
		</div>
		<legend>Adresse du siège social</legend>
		<div class="form-group">
			<label class="col-sm-2 control-label" for="rue">Rue : *</label>
			<div class="col-sm-6">
				<@spring.formInput "entreprise.rue" 'class="form-control"' />	
				<@spring.showErrors "<br>" />	 
			</div>
		</div> 
		<div class="form-group">
			<label class="col-sm-2 control-label" for="numero">Numéro : *</label>
			<div class="col-sm-6">
				<@spring.formInput "entreprise.numero" 'class="form-control"' />	
				<@spring.showErrors "<br>" />	 
			</div>
		</div> 
		<div class="form-group">
			<label class="col-sm-2 control-label" for="codePostal">Code postal : *</label>
			<div class="col-sm-6">
				<@spring.formInput "entreprise.codePostal" 'class="form-control" maxlength="5"' "number" />	
				<@spring.showErrors "<br>" />	 
			</div>
		</div> 
		<div class="form-group">
			<label class="col-sm-2 control-label" for="localite">Localité : *</label>
			<div class="col-sm-6">
				<@spring.formInput "entreprise.localite" 'class="form-control"' />	
				<@spring.showErrors "<br>" />	 
			</div>
		</div>	 
		<div class="form-group">
			<label class="col-sm-2 control-label" for="pays">Pays : *</label>
			<div class="col-sm-6">
				<@spring.formSingleSelect "entreprise.pays" listPays 'selected="entreprise.pays" class="form-control" required' />	
				<@spring.showErrors "<br>" />	 
			</div>
		</div>
		<hr />
		<div class="form-group">
			<label class="col-sm-2 control-label" for="type">Type : *</label>
			<div class="col-sm-6">
				<@spring.formSingleSelect "entreprise.type" type 'selected="entreprise.type" class="form-control"' />	
				<@spring.showErrors "<br>" />	 
			</div>
		</div>
		<div class="form-group" id="numeroBceSocieteMaitre">
			<label class="col-sm-2 control-label" for="numeroBceMaitre">Num�ro BCE de la société maître : *</label>
			<div class="col-sm-6">
				<@spring.formInput "entreprise.numeroBceMaitre" 'class="form-control" maxlength="13" pattern="([A-Z]{2})([0-9]{9})"' />	
				<@spring.showErrors "<br>" />	 
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label" for="raisonDemande">Raison de la demande : *</label>
			<div class="col-sm-6">
				<@spring.formTextarea "entreprise.raisonDemande" 'class="form-control"' />	
				<@spring.showErrors "<br>" />	 
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label" for="numeroReference">Numéro Référence : *</label>
			<div class="col-sm-6">
				<@spring.formInput "entreprise.numeroReference" 'class="form-control"' />	
				<@spring.showErrors "<br>" />	 
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label" for="remarque">Remarques : </label>
			<div class="col-sm-6">
				<@spring.formTextarea "entreprise.remarque" 'class="form-control"' />	
				<@spring.showErrors "<br>" />	 
			</div>
		</div>
		<legend>Documents</legend>
		<div class="well">
			<div class="form-group">
				<label class="col-sm-2 control-label" for="bonCommande">Bon de commande : *</label>
				<div class="col-sm-6">
					<@spring.formInput "entrepriseForm.bonCommande" 'class="form-control"' "file" />	
					<@spring.showErrors "<br>" />	 
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label" for="statutSociete">Statut de la société : *</label>
				<div class="col-sm-6">
					<@spring.formInput "entrepriseForm.statutSociete" 'class="form-control"' "file" />	
					<@spring.showErrors "<br>" />	 
				</div>
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-offset-6 col-sm-6">
				<input type="submit" value="   Modifier   " class="btn btn-success"/>
			</div>
		</div>
		<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
	</form>
</@monLayout.layout>