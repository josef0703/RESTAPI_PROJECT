$(document).ready(function () {
    // alert("테스트중");

    // var price = $('.price').val();
    // alert(price);
    // console.log(price);

    $('#payment').click(function () {
        var price = $('.price').val();
        alert(price);
        // alert(price);
        // console.log(price);
        var IMP = window.IMP; // 생략 가능
        // IMP.init("번호 입력"); //

        IMP.request_pay({ // param
            pg: "kakao",
            // pay_method: "card",
            merchant_uid: "movie_" + new Date().getTime(),
            name: "영화",
            amount: price

            // buyer_email: "gildong@gmail.com",
            // buyer_name: "홍길동",
            // buyer_tel: "010-4242-4242",
            // buyer_addr: "서울특별시 강남구 신사동",
            // buyer_postcode: "01181"
        }, function (rsp) { // callback
            if (rsp.success) {
                var msg = '결제 성공';
                // 결제 성공 시 로직,ㅋ

            } else {
                var msg = '결제 실패';
                msg += rsp.error_msg;
                // 결제 실패 시 로직,

            }
            alert(msg);
        });
    });
});