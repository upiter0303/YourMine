var numTest = /^\d{3}-\d{3,4}-\d{4}$/;

function usersModify() {
    var data = {
        name: $('#name').val(),
        phone: $('#phone').val(),
        address: $('#address').val(),
        detailAddress: $('#detailAddress').val(),
        email: $('#email').val(),
        score: $('#score').val()
    }

    var no = $('#no').val();

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