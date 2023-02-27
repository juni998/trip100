package trip100.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import trip100.domain.item.Item;
import trip100.domain.item.ItemRepository;
import trip100.domain.review.Review;
import trip100.domain.review.ReviewRepository;
import trip100.domain.user.User;
import trip100.domain.user.UserRepository;
import trip100.exception.ItemNotFoundException;
import trip100.exception.UserNotFoundException;
import trip100.web.dto.review.CreateReviewRequestDto;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final UserRepository userRepository;

    private final ItemRepository itemRepository;

    public Long create(Long userId, CreateReviewRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        Item item = itemRepository.findById(requestDto.getItemId())
                .orElseThrow(ItemNotFoundException::new);

        Review review = Review.createReview(item, user, requestDto.getScore(), requestDto.getContent());

        reviewRepository.save(review);

        return review.getId();
    }



}
