package trip100.web.dto.item;

import lombok.AccessLevel;
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

    private LocalDateTime modifiedDate;

    public ItemListResponseDto(Item entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.modifiedDate = entity.getModifiedDate();
    }
}
