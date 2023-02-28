package trip100.web.dto.order;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.criteria.CriteriaBuilder;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Getter
@Builder
public class OrderSearch {

    private static final int MAX_SIZE = 2000;

    @Builder.Default
    private Integer page = 1;

    @Builder.Default
    private Integer size = 5;

    public long getOffset() {
        return (long) (max(1, page) - 1) * min(size, MAX_SIZE);
    }
}
