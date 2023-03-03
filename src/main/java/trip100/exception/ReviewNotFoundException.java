package trip100.exception;

public class ReviewNotFoundException extends MainException {

    private static final String MESSAGE = "리뷰가 존재하지 않습니다";

    public ReviewNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
