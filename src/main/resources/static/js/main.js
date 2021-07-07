var offcanvasElementList = [].slice.call(document.querySelectorAll('.offcanvas'))
var offcanvasList = offcanvasElementList.map(function (offcanvasEl) {
    return new bootstrap.Offcanvas(offcanvasEl)
})

let search = function search() {
    window.location.href="/request/search/0/" + $('#keyword').val();
}

let getter = function getter() {
    let data = {
        kind: $('#kind').val(),
        value: $('#value').val(),
        cursor: $('#cursor').val(),
        id: $('#id').val()
    }
    $.ajax({
        url: "/request/list",
        type: "post",
        contentType: 'application/json; charset=UTF-8',
        data: JSON.stringify(data),
        success: function(result) {
            $("#cursor").val(function(i, val) {
                return val + 1;
            });
            let obj = JSON.parse(result);
            let i = 1;
            obj.forEach(function (item) {
                $("#board").append("<div class=\"col mb-5\">\n" +
                    "                        <div class=\"card h-100\">\n" +
                    "                            <a href=\"/posts/" + item.id + "\">\n" +
                    "                                <img src=\"/postImage/" + item.thumbnail + "\" class=\"card-img-top\" alt=\"" + item.title + "\" width=\"200px\" height=\"200px\">\n" +
                    "                            </a>\n" +
                    "                            <div class=\"card-body p-4\">\n" +
                    "                                <div class=\"text-center\">\n" +
                    "                                    <h5 class=\"fw-bolder\">" + item.title + "</h5>\n" +
                    "                                    <p>" + item.price + "원</p>\n" +
                    "                                    <p>" + item.status + "</p>\n" +
                    "                                </div>\n" +
                    "                            </div>\n" +
                    "                        </div>\n" +
                    "                    </div>");
                i++;
                if (result.length === i) {
                    $('#id').val(item.id);
                }
            })
        },
        error: function(error) {
            console.error(error);
        }
    });
}

function alarm() {
    var alarm = $('#alarm').val();
    if (alarm !== null) {
        $.ajax({
            url: "/alarm/" + alarm,
            type: "get",
            contentType: 'application/json; charset=UTF-8',
            success: function (result) {
                if (result) {
                    alert("new mes");
                }
            },
            error: function (error) {
                console.error(error);
            }
        });
    }
}
alarm();