//check EMPTY INPUT
$("#addSponsorForm").submit(function () { 
    var isFormValid = true;

    $("#id_sponsor_name").each(function () {
        if ($.trim($(this).val()).length === 0) {
            $("#id_sponsor_name").addClass("alert-danger");
            isFormValid = false;
        }
        else {
            $(this).removeClass("alert-danger");
        }
    });
    $("#id_sponsor_phone").each(function () {
        if ($.trim($(this).val()).length === 0) {
            $("#id_sponsor_phone").addClass("alert-danger");
            isFormValid = false;
        }
        else {
            $(this).removeClass("alert-danger");
        }
    });
    $("#id_sponsor_address").each(function () {
        if ($.trim($(this).val()).length === 0) {
            $("#id_sponsor_address").addClass("alert-danger");
            isFormValid = false;
        }
        else {
            $(this).removeClass("alert-danger");
        }
    });
    
    
    if (!isFormValid) {
        alert("Пожалуйста заполните все необходимые поля!");
//        $("#id_name_animal").addClass("form-control");

    }
    return isFormValid;
});