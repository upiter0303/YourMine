window.resizeTo(396,588);
let widthSize = 396/2;
let heightSize = 588/2;
window.moveTo(window.screen.width/2-widthSize, window.screen.height/2-heightSize);
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
        const msg = data.data;

        const chatContainer = document.getElementById("chatBoxTheme");
        const chatContainerMessage = chatContainer.getElementsByClassName("chatting")[0];

        if(msg != null && msg.trim() != ''){
            const d = JSON.parse(msg);
            let sendTime = chatTime(d.fulTime);
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
    const fulTime = new Date();

    const data = {
        type: "message",
        roomId: $("#roomId").val(),
        sessionId : $("#sessionId").val(),
        userName : $("#userName").val(),
        msg : $("#message").val(),
        listener: $('#listener').val(),
        fulTime: fulTime
    }
    ws.send(JSON.stringify(data));
    const chatDB = {
        speaker: data.userName,
        listener: data.listener,
        content: data.msg,
        roomId: data.roomId,
        fulTime: data.fulTime
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
    const now = $('#status').val();
    if (now === "거래완료") {
        $('#statusDropdown').attr("disabled", true);
    }

    const roomId = $("#roomId").val();
    const userName = $("#userName").val();
    $.ajax({
        url: "/chat/db/demand/"+roomId,
        type: "get",
        contentType: 'application/json; charset=UTF-8',
        success: function(result) {
            var obj = JSON.parse(result);
            obj.forEach(function (item) {
                if (item.speaker === userName) {
                    $("#chatting").append("<div class='me'><div class='b'></div><div class='a'><p class='me'>" + item.content + "</p></div><div class='time'>" + LoadChatTime(item.fulTime) + "</div></div>");
                } else {
                    $("#chatting").append("<div class='others'><div class='box'><div class='profile_name'>" + item.speaker + "</div><div class='a'></div><div class='b'><p class='others'>" + item.content + "</p></div><div class='time'>" + LoadChatTime(item.fulTime) + "</div></div><div>");
                }
            })
        },
        error: function(error) {
            console.error(error);
        }
    });
}

function setStandby() {
    const now = $('#status').val();
    if (now === "거래대기") {
    } else {
        const check = confirm("거래 상태를 \"거래 대기\"으로 변경하시겠습니까?");
        if (check) {
            $('#statusDropdown').empty();
            $('#statusDropdown').append("거래대기");
            $('#status').val("거래대기");
            putStatus("거래대기");
        }
    }
}

function setProgress() {
    const now = $('#status').val();
    if (now === "거래중") {
    } else {
        const check = confirm("거래 상태를 \"거래 중\"으로 변경하시겠습니까?");
        if (check) {
            $('#statusDropdown').empty();
            $('#statusDropdown').append("거래중");
            $('#status').val("거래중");
            putStatus("거래중");
        }
    }
}

function setDone() {
    const now = $('#status').val();
    if (now === "거래완료") {
    } else {
        var check = confirm("\"거래 완료\"상태로 변경하면 다시 거래 상태변경이 불가능합니다. 정말 바꾸시겠습니까?");
        if (check) {
            $('#statusDropdown').empty();
            $('#statusDropdown').append("거래완료");
            $('#status').val("거래완료");
            putStatus("거래완료");
            $('#dropdown-menu').empty();
        }
    }
}

function putStatus(item) {
    const no = $('#postNo').val();
    const data = {
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
    const data = {
        roomId: $('#roomId').val(),
        userName: $("#userName").val()
    }
    $.ajax({
        url: '/readCheck',
        type: 'put',
        contentType: 'application/json; charset=UTF-8',
        data: JSON.stringify(data)
    }).done(function () {
        console.log("read check");
    }).fail(function (error) {
        console.error(error);
    });
}

function chatOut() {
    const check = confirm("정말 채팅방을 나가시겠습니까?");
    if (check) {
        const data = {
            identify: $('#roomId').val(),
            id: $('#userName').val()
        }
        $.ajax({
            url: '/chatOut',
            type: 'put',
            contentType: 'application/json; charset=UTF-8',
            data: JSON.stringify(data)
        }).done(function () {
            window.close();
        }).fail(function (error) {
            console.error(error);
        });
    }
}
function LoadChatTime(time) {
    const now = new Date();
    if (now.getDate() === time.date.day) {
        if ((now.getMonth()+1) === time.date.month) {
            if (now.getFullYear() === time.date.year) {
                let korHour = time.time.hour + 9;
                if(korHour > 23) { korHour -= 24; }

                const ampm = (korHour>12 ?  "PM" : "AM");
                const hour = (korHour>12 ? korHour-12 : korHour);
                const min = (time.time.minute>9 ? time.time.minute : "0" + time.time.minute);
                return ampm + " " + hour + ":" + min;
            }
        }
    } else {
        let month = (time.date.month);
        let day = time.date.day;
        month = month >= 10 ? month : '0' + month;
        day = day >= 10 ? day : '0' + day;
        return month + '월 ' + day + '일';
    }
}

function sendReview() {
    const info = {
        id: $('#userName').val(),
        no: $('#no').val(),
        position: $('#position').val(),
        score: $('input[id="battery1"]:checked').val()
    };

    if($('#position').val() === 'buyer') {
        info.buyerReviewContent = $('#reviewContent').val()
    } else if($('#position').val() === 'seller') {
        info.sellerReviewContent = $('#reviewContent').val()
    }
    $.ajax({
        type: 'put',
        url: '/review/set',
        contentType: 'application/json; charset=UTF-8',
        data: JSON.stringify(info)
    }).done(function() {
        alert("리뷰가 작성되었습니다");
        window.close();
    }).fail(function(error) {
        console.error(JSON.stringify(error));
        alert('다시 시도해주세요');
    });
}
wsOpen();
readCheck();
textLoad();
