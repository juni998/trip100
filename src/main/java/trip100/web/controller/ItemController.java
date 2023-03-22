package trip100.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import trip100.config.auth.LoginUser;
import trip100.config.auth.dto.SessionUser;
import trip100.service.ItemService;
import trip100.web.dto.item.ItemSearch;

@RequiredArgsConstructor
@Controller
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/item/save")
    public String itemSave(@LoginUser SessionUser user) {
        return "item/item-save";
    }

    @GetMapping("/item/info/{id}")
    public String itemUpdate(@PathVariable Long id, Model model, @LoginUser SessionUser user) {
        model.addAttribute("item", itemService.findById(id));
        model.addAttribute("userId", user.getId());
        return "item/item-info";
    }

    @GetMapping("/item/list")
    public String item_list(@ModelAttribute ItemSearch itemSearch, Model model, @LoginUser SessionUser user) {
        model.addAttribute("item", itemService.findAll(itemSearch));

        return "item/item-list";
    }
}
