package trip100.service;

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
import trip100.exception.ItemNotFoundException;
import trip100.exception.OrderNotFoundException;
import trip100.exception.UserNotFoundException;
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
        //엔티티 조회
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(UserNotFoundException::new);
        Item item = itemRepository.findById(requestDto.getItemId())
                .orElseThrow(ItemNotFoundException::new);

        //배송정보 생성
        Delivery delivery = Delivery.builder().address(user.getAddress()).build();

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), requestDto.getCount());


        //주문 생성
        Order order = Order.createOrder(user, delivery, orderItem);



        //주문 저장
        orderRepository.save(order);
        orderItem.addOrder(order);

        return order.getId();
    }

    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(OrderNotFoundException::new);

        order.cancel();
    }

}
