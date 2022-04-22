<@monLayout.layout title="modifier acces">
	<h1 class="text-center">Espace Entreprise</h1>
	
	<legend>Modifier accès</legend>
	<form name="acces" action="modifierAcces.html?id=${acces.id}" method="post">
		
		<div class="form-group">
			<label class="col-sm-2 control-label" for="nomTravailleur">Travailleur : *</label>
			<div class="col-sm-6">
				<@spring.formSingleSelect "accesForm.nomTravailleur" travailleurs 'class="form-control"' />	
				<@spring.showErrors "<br>" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label" for="nomProjet">Projet : *</label>
			<div class="col-sm-6">
				<@spring.formSingleSelect "accesForm.nomProjet" projets 'class="form-control"' />	
				<@spring.showErrors "<br>" />	 
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label" for="dateDebut">Date de début : *</label>
			<div class="input-group date form_date col-sm-7" data-date-format="dd/mm/yyyy" data-link-format="dd/mm/yyyy" data-link-field="dateDebut">
			    <@spring.formInput "accesForm.dateDebut" 'readonly class="form-control"' />	
				<@spring.showErrors "<br>" />
				<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label" for="dateFin">Date de fin : *</label>
			<div class="input-group date form_date col-sm-7" data-date-format="dd/mm/yyyy" data-link-format="dd/mm/yyyy" data-link-field="dateFin">
			    <@spring.formInput "accesForm.dateFin" 'readonly class="form-control"' />	
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