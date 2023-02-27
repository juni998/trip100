package trip100.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import trip100.domain.address.Address;
import trip100.domain.item.Item;
import trip100.domain.review.Review;
import trip100.domain.review.ReviewRepository;
import trip100.domain.user.Role;
import trip100.domain.user.User;
import trip100.web.dto.review.CreateReviewRequestDto;

import javax.persistence.EntityManager;

import java.util.OptionalDouble;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ReviewServiceTest {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ReviewService reviewService;

    @Autowired
    EntityManager em;


    @Test
    void 리뷰를_등록하면_저장된다() {
        User user = createUser();
        Item item = createItem();

        int score = 10;

        String content = "리뷰내용";

        CreateReviewRequestDto dto = CreateReviewRequestDto.builder()
                .itemId(item.getId())
                .score(score)
                .content(content)
                .build();

        Long reviewId = reviewService.create(user.getId(), dto);

        Review findReview = reviewRepository.findById(reviewId).get();

        assertThat(10).isEqualTo(findReview.getScore());
        assertThat("리뷰내용").isEqualTo(findReview.getContent());
    }

    @Test
    void 리뷰_평점의_평균() {
        User user1 = createUser();
        User user2 = createUser();
        User user3 = createUser();

        Item
                item = createItem();

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

        reviewService.create(user1.getId(), dto1);
        reviewService.create(user2.getId(), dto2);
        reviewService.create(user3.getId(), dto3);

        double avg = item.reviewScoreAvg(item.getReviews());


        assertThat(6.0).isEqualTo(avg);

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