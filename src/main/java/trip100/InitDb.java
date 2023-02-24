package trip100;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import trip100.domain.item.Item;
import trip100.domain.user.Role;
import trip100.domain.user.User;
import trip100.web.dto.item.ItemSaveRequestDto;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Profile("local")
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

            entityManager.persist(user);

            for (int i = 0; i < 100; i++) {
                entityManager.persist(Item.builder()
                        .title(("아이템 제목") + i)
                        .author(user.getName())
                        .content(("아이템") + i)
                        .price(10000 + i)
                        .stockQuantity(10 + i)
                        .build()
                );
            }

        }
    }


}
