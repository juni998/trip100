package trip100.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import trip100.service.item.ItemService;
import trip100.web.dto.item.ItemResponseDto;
import trip100.web.dto.item.ItemSaveRequestDto;
import trip100.web.dto.item.ItemUpdateRequestDto;

@RequiredArgsConstructor
@RestController
@RequestMapping("/item")
public class ItemApiController {

    private final ItemService itemService;

    @PostMapping("/save")
    public Long item_save(@RequestBody ItemSaveRequestDto requestDto) {
        return itemService.save(requestDto);
    }

    @GetMapping("/{id}")
    public ItemResponseDto findById(@PathVariable Long id) {
        return itemService.findById(id);
    }

    @PutMapping("/{id}")
    public Long update(@PathVariable Long id, @RequestBody
    ItemUpdateRequestDto requestDto) {
        return itemService.update(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public Long delete(@PathVariable Long id) {
        itemService.delete(id);
        return id;
    }
}
