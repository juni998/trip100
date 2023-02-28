package trip100.web.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import trip100.service.OrderService;
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

    @PostMapping("/save")
    public Long order_save(@RequestBody OrderSaveRequestDto requestDto) {

        return orderService.order(requestDto);
    }

    @DeleteMapping("/{orderId}")
    public void order_delete(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
    }

    @GetMapping("/orders/{userId}")
    public List<OrderResponseDto> order_page(@PathVariable Long userId,@ModelAttribute OrderSearch orderSearch) {
        return orderService.findOrders(userId, orderSearch);
    }
}
