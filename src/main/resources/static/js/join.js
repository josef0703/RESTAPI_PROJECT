$(document).ready(function () {
    $("#btn_mail_chk").hide();
});

$(function () {
    var email_cd = {
    };
    var email_chk = false;

    // 인증번호 메일 발송
    $('#btn_email').click(function () {
        email_cd = {"email": $('#email').val()};
        alert("이메일 값 : " + email);


        if (email == '') {
            alert("이메일을 입력해주세요");
            return false;
        }

        $.ajax({
            type: "POST",
            url: "/user/mainConfirm",
            contentType: 'application/json',
            data : JSON.stringify(email_cd),
            success: function (data) {
                // alert("인증번호 발송되었습니다");
                // alert(data);
                // alert(data.email);
                email_cd = data;
                // alert(email_cd);
                $("#btn_mail_chk").show();
                $("#email_chk").prop('type', 'text');
                // alert(email_cd);
            },
            error: function (data) {
                alert(data);
                alert("메일 발송에 실패했습니다.");
            },

        })
    });
    
    // 인증번호 확인 버튼
    $('#btn_mail_chk').click(function () {

        var email_cdchk = $('#email_chk').val();
        if (email_cdchk == email_cd) {
            alert("인증 완료됐습니다!");
            email_chk = true;
        } else {
            alert("인증 실패하였습니다 다시 입력해주세요");
            
        }
    });

    //회원가입 발리데이션
    $('#join_chk').click(function () {
        var join_id = $('#id').val();
        var join_name = $('#name').val();
        var join_passwd = $('#passwd').val();

        if (join_id == "") {
            alert("아이디를 입력해주세요");
            return false;
        }
        if (join_name == "") {
            alert("이름을 입력해주세요");
            return false;
        }
        if (join_passwd == "") {
            alert("비밀번호를 입력해주세요");
            return false;
        }
        if (email_chk == false) {
            alert("이메일 인증해주세요");
            return false;
        }
        return true;

    });

});