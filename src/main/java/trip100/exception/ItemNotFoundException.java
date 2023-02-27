package trip100.exception;

public class ItemNotFoundException extends MainException {

    private static final String MESSAGE = "존재하지 않는 상품입니다.";
    public ItemNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
