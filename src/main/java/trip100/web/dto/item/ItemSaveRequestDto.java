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

    @Builder
    public ItemSaveRequestDto(String title, String author, String content) {
        this.title = title;
        this.author = author;
        this.content = content;
    }

    public Item toEntity() {
        return Item.builder()
                .title(title)
                .author(author)
                .content(content)
                .build();
    }
}
