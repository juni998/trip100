package trip100.service.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import trip100.domain.item.ItemRepository;
import trip100.web.dto.item.ItemResponseDto;
import trip100.web.dto.item.ItemSaveRequestDto;
import trip100.web.dto.item.ItemUpdateRequestDto;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Test
    void 아이템_저장() {
        Long saveItem = saveItem();

        ItemResponseDto findItem = itemService.findById(saveItem);

        assertThat(saveItem).isEqualTo(findItem.getId());
    }

    @Test
    void 아이템_수정() {
        Long saveItem = saveItem();

        Long updateItem = itemService.update(saveItem, ItemUpdateRequestDto.builder()
                .title("변경된 제목")
                .content("변경된 내용")
                .build());

        ItemResponseDto findItem = itemService.findById(updateItem);

        assertThat(saveItem).isEqualTo(updateItem);
        assertThat(findItem.getTitle()).isEqualTo("변경된 제목");
        assertThat(findItem.getContent()).isEqualTo("변경된 내용");
    }

    @Test
    void 아이템_삭제(){
        Long saveItem = saveItem();

        itemService.delete(saveItem);

        assertThatThrownBy(() -> itemService.findById(saveItem))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 아이템이 없습니다");
    }

    private Long saveItem() {
        return itemService.save(ItemSaveRequestDto.builder()
                .title("제목")
                .content("내용")
                .price(10000)
                .stockQuantity(100)
                .build());
    }



}