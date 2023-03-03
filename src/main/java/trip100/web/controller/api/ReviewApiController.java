package trip100.web.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import trip100.config.auth.LoginUser;
import trip100.config.auth.dto.SessionUser;
import trip100.service.ReviewService;
import trip100.web.dto.review.CreateReviewRequestDto;

@RequiredArgsConstructor
@RestController
@RequestMapping("/review")
public class ReviewApiController {

    private final ReviewService reviewService;

    @PostMapping("/save")
    public void review_save(@RequestBody CreateReviewRequestDto requestDto, @LoginUser SessionUser user) {
        reviewService.create(user.getId(), requestDto);
    }
}
