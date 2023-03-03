package trip100.web.dto.item;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import trip100.domain.item.Item;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemSaveRequestDto {

    @NotNull
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;
    private String author;
    @NotNull
    @NotBlank(message = "내용을 입력해주세요.")
    private String content;
    @NotNull
    @NotBlank(message = "가격을 입력해주세요.")
    private int price;
    @NotNull
    @NotBlank(message = "재고1수량을 입력해주세요.")
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
