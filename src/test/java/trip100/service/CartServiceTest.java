package trip100.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import trip100.domain.address.Address;
import trip100.domain.cart.Cart;
import trip100.domain.cart.CartRepository;
import trip100.domain.item.Item;
import trip100.domain.user.Role;
import trip100.domain.user.User;
import trip100.web.dto.cart.AddCartRequestDto;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class CartServiceTest {

    @Autowired
    CartService cartService;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    EntityManager em;

    @Test
    void 카트에_상품이_저장된다() {
        User user = createUser();
        Item item = createItem();

        AddCartRequestDto addCartRequestDto = AddCartRequestDto.builder()
                .userId(user.getId())
                .itemId(item.getId())
                .quantity(5)
                .build();

        cartService.addItem(addCartRequestDto);

        Cart getCart = cartRepository.findAll().get(0);

        assertThat(5).isEqualTo(getCart.getQuantity());
    }


    private User createUser() {
        User user = User.builder()
                .name("이름")
                .email("aaa@gmail.com")
                .role(Role.USER)
                .address(new Address("서울", "은평구", "123-123"))
                .build();

        em.persist(user);

        return user;
    }

    private Item createItem() {
        Item item = Item.builder()
                .title("제목")
                .content("내용")
                .price(10000)
                .stockQuantity(100)
                .build();
        em.persist(item);
        return item;
    }

}