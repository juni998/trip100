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
import trip100.domain.item.ItemRepository;
import trip100.domain.user.Role;
import trip100.domain.user.User;
import trip100.web.dto.cart.AddCartRequestDto;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
    ItemRepository itemRepository;
    @Autowired
    EntityManager em;

    @Test
    void 카트에_상품이_저장된다() {
        User user = createUser();
        Item item = createItem();

        AddCartRequestDto addCartRequestDto = AddCartRequestDto.builder()
                .userId(user.getId())
                .itemId(item.getId())
                .count(5)
                .build();

        cartService.addItem(addCartRequestDto);

        Cart getCart = cartRepository.findAll().get(0);

        assertThat(getCart.getItem().getTitle()).isEqualTo("제목");
        assertThat(getCart.getItem().getContent()).isEqualTo("내용");
        assertThat(getCart.getItem().getAuthor()).isEqualTo("판매자");
        assertThat(getCart.getCount()).isEqualTo(5);
    }

    @Test
    void 카트에_여러_상품이_저장되고_조회한다() {
        User user = createUser();

        List<Item> requestItems = IntStream.range(0, 5)
                .mapToObj(i -> Item.builder()
                        .title("제목 - " + i)
                        .content("내용 -" + i)
                        .price(10000 + i)
                        .stockQuantity(100 + i)
                        .build())
                .collect(Collectors.toList());

        itemRepository.saveAll(requestItems);

        List<AddCartRequestDto> dtoList = IntStream.range(0, 5)
                .mapToObj(i -> AddCartRequestDto.builder()
                        .userId(user.getId())
                        .itemId(requestItems.get(i).getId())
                        .count(1)
                        .build())
                .collect(Collectors.toList());


        IntStream.range(0, 5).forEach(i -> cartService.addItem(dtoList.get(i)));

        List<Cart> allById = cartRepository.findListByUserId(user.getId());

        assertThat(allById.size()).isEqualTo(5);
        assertThat(user.getId()).isEqualTo(allById.get(0).getUser().getId());
    }

    @Test
    void 저장된_아이템을_장바구니에서_삭제한다() {
        User user = createUser();
        Item item = createItem();

        AddCartRequestDto dto1 = AddCartRequestDto.builder()
                .userId(user.getId())
                .itemId(item.getId())
                .count(1)
                .build();


        cartService.addItem(dto1);

        cartService.deleteCart(user.getId());

        assertThat(cartRepository.findAll().size()).isEqualTo(0);
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
                .author("판매자")
                .price(10000)
                .stockQuantity(100)
                .build();
        em.persist(item);
        return item;
    }

}