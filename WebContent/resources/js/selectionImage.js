function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $('#fichierSource').attr('src', e.target.result);
        }

        reader.readAsDataURL(input.files[0]);
    }
}

$(document).ready(function() {
	$("#photoFichier").change(function(){
		 readURL(this);
	});

	$('#image').click(function(){ $('#photoFichier').trigger('click'); });
});