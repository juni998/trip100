package trip100;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import trip100.domain.item.Item;
import trip100.domain.user.Role;
import trip100.domain.user.User;
import trip100.web.dto.item.ItemSaveRequestDto;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager entityManager;
        public void dbInit1() {
            User user = User.builder()
                    .name("test1")
                    .email("abc@naver.com")
                    .role(Role.SELLER)
                    .build();

            Item item = Item.builder()
                    .title("아이템제목")
                    .author(user.getName())
                    .content("아이템1")
                    .price(10000)
                    .stockQuantity(10)
                    .build();

            entityManager.persist(user);
            entityManager.persist(item);
        }
    }


}
