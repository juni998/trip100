package trip100.web.dto.order;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import trip100.domain.order.Order;
import trip100.domain.order.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderResponseDto {

    private Long orderId;

    private LocalDateTime orderDate;

    private OrderStatus orderStatus;

    private List<OrderItemDto> orderItems;

    public OrderResponseDto(Order order) {
        orderId = order.getId();
        orderDate = order.getOrderDate();
        orderStatus = order.getOrderStatus();
        orderItems = order.getOrderItems().stream()
                .map(orderItem -> new OrderItemDto(orderItem))
                .collect(Collectors.toList());
    }
}
