postModify = function postModify() {
    var attZone = $('#att_zone');
    var btnAtt = $('#formFileMultiple');
    let img = document.createElement('img');
    img.setAttribute('style', 'width:100%;height:100%;z-index:none');
    $.ajax({
        type: "get",
        url: "/post/files/"+$('#id').val(),
        success: function (list) {
            console.log("getList");
            list.forEach(function (item) {
                img.src = "/postImage/"+item;
                console.log(item);
                attZone.appendChild(makeImg(img, item));
            })
        }
    });

    makeImg = function makeImg(img, item) {
        var div = document.createElement('div')
        div.setAttribute('style', 'display:inline-block;position:relative;'
            + 'width:150px;height:120px;margin:5px;border:1px solid #00f;z-index:1')

        var btn = document.createElement('input')
        btn.setAttribute('type', 'button')
        btn.setAttribute('value', 'x')
        btn.setAttribute('delFile', item);
        btn.setAttribute('style', 'width:30px;height:30px;position:absolute;font-size:24px;'
            + 'right:0px;bottom:0px;z-index:999;background-color:rgba(255,255,255,0.1);color:#f00');
        btn.onclick = function (ev) {
            console.log("!@#");
            var ele = ev.srcElement;
            var delFile = ele.getAttribute('delFile');
            var p = ele.parentNode;
            attZone.removeChild(p)
        }

        div.appendChild(img)
        div.appendChild(btn)
        return div
    }
};
postModify();