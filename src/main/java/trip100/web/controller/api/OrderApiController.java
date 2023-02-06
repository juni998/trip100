package trip100.web.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import trip100.service.order.OrderService;
import trip100.web.dto.order.OrderSaveRequestDto;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/order")
public class OrderApiController {

    private final OrderService orderService;

    @PostMapping("/save")
    public Long order_save(@RequestBody OrderSaveRequestDto requestDto) {
        log.info("userId : " + requestDto.getUserId());
        log.info("ItemId : " + requestDto.getItemId());
        log.info("count : " + requestDto.getCount());
        return orderService.order(requestDto);
    }

}
