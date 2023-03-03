package trip100.web.dto.item;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemUpdateRequestDto {
    @NotNull
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;
    @NotNull
    @NotBlank(message = "내용을 입력해주세요.")
    private String content;
    @NotNull
    @NotBlank(message = "가격을 입력해주세요.")
    private int price;
    @NotNull
    @NotBlank(message = "재고수량을 입력해주세요.")
    private int stockQuantity;

    @Builder
    public ItemUpdateRequestDto(String title, String content, int price, int stockQuantity) {
        this.title = title;
        this.content = content;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

}
