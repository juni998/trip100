package trip100.service.order;

import lombok.RequiredArgsConstructor;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import trip100.domain.delivery.Delivery;
import trip100.domain.item.Item;
import trip100.domain.item.ItemRepository;
import trip100.domain.order.Order;
import trip100.domain.order.OrderItem;
import trip100.domain.order.OrderRepository;
import trip100.domain.user.User;
import trip100.domain.user.UserRepository;
import trip100.web.dto.order.OrderSaveRequestDto;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public Long order(OrderSaveRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId()).orElseThrow(
                () -> new IllegalArgumentException("해당 유저는 존재하지 않습니다.")
        );
        Item item = itemRepository.findById(requestDto.getItemId()).orElseThrow(
                () -> new IllegalArgumentException("해당 아이템이 없습니다.")
        );

        Delivery delivery = Delivery.builder().address(user.getAddress()).build();

        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), requestDto.getCount());

        Order order = Order.createOrder(user, delivery, orderItem);

        orderRepository.save(order);

        return order.getId();
    }

    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new IllegalArgumentException("해당 유저는 존재하지 않습니다.")
        );
        order.cancel();
    }

}
