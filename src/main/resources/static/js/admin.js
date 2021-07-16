const numTest = /^\d{3}-\d{3,4}-\d{4}$/;

function usersModify() {
    const data = {
        name: $('#name').val(),
        phone: $('#phone').val(),
        address: $('#address').val(),
        detailAddress: $('#detailAddress').val(),
        email: $('#email').val(),
        score: $('#score').val()
    }

    const no = $('#no').val();

    $.ajax({
        type: 'PUT',
        url: '/adminPage/users/modify/' + no,
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(data)
    }).done(function() {
        alert('회원 정보가 수정되었습니다.');
        window.location.href = '/adminPage';
    }).fail(function(error) {
        alert(JSON.stringify(error));
    })
}

function usersDelete(no, role) {
    const deleteNo = no;
    const check = confirm('회원을 탈퇴시킵니다.');

    if (check == true) {
        if(role === "ADMIN") {
            alert('관리자는 탈퇴가 불가합니다.');
            window.location.href = '/adminPage';
        }
        else {
            $.ajax({
                type: 'DELETE',
                url: '/adminPage/' + deleteNo,
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
            }).done(function() {
                alert('회원 탈퇴 성공.');
                window.location.href = '/adminPage';
            }).fail(function(error) {
                alert(JSON.stringify(error));
            })
        }
    }
}

function postCode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            let addr = ''; // 주소 변수
            let extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if(data.userSelectedType === 'R'){
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraAddr !== ''){
                    extraAddr = ' (' + extraAddr + ')';
                }
                // 조합된 참고항목을 해당 필드에 넣는다.
                document.getElementById("extraAddress").value = extraAddr;
            } else {
                document.getElementById("extraAddress").value = '';
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('postcode').value = data.zonecode;
            document.getElementById("address").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("detailAddress").focus();
        }
    }).open();
}