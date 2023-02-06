package trip100.web.dto.order;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import trip100.domain.order.Order;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderSaveRequestDto {

    private Long userId;

    private Long itemId;

    private int count;

    @Builder
    public OrderSaveRequestDto(Long userId, Long itemId, int count) {
        this.userId = userId;
        this.itemId = itemId;
        this.count = count;

    }
}
