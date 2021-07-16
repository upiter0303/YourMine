const numberTest = /[0-9]/g;
const postMain = {
    init: function() {
        const func = this;
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
        let checkList = "";
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
        let ofSize = null;
        if ($('#size3').val() !== "") {
            if ($('#size2').val() !== "") {
                if ($('#size1').val() !== "") {
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
            const ele = document.getElementById('att_zone');
            const eleCount = ele.childElementCount;
            if (!$('#formFileMultiple').val() && eleCount === 0) {
                alert("사진을 하나 이상 첨부하셔야 합니다");
                return;
            } else {
                if ($('#toDelFile').val() !== "") {
                    const fileName = $('#toDelFile').val();
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
        }
        const form = $('#postForm');
        form.submit();
    },

    postDelete: function () {
        const check = confirm("정말 삭제하시겠습니까?");
        if (check) {
            const form = $('#deleteForm');
            form.submit();
        } else {

        }
    },

    attention: function () {
        const info = {
            userId: $('#userId').val(),
            postNo: $('#postNo').val()
        };
        if (info.userId == "guest") {
            const login = confirm("로그인한 사용자만 가능합니다. 로그인 하시겠습니까?");
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
        const userId = $('#userId').val();
        const postNo = $('#postNo').val();
        if (userId == "guest") {
            const login = confirm("로그인한 사용자만 가능합니다. 로그인 하시겠습니까?");
            if (login) {
                window.location.href="/loginPage";
            } else {
                return;
            }
        }
        window.open("/chat/" + postNo + "/" + userId, "", "_blank");
    }
};
postMain.init();