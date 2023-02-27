package trip100.service.order;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import trip100.domain.address.Address;
import trip100.domain.item.Item;
import trip100.domain.order.Order;
import trip100.domain.order.OrderRepository;
import trip100.domain.order.OrderStatus;
import trip100.domain.user.Role;
import trip100.domain.user.User;
import trip100.exception.NotEnoughStockException;
import trip100.exception.OrderNotFoundException;
import trip100.service.OrderService;
import trip100.web.dto.order.OrderSaveRequestDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    void 주문_성공() {
        User user = createUser();
        Item item = createItem();
        int orderCount = 2;

        OrderSaveRequestDto orderSaveRequestDto = OrderSaveRequestDto.builder()
                .userId(user.getId())
                .itemId(item.getId())
                .count(orderCount)
                .build();

        orderService.order(orderSaveRequestDto);

        List<Order> all = orderRepository.findAll();
        Order getOrder = all.get(0);

        assertThat(getOrder.getOrderStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(getOrder.getTotalPrice()).isEqualTo(10000 * 2);
        assertThat(item.getStockQuantity()).isEqualTo(98);

    }

    @Test
    void 상품주문_재고수량초과(){
        User user = createUser();
        Item item = createItem();
        int orderCount = 101;

        OrderSaveRequestDto orderSaveRequestDto = OrderSaveRequestDto.builder()
                .userId(user.getId())
                .itemId(item.getId())
                .count(orderCount)
                .build();

        assertThatThrownBy(() -> orderService.order(orderSaveRequestDto))
                .isInstanceOf(NotEnoughStockException.class);
    }

    @Test
    void 주문취소() {
        User user = createUser();
        Item item = createItem();
        int orderCount = 2;

        OrderSaveRequestDto orderSaveRequestDto = OrderSaveRequestDto.builder()
                .userId(user.getId())
                .itemId(item.getId())
                .count(orderCount)
                .build();

        Long orderId = orderService.order(orderSaveRequestDto);

        orderService.cancelOrder(orderId);

        Order getOrder = orderRepository.findById(orderId).get();

        assertThat(getOrder.getOrderStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(item.getStockQuantity()).isEqualTo(100);

    }

    @Test
    void 주문취소_실패() {
        User user = createUser();
        Item item = createItem();
        int orderCount = 2;

        OrderSaveRequestDto orderSaveRequestDto = OrderSaveRequestDto.builder()
                .userId(user.getId())
                .itemId(item.getId())
                .count(orderCount)
                .build();

        Long orderId = orderService.order(orderSaveRequestDto);

        assertThatThrownBy(() -> orderService.cancelOrder(orderId + 1))
                .isInstanceOf(OrderNotFoundException.class);
    }


    private User createUser() {
        User user = User.builder()
                .name("이름")
                .email("aaa@gmail.com")
                .role(Role.USER)
                .address(new Address("서울", "은평구", "123-123"))
                .build();

        em.persist(user);

        return user;
    }

    private Item createItem() {
        Item item = Item.builder()
                .title("제목")
                .content("내용")
                .price(10000)
                .stockQuantity(100)
                .build();
        em.persist(item);
        return item;
    }
}