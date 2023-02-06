package trip100.web.dto.item;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import trip100.domain.item.Item;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemResponseDto {

    private Long id;
    private String title;
    private String author;
    private String content;
    private int price;

    private int stockQuantity;




    @Builder
    public ItemResponseDto(Item entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.content = entity.getContent();
        this.price = entity.getPrice();
        this.stockQuantity = entity.getStockQuantity();
    }
}
