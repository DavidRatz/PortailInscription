<@monLayout.layout title="modifier mot de passe">
	<h1 class="text-center">Espace Entreprise</h1>
	<legend>Modification du mot de passe</legend>
		<form name="changeMotPasseForm" action="modifierMotPasse.html" method="post">

			<div class="form-group">
				<label class="col-sm-2 control-label" for="motDePasseActuel">Mot de passe actuel : *</label>
				<div class="col-sm-6">
					<@spring.formInput "changeMotPasseForm.motDePasseActuel" 'class="form-control"' "password"/>	
					<@spring.showErrors "<br>" />	 
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label" for="nouveauMotDePasse">Nouveau mot de passe : *</label>
				<div class="col-sm-6">
					<@spring.formInput "changeMotPasseForm.nouveauMotDePasse" 'class="form-control"' "password"/>	
					<@spring.showErrors "<br>" />	 
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label" for="confirmationMotDePasse">Confirmation mot de passe: *</label>
				<div class="col-sm-6">
					<@spring.formInput "changeMotPasseForm.confirmationMotDePasse" 'class="form-control"' "password"/>	
					<@spring.showErrors "<br>" />	 
				</div>
			</div>
			<div class="form-group">
    			<div class="col-sm-offset-2 col-sm-6">
					<input type="submit" value="   Modifier   " class="btn btn-success"/>
				</div>
  			</div>
		</form>
</@monLayout.layout>