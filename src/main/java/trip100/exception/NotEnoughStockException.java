package trip100.exception;

public class NotEnoughStockException extends MainException {

    private static final String MESSAGE = "재고 수량이 부족합니다.";

    public NotEnoughStockException() {
        super(MESSAGE);
    }

    public int getStatusCode() {
        return 404;
    }
}