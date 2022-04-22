<@monLayout.layout title="Connexion">
    <fieldset>
		<legend>Connexion</legend>
		<#if error?has_content>
			<span class="text-danger">${error}</span>
		</#if>
		<form name="connectForm" action="connexion.html" method="post">
			<div class="form-group">
				<label class="col-sm-2 control-label">Email : *</label> 
				<div class="input-group col-sm-6">
					<span class="input-group-addon"><span class="glyphicon glyphicon-envelope"></span></span>
					<@spring.formInput "connectForm.username" 'class="form-control"' "email"/>	
					<@spring.showErrors "<br>" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">Mot de passe : *</label>
				<div class="input-group col-sm-6">
					<span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span>
					<@spring.formPasswordInput "connectForm.password" 'class="form-control"'/>	
					<@spring.showErrors "<br>" />	<br/>
				</div>
			</div>
			<div class="col-sm-offset-6 col-sm-4">
				<a href="modifierMotPasse.html">Mot de passe oublié ?</a>
			</div>
			<div class="form-group">
    			<div class="col-sm-offset-6 col-sm-4">
					<input type="submit" value="   Connexion   " class="btn btn-default"/>
				</div>
  			</div>
  			
  			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
		</form>
		
	</fieldset>
</@monLayout.layout>