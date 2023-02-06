var main = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });
    },
    save : function() {
        var data = {
            userId: $('#userId').val(),
            itemId: $('#itemId').val(),
            count: $('#count').val()
        };

        $.ajax({
            type: 'POST',
            url: '/order/save',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('주문이 완료됐습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
};
main.init();

