package trip100.exception;

import org.jboss.jandex.Main;
import trip100.domain.order.Order;

public class OrderNotFoundException extends MainException {


    private static final String MESSAGE = "주문내역이 존재하지 않습니다.";
    public OrderNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
