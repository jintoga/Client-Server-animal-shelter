//check EMPTY INPUT
$("#addShelterForm").submit(function () { 
    var isFormValid = true;

    $("#id_shelter_name").each(function () {
        if ($.trim($(this).val()).length === 0) {
            $("#id_shelter_name").addClass("alert-danger");
            isFormValid = false;
        }
        else {
            $(this).removeClass("alert-danger");
        }
    });
    $("#id_shelter_phone").each(function () {
        if ($.trim($(this).val()).length === 0) {
            $("#id_shelter_phone").addClass("alert-danger");
            isFormValid = false;
        }
        else {
            $(this).removeClass("alert-danger");
        }
    });
    $("#id_shelter_address").each(function () {
        if ($.trim($(this).val()).length === 0) {
            $("#id_shelter_address").addClass("alert-danger");
            isFormValid = false;
        }
        else {
            $(this).removeClass("alert-danger");
        }
    });
    $("#id_shelter_seat").each(function () {
        if ($.trim($(this).val()).length === 0) {
            $("#id_shelter_seat").addClass("alert-danger");
            isFormValid = false;
        }
        else {
            $(this).removeClass("alert-danger");
        }
    });
    $("#id_shelter_free_seat").each(function () {
        if ($.trim($(this).val()).length === 0) {
            $("#id_shelter_free_seat").addClass("alert-danger");
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