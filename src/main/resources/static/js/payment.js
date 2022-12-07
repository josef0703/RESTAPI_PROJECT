$(document).ready(function () {
    // alert("테스트중");

    // var price = $('.price').val();
    // alert(price);
    // console.log(price);
    var merchant_uid;
    var name;

    $('#payment').click(function () {
        var price = $('.price').val();
        name = "영화";
        merchant_uid = "movie_" + new Date().getTime() + Math.random() * 1000000;

        alert(price);
        // alert(price);
        // console.log(price);
        var IMP = window.IMP; // 생략 가능
        IMP.init("imp65452177"); //

        IMP.request_pay({ // param
            pg: "kakao",
            // pay_method: "card",
            merchant_uid: merchant_uid,
            name: name,
            amount: price
        }, function (rsp) { // callback
            if (rsp.success) {
                var msg = '결제 성공';
                msg += "n\ 금액 : " + price;
                msg += "이름 : " + name + "uid : " + merchant_uid;

                $.ajax({
                    url: "/movie/payment/complete",
                    type: "POST",
                    data: {
                        uid: merchant_uid,
                        name: name,
                        price: price,
                    },
                    success: function (data) {
                        alert("성공-- " +msg);
                    },
                    error: function () {
                        alert("error");
                    },
                })



            } else {
                var msg = '결제 실패';
                msg += rsp.error_msg;
                // 결제 실패 시 로직,

            }
            alert(msg);
        });
    });
});