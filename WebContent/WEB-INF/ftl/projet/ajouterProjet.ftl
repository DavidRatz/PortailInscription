<@monLayout.layout title="ajout projet">
	<h1 class="text-center">Espace Entreprise</h1>
	
	<legend>Ajouter projet pour une entreprise</legend>
	<div class="well">
		<p>Les champs obligatoires sont suivis d'un astérisque (*)</p>
	</div>
	<form name="projet" action="ajouterProjet.html" method="post">

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
				<@spring.formSingleSelect "projetForm.nomType" types 'class="form-control"' />	
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
				<@spring.formSingleSelect "projet.pays" listPays 'class="form-control" required' />	
				<@spring.showErrors "<br>" />	 
			</div>
		</div>
		<div class="col-sm-offset-5 col-sm-6">
			<button type="submit" class="btn btn-success">Ajouter</button>
		</div>
		<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
	</form>
</@monLayout.layout>