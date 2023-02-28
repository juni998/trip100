package trip100.domain.order;

import trip100.web.dto.order.OrderItemDto;
import trip100.web.dto.order.OrderResponseDto;
import trip100.web.dto.order.OrderSearch;

import java.util.List;

public interface OrderRepositoryCustom {

    List<Order> findOrders(Long userId, OrderSearch orderSearch);

}
