
$(document).ready(function () {
//    changeIcon(); 
//    function changeIcon() {
//        var icon = $('.myIcon').text().trim();
//        alert(icon);
//        if (icon === 'Кот') {
//             alert("a");
//        }
//
//
//    }
    function rescale() {
        var size = {width: $(window).width(), height: $(window).height()};
        /*CALCULATE SIZE*/
        var offset = 20;
        var offsetBody = 150;
        $('#myModal').css('height', size.height - offset);
        $('.modal-body').css('height', size.height - (offset + offsetBody));
        $('#myModal').css('top', 0);
    }
    $(window).bind("resize", rescale);

    //reposition Modal to center
    $(function () {
        function reposition() {
            var modal = $(this),
                    dialog = modal.find('.modal-dialog');
            modal.css('display', 'block');

            // Dividing by two centers the modal exactly, but dividing by three 
            // or four works better for larger screens.
            dialog.css("margin-top", Math.max(0, ($(window).height() - dialog.height()) / 2));
        }
        // Reposition when a modal is shown
        $('.modal').on('show.bs.modal', reposition);
        // Reposition when the window is resized
        $(window).on('resize', function () {
            $('.modal:visible').each(reposition);
        });
    });
    //show Modal image
    $(document).on('click', '.showModalAnimal', function () {
        //alert("click");
        pk_animal = $(this).data("id");
        //alert(pk_animal);
        $.ajax({
            type: "POST",
            url: "/animals/" + pk_animal + "/showAnimal",
            dataType: "json",
            data: {pk_animal: pk_animal}
        }).done(function (data) {

            //alert(data.name);

            $('#myModal').modal('show');  // put your modal id 
            $('#modalContent').modal('show').html('<img  style="max-height: 100%; max-width: 100%" src="/img/animals/' + data.pk_animal + '.jpg' + '"/>');
            $('#modalHeader').modal('show').html(data.name);

        });
    });

    $(document).on('click', '.showModalTempAnimal', function () {
        //alert("click");
        pk_animal = $(this).data("id");
        //alert(pk_animal);
        $.ajax({
            type: "POST",
            url: "/animals/" + pk_animal + "/showTempAnimal",
            dataType: "json",
            data: {pk_animal: pk_animal}
        }).done(function (data) {
            var showAge;
            var showGender;
            var showSterilize;
            if (data.age === 1) {

                showAge = "< 1 Года";
            }
            else if (data.age === 2) {

                showAge = "1-3 Лет";
            }
            else if (data.age === 3) {

                showAge = "3-5 Лет";
            }
            else if (data.age === 4) {

                showAge = "> 5 Лет";
            } else {
                showAge = "";
            }

            if (data.gender === 1) {
                showGender = "Мальчик";
            }
            else if (data.gender === 2) {
                showGender = "Девочка";
            }
            else {
                showGender = "";
            }
            if (data.sterilized === 1) {
                showSterilize = "Да";
            }
            else if (data.sterilized === 2) {
                showSterilize = "Нет";
            }
            else {
                showSterilize = "";
            }
            //alert(data.name);

            $('#myModal2').modal('show');  // put your modal id 

            $('#modalContent2').modal('show').html('<img  style="max-height: 100%; max-width: 100%" src="/img/temp_animals/' + data.pk_animal + '.jpg' + '"/>');
            $('#modalContent3').modal('show').html('<h4>Вид: <span class="myIcon">' + data.type_animal + '</span></h4>');
            $('#modalContent3').modal('show').append('<h4>Кличка: <span>' + data.name + '</span></h4>');
            $('#modalContent3').modal('show').append('<h4>Порода: <span>' + data.breed + '</span></h4>');
            $('#modalContent3').modal('show').append('<h4>Пол: <span>' + showGender + '</span></h4>');
            $('#modalContent3').modal('show').append('<h4>Возраст: <span>' + showAge + '</span></h4>');
            $('#modalContent3').modal('show').append('<h4>Вес: <span>' + data.weight + 'кг</span></h4>');
            $('#modalContent3').modal('show').append('<h4>Стерилизован: <span>' + showSterilize + '</span></h4>');
            $('#modalContent3').modal('show').append('<h4>Другие Информации: <span>' + data.description + '</span></h4>');
            $('.myIcon').each(function () {
                var icon = $(this).text().trim();
                if (icon === 'Кот') {
                    //alert("a");

                    $(this).html(icon + '(<img  style="max-height: 100%; max-width: 100%" src="/icon/' + 'cat.png' + '"/>)');
                    //$(this).hide();
                }
                if (icon === 'Собака') {
                    //alert("a");

                    $(this).html(icon + '(<img  style="max-height: 100%; max-width: 100%" src="/icon/' + 'dog.png' + '"/>)');
                    //$(this).hide();
                }
                if (icon === 'Прочие') {
                    //alert("a");

                    $(this).html(icon + '(<img  style="max-height: 100%; max-width: 100%" src="/icon/' + 'alien.png' + '"/>)');
                    //$(this).hide();
                }

//        alert(icon);
            });
            //$('#myModal2').modal('show');

        });

    });

    //change Type animal to Icons
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


    //check EMPTY INPUT
    $("#addFrom").submit(function () {
        var isFormValid = true;

        $("#id_weight").each(function () {
            if ($.trim($(this).val()).length === 0) {
                $("#id_weight").addClass("highlight");
                isFormValid = false;
            }
            else {
                $(this).removeClass("highlight");
            }
        });

        if (!isFormValid)
            alert("Please fill in all the required fields (indicated by *)");

        return isFormValid;
    });

    //notifications from client
    $.ajax({
        type: "POST",
        url: "/notification",
        dataType: "json"

    }).done(function (data) {
        if (data) {
            //alert(data);
            $("#newItemNotification").empty();
            $("#newItemNotification").append(data);
        }
    });
    // Check if body height is higher than window height :)
    $('.approveAnimal').click(function () {

        alert("A New Animal has been added!");

    });

    $('.dp3').datepicker();

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
                var showAge;
                var showGender;



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

    $("#addUpTop").append('<div id="toTop" class="btn btn-info"><span class="glyphicon glyphicon-chevron-up"></span> Наверх</div>');

    if ($("#myBody").height() >= $(window).height()) {
        $(window).scroll(function () {

            //alert("Vertical Scrollbar! D:");
            if ($(this).scrollTop() !== 0) {
                $('#toTop').fadeIn();
            } else {
                $('#toTop').fadeOut();
            }


        });
    }
    else {
        $('#toTop').hide()();
    }


    $('#toTop').click(function () {
        $("html, body").animate({scrollTop: 0}, 600);
        //alert('a22');
        if ($('#inputError').val().length === 0) {
            $('#inputError').addClass("form-control");
        }
        return false;
    });

    $('#myButton').click(function () {
        alert('aas');
        if ($('#inputError').val().length === 0) {
            $('#inputError').addClass("form-control");
        }
    });




//filter 1
    $('#filter1').change(f1);

    $('#filter2').change(f1);

    $('#filter3').change(f1);

});