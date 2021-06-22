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
        if ($('#title').val() == "") {
            alert("제목을 입력해주세요");
            return;
        }
        if ($('#content').val() == "") {
            alert("내용을 입력해주세요");
            return;
        }
        var form = $('#postForm');
        form.submit();
        // var data = {
        //     title: $('#title').val(),
        //     author: $('#author').val(),
        //     content: $('#content').val()
        // };
        //
        // var postForm = new FormData();
        // for(var i=0, filesTempArrLen = filesTempArr.length; i<filesTempArrLen; i++) {
        //     postForm.append("files", filesTempArr[i]);
        // }
        //
        // postForm.append("testData1", "A");
        // postForm.append("testData1", "B");
        // postForm.append("testData1", "C");
        //
        // $.ajax({
        //     type: 'post',
        //     url: '/posts/save',
        //     contentType: 'application/json; charset=UTF-8',
        //     data: JSON.stringify(data)
        // }).done(function() {
        //     alert('글이 등록되었습니다');
        // }).fail(function(error) {
        //     console.error(JSON.stringify(error));
        //     alert('다시 시도해주세요');
        // })
        // $.ajax({
        //     type: 'post',
        //     url: '/posts/imageSave',
        //     contentType: false,
        //     processData: false,
        //     data: postForm
        // }).done(function () {
        //     console.log('submit');
        // }).fail(function (error) {
        //     console.error(error);
        // });
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

$(document).ready(function() {
    $("#fileupload").on("change", addFiles);
});

var filesTempArr = [];
function addFiles(e) {
    var files = e.target.files;
    var filesArr = Array.prototype.slice.call(files);
    var filesArrLen = filesArr.length;
    var filesTempArrLen = filesTempArr.length;
    for( var i=0; i<filesArrLen; i++ ) {
        filesTempArr.push(filesArr[i]);
        $("#fileList").append("<div>" + filesArr[i].name + "<img src=\"/images/deleteImage.png\" onclick=\"deleteFile(event, " + (filesTempArrLen+i)+ ");\"></div>");
    }
    $(this).val('');
}
function deleteFile (eventParam, orderParam) {
    filesTempArr.splice(orderParam, 1);
    var innerHtmlTemp = "";
    var filesTempArrLen = filesTempArr.length;
    for(var i=0; i<filesTempArrLen; i++) {
        innerHtmlTemp += "<div>" + filesTempArr[i].name + "<img src=\"/images/deleteImage.png\" onclick=\"deleteFile(event, " + i + ");\"></div>"
    }
    $("#fileList").html(innerHtmlTemp);
}
