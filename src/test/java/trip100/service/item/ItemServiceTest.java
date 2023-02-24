package trip100.service.item;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import trip100.domain.item.Item;
import trip100.domain.item.ItemRepository;
import trip100.service.ItemService;
import trip100.web.dto.item.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

    @Test
    void 아이템_저장() {
        Item item = Item.builder()
                .title("제목")
                .content("내용")
                .price(10000)
                .stockQuantity(100)
                .build();
        itemRepository.save(item);

        ItemResponseDto findItem = itemService.findById(item.getId());

        assertThat(findItem.getTitle()).isEqualTo("제목");
        assertThat(findItem.getContent()).isEqualTo("내용");
        assertThat(findItem.getPrice()).isEqualTo(10000);
        assertThat(findItem.getStockQuantity()).isEqualTo(100);


    }

    @Test
    void 아이템_수정() {
        Item item = Item.builder()
                .title("제목")
                .content("내용")
                .price(10000)
                .stockQuantity(100)
                .build();
        itemRepository.save(item);

        itemService.update(item.getId(), ItemUpdateRequestDto.builder()
                .title("변경된 제목")
                .content("변경된 내용")
                .build());

        ItemResponseDto findItem = itemService.findById(item.getId());

        assertThat(findItem.getTitle()).isEqualTo("변경된 제목");
        assertThat(findItem.getContent()).isEqualTo("변경된 내용");
    }

    @Test
    void 아이템_제목만_수정() {
        Item item = Item.builder()
                .title("제목")
                .content("내용")
                .price(10000)
                .stockQuantity(100)
                .build();
        itemRepository.save(item);

        itemService.update(item.getId(), ItemUpdateRequestDto.builder()
                .title("변경된 제목")
                .content("내용")
                .build());

        ItemResponseDto findItem = itemService.findById(item.getId());

        assertThat(findItem.getTitle()).isEqualTo("변경된 제목");
        assertThat(findItem.getContent()).isEqualTo("내용");
    }


    @Test
    void 아이템_삭제(){
        Item item = Item.builder()
                .title("제목")
                .content("내용")
                .price(10000)
                .stockQuantity(100)
                .build();
        itemRepository.save(item);

        itemService.delete(item.getId());

        assertThat(itemRepository.count()).isEqualTo(0);
        assertThatThrownBy(() -> itemService.findById(item.getId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 아이템이 없습니다");
    }

    @Test
    void 아이템_리스트_페이징() {
        List<Item> requestItems = IntStream.range(1, 31)
                .mapToObj(i -> Item.builder()
                        .title("제목 - " + i)
                        .author("작성자 - " + i)
                        .build())
                .collect(Collectors.toList());
        itemRepository.saveAll(requestItems);

        ItemSearch itemSearch = ItemSearch.builder()
                .page(1)
                .build();

        List<ItemListResponseDto> items = itemService.findAll(itemSearch);

        assertThat(items.size()).isEqualTo(6);
        assertThat(items.get(0).getTitle()).isEqualTo("제목 - 30");
        assertThat(items.get(0).getAuthor()).isEqualTo("작성자 - 30");
        assertThat(items.get(5).getTitle()).isEqualTo("제목 - 25");
        assertThat(items.get(5).getAuthor()).isEqualTo("작성자 - 25");
    }

}