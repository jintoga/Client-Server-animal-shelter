//check EMPTY INPUT
$("#addTempOwnerForm").submit(function () { 
    var isFormValid = true;

    $("#id_temp_owner_name").each(function () {
        if ($.trim($(this).val()).length === 0) {
            $("#id_temp_owner_name").addClass("alert-danger");
            isFormValid = false;
        }
        else {
            $(this).removeClass("alert-danger");
        }
    });
    $("#id_temp_owner_telephone").each(function () {
        if ($.trim($(this).val()).length === 0) {
            $("#id_temp_owner_telephone").addClass("alert-danger");
            isFormValid = false;
        }
        else {
            $(this).removeClass("alert-danger");
        }
    });
    $("#id_temp_owner_address").each(function () {
        if ($.trim($(this).val()).length === 0) {
            $("#id_temp_owner_address").addClass("alert-danger");
            isFormValid = false;
        }
        else {
            $(this).removeClass("alert-danger");
        }
    });
     $("#id_temp_owner_amount_of_animal").each(function () {
        if ($.trim($(this).val()).length === 0) {
            $("#id_temp_owner_amount_of_animal").addClass("alert-danger");
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