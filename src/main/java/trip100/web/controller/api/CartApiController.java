package trip100.web.controller.api;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import trip100.config.auth.LoginUser;
import trip100.config.auth.dto.SessionUser;
import trip100.service.CartService;
import trip100.web.dto.cart.AddCartRequestDto;
import trip100.web.dto.cart.CartListResponseDto;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/cart")
public class CartApiController {

    private final CartService cartService;

    @PostMapping("/save")
    public void cart_save(@RequestBody AddCartRequestDto requestDto, @LoginUser SessionUser user) {
        cartService.addItem(user.getId(), requestDto);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        cartService.deleteCart(id);
    }

    @GetMapping("/list")
    public List<CartListResponseDto> cart_list(@LoginUser SessionUser user) {
        return cartService.findAll(user.getId());
    }
}
