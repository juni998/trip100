package trip100.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import trip100.domain.address.Address;
import trip100.domain.item.Item;
import trip100.domain.item.ItemRepository;
import trip100.domain.order.Order;
import trip100.domain.order.OrderRepository;
import trip100.domain.order.OrderStatus;
import trip100.domain.user.Role;
import trip100.domain.user.User;
import trip100.service.OrderService;
import trip100.web.dto.order.OrderSaveRequestDto;
import trip100.web.dto.order.OrderSearch;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class OrderApiControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    WebApplicationContext context;

    @Autowired
    EntityManager em;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }


    @Test
    @WithMockUser
    void 주문_성공() throws Exception {
        User user = createUser();
        Item item = createItem();
        int orderCount = 2;

        OrderSaveRequestDto orderSaveRequestDto = OrderSaveRequestDto.builder()
                .userId(user.getId())
                .itemId(item.getId())
                .count(orderCount)
                .build();

        String json = objectMapper.writeValueAsString(orderSaveRequestDto);

        mvc.perform(post("/order/save")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());

        Order order = orderRepository.findAll().get(0);

        assertThat(orderRepository.count()).isEqualTo(1);
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(order.getTotalPrice()).isEqualTo(10000 * 2);
        assertThat(item.getStockQuantity()).isEqualTo(98);

    }

    @Test
    @WithMockUser
    void 주문실패_재고수량초과() throws Exception {
        User user = createUser();
        Item item = createItem();
        int orderCount = 1000;

        OrderSaveRequestDto orderSaveRequestDto = OrderSaveRequestDto.builder()
                .userId(user.getId())
                .itemId(item.getId())
                .count(orderCount)
                .build();



        String json = objectMapper.writeValueAsString(orderSaveRequestDto);

        mvc.perform(post("/order/save")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @WithMockUser
    void 주문취소() throws Exception {
        User user = createUser();
        Item item = createItem();
        int orderCount = 2;

        OrderSaveRequestDto orderSaveRequestDto = OrderSaveRequestDto.builder()
                .userId(user.getId())
                .itemId(item.getId())
                .count(orderCount)
                .build();

        Long orderId = orderService.order(orderSaveRequestDto);

        mvc.perform(delete("/order/{orderId}", orderId)
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithMockUser
    void 주문취소_실패() throws Exception {
        User user = createUser();
        Item item = createItem();
        int orderCount = 2;

        OrderSaveRequestDto orderSaveRequestDto = OrderSaveRequestDto.builder()
                .userId(user.getId())
                .itemId(item.getId())
                .count(orderCount)
                .build();

        Long orderId = orderService.order(orderSaveRequestDto);

        mvc.perform(delete("/order/{orderId}", orderId + 1)
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @WithMockUser
    void 주문내역_조회() throws Exception {
        User user = createUser();
        List<Item> requestItems = IntStream.range(0, 20)
                .mapToObj(i -> Item.builder()
                        .title("아이템 - " + i)
                        .content("내용 - " + i)
                        .author("작성자 - " + i)
                        .price(100 + i)
                        .stockQuantity(100)
                        .build()
                ).collect(Collectors.toList());
        itemRepository.saveAll(requestItems);

        List<OrderSaveRequestDto> requestOrders = IntStream.range(0, 20)
                .mapToObj(i -> OrderSaveRequestDto.builder()
                        .userId(user.getId())
                        .count(1)
                        .itemId(requestItems.get(i).getId())
                        .build()
                ).collect(Collectors.toList());

        IntStream.range(0, 20).forEach(i -> orderService.order(requestOrders.get(i)));


        mvc.perform(get("/order/orders/{orderId}?page=1&size=5", user.getId())
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(5))
                .andExpect(jsonPath("[0].orderStatus").value("ORDER"))
                .andExpect(jsonPath("[0].orderItems[0].itemTitle").value("아이템 - 19"))
                .andExpect(jsonPath("[0].orderItems[0].orderPrice").value("119"))
                .andExpect(jsonPath("[4].orderItems[0].itemTitle").value("아이템 - 15"))
                .andExpect(jsonPath("[4].orderItems[0].orderPrice").value("115"))
                .andDo(print());

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
