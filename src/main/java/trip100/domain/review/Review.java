package trip100.domain.review;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import trip100.domain.BaseTimeEntity;
import trip100.domain.item.Item;
import trip100.domain.user.User;

import javax.persistence.*;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Review extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private int score;

    private String content;

    @Builder
    public Review(Item item, User user, int score, String content) {
        this.item = item;
        this.user = user;
        this.score = score;
        this.content = content;

        user.getReviews().add(this);
        item.getReviews().add(this);
    }

    public static Review createReview(Item item, User user, int score, String content) {
        return builder()
                .item(item)
                .user(user)
                .score(score)
                .content(content)
                .build();
    }


}
