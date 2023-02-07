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

    private int quantity;

    @Builder
    public AddCartRequestDto(Long userId, Long itemId, int quantity) {
        this.userId = userId;
        this.itemId = itemId;
        this.quantity = quantity;
    }
}
