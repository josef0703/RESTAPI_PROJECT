$(document).ready(function () {
    const content = $('#com_content').val();
    const writer = $('#user').val();
    const b_num = $('#boardnum').val();
});

$(function () {
    $('#insert_comment').click(function () {



        $.ajax({
            url: "/board/comment/" + b_num,
            type: "post",
            dataType: "json",
            data: comment_list,
            success: function (data) {
                const comment_list = "<div>" + content + "</div><hr>";
                alert(data);
                $('#comment').html(comment_list);
            },
            error: function (data) {
                alert("실패");
            },

        });

    });



});