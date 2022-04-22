<@monLayout.layout title="Modifier travailleur">
	<legend class="text-center">Modification d'un travailleur</legend></h1>
		<form name="travailleur" action="modifierTravailleur.html?id=${travailleur.id}" method="post" enctype="multipart/form-data">

			<legend>Informations personnelles</legend>
			<div class="form-group">
				<div class="hidden">
					<@spring.formInput "travailleurForm.photoFichier" 'class="form-control"' "file" />
				</div>
				<div class="col-sm-2">
					<img height="220" id="image" src="<@spring.url '/resources/image/${travailleur.photo}'/>" alt="${travailleur.nom} ${travailleur.prenom}" />
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
						<@spring.formSingleSelect "travailleur.nationalite" listNationalite 'selected="travailleur.nationalite" class="form-control" required' />
						<@spring.showErrors "<br>" />	 
					</div>
					<label class="col-sm-2 control-label" for="langue">Langue : *</label>
					<div class="col-sm-7">
						<@spring.formSingleSelect "travailleur.langue" listLangue 'selected="travailleur.langue" class="form-control" required' />	
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
			<hr />
			<legend>Documents</legend>
			<div class="well">
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
			</div>
			<div class="well">
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
			</div>
			<div class="well">
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
			</div>
			<div class="well">
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
			</div>
			<div class="form-group">
    			<div class="col-sm-offset-2 col-sm-6">
					<input type="submit" value="   Modifier   " class="btn btn-success"/>
				</div>
  			</div>
  			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
		</form>
</@monLayout.layout>