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
                .itemId(item.getId())
                .count(5)
                .build();

        cartService.addItem(user.getId(), addCartRequestDto);

        Cart getCart = cartRepository.findAll().get(0);

        assertThat(5).isEqualTo(getCart.getCount());
    }

    @Test
    void 카트에_여러_상품이_저장되고_조회한다() {
        User user = createUser();
        Item item1 = createItem();
        Item item2 = createItem();
        Item item3 = createItem();
        Item item4 = createItem();


        AddCartRequestDto dto1 = AddCartRequestDto.builder()
                .itemId(item1.getId())
                .count(1)
                .build();

        AddCartRequestDto dto2 = AddCartRequestDto.builder()
                .itemId(item2.getId())
                .count(1)
                .build();

        AddCartRequestDto dto3 = AddCartRequestDto.builder()
                .itemId(item3.getId())
                .count(1)
                .build();

        AddCartRequestDto dto4 = AddCartRequestDto.builder()
                .itemId(item4.getId())
                .count(1)
                .build();

        cartService.addItem(user.getId(), dto1);
        cartService.addItem(user.getId(), dto2);
        cartService.addItem(user.getId(), dto3);
        cartService.addItem(user.getId(), dto4);

        List<Cart> allById = cartRepository.findListByUserId(user.getId());

        assertThat(4).isEqualTo(allById.size());
        assertThat(user.getId()).isEqualTo(allById.get(0).getUser().getId());
    }

    @Test
    void 저장된_아이템을_장바구니에서_삭제한다() {
        User user = createUser();
        Item item = createItem();
        Item item2 = createItem();

        AddCartRequestDto dto1 = AddCartRequestDto.builder()
                .itemId(item.getId())
                .count(1)
                .build();

        AddCartRequestDto dto2 = AddCartRequestDto.builder()
                .itemId(item2.getId())
                .count(3)
                .build();

        cartService.addItem(user.getId(), dto1);
        cartService.addItem(user.getId(), dto2);

        cartService.deleteCart(user.getId());

        assertThat(1).isEqualTo(cartRepository.findAll().size());

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