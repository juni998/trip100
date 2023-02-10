package trip100.web.controller.api;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import trip100.config.auth.LoginUser;
import trip100.config.auth.dto.SessionUser;
import trip100.service.CartService;
import trip100.web.dto.cart.AddCartRequestDto;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cart")
public class CartApiController {

    private final CartService cartService;

    @PostMapping("/save")
    public Long cart_save(@RequestBody AddCartRequestDto requestDto, @LoginUser SessionUser user) {
        return cartService.addItem(user.getId(), requestDto);
    }

    @DeleteMapping("/delete/{id}")
    public Long delete(@PathVariable Long id) {
        cartService.deleteCart(id);
        return id;
    }
}
