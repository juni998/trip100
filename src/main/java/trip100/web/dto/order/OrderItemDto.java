package trip100.web.dto.order;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import trip100.domain.order.OrderItem;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItemDto {

    private String itemTitle;

    private int orderPrice;

    private int count;

    @Builder
    public OrderItemDto(OrderItem orderItem) {
        this.itemTitle = orderItem.getItem().getTitle();
        this.orderPrice = orderItem.getOrderPrice();
        this.count = orderItem.getCount();
    }
}
