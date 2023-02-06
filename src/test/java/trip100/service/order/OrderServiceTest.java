package trip100.service.order;

import org.assertj.core.api.Assertions;
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
import trip100.service.item.ItemService;
import trip100.web.dto.item.ItemSaveRequestDto;
import trip100.web.dto.order.OrderSaveRequestDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

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

        assertThat(OrderStatus.ORDER).isEqualTo(getOrder.getStatus());
        assertThat(1).isEqualTo(getOrder.getOrderItems().size());
        assertThat(10000 * 2).isEqualTo(getOrder.getTotalPrice());
        assertThat(98).isEqualTo(item.getStockQuantity());

    }

    @Test
    void 상품주문_재고수량초과() throws Exception {
        User user = createUser();
        Item item = createItem();
        int orderCount = 101;

        OrderSaveRequestDto orderSaveRequestDto = OrderSaveRequestDto.builder()
                .userId(user.getId())
                .itemId(item.getId())
                .count(orderCount)
                .build();

        assertThatThrownBy(() -> orderService.order(orderSaveRequestDto))
                .isInstanceOf(NotEnoughStockException.class)
                .hasMessageContaining("재고수량이 부족합니다");
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

        assertThat(OrderStatus.CANCEL).isEqualTo(getOrder.getStatus());
        assertThat(100).isEqualTo(item.getStockQuantity());

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