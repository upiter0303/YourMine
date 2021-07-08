window.resizeTo(720,800);
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
            var sendTime = d.sendTime;
            if(d.type == "getId"){
                var si = d.sessionId != null ? d.sessionId : "";
                if(si != ''){
                    $("#sessionId").val(si);
                }
            }else if(d.type == "message"){
                if(d.sessionId == $("#sessionId").val()){
                    $("#chatting").append("<div class='me'><div class='b'></div><div class='a'><p class='me'>" + d.msg + "</p></div><div class='time'>" + sendTime + "</div></div>");
                }else{
                    $("#chatting").append("<div class='others'><div class='box'><div class='profile_name'>" + d.userName + "</div><div class='a'></div><div class='b'><p class='others'>" + d.msg + "</p></div><div class='time'>" + sendTime + "</div></div></div>");
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
    // Setting a sending time
    var nowTime = new Date();
    var ampm = (nowTime.getHours()>12 ?  "PM" : "AM");
    var hour = (nowTime.getHours()>12 ? nowTime.getHours()-12 : nowTime.getHours());
    var min = (nowTime.getMinutes()>9 ? nowTime.getMinutes() : "0" + nowTime.getMinutes());
    var sendTime = ampm + " " + hour +":" + min;

    var data = {
        type: "message",
        roomId: $("#roomId").val(),
        sessionId : $("#sessionId").val(),
        userName : $("#userName").val(),
        msg : $("#message").val(),
        listener: $('#listener').val(),
        sendTime: sendTime
    }
    ws.send(JSON.stringify(data));
    var chatDB = {
        speaker: data.userName,
        listener: data.listener,
        content: data.msg,
        roomId: data.roomId,
        sendTime: data.sendTime
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
    var now = $('#status').val();
    if (now === "거래완료") {
        $('#statusDropdown').attr("disabled", true);
    }

    var roomId = $("#roomId").val();
    var userName = $("#userName").val();

    readCheck();
    $.ajax({
        url: "/chat/db/demand/"+roomId,
        type: "get",
        contentType: 'application/json; charset=UTF-8',
        success: function(result) {
            var obj = JSON.parse(result);
            obj.forEach(function (item) {
                if (item.speaker === userName) {
                    $("#chatting").append("<div class='me'><div class='b'></div><div class='a'><p class='me'>" + item.content + "</p></div><div class='time'>" + item.sendTime + "</div></div>");
                } else {
                    $("#chatting").append("<div class='others'><div class='box'><div class='profile_name'>" + item.speaker + "</div><div class='a'></div><div class='b'><p class='others'>" + item.content + "</p></div><div class='time'>" + item.sendTime + "</div></div><div>");
                }
            })
        },
        error: function(error) {
            console.error(error);
        }
    });
}

function setStandby() {
    var now = $('#status').val();
    if (now === "거래대기") {
    } else {
        var check = confirm("거래 상태를 \"거래 대기\"으로 변경하시겠습니까?");
        if (check) {
            $('#statusDropdown').val("거래 대기");
            $('#status').val("거래대기");
            putStatus("거래대기");
        }
    }
}

function setProgress() {
    var now = $('#status').val();
    if (now === "거래중") {
    } else {
        var check = confirm("거래 상태를 \"거래 중\"으로 변경하시겠습니까?");
        if (check) {
            $('#statusDropdown').val("거래 중");
            $('#status').val("거래중");
            putStatus("거래중");
        }
    }
}

function setDone() {
    var now = $('#status').val();
    if (now === "거래완료") {
    } else {
        var check = confirm("\"거래 완료\"상태로 변경하면 다시 거래 상태변경이 불가능합니다. 정말 바꾸시겠습니까?");
        if (check) {
            $('#statusDropdown').val("거래 완료");
            $('#status').val("거래완료");
            putStatus("거래완료");
            $('#statusDropdown').attr("disabled", true);
        }
    }
}

function putStatus(item) {
    var no = $('#postNo').val();
    var data = {
        seller: $('#userName').val(),
        roomId: $('#roomId').val()
    }
    $.ajax({
        type: 'put',
        url: "/post/status/"+no+"/"+item,
        contentType: 'application/json; charset=UTF-8',
        data: JSON.stringify(data)
    }).done(function () {
        console.log("done");
    }).fail(function (error) {
        console.error(error);
    });
}

function readCheck() {
    var data = {
        roomId: $('#roomId').val(),
        userName: $("#userName").val()
    }
    $.ajax({
        type: 'put',
        url: "/chat/db/check",
        contentType: 'application/json; charset=UTF-8',
        data: JSON.stringify(data)
    }).done(function () {
        console.log("read check");
    }).fail(function (error) {
        console.error(error);
    });
}


wsOpen();
textLoad();
