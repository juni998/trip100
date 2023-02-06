package trip100.web.dto.order;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderSaveRequestDto {

    private Long userId;

    private Long ItemId;

    private int count;

    @Builder
    public OrderSaveRequestDto(Long userId, Long itemId, int count) {
        this.userId = userId;
        this.ItemId = itemId;
        this.count = count;
    }
}
