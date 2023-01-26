package trip100.domain.item;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import trip100.domain.BaseTimeEntity;
import trip100.domain.review.Review;

import javax.persistence.*;

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

    @Embedded
    private Address address;

    private int price;

    @OneToMany(mappedBy = "item")
    private Review review;

    @Builder
    public Item(String title, String content, String author, int price, Address address, Review review) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.price = price;
        this.address = address;
        this.review = review;
    }

    public void update(String title, String content, int price, Address address) {
        this.title = title;
        this.content = content;
        this.price = price;
        this.address = address;
    }
}
