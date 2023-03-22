var main = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });
        $('#btn-update').on('click', function () {
            _this.update();
        });
        $('#btn-delete').on('click', function () {
            _this.delete();
        });
        $('#btn-cart-save').on('click', function () {
            _this.cart();
        });
    },
    save : function() {
        var data = {
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val(),
            price: $('#price').val(),
            stockQuantity: $('#stockQuantity').val()

        };
        $.ajax({
            type: 'POST',
            url: '/item/save',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('글이 등록되었습니다.');
            window.location.href = '/item';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    update : function () {
        var data = {
            title: $('#title').val(),
            content: $('#content').val(),
            price: $('#price').val(),
            stockQuantity: $('#stockQuantity').val()

        };

        var id = $('#id').val();

        $.ajax({
            type: 'PUT',
            url: '/item/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('글이 수정되었습니다.');
            window.location.href = '/item';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    delete : function () {
        var id = $('#id').val();
        $.ajax({
            type : 'DELETE',
            url : '/item/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
        }).done(function() {
            alert('글이 삭제되었습니다.');
            window.location.href = '/item';
        }).fail(function (error){
            alert(JSON.stringify(error));
        });
    },
    cart : function () {
        var data = {
            userId: $('#userId').val(),
            itemId: $('#id').val(),
            count: $('#count').val()
        };
        $.ajax({
            type : 'POST',
            url : '/cart/save',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('장바구니에 담았습니다.');
        }).fail(function (error){
            alert(JSON.stringify(error));
        });
    },
};
main.init();