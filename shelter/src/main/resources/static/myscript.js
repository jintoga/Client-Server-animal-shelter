
$(document).ready(function () {

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

        alert("I approve this shit");

    });

    $('.dp3').datepicker();

    function f1() {
        $('.getRidOfIt').hide();

        v1 = $("#filter1").val();
        v2 = $("#filter2").val();
        v3 = $("#filter3").val();


//        alert(v1);
//        alert(v2);
//        alert(v3);

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
                //alert(showAge);

                $("#message").append('<div class="col-sm-6 col-md-4 myfilter getRid">'
                        + '<form class="getRidOfIt" method="GET" action="/animals/' + data[item].pk_animal + '">'
                        + '<button class="btn-info btn-sm"> Подробнее </button>'
                        + '</form>'
                        + '<div id="id_thumbnail" class="thumbnail"'
                        + 'href="/animals/' + data[item].pk_animal + '">'
                        + '<img class="img-responsive" height="350" width="500" src="/img/animals/' + data[item].pk_animal + '"/>'
                        + '<div id="id_caption" class="caption">'
                        + ' <center><h3>Вид: <span class="myfilter2">'
                        + data[item].type_animal + '</span></h3></center> '
                        + '<center><h4>Имя: <strong><span>'
                        + data[item].name + '</span></strong></h4></center>'
                        + '<center><h6>Возраст: <span>' + showAge
                        + '</span></h6></center>'
                        + '</div>' + '</div></div>');
            }
            var n = $(document).height();
            $('html, body').animate({scrollTop: n}, 1000);

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