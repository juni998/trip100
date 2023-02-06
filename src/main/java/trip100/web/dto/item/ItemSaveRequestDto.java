package trip100.web.dto.item;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import trip100.domain.item.Item;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemSaveRequestDto {

    private String title;
    private String author;
    private String content;
    private int price;

    private int stockQuantity;


    @Builder
    public ItemSaveRequestDto(String title, String author, String content, int price, int stockQuantity) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public Item toEntity() {
        return Item.builder()
                .title(title)
                .author(author)
                .content(content)
                .price(price)
                .stockQuantity(stockQuantity)
                .build();
    }
}
