package trip100.domain.order;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import trip100.domain.delivery.QDelivery;
import trip100.domain.user.QUser;
import trip100.web.dto.OrderSimpleQueryTest;
import trip100.web.dto.QOrderSimpleQueryTest;
import trip100.web.dto.order.*;

import java.util.List;

import static trip100.domain.delivery.QDelivery.*;
import static trip100.domain.item.QItem.*;
import static trip100.domain.order.QOrder.order;
import static trip100.domain.order.QOrderItem.*;
import static trip100.domain.user.QUser.*;

@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public List<Order> findOrders(Long userId, OrderSearch orderSearch) {
        return queryFactory
                .selectFrom(order)
                .limit(orderSearch.getSize())
                .offset(orderSearch.getOffset())
                .leftJoin(order.user, user)
                .leftJoin(order.delivery, delivery)
                .orderBy(order.id.desc())
                .where(order.user.id.eq(userId))
                .fetch();
    }

}
