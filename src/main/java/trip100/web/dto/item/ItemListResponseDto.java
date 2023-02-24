package trip100.web.dto.item;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import trip100.domain.item.Item;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemListResponseDto {

    private Long id;

    private String title;
    private String author;
    private int price;
    private LocalDateTime modifiedDate;

    public ItemListResponseDto(Item item) {
        this.id = item.getId();
        this.title = item.getTitle();
        this.author = item.getAuthor();
        this.price = item.getPrice();
        this.modifiedDate = item.getModifiedDate();
    }

    @Builder
    public ItemListResponseDto(Long id, String title, String author, int price, LocalDateTime modifiedDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.modifiedDate = modifiedDate;
    }
}
