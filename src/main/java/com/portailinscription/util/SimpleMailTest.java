package com.portailinscription.util;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.*;

import com.portailinscription.model.Entreprise;

public class SimpleMailTest {
	@Autowired
	private MailSender mailSender;
	@Autowired
	private SimpleMailMessage templateMessage;
	
	static Logger log = Logger.getLogger(SimpleMailTest.class.getName());
	
	public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setTemplateMessage(SimpleMailMessage templateMessage) {
        this.templateMessage = templateMessage;
    }
    
	public void envoyeMailConfirmation(Entreprise entreprise) {
	    SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
	    msg.setTo(entreprise.getEmailContact());
	    msg.setText(
	        "Cher Madame, Monsieur " + entreprise.getNomContact() + ", \n"
	        + "Merci de vous être enregistré !!!" + "\n"
	        + " Voici les informations concernant l'entreprise enregistrée : " + "\n"
	        + "Numéro BCE : " + entreprise.getNumeroBCE() + "\n"
	        + "Nom de l'entreprise : " + entreprise.getNom() + "\n"
	        + "Adresse du siège social" + "\n"
	        + "Rue : " + entreprise.getRue() + ", "
	        + "Numéro : " + entreprise.getNumero() + "\n"
	        + "Code Postal : " + entreprise.getCodePostal() + " "
	        + "Localité : " + entreprise.getLocalite() + "\n"
	        + "Pays : " + entreprise.getPays() + "\n"
	        + "Type : " + entreprise.getType() + "\n"
	        + "Raison : " + entreprise.getRaisonDemande() + "\n"
	        + "Numéro de référence : " + entreprise.getNumeroReference()
	        + "Veuillez valider votre inscription ici : http://localhost:8080/PortailInscription/entreprise/ficheEntreprise.html?id=" + entreprise.getId());
	    
	    try{
	        this.mailSender.send(msg);
	    }
	    catch (MailException ex) {
	    	log.info(ex);
	    	throw new MailSendException(ex.getMessage());
	    }
	}
}
