<@monLayout.layout title="Ajouter travailleur">
	<h1 class="text-center">Espace Entreprise</h1>
	
	<legend class="text-center">Ajout d'un travailleur</legend>
	<div class="well">
		<p>Les champs obligatoires sont suivis d'un astérisque (*)</p>
	</div>
	<form name="travailleur" action="ajouterTravailleur.html?id=${id}" method="post" enctype="multipart/form-data">
		<legend>Informations personnelles</legend>
		<div class="form-group">
			<div class="hidden">
				<@spring.formInput "travailleurForm.photoFichier" 'class="form-control" required' "file" />
			</div>
			<div class="col-sm-2">
				<img height="220" id="fichierSource" src="<@spring.url '/resources/image/nophotoavailable.jpg'/>" alt="your image" />
				<button type="button" id="image">Ajouter une photo</button>
				<#if errorImage?has_content>
					${errorImage}
				</#if>
			</div>
			<div class="col-sm-10">
				<label class="col-sm-2 control-label" for="nom">Nom : *</label>
				<div class="col-sm-7">
					<@spring.formInput "travailleur.nom" 'class="form-control" required' />	
					<@spring.showErrors "<br>" />	 
				</div>
				<label class="col-sm-2 control-label" for="prenom">Prenom : *</label>
				<div class="col-sm-7">
					<@spring.formInput "travailleur.prenom" 'class="form-control" required' />	
					<@spring.showErrors "<br>" />	 
				</div>
				<label class="col-sm-2 control-label" for="dateNaissance">Date de naissance : *</label>
				<div class="input-group date form_date col-sm-7" data-date-format="dd/mm/yyyy" data-link-format="dd/mm/yyyy" data-link-field="dateNaissance">
				    <@spring.formInput "travailleurForm.dateNaissance" 'readonly class="form-control" required' />	
					<@spring.showErrors "<br>" />
					<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
				</div>
				<label class="col-sm-2 control-label" for="nationalite">Nationalité : *</label>
				<div class="col-sm-7">
					<@spring.formSingleSelect "travailleur.nationalite" listNationalite 'class="form-control" required' />
					<@spring.showErrors "<br>" />	 
				</div>
				<label class="col-sm-2 control-label" for="langue">Langue : *</label>
				<div class="col-sm-7">
					<@spring.formSingleSelect "travailleur.langue" listLangue 'class="form-control" required' />	
					<@spring.showErrors "<br>" />	 
				</div>
				<label class="col-sm-2 control-label" for="telephone">Gsm : *</label>
				<div class="col-sm-7">
					<@spring.formInput "travailleur.telephone" 'class="form-control" required' />	
					<@spring.showErrors "<br>" />	 
				</div>
				<label class="col-sm-2 control-label" for="adresseEmail">Email : *</label>
				<div class="input-group col-sm-7">
					<span class="input-group-addon"><span class="glyphicon glyphicon-envelope"></span></span>
					<@spring.formInput "travailleur.adresseEmail" 'class="form-control" required' "email" />	
					<@spring.showErrors "<br>" />
				</div>
			</div>
		</div>
		<legend>Documents</legend>
		<legend>E101</legend>
		<div class="form-group">
			<label class="col-sm-2 control-label" for="file">Fichier : *</label>
			<div class="input-group col-sm-6">
				<@spring.formInput "travailleurForm.file" 'class="form-control" required' "file" />	
				<@spring.showErrors "<br>" />	
				<span class="input-group-addon"><span class="glyphicon glyphicon-file"></span></span>  
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label" for="date">Date de validité : *</label>
			<div class="input-group date form_date col-sm-6" data-date-format="dd/mm/yyyy" data-link-format="dd/mm/yyyy" data-link-field="date">
			    <@spring.formInput "travailleurForm.date[0]" 'readonly class="form-control" required' />	
				<@spring.showErrors "<br>" />
				<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
			</div>
		</div>
		<legend>Limosa</legend>
		<div class="form-group">
			<label class="col-sm-2 control-label" for="file">Fichier : *</label>
			<div class="input-group col-sm-6">
				<@spring.formInput "travailleurForm.file" 'class="form-control" required' "file" />	
				<@spring.showErrors "<br>" />
				<span class="input-group-addon"><span class="glyphicon glyphicon-file"></span></span> 	 
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label" for="date">Date de validité : *</label>
			<div class="input-group date form_date col-sm-6" data-date-format="dd/mm/yyyy" data-link-format="dd/mm/yyyy" data-link-field="date">
			    <@spring.formInput "travailleurForm.date[1]" 'readonly class="form-control" required' />	
				<@spring.showErrors "<br>" />
				<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
			</div>
		</div>
		<legend>Certificat Medical</legend>
		<div class="form-group">
			<label class="col-sm-2 control-label" for="file">Fichier : *</label>
			<div class="input-group col-sm-6">
				<@spring.formInput "travailleurForm.file" 'class="form-control" required' "file" />	
				<@spring.showErrors "<br>" />
				<span class="input-group-addon"><span class="glyphicon glyphicon-file"></span></span>  
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label" for="date">Date de validité : *</label>
			<div class="input-group date form_date col-sm-6" data-date-format="dd/mm/yyyy" data-link-format="dd/mm/yyyy" data-link-field="date">
			    <@spring.formInput "travailleurForm.date[2]" 'readonly class="form-control" required' />	
				<@spring.showErrors "<br>" />
				<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
			</div>
		</div>
		<legend>Copie Passeport</legend>
		<div class="form-group">
			<label class="col-sm-2 control-label" for="file">Fichier : *</label>
			<div class="input-group col-sm-6">
				<@spring.formInput "travailleurForm.file" 'class="form-control" required' "file" />	
				<@spring.showErrors "<br>" />
				<span class="input-group-addon"><span class="glyphicon glyphicon-file"></span></span> 
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label" for="date">Date de validité : *</label>
			<div class="input-group date form_date col-sm-6" data-date-format="dd/mm/yyyy" data-link-format="dd/mm/yyyy" data-link-field="date">
			    <@spring.formInput "travailleurForm.date[3]" 'readonly class="form-control" required' />	
				<@spring.showErrors "<br>" />
				<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
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