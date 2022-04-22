<@monLayout.layout title="Inscription entreprise">
	    
	<legend class="text-center"><@spring.message "inscription"/></legend>
	<div class="well">
		<p>Les champs obligatoires sont suivis d'un astérisque (*)</p>
	</div>
	<form name="entreprise" action="ajouterEntreprise.html" method="post">

		<div class="form-group">
			<label class="col-sm-2 control-label" for="numeroBCE"><@spring.message "numeroBCE"/> : *</label>
			<div class="col-sm-6">
				<@spring.formInput "entreprise.numeroBCE" 'class="form-control" maxlength="13" pattern="([A-Z]{2})([0-9]{9})" required' />	
				<@spring.showErrors "<br>" />	 
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label" for="nom"><@spring.message "nom"/> : *</label>
			<div class="col-sm-6">
				<@spring.formInput "entreprise.nom" 'class="form-control" required' />	
				<@spring.showErrors "<br>" />	 
			</div>
		</div>
		<legend><@spring.message "personneContact"/></legend>
		<div class="form-group">
			<label class="col-sm-2 control-label" for="nomContact"><@spring.message "nomContact"/> : *</label>
			<div class="col-sm-6">
				<@spring.formInput "entreprise.nomContact" 'class="form-control" required' />	
				<@spring.showErrors "<br>" />	 
			</div>
		</div> 
		<div class="form-group">
			<label class="col-sm-2 control-label" for="prenomContact"><@spring.message "prenomContact"/> : *</label>
			<div class="col-sm-6">
				<@spring.formInput "entreprise.prenomContact" 'class="form-control" required' />	
				<@spring.showErrors "<br>" />	 
			</div>
		</div> 
		<div class="form-group">
			<label class="col-sm-2 control-label" for="telContact"><@spring.message "telContact"/> : *</label>
			<div class="col-sm-6">
				<@spring.formInput "entreprise.telContact" 'class="form-control" required' />	
				<@spring.showErrors "<br>" />	 
			</div>
		</div> 
		<div class="form-group">
			<label class="col-sm-2 control-label" for="emailContact"><@spring.message "emailContact"/> : *</label>
			<div class="input-group col-sm-7">
				<span class="input-group-addon"><span class="glyphicon glyphicon-envelope"></span></span>
				<@spring.formInput "entreprise.emailContact" 'class="form-control" required' "email" />
				<#if errorMail?has_content>
					<span class="text-danger">${errorMail}</span>
				</#if>
				<@spring.showErrors "<br>" />	 
			</div>
		</div>
		<legend><@spring.message "adresse"/></legend>
		<div class="form-group">
			<label class="col-sm-2 control-label" for="rue"><@spring.message "rue"/> : *</label>
			<div class="col-sm-6">
				<@spring.formInput "entreprise.rue" 'class="form-control" required' />	
				<@spring.showErrors "<br>" />	 
			</div>
		</div> 
		<div class="form-group">
			<label class="col-sm-2 control-label" for="numero"><@spring.message "numero"/> : *</label>
			<div class="col-sm-6">
				<@spring.formInput "entreprise.numero" 'class="form-control" required' />	
				<@spring.showErrors "<br>" />	 
			</div>
		</div> 
		<div class="form-group">
			<label class="col-sm-2 control-label" for="codePostal"><@spring.message "codePostal"/> : *</label>
			<div class="col-sm-6">
				<@spring.formInput "entreprise.codePostal" 'class="form-control" required maxlength="5"' "number" />	
				<@spring.showErrors "<br>" />	 
			</div>
		</div> 
		<div class="form-group">
			<label class="col-sm-2 control-label" for="localite"><@spring.message "localite"/> : *</label>
			<div class="col-sm-6">
				<@spring.formInput "entreprise.localite" 'class="form-control" required' />	
				<@spring.showErrors "<br>" />	 
			</div>
		</div>	 
		<div class="form-group">
			<label class="col-sm-2 control-label" for="pays"><@spring.message "pays"/> : *</label>
			<div class="col-sm-6">
				<@spring.formSingleSelect "entreprise.pays" listPays 'class="form-control" required' />	
				<@spring.showErrors "<br>" />	 
			</div>
		</div>
		<hr />
		<div class="form-group">
			<label class="col-sm-2 control-label" for="type"><@spring.message "type"/> : *</label>
			<div class="col-sm-6">
				<@spring.formRadioButtons "entreprise.type" type " " 'class="form-check-input"' />	
				<@spring.showErrors "<br>" />	 
			</div>
		</div>
		<div class="form-group" id="numeroBceSocieteMaitre">
			<label class="col-sm-2 control-label" for="numeroBceMaitre"><@spring.message "numeroBceMaitre"/> : *</label>
			<div class="col-sm-6">
				<@spring.formInput "entreprise.numeroBceMaitre" 'class="form-control" maxlength="13" pattern="([A-Z]{2})([0-9]{9})"' />	
				<@spring.showErrors "<br>" />	 
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label" for="raisonDemande"><@spring.message "raisonDemande"/> : *</label>
			<div class="col-sm-6">
				<@spring.formTextarea "entreprise.raisonDemande" 'class="form-control" required' />	
				<@spring.showErrors "<br>" />	 
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label" for="numeroReference"><@spring.message "numeroReference"/> : *</label>
			<div class="col-sm-6">
				<@spring.formInput "entreprise.numeroReference" 'class="form-control" required' />	
				<@spring.showErrors "<br>" />	 
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label" for="remarque"><@spring.message "remarque"/> : </label>
			<div class="col-sm-6">
				<@spring.formTextarea "entreprise.remarque" 'class="form-control"' />	
				<@spring.showErrors "<br>" />	 
			</div>
		</div>
		<legend>Documents</legend>
		<div class="form-group">
			<label class="col-sm-2 control-label" for="bonCommande"><@spring.message "bonCommande"/> : *</label>
			<div class="col-sm-6">
				<@spring.formInput "entrepriseForm.bonCommande" 'class="form-control" required' "file" />	
				<@spring.showErrors "<br>" />	 
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label" for="statutSociete"><@spring.message "statutSociete"/> : *</label>
			<div class="col-sm-6">
				<@spring.formInput "entrepriseForm.statutSociete" 'class="form-control" required' "file" />	
				<@spring.showErrors "<br>" />	 
			</div>
		</div>
		<hr />
		<div class="form-group">
			<label class="col-sm-2 control-label" for="motPasse"><@spring.message "motPasse"/> : *</label>
			<div class="col-sm-6">
				<@spring.formPasswordInput "entrepriseForm.motPasse" 'class="form-control" required' />	
				<@spring.showErrors "<br>" />	 
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label" for="confirmation"><@spring.message "confirmation"/> : *</label>
			<div class="col-sm-6">
				<@spring.formPasswordInput "entrepriseForm.confirmation" 'class="form-control" required' />	
				<@spring.showErrors "<br>" />	 
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-offset-6 col-sm-6">
				<input type="submit" value="   Ajouter   " class="btn btn-success"/>
			</div>
		</div>
		<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
	</form>
</@monLayout.layout>