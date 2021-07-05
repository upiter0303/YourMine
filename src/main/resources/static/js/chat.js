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

        var chatContainer = document.getElementById("chatBoxTheme");
        var chatContainerMessage = chatContainer.getElementsByClassName("chatting")[0];

        if(msg != null && msg.trim() != ''){
            var d = JSON.parse(msg);
            if(d.type == "getId"){
                var si = d.sessionId != null ? d.sessionId : "";
                if(si != ''){
                    $("#sessionId").val(si);
                }
            }else if(d.type == "message"){
                if(d.sessionId == $("#sessionId").val()){
                    $("#chatting").append("<div class='me'><div class='b'></div><div class='a'><p class='me'>" + d.msg + "</p></div></div>");
                }else{
                    $("#chatting").append("<div class='others'><div class='box'><div class='profile_name'>" + d.userName + "</div><div class='a'></div><div class='b'><p class='others'>" + d.msg + "</p></div></div></div>");
                }

            }else{
                console.warn("unknown type!")
            }
        }

        chatContainerMessage.scrollTop = chatContainerMessage.scrollHeight;
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
                    $("#chatting").append("<div class='me'><div class='b'></div><div class='a'><p class='me'>" + item.content + "</p></div></div>");
                } else {
                    $("#chatting").append("<div class='others'><div class='box'><div class='profile_name'>" + item.speaker + "</div><div class='a'></div><div class='b'><p class='others'>" + item.content + "</p></div></div><div>");
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