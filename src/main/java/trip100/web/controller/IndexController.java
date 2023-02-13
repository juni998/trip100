package trip100.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import trip100.config.auth.LoginUser;
import trip100.config.auth.dto.SessionUser;
import trip100.service.CartService;
import trip100.service.ItemService;
import trip100.service.OrderService;

@RequiredArgsConstructor
@Controller
@Slf4j
public class IndexController {
    private final ItemService itemService;

    private final CartService cartService;


    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    @GetMapping("/item")
    public String item(Model model, @LoginUser SessionUser user) {
        log.info("userId : " + user.getId());
        model.addAttribute("item", itemService.findAllDesc());
        model.addAttribute("user_id", user.getId());
        return "item/item-list";
    }

    @GetMapping("/item/save")
    public String itemSave() {
        return "item/item-save";
    }

    @GetMapping("/item/update/{id}")
    public String itemUpdate(@PathVariable Long id, Model model, @LoginUser SessionUser user) {
        log.info("userId : " + user.getId());
        model.addAttribute("item", itemService.findById(id));
        model.addAttribute("user_id", user.getId());
        return "item/item-update";
    }

    @GetMapping("/order/{itemId}")
    public String order(@PathVariable Long itemId, Model model, @LoginUser SessionUser user) {
        log.info("userId : " + user.getId());
        log.info("itemId : " + itemId);
        model.addAttribute("item", itemService.findById(itemId));
        model.addAttribute("user", user);
        return "order/order-save";
    }

    @GetMapping("/cart/list")
    public String cartList(Model model, @LoginUser SessionUser user) {
        model.addAttribute("cart", cartService.findAll(user.getId()));
        model.addAttribute("userId", user.getId());
        return "cart/cart-list";
    }


}
