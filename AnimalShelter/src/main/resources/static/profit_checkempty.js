//check EMPTY INPUT
$("#addProfitForm").submit(function () { 
    var isFormValid = true;

    $("#id_profit_amount").each(function () {
        if ($.trim($(this).val()).length === 0) {
            $("#id_profit_amount").addClass("alert-danger");
            isFormValid = false;
        }
        else {
            $(this).removeClass("alert-danger");
        }
    });
    $("#id_profit_date").each(function () {
        if ($.trim($(this).val()).length === 0) {
            $("#id_profit_date").addClass("alert-danger");
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