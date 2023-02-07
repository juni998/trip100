package trip100.domain.order;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import trip100.domain.delivery.Delivery;
import trip100.domain.delivery.DeliveryStatus;
import trip100.domain.item.Item;
import trip100.domain.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Table(name="orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @BatchSize(size = 1000)
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Builder
    public Order(User user, Delivery delivery, List<OrderItem> orderItems, LocalDateTime orderDate, OrderStatus orderStatus) {
        this.user = user;
        this.delivery = delivery;
        this.orderItems = orderItems;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        user.getOrders().add(this);
        delivery.builder().order(this);
    }


    public static Order createOrder(User user, Delivery delivery, OrderItem... orderItems) {
        return builder()
                .user(user)
                .delivery(delivery)
                .orderItems(Arrays.asList(orderItems))
                .orderStatus(OrderStatus.ORDER)
                .orderDate(LocalDateTime.now())
                .build();

    }

    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다");
        }
        this.orderStatus = OrderStatus.CANCEL;
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    public int getTotalPrice() {
        return orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();
    }

}
