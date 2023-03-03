package trip100.web.dto.review;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import trip100.domain.review.Review;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateReviewRequestDto {

    private Long itemId;

    private int score;

    private String content;

    @Builder
    public CreateReviewRequestDto(Long itemId, int score, String content) {
        this.itemId = itemId;
        this.score = score;
        this.content = content;
    }

}
