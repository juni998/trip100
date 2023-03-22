package trip100.web.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import trip100.config.auth.LoginUser;
import trip100.config.auth.dto.SessionUser;
import trip100.domain.item.ItemRepository;
import trip100.service.ItemService;
import trip100.web.dto.item.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/item")
public class ItemApiController {

    private final ItemService itemService;

    private final ItemRepository itemRepository;


    @PostMapping("/save")
    public void item_save(@RequestBody ItemSaveRequestDto requestDto) {
        itemService.save(requestDto);
    }

    @GetMapping("/{id}")
    public ItemResponseDto findById(@PathVariable Long id) {
        return itemService.findById(id);
    }

    @PatchMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody
    ItemUpdateRequestDto requestDto) {
        itemService.update(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        itemService.delete(id);
    }


    @GetMapping("/recommend")
    public List<ItemListResponseDto> itemRecommend() {
        return itemService.recommendItem();
    }
}
