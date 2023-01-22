package trip100.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import trip100.domain.item.Item;
import trip100.domain.item.ItemRepository;
import trip100.web.dto.item.ItemResponseDto;
import trip100.web.dto.item.ItemSaveRequestDto;
import trip100.web.dto.item.ItemUpdateRequestDto;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ItemApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mvc;

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @AfterEach
    void clear() {
        itemRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    void 아이템_저장() throws Exception {
        String title = "제목";
        String content = "내용";
        String author = "작성자";

        ItemSaveRequestDto requestDto = ItemSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();

        String url = "http://localhost:" + port + "/item/save";
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                        .andExpect(status().isOk()
                );

        List<Item> all = itemRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
        assertThat(all.get(0).getAuthor()).isEqualTo(author);


    }

    @Test
    @WithMockUser(roles = "USER")
    void 아이템_수정() throws Exception {
        Item saveItem = itemRepository.save(Item.builder()
                .title("제목")
                .content("내용")
                .author("작성자")
                .build()
        );

        Long saveItemId = saveItem.getId();
        String updateTitle = "수정된 제목";
        String updateContent = "수정된 내용";

        ItemUpdateRequestDto requestDto = ItemUpdateRequestDto.builder()
                .title(updateTitle)
                .content(updateContent)
                .build();

        String url = "http://localhost:" + port + "/item/" + saveItemId;

        HttpEntity<ItemUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        mvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk()
                );

        List<Item> all = itemRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(updateTitle);
        assertThat(all.get(0).getContent()).isEqualTo(updateContent);

    }

    @Test
    @WithMockUser("USER")
    void 아이템_삭제() throws Exception {
        Item saveItem = itemRepository.save(Item.builder()
                .title("제목")
                .content("내용")
                .author("작성자")
                .build()
        );



        Long saveItemId = saveItem.getId();

        String url = "http://localhost:" + port + "/item/" + saveItemId;

        mvc.perform(delete(url)).andExpect(status().isOk());

        assertThat(itemRepository.findById(saveItemId)).isEmpty();

    }

}