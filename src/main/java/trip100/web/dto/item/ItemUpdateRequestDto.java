package trip100.web.dto.item;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import trip100.domain.item.Address;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemUpdateRequestDto {
    private String title;

    private String content;

    private Address address;

    private int price;

    @Builder
    public ItemUpdateRequestDto(String title, String content, int price, Address address) {
        this.title = title;
        this.content = content;
        this.price = price;
        this.address = address;
    }

}
