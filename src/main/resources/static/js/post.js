var numberTest = /[0-9]/g;
var postMain = {
    init: function() {
        var func = this;
        $('#btn-save').on('click', function() {
            func.save();
        });

        $('#btn-postDelete').on('click', function() {
            func.postDelete();
        });

        $('#btn-attention').on('click', function() {
            func.attention();
        });

        $('#btn-chatOpen').on('click', function() {
            func.chatOpen();
        });

        $('#btn-setReview').on('click', function() {
            func.setReview();
        });
    },

    save : function() {
        if ($('#title').val() === "") {
            alert("제목을 입력해주세요");
            $('#title').focus();
            return;
        }
        if ($('#category').val() === 'x') {
            alert("카테고리를 선택해주세요");
            $('#category').focus();
            return;
        }
        if ($('#price').val() === "") {
            alert("가격을 입력해주세요");
            $('#price').focus();
            return;
        }
        if(!numberTest.test($('#price').val())) {
            alert("가격에는 숫자만 입력 가능합니다");
            $('#price').focus();
            return;
        }
        var checkList = "";
        if ($('#direct').is(':checked')) {
            checkList = "A";
        }
        if ($('#delivery').is(':checked')) {
            checkList += "B";
        }
        if ($('#direct').is(":checked") === false && $('#delivery').is(':checked') === false) {
            alert("거래방식은 최소 한개 이상 선택하셔야 합니다");
            $('#check1').focus();
            return;
        } else {
            $('#way').val(checkList);
        }
        var ofSize = null;
        if ($('#size3').val() !== "") {
            if(!numberTest.test($('#size3').val())) {
                alert("숫자만 입력 가능합니다");
                $('#size3').focus();
                return;
            }
            if ($('#size2').val() !== "") {
                if(!numberTest.test($('#size2').val())) {
                    alert("숫자만 입력 가능합니다");
                    $('#size2').focus();
                    return;
                }
                if ($('#size1').val() !== "") {
                    if(!numberTest.test($('#size1').val())) {
                        alert("숫자만 입력 가능합니다");
                        $('#size1').focus();
                        return;
                    }
                    ofSize = $('#size1').val();
                } else {
                    alert("\'가로\'부터 채워주세요");
                    return;
                }
                ofSize += "*"+$('#size2').val();
            } else {
                alert("\'세로\'부터 채워주세요");
                return;
            }
            ofSize += "*"+$('#size3').val();
            $('#ofSize').val(ofSize);
        }
        if ($('#content').val() === "") {
            alert("내용을 입력해주세요");
            $('#content').focus();
            return;
        }
        if ($('#subType').val() === "save") {
            if (!$('#formFileMultiple').val()) {
                alert("사진을 하나 이상 첨부하셔야 합니다");
                return;
            }
        } else {
            var ele = document.getElementById('att_zone');
            var eleCount = ele.childElementCount;
            if (!$('#formFileMultiple').val() && eleCount === 0) {
                alert("사진을 하나 이상 첨부하셔야 합니다");
                return;
            } else {
                var fileName = $('#toDelFile').val();
                $.ajax({
                    type: 'delete',
                    url: '/post/files/del',
                    data: {
                        fileName: fileName
                    },
                    contentType: "application/x-www-form-urlencoded; charset=UTF-8"
                }).done(function() {
                    console.log("del file")
                }).fail(function(error) {
                    console.error(JSON.stringify(error));
                });
            }
        }
        var form = $('#postForm');
        form.submit();
    },

    postDelete: function () {
        var check = confirm("정말 삭제하시겠습니까?");
        if (check) {
            var form = $('#deleteForm');
            form.submit();
        } else {

        }
    },

    attention: function () {
        var info = {
            userId: $('#userId').val(),
            postNo: $('#postNo').val()
        };
        if (info.userId == "guest") {
            var login = confirm("로그인한 사용자만 가능합니다. 로그인 하시겠습니까?");
            if (login) {
                window.location.href="/loginPage";
            } else {
                return;
            }
        }
        $.ajax({
            type: 'POST',
            url: '/attention/button',
            contentType: 'application/json; charset=UTF-8',
            data: JSON.stringify(info)
        }).done(function(result) {
            if (result) {
                alert("찜하기 완료!");
            } else {
                alert("찜 목록에서 삭제되었습니다");
            }
        }).fail(function(error) {
            console.error(JSON.stringify(error));
            alert('다시 시도해주세요');
        });
    },

    chatOpen: function () {
        var userId = $('#userId').val();
        var postNo = $('#postNo').val();
        if (userId == "guest") {
            var login = confirm("로그인한 사용자만 가능합니다. 로그인 하시겠습니까?");
            if (login) {
                window.location.href="/loginPage";
            } else {
                return;
            }
        }
        window.open("/chat/" + postNo + "/" + userId, "", "_blank");
    },

    setReview: function () {
        var info = {
            id: $('#id').val(),
            no: $('#no').val(),
            position: $('#position').val(),
            score: $('#score').val()
        };
        $.ajax({
            type: 'put',
            url: '/review/set',
            contentType: 'application/json; charset=UTF-8',
            data: JSON.stringify(info)
        }).done(function() {
            alert("리뷰가 작성되었습니다");
            window.location.href="/myPage";
        }).fail(function(error) {
            console.error(JSON.stringify(error));
            alert('다시 시도해주세요');
        });
    }

};
postMain.init();