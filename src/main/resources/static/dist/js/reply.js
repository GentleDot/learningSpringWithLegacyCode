console.log("댓글 모듈......");

class ReplyService {

    constructor() {
        console.log("뭐냐......")
    }

    add(reply, callback, error) {
        console.log("댓글 추가......");

        $.ajax({
            type: 'post',
            url: '/replies/new',
            data: JSON.stringify(reply),
            contentType: "application/json; charset=UTF-8",
            success: function (result, status, xhr) {
                if (callback) {
                    callback(result);
                }
            },
            error: function (xhr, status, er) {
                if (error) {
                    error(er);
                }
            }
        });
    }

}
