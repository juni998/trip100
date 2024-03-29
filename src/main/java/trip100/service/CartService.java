package trip100.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import trip100.domain.cart.Cart;
import trip100.domain.cart.CartRepository;
import trip100.domain.item.Item;
import trip100.domain.item.ItemRepository;
import trip100.domain.user.User;
import trip100.domain.user.UserRepository;
import trip100.exception.CartNotFoundException;
import trip100.exception.ItemNotFoundException;
import trip100.exception.UserNotFoundException;
import trip100.web.dto.cart.AddCartRequestDto;
import trip100.web.dto.cart.CartListResponseDto;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
@Transactional
public class CartService {

    private final CartRepository cartRepository;

    private final UserRepository userRepository;

    private final ItemRepository itemRepository;


    public void addItem(AddCartRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(UserNotFoundException::new);

        Item item = itemRepository.findById(requestDto.getItemId())
                .orElseThrow(ItemNotFoundException::new);

        Cart cart = Cart.addCart(user, item, requestDto.getCount());

        cartRepository.save(cart);

        cart.createCart(user);
    }

    @Transactional(readOnly = true)
    public List<CartListResponseDto> findAll(Long userId) {
        return cartRepository.findListByUserId(userId).stream()
                .map(CartListResponseDto::new)
                .collect(Collectors.toList());
    }

    public void deleteCart(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(CartNotFoundException::new);

        cartRepository.delete(cart);
    }

}
