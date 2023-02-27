package trip100.exception;

import trip100.domain.cart.Cart;

public class CartNotFoundException extends MainException {


    private static final String MESSAGE = "카트에 상품이 존재하지 않습니다.";
    public CartNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
