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

imageView = function imageView(att_zone, btn) {

    var attZone = document.getElementById(att_zone);
    var btnAtt = document.getElementById(btn);
    var sel_files = [];

    // 이미지와 체크 박스를 감싸고 있는 div 속성
    var div_style = 'display:inline-block;position:relative;'
        + 'width:150px;height:120px;margin:5px;border:1px solid #00f;z-index:1';
    // 미리보기 이미지 속성
    var img_style = 'width:100%;height:100%;z-index:none';
    // 이미지안에 표시되는 체크박스의 속성
    var chk_style = 'width:30px;height:30px;position:absolute;font-size:24px;'
        + 'right:0px;bottom:0px;z-index:999;background-color:rgba(255,255,255,0.1);color:#f00';

    btnAtt.onchange = function (e) {
        var files = e.target.files;
        var fileArr = Array.prototype.slice.call(files)
        for (f of fileArr) {
            imageLoader(f);
        }
    }
// 탐색기에서 드래그앤 드롭 사용
    attZone.addEventListener('dragenter', function (e) {
        e.preventDefault();
        e.stopPropagation();
    }, false)

    attZone.addEventListener('dragover', function (e) {
        e.preventDefault();
        e.stopPropagation();

    }, false)

    attZone.addEventListener('drop', function (e) {
        var files = {};
        e.preventDefault();
        e.stopPropagation();
        var dt = e.dataTransfer;
        files = dt.files;
        for (f of files) {
            imageLoader(f);
        }

    }, false)
    /*첨부된 이미리즐을 배열에 넣고 미리보기 */
    imageLoader = function (file) {
        sel_files.push(file);
        var reader = new FileReader();
        reader.onload = function (ee) {
            let img = document.createElement('img')
            img.setAttribute('style', img_style)
            img.src = ee.target.result;
            attZone.appendChild(makeDiv(img, file));
        }

        reader.readAsDataURL(file);
    }

    /*첨부된 파일이 있는 경우 checkbox와 함께 attZone에 추가할 div를 만들어 반환 */
    makeDiv = function (img, file) {
        var div = document.createElement('div')
        div.setAttribute('style', div_style)

        var btn = document.createElement('input')
        btn.setAttribute('type', 'button')
        btn.setAttribute('value', 'x')
        btn.setAttribute('delFile', file.name);
        btn.setAttribute('style', chk_style);
        btn.onclick = function (ev) {
            var ele = ev.srcElement;
            var delFile = ele.getAttribute('delFile');
            for (var i = 0; i < sel_files.length; i++) {
                if (delFile == sel_files[i].name) {
                    sel_files.splice(i, 1);
                }
            }

            dt = new DataTransfer();
            for (f in sel_files) {
                var file = sel_files[f];
                dt.items.add(file);
            }
            btnAtt.files = dt.files;
            var p = ele.parentNode;
            attZone.removeChild(p)
        }
        div.appendChild(img)
        div.appendChild(btn)
        return div
    }
}
imageView('att_zone', 'image')