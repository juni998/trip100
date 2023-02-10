package trip100.web.dto.cart;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import trip100.domain.cart.Cart;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartListResponseDto {

    private Long id;
    private String title;
    private int price;
    private int count;

    @Builder
    public CartListResponseDto(Cart entity) {
        this.id = entity.getId();
        this.title = entity.getItem().getTitle();
        this.price = entity.getItem().getPrice();
        this.count = entity.getCount();
    }
}
