var postMain = {
    init: function() {
        var func = this;
        $('#btn-save').on('click', function() {
            func.save();
        });

        $('#btn-postModify').on('click', function() {
            func.postModify();
        });
    },

    save : function() {
        var data = {
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val()
        };

        $.ajax({
            type: 'POST',
            url: '/posts/save',
            contentType: 'application/json; charset=UTF-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('글이 등록되었습니다');
            window.location.href='/';
        }).fail(function(error) {
            console.error(JSON.stringify(error));
            alert('다시 시도해주세요');
        });
    },

    postModify : function() {
        if ($('#title').val() == "") {
            alert("제목을 입력해주세요");
            return;
        }
        if ($('#content').val() == "") {
            alert("내용을 입력해주세요");
            return;
        }
        var data = {
            title: $('#title').val(),
            id: $('#id').val(),
            content: $('#content').val(),
            status: $('#status').val()
        }
        $.ajax({
            type: 'POST',
            url: '/posts/modify',
            contentType: 'application/json; charset=UTF-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('글이 수정되었습니다');
            window.location.href='/';
        }).fail(function(error) {
            console.error(JSON.stringify(error));
            alert('다시 시도해주세요');
        });
    }

};

postMain.init();