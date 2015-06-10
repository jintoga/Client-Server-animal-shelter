$('#filter1').change(f1);

$('#filter2').change(f1);

$('#filter3').change(f1);

function f1() {
    $('.getRidOfIt').hide();

    v1 = $("#filter1").val();
    v2 = $("#filter2").val();
    v3 = $("#filter3").val();

    $.ajax({
        type: "GET",
        url: "/filter",
        dataType: "json",
        data: {v1: v1, v2: v2, v3: v3}

    }).done(function (data) {
        $('.getRid').hide();

        for (var item in data) {
            //alert(data[item].name);
            var showAge = null;
            var showGender = null;



            if (data[item].age === 1) {
                showAge = "< 1 Года";
            }
            else if (data[item].age === 2) {

                showAge = "1-3 Лет";
            }
            else if (data[item].age === 3) {

                showAge = "3-5 Лет";
            }
            else if (data[item].age === 4) {

                showAge = "> 5 Лет";
            }

            if (data[item].gender === 1) {
                showGender = "Мальчик";
            }
            else if (data[item].gender === 2) {
                showGender = "Девочка";
            }

            if (showAge === null) {
                showAge = "Неизвестно";
            }
            if (showGender === null) {
                showGender = "Неизвестно";
            }

            //alert(showAge);
            //$("#newTableHeader").html('<th>Фото</th> <th>Вид</th><th>Кличка</th><th>Пол</th><th>Возраст</th>');
            $("#message").append('<tr class="getRid"><td> <div style="height: 70px; width: 70px"><a class="showModalAnimal" data-id=' + data[item].pk_animal + '><img style="cursor: pointer; max-height: 100%; max-width: 100%" src="/img/animals/' + data[item].pk_animal + '.jpg' + '"/></a></div></td>'
                    + '<td> <h4><span class="myIcon">' + data[item].type_animal + '</span></h4></td>'
                    + '<td> <h4><span>' + data[item].name + '</span></h4></td>'
                    + '<td> <h4><span>' + showGender + '</span></h4></td>'
                    + '<td> <h4><span>' + showAge + '</span></h4></td>'
                    + '<td><form class="getRidOfIt" method="GET" action="/animals/'
                    + data[item].pk_animal + '"><button class="btn btn-info btn-xs">Подробнее</button></form></td></tr>');
//                var id = data[item].pk_animal;
//                alert(id);
//                //$(".showModalAnimal").attr("id", id);
            $('.myIcon').each(function () {
                var icon = $(this).text().trim();
                if (icon === 'Кот') {
                    //alert("a");

                    $(this).html('<div style="height: 70px; width: 70px"><img  style="max-height: 100%; max-width: 100%" src="/icon/' + 'cat.png' + '"/></div>');
                    //$(this).hide();
                }
                if (icon === 'Собака') {
                    //alert("a");

                    $(this).html('<div style="height: 70px; width: 70px"><img  style="max-height: 100%; max-width: 100%" src="/icon/' + 'dog.png' + '"/></div>');
                    //$(this).hide();
                }
                if (icon === 'Прочие') {
                    //alert("a");

                    $(this).html('<div style="height: 70px; width: 70px"><img  style="max-height: 100%; max-width: 100%" src="/icon/' + 'alien.png' + '"/></div>');
                    //$(this).hide();
                }

//        alert(icon);
            });
        }
//            var n = $(document).height();
//            $('html, body').animate({scrollTop: n}, 1000);

    });

}

$(".maybeUnknown").each(function (){
   if($(this).text()===""){
       $(this).html("Неизвестно");
   } 
});

//check EMPTY INPUT
$("#addAnimalFrom").submit(function () {
    var isFormValid = true;

    $("#id_weight").each(function () {
        if ($.trim($(this).val()).length === 0) {
            $("#id_weight").addClass("alert-danger");
            isFormValid = false;
        }
        else {
            $(this).removeClass("alert-danger");
        }
    });
    $("#id_name_animal").each(function () {
        if ($.trim($(this).val()).length === 0) {
            $("#id_name_animal").addClass("alert-danger");
            isFormValid = false;
        }
        else {
            $(this).removeClass("alert-danger");
        }
    });
    $("#id_breed").each(function () {
        if ($.trim($(this).val()).length === 0) {
            $("#id_breed").addClass("alert-danger");
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

//$('#myButton').click(function () {
//    alert('aas');
//    if ($('#inputError').val().length === 0) {
//        $('#inputError').addClass("form-control");
//    }
//});


