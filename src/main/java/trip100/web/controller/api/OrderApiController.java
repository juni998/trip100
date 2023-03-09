package trip100.web.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import trip100.config.auth.LoginUser;
import trip100.config.auth.dto.SessionUser;
import trip100.domain.order.OrderRepository;
import trip100.service.OrderService;
import trip100.web.dto.OrderSimpleQueryTest;
import trip100.web.dto.order.OrderResponseDto;
import trip100.web.dto.order.OrderSaveRequestDto;
import trip100.web.dto.order.OrderSearch;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/order")
public class OrderApiController {

    private final OrderService orderService;

    private final OrderRepository orderRepository;

    @PostMapping("/save")
    public Long order_save(@RequestBody OrderSaveRequestDto requestDto) {

        return orderService.order(requestDto);
    }

    @DeleteMapping("/{orderId}")
    public void order_delete(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
    }

    @GetMapping("/orders")
    public List<OrderResponseDto> order_page(@ModelAttribute OrderSearch orderSearch, @LoginUser SessionUser user) {
        return orderService.findOrders(user.getId(), orderSearch);
    }

}
