//$(document).ready(function() {
//	$("#emailSousTraitant").hide();
//	$('input[name=type]').change(function() {
//		if($(this).val() == "Societe sous-traitante maitre"){
//			$("#emailSousTraitant").show();
//		}
//		else
//		{
//            $("#emailSousTraitant").hide();
//		}
//    });
//});

$(this).ready(function() {
	$("#numeroBceSocieteMaitre").hide();
	$('input[name=type]').change(function() {
		if($(this).val() == "Societe sous-traitante maitre"){
			$("#numeroBceSocieteMaitre").hide();
		}
		else
		{
            $("#numeroBceSocieteMaitre").show();
		}
    });
	
	$('#type').change(function() {
		var val = this.value;
		if(val == "Societe sous-traitante maitre"){
			$("#numeroBceSocieteMaitre").hide();
		}
		else
		{
            $("#numeroBceSocieteMaitre").show();
		}
    });
	$("table #afficher").click(function() {
	    window.location.href = $(this).find("a").attr("href");
	});
	
	$('table.display').dataTable( {
        "language": {
            "url": "//cdn.datatables.net/plug-ins/1.10.13/i18n/French.json"
        },
		"columns": [
			{ "orderable": false},
		    null,
		    null,
		    null,
		    null,
		    null,
		    { "orderable": false, "width": "10%" },
			{ "orderable": false, "width": "10%" },
			{ "orderable": false},
	    ],
	    "order": [[ 1, "asc" ]]
    } );

	$('#projet').dataTable( {
        "language": {
            "url": "//cdn.datatables.net/plug-ins/1.10.13/i18n/French.json"
        },
		"columns": [
			null,
		    null,
		    null,
		    null,
		    null,
		    null,
		    { "orderable": false, "width": "10%" },
			{ "orderable": false, "width": "10%" },
			{ "orderable": false},
	    ]
    } );
	
	$('#acces').dataTable( {
        "language": {
            "url": "//cdn.datatables.net/plug-ins/1.10.13/i18n/French.json"
        },
		"columns": [
			null,
		    null,
		    null,
		    null,
		    null,
		    { "orderable": false, "width": "10%" },
			{ "orderable": false, "width": "10%" },
			{ "orderable": false},
	    ]
    } );
	
	$('#entrepriseSousTraitante').dataTable( {
        "language": {
            "url": "//cdn.datatables.net/plug-ins/1.10.13/i18n/French.json"
        },
		"columns": [
			null,
		    null,
		    null,
		    null,
		    null,
		    null,
		    null,
		    null,
		    { "orderable": false, "width": "10%" },
			{ "orderable": false, "width": "10%" },
			{ "orderable": false},
	    ]
    } );
	
	$("#confirmationSuppression").click(function(e) {
		if(!confirm("Etes-vous sure de vouloir supprimer cette ligne ?")){
			e.preventDefault();
			return false;
 		}
 	});
	
	$("#confirmationDeconnexion").click(function(e) {
		if(!confirm("Etes-vous sure de vouloir vous deconnecter ?")){
			e.preventDefault();
			return false;
 		}
 	});
	
    $("#locale").change(function () {
        var selectedOption = $('#locale').val();
        if (selectedOption != ''){
            window.location.replace('?lang=' + selectedOption);
        }
    });
});