//check EMPTY INPUT
$("#addVolunteerForm").submit(function () { 
    var isFormValid = true;

    $("#id_volunteer_name").each(function () {
        if ($.trim($(this).val()).length === 0) {
            $("#id_volunteer_name").addClass("alert-danger");
            isFormValid = false;
        }
        else {
            $(this).removeClass("alert-danger");
        }
    });
    $("#id_volunteer_phone").each(function () {
        if ($.trim($(this).val()).length === 0) {
            $("#id_volunteer_phone").addClass("alert-danger");
            isFormValid = false;
        }
        else {
            $(this).removeClass("alert-danger");
        }
    });
    $("#id_volunteer_address").each(function () {
        if ($.trim($(this).val()).length === 0) {
            $("#id_volunteer_address").addClass("alert-danger");
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