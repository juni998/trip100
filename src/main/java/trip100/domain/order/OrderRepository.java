package trip100.domain.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {

    List<Order> findListByUserId(Long userId);
    Order findByUserId(Long userId);


}
