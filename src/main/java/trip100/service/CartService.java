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


    public Long addItem(Long userId, AddCartRequestDto requestDto) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("해당 유저는 존재하지 않습니다.")
        );
        Item item = itemRepository.findById(requestDto.getItemId()).orElseThrow(
                () -> new IllegalArgumentException("해당 아이템이 없습니다.")
        );

        Cart cart = Cart.addCart(user, item, requestDto.getCount());

        cartRepository.save(cart);

        cart.createCart(user);

        return cart.getId();
    }

    @Transactional(readOnly = true)
    public List<CartListResponseDto> findAll(Long userId) {
        return cartRepository.findListByUserId(userId).stream()
                .map(CartListResponseDto::new)
                .collect(Collectors.toList());
    }

    public void deleteCart(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 상품이 장바구니에 존재하지 않습니다")
        );

        cartRepository.delete(cart);


    }

}
