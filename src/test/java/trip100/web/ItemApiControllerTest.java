package trip100.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import trip100.domain.item.Item;
import trip100.domain.item.ItemRepository;
import trip100.web.dto.item.ItemResponseDto;
import trip100.web.dto.item.ItemSaveRequestDto;
import trip100.web.dto.item.ItemUpdateRequestDto;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class ItemApiControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    WebApplicationContext context;

    @BeforeEach
    void clear() {
        itemRepository.deleteAll();
    }

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("/item/save 요청시 DB에 값이 저장된다")
    void item_save() throws Exception {

        ItemSaveRequestDto dto = ItemSaveRequestDto.builder()
                .title("제목")
                .author("작성자")
                .content("내용")
                .price(1000)
                .stockQuantity(999)
                .build();

        String json = objectMapper.writeValueAsString(dto);

        mvc.perform(post("/item/save")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());
        Item item = itemRepository.findAll().get(0);

        assertThat(itemRepository.count()).isEqualTo(1L);
        assertThat(item.getTitle()).isEqualTo("제목");
        assertThat(item.getContent()).isEqualTo("내용");
    }

    @Test
    @DisplayName("아이템 1개 조회")
    void findItemOne() throws Exception {
        Item item = saveItem();

        mvc.perform(get("/item/{id}", item.getId())
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(item.getId()))
                .andExpect(jsonPath("$.title").value(item.getTitle()))
                .andExpect(jsonPath("$.content").value(item.getContent()))
                .andExpect(jsonPath("$.price").value(item.getPrice()))
                .andExpect(jsonPath("$.stockQuantity").value(item.getStockQuantity()))
                .andDo(print());

    }

    @Test
    @DisplayName("아이템 여러개 조회")
    void findItemList() throws Exception {
        List<Item> requestItems = IntStream.range(1, 31)
                .mapToObj(i -> Item.builder()
                        .title("제목 - " + i)
                        .author("작성자 - " + i)
                        .build())
                .collect(Collectors.toList());
        itemRepository.saveAll(requestItems);

        mvc.perform(get("/item/list?page=1&size=6")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(6))
                .andExpect(jsonPath("$[0].title").value("제목 - 30"))
                .andExpect(jsonPath("$[0].author").value("작성자 - 30"))
                .andExpect(jsonPath("$[5].title").value("제목 - 25"))
                .andExpect(jsonPath("$[5].author").value("작성자 - 25"))
                .andDo(print());
    }

    @Test
    @DisplayName("아이템 제목 수정")
    void item_update() throws Exception {
        Item item = saveItem();

        ItemUpdateRequestDto build = ItemUpdateRequestDto.builder()
                .title("수정된 제목")
                .content("내용")
                .price(1000)
                .stockQuantity(10)
                .build();

        mvc.perform(patch("/item/{id}", item.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(build))
                )
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @DisplayName("존재하지 않는 아이템 조회")
    void findItemOne_exception() throws Exception {
        Item item = saveItem();

        mvc.perform(get("/item/{id}", item.getId() + 1)
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andDo(print());

    }

    @Test
    @DisplayName("존재하지 않는 아이템 제목 수정")
    void item_update_exception() throws Exception {
        Item item = saveItem();

        ItemUpdateRequestDto build = ItemUpdateRequestDto.builder()
                .title("수정된 제목")
                .content("내용")
                .price(1000)
                .stockQuantity(10)
                .build();

        mvc.perform(patch("/item/{id}", item.getId() + 1)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(build))
                )
                .andExpect(status().isNotFound())
                .andDo(print());

    }


    private Item saveItem() {
        return itemRepository.save(Item.builder()
                .title("제목")
                .content("내용")
                .price(10000)
                .stockQuantity(100)
                .build());
    }


}