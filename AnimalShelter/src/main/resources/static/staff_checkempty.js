//check EMPTY INPUT
$("#addStaffForm").submit(function () { 
    var isFormValid = true;

    $("#id_staff_name").each(function () {
        if ($.trim($(this).val()).length === 0) {
            $("#id_staff_name").addClass("alert-danger");
            isFormValid = false;
        }
        else {
            $(this).removeClass("alert-danger");
        }
    });
    $("#id_staff_career").each(function () {
        if ($.trim($(this).val()).length === 0) {
            $("#id_staff_career").addClass("alert-danger");
            isFormValid = false;
        }
        else {
            $(this).removeClass("alert-danger");
        }
    });
    $("#id_staff_phone").each(function () {
        if ($.trim($(this).val()).length === 0) {
            $("#id_staff_phone").addClass("alert-danger");
            isFormValid = false;
        }
        else {
            $(this).removeClass("alert-danger");
        }
    });
    $("#id_staff_address").each(function () {
        if ($.trim($(this).val()).length === 0) {
            $("#id_staff_address").addClass("alert-danger");
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