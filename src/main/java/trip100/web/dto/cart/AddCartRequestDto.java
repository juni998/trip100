package trip100.web.dto.cart;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddCartRequestDto {

    private Long userId;
    private Long itemId;
    private int count;

    @Builder
    public AddCartRequestDto(Long userId, Long itemId, int count) {
        this.userId = userId;
        this.itemId = itemId;
        this.count = count;
    }
}
