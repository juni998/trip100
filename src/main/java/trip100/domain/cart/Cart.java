package trip100.domain.cart;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import trip100.domain.item.Item;
import trip100.domain.user.User;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "cart_count")
    private int count;


    @Builder
    public Cart(User user, Item item, int count) {
        this.user = user;
        this.item = item;
        this.count = count;
    }

    public void createCart(User user) {
        user.getCart().add(this);
    }


    public static Cart addCart(User user, Item item, int count) {

        return builder()
                .user(user)
                .item(item)
                .count(count)
                .build();
    }
}
