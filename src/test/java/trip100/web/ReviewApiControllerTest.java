package trip100.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import trip100.config.auth.dto.SessionUser;
import trip100.domain.address.Address;
import trip100.domain.cart.CartRepository;
import trip100.domain.item.Item;
import trip100.domain.item.ItemRepository;
import trip100.domain.order.OrderRepository;
import trip100.domain.review.Review;
import trip100.domain.review.ReviewRepository;
import trip100.domain.user.Role;
import trip100.domain.user.User;
import trip100.domain.user.UserRepository;
import trip100.service.CartService;
import trip100.service.OrderService;
import trip100.service.ReviewService;
import trip100.web.dto.review.CreateReviewRequestDto;

import javax.persistence.EntityManager;

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
public class ReviewApiControllerTest {


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
    ReviewRepository reviewRepository;

    @Autowired
    ReviewService reviewService;



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

    }

    @Test
    @WithMockUser
    void 리뷰를_등록하면_저장된다() throws Exception {
        User user = createUser();
        SessionUser sessionUser = SessionUser.builder()
                .user(user)
                .build();
        Item item = createItem();

        int score = 10;
        String content = "리뷰내용";

        CreateReviewRequestDto dto = CreateReviewRequestDto.builder()
                .itemId(item.getId())
                .score(score)
                .content(content)
                .build();

        String json = objectMapper.writeValueAsString(dto);

        mvc.perform(post("/review/save")
                        .sessionAttr("user", sessionUser)
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());

        Review findReview = reviewRepository.findById(1L).get();
        assertThat(10).isEqualTo(findReview.getScore());
        assertThat("리뷰내용").isEqualTo(findReview.getContent());
    }

    @Test
    @WithMockUser
    void 리뷰_평균평점() throws Exception {
        User user = createUser();
        SessionUser sessionUser = SessionUser.builder()
                .user(user)
                .build();
        User user2 = createUser();
        User user3 = createUser();

        Item item = createItem();

        CreateReviewRequestDto dto1 = CreateReviewRequestDto.builder()
                .itemId(item.getId())
                .score(10)
                .content("리뷰 내용")
                .build();

        CreateReviewRequestDto dto2 = CreateReviewRequestDto.builder()
                .itemId(item.getId())
                .score(5)
                .content("리뷰 내용")
                .build();

        CreateReviewRequestDto dto3 = CreateReviewRequestDto.builder()
                .itemId(item.getId())
                .score(3)
                .content("리뷰 내용")
                .build();


        mvc.perform(post("/review/")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
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
