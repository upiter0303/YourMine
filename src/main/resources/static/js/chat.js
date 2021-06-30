
window.resizeTo(600,800);
let ws;

function wsOpen(){
    ws = new WebSocket("ws://" + location.host + "/chatsocket/" + $('#roomId').val());
    wsEvt();
}

function wsEvt() {
    ws.onopen = function(data){
        //소켓이 열리면 동작
    }

    ws.onmessage = function(data) {
        //메시지를 받으면 동작
        var msg = data.data;
        if(msg != null && msg.trim() != ''){
            var d = JSON.parse(msg);
            if(d.type == "getId"){
                var si = d.sessionId != null ? d.sessionId : "";
                if(si != ''){
                    $("#sessionId").val(si);
                }
            }else if(d.type == "message"){
                if(d.sessionId == $("#sessionId").val()){
                    $("#chatting").append("<p class='me'>나 :" + d.msg + "</p>");
                }else{
                    $("#chatting").append("<p class='others'>" + d.userName + " :" + d.msg + "</p>");
                }

            }else{
                console.warn("unknown type!")
            }
        }
    }

    document.addEventListener("keypress", function(e){
        if(e.keyCode == 13){ //enter press
            send();
        }
    });
}
function send() {
    var data = {
        type: "message",
        roomId: $("#roomId").val(),
        sessionId : $("#sessionId").val(),
        userName : $("#userName").val(),
        msg : $("#message").val()
    }
    ws.send(JSON.stringify(data));
    var chatDB = {
        speaker: data.userName,
        content: data.msg,
        roomId: data.roomId
    }
    $.ajax({
        type: 'POST',
        url: "/chat/db",
        contentType: 'application/json; charset=UTF-8',
        data: JSON.stringify(chatDB)
    }).done(function () {
        console.log("dbIn");
    }).fail(function (error) {
        console.error(error);
    });
    $('#message').val("");
}
function textLoad() {
    var roomId = $("#roomId").val();
    var userName = $("#userName").val();
    $.ajax({
        url: "/chat/db/demand/"+roomId,
        type: "get",
        contentType: 'application/json; charset=UTF-8',
        success: function(result) {
            var obj = JSON.parse(result);
            obj.forEach(function (item) {
                if (item.speaker === userName) {
                    console.log("my chat");
                    $("#chatting").append("<p class='me'>나 :" + item.content + "</p>");
                } else {
                    console.log("your chat");
                    $("#chatting").append("<p class='others'>" + item.speaker + " :" + item.content + "</p>");
                }
            })
        },
        error: function(error) {
            console.error(error);
        }
    });
}
wsOpen();
textLoad();