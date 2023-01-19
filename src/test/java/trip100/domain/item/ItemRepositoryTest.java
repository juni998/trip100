package trip100.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @AfterEach
    public void cleanUp() {
        itemRepository.deleteAll();
    }


    /**
     * 아이템 저장 실패도 만들 것
     */

    @Test
    @DisplayName("아이템 저장 + 불러오기")
    void item_save() {
        String title = "테스트 아이템";
        String content = "테스트 내용";

        itemRepository.save(Item.builder()
                .title(title)
                .content(content)
                .build());

        List<Item> itemList = itemRepository.findAll();

        Item item = itemList.get(0);
        assertThat(item.getTitle()).isEqualTo(title);
        assertThat(item.getContent()).isEqualTo(content);

    }

    @Test
    @DisplayName("BaseTimeEntity 등록 확인")
    void BaseTimeEntity() {
        LocalDateTime time = LocalDateTime.of(2023, 1, 17, 0, 0, 0);
        String title = "테스트 아이템";
        String content = "테스트 내용";
        itemRepository.save(Item.builder()
                .title(title)
                .content(content)
                .build());

        List<Item> itemList = itemRepository.findAll();

        Item item = itemList.get(0);

        assertThat(item.getCreatedDate()).isAfter(time);
        assertThat(item.getModifiedDate()).isAfter(time);

    }

    @Test
    void 아이템_리스트_아이템_내림차순_정렬() {
        itemRepository.save(Item.builder()
                .title("제목1")
                .content("내용1")
                .build());
        itemRepository.save(Item.builder()
                .title("제목2")
                .content("내용2")
                .build());
        itemRepository.save(Item.builder()
                .title("제목3")
                .content("내용3")
                .build());

        List<Item> allDesc = itemRepository.findAllDesc();

        assertThat(allDesc.size()).isEqualTo(3);
        assertThat(allDesc.get(0).getTitle()).isEqualTo("제목3");
        assertThat(allDesc.get(1).getTitle()).isEqualTo("제목2");
        assertThat(allDesc.get(2).getTitle()).isEqualTo("제목1");

    }
}