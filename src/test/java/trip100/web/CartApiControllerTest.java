package trip100.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import trip100.config.auth.LoginUserArgumentResolver;
import trip100.config.auth.dto.SessionUser;
import trip100.domain.address.Address;
import trip100.domain.cart.Cart;
import trip100.domain.cart.CartRepository;
import trip100.domain.item.Item;
import trip100.domain.item.ItemRepository;
import trip100.domain.order.OrderRepository;
import trip100.domain.user.Role;
import trip100.domain.user.User;
import trip100.domain.user.UserRepository;
import trip100.exception.UserNotFoundException;
import trip100.service.CartService;
import trip100.service.OrderService;
import trip100.web.dto.cart.AddCartRequestDto;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
public class CartApiControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private OrderService orderService;

    @Autowired
    WebApplicationContext context;

    @Autowired
    EntityManager em;

    @Autowired
    CartService cartService;

    @Autowired
    CartRepository cartRepository;


    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @BeforeEach
    public void clear() {
        itemRepository.deleteAll();
        userRepository.deleteAll();
        cartRepository.deleteAll();
    }

    @Test
    @WithMockUser
    void 카트에_상품이_저장된다() throws Exception {
        User user = createUser();
        SessionUser sessionUser = SessionUser.builder()
                .user(user)
                .build();

        Item item = createItem();

        AddCartRequestDto dto = AddCartRequestDto.builder()
                .userId(user.getId())
                .itemId(item.getId())
                .count(3)
                .build();

        String json = objectMapper.writeValueAsString(dto);


        mvc.perform(post("/cart/save")
                        .sessionAttr("user", sessionUser)
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());

        Cart getCart = cartRepository.findAll().get(0);
        assertThat(getCart.getItem().getTitle()).isEqualTo("제목");
        assertThat(getCart.getItem().getContent()).isEqualTo("내용");
        assertThat(getCart.getItem().getAuthor()).isEqualTo("판매자");
        assertThat(getCart.getCount()).isEqualTo(3);

    }

    @Test
    @WithMockUser
    void 카트에_여러_상품이_조회된다() throws Exception {
        User user = createUser();
        SessionUser sessionUser = SessionUser.builder()
                .user(user)
                .build();

        List<Item> requestItems = IntStream.range(0, 5)
                .mapToObj(i -> Item.builder()
                        .title("제목 - " + i)
                        .content("내용 -" + i)
                        .price(10000 + i)
                        .stockQuantity(100 + i)
                        .build())
                .collect(Collectors.toList());

        itemRepository.saveAll(requestItems);

        List<AddCartRequestDto> dtoList = IntStream.range(0, 5)
                .mapToObj(i -> AddCartRequestDto.builder()
                        .userId(user.getId())
                        .itemId(requestItems.get(i).getId())
                        .count(1)
                        .build())
                .collect(Collectors.toList());


        IntStream.range(0, 5).forEach(i -> cartService.addItem(dtoList.get(i)));


        mvc.perform(get("/cart/list")
                        .sessionAttr("user", sessionUser)
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(5))
                .andExpect(jsonPath("[0].title").value("제목 - 0"))
                .andExpect(jsonPath("[0].price").value(10000))
                .andExpect(jsonPath("[4].title").value("제목 - 4"))
                .andExpect(jsonPath("[4].price").value(10004))
                .andDo(print());


    }

    @Test
    @WithMockUser
    void 저장된_아이템을_장바구니에서_삭제한다() throws Exception {
        User user = createUser();
        Item item = createItem();

        AddCartRequestDto dto = AddCartRequestDto.builder()
                .userId(user.getId())
                .itemId(item.getId())
                .count(3)
                .build();

        cartService.addItem(dto);

        mvc.perform(delete("/cart/delete/{id}", 1L)
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());

        assertThat(cartRepository.findAll().size()).isEqualTo(0);
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
                .author("판매자")
                .price(10000)
                .stockQuantity(100)
                .build();
        em.persist(item);
        return item;
    }
}
