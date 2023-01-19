package trip100.web;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import trip100.service.item.ItemService;
import trip100.web.dto.item.ItemResponseDto;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final ItemService itemService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/item")
    public String item(Model model) {
        model.addAttribute("item", itemService.findAllDesc());

        return "item/item-list";
    }

    @GetMapping("/item/save")
    public String itemSave() {
        return "item/item-save";
    }

    @GetMapping("/item/update/{id}")
    public String itemUpdate(@PathVariable Long id, Model model) {
        ItemResponseDto dto = itemService.findById(id);
        model.addAttribute("item", dto);
        return "item/item-update";
    }

}
