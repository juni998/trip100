package trip100.web.dto.item;

import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import lombok.Builder;
import lombok.Getter;

import static java.lang.Math.*;

@Getter
@Builder
public class ItemSearch {

    private static final int MAX_SIZE = 2000;

    @Builder.Default
    private Integer page = 1;

    @Builder.Default
    private Integer size = 6;

    public long getOffset() {
        return (long) (max(1, page) - 1) * min(size, MAX_SIZE);
    }
}
