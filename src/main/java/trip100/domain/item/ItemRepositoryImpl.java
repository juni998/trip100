package trip100.domain.item;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import trip100.web.dto.item.ItemListResponseDto;
import trip100.web.dto.item.ItemSearch;


import javax.persistence.EntityManager;

import java.util.List;
import java.util.Random;

import static trip100.domain.item.QItem.*;

@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Item> getListDESC(ItemSearch itemSearch) {
        return queryFactory
                .select(item)
                .from(item)
                .limit(itemSearch.getSize())
                .offset(itemSearch.getOffset())
                .orderBy(item.id.desc())
                .fetch();
    }

    @Override
    public List<Item> getListRecommend() {
        return queryFactory
                .select(item)
                .from(item)
                .orderBy(Expressions.numberTemplate(Double.class, "function('rand')").asc())
                .limit(5)
                .fetch();
    }
}
