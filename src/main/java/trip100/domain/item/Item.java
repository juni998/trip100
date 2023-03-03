package trip100.domain.item;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import trip100.domain.BaseTimeEntity;
import trip100.domain.review.Review;
import trip100.exception.NotEnoughStockException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Item extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    private String title;

    private String author;

    private String content;

    private int price;

    private int stockQuantity;

    @OneToMany(mappedBy = "item")
    private List<Review> reviews = new ArrayList<>();


    @Builder
    public Item(String title, String author, String content, int price, int stockQuantity) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException();
        }
        this.stockQuantity = restStock;
    }

    public void update(String title, String content, int price, int stockQuantity) {
        this.title = title;
        this.content = content;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public double reviewScoreAvg(List<Review> reviews) {
        return reviews.stream()
                .mapToInt(Review::getScore)
                .average()
                .orElse(0);
    }

}
