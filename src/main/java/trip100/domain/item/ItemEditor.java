package trip100.domain.item;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ItemEditor {

    private String title;
    private String content;
    private int price;
    private int stockQuantity;

    @Builder
    public ItemEditor(String title, String content, int price, int stockQuantity) {
        this.title = title;
        this.content = content;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
}
