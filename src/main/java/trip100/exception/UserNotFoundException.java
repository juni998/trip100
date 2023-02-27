package trip100.exception;

import trip100.domain.user.User;

public class UserNotFoundException extends MainException {

    private static final String MESSAGE = "존재하지 않는 유저입니다.";
    public UserNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
