<@monLayout.layout title="modifier projet">
	<h1 class="text-center">Espace Entreprise</h1>
	
	<legend>Ajouter projet pour une entreprise</legend>
		<form name="projet" action="modifierProjet.html?id=${projet.id}" method="post">

			<div class="form-group">
				<label class="col-sm-2 control-label" for="nom">Nom: *</label>
				<div class="col-sm-6">
					<@spring.formInput "projet.nom" 'class="form-control" required' />	
					<@spring.showErrors "<br>" />	 
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label" for="typeLabel">Type : *</label>
				<div class="col-sm-6">
					<@spring.formSingleSelect "projetForm.nomType" types 'selected="projetForm.nomType" class="form-control"' />	
					<@spring.showErrors "<br>" />	 
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label" for="rue">Rue : *</label>
				<div class="col-sm-6">
					<@spring.formInput "projet.rue" 'class="form-control" required' />	
					<@spring.showErrors "<br>" />	 
				</div>
			</div> 
			<div class="form-group">
				<label class="col-sm-2 control-label" for="numero">Numéro : *</label>
				<div class="col-sm-6">
					<@spring.formInput "projet.numero" 'class="form-control" required' />	
					<@spring.showErrors "<br>" />	 
				</div>
			</div> 
			<div class="form-group">
				<label class="col-sm-2 control-label" for="codePostal">Code postal : *</label>
				<div class="col-sm-6">
					<@spring.formInput "projet.codePostal" 'class="form-control" min="0000" max="99999" "maxlength="5"' "number" />	
					<@spring.showErrors "<br>" />	 
				</div>
			</div> 
			<div class="form-group">
				<label class="col-sm-2 control-label" for="localite">Localité : *</label>
				<div class="col-sm-6">
					<@spring.formInput "projet.localite" 'class="form-control" required' />	
					<@spring.showErrors "<br>" />	 
				</div>
			</div>	 
			<div class="form-group">
				<label class="col-sm-2 control-label" for="pays">Pays : *</label>
				<div class="col-sm-6">
					<@spring.formSingleSelect "projet.pays" listPays 'selected="projet.pays" class="form-control" required' />	
					<@spring.showErrors "<br>" />	 
				</div>
			</div>
			<div class="form-group">
    			<div class="col-sm-offset-5 col-sm-6">
					<input type="submit" value="   Modifier   " class="btn btn-success"/>
				</div>
  			</div>
  			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
		</form>
</@monLayout.layout>