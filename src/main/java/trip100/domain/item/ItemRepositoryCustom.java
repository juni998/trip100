package trip100.domain.item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import trip100.web.dto.item.ItemListResponseDto;
import trip100.web.dto.item.ItemSearch;

import java.util.List;

public interface ItemRepositoryCustom {

    List<Item> getListDESC(ItemSearch itemSearch);
}
