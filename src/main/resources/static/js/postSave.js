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
        var limit = 10;
        var files = e.target.files;
        var fileArr = Array.prototype.slice.call(files)
        if (fileArr.length > limit) {
            alert("파일은 최대 10개까지만 업로드 가능합니다");
            return;
        }
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
        dt = new DataTransfer();
        for (f in sel_files) {
            var file = sel_files[f];
            dt.items.add(file);
        }
        btnAtt.files = dt.files;
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
imageView('att_zone', 'formFileMultiple')



var mapContainer = document.getElementById('map'), // 지도를 표시할 div
    mapOption = {
        center: new daum.maps.LatLng(37.537187, 127.005476), // 지도의 중심좌표
        level: 5 // 지도의 확대 레벨
    };

//지도를 미리 생성
var map = new daum.maps.Map(mapContainer, mapOption);
//주소-좌표 변환 객체를 생성
var geocoder = new daum.maps.services.Geocoder();
//마커를 미리 생성
var marker = new daum.maps.Marker({
    position: new daum.maps.LatLng(37.537187, 127.005476),
    map: map
});


function findAddress() {
    new daum.Postcode({
        oncomplete: function(data) {
            var addr = data.address; // 최종 주소 변수

            // 주소 정보를 해당 필드에 넣는다.
            document.getElementById("area").value = addr;
            // 주소로 상세 정보를 검색
            geocoder.addressSearch(data.address, function(results, status) {
                // 정상적으로 검색이 완료됐으면
                if (status === daum.maps.services.Status.OK) {

                    var result = results[0]; //첫번째 결과의 값을 활용

                    // 해당 주소에 대한 좌표를 받아서
                    var coords = new daum.maps.LatLng(result.y, result.x);
                    // 지도를 보여준다.
                    mapContainer.style.display = "block";
                    map.relayout();
                    // 지도 중심을 변경한다.
                    map.setCenter(coords);
                    // 마커를 결과값으로 받은 위치로 옮긴다.
                    marker.setPosition(coords)
                }
            });
        }
    }).open();
}