package trip100.web.dto.item;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemUpdateRequestDto {
    private String title;
    private String content;
    private int price;

    private int stockQuantity;

    @Builder
    public ItemUpdateRequestDto(String title, String content, int price, int stockQuantity) {
        this.title = title;
        this.content = content;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

}
