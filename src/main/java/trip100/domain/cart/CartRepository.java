package trip100.domain.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import trip100.web.dto.cart.CartListResponseDto;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findListByUserId(Long userId);

}
