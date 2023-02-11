package trip100.web.dto.review;

import lombok.Builder;
import lombok.Getter;
import trip100.domain.review.Review;

@Getter
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
